/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.GetAppRequest;
import com.cybozu.kintone.client.model.app.GetAppsRequest;
import com.cybozu.kintone.client.model.app.GetFormFieldsRequest;
import com.cybozu.kintone.client.model.app.GetFormLayoutRequest;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.basic.request.AddPreviewAppRequest;
import com.cybozu.kintone.client.model.app.basic.request.AddUpdateFormFieldsRequest;
import com.cybozu.kintone.client.model.app.basic.request.DeleteFormFieldsRequest;
import com.cybozu.kintone.client.model.app.basic.request.DeployAppSettingsRequest;
import com.cybozu.kintone.client.model.app.basic.request.GetAppDeployStatusRequest;
import com.cybozu.kintone.client.model.app.basic.request.GetGeneralSettingsRequest;
import com.cybozu.kintone.client.model.app.basic.request.GetViewsRequest;
import com.cybozu.kintone.client.model.app.basic.request.PreviewAppRequest;
import com.cybozu.kintone.client.model.app.basic.request.UpdateFormLayoutRequest;
import com.cybozu.kintone.client.model.app.basic.request.UpdateGeneralSettingsRequest;
import com.cybozu.kintone.client.model.app.basic.request.UpdateViewsRequest;
import com.cybozu.kintone.client.model.app.basic.response.AddPreviewAppResponse;
import com.cybozu.kintone.client.model.app.basic.response.BasicResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetAppDeployStatusResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetViewsResponse;
import com.cybozu.kintone.client.model.app.basic.response.UpdateViewsResponse;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.general.GeneralSettings;
import com.cybozu.kintone.client.model.app.view.ViewModel;
import com.cybozu.kintone.client.module.parser.AppParser;
import com.google.gson.JsonElement;

/**
 * Managing kintone applications such as creating app, setting of form fields, form layout,
 * app permission, progress management, etc
 *
 */
/**
 * @author p000796
 *
 */
public class App {
    private static final AppParser parser = new AppParser();
    private Connection connection;

    /**
     * @param connection connection of the App
     */
    public App(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get app with id
     * Permissions to view the App is needed.
     * API Tokens cannot be used with this API.
     * @param appId appId of the getApp
     * @return AppModel
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     * 
     */
    public AppModel getApp(Integer appId) throws KintoneAPIException {
        GetAppRequest getAppRequest = new GetAppRequest(appId);
        String requestbody = parser.parseObject(getAppRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP,
                requestbody);
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
    private ArrayList<AppModel> getApps(ArrayList<Integer> ids, ArrayList<String> codes, String name, ArrayList<Integer> spaceIds,
            Integer offset, Integer limit) throws KintoneAPIException {
        GetAppsRequest getAppsRequest = new GetAppsRequest(ids, codes, name, spaceIds, offset, limit);
        String requestBody = parser.parseObject(getAppsRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APPS,
                requestBody);
        return parser.parseApps(response);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param limit limit of the getApps
     * @param offset offset of the getApps
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getApps(Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the id for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param ids ids of the getAppsByIDs
     * @param offset offset of the getAppsByIDs
     * @param limit limit of the getAppsByIDs
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app code for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param codes codes of the getAppsByCodes
     * @param offset offset of the getAppsByCodes
     * @param limit limit of the getAppsByCodes
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, limit);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app name for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param name name of the getAppsByName
     * @param offset offset of the getAppsByName
     * @param limit limit of the getAppsByName
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByName(String name, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, limit);
    }

    /**
     * Get apps which belongs to a specific space.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     * @param spaceIds spaceIds of the getAppsBySpaceIDs
     * @param offset offset of the getAppsBySpaceIDs
     * @param limit limit of the getAppsBySpaceIDs
     * @return ArrayList
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset, Integer limit)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, limit);
    }

    /**
     * Gets the list of fields and field settings of an App.
     * Permission to manage the App is needed when obtaining data of live Apps.
     * Permission to manage the App is needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param appId appId of the getFormFields
     * @param lang lang of the getFormFields
     * @param isPreview isPreview of the getFormFields
     * @return FormFields
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        if (lang == null) {
            lang = LanguageSetting.DEFAULT;
        }

        String apiRequest = ConnectionConstants.APP_FIELDS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        }

        GetFormFieldsRequest getFormFieldsRequest = new GetFormFieldsRequest(appId, lang);
        String requestBody = parser.parseObject(getFormFieldsRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest.toString(),
                requestBody);
        FormFields formfields = parser.parseFormFields(response);
        formfields.setApp(appId);

        return formfields;
    }

    /**
     * Add Form Fields into Application.
     * Permission to manage the App is needed when obtaining data of live Apps.
     * API Tokens cannot be used with this API.
     * @param appId appId of the addFormFields
     * @param fields fields of the addFormFields
     * @param revision revision of the addFormFields
     * @return BasicResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    /**
     * Update Form Fields into Application. Permission to manage the App is needed
     * when obtaining data of live Apps. API Tokens cannot be used with this API.
     *
     * @param appId appId of the updateFormFields
     * @param fields fields of the updateFormFields
     * @param revision revision of the updateFormFields
     * @return BasicResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        DeleteFormFieldsRequest deleteFormFieldsCode = new DeleteFormFieldsRequest(app, fields, revision);
        String requestBody = parser.parseObject(deleteFormFieldsCode);

        JsonElement response = this.connection.request(ConnectionConstants.DELETE_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    /**
     * Gets the field layout info of a form in an App.
     * Perrmission to view records are needed when obtaining information of live Apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param appId appId of the getFormLayout
     * @param isPreview isPreview of the getFormLayout
     * @return FormLayout
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_LAYOUT;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        }
        GetFormLayoutRequest getFormLayoutRequest = new GetFormLayoutRequest(appId);
        String requestBody = parser.parseObject(getFormLayoutRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return parser.parseFormLayout(response);
    }

    public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout, Integer revision)
            throws KintoneAPIException {

        UpdateFormLayoutRequest updateFormLayoutRequest = new UpdateFormLayoutRequest(app, layout, revision);

        String apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        String requestBody = parser.parseObject(updateFormLayoutRequest);

        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public AddPreviewAppResponse addPreviewApp(String name, Integer space, Integer thread) throws KintoneAPIException {
        AddPreviewAppRequest addPreviewAppRequest = new AddPreviewAppRequest(name, space, thread);
        String apiRequest = ConnectionConstants.APP_PREVIEW;
        String requestBody = parser.parseObject(addPreviewAppRequest);

        JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseAddPreviewAppResponse(response);
    }

    public void deployAppSettings(ArrayList<PreviewAppRequest> apps, Boolean revert) throws KintoneAPIException {
        DeployAppSettingsRequest deployAppSettingsRequest = new DeployAppSettingsRequest(apps, revert);
        String apiRequest = ConnectionConstants.APP_DEPLOY_PREVIEW;
        String requestBody = parser.parseObject(deployAppSettingsRequest);

        this.connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
    }

    public GetAppDeployStatusResponse getAppDeployStatus(ArrayList<Integer> apps) throws KintoneAPIException {
        GetAppDeployStatusRequest deployStatusRequest = new GetAppDeployStatusRequest(apps);
        String apiRequest = ConnectionConstants.APP_DEPLOY_PREVIEW;
        String requestBody = parser.parseObject(deployStatusRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return parser.parseAppDeployStatusResponse(response);
    }

    /**
     * Gets the View settings of a an App.
     * Permission to view records are needed when obtaining information of live apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param app app of the getViews
     * @param lang lang of the getViews
     * @param isPreview isPreview of the getViews
     * @return GetViewsResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public GetViewsResponse getViews(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_VIEWS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        }

        GetViewsRequest getViewsRequest = new GetViewsRequest(app, lang);
        String requestBody = parser.parseObject(getViewsRequest);

        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);

        return (GetViewsResponse) parser.parseJson(response, GetViewsResponse.class);
    }

    /**
     * Update the View settings of a an App.
     * Permission to view records are needed when obtaining information of live apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param app app of the updateViews
     * @param views views of the updateViews
     * @param revision revision of the updateViews
     * @return UpdateViewsResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public UpdateViewsResponse updateViews(Integer app, HashMap<String, ViewModel> views, Integer revision)
            throws KintoneAPIException {

        UpdateViewsRequest updateViewsRequest = new UpdateViewsRequest(app, views, revision);

        String requestBody = parser.parseObject(updateViewsRequest);

        String apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);

        return (UpdateViewsResponse) parser.parseJson(response, UpdateViewsResponse.class);
    }

    /**
     * Gets the description, name, icon, revision and color theme of an App.
     * Permission to view records are needed when obtaining data of live apps.
     * App Management Permissions are needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param app app of the getGeneralSettings
     * @param lang lang of the getGeneralSettings
     * @param isPreview isPreview of the getGeneralSettings
     * @return GeneralSettings
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_SETTINGS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_SETTINGS_PREVIEW;
        }

        GetGeneralSettingsRequest getGeneralSettingsRequest = new GetGeneralSettingsRequest(app, lang);

        String requestBody = parser.parseObject(getGeneralSettingsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return (GeneralSettings) parser.parseJson(response, GeneralSettings.class);
    }

    /**
     * Updates the description, name, icon, revision and color theme of an App.
     * Permission to view records are needed when obtaining data of live apps.
     * App Management Permissions are needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     * @param app app of the updateGeneralSettings
     * @param generalSettings generalSettings of the updateGeneralSettings
     * @return BasicResponse
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public BasicResponse updateGeneralSettings(Integer app, GeneralSettings generalSettings)
            throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_SETTINGS_PREVIEW;

        UpdateGeneralSettingsRequest updateGeneralSettingsRequest = new UpdateGeneralSettingsRequest(app,
                generalSettings);
        String requestBody = parser.parseObject(updateGeneralSettingsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return (BasicResponse) parser.parseJson(response, BasicResponse.class);
    }
}
