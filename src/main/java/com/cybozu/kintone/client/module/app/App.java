/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.app;

import java.util.List;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.GetAppRequest;
import com.cybozu.kintone.client.model.app.GetAppsRequest;
import com.cybozu.kintone.client.model.app.GetFormFieldsRequest;
import com.cybozu.kintone.client.model.app.GetFormLayoutRequest;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.module.parser.AppParser;
import com.google.gson.JsonElement;

/**
 * Managing kintone applications such as creating app, setting of form fields, form layout,
 * app permission, progress management, etc
 *
 */
public class App {
    private static final AppParser parser = new AppParser();
    private Connection connection;

    /**
     * @param connection
     */
    public App(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get app with id
     * Permissions to view the App is needed.
     * API Tokens cannot be used with this API.
     * @param appId
     * @return
     * @throws KintoneAPIException
     */
    public AppModel getApp(Integer appId) throws KintoneAPIException {
        GetAppRequest getAppRequest = new GetAppRequest(appId);
        String requestbody = parser.parseObject(getAppRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, requestbody);
        return parser.parseApp(response);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param ids
     * @param codes
     * @param name
     * @param spaceIds
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    private List<AppModel> getApps(List<Integer> ids, List<String> codes, String name, List<Integer> spaceIds, Integer offset, Integer limit) throws KintoneAPIException {
        GetAppsRequest getAppsRequest = new GetAppsRequest(ids, codes, name, spaceIds, offset, limit);
        String requestBody = parser.parseObject(getAppsRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APPS, requestBody);
        return parser.parseApps(response);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> getApps(Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the id for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param ids
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> getAppsByIDs(List<Integer> ids, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app code for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> getAppsByCodes(List<String> codes, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app name for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> getAppsByName(String name, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, limit);
    }

    /**
     * Get apps which belongs to a specific space.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param limit
     * @param offset
     * @return
     * @throws KintoneAPIException
     */
    public List<AppModel> getAppsBySpaceIDs(List<Integer> spaceIds, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, limit);
    }

    /**
     * Gets the list of fields and field settings of an App.
     * Permission to manage the App is needed when obtaining data of live Apps.
     * Permission to manage the App is needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param appId
     * @param lang
     * @param isPreview
     * @return FormFields
     * @throws KintoneAPIException
     */
    public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        if (appId == null || appId < 0) {
            throw new KintoneAPIException("Invalid app id value:" + appId);
        }

        if (lang == null) {
            lang = LanguageSetting.DEFAULT;
        }

        String apiRequest = ConnectionConstants.APP_FIELDS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        }

        GetFormFieldsRequest getFormFieldsRequest = new GetFormFieldsRequest(appId, lang);
        String requestBody = parser.parseObject(getFormFieldsRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest.toString(), requestBody);
        FormFields formfields = parser.parseFormFields(response);
        formfields.setApp(appId);

        return formfields;
    }

    /**
     * Gets the field layout info of a form in an App.
     * Perrmission to view records are needed when obtaining information of live Apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param appId
     * @param isPreview
     * @return
     * @throws KintoneAPIException
     */
    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        if (appId == null || appId < 0) {
            throw new KintoneAPIException("Invalid app id value: " + appId);
        }

        String apiRequest = ConnectionConstants.APP_LAYOUT;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        }
        GetFormLayoutRequest getFormLayoutRequest = new GetFormLayoutRequest(appId);
        String requestBody = parser.parseObject(getFormLayoutRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return parser.parseFormLayout(response);
    }
}
