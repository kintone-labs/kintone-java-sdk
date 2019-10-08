/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.request;

import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;

import java.util.ArrayList;

public class UpdateRecordsStatusRequest {

	private Integer app;
	private ArrayList<RecordUpdateStatusItem> records;

	/**
	 * Constructor
	 * @param app app of the UpdateRecordsStatusRequest
	 * @param records records of the UpdateRecordsStatusRequest
	 */
	public UpdateRecordsStatusRequest(Integer app, ArrayList<RecordUpdateStatusItem> records) {
		this.app = app;
		this.records = records;
	}

}
