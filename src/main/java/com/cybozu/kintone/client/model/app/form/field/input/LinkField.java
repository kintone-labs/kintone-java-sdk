/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.LinkProtocol;

public class LinkField extends AbstractInputField {
    private String defaultValue;
    private Boolean unique;
    private String maxLength;
    private String minLength;
    private LinkProtocol protocol;

    /**
     * @param code code of the LinkField
     */
    public LinkField(String code) {
        this.code = code;
        this.type = FieldType.LINK;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the unique
     */
    public Boolean getUnique() {
        return this.unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * @return the maxLength
     */
    public String getMaxLength() {
        return this.maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the minLength
     */
    public String getMinLength() {
        return this.minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the protocol
     */
    public LinkProtocol getProtocol() {
        return this.protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(LinkProtocol protocol) {
        this.protocol = protocol;
    }
}
