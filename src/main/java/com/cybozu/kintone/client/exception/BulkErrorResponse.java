/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.exception;

import java.util.ArrayList;

import com.cybozu.kintone.client.exception.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;;

public class BulkErrorResponse {
	private ArrayList<Object> results;

	/**
	 * Constructor
	 */
	public BulkErrorResponse() {
		this.results = new ArrayList<Object>();
	}

	public BulkErrorResponse(String resultJsonString) {
		setResults(resultJsonString);
	}

	/**
	 * Get result
	 * 
	 * @return results
	 */
	public ArrayList<Object> getResults() {
		return results;
	}

	public void setResults(String resultJsonString) {
		JsonElement jsonElement = new JsonParser().parse(resultJsonString);
		JsonObject jsonObject1 = jsonElement.getAsJsonObject();
		JsonArray jarray = jsonObject1.getAsJsonArray("results");

		ArrayList<Object> results = new ArrayList<>();
		if (jarray != null) {
			for (JsonElement elementResponse : jarray) {
				if (elementResponse.getAsJsonObject().entrySet().isEmpty()) {
					results.add(null);
				} else if (elementResponse.getAsJsonObject().get("code") != null) {
					results.add(new Gson().fromJson(elementResponse, ErrorResponse.class));
				} else {
					results.add(null);
				}
			}
		}
		this.results = results;
	}
}
