package com.mlimavieira.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Product Entity
 * 
 * This class represent the Product.
 * 
 * @author marciovieira
 */
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represent the description of the product
	 */
	@NotEmpty
	@Size(min = 5, max = 255)
	@Column(name = "description", nullable = false, length = 255)
	private String description;

	/**
	 * Represent the price of the product
	 */
	@NotNull
	@Column(name = "price", nullable = false, precision = 15, scale = 2)
	private Double price;

	/**
	 * Represent the current stock of the product
	 */
	@NotNull
	@Min(value = 0)
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	/**
	 * Relation between Sale Order and Product
	 */
	@OneToMany(mappedBy = "product")
	private List<OrderLine> orderLines;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
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
		final Product other = (Product) obj;
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
		builder.append("Product [description=");
		builder.append(description);
		builder.append(", price=");
		builder.append(price);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", orderLines=");
		builder.append(orderLines);
		builder.append("]");
		return builder.toString();
	}

}