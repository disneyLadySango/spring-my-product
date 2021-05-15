package jp.co.disneyladysango.product.entities;

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
 * 商品タグテーブル
 * INSERTで元データは作成済み
 * 後々管理画面からタグの追加ができるように変更予定はあり
 * @author y-wayama
 *
 */
@Entity
@Table(name = "tags")
@Where(clause = "delete_flg = '0'")
public class Tags {
	/**
	 * ID
	 *
	 * @Id→IDとして設定
     * @GeneratedValue→主キーの設定
	 */
	@Id
    @GeneratedValue
	private Integer id;

	/**
	 * タグ名
	 */
	private String name;

	/**
	 * アイテムタグリスト
	 * OneToMany→1対多の関係性
	 * ItemsTagsクラスのtagsIdと関連付け
	 * 検索タイプはLAZY
	 */
    @OneToMany(targetEntity = ItemsTags.class, mappedBy = "tagsId", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @Where(clause = "delete_flg = '0'")
    @OrderBy("modified desc")
    private List<ItemsTags> itemsTagsList;

	/**
	 * 論理削除フラグ
	 */
	private boolean deleteFlg;

	/**
	 * 作成日時
	 */
	private Date created;

	/**
	 * 更新日時
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
     * @return ID
     */
	public Integer getId() {
		return id;
	}

	/**
	 * IDを設定します
	 * @param id ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * タグ名を設定します
	 * @return タグ名
	 */
	public String getName() {
		return name;
	}
	/**
	 * タグ名を取得します
	 * @param name タグ名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 論理削除フラグを取得します
	 * @return 論理削除フラグ
	 */
	public boolean isDeleteFlg() {
		return deleteFlg;
	}

	/**
	 * 論理削除フラグ設定します
	 * @param deleteFlg 論理削除フラグ
	 */
	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	/**
	 * 作成日時を取得します
	 * @return 作成日時
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * 作成日時を設定します
	 * @param created 作成日時
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * 更新日時を取得します
	 * @return 更新日時
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * 更新日時を設定します
	 * @param modified 更新日時
	 */
	public void setModified(Date modified) {
		this.modified = modified;
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
