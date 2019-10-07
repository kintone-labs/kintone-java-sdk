/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulk_request;
import java.util.ArrayList;;

public class BulkRequestResponse {
	private ArrayList<Object> results;

	/**
	 * Constructor
	 */
	public BulkRequestResponse() {
		this.results = new ArrayList<Object>();
	}

	/**
	 * Get result
	 * @return results
	 */
	public ArrayList<Object> getResults() {
		return results;
	}

	/**
	 * Add response Object to the results.
	 *
	 * @param responseObject the responseObject to add
	 */
	public void addResponse(Object responseObject) {
		results.add(responseObject);
	}
	
	public ArrayList<Object> addResponses(ArrayList<Object> responseObject) {
		results.addAll(responseObject);
		return results;
	}
}
