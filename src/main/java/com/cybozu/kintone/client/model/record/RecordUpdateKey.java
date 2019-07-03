/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

public class RecordUpdateKey {

	private String field;
	private String value;

	/**
	 * Constructor
	 * @param field field of the RecordUpdateKey
	 * @param value value of the RecordUpdateKey
	 */
	public RecordUpdateKey(String field, String value) {
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
