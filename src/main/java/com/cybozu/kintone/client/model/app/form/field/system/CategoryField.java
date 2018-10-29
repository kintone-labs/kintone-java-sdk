/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.system;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class CategoryField extends AbstractSystemField {
    protected Boolean enabled;

    /**
     * @param code
     */
    public CategoryField(String code) {
        this.code = code;
        this.type = FieldType.CATEGORY;
    }

    /**
     * @return
     */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
