package jp.co.jwebgate.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.jwebgate.product.entities.Items;

/**
 * アイテムリポジトリ（検索）インターフェース
 *
 * @author y-wayama
 */
@Repository
public interface ItemsRepositoryEX {

	/**
	 * キーワード検索をしてアイテム検索を実行
	 *
	 * 	findByCondition(BeanMap conditions)
	 * 条件付きで検索を行う
	 * List<>格納できるデータは参照型のみ
	 *
	 * @param keyword キーワード
	 * @return レコード情報
	 */
	public Page<Items> findByConditions(String keyword, String conditions, Integer maxPrice,
										Integer minPrice, String[] tags,
										String sort, boolean sortSc, Pageable pageable);


}
