/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.response;

public class RecordUpdateResponseItem {

	private Integer id;
	private Integer revision;

	/**
	 * Get record id
	 * @return id
	 */
	public Integer getID() {
		return this.id;
	}

	/**
	 * Set record id
	 * @param id the id to set
	 */
	public void setID(Integer id) {
		this.id = id;
	}

	/**
	 * Get revision number of the record
	 * @return revision
	 */
	public Integer getRevision() {
		return this.revision;
	}

	/**
	 * Set revision number of the record
	 * @param revision the revision to set
	 */
	public void setRevision(Integer revision) {
		this.revision = revision;
	}

}
