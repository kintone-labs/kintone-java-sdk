/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

import java.util.ArrayList;

public class AddRecordsResponse {

	private ArrayList<Integer> ids;
	private ArrayList<Integer> revisions;

	/**
	 * Get record ids
	 * @return
	 */
	public ArrayList<Integer> getIDs() {
		return this.ids;
	}

	/**
	 * Set record ids
	 * @param id
	 */
	public void setIDs(ArrayList<Integer> ids) {
		this.ids = ids;
	}

	/**
	 * Get revision number of records
	 * @return
	 */
	public ArrayList<Integer> getRevisions() {
		return this.revisions;
	}

	/**
	 * Set revision number of records
	 * @param revision
	 */
	public void setRevisions(ArrayList<Integer> revisions) {
		this.revisions = revisions;
	}

}
