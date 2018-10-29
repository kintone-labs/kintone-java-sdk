/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member;

import java.util.ArrayList;
import java.util.List;

import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public abstract class AbstractMemberSelectField extends AbstractInputField {
    protected List<MemberSelectEntity> defaultValue = new ArrayList<MemberSelectEntity>();
    protected List<MemberSelectEntity> entites = new ArrayList<MemberSelectEntity>();

    /**
     * @return the defaultValue
     */
    public List<MemberSelectEntity> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(List<MemberSelectEntity> defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the entites
     */
    public List<MemberSelectEntity> getEntites() {
        return this.entites;
    }

    /**
     * @param entites
     *            the entites to set
     */
    public void setEntites(List<MemberSelectEntity> entites) {
        this.entites = entites;
    }
}
