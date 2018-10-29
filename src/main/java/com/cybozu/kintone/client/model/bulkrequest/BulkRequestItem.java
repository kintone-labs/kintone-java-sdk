/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.bulkrequest;

public class BulkRequestItem {
	private String method;
	private String api;
	private Object payload;

	/**
	 * Constructor
	 * @param app
	 * @param record
	 */
	public BulkRequestItem(String method, String api, Object payload) {
		this.method = method;
		this.api = api;
		this.payload = payload;
	}

	/**
	 * Get api
	 * @return
	 */
	public String getApi() {
		return this.api;
	}

	/**
	 * Get http method
	 * @return
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Get payload
	 * @return
	 */
	public Object getPayload() {
		return this.payload;
	}
}
