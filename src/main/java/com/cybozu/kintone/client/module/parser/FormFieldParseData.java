/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.util.List;
import java.util.Map;

import com.cybozu.kintone.client.model.app.form.AlignLayout;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.LinkProtocol;
import com.cybozu.kintone.client.model.app.form.NumberFormat;
import com.cybozu.kintone.client.model.app.form.UnitPosition;
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupItem;
import com.cybozu.kintone.client.model.app.form.field.input.member.MemberSelectEntity;
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
    private List<MemberSelectEntity> entities;
    private Map<String, OptionData> options;
    private LinkProtocol protocol;
    private AlignLayout align;
    private Object defaultValue;
    private UnitPosition unitPosition;
    private NumberFormat format;
    private JsonObject fields;

    public FormFieldParseData() {

    }

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public FieldType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(FieldType type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return
     */
    public Boolean getNoLabel() {
        return noLabel;
    }

    /**
     * @param noLabel
     */
    public void setNoLabel(Boolean noLabel) {
        this.noLabel = noLabel;
    }

    /**
     * @return
     */
    public Boolean getOpenGroup() {
        return openGroup;
    }

    /**
     * @param openGroup
     */
    public void setOpenGroup(Boolean openGroup) {
        this.openGroup = openGroup;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * @return
     */
    public Boolean getDigit() {
        return digit;
    }

    /**
     * @param digit
     */
    public void setDigit(Boolean digit) {
        this.digit = digit;
    }

    /**
     * @return
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * @param unique
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    /**
     * @return
     */
    public LinkProtocol getProtocol() {
        return protocol;
    }

    /**
     * @param protocol
     */
    public void setProtocol(LinkProtocol protocol) {
        this.protocol = protocol;
    }

    /**
     * @return
     */
    public Map<String, OptionData> getOptions() {
        return options;
    }

    /**
     * @param options
     */
    public void setOptions(Map<String, OptionData> options) {
        this.options = options;
    }

    /**
     * @return
     */
    public AlignLayout getAlign() {
        return align;
    }

    /**
     * @param align
     */
    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    /**
     * @return
     */
    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    /**
     * @param defaultNowValue
     */
    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }

    /**
     * @return
     */
    public List<MemberSelectEntity> getEntities() {
        return entities;
    }

    /**
     * @param entities
     */
    public void setEntities(List<MemberSelectEntity> entities) {
        this.entities = entities;
    }

    /**
     * @return
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return
     */
    public String getMinLength() {
        return minLength;
    }

    /**
     * @param minLength
     */
    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    /**
     * @return
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @param minValue
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    /**
     * @return
     */
    public String getThumbnailSize() {
        return thumbnailSize;
    }

    /**
     * @param thumbnailSize
     */
    public void setThumbnailSize(String thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    /**
     * @return
     */
    public LookupItem getLookup() {
        return lookup;
    }

    /**
     * @param lookup
     */
    public void setLookup(LookupItem lookup) {
        this.lookup = lookup;
    }

    /**
     * @return
     */
    public ReferenceTable getReferenceTable() {
        return referenceTable;
    }

    /**
     * @param referenceTable
     */
    public void setReferenceTable(ReferenceTable referenceTable) {
        this.referenceTable = referenceTable;
    }

    /**
     * @return
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return
     */
    public Boolean getHideExpression() {
        return hideExpression;
    }

    /**
     * @param hideExpression
     */
    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
    }

    /**
     * @return
     */
    public String getDisplayScale() {
        return displayScale;
    }

    /**
     * @param displayScale
     */
    public void setDisplayScale(String displayScale) {
        this.displayScale = displayScale;
    }

    /**
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return
     */
    public UnitPosition getUnitPosition() {
        return unitPosition;
    }

    /**
     * @param unitPosition
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return
     */
    public NumberFormat getFormat() {
        return format;
    }

    /**
     * @param format
     */
    public void setFormat(NumberFormat format) {
        this.format = format;
    }

    /**
     * @param fields
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
