/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.HashMap;

import com.cybozu.kintone.client.model.record.field.FieldValue;

public class UpdateRecordRequest {

	private Integer app;
	private Integer id;
	private RecordUpdateKey updateKey;
	private Integer revision;
	private HashMap<String, FieldValue> record;

	/**
	 * Constructor
	 * @param app
	 * @param id
	 * @param updateKey
	 * @param revision
	 * @param record
	 */
	public UpdateRecordRequest(Integer app, Integer id, RecordUpdateKey updateKey, Integer revision, HashMap<String, FieldValue> record) {
		this.app = app;
		this.id = id;
		this.updateKey = updateKey;
		this.revision = revision;
		this.record = record;
	}

}
