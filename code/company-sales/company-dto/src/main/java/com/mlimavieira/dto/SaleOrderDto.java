package com.mlimavieira.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaleOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private Long customerId;

	@Size(max = 100)
	private String customerName;

	@Size(min = 1)
	private List<OrderLineDto> orderLines;

	private Double totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<OrderLineDto> getOrderLines() {
		if (orderLines == null) {
			orderLines = new ArrayList<OrderLineDto>();
		}
		return orderLines;
	}

	public void setOrderLines(List<OrderLineDto> orderLines) {
		this.orderLines = orderLines;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
