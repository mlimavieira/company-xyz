package com.mlimavieira.model.valitation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mlimavieira.model.Customer;

/**
 * This class is reponsible to validate if the customer has available credit for
 * the sale.
 * 
 * @author marciovieira
 *
 */
public class CustomerCreditLimitConstraint implements ConstraintValidator<CustomerCredtValidation, Customer> {

	@Override
	public void initialize(CustomerCredtValidation constraintAnnotation) {

	}

	@Override
	public boolean isValid(Customer customer, ConstraintValidatorContext context) {

		if (customer.getCreditLimit() == null || customer.getCurrentCreditLimit() == null) {
			return false;
		}
		if (customer.getCurrentCreditLimit() > customer.getCreditLimit()) {
			return false;
		}

		return true;
	}

}
