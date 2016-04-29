package com.mlimavieira.dto;

public class ResponseDto<T> {

	private boolean success;
	private T body;

	public ResponseDto() {
		super();
	}

	public ResponseDto(T body, boolean isSuccess) {
		this.body = body;
		this.success = isSuccess;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
