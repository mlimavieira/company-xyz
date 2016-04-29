package com.mlimavieira.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderLineDto {

	private Long id;

	@NotNull
	private Long productId;

	@NotNull
	@Min(value = 1)
	private Integer quantity;

	@NotNull
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}