/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.selection;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.AlignLayout;
import com.cybozu.kintone.client.model.app.form.FieldType;

public class CheckboxField extends AbstractSelectionField {
    protected List<String> defaultValue = new ArrayList<String>();
    protected AlignLayout align;

    /**
     * default constructor
     */
    public CheckboxField() {
        this.type = FieldType.CHECK_BOX;
    }

    /**
     * @param code
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
     * @return
     */
    public List<String> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue
     */
    public void setDefaultValue(List<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
