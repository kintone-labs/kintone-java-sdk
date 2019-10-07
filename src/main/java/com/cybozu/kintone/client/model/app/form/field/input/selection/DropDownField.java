/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class DropDownField extends AbstractSelectionField {
    protected String defaultValue;

    /**
     * @param code code of the DropDownField
     */
    public DropDownField(String code) {
        this.code = code;
        this.type = FieldType.DROP_DOWN;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
