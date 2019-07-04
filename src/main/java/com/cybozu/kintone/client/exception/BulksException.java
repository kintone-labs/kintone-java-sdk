package com.cybozu.kintone.client.exception;

import java.util.ArrayList;

/**
 * Exception will occur when using multiple bulk request.
 *
 */
public class BulksException extends Exception{
	private static final long serialVersionUID = 1L;
	private ArrayList<Object> results;
	public Object getResults() {
		return results;
	}

	public BulksException(ArrayList<Object> object) {
		this.results = object;
	}
	
}
