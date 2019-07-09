package com.cybozu.kintone.client.model.cursor;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.model.record.field.FieldValue;

public class GetRecordCursorResponse {
	private ArrayList<HashMap<String, FieldValue>> records;
	private Boolean next;

	public ArrayList<HashMap<String, FieldValue>> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<HashMap<String, FieldValue>> records) {
		this.records = records;
	}
	public Boolean getNext() {
		return next;
	}
	public void setNext(Boolean next) {
		this.next = next;
	}
}
