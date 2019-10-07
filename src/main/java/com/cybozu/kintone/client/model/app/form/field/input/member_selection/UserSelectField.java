/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member_selection;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class UserSelectField extends AbstractMemberSelectField {
    /**
     * @param code code of the UserSelectionField
     */
    public UserSelectField(String code) {
        this.code = code;
        this.type = FieldType.USER_SELECT;
    }
}
