package jp.co.disneyladysango.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.disneyladysango.product.entities.Tags;

/**
 * タグリポジトリインターフェース
 * @author y-wayama
 *
 */
@Repository
public interface TagsRepository  extends JpaRepository<Tags, Integer>{
	/**
     * IDからTags情報を取得します
     *
     * @param id ID
     * @return Items情報
     */
    @Query("select t from Tags t where t.id=:id")
    public Tags findById(@Param("id") Integer id);

    /**
     * Tagsを全件取得します
     *
     * @return Items情報
     */
    @Query("select t from Tags t order by id asc")
    public List<Tags> findAll();

}
