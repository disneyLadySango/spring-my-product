package jp.co.jwebgate.product.forms;

import org.hibernate.validator.constraints.NotEmpty;

public class AccountForm {

	/** ID */
	private Integer id;

	/** ユーザー名 */
	@NotEmpty(message = "ユーザー名は入力必須です")
	private String name;

	/** パスワード */
	@NotEmpty(message = "パスワードは入力必須です")
	private String password;

	/** 権限 */
	private String role;

	/** 論理削除フラグ */
	private boolean deleteFlg;

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
	 * @return ユーザー名
	 */
	public String getName(){
		return name;
	}

	/**
	 * ユーザー名を取得します
	 * @param userName ユーザー名
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * パスワードを設定します
	 * @return パスワード
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * パスワードを取得します
	 * @param password パスワード
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * 論理削除フラグを設定します
	 * @return 論理削除フラグ
	 */
	public boolean isDeleteFlg(){
		return deleteFlg;
	}

	/**
	 * 論理削除フラグを取得します
	 * @param deleteFlg 論理削除フラグ
	 */
	public void setDeleteFlg(boolean deleteFlg){
		this.deleteFlg = deleteFlg;
	}
	/**
	 * 権限を取得します
	 * @return 権限
	 */
	public String getRole() {
		return role;
	}
	/**
	 * 権限を設定します
	 * @param role 権限
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
