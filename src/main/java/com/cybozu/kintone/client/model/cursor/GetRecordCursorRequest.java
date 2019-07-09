package com.cybozu.kintone.client.model.cursor;

public class GetRecordCursorRequest {
	private String id;
	
	public GetRecordCursorRequest(String id) {
		this.setId(id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
