/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class UserSelectionField extends AbstractMemberSelectField {
    /**
     * @param code code of the UserSelectionField
     */
    public UserSelectionField(String code) {
        this.code = code;
        this.type = FieldType.USER_SELECT;
    }
}
