package com.mlimavieira.repository;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mlimavieira.CompanyCoreApplicationTests;
import com.mlimavieira.model.Customer;

@Profile("test")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringApplicationConfiguration(classes = CompanyCoreApplicationTests.class)
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void insertTest() {

		final Customer customer = new Customer();

		customer.setName("Marcio");
		customer.setCreditLimit(1000.0);
		customer.setCurrentCreditLimit(1000.0);
		customer.setAddress("Address");

		final Customer savedCustomer = customerRepository.save(customer);

		Assert.assertNotNull(savedCustomer);
		Assert.assertNotNull(savedCustomer.getId());
	}

	@Test
	public void listTest() {
		final List<Customer> findAll = (List<Customer>) customerRepository.findAll();

		Assert.assertFalse(findAll.isEmpty());
	}

	@Test
	public void deleteByIdTest() {

		customerRepository.delete(2L);

		final Customer deletedCustomer = customerRepository.findOne(2L);

		Assert.assertNull(deletedCustomer);
	}

	@Test
	public void updateTest() {

		final Customer customer = customerRepository.findOne(1L);

		final String auxName = customer.getName();

		customer.setName("New Customer Name");
		customerRepository.save(customer);

		final Customer updatedCustomer = customerRepository.findOne(1L);

		Assert.assertFalse(auxName.equals(updatedCustomer.getName()));
	}

	@Test(expected = ConstraintViolationException.class)
	public void customerLimitValidation() {

		final Customer customer = new Customer();

		customer.setName("Marcio");
		customer.setCreditLimit(1000.0);
		customer.setCurrentCreditLimit(1001.0);
		customer.setAddress("Address");

		final Customer savedCustomer = customerRepository.save(customer);

		Assert.assertTrue(savedCustomer.getCurrentCreditLimit() > customer.getCreditLimit());

	}

}
