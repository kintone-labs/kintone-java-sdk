/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class GroupSelectionField extends AbstractMemberSelectField {
    /**
     * default constructor
     */
    public GroupSelectionField() {
        this.type = FieldType.GROUP_SELECT;
    }

    /**
     * @param code
     */
    public GroupSelectionField(String code) {
        this.code = code;
        this.type = FieldType.GROUP_SELECT;
    }
}
