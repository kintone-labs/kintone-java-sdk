/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field;

import java.util.HashMap;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class SubTableField extends Field {
    protected HashMap<String, AbstractInputField> fields;

    /**
     * @param code the code of the SubTableField
     */
    public SubTableField(String code) {
        this.code = code;
        this.type = FieldType.SUBTABLE;
    }

    /**
     * @return the fields
     */
    public HashMap<String, AbstractInputField> getFields() {
        return this.fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(HashMap<String, AbstractInputField> fields) {
        this.fields = fields;
    }

    /**
     * @param field AbstractInputField
     */
    public void addField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }
        if (this.fields == null) {
        	this.fields = new HashMap<String, AbstractInputField>();
        }
        this.fields.put(field.getCode(), field);
    }

    /**
     * @param field AbstractInputField
     */
    public void removeField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }
        fields.remove(field.getCode());
    }
}
