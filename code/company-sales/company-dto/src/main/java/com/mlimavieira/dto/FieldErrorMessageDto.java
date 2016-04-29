package com.mlimavieira.dto;

import java.util.HashMap;
import java.util.Map;

public class FieldErrorMessageDto extends MessageDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Map<String, String> fields = new HashMap<String, String>();

	public FieldErrorMessageDto(String code, String message) {
		super(code, message);
	}

	public void addError(String field, String message) {
		fields.put(field, message);
	}

	public Map<String, String> getFields() {
		return fields;
	}
}
