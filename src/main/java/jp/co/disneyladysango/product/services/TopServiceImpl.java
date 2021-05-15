package jp.co.disneyladysango.product.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import jp.co.disneyladysango.product.entities.Images;
import jp.co.disneyladysango.product.entities.Items;
import jp.co.disneyladysango.product.entities.ItemsTags;
import jp.co.disneyladysango.product.entities.Tags;
import jp.co.disneyladysango.product.forms.ItemsForm;
import jp.co.disneyladysango.product.forms.SearchForm;
import jp.co.disneyladysango.product.repositories.ImagesRepository;
import jp.co.disneyladysango.product.repositories.ItemsPageRepository;
import jp.co.disneyladysango.product.repositories.ItemsRepository;
import jp.co.disneyladysango.product.repositories.ItemsRepositoryEX;
import jp.co.disneyladysango.product.repositories.TagsRepository;

/**
 * トップサービス
 * Sping MVCでサービス層のクラス（ビジネスロジック等）に付与する。
 * Service は業務処理を提供する。
 *
 * implements「インターフェイスを実装する」
 * インターフェイスを継承してクラスを定義すること
 * @author k-tsuda
 */
@Service
public class TopServiceImpl implements TopService {

    /**
     *  アイテムリポジトリインターフェース
     */
    @Autowired
    ItemsRepository itemsRepository;

    /** アイテムリポジトリ(検索用での拡張) */
    @Autowired
    ItemsRepositoryEX itemsRepositoryEX;

    /** 画像リポジトリ */
    @Autowired
    ImagesRepository imagesRepository;

    /** タグリポジトリ */
    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    ItemsPageRepository itemsPageRepository;

    /**
     * IDからItems情報を取得します
     *
     * @param id ID
     * @return Items情報
     */
    @Override
    public Items getItems(Integer id) {

        return itemsRepository.findById(id);
    }

    /**
     * Items情報を取得します
     * 検索機能の設定を行います
     *
     * @return Items情報一覧
     */
    @Override
    public Page<Items> getItemsList(SearchForm searchForm, Pageable pageable) {

    	//初期値の設定
    	String keyword = null;
    	String conditions = null;
    	Integer maxPrice;
    	Integer minPrice;
    	String[] tags = null;
    	String sort;
    	boolean sortSc = searchForm.isSortSc();
    	//キーワードが入力されていたらキーワードを設定
    	if(searchForm.getKeyword() != null){
    //		keyword = searchForm.getKeyword();
    		//半角数字を全角数字へ返還
    		//理由は不明だがエラーが出ていたので。
    	 	StringBuffer keywords = new StringBuffer(searchForm.getKeyword());
    	 	for (int i = 0; i < searchForm.getKeyword().length(); i++) {
    	 		char c = searchForm.getKeyword().charAt(i);
    		    if (c >= '0' && c <= '9') {
    		    	keywords.setCharAt(i, (char)(c - '0' + '０'));
    		    }
    	 	}
    		//キーワードに戻す
    		keyword = keywords.toString();
    	}
    	//条件分岐をどうするか決定します
    	if(searchForm.getConditions() != null){
    		switch(searchForm.getConditions()){
    			case "1":
    				conditions = "AND";
    				break;
    			case "2":
    				conditions = "OR";
    				break;
    		}
    	}else{
    		conditions = "AND";
    	}

    	//最大金額が設定されていたら最大金額の設定
    	//されていない場合はintのMAX値を代入
    	if(searchForm.getMaxPrice() != null){
    		maxPrice = searchForm.getMaxPrice();
    	}else{
    		//SQLでのint最大値を代入
    		maxPrice = 2147483647;
    	}
    	//最小値が設定されていたら最小値を設定
    	//最小値が設定されていない場合は0
    	if(searchForm.getMinPrice() != null){
    		minPrice = searchForm.getMinPrice();
    	}else{
    		minPrice = 0;
    	}
    	//検索フォームのタグ配列が空じゃなかったら
    	if(searchForm.getTags() != null){
    		tags = searchForm.getTags();
    	}
    	//ソート基準
    	if(searchForm.getSort() != null){
    		sort = searchForm.getSort();
    	}else{
    		sort = "id";
    	}
    	//検索用の拡張リポジトリへ返します
    	return itemsRepositoryEX.findByConditions(keyword, conditions, maxPrice, minPrice,
    												tags, sort, sortSc, pageable);

    }

    /**
     * Items情報を追加もしくは更新します
     *
     * @param itemsForm Itemsフォーム
     */
    @Override
    @Transactional
    public void saveItems(ItemsForm itemsForm) {

        Items items;

        // Itemsフォームがnullの場合は例外を投げる
        if (itemsForm == null) {
            throw new RuntimeException();
        }

        // ItemsFormにIDが設定されていなければ新規登録
        if (itemsForm.getId() == null) {
            items = new Items();
        } else {
            // FormにIDが設定されていたらIDをキーにレコードを取得して更新日時を更新する
            items = itemsRepository.findById(itemsForm.getId());
            items.setModified(new Date());
        }
        //フォームからエンティティへ値の設定
        items.setName(itemsForm.getName());
        items.setDescription(itemsForm.getDescription());
        items.setPrice(itemsForm.getPrice());

        // 新規追加の場合はidを発番するために一旦保存する
        if (itemsForm.getId() == null) {
            items = itemsRepository.save(items);
        }
        //フォームのタグがNULLなら新規なので、
        //タグ配列の初期化を実施
        if (itemsForm.getTags() == null) {
            itemsForm.setTags(new String[] {});
        }
        //商品テーブルの商品タグリストがNULLなら
        //商品タグリストの初期化を商品タグテーブルを参照に実施
        if (items.getItemsTagsList() == null){
            items.setItemsTagsList(new ArrayList<ItemsTags>());
        }

        // 未選択のタグ情報は表示が不要の為、論理削除しておく
        for (ItemsTags itemsTags : items.getItemsTagsList()) {
            boolean isSelectedFlag = false;
            for (String tags : itemsForm.getTags()) {
            	//もしタグのIDがタグ配列の中身と同じなら
                if (itemsTags.getTagsId() == Integer.valueOf(tags)) {
                	//非表示
                    isSelectedFlag = true;
                }
            }
            if (!isSelectedFlag) {
                itemsTags.setDeleteFlg(true);
            }
        }

        // 選択されたタグ情報を登録する
        for (String tags : itemsForm.getTags()) {
            boolean isSelectedFlag = false;
            for (ItemsTags itemsTags : items.getItemsTagsList()) {
                if (Integer.parseInt(tags) == itemsTags.getTagsId()) {
                    isSelectedFlag = true;
                }
            }
            if (!isSelectedFlag) {
                ItemsTags itemsTags = new ItemsTags();
                itemsTags.setItemsId(items.getId());
                itemsTags.setTagsId(Integer.parseInt(tags));
                items.getItemsTagsList().add(itemsTags);
            }
        }

        items = itemsRepository.save(items);

        /**
         * 画像情報の更新
         * 画像削除のチェックボックスがONでなければ画像データ登録or更新
         * 画像削除のチェックボックスがONになっていたら画像データ削除
         */
        if(!itemsForm.isDeleteImage()){
        	//フォームに画像データが設定されていたら画像更新を実施
        	if(itemsForm.getImageDataBase64() != null &&
        			itemsForm.getImageDataBase64().length() > 0){
        		//フォームに画像データがあったら旧画像を論理削除
        		if(itemsForm.getImageId() != null){
        			//imagesにitemsFormのIDから検索して代入
        			Images images = imagesRepository.findById(itemsForm.getImageId());
        			images.setItemsId(items.getId());
        			images.setDeleteFlg(true);
        			images = imagesRepository.save(images);
        		}
        		//画像データの登録
        		Images images = new Images();
        		images.setItemsId(items.getId());
        		images.setData(convertBase64ToImageData(itemsForm.getImageDataBase64()));
        		List<Images> imageList = new ArrayList<Images>();
        		imageList.add(images);
                items.setImageList(imageList);
        	}
        }else{
        	//画像データの論理削除
        	Images images = imagesRepository.findById(itemsForm.getImageId());
        	images.setDeleteFlg(true);
        	images = imagesRepository.save(images);
        }

        // 保存(追加、更新兼用)
        itemsRepository.save(items);
    }

    /**
     * Items情報を削除します。
     * 論理削除フラグでtrueを返すことで、非表示にします。
     * 物理削除は今回は行っていません。
     * @param itemsForm Itemsフォーム
     *
     */
    @Override
    @Transactional
    public void removeItems(ItemsForm itemsForm){
    	//Itemsの入れ物を用意
    	Items items;
    	//処理前にItemsFormがnullの場合例外を投げておく
    	if(itemsForm == null){
    		throw new RuntimeException();
    	}
    	//削除機能を実行していきます。
    	//非表示にする前にまずは更新日時の更新を行います。
    	//レポジトリからID検索を引っ張りIDを参考に検索を行う。
    	//データをitems内のそれぞれに代入
    	items = itemsRepository.findById(itemsForm.getId());
    	items.setModified(new Date());
    	//論理削除フラグにtrueを返し更新の結果を保存します
    	items.setDeleteFlg(true);
    	itemsRepository.save(items);
    }

    @Override
    public Images getImages(Integer imageId) {

        return imagesRepository.findById(imageId);
    }

    @Override
    public byte[] convertBase64ToImageData(String base64) {

        // Base64デコーダを使用して、Base64にエンコードされた文字列をバイナリデータに変換する
        return Base64.getDecoder().decode(base64);
    }

    @Override
    public String convertImageDataToBase64(MultipartFile imageData) throws IOException {

        // Base64エンコーダを使用して、バイナリデータをBase64文字列にエンコードする
        return Base64.getEncoder().encodeToString(imageData.getBytes());
    }

    @Override
    public String convertImageDataToDataUriScheme
    	(MultipartFile imageData) throws IOException {
        // HTML内に画像データを埋め込むため、
    	//画像データをBase64エンコードしDataURIスキーム表現に変換する
        // DataURIスキーム:例) <img src="data:image/jpeg;base64,/Akwk+jasdH..." >
        StringBuilder dataUri = new StringBuilder();
        dataUri.append("data:").append(imageData.getContentType()).append(";base64,")
            .append(this.convertImageDataToBase64(imageData));

        return dataUri.toString();
    }

    @Override
    public String getContentType(byte[] data) {

        // MIMEタイプを判別するクラス
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");

        // MimeUtilにバイナリデータを渡してメディアタイプを判別してもらう
        Collection<?> mimeTypes = MimeUtil.getMimeTypes(data);
        if (!mimeTypes.isEmpty()) {
            Iterator<?> iterator = mimeTypes.iterator();
            MimeType mimeType = (MimeType) iterator.next();

            // 取得できた最初のContentTypeを返却
            return mimeType.getMediaType() + "/" + mimeType.getSubType();
        }

        // メディアタイプを取得出来なかったので不明なContentTypeを返却する
        return "application/octet-stream";
    }

    @Override
    public List<Tags> getTagsList(){
    	return tagsRepository.findAll();

    }

    @Override
    public Page<Items> getAllItems(Pageable pageable){
    	return itemsPageRepository.findAll(pageable);
    }

}
