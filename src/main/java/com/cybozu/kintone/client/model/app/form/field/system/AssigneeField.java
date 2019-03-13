/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.system;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class AssigneeField extends AbstractProcessManagementField {
    /**
     * @param code code of the AssigneeField
     */
    public AssigneeField(String code) {
        this.code = code;
        this.type = FieldType.STATUS_ASSIGNEE;
    }
}
