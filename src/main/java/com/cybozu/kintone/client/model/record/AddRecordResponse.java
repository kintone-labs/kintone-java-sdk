/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

public class AddRecordResponse {

	private Integer id;
	private Integer revision;

	/**
	 * Get record id
	 * @return
	 */
	public Integer getID() {
		return this.id;
	}

	/**
	 * Set record id
	 * @param id
	 */
	public void setID(Integer id) {
		this.id = id;
	}

	/**
	 * Get revision number of record
	 * @return
	 */
	public Integer getRevision() {
		return this.revision;
	}

	/**
	 * Set revision number of record
	 * @param revision
	 */
	public void setRevision(Integer revision) {
		this.revision = revision;
	}

}
