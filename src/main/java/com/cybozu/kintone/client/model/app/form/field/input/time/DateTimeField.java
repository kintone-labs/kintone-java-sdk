/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.time;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class DateTimeField extends AbstractInputField {
    protected Boolean unique;
    protected String defaultValue;
    protected Boolean defaultNowValue;

    /**
     * @param code
     */
    public DateTimeField(String code) {
        this.code = code;
        this.type = FieldType.DATETIME;
    }

    /**
     * @return the unique
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return
     */
    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    /**
     * @param defaultNowValue
     */
    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }
}
