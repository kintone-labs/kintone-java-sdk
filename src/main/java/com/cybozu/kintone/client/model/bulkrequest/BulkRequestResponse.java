/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulkrequest;

import java.util.ArrayList;

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
	 * 
	 * @return results
	 */
	public ArrayList<Object> getResults() {
		return results;
	}
}
