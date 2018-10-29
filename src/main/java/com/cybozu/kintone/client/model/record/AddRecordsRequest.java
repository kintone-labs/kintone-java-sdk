/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.model.record.field.FieldValue;

public class AddRecordsRequest {

	private Integer app;
	private ArrayList<HashMap<String, FieldValue>> records;

	/**
	 * Constructor
	 * @param app
	 * @param records
	 */
	public AddRecordsRequest(Integer app, ArrayList<HashMap<String, FieldValue>> records) {
		this.app = app;
		this.records = records;
	}

}
