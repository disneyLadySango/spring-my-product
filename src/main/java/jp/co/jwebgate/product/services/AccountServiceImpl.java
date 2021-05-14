package jp.co.jwebgate.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.jwebgate.product.entities.UsersLogin;
import jp.co.jwebgate.product.forms.AccountForm;
import jp.co.jwebgate.product.repositories.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	/**
	 * アカウント用のリポジトリ
	 */
	@Autowired
	AccountRepository accountRepository;


	/**
	 * 	新規作成したアカウント情報を
	 *  パスワードを暗号化したのちDBへ保存する
	 * @param usersLogin
	 * @param rawPassword
	 * @return
	 */
	@Override
	@Transactional
	public void saveAccount(AccountForm accountForm){


		// アカウントフォームがnullの場合は例外を投げる
        if (accountForm == null) {
            throw new RuntimeException();
        }

        UsersLogin usersLogin = new UsersLogin();

        usersLogin.setUsername(accountForm.getName());
		usersLogin.setPassword(accountForm.getPassword());
		usersLogin.setRole("USER");
		//保存・更新を実施
		accountRepository.save(usersLogin);
	}

}

