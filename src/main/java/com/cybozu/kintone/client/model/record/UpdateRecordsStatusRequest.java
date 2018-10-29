/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class UpdateRecordsStatusRequest {

	private Integer app;
	private ArrayList<RecordUpdateStatusItem> records;

	/**
	 * Constructor
	 * @param app
	 * @param records
	 */
	public UpdateRecordsStatusRequest(Integer app, ArrayList<RecordUpdateStatusItem> records) {
		this.app = app;
		this.records = records;
	}

}
