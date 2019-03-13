/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.time;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class TimeField extends AbstractInputField {
    protected String defaultValue;
    protected Boolean defaultNowValue;

    /**
     * @param code code of the TimeField
     */
    public TimeField(String code) {
        this.code = code;
        this.type = FieldType.TIME;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the defaultNowValue
     */
    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    /**
     * @param defaultNowValue the defaultNowValue to set
     */
    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }
}
