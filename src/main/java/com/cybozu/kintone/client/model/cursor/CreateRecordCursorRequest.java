package com.cybozu.kintone.client.model.cursor;

import java.util.ArrayList;

public class CreateRecordCursorRequest {
	private Integer app;
    private ArrayList<String> fields;
    private String query;
    private Integer size;

    public CreateRecordCursorRequest(Integer app, ArrayList<String> fields, String query, Integer size) {
        this.app = app;
        this.fields = fields;
        this.query = query;
        this.size = size;
    }

}
