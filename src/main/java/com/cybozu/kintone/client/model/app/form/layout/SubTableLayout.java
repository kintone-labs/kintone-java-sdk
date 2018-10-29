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

public class SubTableLayout extends ItemLayout {
    private String code;
    private List<FieldLayout> fields = new ArrayList<FieldLayout>();

    /**
     * default constructor
     */
    public SubTableLayout() {
        this.type = LayoutType.SUBTABLE;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the fields
     */
    public List<FieldLayout> getFields() {
        return this.fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<FieldLayout> fields) {
        this.fields = fields;
    }
}
