package com.mlimavieira.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Order Line Entity
 * 
 * This class represent each line of the Sale Order.
 * 
 * @author marciovieira
 */
@Entity
@Table(name = "order_line")
public class OrderLine extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represent the quantity of products
	 */
	@NotNull
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	/**
	 * Relation between Sale Order and Order Line
	 */
	@NotNull
	@JoinColumn(name = "sale_order_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private SaleOrder saleOrder;

	/**
	 * Represent the relation between Order Line and Product
	 */
	@NotNull
	@JoinColumn(name = "product_id")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Product product;

	/**
	 * The product price is used to keep the historical price of the product.
	 * Even though product price change it was easier to know the price in that
	 * moment.
	 */
	@NotNull
	@Column(name = "product_price", nullable = false, precision = 15, scale = 2)
	private Double productPrice;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OrderLine other = (OrderLine) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderLine [quantity=");
		builder.append(quantity);
		builder.append(", saleOrder=");
		builder.append(saleOrder);
		builder.append(", product=");
		builder.append(product);
		builder.append(", productPrice=");
		builder.append(productPrice);
		builder.append("]");
		return builder.toString();
	}

}
