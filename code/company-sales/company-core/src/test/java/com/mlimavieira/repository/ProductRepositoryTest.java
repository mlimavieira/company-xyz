package com.mlimavieira.repository;

import java.util.List;

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
import com.mlimavieira.model.Product;

@Profile("test")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringApplicationConfiguration(classes = CompanyCoreApplicationTests.class)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void insertTest() {

		final Product product = new Product();

		product.setDescription("Product 1");
		product.setPrice(10.0);
		product.setQuantity(100);

		final Product savedProduct = productRepository.save(product);

		Assert.assertNotNull(savedProduct);
		Assert.assertNotNull(savedProduct.getId());
	}

	@Test
	public void listTest() {
		final List<Product> findAll = (List<Product>) productRepository.findAll();

		Assert.assertFalse(findAll.isEmpty());
	}

	@Test
	public void deleteByIdTest() {

		productRepository.delete(2L);

		final Product deletedProduct = productRepository.findOne(2L);

		Assert.assertNull(deletedProduct);
	}

	@Test
	public void updateTest() {

		final Product product = productRepository.findOne(1L);

		final String auxName = product.getDescription();

		product.setDescription("New Product Name");
		productRepository.save(product);

		final Product updatedProduct = productRepository.findOne(1L);

		Assert.assertFalse(auxName.equals(updatedProduct.getDescription()));
	}

}
