package jp.co.jwebgate.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.jwebgate.product.entities.Items;

/**
 * レコードリポジトリインターフェース
 * @Repository→
 * Spring MVCでデータ層のクラス（DAO等のDBアクセスを行うクラス）に付与する。
 *
 * extendsは、
 * class 宣言 や class 式内で、他のクラスの子としてクラスを作成するために使用します。
 *
 * JpaRepositoryは、インターフェイス
 * あらかじめ検索メソッドを定義しておくことで、
 * メソッドを呼び出すだけでスマートにデータ検索が行えるようになる
 * <>内には、エンティティのクラス名と、IDフィールドのタイプが指定されます。
 * 「基本型の場合は、ラッパークラスを指定する」
 * @author k-tsuda
 */
@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {

    /**
     * IDからItems情報を取得します
     *
     * @param id ID
     * @return Items情報
     */
    @Query("select i from Items i where i.id=:id")
    public Items findById(@Param("id") Integer id);

    /**
     * レコードを全件取得します
     *
     * @return Items情報
     */
    @Query("select i from Items i order by id desc")
    public List<Items> findAll();
}
