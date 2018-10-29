/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.util.Iterator;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.layout.FieldLayout;
import com.cybozu.kintone.client.model.app.form.layout.FieldSize;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.GroupLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.form.layout.RowLayout;
import com.cybozu.kintone.client.model.app.form.layout.SubTableLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FormLayoutParser {

    /**
     * Convert json to FormLayout
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonObject root = input.getAsJsonObject();
        FormLayout formLayout = new FormLayout();

        formLayout.setRevision(root.get("revision").getAsString());

        JsonArray layoutProperties = root.get("layout").getAsJsonArray();
        Iterator<JsonElement> iterator = layoutProperties.iterator();
        while (iterator.hasNext()) {
            formLayout.getLayout().add(parseItemLayout(iterator.next()));
        }

        return formLayout;
    }

    /**
     * Convert json to ItemLayout
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public ItemLayout parseItemLayout(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonObject root = input.getAsJsonObject();
        switch(root.get("type").getAsString()) {
            case "ROW":
                return parseRowLayout(root);
            case "SUBTABLE":
                return parseSubTableLayout(root);
            case "GROUP":
                return parseGroupLayout(root);
            default:
                throw new KintoneAPIException("Layout type is not supported");
        }
    }

    /**
     * Convert json to GroupLayout
     * @param root
     * @return
     */
    public GroupLayout parseGroupLayout(JsonObject root) {
        GroupLayout groupLayout = new GroupLayout();
        groupLayout.setCode(root.get("code").getAsString());

        JsonArray layout = root.get("layout").getAsJsonArray();
        Iterator<JsonElement> iterator = layout.iterator();
        while(iterator.hasNext()) {
            groupLayout.getLayout().add(parseRowLayout(iterator.next().getAsJsonObject()));
        }

        return groupLayout;
    }

    /**
     * Convert json to SubTableLayout
     * @param root
     * @return
     */
    public SubTableLayout parseSubTableLayout(JsonObject root) {
        SubTableLayout subTableLayout = new SubTableLayout();

        subTableLayout.setCode(root.get("code").getAsString());

        JsonArray fields = root.get("fields").getAsJsonArray();
        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            subTableLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return subTableLayout;
    }

    /**
     * Convert json to RowLayout
     * @param root
     * @return
     */
    public RowLayout parseRowLayout(JsonObject root) {
        RowLayout rowLayout = new RowLayout();
        JsonArray fields = root.get("fields").getAsJsonArray();

        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            rowLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return rowLayout;
    }

    /**
     * Convert json to FieldLayout
     * @param input
     * @return
     */
    public FieldLayout parseFieldLayout(JsonElement input) {
        JsonObject root = input.getAsJsonObject();
        FieldLayout fieldLayout = new FieldLayout();

        JsonElement type = root.get("type");
        if (type != null && type.isJsonPrimitive()) {
            fieldLayout.setType(type.getAsString());
        }

        JsonElement code = root.get("code");
        if (code != null && code.isJsonPrimitive()) {
            fieldLayout.setCode(code.getAsString());
        }

        JsonElement label = root.get("label");
        if (label != null && label.isJsonPrimitive()) {
            fieldLayout.setLabel(label.getAsString());
        }

        JsonElement elementId = root.get("elementId");
        if (elementId != null && elementId.isJsonPrimitive()) {
            fieldLayout.setElementId(elementId.getAsString());
        }

        fieldLayout.setSize(parseFieldSize(root.get("size").getAsJsonObject()));

        return fieldLayout;
    }

    /**
     * Convert json to FieldSize
     * @param root
     * @return
     */
    public FieldSize parseFieldSize(JsonObject root) {
        FieldSize fieldSize = new FieldSize();

        JsonElement width = root.get("width");
        if (width != null && width.isJsonPrimitive()) {
            fieldSize.setWidth(width.getAsString());
        }

        JsonElement height = root.get("height");
        if (height != null && height.isJsonPrimitive()) {
            fieldSize.setHeight(height.getAsString());
        }

        JsonElement innerHeight = root.get("innerHeight");
        if (innerHeight != null && innerHeight.isJsonPrimitive()) {
            fieldSize.setInnerHeight(innerHeight.getAsString());
        }

        return fieldSize;
    }
}
