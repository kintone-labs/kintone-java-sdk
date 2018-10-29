/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class DeleteRecordsRequest {

	private Integer app;
	private ArrayList<Integer> ids;
	private ArrayList<Integer> revisions;

	/**
	 * Constructor
	 * @param app
	 * @param ids
	 * @param revision
	 */
	public DeleteRecordsRequest(Integer app, ArrayList<Integer> ids, ArrayList<Integer> revisions) {
		this.app = app;
		this.ids = ids;
		this.revisions = revisions;
	}

}
