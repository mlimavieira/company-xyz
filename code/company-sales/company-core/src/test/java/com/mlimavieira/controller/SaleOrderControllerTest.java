package com.mlimavieira.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.http.ContentType;
import com.mlimavieira.dto.OrderLineDto;
import com.mlimavieira.dto.SaleOrderDto;
import com.mlimavieira.model.Customer;
import com.mlimavieira.model.Product;
import com.mlimavieira.repository.CustomerRepository;
import com.mlimavieira.repository.ProductRepository;

public class SaleOrderControllerTest extends BaseCrudTest {

	private static final String PRODUCT_BASE = "/1/sale-order";

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	protected String getBaseUrl() {
		return PRODUCT_BASE;
	}

	@Override
	protected Object getCreateSuccessDto() {
		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomerId(10L);
		saleOrderDto.setTotalPrice(10.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(10.0);
		orderLineDto.setProductId(10L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);
		return saleOrderDto;
	}

	@Override
	protected Object getUpdateSuccessDto() {
		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setId(5L);
		saleOrderDto.setCustomerId(10L);
		saleOrderDto.setTotalPrice(10.0);

		OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(10.0);
		orderLineDto.setProductId(10L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(20.0);
		orderLineDto.setProductId(11L);
		orderLineDto.setQuantity(1);

		return saleOrderDto;
	}

	@Override
	protected Object getPostObjectFieldSizeError() {
		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setId(5L);
		saleOrderDto.setCustomerId(10L);
		saleOrderDto.setTotalPrice(10.0);
		saleOrderDto.setCustomerName(StringUtils.repeat("x", 105));

		OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(10.0);
		orderLineDto.setProductId(10L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(20.0);
		orderLineDto.setProductId(11L);
		orderLineDto.setQuantity(1);

		return saleOrderDto;
	}

	@Override
	protected Object getPostObjectRequiredField() {

		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setTotalPrice(10.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(10.0);
		orderLineDto.setProductId(13L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		return saleOrderDto;
	}

	@Override
	protected Long foundById() {
		return 5L;
	}

	@Override
	protected Long notFoundById() {
		return 123L;
	}

	// CUSTOM Tests

	@Test
	public void productNotAvailable() {
		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomerId(10L);
		saleOrderDto.setTotalPrice(10.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(10.0);
		orderLineDto.setProductId(13L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		given().contentType(ContentType.JSON).body(saleOrderDto).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_BAD_REQUEST).body("body.message", containsString("not available"))
				.contentType("application/json");
	}

	@Test
	public void customerWithOutCredit() {

		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomerId(8L);
		saleOrderDto.setTotalPrice(300.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(30.0);
		orderLineDto.setProductId(12L);
		orderLineDto.setQuantity(10);

		saleOrderDto.getOrderLines().add(orderLineDto);

		given().contentType(ContentType.JSON).body(saleOrderDto).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_BAD_REQUEST)
				.body("body.message", equalTo("The customer does not have available credit"))
				.contentType("application/json");
	}

	@Test
	public void customerCheckCreditReduce() {

		final Customer customer = customerRepository.findOne(8L);
		final Double originalCredit = customer.getCurrentCreditLimit();

		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomerId(8L);
		saleOrderDto.setTotalPrice(30.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(30.0);
		orderLineDto.setProductId(12L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		given().contentType(ContentType.JSON).body(saleOrderDto).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_CREATED).contentType("application/json");

		final Customer customerAfterTransaction = customerRepository.findOne(8L);
		Assert.assertNotEquals(originalCredit, customerAfterTransaction.getCurrentCreditLimit());
	}

	@Test
	public void productCheckStockReduce() {

		final Product product = productRepository.findOne(12L);
		final Integer originalQuantity = product.getQuantity();
		final SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomerId(8L);
		saleOrderDto.setTotalPrice(30.0);

		final OrderLineDto orderLineDto = new OrderLineDto();
		orderLineDto.setPrice(30.0);
		orderLineDto.setProductId(12L);
		orderLineDto.setQuantity(1);

		saleOrderDto.getOrderLines().add(orderLineDto);

		given().contentType(ContentType.JSON).body(saleOrderDto).when().post(getBaseUrl()).then()
				.statusCode(HttpServletResponse.SC_CREATED).contentType("application/json");

		final Product productAfterTransaction = productRepository.findOne(12L);

		Assert.assertNotEquals(originalQuantity, productAfterTransaction.getQuantity());
	}
}
