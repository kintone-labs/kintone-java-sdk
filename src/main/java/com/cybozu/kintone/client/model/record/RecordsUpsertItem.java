/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.HashMap;


public class RecordsUpsertItem {

	private RecordUpdateKey updateKey;
	private HashMap<String, FieldValue> record;

	/**
	 * Constructor
	 * @param updateKey updateKey of the RecordUpsertItem
	 * @param record record of the RecordUpsertItem
	 */
	public RecordsUpsertItem(RecordUpdateKey updateKey, HashMap<String, FieldValue> record) {
		this.updateKey = updateKey;
		this.record = record;
	}
	
	/**
	 * Constructor
	 * @param record record of the RecordUpsertItem
	 */
	public RecordsUpsertItem(HashMap<String, FieldValue> record) {
		this.record = record;
    }
    
    /**
	 * Get updateKey
	 * @return updateKey
	 */
	public RecordUpdateKey getUpdateKey() {
		return this.updateKey;
    }
    
    /**
	 * Set updateKey
	 * @param updateKey the updateKey to set
	 */
    public void setUpdateKey(RecordUpdateKey updateKey) {
        this.updateKey = updateKey;
    }

    /**
	 * Get record
	 * @return record
	 */
	public HashMap<String, FieldValue> getRecord() {
		return this.record;
    }
    
    /**
	 * Set record
	 * @param record the updateKey to set
	 */
    public void setRecord(HashMap<String, FieldValue> record) {
        this.record = record;
    }
}
