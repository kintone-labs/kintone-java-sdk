/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.request;

import java.util.ArrayList;

public class UpdateRecordAssigneesRequest {

	private Integer app;
	private Integer id;
	private ArrayList<String> assignees;
	private Integer revision;

	/**
	 * Constructor
	 * @param app app of the UpdateRecordAssigneesRequest
	 * @param id id of the UpdateRecordAssigneesRequest
	 * @param assignees assignees of the UpdateRecordAssigneesRequest
	 * @param revision revision of the UpdateRecordAssigneesRequest
	 */
	public UpdateRecordAssigneesRequest(Integer app, Integer id, ArrayList<String> assignees, Integer revision) {
		this.app = app;
		this.id = id;
		this.assignees = assignees;
		this.revision = revision;
	}

}
