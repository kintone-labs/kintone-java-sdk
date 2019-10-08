/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.request;

import com.cybozu.kintone.client.model.record.RecordUpdateItem;

import java.util.ArrayList;

public class UpdateRecordsRequest {

	private Integer app;
	private ArrayList<RecordUpdateItem> records;

	/**
	 * Constructor
	 * @param app app of the UpdateRecordsRequest
	 * @param records records of the UpdateRecordsRequest
	 */
	public UpdateRecordsRequest(Integer app, ArrayList<RecordUpdateItem> records) {
		this.app = app;
		this.records = records;
	}

}
