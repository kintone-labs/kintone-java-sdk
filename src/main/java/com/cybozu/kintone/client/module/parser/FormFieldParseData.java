/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.input.LinkProtocol;
import com.cybozu.kintone.client.model.app.form.field.input.NumberFormat;
import com.cybozu.kintone.client.model.app.form.field.input.UnitPosition;
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupItem;
import com.cybozu.kintone.client.model.app.form.field.input.member_selection.MemberSelectEntity;
import com.cybozu.kintone.client.model.app.form.field.input.selection.AlignLayout;
import com.cybozu.kintone.client.model.app.form.field.input.selection.OptionData;
import com.cybozu.kintone.client.model.app.form.field.related_record.ReferenceTable;
import com.google.gson.JsonObject;

public class FormFieldParseData {
    private String code;
    private FieldType type;
    private String label;
    private String expression;
    private String unit;
    private Boolean noLabel;
    private Boolean openGroup;
    private Boolean enabled;
    private Boolean required;
    private Boolean digit;
    private Boolean unique;
    private Boolean hideExpression;
    private Boolean defaultNowValue;
    private String maxLength;
    private String minLength;
    private String maxValue;
    private String minValue;
    private String thumbnailSize;
    private String displayScale;
    private LookupItem lookup;
    private ReferenceTable referenceTable;
    private ArrayList<MemberSelectEntity> entities;
    private HashMap<String, OptionData> options;
    private LinkProtocol protocol;
    private AlignLayout align;
    private Object defaultValue;
    private UnitPosition unitPosition;
    private NumberFormat format;
    private JsonObject fields;

    public FormFieldParseData() {

    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return type
     */
    public FieldType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FieldType type) {
        this.type = type;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the lable to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return noLabel
     */
    public Boolean getNoLabel() {
        return noLabel;
    }

    /**
     * @param noLabel the noLabel to set
     */
    public void setNoLabel(Boolean noLabel) {
        this.noLabel = noLabel;
    }

    /**
     * @return openGroup
     */
    public Boolean getOpenGroup() {
        return openGroup;
    }

    /**
     * @param openGroup the openGroup to set
     */
    public void setOpenGroup(Boolean openGroup) {
        this.openGroup = openGroup;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * @return digit
     */
    public Boolean getDigit() {
        return digit;
    }

    /**
     * @param digit the digit to set
     */
    public void setDigit(Boolean digit) {
        this.digit = digit;
    }

    /**
     * @return unique
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * @return protocol
     */
    public LinkProtocol getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(LinkProtocol protocol) {
        this.protocol = protocol;
    }

    /**
     * @return options
     */
    public HashMap<String, OptionData> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(HashMap<String, OptionData> options) {
        this.options = options;
    }

    /**
     * @return align
     */
    public AlignLayout getAlign() {
        return align;
    }

    /**
     * @param align the align to set
     */
    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    /**
     * @return defaultNowValue
     */
    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    /**
     * @param defaultNowValue the defaultNowValue to set
     */
    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }

    /**
     * @return entities
     */
    public ArrayList<MemberSelectEntity> getEntities() {
        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(ArrayList<MemberSelectEntity> entities) {
        this.entities = entities;
    }

    /**
     * @return maxLength
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return minLength
     */
    public String getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    /**
     * @return maxValue
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return minValue
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    /**
     * @return thumbnailSize
     */
    public String getThumbnailSize() {
        return thumbnailSize;
    }

    /**
     * @param thumbnailSize the thumbnailSize to set
     */
    public void setThumbnailSize(String thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    /**
     * @return lookup
     */
    public LookupItem getLookup() {
        return lookup;
    }

    /**
     * @param lookup the lookup to set
     */
    public void setLookup(LookupItem lookup) {
        this.lookup = lookup;
    }

    /**
     * @return referenceTable
     */
    public ReferenceTable getReferenceTable() {
        return referenceTable;
    }

    /**
     * @param referenceTable the referenceTable to set
     */
    public void setReferenceTable(ReferenceTable referenceTable) {
        this.referenceTable = referenceTable;
    }

    /**
     * @return defaultValue
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return hideExpression
     */
    public Boolean getHideExpression() {
        return hideExpression;
    }

    /**
     * @param hideExpression the hideExpression to set
     */
    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
    }

    /**
     * @return displayScale
     */
    public String getDisplayScale() {
        return displayScale;
    }

    /**
     * @param displayScale the displayScale to set
     */
    public void setDisplayScale(String displayScale) {
        this.displayScale = displayScale;
    }

    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return unitPosition
     */
    public UnitPosition getUnitPosition() {
        return unitPosition;
    }

    /**
     * @param unitPosition the unitPosition to set
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return format
     */
    public NumberFormat getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(NumberFormat format) {
        this.format = format;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(JsonObject fields) {
        this.fields = fields;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @return the fields
     */
    public JsonObject getFields() {
        return fields;
    }
}
