package jp.co.disneyladysango.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.disneyladysango.product.entities.Images;

/**
 * 画像リポジトリインターフェース
 *
 * @author y-wayama
 *
 */
public interface ImagesRepository extends JpaRepository<Images, Integer>{

	/**
	 * IDから画像情報を取得します
	 *
	 * @param id
	 * @return
	 */
	@Query("SELECT im FROM Images im WHERE im.id=:id")
	public Images findById(@Param("id") Integer id);
}
