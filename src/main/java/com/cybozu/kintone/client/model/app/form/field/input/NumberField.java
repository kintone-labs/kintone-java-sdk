/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.form.field.input;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.UnitPosition;

public class NumberField extends AbstractInputField {
    private Integer displayScale;
    private String unit;
    private UnitPosition unitPosition;
    private Boolean digit;
    private Integer maxValue;
    private Integer minValue;
    private String defaultValue;
    private Boolean unique;

    /**
     * @param code
     */
    public NumberField(String code) {
        this.code = code;
        this.type = FieldType.NUMBER;
    }

    /**
     * @return the displayScale
     */
    public Integer getDisplayScale() {
        return this.displayScale;
    }

    /**
     * @param displayScale the displayScale to set
     */
    public void setDisplayScale(Integer displayScale) {
        this.displayScale = displayScale;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * @param unit the unit to set
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
     * @param unitPosition the unitPosition to set
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return the digit
     */
    public Boolean getDigit() {
        return this.digit;
    }

    /**
     * @param digit the digit to set
     */
    public void setDigit(Boolean digit) {
        this.digit = digit;
    }

    /**
     * @return the maxValue
     */
    public Integer getMaxValue() {
        return this.maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public Integer getMinValue() {
        return this.minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
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
     * @return
     */
    public Boolean getUnique() {
        return this.unique;
    }

    /**
     * @param unique
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }
}
