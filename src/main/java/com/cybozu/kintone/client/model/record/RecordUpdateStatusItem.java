/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

public class RecordUpdateStatusItem {

	private String action;
	private String assignee;
	private Integer id;
	private Integer revision;

	/**
	 * Constructor
	 * @param action
	 * @param assignee
	 * @param id
	 * @param revision
	 */
	public RecordUpdateStatusItem(String action, String assignee, Integer id, Integer revision) {
		this.action = action;
		this.assignee = assignee;
		this.id = id;
		this.revision = revision;
	}

}
