package com.mlimavieira.client.api.unirest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;

public class CustomObjectMapper implements ObjectMapper {

	private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

	@Override
	public <T> T readValue(String value, Class<T> valueType) {
		try {

			return jacksonObjectMapper.readValue(value, valueType);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String writeValue(Object value) {
		try {
			return jacksonObjectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
