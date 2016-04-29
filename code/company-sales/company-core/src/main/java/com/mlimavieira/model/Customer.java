package com.mlimavieira.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.mlimavieira.model.valitation.CustomerCredtValidation;

/**
 * Customer Line Entity
 * 
 * This class represent the Customer.
 * 
 * @author marciovieira
 */
@Entity
@CustomerCredtValidation
@Table(name = "customer")
public class Customer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represent the name of the client
	 */
	@NotEmpty
	@Size(max = 100)
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	/**
	 * Represent the total amount of credit for the client
	 */
	@NotNull
	@Min(value = 1)
	@Column(name = "credit_limit", nullable = false, precision = 15, scale = 2)
	private Double creditLimit;

	/**
	 * Represent the total amount of available credit for the client on each
	 * Sale this value is reduced. The customer Only can buy if this value was
	 * available.
	 */
	@NotNull
	@Min(value = 0)
	@Column(name = "current_credit_limit", nullable = false, precision = 15, scale = 2)
	private Double currentCreditLimit;

	/**
	 * Represent the customer Address
	 */
	@Column(name = "address", length = 255)
	private String address;

	/**
	 * Represent the customer first phone
	 */
	@Column(name = "phone1", length = 20)
	private String phone1;

	/**
	 * Represent the customer second phone
	 */
	@Column(name = "phone2", length = 20)
	private String phone2;

	/**
	 * Represent the relation between Sale Order and Customer. Used to retrieve
	 * all the Sale Orders for the customer.
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<SaleOrder> saleOrders;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Double getCurrentCreditLimit() {
		return currentCreditLimit;
	}

	public void setCurrentCreditLimit(Double currentCreditLimit) {
		this.currentCreditLimit = currentCreditLimit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public List<SaleOrder> getSaleOrders() {
		return saleOrders;
	}

	public void setSaleOrders(List<SaleOrder> saleOrders) {
		this.saleOrders = saleOrders;
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
		final Customer other = (Customer) obj;
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
		builder.append("Customer [name=");
		builder.append(name);
		builder.append(", creditLimit=");
		builder.append(creditLimit);
		builder.append(", currentCreditLimit=");
		builder.append(currentCreditLimit);
		builder.append(", address=");
		builder.append(address);
		builder.append(", phone1=");
		builder.append(phone1);
		builder.append(", phone2=");
		builder.append(phone2);
		builder.append(", saleOrders=");
		builder.append(saleOrders);
		builder.append("]");
		return builder.toString();
	}

}
