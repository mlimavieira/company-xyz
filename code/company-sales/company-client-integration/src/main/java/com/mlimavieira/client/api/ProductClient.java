package com.mlimavieira.client.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mlimavieira.client.api.unirest.CustomObjectMapper;
import com.mlimavieira.dto.MessageDto;
import com.mlimavieira.dto.ProductDto;
import com.mlimavieira.dto.ResponseDto;

public class ProductClient {

	private static final String BASE_URL = "http://localhost:8080/1/product";

	static {
		Unirest.setObjectMapper(new CustomObjectMapper());
		Unirest.setTimeouts(10000, 10000);
		Unirest.setDefaultHeader("accept", "application/json");
		Unirest.setDefaultHeader("content-type", "application/json");
	}

	public static ProductDto save(ProductDto product) {
		try {

			final HttpResponse<String> httpResponse = Unirest.post(BASE_URL).body(product).asString();

			System.out.println(httpResponse.getBody());
			if (httpResponse.getStatus() != 200 && httpResponse.getStatus() != 201) {
				return null;
			}

			return new Gson().fromJson(httpResponse.getBody(), ProductDto.class);

		} catch (final UnirestException e) {
			System.err.println(e);
			return null;
		}
	}

	public static boolean delete(Long id) {
		if (id == null) {
			return false;
		}
		try {
			final HttpResponse<String> httpResponse = Unirest.delete(BASE_URL + "/{id}")
					.routeParam("id", String.valueOf(id)).asString();

			System.out.println(httpResponse.getBody());
			if (httpResponse.getStatus() != 200) {
				return false;
			}

			final Type type = new TypeToken<ResponseDto<MessageDto>>() {
			}.getType();

			final ResponseDto responseDto = new Gson().fromJson(httpResponse.getBody(), type);

			return responseDto.isSuccess();
		} catch (final UnirestException e) {
			System.err.println(e);
			return false;
		}
	}

	public static ProductDto getById(Long id) {
		try {

			final HttpResponse<String> httpResponse = Unirest.get(BASE_URL + "/{id}")
					.routeParam("id", String.valueOf(id)).asString();

			if (httpResponse.getStatus() != 200) {
				System.out.println(httpResponse.getBody());
				return null;
			}

			final Type type = new TypeToken<ResponseDto<ProductDto>>() {
			}.getType();

			final ResponseDto<ProductDto> responseDto = new Gson().fromJson(httpResponse.getBody(), type);
			return responseDto.getBody();
		} catch (final UnirestException e) {
			System.err.println(e);
			return null;
		}

	}

	public static List list() {
		try {
			final HttpResponse<String> httpResponse = Unirest.get(BASE_URL).asString();

			if (httpResponse.getStatus() != 200) {
				System.out.println(httpResponse.getBody());
				return new ArrayList<>();
			}

			final Type listType = new TypeToken<List<ProductDto>>() {
			}.getType();

			return new Gson().fromJson(httpResponse.getBody(), listType);
		} catch (final UnirestException e) {
			System.err.println(e);
			return null;
		}

	}
}
