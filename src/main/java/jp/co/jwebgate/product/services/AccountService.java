package jp.co.jwebgate.product.services;

import jp.co.jwebgate.product.forms.AccountForm;

/**
 * アカウント用サービス
 * @author y-wayama
 *
 */

public interface AccountService {


	/**
	 * アカウントフォーム
	 * ユーザー情報を保存します。
	 * @param accountForm
	 */
	void saveAccount(AccountForm accountForm);

}
