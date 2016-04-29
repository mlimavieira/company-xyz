package com.mlimavieira.controller;

import org.apache.commons.lang3.StringUtils;

import com.mlimavieira.dto.CustomerDto;

public class CustomerControllerTest extends BaseCrudTest {

	private static final String CUSTOMER_BASE = "/1/customer";

	@Override
	protected String getBaseUrl() {
		return CUSTOMER_BASE;
	}

	@Override
	protected Object getCreateSuccessDto() {

		final CustomerDto customer = new CustomerDto();

		customer.setName("Test Save");
		customer.setCreditLimit(1000.0);
		customer.setCurrentCreditLimit(10000.0);
		customer.setAddress("Address");

		return customer;
	}

	@Override
	protected Object getUpdateSuccessDto() {
		final CustomerDto customer = new CustomerDto();
		customer.setId(10L);
		customer.setName("Test Save");
		customer.setCreditLimit(1000.0);
		customer.setCurrentCreditLimit(10000.0);
		customer.setAddress("Address");
		return customer;
	}

	@Override
	protected Object getPostObjectFieldSizeError() {
		final CustomerDto customer = new CustomerDto();
		customer.setName(StringUtils.repeat("x", 101));
		customer.setCreditLimit(1000.0);
		customer.setCurrentCreditLimit(10000.0);
		customer.setAddress("Address");
		return customer;
	}

	@Override
	protected Object getPostObjectRequiredField() {
		final CustomerDto customer = new CustomerDto();
		customer.setName("Test Save");
		customer.setCurrentCreditLimit(10000.0);
		customer.setAddress("Address");
		return customer;
	}

	@Override
	protected Long foundById() {
		return 1L;
	}

	@Override
	protected Long notFoundById() {
		return 25L;
	}

}
