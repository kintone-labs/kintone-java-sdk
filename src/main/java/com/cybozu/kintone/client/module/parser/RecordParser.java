/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RecordParser extends Parser{

    /**
     * Convert json to FieldValue class
     * @param fieldType fieldType of the parseField
     * @param fieldValue fieldValue of the parseField
     * @return FieldValue
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FieldValue parseField(String fieldType, JsonElement fieldValue) throws KintoneAPIException {
        FieldValue field = new FieldValue();
        field.setType(FieldType.valueOf(fieldType));

        try {
            switch (FieldType.valueOf(fieldType)) {
            case SUBTABLE:
                ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
                JsonArray tableItemArray = fieldValue.getAsJsonArray();
                // processing for each table-item
                for (JsonElement item : tableItemArray) {
                    JsonObject itemFieldsJson = item.getAsJsonObject().getAsJsonObject("value");
                    HashMap<String, FieldValue> itemFields = new HashMap<String, FieldValue>();
                    // convert each field in table-item to FieldValue class
                    for (Entry<String, JsonElement> entry : itemFieldsJson.entrySet()) {
                        String itemFieldType = entry.getValue().getAsJsonObject().get("type").getAsString();
                        JsonElement itemFieldValue = entry.getValue().getAsJsonObject().get("value");
                        FieldValue itemField = parseField(itemFieldType, itemFieldValue);
                        itemFields.put(entry.getKey(), itemField);
                    }
                    SubTableValueItem tableItem = new SubTableValueItem();
                    Integer itemId = item.getAsJsonObject().get("id").getAsInt();
                    tableItem.setID(itemId);
                    tableItem.setValue(itemFields);
                    subTable.add(tableItem);
                }
                field.setValue(subTable);
                break;
            case USER_SELECT:
            case ORGANIZATION_SELECT:
            case GROUP_SELECT:
            case STATUS_ASSIGNEE:
                ArrayList<Member> memberList = new ArrayList<Member>();
                JsonArray memberArray = fieldValue.getAsJsonArray();
                // processing for each item in MemberList
                for (JsonElement member : memberArray) {
                    memberList.add(gson.fromJson(member, Member.class));
                }
                field.setValue(memberList);
                break;
            case CREATOR:
            case MODIFIER:
                field.setValue(gson.fromJson(fieldValue, Member.class));
                break;
            case CHECK_BOX:
            case MULTI_SELECT:
            case CATEGORY:
                ArrayList<String> selectedItemList = new ArrayList<String>();
                JsonArray selectedItemArray = fieldValue.getAsJsonArray();
                // processing for each item in SelectedItemList
                for (JsonElement item : selectedItemArray) {
                    selectedItemList.add(gson.fromJson(item, String.class));
                }
                field.setValue(selectedItemList);
                break;
            case FILE:
                ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
                JsonArray cbFileArray = fieldValue.getAsJsonArray();
                // processing for each item in FileList
                for (JsonElement item : cbFileArray) {
                    cbFileList.add(gson.fromJson(item, FileModel.class));
                }
                field.setValue(cbFileList);
                break;
            default:
                // by default, we convert field value to String class .
                field.setValue(gson.fromJson(fieldValue, String.class));
                break;
            }
            return field;
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error", e);
        }
    }
}
