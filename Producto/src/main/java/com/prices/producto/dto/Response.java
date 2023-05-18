package com.prices.producto.dto;

import java.util.Calendar;
import java.util.Date;

public class Response<T> {
	private static final String MSG_EMPTY = "OK";
	private static final int CODE_OK = 200;
	private static final String BLANK = "";
	private static final Long CERO_TIME = 0L;

	private int status;
	private final Date timestamp;
	private String message;
	private final String error;
	private final String path;
	private Long processTime;

	private T response;

	public Response() {
		this.timestamp = Calendar.getInstance().getTime();
		this.message = Response.MSG_EMPTY;
		this.status = Response.CODE_OK;
		this.error = Response.BLANK;
		this.path = Response.BLANK;
		this.response = null;
		this.processTime = Response.CERO_TIME;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public T getResponse() {
		return this.response;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}
	
	public String getError() {
		return this.error;
	}
	
	public String getPath() {
		return this.path;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public Response<T> setResponse(final T obj) {
		this.response = obj;
		return this;
	}
}
