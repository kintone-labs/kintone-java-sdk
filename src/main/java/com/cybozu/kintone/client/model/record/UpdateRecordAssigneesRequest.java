/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class UpdateRecordAssigneesRequest {

	private Integer app;
	private Integer id;
	private ArrayList<String> assignees;
	private Integer revision;

	/**
	 * Constructor
	 * @param app
	 * @param id
	 * @param assignees
	 * @param revision
	 */
	public UpdateRecordAssigneesRequest(Integer app, Integer id, ArrayList<String> assignees, Integer revision) {
		this.app = app;
		this.id = id;
		this.assignees = assignees;
		this.revision = revision;
	}

}
