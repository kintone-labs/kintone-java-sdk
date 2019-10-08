package com.cybozu.kintone.client.model.cursor;

public class CreateRecordCursorResponse {
	private String id;
	private Integer totalCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
