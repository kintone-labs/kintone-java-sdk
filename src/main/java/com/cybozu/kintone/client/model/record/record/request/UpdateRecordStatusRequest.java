/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

public class UpdateRecordStatusRequest {

	private String action;
	private Integer app;
	private String assignee;
	private Integer id;
	private Integer revision;

	/**
	 * Constructor
	 * @param action action of the UpdateRecordStatusRequest
	 * @param app app of the UpdateRecordStatusRequest
	 * @param assignee assignee of the UpdateRecordStatusRequest
	 * @param id id of the UpdateRecordStatusRequest
	 * @param revision revision of the UpdateRecordStatusRequest
	 */
	public UpdateRecordStatusRequest(String action, Integer app, String assignee, Integer id, Integer revision) {
		this.action = action;
		this.app = app;
		this.assignee = assignee;
		this.id = id;
		this.revision = revision;
	}

}
