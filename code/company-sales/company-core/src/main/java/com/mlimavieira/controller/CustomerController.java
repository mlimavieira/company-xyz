package com.mlimavieira.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlimavieira.dto.CustomerDto;
import com.mlimavieira.dto.MessageDto;
import com.mlimavieira.dto.ProductDto;
import com.mlimavieira.dto.ResponseDto;
import com.mlimavieira.model.Customer;
import com.mlimavieira.model.SaleOrder;
import com.mlimavieira.repository.CustomerRepository;
import com.mlimavieira.repository.SaleOrderRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "/{version}/customer", produces = MediaType.APPLICATION_JSON_VALUE, description = "This API is responsible for the Customers CRUD ")
@RestController
@RequestMapping(value = "/{version}/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private Mapper dozerMapper;

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<?> exeptionHandler(EmptyResultDataAccessException ex) {

		final MessageDto messageDto = new MessageDto("404", "Customer not found");
		messageDto.setDescription(ex.getMessage());

		return new ResponseEntity<>(new ResponseDto<>(messageDto, false), HttpStatus.NOT_FOUND);
	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "delete customer", notes = "This endpoint is responsible to delete customer. If this customer is not associate to any sale order")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final List<SaleOrder> saleOrders = saleOrderRepository.getByCustomer(id);
		if (saleOrders != null && !saleOrders.isEmpty()) {
			final MessageDto message = new MessageDto("400", "Exist Sale Order for this customer");
			return new ResponseEntity<>(new ResponseDto<MessageDto>(message, false), HttpStatus.BAD_REQUEST);
		}

		customerRepository.delete(id);

		final MessageDto message = new MessageDto("200", "Customer removed");
		return new ResponseEntity<>(new ResponseDto<>(message, true), HttpStatus.OK);
	}

	@ApiOperation(value = "/{id}", response = ResponseDto.class, nickname = "find customer by id", notes = "This endpoint is responsible to retrieve customer using id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final Customer customer = customerRepository.findOne(id);

		if (customer == null) {
			final MessageDto message = new MessageDto("404", "Customer not found");
			return new ResponseEntity<>(new ResponseDto<>(message, false), HttpStatus.NOT_FOUND);

		}

		final CustomerDto dto = dozerMapper.map(customer, CustomerDto.class);

		return new ResponseEntity<>(new ResponseDto<>(dto, true), HttpStatus.OK);
	}

	@ApiOperation(value = "/", responseContainer = "List", response = ProductDto.class, nickname = "list all customers", notes = "This endpoint is responsible to list all customers in application")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAll(@PathVariable("version") Long version) {

		final List<Customer> listCustomer = (List<Customer>) customerRepository.findAll();

		if (listCustomer == null || listCustomer.isEmpty()) {
			final MessageDto message = new MessageDto("404", "Any customer found on the database");
			return new ResponseEntity<>(new ResponseDto<>(message, false), HttpStatus.NOT_FOUND);
		}

		final List<CustomerDto> listCustomerDto = new ArrayList<>();
		for (final Customer customer : listCustomer) {
			final CustomerDto dto = dozerMapper.map(customer, CustomerDto.class);
			listCustomerDto.add(dto);
		}
		return new ResponseEntity<>(listCustomerDto, HttpStatus.OK);
	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "save customer", notes = "This endpoint is responsible to create or update customer")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@PathVariable("version") Long version, @Valid @RequestBody CustomerDto dto) {

		final boolean isNewCustomer = dto.getId() == null;

		Customer customer = dozerMapper.map(dto, Customer.class);

		if (isNewCustomer) {
			customer.setCurrentCreditLimit(dto.getCreditLimit());
		}

		customer = customerRepository.save(customer);
		dto.setId(customer.getId());
		return new ResponseEntity<>(new ResponseDto<>(dto, true), isNewCustomer ? HttpStatus.CREATED : HttpStatus.OK);
	}
}
