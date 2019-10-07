/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulkrequest;
import java.util.ArrayList;

public class BulkRequestResponses {
	private ArrayList<Object> responses;

	/**
	 * Constructor
	 */
	public BulkRequestResponses() {
		this.responses = new ArrayList<Object>();
	}

	/**
	 * Get result
	 * @return results
	 */
	public ArrayList<Object> getResponses() {
		return responses;
	}

	/**
	 * Add response Object to the results.
	 *
	 * @param responseObject the responseObject to add
	 */
	public void addResponse(Object responseObject) {
		responses.add(responseObject);
	}
	
	public ArrayList<Object> addResponses(ArrayList<Object> responsesObject) {
		responses.addAll(responsesObject);
		return responses;
	}
}
