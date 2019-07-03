package com.cybozu.kintone.client.exception;

/**
 * Exception will occur when using multiple bulk request.
 *
 */
public class BulksException extends Exception{
	private static final long serialVersionUID = 1L;
	private Object results;
	public Object getResults() {
		return results;
	}

	public BulksException(Object object) {
		this.results = object;
	}
	
	public Object getResponse() {
		return results;
	}
	
}
