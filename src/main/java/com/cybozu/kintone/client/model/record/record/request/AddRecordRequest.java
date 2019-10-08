/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.request;

import com.cybozu.kintone.client.model.record.FieldValue;

import java.util.HashMap;


public class AddRecordRequest {

	private Integer app;
	private HashMap<String, FieldValue> record;

	/**
	 * Constructor
	 * @param app app of the AddRecordRequest
	 * @param record record of the AddRecordRequest
	 */
	public AddRecordRequest(Integer app, HashMap<String, FieldValue> record) {
		this.app = app;
		this.record = record;
	}
}
