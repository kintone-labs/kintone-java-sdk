/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class MultipleSelectField extends AbstractSelectionField {
    protected List<String> defaultValue = new ArrayList<String>();

    /**
     * @param code
     */
    public MultipleSelectField(String code) {
        this.code = code;
        this.type = FieldType.MULTI_SELECT;
    }

    /**
     * @return the defaultValue
     */
    public List<String> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(List<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
