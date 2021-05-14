package jp.co.jwebgate.product.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jp.co.jwebgate.product.entities.Items;

/**
 * アイテムリポジトリ（検索用）詳細処理
 * Dao層には、@Componentのかわりに@Repositoryを使用することも可能
 * つまりDAOとRepositoryは同じもの
 *
 * @author y-wayama
 *
 */
@Repository
public class ItemsRepositoryEXImpl implements ItemsRepositoryEX {

	/**
	 * エンティティー・マネージャー
	 * @Autowiredとは
	 * アノテーションのAutowiring機能を使うと、
	 * @Bean設定をいちいち書かなくてもDIすることができる
	 * DIって何
	 * 日本語訳すると「依存性の注入」です。
     * SpringのDIコンテナの利点は大きく2つある
	 * ・クラスからnew演算子を消せる
     * ・インスタンス化を1回で済ませられる（Singleton
     * インスタンス変数（注入先の変数）の前に@Autowiredをつけると、
     * @Componentもしくは@Service、@Repository
     * アノテーションのついたクラスの中から該当するものを探し、
     * newしてインスタンスを突っ込んでくれる
     *
     * エンティティ・マネジャー（EntityManager）：
     * エンティティを管理するオブジェクト
     * @Entityを受け取りデータベースに関する処理を一括で行う
	 */
	@Autowired
	private EntityManager entityManager;

	/**
	 * 	findByCondition(BeanMap conditions)
	 * 条件付きで検索を行う
	 * List<>格納できるデータは参照型のみ
	 *
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<Items> findByConditions(String keyword, String conditions, Integer maxPrice,
										Integer minPrice, String[] tags,
										String sort, boolean sortSc, Pageable pageable){
		/**
		 * StringBuilderクラスを使うと、以下のような処理が簡単に行える
		 * ・文字列の結合
		 * ・文字列の挿入
		 * ・文字列の部分置換
		 * ・文字列の部分削除
		 * ・文字列の部分取出
		 * ・文字列の検索
		 *
		 * SQL文において「WHERE 1=1」とは必ず正であるという意味
		 *
		 *
		 */

		StringBuilder whereStrKey = new StringBuilder(" WHERE 1=1");
		StringBuilder whereStrPri = new StringBuilder();
		StringBuilder whereStrTags = new StringBuilder();
		StringBuilder whereStrSort = new StringBuilder();
		StringBuilder whereStrSortSc = new StringBuilder();

		/**
		 * キーワード検索の実行分を代入
		 * StringUtils.isEmpty()で中身がnullかどうか調べている
		 * 今回は否定なので
		 * キーワードがnullでないならtrue
		 */
		if(!StringUtils.isEmpty(keyword)){
			/**
			 * .appendでは
			 * 文字列の結合を行っている
			 * なぜかというと、検索条件をA,B,C...を
			 * 結合してしまって文字列を含む検索をしたい為（LIKE)
			 * 今回は商品名と商品説明から検索条件を引っ張る
			 */

			String key = keyword.replaceAll("　"," ");
			String keywords[] = key.split("\\s+");

			whereStrKey.append(" AND (");
			for(int i = 0; i < keywords.length; i++){
				if(i == 0){
					whereStrKey.append("((i.name LIKE :");
				}else{
					whereStrKey.append(conditions)
							   .append("((i.name LIKE :");
				}
				whereStrKey.append(keywords[i])
						   .append(")")
						   .append("OR (i.description LIKE :")
						   .append(keywords[i])
						   .append("))");

			}
			whereStrKey.append(")");

		}

		//料金範囲検索の条件式を代入
		//keywordがnullの場合→WHERE 1=1に対してAND
		//nullでない場合はwhereStrKeyに対してOR
		whereStrPri.append(" AND (")
				   .append("(i.price BETWEEN ")
				   .append(minPrice)
				   .append(" AND ")
				   .append(maxPrice)
				   .append(" ) )");

		//タグの検索の条件式
		//別テーブルなのでAND EXISTS
		if(tags != null && tags.length > 0){
			whereStrTags.append(" AND EXISTS(")
						.append(" SELECT 1 FROM ItemsTags it")
						.append(" WHERE i.id = it.itemsId")
						.append(" AND it.tagsId IN (:tagsIdList)")
						.append(" AND it.deleteFlg = '0'")
						.append(")");
		}
		whereStrSort.append(" ORDER BY");
		//ORDER BYの後にソート条件を追加
		switch(sort){
			case "id": //商品ID順
				whereStrSort.append(" i.id");
				break;
			case "name": //商品名順
				whereStrSort.append(" i.name");
				break;
			case "price": //商品価格順
				whereStrSort.append(" i.price");
				break;
			case "modified": //更新日時順
				whereStrSort.append(" i.modified");
				break;
			case "created": //作成日時順
				whereStrSort.append(" i.created");
				break;
			default:
				whereStrSort.append(" i.id");
		}

		//前提の設定 SortSc→true or false
		if(sortSc == true){
			whereStrSortSc.append(" DESC");
		}else{
			whereStrSortSc.append(" ASC");
		}

		//クエリを生成します、全ての実行分を結合
		StringBuilder queryStr = new StringBuilder("SELECT i FROM Items i");
		Query query = entityManager.createQuery
				(queryStr.append(whereStrKey)
						 .append(whereStrPri)
						 .append(whereStrTags)
						 .append(whereStrSort)
						 .append(whereStrSortSc).toString());

		/**
		 * パラメータのバインド
		 * keywordがそのままでは文字列にならない為
		 * もしキーワードがnullでなければ検索機能を使う為、
		 * キーワードを「%keyword%」の形に置き換えている
		 *
		 * .replaceAll→""の中を置き換える
		 * "_"の「_」をkeywordに変更
		 *
		 */
		if(!StringUtils.isEmpty(keyword)){
			String key = keyword.replaceAll("　"," ");
			String keywords[] = key.split("\\s+");
			for(int i = 0; i < keywords.length; i++){
				query.setParameter(keywords[i], "%" + keywords[i] + "%");
			}
		}
		//タグがNULLでなく、タグが0以上
		if (tags != null && tags.length > 0) {
			//Integer型のリストの作成
            List<Integer> tagsIdList = new ArrayList<Integer>();
            for (String tagId : tags) {
            	//リストにIDの追加
                tagsIdList.add(Integer.parseInt(tagId));
            }
            //文字を条件に変更
            query.setParameter("tagsIdList", tagsIdList);
        }

		//クエリの実行
		//検索結果でどのくらいのアイテムを取り出したのか
		int total = query.getResultList().size();
		//リストにクエリ発行したものを入れる。
		//開始位置で指定したクエリ結果だけを取得するにはsetFirstResultメソッドを使用する
		//1ページにどのくらい入れるかという部分
		//pageable.getoffsetで基になるページとページサイズに従って、実行するオフセットを返す。
		List<Items> list = query.setFirstResult(pageable.getOffset())
				//複数件あるクエリ結果の中から任意に指定した件数だけを取得するには
				//QueryインタフェースのsetMaxResultsメソッドを使用します。
				//setMaxResultsメソッドの記述形式を次に示します。
				//.getPageSize返される項目の数を返す。
								 .setMaxResults(pageable.getPageSize())
								 //検索結果エントリのリストを返します。
								 .getResultList();
		//Page型でresultで返す。
		Page<Items> result = new PageImpl(list,pageable,total);
		return result;
	}

}
