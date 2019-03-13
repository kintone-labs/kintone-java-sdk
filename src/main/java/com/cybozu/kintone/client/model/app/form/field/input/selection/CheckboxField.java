/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.AlignLayout;
import com.cybozu.kintone.client.model.app.form.FieldType;

public class CheckboxField extends AbstractSelectionField {
    protected ArrayList<String> defaultValue;
    protected AlignLayout align;

    /**
     * @param code code of the CheckboxField
     */
    public CheckboxField(String code) {
        this.code = code;
        this.type = FieldType.CHECK_BOX;
    }

    /**
     * @return the align
     */
    public AlignLayout getAlign() {
        return this.align;
    }

    /**
     * @param align
     *            the align to set
     */
    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    /**
     * @return defaultValue
     */
    public ArrayList<String> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(ArrayList<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
