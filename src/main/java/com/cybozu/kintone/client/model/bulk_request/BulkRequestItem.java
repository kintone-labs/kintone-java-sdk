/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulk_request;

public class BulkRequestItem {
	private String method;
	private String api;
	private Object payload;

	/**
	 * Constructor
	 * @param method method of the BulkRequestItem
	 * @param api api of the BulkRequestItem
	 * @param payload payload of the BulkRequestItem
	 */
	public BulkRequestItem(String method, String api, Object payload) {
		this.method = method;
		this.api = api;
		this.payload = payload;
	}

	/**
	 * Get api
	 * @return api
	 */
	public String getApi() {
		return this.api;
	}

	/**
	 * Get http method
	 * @return method
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Get payload
	 * @return payload
	 */
	public Object getPayload() {
		return this.payload;
	}
}
