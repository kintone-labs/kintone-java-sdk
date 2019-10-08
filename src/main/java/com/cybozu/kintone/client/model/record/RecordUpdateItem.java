/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.HashMap;

public class RecordUpdateItem {

	private Integer id;
	private Integer revision;
	private RecordUpdateKey updateKey;
	private HashMap<String, FieldValue> record;

	/**
	 * Constructor
	 * @param id id of the RecordUpdateItem
	 * @param revision revision of the RecordUpdateItem
	 * @param updateKey updateKey of the RecordUpdateItem
	 * @param record record of the RecordUpdateItem
	 */
	public RecordUpdateItem(Integer id, Integer revision, RecordUpdateKey updateKey, HashMap<String, FieldValue> record) {
		this.id = id;
		this.revision = revision;
		this.updateKey = updateKey;
		this.record = record;
	}

	/**
	 * Constructor
	 * @param updateKey updateKey of the RecordUpdateItem
	 * @param record record of the RecordUpdateItem
	 */
	public RecordUpdateItem(RecordUpdateKey updateKey, HashMap<String, FieldValue> record) {
		this.updateKey = updateKey;
		this.record = record;
	}
}
