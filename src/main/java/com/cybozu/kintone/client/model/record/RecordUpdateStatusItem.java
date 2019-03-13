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
	 * @param action action of the RecordUpdateStatusItem
	 * @param assignee assignee of the RecordUpdateStatusItem
	 * @param id id of the RecordUpdateStatusItem
	 * @param revision revision of the RecordUpdateStatusItem
	 */
	public RecordUpdateStatusItem(String action, String assignee, Integer id, Integer revision) {
		this.action = action;
		this.assignee = assignee;
		this.id = id;
		this.revision = revision;
	}

}
