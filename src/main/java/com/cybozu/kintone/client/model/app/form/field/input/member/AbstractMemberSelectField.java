/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input.member;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;

public abstract class AbstractMemberSelectField extends AbstractInputField {
    protected ArrayList<MemberSelectEntity> defaultValue;
    protected ArrayList<MemberSelectEntity> entities;

    /**
     * @return the defaultValue
     */
    public ArrayList<MemberSelectEntity> getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(ArrayList<MemberSelectEntity> defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the entities
     */
    public ArrayList<MemberSelectEntity> getEntities() {
        return this.entities;
    }

    /**
     * @param entities
     *            the entities to set
     */
    public void setEntities(ArrayList<MemberSelectEntity> entities) {
        this.entities = entities;
    }
}
