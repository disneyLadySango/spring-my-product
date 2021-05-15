package jp.co.disneyladysango.product.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * レコードエンティティ
 *
 * @Entity→エンティティ化
 * DBの入出力の際にデータの器の役割をするエンティティを扱うパッケージ
 * @Table→使用するテーブル"items"
 * @Where→最初から検索に含めたい条件をデフォルトとして持たせることができる
 *
 * @author y-wayama
 */
@Entity
@Table(name = "items")
@Where(clause = "delete_flg = '0'")
public class Items implements Serializable {



    /**
     * ID
     * @Id→IDとして設定
     * @GeneratedValue→主キーの設定
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     *  商品名
     */
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
     * 画像リスト
     * OneToMany→１対多の関係
     * ImagesクラスのitemsIdと関連づいたリスト
     * LAZYタイプで全件検索は行っていない
     * Where→リスト内の削除フラグを０（false)
     * OrderByで降順
     */
    @OneToMany(targetEntity = Images.class, mappedBy = "itemsId", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @Where(clause = "delete_flg = '0'")
    @OrderBy("modified desc")
    private List<Images> imageList;

    /**
     *  商品タグリスト
     *  OneToMany→1対多の関係
     *  ItemsTagsクラスのitemsIdと関連付け
     *  EAGERタイプなので、全件検索
     *  OrderByで降順
     */
    @OneToMany(targetEntity = ItemsTags.class, mappedBy = "itemsId", fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @Where(clause = "delete_flg = '0'")
    @OrderBy("modified desc")
    private List<ItemsTags> itemsTagsList;

    /**
     *  論理削除フラグ
     */
    private boolean deleteFlg;

    /**
     *  作成日時
     */
    private Date created;

    /**
     *  更新日時
     */
    private Date modified;

    /**
     * 更新前処理
     * @PrePersist
     * データベースにINSERT文を発行する前に
     * 呼び出されるコールバックメソッドであることを示すアノテーション
     * @PreUpdate
     * データベースにUPDATE文を発行したあとに
     * 呼び出されるコールバックメソッドであることを示すアノテーション
     */
    @PrePersist
    @PreUpdate
    public void preUpdate() {

        modified = new Date();
        if (created == null) {
            created = new Date(modified.getTime());
        }
    }

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
    public void setName(String memo) {
        this.name = memo;
    }

    /**
     * 商品説明を取得します
     *
     * @return	商品説明
     */
	public String getDescription() {
		return description.replaceAll("\n", "<br/>");

	}

	/**
	 * 商品説明を設定します
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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
     * 論理削除フラグを取得します
     *
     * @return 論理削除フラグ
     */
    public boolean isDeleteFlg() {
        return deleteFlg;
    }

    /**
     * 論理削除フラグを設定します
     *
     * @param deleteFlg 論理削除フラグ
     */
    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * 作成日時を取得します
     *
     * @return 作成日時
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 作成日時を設定します
     *
     * @param created 作成日時
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 更新日時を取得します
     *
     * @return 更新日時
     */
    public Date getModified() {
        return modified;
    }

    /**
     * 更新日時を設定します
     *
     * @param modified 更新日時
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * 画像リストを取得します。
     *
     * @return 画像リスト
     */
    public List<Images> getImageList() {
        return imageList;
    }

    /**
     * 画像リストを設定します。
     *
     * @param imageList 画像リスト
     */
    public void setImageList(List<Images> imageList) {
        this.imageList = imageList;
    }
    /**
     * アイテムタグリストを取得します
     * @return アイテムタグリスト
     */
    public List<ItemsTags> getItemsTagsList(){
    	return itemsTagsList;
    }
    /**
     * アイテムタグリストを設定します
     * @param itemsTagsItemsList アイテムタグリスト
     */
    public void setItemsTagsList(List<ItemsTags> itemsTagsList){
    	this.itemsTagsList = itemsTagsList;
    }

}
