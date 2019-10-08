/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.response;

import com.cybozu.kintone.client.model.record.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;


public class GetRecordsResponse {

	private ArrayList<HashMap<String, FieldValue>> records;
	private Integer totalCount;

	/**
	 * Get records data response
	 * @return records
	 */
	public ArrayList<HashMap<String, FieldValue>> getRecords() {
		return this.records;
	}

	/**
	 * Set records data response
	 * @param records the records to set
	 */
	public void setRecords(ArrayList<HashMap<String, FieldValue>> records) {
		this.records = records;
	}

	/**
	 * Get total count
	 * @return totalCount
	 */
	public Integer getTotalCount() {
		return this.totalCount;
	}

	/**
	 * Set total count
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
