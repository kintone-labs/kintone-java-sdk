/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FieldGroup;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.field.SubTableField;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;
import com.cybozu.kintone.client.model.app.form.field.input.AttachmentField;
import com.cybozu.kintone.client.model.app.form.field.input.LinkField;
import com.cybozu.kintone.client.model.app.form.field.input.MultiLineTextField;
import com.cybozu.kintone.client.model.app.form.field.input.NumberField;
import com.cybozu.kintone.client.model.app.form.field.input.RichTextField;
import com.cybozu.kintone.client.model.app.form.field.input.SingleLineTextField;
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupField;
import com.cybozu.kintone.client.model.app.form.field.input.member.DepartmentSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.GroupSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.MemberSelectEntity;
import com.cybozu.kintone.client.model.app.form.field.input.member.UserSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.CheckboxField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.DropDownField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.MultipleSelectField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.RadioButtonField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateTimeField;
import com.cybozu.kintone.client.model.app.form.field.input.time.TimeField;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedRecordsField;
import com.cybozu.kintone.client.model.app.form.field.system.AssigneeField;
import com.cybozu.kintone.client.model.app.form.field.system.CategoryField;
import com.cybozu.kintone.client.model.app.form.field.system.CreatedTimeField;
import com.cybozu.kintone.client.model.app.form.field.system.CreatorField;
import com.cybozu.kintone.client.model.app.form.field.system.ModifierField;
import com.cybozu.kintone.client.model.app.form.field.system.RecordNumberField;
import com.cybozu.kintone.client.model.app.form.field.system.StatusField;
import com.cybozu.kintone.client.model.app.form.field.system.UpdatedTimeField;
import com.cybozu.kintone.client.model.member.MemberSelectEntityType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FormFieldParser extends Parser {

    /**
     * Convert json to FormFields class
     * @param root root of the parse
     * @return FormFields
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FormFields parse(JsonElement root) throws KintoneAPIException {
        if (!root.isJsonObject()) {
            throw new KintoneAPIException("Input is not a json object type");
        }
        FormFields formFields = new FormFields();

        JsonObject rootObject = root.getAsJsonObject();
        formFields.setRevision(rootObject.get("revision").getAsInt());
        formFields.setProperties(parseProperties(rootObject.get("properties")));
        return formFields;
    }

    /**
     * Convert json to HashMap<String, Field> class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    private HashMap<String, Field> parseProperties(JsonElement input) throws KintoneAPIException {
        HashMap<String, Field> result = new HashMap<String, Field>();
        if (!input.isJsonObject()) {
            return result;
        }

        Set<HashMap.Entry<String, JsonElement>> entries = input.getAsJsonObject().entrySet();
        for (HashMap.Entry<String, JsonElement> entry : entries) {
            Field formField = parseFormField(entry.getValue());
            if (formField != null) {
                result.put(formField.getCode(), formField);
            }
        }
        return result;
    }

    /**
     * Convert json to field class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    private Field parseFormField(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            return null;
        }
        FormFieldParseData data = null;
        try {
            data = gson.fromJson(input, FormFieldParseData.class);
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type",e );
        }

        if (data == null) {
            throw new KintoneAPIException("Invalid data form stucture");
        }

        if (data.getCode() == null || data.getCode().trim().length() == 0) {
            throw new KintoneAPIException("Missing code when parse form field");
        }

        if (data.getType() == null) {
            throw new KintoneAPIException("Missing type when parse form field");
        }

        Field formField = null;
        // Parse Lookup first to avoid confusing with NUMBER or TEXT type.
        if (data.getLookup() != null) {
            formField =  parseInputField(input);
        } else {
            switch(data.getType()) {
                case REFERENCE_TABLE:
                    formField = parseRelatedRecordType(data);
                    break;
                case GROUP:
                    formField = parseFieldGroupType(data);
                    break;
                case CATEGORY:
                    formField = parseCategoryType(data);
                    break;
                case SUBTABLE:
                    formField = parseSubTableType(data);
                    break;
                case CREATOR:
                    formField = parseCreatorType(data);
                    break;
                case RECORD_NUMBER:
                    formField = parseRecordNumberType(data);
                    break;
                case MODIFIER:
                    formField = parseUpdaterType(data);
                    break;
                case CREATED_TIME:
                    formField = parseCreatedDateTimeType(data);
                    break;
                case UPDATED_TIME:
                    formField = parseUpdatedDateTimeType(data);
                    break;
                case STATUS:
                    formField = parseStatusType(data);
                    break;
                case STATUS_ASSIGNEE:
                    formField = parseAssigneeType(data);
                    break;
                default:
                    formField = parseInputField(input);
                    break;
            }
        }

        return formField;
    }

    /**
     * Convert json to AbstractInputField class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    private AbstractInputField parseInputField(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            return null;
        }

        FormFieldParseData data = null;
        try {
            data = gson.fromJson(input, FormFieldParseData.class);
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type", e);
        }

        if (data == null) {
            throw new KintoneAPIException("Invalid data form stucture");
        }

        if (data.getCode() == null || data.getCode().trim().length() == 0) {
            throw new KintoneAPIException("Missing code when parse form field");
        }

        if (data.getType() == null) {
            throw new KintoneAPIException("Missing type when parse form field");
        }

        AbstractInputField inputField = null;

        // Parse Lookup first to avoid confusing with NUMBER or TEXT type.
        if (data.getLookup() != null) {
            inputField = parseLookupType(data);
        } else {
            switch(data.getType()) {
                case NUMBER:
                    inputField = parseNumberType(data);
                    break;
                case RICH_TEXT:
                    inputField = parseRichTextType(data);
                    break;
                case LINK:
                    inputField = parseLinkType(data);
                    break;
                case TIME:
                    inputField = parseTimeFieldType(data);
                    break;
                case DATE:
                    inputField = parseDateFieldType(data);
                    break;
                case DATETIME:
                    inputField = parseDateTimeFieldType(data);
                    break;
                case FILE:
                    inputField = parseAttachmentType(data);
                    break;
                case SINGLE_LINE_TEXT:
                    inputField = parseSingleLineTextType(data);
                    break;
                case MULTI_LINE_TEXT:
                    inputField = parseMultiLineTextType(data);
                    break;
                case DROP_DOWN:
                    inputField = parseDropDownTextType(data);
                    break;
                case CHECK_BOX:
                    inputField = parseCheckboxTextType(data);
                    break;
                case RADIO_BUTTON:
                    inputField = parseRadioButtonType(data);
                    break;
                case MULTI_SELECT:
                    inputField = parseMultiSelectTextType(data);
                    break;
                case USER_SELECT:
                    inputField = parseUserSelectionType(data);
                    break;
                case GROUP_SELECT:
                    inputField = parseGroupSelectionType(data);
                    break;
                case ORGANIZATION_SELECT:
                    inputField = parseDepartmentSelectionType(data);
                    break;
                default:
                    break;
            }
        }

        if (inputField != null) {
            inputField.setLabel(data.getLabel());
            inputField.setNoLabel(data.getNoLabel());
            inputField.setRequired(data.getRequired());
        }

        return inputField;
    }

    /**
     * Convert FormFieldParseData to NumberField class
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private NumberField parseNumberType(FormFieldParseData data) throws KintoneAPIException {
        NumberField number = new NumberField(data.getCode());

        if (data.getDefaultValue() == null ) {
            number.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            number.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        number.setMaxValue(data.getMaxValue());
        number.setMinValue(data.getMinValue());
        number.setDisplayScale(data.getDisplayScale());
        number.setDigit(data.getDigit());
        number.setUnit(data.getUnit());
        number.setUnitPosition(data.getUnitPosition());
        number.setUnique(data.getUnique());

        return number;
    }

    /**
     * convert String to Integer
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    private Integer parseInteger(String input) throws KintoneAPIException {
        if (input == null || input.trim().length() == 0) {
            return null;
        }
        Integer result = null;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new KintoneAPIException("Invalid data type", e);
        }

        return result;
    }


    /**
     * Convert FormFieldParseData to LookupField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private LookupField parseLookupType(FormFieldParseData data) throws KintoneAPIException {
        LookupField lookup = new LookupField(data.getCode(), data.getType());
        lookup.setLookup(data.getLookup());

        return lookup;
    }

    /**
     * Convert FormFieldParseData to CreatorField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private CreatorField parseCreatorType(FormFieldParseData data) throws KintoneAPIException {
        CreatorField creator = new CreatorField(data.getCode());
        creator.setLabel(data.getLabel());
        creator.setNoLabel(data.getNoLabel());

        return creator;
    }

    /**
     * Convert FormFieldParseData to RecordNumberField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private RecordNumberField parseRecordNumberType(FormFieldParseData data) throws KintoneAPIException {
        RecordNumberField recordNumber = new RecordNumberField(data.getCode());
        recordNumber.setLabel(data.getLabel());
        recordNumber.setNoLabel(data.getNoLabel());

        return recordNumber;
    }

    /**
     * Convert FormFieldParseData to CreatedTimeField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private CreatedTimeField parseCreatedDateTimeType(FormFieldParseData data) throws KintoneAPIException {
        CreatedTimeField createdDateTime = new CreatedTimeField(data.getCode());
        createdDateTime.setLabel(data.getLabel());
        createdDateTime.setNoLabel(data.getNoLabel());

        return createdDateTime;
    }

    /**
     * Convert FormFieldParseData to ModifierField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private ModifierField parseUpdaterType(FormFieldParseData data) throws KintoneAPIException {
        ModifierField updater = new ModifierField(data.getCode());
        updater.setLabel(data.getLabel());
        updater.setNoLabel(data.getNoLabel());

        return updater;
    }

    /**
     * Convert FormFieldParseData to UpdatedTimeField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private UpdatedTimeField parseUpdatedDateTimeType(FormFieldParseData data) throws KintoneAPIException {
        UpdatedTimeField updatedDateTime = new UpdatedTimeField(data.getCode());
        updatedDateTime.setLabel(data.getLabel());
        updatedDateTime.setNoLabel(data.getNoLabel());

        return updatedDateTime;
    }

    /**
     * Convert FormFieldParseData to StatusField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private StatusField parseStatusType(FormFieldParseData data) throws KintoneAPIException {
        StatusField status = new StatusField(data.getCode());
        status.setLabel(data.getLabel());
        status.setEnabled(data.getEnabled());

        return status;
    }

    /**
     * Convert FormFieldParseData to AssigneeField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private AssigneeField parseAssigneeType(FormFieldParseData data) throws KintoneAPIException {
        AssigneeField assignee = new AssigneeField(data.getCode());
        assignee.setLabel(data.getLabel());
        assignee.setEnabled(data.getEnabled());

        return assignee;
    }

    /**
     * Convert FormFieldParseData to CategoryField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private CategoryField parseCategoryType(FormFieldParseData data) throws KintoneAPIException {
        CategoryField category = new CategoryField(data.getCode());
        category.setLabel(data.getLabel());
        category.setEnabled(data.getEnabled());

        return category;
    }

    /**
     * Convert FormFieldParseData to RichTextField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private RichTextField parseRichTextType(FormFieldParseData data) throws KintoneAPIException {
        RichTextField richText = new RichTextField(data.getCode());

        if (data.getDefaultValue() == null ) {
            richText.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            richText.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return richText;
    }

    /**
     * Convert FormFieldParseData to LinkField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private LinkField parseLinkType(FormFieldParseData data) throws KintoneAPIException {
        LinkField link = new LinkField(data.getCode());

        link.setProtocol(data.getProtocol());
        link.setMinLength(data.getMinLength());
        link.setMaxLength(data.getMaxLength());
        link.setUnique(data.getUnique());

        if (data.getDefaultValue() == null ) {
            link.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            link.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return link;
    }

    /**
     * Convert FormFieldParseData to TimeField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private TimeField parseTimeFieldType(FormFieldParseData data) throws KintoneAPIException {
        TimeField time = new TimeField(data.getCode());

        if (data.getDefaultValue() == null ) {
            time.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            time.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }
        time.setDefaultNowValue(data.getDefaultNowValue());

        return time;
    }

    /**
     * Convert FormFieldParseData to DateField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private DateField parseDateFieldType(FormFieldParseData data) throws KintoneAPIException {
        DateField date = new DateField(data.getCode());

        if (data.getDefaultValue() == null ) {
            date.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            date.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }
        date.setDefaultNowValue(data.getDefaultNowValue());
        date.setUnique(data.getUnique());

        return date;
    }

    /**
     * Convert FormFieldParseData to DateTimeField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private DateTimeField parseDateTimeFieldType(FormFieldParseData data) throws KintoneAPIException {
        DateTimeField datetime = new DateTimeField(data.getCode());

        if (data.getDefaultValue() == null ) {
            datetime.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            datetime.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }
        datetime.setDefaultNowValue(data.getDefaultNowValue());
        datetime.setUnique(data.getUnique());

        return datetime;
    }

    /**
     * Convert FormFieldParseData to FieldGroup
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private FieldGroup parseFieldGroupType(FormFieldParseData data) throws KintoneAPIException {
        FieldGroup fieldGroup = new FieldGroup(data.getCode());

        fieldGroup.setLabel(data.getLabel());
        fieldGroup.setNoLabel(data.getNoLabel());
        fieldGroup.setOpenGroup(data.getOpenGroup());

        return fieldGroup;
    }

    /**
     * Convert FormFieldParseData to AttachmentField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private AttachmentField parseAttachmentType(FormFieldParseData data) throws KintoneAPIException {
        AttachmentField attachment = new AttachmentField(data.getCode());

        attachment.setThumbnailSize(data.getThumbnailSize());

        return attachment;
    }

    /**
     * Convert FormFieldParseData to SingleLineTextField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private SingleLineTextField parseSingleLineTextType(FormFieldParseData data) throws KintoneAPIException {
        SingleLineTextField text = new SingleLineTextField(data.getCode());

        text.setExpression(data.getExpression());
        text.setHideExpression(data.getHideExpression());
        text.setUnique(data.getUnique());

        if (data.getDefaultValue() == null ) {
            text.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            text.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }
        return text;
    }

    /**
     * Convert FormFieldParseData to MultiLineTextField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private MultiLineTextField parseMultiLineTextType(FormFieldParseData data) throws KintoneAPIException {
        MultiLineTextField textArea = new MultiLineTextField(data.getCode());

        if (data.getDefaultValue() == null ) {
            textArea.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            textArea.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }
        return textArea;
    }

    /**
     * Convert FormFieldParseData to DropDownField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private DropDownField parseDropDownTextType(FormFieldParseData data) throws KintoneAPIException {
        DropDownField dropDown = new DropDownField(data.getCode());

        dropDown.setOptions(data.getOptions());

        if (data.getDefaultValue() == null ) {
            dropDown.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            dropDown.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return dropDown;
    }

    /**
     * Convert FormFieldParseData to  CheckboxField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    @SuppressWarnings("unchecked")
    private CheckboxField parseCheckboxTextType(FormFieldParseData data) throws KintoneAPIException {
        CheckboxField checkbox = new CheckboxField(data.getCode());

        checkbox.setOptions(data.getOptions());
        checkbox.setAlign(data.getAlign());

        if (data.getDefaultValue() == null ) {
            checkbox.setDefaultValue(new ArrayList<String>());
        } else if (data.getDefaultValue() instanceof ArrayList) {
            checkbox.setDefaultValue((ArrayList<String>) data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return checkbox;
    }

    /**
     * Convert FormFieldParseData to MultipleSelectField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    @SuppressWarnings("unchecked")
    private MultipleSelectField parseMultiSelectTextType(FormFieldParseData data) throws KintoneAPIException {
        MultipleSelectField multiSelect = new MultipleSelectField(data.getCode());

        multiSelect.setOptions(data.getOptions());

        if (data.getDefaultValue() == null ) {
            multiSelect.setDefaultValue(new ArrayList<String>());
        } else if (data.getDefaultValue() instanceof ArrayList) {
            multiSelect.setDefaultValue((ArrayList<String>) data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return multiSelect;
    }

    /**
     * Convert FormFieldParseData to RadioButtonField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private RadioButtonField parseRadioButtonType(FormFieldParseData data) throws KintoneAPIException {
        RadioButtonField radioBtn = new RadioButtonField(data.getCode());

        radioBtn.setOptions(data.getOptions());
        radioBtn.setAlign(data.getAlign());

        if (data.getDefaultValue() == null ) {
            radioBtn.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof String) {
            radioBtn.setDefaultValue((String)data.getDefaultValue());
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return radioBtn;
    }

    /**
     * Convert FormFieldParseData to RelatedRecordsField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private RelatedRecordsField parseRelatedRecordType(FormFieldParseData data) throws KintoneAPIException {
        RelatedRecordsField relatedRecord = new RelatedRecordsField(data.getCode());
        relatedRecord.setLabel(data.getLabel());
        relatedRecord.setNoLabel(data.getNoLabel());
        relatedRecord.setReferenceTable(data.getReferenceTable());

        return relatedRecord;
    }

    /**
     * Convert FormFieldParseData to UserSelectionField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    @SuppressWarnings("unchecked")
    private UserSelectionField parseUserSelectionType(FormFieldParseData data) throws KintoneAPIException {
        UserSelectionField userSelection = new UserSelectionField(data.getCode());
        userSelection.setEntities(data.getEntities());

        if (data.getDefaultValue() == null ) {
            userSelection.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof ArrayList) {
            ArrayList<MemberSelectEntity> defaultValue = new ArrayList<MemberSelectEntity>();

            @SuppressWarnings("rawtypes")
            Iterator iterator = ((ArrayList) data.getDefaultValue()).iterator();
            while (iterator.hasNext()) {
            	Map<String, String> map = (Map<String, String>)iterator.next();
                MemberSelectEntity entity = new MemberSelectEntity();
                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey().equals("type")) {
                        entity.setType(MemberSelectEntityType.valueOf(entry.getValue()));
                    } else if (entry.getKey().equals("code")) {
                        entity.setCode(entry.getValue());
                    }
                }
                defaultValue.add(entity);
            }

            userSelection.setDefaultValue(defaultValue);
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return userSelection;
    }

    /**
     * Convert FormFieldParseData to GroupSelectionField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    @SuppressWarnings("unchecked")
    private GroupSelectionField parseGroupSelectionType(FormFieldParseData data) throws KintoneAPIException {
        GroupSelectionField groupSelection = new GroupSelectionField(data.getCode());
        groupSelection.setEntities(data.getEntities());

        if (data.getDefaultValue() == null ) {
            groupSelection.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof ArrayList) {
            ArrayList<MemberSelectEntity> defaultValue = new ArrayList<MemberSelectEntity>();

            @SuppressWarnings("rawtypes")
            Iterator iterator = ((ArrayList) data.getDefaultValue()).iterator();
            while (iterator.hasNext()) {
            	Map<String, String> map = (Map<String, String>)iterator.next();
                MemberSelectEntity entity = new MemberSelectEntity();
                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey().equals("type")) {
                        entity.setType(MemberSelectEntityType.valueOf(entry.getValue()));
                    } else if (entry.getKey().equals("code")) {
                        entity.setCode(entry.getValue());
                    }
                }
                defaultValue.add(entity);
            }

            groupSelection.setDefaultValue(defaultValue);
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return groupSelection;
    }

    /**
     * Convert FormFieldParseData to DepartmentSelectionField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    @SuppressWarnings("unchecked")
    private DepartmentSelectionField parseDepartmentSelectionType(FormFieldParseData data) throws KintoneAPIException {
        DepartmentSelectionField departSelect = new DepartmentSelectionField(data.getCode());
        departSelect.setEntities(data.getEntities());

        if (data.getDefaultValue() == null ) {
            departSelect.setDefaultValue(null);
        } else if (data.getDefaultValue() instanceof ArrayList) {
            ArrayList<MemberSelectEntity> defaultValue = new ArrayList<MemberSelectEntity>();

            @SuppressWarnings("rawtypes")
            Iterator iterator = ((ArrayList) data.getDefaultValue()).iterator();
            while (iterator.hasNext()) {
            	Map<String, String> map = (Map<String, String>)iterator.next();
                MemberSelectEntity entity = new MemberSelectEntity();
                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey().equals("type")) {
                        entity.setType(MemberSelectEntityType.valueOf(entry.getValue()));
                    } else if (entry.getKey().equals("code")) {
                        entity.setCode(entry.getValue());
                    }
                }
                defaultValue.add(entity);
            }

            departSelect.setDefaultValue(defaultValue);
        } else {
            throw new KintoneAPIException("Invalid default value data type:" + data.getDefaultValue());
        }

        return departSelect;
    }

    /**
     * Convert FormFieldParseData to SubTableField
     * @param data
     * @return
     * @throws KintoneAPIException
     */
    private SubTableField parseSubTableType(FormFieldParseData data) throws KintoneAPIException {
        SubTableField subtable = new SubTableField(data.getCode());

        HashMap<String, AbstractInputField> fields = new HashMap<String, AbstractInputField>();

        Set<Entry<String, JsonElement>> fieldsJson = data.getFields().entrySet();
        for(Entry<String, JsonElement> element : fieldsJson) {
            AbstractInputField field = parseInputField(element.getValue());
            fields.put(field.getCode(), field);
        }

        subtable.setFields(fields);
        return subtable;
    }
}
