/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record;

public class GetRecordRequest {

	private Integer app;
	private Integer id;

	/**
	 * Constructor
	 * @param app app of the GetRecordRequest
	 * @param id id of the GetRecordRequest
	 */
	public GetRecordRequest(Integer app, Integer id) {
		this.app = app;
		this.id = id;
	}

}
