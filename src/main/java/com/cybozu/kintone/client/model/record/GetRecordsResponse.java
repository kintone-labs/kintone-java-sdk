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

public class GetRecordsResponse {

	private ArrayList<HashMap<String, FieldValue>> records;
	private Integer totalCount;

	/**
	 * Get records data response
	 * @return
	 */
	public ArrayList<HashMap<String, FieldValue>> getRecords() {
		return this.records;
	}

	/**
	 * Set records data response
	 * @param records
	 */
	public void setRecords(ArrayList<HashMap<String, FieldValue>> records) {
		this.records = records;
	}

	/**
	 * Get total count
	 * @return
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * Set total count
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
