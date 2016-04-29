package com.mlimavieira.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mlimavieira.model.Product;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

}
