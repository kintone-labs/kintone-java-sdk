/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field;

import java.util.HashMap;
import java.util.Map;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class SubTableField extends Field {
    protected Map<String, AbstractInputField> fields = new HashMap<String, AbstractInputField>();

    /**
     * default constructor
     */
    public SubTableField() {
        this.type = FieldType.SUBTABLE;
    }

    /**
     * @param code
     */
    public SubTableField(String code) {
        this.code = code;
        this.type = FieldType.SUBTABLE;
    }

    /**
     * @return the fields
     */
    public Map<String, AbstractInputField> getFields() {
        return this.fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(Map<String, AbstractInputField> fields) {
        this.fields = fields;
    }

    /**
     * @param field
     */
    public void addField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }

        fields.put(field.getCode(), field);
    }

    /**
     * @param field
     */
    public void removeField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }
        fields.remove(field.getCode());
    }
}
