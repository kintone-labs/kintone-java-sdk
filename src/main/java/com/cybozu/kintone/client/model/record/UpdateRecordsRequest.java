/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class UpdateRecordsRequest {

	private Integer app;
	private ArrayList<RecordUpdateItem> records;

	/**
	 * Constructor
	 * @param app
	 * @param records
	 */
	public UpdateRecordsRequest(Integer app, ArrayList<RecordUpdateItem> records) {
		this.app = app;
		this.records = records;
	}

}
