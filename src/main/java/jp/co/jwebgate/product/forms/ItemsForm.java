package jp.co.jwebgate.product.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * レコードフォーム
 *フォームオブジェクトを扱うパッケージ。
 * Web上のフォームデータと紐付きます。
 *
 * @author y-wayama
 */
public class ItemsForm {

    /**
     *  ID
     */
    private Integer id;

    /**
     *  商品名
     *  @NotEmpty→必須個所、メッセージは入力されていない場合に出力
     */
    @NotEmpty(message = "名前は入力必須です")
    private String name;

    /**
     * 商品説明
     */
    private String description;

    /**
     * 商品価格
     */
    private int price;

    /**
     *  画像ID
     */
    private Integer imageId;

    /**
     *  画像データ(受信用)
     *  MultipartFile
     *  Springにおけるファイルアップロードの受け取りに使うインターフェイス
     */
    private MultipartFile imageData;

    /** 画像データ(Base64) */
    private String imageDataBase64;

    /** 画像データ(URIScheme) */
    private String imageDataUriScheme;

    /** 画像削除フラグ */
    private boolean deleteImage;

    /**
     * タグリスト
     * タグは複数ある為、全てのタグを入れる入れ物
     */
    private String[] tags;

    /**
     * IDを取得します
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * IDを設定します
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 商品名を取得します
     *
     * @return 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 商品名を設定します
     *
     * @param name 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 商品説明を取得します
     *
     * @return 商品説明
     */
	public String getDescription() {
		return description;

	}


	/**
	 * 商品説明を設定します
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description.replaceAll("<br/>", "\n");
	}

	/**
	 * 商品価格を取得します
	 *
	 * @return 商品価格
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 商品価格を設定します
	 *
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 画像データIDを取得します
	 * @return 画像データID
	 */
	public Integer getImageId() {
		return imageId;
	}

	/**
	 * 画像データIDを設定します
	 * @param imageId 画像データID
	 */
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	/**
	 * 受信した画像データを取得します
	 * @return 画像データ（受信）
	 */
	public MultipartFile getImageData() {
		return imageData;
	}

	/**
	 * 受信した画像データを設定します
	 * @param imageData 画像データ（受信）
	 */
	public void setImageData(MultipartFile imageData) {
		this.imageData = imageData;
	}

	/**
	 * 画像データ(Base64)を取得します
	 * @return
	 */
	public String getImageDataBase64() {
		return imageDataBase64;
	}

	/**
	 * 画像データ(Base64)を設定します
	 * @param imageDataBase64
	 */
	public void setImageDataBase64(String imageDataBase64) {
		this.imageDataBase64 = imageDataBase64;
	}

	/**
	 * 画像データ(URIScheme)を取得します
	 * @return 画像データ(URIScheme)
	 */
	public String getImageDataUriScheme() {
		return imageDataUriScheme;
	}

	/**
	 * 画像データ（URIScheme）を設定します
	 * @param imageDataUriScheme
	 */
	public void setImageDataUriScheme(String imageDataUriScheme) {
		this.imageDataUriScheme = imageDataUriScheme;
	}

	/**
	 * 画像削除フラグの取得
	 * @return
	 */
	public boolean isDeleteImage() {
		return deleteImage;
	}

	/**
	 * 画像削除フラグの設定
	 * @param deleteImage
	 */
	public void setDeleteImage(boolean deleteImage) {
		this.deleteImage = deleteImage;
	}

	/**
	 * タグリストを取得します
	 * @return タグリスト
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * タグリストを設定します
	 * @param tags タグリスト
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
