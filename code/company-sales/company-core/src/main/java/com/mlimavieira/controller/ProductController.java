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
import com.mlimavieira.model.Product;
import com.mlimavieira.model.SaleOrder;
import com.mlimavieira.repository.ProductRepository;
import com.mlimavieira.repository.SaleOrderRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "/{version}/product", produces = MediaType.APPLICATION_JSON_VALUE, description = "This API is responsible for the Products CRUD")
@RestController
@RequestMapping(value = "/{version}/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private Mapper dozerMapper;

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<?> exeptionHandler(EmptyResultDataAccessException ex) {

		final MessageDto messageDto = new MessageDto("404", "Product not found");
		messageDto.setDescription(ex.getMessage());

		return new ResponseEntity<>(new ResponseDto<>(messageDto, false), HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "/", responseContainer = "List", response = ProductDto.class, nickname = "list all products", notes = "This endpoint is responsible to list all products in application")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAll(@PathVariable("version") Long version) {

		final List<Product> listProduct = (List<Product>) productRepository.findAll();

		if (listProduct == null || listProduct.isEmpty()) {
			final MessageDto message = new MessageDto("404", "Any product found on the database");
			return new ResponseEntity<>(new ResponseDto<MessageDto>(message, false), HttpStatus.NOT_FOUND);

		}

		final List<ProductDto> listProductDto = new ArrayList<>();
		for (final Product product : listProduct) {
			final ProductDto dto = dozerMapper.map(product, ProductDto.class);
			listProductDto.add(dto);
		}

		return new ResponseEntity<>(listProductDto, HttpStatus.OK);
	}

	@ApiOperation(value = "/{id}", response = ResponseDto.class, nickname = "find product by id", notes = "This endpoint is responsible to retrieve product using id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final Product product = productRepository.findOne(id);

		if (product == null) {
			final MessageDto message = new MessageDto("404", "Product not found");
			return new ResponseEntity<>(new ResponseDto<MessageDto>(message, false), HttpStatus.NOT_FOUND);

		}

		final ProductDto productDto = dozerMapper.map(product, ProductDto.class);

		return new ResponseEntity<>(new ResponseDto<>(productDto, true), HttpStatus.OK);

	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "save product", notes = "This endpoint is responsible to create or update product")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@PathVariable("version") Long version, @Valid @RequestBody ProductDto dto) {

		final boolean isNewProduct = dto.getId() == null;

		Product product = dozerMapper.map(dto, Product.class);
		product = productRepository.save(product);
		dto.setId(product.getId());

		return new ResponseEntity<>(new ResponseDto<>(dto, true), isNewProduct ? HttpStatus.CREATED : HttpStatus.OK);
	}

	@Transactional
	@ApiOperation(value = "/", response = ResponseDto.class, nickname = "delete product", notes = "This endpoint is responsible to delete product. If this product is not associate to any sale order")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("version") Long version, @PathVariable("id") Long id) {

		final List<SaleOrder> saleOrders = saleOrderRepository.getByProductId(id);
		if (saleOrders != null && !saleOrders.isEmpty()) {

			final MessageDto message = new MessageDto("400", "Exist Sale Order for this product");
			return new ResponseEntity<>(new ResponseDto<MessageDto>(message, false), HttpStatus.BAD_REQUEST);
		}

		productRepository.delete(id);
		final MessageDto message = new MessageDto("200", "Product removed");
		return new ResponseEntity<>(new ResponseDto<MessageDto>(message, true), HttpStatus.OK);
	}
}
