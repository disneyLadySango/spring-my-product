package jp.co.jwebgate.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.jwebgate.product.entities.Items;

@Repository
public interface ItemsPageRepository extends CrudRepository<Items, Integer>{
	public Page<Items> findAll(Pageable pageable);
}
