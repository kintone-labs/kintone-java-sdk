/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field;

import java.util.HashMap;

public class FormFields {
    private Integer app;
    private Integer revision;
    private HashMap<String, Field> properties;

    /**
     * default constructor
     */
    public FormFields() {
        app = null;
        revision = null;
        properties = new HashMap<String, Field>();
    }

    /**
     * @param app app of the FormFields
     * @param properties properties of the FormFields
     * @param revision revision of the FormFields
     */
    public FormFields(Integer app, HashMap<String, Field> properties, Integer revision) {
        this.app = app;
        this.revision = revision;
        this.properties = properties;
    }

    /**
     * @return the app
     */
    public Integer getApp() {
        return this.app;
    }

    /**
     * @param app
     *            the app to set
     */
    public void setApp(Integer app) {
        this.app = app;
    }

    /**
     * @return the revision
     */
    public Integer getRevision() {
        return this.revision;
    }

    /**
     * @param revision
     *            the revision to set
     */
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    /**
     * @return the properties
     */
    public HashMap<String, Field> getProperties() {
        return this.properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(HashMap<String, Field> properties) {
        this.properties = properties;
    }
}
