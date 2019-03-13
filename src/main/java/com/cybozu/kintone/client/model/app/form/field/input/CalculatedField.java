/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.NumberFormat;
import com.cybozu.kintone.client.model.app.form.UnitPosition;

public class CalculatedField extends AbstractInputField {
    private String expression;
    private Boolean hideExpression;
    private String displayScale;
    private String unit;
    private UnitPosition unitPosition;
    private NumberFormat format;

    public CalculatedField(String code) {
        this.code = code;
        this.type = FieldType.CALC;
    }

    /**
     * @return the expression
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * @param expression
     *            the expression to set
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
     * @param hideExpression
     *            the hideExpression to set
     */
    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
    }

    /**
     * @return the displayScale
     */
    public String getDisplayScale() {
        return this.displayScale;
    }

    /**
     * @param displayScale
     *            the displayScale to set
     */
    public void setDisplayScale(String displayScale) {
        this.displayScale = displayScale;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the unitPosition
     */
    public UnitPosition getUnitPosition() {
        return this.unitPosition;
    }

    /**
     * @param unitPosition
     *            the unitPosition to set
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return the format
     */
    public NumberFormat getFormat() {
        return this.format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(NumberFormat format) {
        this.format = format;
    }
}
