package jp.co.jwebgate.product.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * ItemsTags 中間テーブル
 * タグIDを使ってタグを読み込み
 * 商品IDへ受け渡す
 * 商品のID 1 : 多 商品ID
 * タグID 多 : 1 タグのID
 *
 * @author y-wayama
 *
 */
@Entity
@Table(name = "items_tags")
@Where(clause = "delete_flg = '0'")
public class ItemsTags {

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
	 * 商品ID
	 */
	private Integer itemsId;
	/**
	 * タグID
	 */
	private Integer tagsId;

    /**
     * 商品
     * 商品Entity
     * @ManyToOne
     * →多対１
     */
    @ManyToOne
    @JoinColumn(name = "itemsId", referencedColumnName = "id", updatable = false, insertable = false)
    private Items items;

    /**
     * タグ
     *  タグEntity
     *  @ManyToOne
     * →多対１
     */
    @ManyToOne
    @JoinColumn(name = "tagsId", referencedColumnName = "id", updatable = false, insertable = false)
    private Tags tags;


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
	 * 商品IDを取得します
	 * @return 商品ID
	 */
	public Integer getItemsId() {
		return itemsId;
	}
	/**
	 * 商品IDを設定します
	 * @param itemsId 商品ID
	 */
	public void setItemsId(Integer itemsId) {
		this.itemsId = itemsId;
	}
	/**
	 * タグIDを取得します
	 * @return タグID
	 */
	public Integer getTagsId() {
		return tagsId;
	}
	/**
	 * タグIDを設定します
	 * @param tagsId タグID
	 */
	public void setTagsId(Integer tagsId) {
		this.tagsId = tagsId;
	}
	/**
	 * 論理削除フラグを取得します
	 * @return 論理削除フラグ
	 */
	public boolean isDeleteFlg() {
		return deleteFlg;
	}
	/**
	 * 論理削除フラグを設定します
	 * @param deleteFlg 論理削除フラグ
	 */
	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	/**
	 * 作成日時を取得します
	 * @return 作成日時
	 */
	public Date getCreated(){
		return created;
	}
	/**
	 * 作成日時を設定します
	 * @param created 作成日時
	 */
	public void setCreated(Date created){
		this.created = created;
	}
	/**
	 * 更新日時を取得します
	 * @return 更新日時
	 */
	public Date getModified(){
		return modified;
	}
	/**
	 * 更新日時を設定します
	 * @param modified 更新日時
	 */
	public void setModified(Date modified){
		this.modified = modified;
	}
	/**
	 * タグを取得します
	 * @return タグ
	 */
	public Tags getTags(){
		return tags;
	}
	/**
	 * タグを設定します
	 * @param tags タグ
	 */
	public void setTags(Tags tags){
		this.tags = tags;
	}
	/**
	 * 商品を取得します
	 * @return 商品
	 */
	public Items getItems(){
		return items;
	}
	/**
	 * 商品を設定します
	 * @param items 商品
	 */
	public void setItems(Items items){
		this.items = items;
	}
}
