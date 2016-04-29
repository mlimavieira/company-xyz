package com.mlimavieira.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mlimavieira.model.Customer;

@Transactional
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
