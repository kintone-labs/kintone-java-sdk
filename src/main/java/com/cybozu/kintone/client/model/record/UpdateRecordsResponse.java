/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class UpdateRecordsResponse {

	private ArrayList<RecordUpdateResponseItem> records;

	/**
	 * Get record ids
	 * @return records
	 */
	public ArrayList<RecordUpdateResponseItem> getRecords() {
		return this.records;
	}

	/**
	 * Set record ids
	 * @param records the records to set
	 */
	public void setRecords(ArrayList<RecordUpdateResponseItem> records) {
		this.records = records;
	}

}
