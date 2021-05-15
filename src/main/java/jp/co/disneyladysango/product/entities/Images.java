package jp.co.disneyladysango.product.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * イメージエンティティ
 *
 * @author y-wayama
 *
 */
@Entity
@Table(name = "images")
@Where(clause = "delete_flg = '0'")
public class Images {

	/**
	 * ID(画像データ用)
	 * @Id→IDとして設定
	 * @GeneratedValue→主キーの設定
	 */
	@Id
    @GeneratedValue
	private Integer id;

	/**
	 * 画像データ
	 */
	private byte[] data;

	private Integer itemsId;

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
	 * 新規の場合作成日時情報の作成
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
	 * 画像データを取得します
	 * @return 画像データ
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * 画像データを設定します
	 * @param data 画像データ
	 */
	public void setData(byte[] data) {
		this.data = data;
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
	 * @param delete_flg 論理削除フラグ
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


}
