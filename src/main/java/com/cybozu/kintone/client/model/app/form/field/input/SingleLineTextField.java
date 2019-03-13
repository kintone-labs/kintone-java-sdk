/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;

public class SingleLineTextField extends AbstractInputField {
    private String expression;
    private Boolean hideExpression;
    private String minLength;
    private String maxLength;
    private String defaultValue;
    private Boolean unique;

    /**
     * @param code code of the SingleLineTextField
     */
    public SingleLineTextField(String code) {
        this.code = code;
        this.type = FieldType.SINGLE_LINE_TEXT;
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
     * @return the expression
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return the hideExpression
     */
    public Boolean getHideExpression() {
        return this.hideExpression;
    }

    /**
     * @param hideExpression the hideExpression to set
     */
    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
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
     * @return unique
     */
    public Boolean getUnique() {
        return this.unique;
    }

    /**
     * @param isUnique the isUnique to set
     */
    public void setUnique(Boolean isUnique) {
        this.unique = isUnique;
    }
}
