/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import java.util.ArrayList;
import java.util.List;

public class FormLayout {
    private String revision;
    private List<ItemLayout> layout = new ArrayList<ItemLayout>();

    /**
     * default constructor
     */
    public FormLayout() {

    }

    /**
     * @return the revision
     */
    public String getRevision() {
        return this.revision;
    }

    /**
     * @param revision the revision to set
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return the layout
     */
    public List<ItemLayout> getLayout() {
        return this.layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(List<ItemLayout> layout) {
        this.layout = layout;
    }
}
