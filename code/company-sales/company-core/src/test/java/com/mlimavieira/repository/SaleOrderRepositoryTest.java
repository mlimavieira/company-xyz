package com.mlimavieira.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
import com.mlimavieira.model.OrderLine;
import com.mlimavieira.model.Product;
import com.mlimavieira.model.SaleOrder;

@Profile("test")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringApplicationConfiguration(classes = CompanyCoreApplicationTests.class)
public class SaleOrderRepositoryTest {

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;

	private List<Product> products;
	private List<Customer> customers;

	@Before
	public void before() {

		products = (List<Product>) productRepository.findAll();
		customers = (List<Customer>) customerRepository.findAll();

	}

	@Test
	public void insertTest() {

		final Product product = products.get(0);

		final SaleOrder saleOrder = new SaleOrder();
		saleOrder.setCustomer(customers.get(0));
		saleOrder.setTotalPrice(product.getPrice());

		final OrderLine orderLine = new OrderLine();

		orderLine.setProduct(product);
		orderLine.setQuantity(1);
		orderLine.setSaleOrder(saleOrder);
		orderLine.setProductPrice(product.getPrice());

		saleOrder.getOrderLines().add(orderLine);

		final SaleOrder savedOrder = saleOrderRepository.save(saleOrder);

		Assert.assertNotNull(savedOrder);
		Assert.assertNotNull(savedOrder.getId());
	}

	@Test
	public void updateTest() {
		final SaleOrder saleOrder = saleOrderRepository.getByIdFetched(3L);

		final OrderLine orderLine = new OrderLine();

		final Product product = products.get(0);

		orderLine.setProduct(product);
		orderLine.setQuantity(1);
		orderLine.setSaleOrder(saleOrder);
		orderLine.setProductPrice(product.getPrice());
		saleOrder.getOrderLines().add(orderLine);

		final SaleOrder savedSaleOrder = saleOrderRepository.save(saleOrder);

		Assert.assertTrue(savedSaleOrder.getOrderLines().size() == 2);
	}

	@Test
	public void deleteTest() {

		saleOrderRepository.delete(3L);

		final SaleOrder findOne = saleOrderRepository.findOne(3L);

		Assert.assertNull(findOne);

	}
}
