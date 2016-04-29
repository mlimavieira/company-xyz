package com.mlimavieira.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mlimavieira.model.SaleOrder;

@Transactional
public interface SaleOrderRepository extends CrudRepository<SaleOrder, Long> {

	@Query("SELECT s FROM SaleOrder s JOIN FETCH s.orderLines o JOIN FETCH o.product p WHERE s.id = :id")
	SaleOrder getByIdFetched(@Param("id") Long id);

	@Query("SELECT s FROM SaleOrder s JOIN s.orderLines o JOIN o.product p WHERE p.id = :prodId")
	List<SaleOrder> getByProductId(@Param("prodId") Long prodId);

	@Query("SELECT s FROM SaleOrder s JOIN s.customer c WHERE c.id = :custId")
	List<SaleOrder> getByCustomer(@Param("custId") Long custId);
}
