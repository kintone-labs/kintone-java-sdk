package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

public class DeleteFormFieldsRequest {
    private Integer app;
    private ArrayList<String> fields;
    private Integer revision;

    public Integer getApp() {
        return this.app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public ArrayList<String> getFields() {
        return this.fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public DeleteFormFieldsRequest(Integer app, ArrayList<String> fields, Integer revision) {
        this.app = app;
        this.fields = fields;
        this.revision = revision;
    }
}
