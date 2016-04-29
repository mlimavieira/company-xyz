package com.mlimavieira.controller;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mlimavieira.dto.FieldErrorMessageDto;
import com.mlimavieira.dto.MessageDto;
import com.mlimavieira.dto.ResponseDto;
import com.mlimavieira.exception.BusinessException;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<?> exeptionHandler(Exception ex) {

		LOG.error("unexpected error", ex);

		final MessageDto messageDto = new MessageDto("500", "An unexpected error has occurred, please try again");
		messageDto.setDescription(ex.getMessage());

		return new ResponseEntity<>(new ResponseDto<>(messageDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<?> handleBusinessException(BusinessException ex) {

		LOG.warn("BusinessException error", ex.getMessage());

		final MessageDto messageDto = new MessageDto("400", ex.getMessage());
		messageDto.setDescription(ex.getMessage());

		return new ResponseEntity<>(new ResponseDto<>(messageDto, false), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {

		LOG.warn("Validation error", ex.getMessage());

		final FieldErrorMessageDto errorMessageDto = new FieldErrorMessageDto("400",
				"Validation Exception, please check your request");

		for (final ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {

			final Path propertyPath = constraintViolation.getPropertyPath();
			errorMessageDto.addError(propertyPath.toString(), constraintViolation.getMessage());
		}

		return new ResponseEntity<>(new ResponseDto<>(errorMessageDto, false), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex) {

		LOG.warn("Validation error", ex.getMessage());

		final BindingResult result = ex.getBindingResult();

		final FieldErrorMessageDto errorMessageDto = new FieldErrorMessageDto("400",
				"Validation Exception, please check your request");

		final List<FieldError> fieldErrors = result.getFieldErrors();
		for (final FieldError fieldError : fieldErrors) {
			errorMessageDto.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return new ResponseEntity<>(new ResponseDto<>(errorMessageDto, false), HttpStatus.BAD_REQUEST);
	}
}
