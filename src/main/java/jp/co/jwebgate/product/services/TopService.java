package jp.co.jwebgate.product.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import jp.co.jwebgate.product.entities.Images;
import jp.co.jwebgate.product.entities.Items;
import jp.co.jwebgate.product.entities.Tags;
import jp.co.jwebgate.product.forms.ItemsForm;
import jp.co.jwebgate.product.forms.SearchForm;

/**
 * トップサービスインターフェース
 * コントローラから呼び出されるビジネスロジックを担うサービスクラスを扱う
 *
 * @author k-tsuda
 */
public interface TopService {

    /**
     * IDからItems情報を取得します。
     *
     * @param id ID
     * @return Items情報
     */
    Items getItems(Integer id);

    /**
     * Items情報一覧を検索フォームを使い取得します
     *
     * Page→List
     *
     * @param serachForm 検索フォーム
     * @return Items情報一覧
     */
    Page<Items> getItemsList(SearchForm searchForm, Pageable pageable);


    /**
     * Items情報を保存します。
     *
     * @param itemsForm Itemsフォーム
     */
    void saveItems(ItemsForm itemsForm);

    /**
     * Items情報を削除します。
     * 論理削除
     * @param itemsForm Itemsフォーム
     */

    void removeItems(ItemsForm itemsForm);

    /**
     * 画像情報を取得します。
     *
     * @param id ID
     * @return 画像情報
     */
    Images getImages(Integer id);

    /**
     * Base64を画像データにデコードします。
     *
     * @param base64 Base64
     * @return 画像データ
     */
    byte[] convertBase64ToImageData(String base64);

    /**
     * 画像データをBase64エンコードします。
     *
     * @param imageData 画像データ
     * @return Base64
     * @throws IOException 入出力例外
     */
    String convertImageDataToBase64(MultipartFile imageData) throws IOException;

    /**
     * 画像データをDataULIScheme形式に変換します。
     *
     * @param imageData 画像データ
     * @return DataURIScheme
     * @throws IOException 入出力例外
     */
    String convertImageDataToDataUriScheme(MultipartFile imageData) throws IOException;

    /**
     * バイナリデータからContentTypeを取得します
     *
     * @param data データ
     * @return ContentType
     */
    public String getContentType(byte[] data);

    /**
     * タグリストの取得を実施
     * @return タグリスト
     */
    List<Tags> getTagsList();

    Page<Items> getAllItems(Pageable pageable);

}
