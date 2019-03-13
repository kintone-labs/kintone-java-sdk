/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.layout;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.LayoutType;


public class RowLayout extends ItemLayout {
    private ArrayList<FieldLayout> fields;

    /**
     * default constructor
     */
    public RowLayout() {
        this.type = LayoutType.ROW;
    }

    /**
     * @return the fields
     */
    public ArrayList<FieldLayout> getFields() {
        return this.fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(ArrayList<FieldLayout> fields) {
        this.fields = fields;
    }
}
