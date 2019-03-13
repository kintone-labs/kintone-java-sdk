/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class MultipleSelectField extends AbstractSelectionField {
    protected ArrayList<String> defaultValue;

    /**
     * @param code code of the MultipleSelectField
     */
    public MultipleSelectField(String code) {
        this.code = code;
        this.type = FieldType.MULTI_SELECT;
    }

    /**
     * @return the defaultValue
     */
    public ArrayList<String> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(ArrayList<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
