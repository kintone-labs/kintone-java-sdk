/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulk_request;
import java.util.ArrayList;

public class BulkRequestResponse {
	private ArrayList<Object> results;

	/**
	 * Constructor
	 */
	public BulkRequestResponse() {
		this.results = new ArrayList<>();
	}

	public void addResponse(Object responseObject) {
		results.add(responseObject);
	}
	/**
	 * Get result
	 * 
	 * @return results
	 */
	public ArrayList<Object> getResults() {
		return results;
	}
}
