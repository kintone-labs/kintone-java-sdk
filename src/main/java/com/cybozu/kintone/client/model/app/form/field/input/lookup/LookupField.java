/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.lookup;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public class LookupField extends AbstractInputField {
    private LookupItem lookup;

    /**
     * @param code
     * @param type
     */
    public LookupField(String code, FieldType type) {
        this.type = type;
        this.code = code;
    }

    /**
     * @return the lookup
     */
    public LookupItem getLookup() {
        return this.lookup;
    }

    /**
     * @param lookup the lookup to set
     */
    public void setLookup(LookupItem lookup) {
        this.lookup = lookup;
    }


    /**
     * @param type
     *            the type to set
     */
    public void setType(FieldType type) {
        this.type = type;
    }

}
