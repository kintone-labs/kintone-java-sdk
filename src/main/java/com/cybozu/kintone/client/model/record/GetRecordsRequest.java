/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;


import java.util.ArrayList;

public class GetRecordsRequest {

	private ArrayList<String> fields;
	private Integer app;
	private String query;
	private Boolean totalCount;

	/**
	 * Constructor
	 * @param fields
	 * @param app
	 * @param query
	 * @param totalCount
	 */
	public GetRecordsRequest(ArrayList<String> fields, Integer app, String query, Boolean totalCount) {
		this.fields = fields;
		this.app = app;
		this.totalCount = totalCount;
		this.query = query;
	}

}
