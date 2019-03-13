/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.basic.request;

import java.util.HashMap;

import com.cybozu.kintone.client.model.app.form.field.Field;

public class AddUpdateFormFieldsRequest {
    private Integer app;
    private HashMap<String, Field> properties;
    private Integer revision;

    public AddUpdateFormFieldsRequest(Integer app, HashMap<String, Field> properties, Integer revision) {
        this.app = app;
        this.properties = properties;
        this.revision = revision;
    }

    public Integer getApp() {
        return this.app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public HashMap<String, Field> getProperties() {
        return this.properties;
    }

    public void setProperties(HashMap<String, Field> properties) {
        this.properties = properties;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

}
