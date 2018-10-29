/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.LayoutType;


public class RowLayout extends ItemLayout {
    private List<FieldLayout> fields = new ArrayList<FieldLayout>();

    /**
     * default constructor
     */
    public RowLayout() {
        this.type = LayoutType.ROW;
    }

    /**
     * @return the fields
     */
    public List<FieldLayout> getFields() {
        return this.fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(List<FieldLayout> fields) {
        this.fields = fields;
    }
}
