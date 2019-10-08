/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.app.AppDeployStatus;
import com.cybozu.kintone.client.model.app.app.AppModel;
import com.cybozu.kintone.client.model.app.basic.PreviewApp;
import com.cybozu.kintone.client.model.app.basic.response.BasicResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetAppDeployStatusResponse;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.member.Member;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class AppParser extends Parser {
    private static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final FormLayoutParser formLayoutParser = new FormLayoutParser();

    static {
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Convert json to AppModel class
     * @param input input of the parseApp
     * @return AppModel
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
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
                throw new KintoneAPIException("Parse data error", e);
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
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type", e);
        }
        return app;
    }

    /**
     * Convert json to AppModel List
     * @param input input of the parseApps
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> parseApps(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonElement apps = input.getAsJsonObject().get("apps");
        if (!apps.isJsonArray()) {
            throw new KintoneAPIException("Parse error");
        }

        ArrayList<AppModel> result = new ArrayList<AppModel>();

        Iterator<JsonElement> iterator = apps.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            result.add(parseApp(iterator.next()));
        }

        return result;
    }

    /**
     * Convert json to FormFields class
     *
     * @param input input of the parseFormFields
     * @return FormFields
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FormFields parseFormFields(JsonElement input) throws KintoneAPIException {
        FormFieldParser parser = new FormFieldParser();
        return parser.parse(input);
    }

    /**
     * Convert json to FormLayout class
     * @param input input of the parseFormLayout
     * @return FormLayout
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
        return formLayoutParser.parseFormLayout(input);
    }

    /**
     * Convert json to BasicResponse class
     *
     * @param input input of the parseBasicResponse
     * @return BasicResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public BasicResponse parseBasicResponse(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        try {
            BasicResponse response = new BasicResponse();
            response = gson.fromJson(input, BasicResponse.class);
            return response;
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type", e);
        }
    }

    /**
     * Convert json string to AddPreviewAppResponse class
     *
     * @param input input of the parseAddPreviewAppResponse
     * @return AddPreviewAppResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public PreviewApp parseAddPreviewAppResponse(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }
        try {
            PreviewApp response = new PreviewApp();
            response = gson.fromJson(input, PreviewApp.class);
            return response;
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type", e);
        }
    }

    /**
     * Convert json string to AddPreviewAppResponse class
     *
     * @param input input of the parseAppDeployStatusResponse
     * @return GetAppDeployStatusResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public GetAppDeployStatusResponse parseAppDeployStatusResponse(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        GetAppDeployStatusResponse response = new GetAppDeployStatusResponse();
        JsonObject jsonObject = input.getAsJsonObject();
        ArrayList<AppDeployStatus> arrayAppDeployStatus = new ArrayList<AppDeployStatus>();
        Type appDeployStatusListType = new TypeToken<ArrayList<AppDeployStatus>>() {
        }.getType();
        try {
            arrayAppDeployStatus = gson.fromJson(jsonObject.get("apps"), appDeployStatusListType);
            response.setApps(arrayAppDeployStatus);

            return response;
        } catch (Exception e) {
            throw new KintoneAPIException("Invalid data type", e);
        }
    }

}
