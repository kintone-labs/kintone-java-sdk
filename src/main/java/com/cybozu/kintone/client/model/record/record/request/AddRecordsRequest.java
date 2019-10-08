/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.request;

import com.cybozu.kintone.client.model.record.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;


public class AddRecordsRequest {

	private Integer app;
	private ArrayList<HashMap<String, FieldValue>> records;

	/**
	 * Constructor
	 * @param app app of the AddRecordsRequest
	 * @param records records of the AddRecordsRequest
	 */
	public AddRecordsRequest(Integer app, ArrayList<HashMap<String, FieldValue>> records) {
		this.app = app;
		this.records = records;
	}

}
