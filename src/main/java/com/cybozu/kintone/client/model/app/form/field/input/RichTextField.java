/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class RichTextField extends AbstractInputField {
    private String defaultValue;

    /**
     * @param code
     */
    public RichTextField(String code) {
        this.code = code;
        this.type = FieldType.RICH_TEXT;
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
