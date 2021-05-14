package jp.co.jwebgate.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.jwebgate.product.entities.UsersLogin;



/**
 * アカウント用のリポジトリ
 * @author y-wayama
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<UsersLogin, Long> {

	public UsersLogin findByUsername(String username);

}
