/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.HashMap;

import com.cybozu.kintone.client.model.record.field.FieldValue;

public class RecordUpdateItem {

	private Integer id;
	private Integer revision;
	private RecordUpdateKey updateKey;
	private HashMap<String, FieldValue> record;

	/**
	 * Constructor
	 * @param id
	 * @param revision
	 * @param updateKey
	 * @param record
	 */
	public RecordUpdateItem(Integer id, Integer revision, RecordUpdateKey updateKey, HashMap<String, FieldValue> record) {
		this.id = id;
		this.revision = revision;
		this.updateKey = updateKey;
		this.record = record;
	}

}
