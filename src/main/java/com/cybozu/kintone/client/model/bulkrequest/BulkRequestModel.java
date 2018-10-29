/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulkrequest;
import java.util.ArrayList;

public class BulkRequestModel {
	private ArrayList<BulkRequestItem> requests;

	/**
	 * Constructor
	 */
	public BulkRequestModel() {
		this.requests = new ArrayList<BulkRequestItem>();
	}

	/**
	 * Add bulk Request Item to the bulk request function.
	 *
	 * @param bulkRequestItem
	 */
	public void addRequest(BulkRequestItem bulkRequestItem) {
		this.requests.add(bulkRequestItem);
	}

	/**
	 * Get bulk Request Item to the bulk request function.
	 */
	public ArrayList<BulkRequestItem> getRequests() {
		return this.requests;
	}

}
