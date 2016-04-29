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

import com.mlimavieira.dto.MessageDto;
import com.mlimavieira.dto.ProductDto;
import com.mlimavieira.dto.ResponseDto;
import com.mlimavieira.dto.SaleOrderDto;
import com.mlimavieira.exception.BusinessException;
import com.mlimavieira.model.Customer;
import com.mlimavieira.model.OrderLine;
import com.mlimavieira.model.Product;
import com.mlimavieira.model.SaleOrder;
import com.mlimavieira.repository.CustomerRepository;
import com.mlimavieira.repository.ProductRepository;
import com.mlimavieira.repository.SaleOrderRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "/{version}/sale-order", produces = MediaType.APPLICATION_JSON_VALUE, description = "This API is responsible for the Sale Orders CRUD ")
@RestController
@RequestMapping(value = "/{version}/sale-order", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaleOrderController {

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Mapper dozerMapper;

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<?> exeptionHandler(EmptyResultDataAccessException ex) {

		final MessageDto messageDto = new MessageDto("404", "Sale Order not found");
		messageDto.setDescription(ex.getMessage());

		return new ResponseEntity<>(new ResponseDto<>(messageDto, false), HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "/", responseContainer = "List", response = ProductDto.class, nickname = "list all sale orders", notes = "This endpoint is responsible to list all sale orders in application")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAll(@PathVariable("version") Long version) {

		final List<SaleOrder> listSaleOrder = (List<SaleOrder>) saleOrderRepository.findAll();

		if (listSaleOrder == null || listSaleOrder.isEmpty()) {
			final MessageDto message = new MessageDto("404", "Any saleOrder found on the database");
			return new ResponseEntity<>(new ResponseDto<>(message, false), HttpStatus.NOT_FOUND);

		}

		final List<SaleOrderDto> listSaleOrderDto = new ArrayList<>();
		for (final SaleOrder saleOrder : listSaleOrder) {
			final SaleOrderDto dto = dozerMapper.map(saleOrder, SaleOrderDto.class);
			listSaleOrderDto.add(dto);
		}

		return new ResponseEntity<>(listSaleOrderDto, HttpStatus.OK);
	}

	@ApiOperation(value = "/{id}", response = ResponseDto.class, nickname = "find sale order by id", notes = "This endpoint is responsible to retrieve sale order using id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final SaleOrder saleOrder = saleOrderRepository.findOne(id);

		if (saleOrder == null) {
			final MessageDto message = new MessageDto("404", "SaleOrder not found");
			return new ResponseEntity<>(new ResponseDto<>(message, false), HttpStatus.NOT_FOUND);
		}

		final SaleOrderDto saleOrderDto = dozerMapper.map(saleOrder, SaleOrderDto.class);

		return new ResponseEntity<>(new ResponseDto<>(saleOrderDto, true), HttpStatus.OK);

	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "save sale order", notes = "This endpoint is responsible to create or update sale order ")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@PathVariable("version") Long version, @Valid @RequestBody SaleOrderDto dto) {

		final boolean isNewSaleOrder = dto.getId() == null;

		SaleOrder saleOrder = dozerMapper.map(dto, SaleOrder.class);

		final Double total = reduceProductStock(saleOrder);

		reduceCustomerAvailableCredit(saleOrder, total);

		saleOrder.setTotalPrice(total);
		saleOrder = saleOrderRepository.save(saleOrder);
		dto.setId(saleOrder.getId());

		return new ResponseEntity<>(new ResponseDto<>(dto, true), isNewSaleOrder ? HttpStatus.CREATED : HttpStatus.OK);
	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "delete sale order", notes = "This endpoint is responsible to delete a sale order.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final SaleOrder currentSaleOrder = saleOrderRepository.getByIdFetched(id);

		if (currentSaleOrder == null) {
			final MessageDto message = new MessageDto("404", "Sale Order not found");
			return new ResponseEntity<>(new ResponseDto<MessageDto>(message, false), HttpStatus.NOT_FOUND);
		}
		final Customer currentCustomer = currentSaleOrder.getCustomer();

		increaseClientAvailableCredit(currentSaleOrder, currentCustomer);

		increaseStockQuantity(currentSaleOrder);

		saleOrderRepository.delete(id);

		final MessageDto message = new MessageDto("200", "Sale Order removed");
		return new ResponseEntity<>(new ResponseDto<MessageDto>(message, true), HttpStatus.OK);
	}

	/**
	 * Reduce customer available credit
	 * 
	 * @param saleOrder
	 * @param total
	 */
	private void reduceCustomerAvailableCredit(SaleOrder saleOrder, final Double total) {
		final Customer customer = customerRepository.findOne(saleOrder.getCustomer().getId());

		if (customer.getCurrentCreditLimit() < total) {
			throw new BusinessException("The customer does not have available credit");
		}

		customer.setCurrentCreditLimit(customer.getCurrentCreditLimit() - total);
		customerRepository.save(customer);
	}

	/**
	 * Reduce the product stock
	 * 
	 * @param saleOrder
	 * @return
	 */
	private Double reduceProductStock(SaleOrder saleOrder) {
		Double total = 0D;
		for (final OrderLine item : saleOrder.getOrderLines()) {

			final Product product = productRepository.findOne(item.getProduct().getId());

			if (product.getQuantity() < item.getQuantity()) {
				throw new BusinessException(product.getDescription() + " not available");
			}

			product.setQuantity(product.getQuantity() - item.getQuantity());
			productRepository.save(product);

			item.setSaleOrder(saleOrder);
			item.setProductPrice(product.getPrice());
			total += (item.getQuantity() * product.getPrice());
		}
		return total;
	}

	/**
	 * Increase the customer available credit when the sale order is deleted
	 * 
	 * @param currentSaleOrder
	 * @param currentCustomer
	 */
	private void increaseClientAvailableCredit(final SaleOrder currentSaleOrder, final Customer currentCustomer) {
		Double newCreditLimit = currentCustomer.getCurrentCreditLimit() + currentSaleOrder.getTotalPrice();

		if (currentCustomer.getCreditLimit() <= newCreditLimit) {
			newCreditLimit = currentCustomer.getCreditLimit();
		}

		currentCustomer.setCurrentCreditLimit(newCreditLimit);
		customerRepository.save(currentCustomer);
	}

	/**
	 * Increase the stock of the product when the sale order is deleted
	 * 
	 * @param currentSaleOrder
	 */
	private void increaseStockQuantity(final SaleOrder currentSaleOrder) {

		for (final OrderLine line : currentSaleOrder.getOrderLines()) {
			final Product orderedProduct = line.getProduct();

			final Integer newStock = line.getQuantity() + orderedProduct.getQuantity();
			orderedProduct.setQuantity(newStock);

			productRepository.save(orderedProduct);
		}
	}

}
