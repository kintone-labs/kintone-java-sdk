/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.member.Member;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AppParser {
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final FormLayoutParser formLayoutParser = new FormLayoutParser();

    static {
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Convert json to AppModel class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public AppModel parseApp(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        AppModel app = new AppModel();
        JsonObject jsonObject = input.getAsJsonObject();

        try {
            app.setAppId(Integer.parseInt(jsonObject.get("appId").getAsString()));
            app.setCode(jsonObject.get("code").getAsString());
            app.setName(jsonObject.get("name").getAsString());
            app.setDescription(jsonObject.get("description").getAsString());

            try {
                Date createdDate = isoDateFormat.parse(jsonObject.get("createdAt").getAsString());
                app.setCreatedAt(createdDate);

                Date modifiedDate = isoDateFormat.parse(jsonObject.get("modifiedAt").getAsString());
                app.setModifiedAt(modifiedDate);
            } catch (ParseException e) {
                throw new KintoneAPIException("Parse date error");
            }

            app.setCreator(gson.fromJson(jsonObject.get("creator"), Member.class));
            app.setModifier(gson.fromJson(jsonObject.get("modifier"), Member.class));

            JsonElement spaceId = jsonObject.get("spaceId");
            if (spaceId.isJsonPrimitive()) {
                app.setSpaceId(spaceId.getAsInt());
            }

            JsonElement threadId = jsonObject.get("threadId");
            if (threadId.isJsonPrimitive()) {
                app.setThreadId(threadId.getAsInt());
            }
        } catch (NumberFormatException e) {
            throw new KintoneAPIException("Invalid data type");
        }
        return app;
    }

    /**
     * Convert json to AppModel List
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> parseApps(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonElement apps = input.getAsJsonObject().get("apps");
        if (!apps.isJsonArray()) {
            throw new KintoneAPIException("Parse error");
        }

        List<AppModel> result = new ArrayList<AppModel>();

        Iterator<JsonElement> iterator = apps.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            result.add(parseApp(iterator.next()));
        }

        return result;
    }

    /**
     * Convert json to FormFields class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public FormFields parseFormFields(JsonElement input) throws KintoneAPIException {
        FormFieldParser parser = new FormFieldParser();
        return parser.parse(input);
    }


    /**
     * Convart json to FormLayout class
     * @param input
     * @return
     * @throws KintoneAPIException
     */
    public FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
        return formLayoutParser.parseFormLayout(input);
    }

    /**
     * Convert Object to jsonString
     * @param obj
     * @return
     * @throws KintoneAPIException
     */
    public String parseObject(Object obj) throws KintoneAPIException {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }

}
