/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.response;

import java.util.HashMap;

import com.cybozu.kintone.client.model.record.FieldValue;

public class GetRecordResponse {

	private HashMap<String, FieldValue> record;

	/**
	 * Get Record data response
	 * @return record
	 */
	public HashMap<String, FieldValue> getRecord() {
		return this.record;
	}

	/**
	 * Set Record data response
	 * @param record the record to set
	 */
	public void setRecord(HashMap<String, FieldValue> record) {
		this.record = record;
	}

}
