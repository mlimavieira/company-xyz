package com.mlimavieira.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.mlimavieira.dto.ProductDto;

public class ProductControllerTest extends BaseCrudTest {

	private static final String PRODUCT_BASE = "/1/product";

	@Override
	protected String getBaseUrl() {
		return PRODUCT_BASE;
	}

	@Override
	protected Object getCreateSuccessDto() {
		final ProductDto product = new ProductDto();

		product.setDescription("Test Insert");
		product.setQuantity(100);
		product.setPrice(100.0);
		return product;
	}

	@Override
	protected Object getUpdateSuccessDto() {
		final ProductDto product = new ProductDto();
		product.setId(13L);
		product.setDescription("Test Update");
		product.setQuantity(100);
		product.setPrice(100.0);
		return product;
	}

	@Override
	protected Object getPostObjectFieldSizeError() {
		final ProductDto product = new ProductDto();
		product.setDescription(StringUtils.repeat("x", 256));
		product.setQuantity(100);
		product.setPrice(100.0);
		return product;
	}

	@Override
	protected Object getPostObjectRequiredField() {
		final ProductDto product = new ProductDto();
		product.setDescription("Test Save");
		product.setPrice(100.0);
		return product;
	}

	@Override
	protected Long foundById() {
		return 10L;
	}

	@Override
	protected Long notFoundById() {
		return 125L;
	}

	@Test
	public void errorFieldMinSizeTest() {
		final ProductDto product = new ProductDto();
		product.setDescription(StringUtils.repeat("x", 3));
		product.setQuantity(100);
		product.setPrice(100.0);

		given().contentType(ContentType.JSON).body(product).when().post(PRODUCT_BASE).then()
				.statusCode(HttpServletResponse.SC_BAD_REQUEST).contentType("application/json");
	}
}
