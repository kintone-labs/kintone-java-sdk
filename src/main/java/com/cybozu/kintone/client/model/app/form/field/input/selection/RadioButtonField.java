/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class RadioButtonField extends AbstractSelectionField {
    protected String defaultValue;
    protected AlignLayout align;

    /**
     * @param code code of the RadioButtonField
     */
    public RadioButtonField(String code) {
        this.code = code;
        this.type = FieldType.RADIO_BUTTON;
    }

    /**
     * @return the align
     */
    public AlignLayout getAlign() {
        return this.align;
    }

    /**
     * @param align the align to set
     */
    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    /**
     * @return defaultValue
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
