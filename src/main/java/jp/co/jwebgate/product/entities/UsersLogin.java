package jp.co.jwebgate.product.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users_login")
@Where(clause = "delete_flg = '0'")
public class UsersLogin implements UserDetails{

	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	/** ユーザー名 */
	@Column(nullable=false ,unique=true , name = "username")
	private String username;

	/** パスワード */
	@Column(nullable=false ,length=60 , name = "password")
	private String password;

	/** ロール */
	@Column(nullable=false ,length=60 , name = "role")
	private String role;

	/** 論理削除フラグ */
	private boolean deleteFlg;

	/** 作成日時 */
	private Date created;

	/** 更新日時 */
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
	public Integer getId(){
		return id;
	}

	/**
	 * IDを設定します
	 * @param id ID
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * ユーザー名を設定します
	 * @param userName ユーザー名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * パスワードを設定します
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 権限を取得します
	 * @return 権限
	 */
	public String getRole(){
		return role;
	}

	/**
	 * 権限を設定します
	 * @param role 権限
	 */
	public void setRole(String role){
		this.role = role;
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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_" + this.role));
		return authorityList;
	}

	@Override
	public String getPassword() {
		// どのパスワードを使うか
		return this.password;
	}

	@Override
	public String getUsername() {
		// どのユーザー名を使うのか
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}


}
