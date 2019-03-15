/**
 * Copyright 2017 Cybozu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cybozu.kintone.client.module.parser;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.AlignLayout;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.LinkProtocol;
import com.cybozu.kintone.client.model.app.form.UnitPosition;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FieldGroup;
import com.cybozu.kintone.client.model.app.form.field.FieldMapping;
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
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupItem;
import com.cybozu.kintone.client.model.app.form.field.input.member.DepartmentSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.GroupSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.MemberSelectEntity;
import com.cybozu.kintone.client.model.app.form.field.input.member.UserSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.CheckboxField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.DropDownField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.MultipleSelectField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.OptionData;
import com.cybozu.kintone.client.model.app.form.field.input.selection.RadioButtonField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateTimeField;
import com.cybozu.kintone.client.model.app.form.field.input.time.TimeField;
import com.cybozu.kintone.client.model.app.form.field.related_record.ReferenceTable;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedApp;
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
import com.google.gson.JsonParser;

public class FormFieldParserTest {
    private static final JsonParser jsonParser = new JsonParser();
    private static JsonElement validInput;

    @BeforeClass
    public static void setup() {
        validInput = jsonParser.parse(readInput("/xxx/xxx/xxx.txt"));
    }

    private static String readInput(String file) {
        URL url = FormFieldParserTest.class.getResource(file);
        if (url == null) {
            return null;
        }

        String result = null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new FileReader(new File(url.getFile())));
            char[] buffer = new char[1024];
            int size = -1;
            while ((size = reader.read(buffer, 0, buffer.length)) >= 0) {
                sb.append(buffer, 0, size);
            }
            result = sb.toString();
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Test
    public void testParseRevisionShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getRevision());
            if (formFields.getRevision() < 0) {
                fail("Invalid revision value");
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseNumberFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                NumberField number = (NumberField) properties.get("Number");
                assertNotNull(number);
                assertEquals("Number", number.getCode());
                assertEquals(FieldType.NUMBER, number.getType());
                assertEquals("0", number.getMinValue());
                assertEquals("1000", number.getMaxValue());
                assertEquals("Number", number.getLabel());
                assertEquals("$", number.getUnit());
                assertEquals(true, number.getRequired());
                assertEquals(true, number.getNoLabel());
                assertEquals(true, number.getDigit());
                assertEquals(true, number.getUnique());
                assertEquals("12345", number.getDefaultValue());
                assertEquals("2", number.getDisplayScale());
                assertEquals(UnitPosition.AFTER, number.getUnitPosition());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseLookupFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                LookupField lookup = (LookupField) properties.get("Lookup");
                assertNotNull(lookup);
                assertEquals("Lookup", lookup.getCode());
                assertEquals(FieldType.NUMBER, lookup.getType());
                assertEquals("Lookup", lookup.getLabel());
                assertEquals(false, lookup.getNoLabel());
                assertEquals(false, lookup.getRequired());

                LookupItem lookupItem = lookup.getLookup();
                assertNotNull(lookupItem);
                assertEquals("Number", lookupItem.getRelatedKeyField());
                assertEquals("Record_number desc", lookupItem.getSort());
                assertEquals("", lookupItem.getFilterCond());
                assertEquals(0, lookupItem.getFieldMapping().size());
                assertEquals(0, lookupItem.getLookupPickerFields().size());

                RelatedApp relatedApp = lookupItem.getRelatedApp();
                assertNotNull(relatedApp);
                assertEquals("12", relatedApp.getApp());
                assertEquals("", relatedApp.getCode());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseCreatorFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                CreatorField creator = (CreatorField) properties.get("Created_by");
                assertNotNull(creator);
                assertEquals("Created_by", creator.getCode());
                assertEquals(FieldType.CREATOR, creator.getType());
                assertEquals("Created by", creator.getLabel());
                assertEquals(false, creator.getNoLabel());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseCreatedDateTimeFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                CreatedTimeField createdDateTime = (CreatedTimeField) properties.get("Created_datetime");
                assertNotNull(createdDateTime);
                assertEquals("Created_datetime", createdDateTime.getCode());
                assertEquals(FieldType.CREATED_TIME, createdDateTime.getType());
                assertEquals("Created datetime", createdDateTime.getLabel());
                assertEquals(false, createdDateTime.getNoLabel());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseRecordNumberFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                RecordNumberField recordNumber = (RecordNumberField) properties.get("Record_number");
                assertNotNull(recordNumber);
                assertEquals("Record_number", recordNumber.getCode());
                assertEquals(FieldType.RECORD_NUMBER, recordNumber.getType());
                assertEquals("Record number", recordNumber.getLabel());
                assertEquals(false, recordNumber.getNoLabel());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseUpdaterFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                ModifierField updater = (ModifierField) properties.get("Updated_by");
                assertNotNull(updater);
                assertEquals("Updated_by", updater.getCode());
                assertEquals(FieldType.MODIFIER, updater.getType());
                assertEquals("Updated by", updater.getLabel());
                assertEquals(false, updater.getNoLabel());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseUpdatedDateTimeFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                UpdatedTimeField updatedDateTime = (UpdatedTimeField) properties.get("Updated_datetime");
                assertNotNull(updatedDateTime);
                assertEquals("Updated_datetime", updatedDateTime.getCode());
                assertEquals(FieldType.UPDATED_TIME, updatedDateTime.getType());
                assertEquals("Updated datetime", updatedDateTime.getLabel());
                assertEquals(false, updatedDateTime.getNoLabel());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseStatusFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                StatusField status = (StatusField) properties.get("Status");
                assertNotNull(status);
                assertEquals("Status", status.getCode());
                assertEquals(FieldType.STATUS, status.getType());
                assertEquals("Status", status.getLabel());
                assertEquals(false, status.getEnabled());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseAssigneeFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                AssigneeField assignee = (AssigneeField) properties.get("Assignee");
                assertNotNull(assignee);
                assertEquals("Assignee", assignee.getCode());
                assertEquals(FieldType.STATUS_ASSIGNEE, assignee.getType());
                assertEquals("Assignee", assignee.getLabel());
                assertEquals(false, assignee.getEnabled());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseCategoryFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                CategoryField category = (CategoryField) properties.get("Categories");
                assertNotNull(category);
                assertEquals("Categories", category.getCode());
                assertEquals(FieldType.CATEGORY, category.getType());
                assertEquals("Categories", category.getLabel());
                assertEquals(false, category.getEnabled());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseRichTextFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                RichTextField richText = (RichTextField) properties.get("Rich_text");
                assertNotNull(richText);
                assertEquals("Rich_text", richText.getCode());
                assertEquals(FieldType.RICH_TEXT, richText.getType());
                assertEquals("Rich text", richText.getLabel());
                assertEquals("", richText.getDefaultValue());
                assertEquals(false, richText.getNoLabel());
                assertEquals(false, richText.getRequired());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseLinkFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                LinkField link = (LinkField) properties.get("Link");
                assertNotNull(link);
                assertEquals(FieldType.LINK, link.getType());
                assertEquals("Link", link.getCode());
                assertEquals("Link", link.getLabel());
                assertEquals(false, link.getNoLabel());
                assertEquals(false, link.getRequired());
                assertEquals(LinkProtocol.WEB, link.getProtocol());
                assertEquals("", link.getMinLength());
                assertEquals("", link.getMaxLength());
                assertEquals(false, link.getUnique());
                assertEquals("", link.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseFieldGroupShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                FieldGroup fieldGroup = (FieldGroup) properties.get("Field_group");
                assertNotNull(fieldGroup);
                assertEquals(FieldType.GROUP, fieldGroup.getType());
                assertEquals("Field_group", fieldGroup.getCode());
                assertEquals("Field group", fieldGroup.getLabel());
                assertEquals(true, fieldGroup.getNoLabel());
                assertEquals(true, fieldGroup.getOpenGroup());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseTimeFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                TimeField timeField = (TimeField) properties.get("Time");
                assertNotNull(timeField);
                assertEquals(FieldType.TIME, timeField.getType());
                assertEquals("Time", timeField.getCode());
                assertEquals("Time", timeField.getLabel());
                assertEquals(false, timeField.getNoLabel());
                assertEquals(false, timeField.getRequired());
                assertEquals(true, timeField.getDefaultNowValue());
                assertEquals("", timeField.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseDateFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                DateField dateField = (DateField) properties.get("Date");
                assertNotNull(dateField);
                assertEquals(FieldType.DATE, dateField.getType());
                assertEquals("Date", dateField.getCode());
                assertEquals("Date", dateField.getLabel());
                assertEquals(false, dateField.getNoLabel());
                assertEquals(false, dateField.getRequired());
                assertEquals(false, dateField.getUnique());
                assertEquals(true, dateField.getDefaultNowValue());
                assertEquals("", dateField.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseDateTimeFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                DateTimeField dateTimeField = (DateTimeField) properties.get("Date_and_time");
                assertNotNull(dateTimeField);
                assertEquals(FieldType.DATETIME, dateTimeField.getType());
                assertEquals("Date_and_time", dateTimeField.getCode());
                assertEquals("Date and time", dateTimeField.getLabel());
                assertEquals(false, dateTimeField.getNoLabel());
                assertEquals(false, dateTimeField.getRequired());
                assertEquals(false, dateTimeField.getUnique());
                assertEquals(true, dateTimeField.getDefaultNowValue());
                assertEquals("", dateTimeField.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseAttachmentFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                AttachmentField attachment = (AttachmentField) properties.get("Attachment");
                assertNotNull(attachment);
                assertEquals(FieldType.FILE, attachment.getType());
                assertEquals("Attachment", attachment.getCode());
                assertEquals("Attachment", attachment.getLabel());
                assertEquals(false, attachment.getNoLabel());
                assertEquals(false, attachment.getRequired());
                assertEquals("150", attachment.getThumbnailSize());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseSingleLTextFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                SingleLineTextField text = (SingleLineTextField) properties.get("Text");
                assertNotNull(text);
                assertEquals(FieldType.SINGLE_LINE_TEXT, text.getType());
                assertEquals("Text", text.getCode());
                assertEquals("Text", text.getLabel());
                assertEquals(false, text.getNoLabel());
                assertEquals(false, text.getRequired());
                assertEquals(null, text.getMinLength());
                assertEquals(null, text.getMaxLength());
                assertEquals("", text.getExpression());
                assertEquals(false, text.getHideExpression());
                assertEquals(false, text.getUnique());
                assertEquals("", text.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseMultiLTextFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                MultiLineTextField textArea = (MultiLineTextField) properties.get("Text_Area");
                assertNotNull(textArea);
                assertEquals(FieldType.MULTI_LINE_TEXT, textArea.getType());
                assertEquals("Text_Area", textArea.getCode());
                assertEquals("Text Area", textArea.getLabel());
                assertEquals(false, textArea.getNoLabel());
                assertEquals(false, textArea.getRequired());
                assertEquals("", textArea.getDefaultValue());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseDropDownFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                DropDownField dropDown = (DropDownField) properties.get("Drop_down");
                assertNotNull(dropDown);
                assertEquals(FieldType.DROP_DOWN, dropDown.getType());
                assertEquals("Drop_down", dropDown.getCode());
                assertEquals("Drop-down", dropDown.getLabel());
                assertEquals(false, dropDown.getNoLabel());
                assertEquals(false, dropDown.getRequired());
                assertEquals("", dropDown.getDefaultValue());

                Map<String, OptionData> options = dropDown.getOptions();
                assertNotNull(options);
                assertEquals(2, options.size());

                OptionData option1 = options.get("sample1");
                assertNotNull(option1);
                assertEquals("sample1", option1.getLabel());
                assertSame(0, option1.getIndex());

                OptionData option2 = options.get("sample2");
                assertNotNull(option2);
                assertEquals("sample2", option2.getLabel());
                assertSame(1, option2.getIndex());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseMultiSelectFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                MultipleSelectField multiSelect = (MultipleSelectField) properties.get("Multi_choice");
                assertNotNull(multiSelect);
                assertEquals(FieldType.MULTI_SELECT, multiSelect.getType());
                assertEquals("Multi_choice", multiSelect.getCode());
                assertEquals("Multi-choice", multiSelect.getLabel());
                assertEquals(false, multiSelect.getNoLabel());
                assertEquals(false, multiSelect.getRequired());

                Map<String, OptionData> options = multiSelect.getOptions();
                assertNotNull(options);
                assertEquals(4, options.size());

                OptionData option1 = options.get("Orange");
                assertNotNull(option1);
                assertEquals("Orange", option1.getLabel());
                assertSame(0, option1.getIndex());

                OptionData option2 = options.get("sample2");
                assertNotNull(option2);
                assertEquals("sample2", option2.getLabel());
                assertSame(1, option2.getIndex());

                OptionData option3 = options.get("sample3");
                assertNotNull(option3);
                assertEquals("sample3", option3.getLabel());
                assertSame(2, option3.getIndex());

                OptionData option4 = options.get("sample4");
                assertNotNull(option4);
                assertEquals("sample4", option4.getLabel());
                assertSame(3, option4.getIndex());

                List<String> defaultValue = multiSelect.getDefaultValue();
                assertNotNull(defaultValue);
                assertEquals(3, defaultValue.size());
                assertEquals("Orange", defaultValue.get(0));
                assertEquals("sample2", defaultValue.get(1));
                assertEquals("sample3", defaultValue.get(2));
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseCheckboxFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                CheckboxField checkbox = (CheckboxField) properties.get("Check_box");
                assertNotNull(checkbox);
                assertEquals(FieldType.CHECK_BOX, checkbox.getType());
                assertEquals("Check_box", checkbox.getCode());
                assertEquals("Check box", checkbox.getLabel());
                assertEquals(false, checkbox.getNoLabel());
                assertEquals(false, checkbox.getRequired());

                Map<String, OptionData> options = checkbox.getOptions();
                assertNotNull(options);
                assertEquals(2, options.size());

                OptionData option1 = options.get("sample1");
                assertNotNull(option1);
                assertEquals("sample1", option1.getLabel());
                assertSame(0, option1.getIndex());

                OptionData option2 = options.get("sample2");
                assertNotNull(option2);
                assertEquals("sample2", option2.getLabel());
                assertSame(1, option2.getIndex());

                List<String> defaultValue = checkbox.getDefaultValue();
                assertNotNull(defaultValue);
                assertEquals(1, defaultValue.size());
                assertTrue(defaultValue.contains("sample1"));

                assertEquals(AlignLayout.HORIZONTAL, checkbox.getAlign());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseRadioButtonFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                RadioButtonField radioBtn = (RadioButtonField) properties.get("Radio_Button");
                assertNotNull(radioBtn);
                assertEquals(FieldType.RADIO_BUTTON, radioBtn.getType());
                assertEquals("Radio_Button", radioBtn.getCode());
                assertEquals("Radio Button", radioBtn.getLabel());
                assertEquals(false, radioBtn.getNoLabel());
                assertEquals(true, radioBtn.getRequired());

                Map<String, OptionData> options = radioBtn.getOptions();
                assertNotNull(options);
                assertEquals(2, options.size());

                OptionData option1 = options.get("sample1");
                assertNotNull(option1);
                assertEquals("sample1", option1.getLabel());
                assertSame(0, option1.getIndex());

                OptionData option2 = options.get("sample2");
                assertNotNull(option2);
                assertEquals("sample2", option2.getLabel());
                assertSame(1, option2.getIndex());

                assertEquals("sample1", radioBtn.getDefaultValue());

                assertEquals(AlignLayout.VERTICAL, radioBtn.getAlign());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseRelatedRecordFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                RelatedRecordsField relatedRecords = (RelatedRecordsField) properties.get("Related_Records");
                assertNotNull(relatedRecords);
                assertEquals(FieldType.REFERENCE_TABLE, relatedRecords.getType());
                assertEquals("Related_Records", relatedRecords.getCode());
                assertEquals("Related Records", relatedRecords.getLabel());
                assertEquals(false, relatedRecords.getNoLabel());

                ReferenceTable refTable = relatedRecords.getReferenceTable();
                assertNotNull(refTable);
                assertEquals("", refTable.getFilterCond());
                assertEquals(Integer.valueOf(5), refTable.getSize());
                assertEquals("Record_number desc", refTable.getSort());

                RelatedApp refApp = refTable.getRelatedApp();
                assertNotNull(refApp);
                assertEquals("12", refApp.getApp());
                assertEquals("", refApp.getCode());

                FieldMapping condition = refTable.getCondition();
                assertNotNull(condition);
                assertEquals("Text", condition.getField());
                assertEquals("Text", condition.getRelatedFields());

                List<String> displayFields = refTable.getDisplayFields();
                assertNotNull(displayFields);
                assertEquals(1, displayFields.size());
                assertTrue(displayFields.contains("Number"));
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseUserSelectionFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                UserSelectionField userSelect = (UserSelectionField) properties.get("User_selection");
                assertNotNull(userSelect);
                assertEquals(FieldType.USER_SELECT, userSelect.getType());
                assertEquals("User_selection", userSelect.getCode());
                assertEquals("User selection", userSelect.getLabel());
                assertEquals(false, userSelect.getNoLabel());
                assertEquals(false, userSelect.getRequired());

                List<MemberSelectEntity> entites = userSelect.getEntities();
                assertNotNull(entites);
                assertEquals(1, entites.size());
                MemberSelectEntity entity = entites.get(0);
                assertEquals(MemberSelectEntityType.USER, entity.getType());
                assertEquals("dinh", entity.getCode());

                List<MemberSelectEntity> defaultValue = userSelect.getDefaultValue();
                assertNotNull(defaultValue);
                assertEquals(2, defaultValue.size());

                assertTrue(defaultValue.contains(new MemberSelectEntity("dinh", MemberSelectEntityType.USER)));
                assertTrue(defaultValue.contains(new MemberSelectEntity("cuc", MemberSelectEntityType.USER)));
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseGroupSelectionFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                GroupSelectionField groupSelect = (GroupSelectionField) properties.get("Group_selection");
                assertNotNull(groupSelect);
                assertEquals(FieldType.GROUP_SELECT, groupSelect.getType());
                assertEquals("Group_selection", groupSelect.getCode());
                assertEquals("Group selection", groupSelect.getLabel());
                assertEquals(false, groupSelect.getNoLabel());
                assertEquals(false, groupSelect.getRequired());

                List<MemberSelectEntity> entites = groupSelect.getEntities();
                assertNotNull(entites);
                assertEquals(0, entites.size());

                List<MemberSelectEntity> defaultValue = groupSelect.getDefaultValue();
                assertNotNull(defaultValue);
                assertEquals(0, defaultValue.size());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseDepartmentSelectionFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                DepartmentSelectionField departSelect = (DepartmentSelectionField) properties
                        .get("Department_selection");
                assertNotNull(departSelect);
                assertEquals(FieldType.ORGANIZATION_SELECT, departSelect.getType());
                assertEquals("Department_selection", departSelect.getCode());
                assertEquals("Department selection", departSelect.getLabel());
                assertEquals(false, departSelect.getNoLabel());
                assertEquals(false, departSelect.getRequired());

                List<MemberSelectEntity> entites = departSelect.getEntities();
                assertNotNull(entites);
                assertEquals(0, entites.size());

                List<MemberSelectEntity> defaultValue = departSelect.getDefaultValue();
                assertNotNull(defaultValue);
                assertEquals(0, defaultValue.size());
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testParseSubTableFieldShouldSuccess() {
        assertNotNull(validInput);

        FormFieldParser parser = new FormFieldParser();
        try {
            FormFields formFields = parser.parse(validInput);
            assertNotNull(formFields);
            assertNotNull(formFields.getProperties());

            Map<String, Field> properties = formFields.getProperties();
            try {
                SubTableField subTable = (SubTableField) properties.get("Table");
                assertNotNull(subTable);
                assertEquals(FieldType.SUBTABLE, subTable.getType());
                assertEquals("Table", subTable.getCode());

                Map<String, AbstractInputField> fields = subTable.getFields();
                assertNotNull(fields);
                assertEquals(2, fields.size());

                AbstractInputField number = fields.get("Number_row");
                assertTrue(number instanceof NumberField);
                NumberField numberField = (NumberField) number;
                assertEquals(FieldType.NUMBER, numberField.getType());
                assertEquals("Number_row", numberField.getCode());
                assertEquals("Number row", numberField.getLabel());
                assertEquals(false, numberField.getNoLabel());
                assertEquals(false, numberField.getRequired());
                assertEquals(false, numberField.getDigit());
                assertEquals("", numberField.getMinValue());
                assertEquals("", numberField.getMaxValue());
                assertEquals("", numberField.getDefaultValue());
                assertEquals("", numberField.getDisplayScale());
                assertEquals("", numberField.getUnit());
                assertEquals(UnitPosition.BEFORE, numberField.getUnitPosition());

                AbstractInputField text = fields.get("Text_row");
                assertTrue(text instanceof SingleLineTextField);
            } catch (ClassCastException e) {
                fail(e.getMessage());
            }
        } catch (KintoneAPIException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseShouldFailWhenGivenNoCode() throws KintoneAPIException {
        String invalidMaxValue = readInput("/xxx/xxx/xxx.txt");

        assertNotNull(invalidMaxValue);

        FormFieldParser parser = new FormFieldParser();
        parser.parse(jsonParser.parse(invalidMaxValue));
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseShouldFailWhenGivenNoField() throws KintoneAPIException {
        String invalidMaxValue = readInput("/xxx/xxx/xxx.txt");

        assertNotNull(invalidMaxValue);

        FormFieldParser parser = new FormFieldParser();
        parser.parse(jsonParser.parse(invalidMaxValue));
    }
}
