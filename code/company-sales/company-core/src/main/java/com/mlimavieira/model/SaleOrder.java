package com.mlimavieira.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Sale Order Entity
 * 
 * This class represent the sale for the customer.
 * 
 * @author marciovieira
 */
@Entity
@Table(name = "sale_order")
public class SaleOrder extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represent the relation between SaleOrder and Customer
	 */
	@NotNull
	@JoinColumn(name = "customer_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	/**
	 * Total price of the Sale Order.
	 */
	@NotNull
	@Column(name = "total_price", nullable = false, precision = 15, scale = 2)
	private Double totalPrice;

	/**
	 * Represent each line of the Sale Order
	 */
	@Size(min = 1)
	@OneToMany(mappedBy = "saleOrder", cascade = { CascadeType.ALL })
	private List<OrderLine> orderLines;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderLine> getOrderLines() {
		if (orderLines == null) {
			orderLines = new ArrayList<>();
		}
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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
		final SaleOrder other = (SaleOrder) obj;
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
		builder.append("SaleOrder [customer=");
		builder.append(customer);
		builder.append(", totalPrice=");
		builder.append(totalPrice);
		builder.append(", orderLines=");
		builder.append(orderLines);
		builder.append("]");
		return builder.toString();
	}

}
