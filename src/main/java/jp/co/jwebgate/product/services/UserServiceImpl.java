package jp.co.jwebgate.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.co.jwebgate.product.entities.UsersLogin;
import jp.co.jwebgate.product.repositories.AccountRepository;

@Service
public class UserServiceImpl implements UserDetailsService{

	 @Autowired
	 AccountRepository accountRepository;

	//loadUserByUsernameメソッドの引数→ログイン画面で入力されたユーザーIDが渡される
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//ユーザー名が空ならエラー
		if(StringUtils.isEmpty(username)){
			throw new UsernameNotFoundException("");
		}
		//ユーザーの中に検索ユーザー名での検索結果を格納
		UsersLogin usersLogin = accountRepository.findByUsername(username);
		//空だった場合存在していないので、エラーを返す
		if(usersLogin == null){
			throw new UsernameNotFoundException("");
		}
		return usersLogin;
   }



}