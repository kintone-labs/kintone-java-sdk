/**
 * MIT License
 * <p>
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
     *
     * @param appId appId of the getApp
     * @return AppModel
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public AppModel getApp(Integer appId) throws KintoneAPIException {
        GetAppRequest getAppRequest = new GetAppRequest(appId);
        String requestbody = parser.parseObject(getAppRequest);
        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP,
                requestbody);
        return parser.parseApp(response);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
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

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APPS,
                requestBody);
        return parser.parseApps(response);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
     * @param limit  limit of the getApps
     * @param offset offset of the getApps
     * @return ArrayList
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getApps(Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, limit);
    }

    public ArrayList<AppModel> getApps() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    public ArrayList<AppModel> getApps(Integer offset) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, null);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the id for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
     * @param ids    ids of the getAppsByIDs
     * @param offset offset of the getAppsByIDs
     * @param limit  limit of the getAppsByIDs
     * @return ArrayList
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, limit);
    }

    public ArrayList<AppModel> getAppsByIDs() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids) throws KintoneAPIException {
        return getApps(ids, null, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, null);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app code for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
     * @param codes  codes of the getAppsByCodes
     * @param offset offset of the getAppsByCodes
     * @param limit  limit of the getAppsByCodes
     * @return ArrayList
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, limit);
    }

    public ArrayList<AppModel> getAppsByCodes() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes) throws KintoneAPIException {
        return getApps(null, codes, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset) throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, null);
    }

    /**
     * Get apps which regardless belongs to space or not.
     * Require the app name for retrieve.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
     * @param name   name of the getAppsByName
     * @param offset offset of the getAppsByName
     * @param limit  limit of the getAppsByName
     * @return ArrayList
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByName(String name, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, limit);
    }

    public ArrayList<AppModel> getAppsByName() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsByName(String name) throws KintoneAPIException {
        return getApps(null, null, name, null, null, null);
    }

    public ArrayList<AppModel> getAppsByName(String name, Integer offset) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, null);
    }

    /**
     * Get apps which belongs to a specific space.
     * Permissions to view the Apps are needed.
     * API Tokens cannot be used with this API.
     *
     * @param spaceIds spaceIds of the getAppsBySpaceIDs
     * @param offset   offset of the getAppsBySpaceIDs
     * @param limit    limit of the getAppsBySpaceIDs
     * @return ArrayList
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset, Integer limit)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, limit);
    }

    public ArrayList<AppModel> getAppsBySpaceIDs() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, null, null);
    }

    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, null);
    }

    /**
     * Gets the list of fields and field settings of an App.
     * Permission to manage the App is needed when obtaining data of live Apps.
     * Permission to manage the App is needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param appId     appId of the getFormFields
     * @param lang      lang of the getFormFields
     * @param isPreview isPreview of the getFormFields
     * @return FormFields
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private FormFields getFormFieldsApp(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        if (lang == null) {
            lang = LanguageSetting.DEFAULT;
        }

        String apiRequest = ConnectionConstants.APP_FIELDS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        }

        GetFormFieldsRequest getFormFieldsRequest = new GetFormFieldsRequest(appId, lang);
        String requestBody = parser.parseObject(getFormFieldsRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest,
                requestBody);
        FormFields formfields = parser.parseFormFields(response);
        formfields.setApp(appId);

        return formfields;
    }

    public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        return getFormFieldsApp(appId, lang, isPreview);
    }

    public FormFields getFormFields(Integer appId) throws KintoneAPIException {
        return getFormFieldsApp(appId, null, false);
    }

    public FormFields getFormFields(Integer appId, LanguageSetting lang) throws KintoneAPIException {
        return getFormFieldsApp(appId, lang, false);
    }

    public FormFields getFormFields(Integer appId, Boolean isPreview) throws KintoneAPIException {
        return getFormFieldsApp(appId, null, isPreview);
    }


    /**
     * Add Form Fields into Application.
     * Permission to manage the App is needed when obtaining data of live Apps.
     * API Tokens cannot be used with this API.
     *
     * @param appId    appId of the addFormFields
     * @param fields   fields of the addFormFields
     * @param revision revision of the addFormFields
     * @return BasicResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private BasicResponse addFormFieldsApp(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException {
        return addFormFieldsApp(appId, fields, null);
    }

    public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {
        return addFormFieldsApp(appId, fields, revision);
    }

    /**
     * Update Form Fields into Application. Permission to manage the App is needed
     * when obtaining data of live Apps. API Tokens cannot be used with this API.
     *
     * @param appId    appId of the updateFormFields
     * @param fields   fields of the updateFormFields
     * @param revision revision of the updateFormFields
     * @return BasicResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private BasicResponse updateFormFieldsApp(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException {
        return updateFormFieldsApp(appId, fields, null);
    }

    public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {
        return updateFormFieldsApp(appId, fields, revision);
    }

    /**
     * Deletes fields from a form of an App.
     *
     * @param app
     * @param fields
     * @param revision
     * @return BasicResponse
     * @throws KintoneAPIException
     */

    private BasicResponse deleteFormFieldsApp(Integer app, ArrayList<String> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        DeleteFormFieldsRequest deleteFormFieldsCode = new DeleteFormFieldsRequest(app, fields, revision);
        String requestBody = parser.parseObject(deleteFormFieldsCode);

        JsonElement response = connection.request(ConnectionConstants.DELETE_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields) throws KintoneAPIException {
        return deleteFormFieldsApp(app, fields, null);
    }

    public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields, Integer revision)
            throws KintoneAPIException {
        return deleteFormFieldsApp(app, fields, revision);
    }

    /**
     * Gets the field layout info of a form in an App.
     * Perrmission to view records are needed when obtaining information of live Apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param appId     appId of the getFormLayout
     * @param isPreview isPreview of the getFormLayout
     * @return FormLayout
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private FormLayout getFormLayoutApp(Integer appId, Boolean isPreview) throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_LAYOUT;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        }
        GetFormLayoutRequest getFormLayoutRequest = new GetFormLayoutRequest(appId);
        String requestBody = parser.parseObject(getFormLayoutRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return parser.parseFormLayout(response);
    }

    public FormLayout getFormLayout(Integer appId) throws KintoneAPIException {
        return getFormLayoutApp(appId, false);
    }

    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        return getFormLayoutApp(appId, isPreview);
    }

    /**
     * Updates the field layout info of a form in an App.
     *
     * @param app
     * @param layout
     * @param revision
     * @return BasicResponse
     * @throws KintoneAPIException
     */

    private BasicResponse updateFormLayoutApp(Integer app, ArrayList<ItemLayout> layout, Integer revision)
            throws KintoneAPIException {

        UpdateFormLayoutRequest updateFormLayoutRequest = new UpdateFormLayoutRequest(app, layout, revision);

        String apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        String requestBody = parser.parseObject(updateFormLayoutRequest);

        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout) throws KintoneAPIException {
        return updateFormLayoutApp(app, layout, null);
    }

    public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout, Integer revision)
            throws KintoneAPIException {
        return updateFormLayoutApp(app, layout, revision);
    }

    /**
     * Creates a preview App.
     *
     * @param name
     * @param space
     * @param thread
     * @return AddPreviewAppResponse
     * @throws KintoneAPIException
     */

    private AddPreviewAppResponse addPreviewAppResponse(String name, Integer space, Integer thread) throws KintoneAPIException {
        AddPreviewAppRequest addPreviewAppRequest = new AddPreviewAppRequest(name, space, thread);
        String apiRequest = ConnectionConstants.APP_PREVIEW;
        String requestBody = parser.parseObject(addPreviewAppRequest);

        JsonElement response = connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseAddPreviewAppResponse(response);
    }

    public AddPreviewAppResponse addPreviewApp(String name) throws KintoneAPIException {
        return addPreviewAppResponse(name, null, null);
    }

    public AddPreviewAppResponse addPreviewApp(String name, Integer space) throws KintoneAPIException {
        return addPreviewAppResponse(name, space, null);
    }

    public AddPreviewAppResponse addPreviewApp(String name, Integer space, Integer thread) throws KintoneAPIException {
        return addPreviewAppResponse(name, space, thread);
    }

    /**
     * Updates the settings of a pre-live App to the live App.
     *
     * @param apps
     * @param revert
     * @throws KintoneAPIException
     */

    private void deployAppSettingResult(ArrayList<PreviewAppRequest> apps, Boolean revert) throws KintoneAPIException {
        DeployAppSettingsRequest deployAppSettingsRequest = new DeployAppSettingsRequest(apps, revert);
        String apiRequest = ConnectionConstants.APP_DEPLOY_PREVIEW;
        String requestBody = parser.parseObject(deployAppSettingsRequest);

        connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
    }

    public void deployAppSettings(ArrayList<PreviewAppRequest> apps) throws KintoneAPIException {
        deployAppSettingResult(apps, false);
    }

    public void deployAppSettings(ArrayList<PreviewAppRequest> apps, Boolean revert) throws KintoneAPIException {
        deployAppSettingResult(apps, revert);
    }

    /**
     * Updates the settings of a pre-live App to the live App.
     *
     * @param apps
     * @return GetAppDeployStatusResponse
     * @throws KintoneAPIException
     */
    public GetAppDeployStatusResponse getAppDeployStatus(ArrayList<Integer> apps) throws KintoneAPIException {
        GetAppDeployStatusRequest deployStatusRequest = new GetAppDeployStatusRequest(apps);
        String apiRequest = ConnectionConstants.APP_DEPLOY_PREVIEW;
        String requestBody = parser.parseObject(deployStatusRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return parser.parseAppDeployStatusResponse(response);
    }

    /**
     * Gets the View settings of a an App.
     * Permission to view records are needed when obtaining information of live apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param app       app of the getViews
     * @param lang      lang of the getViews
     * @param isPreview isPreview of the getViews
     * @return GetViewsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private GetViewsResponse getViewsApp(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_VIEWS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        }

        GetViewsRequest getViewsRequest = new GetViewsRequest(app, lang);
        String requestBody = parser.parseObject(getViewsRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);

        return (GetViewsResponse) parser.parseJson(response, GetViewsResponse.class);
    }

    public GetViewsResponse getViews(Integer app) throws KintoneAPIException {
        return getViewsApp(app, null, false);
    }

    public GetViewsResponse getViews(Integer app, LanguageSetting lang) throws KintoneAPIException {
        return getViewsApp(app, lang, false);
    }

    public GetViewsResponse getViews(Integer app, Boolean isPreview) throws KintoneAPIException {
        return getViewsApp(app, null, isPreview);
    }

    public GetViewsResponse getViews(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        return getViewsApp(app, lang, isPreview);
    }

    /**
     * Update the View settings of a an App.
     * Permission to view records are needed when obtaining information of live apps.
     * App Management Permissions are needed when obtaining information of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param app      app of the updateViews
     * @param views    views of the updateViews
     * @param revision revision of the updateViews
     * @return UpdateViewsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private UpdateViewsResponse updateViewsApp(Integer app, HashMap<String, ViewModel> views, Integer revision)
            throws KintoneAPIException {

        UpdateViewsRequest updateViewsRequest = new UpdateViewsRequest(app, views, revision);

        String requestBody = parser.parseObject(updateViewsRequest);

        String apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);

        return (UpdateViewsResponse) parser.parseJson(response, UpdateViewsResponse.class);
    }

    public UpdateViewsResponse updateViews(Integer app, HashMap<String, ViewModel> views) throws KintoneAPIException {
        return updateViewsApp(app, views, null);
    }

    public UpdateViewsResponse updateViews(Integer app, HashMap<String, ViewModel> views, Integer revision)
            throws KintoneAPIException {
        return updateViewsApp(app, views, revision);
    }

    /**
     * Gets the description, name, icon, revision and color theme of an App.
     * Permission to view records are needed when obtaining data of live apps.
     * App Management Permissions are needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param app       app of the getGeneralSettings
     * @param lang      lang of the getGeneralSettings
     * @param isPreview isPreview of the getGeneralSettings
     * @return GeneralSettings
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    private GeneralSettings getGeneralSettingsApp(Integer app, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_SETTINGS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_SETTINGS_PREVIEW;
        }

        GetGeneralSettingsRequest getGeneralSettingsRequest = new GetGeneralSettingsRequest(app, lang);

        String requestBody = parser.parseObject(getGeneralSettingsRequest);
        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);
        return (GeneralSettings) parser.parseJson(response, GeneralSettings.class);
    }

    public GeneralSettings getGeneralSettings(Integer app) throws KintoneAPIException {
        return getGeneralSettingsApp(app, null, false);
    }

    public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang) throws KintoneAPIException {
        return getGeneralSettingsApp(app, lang, false);
    }

    public GeneralSettings getGeneralSettings(Integer app, Boolean isPreview) throws KintoneAPIException {
        return getGeneralSettingsApp(app, null, isPreview);
    }

    public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {
        return getGeneralSettingsApp(app, lang, isPreview);
    }

    /**
     * Updates the description, name, icon, revision and color theme of an App.
     * Permission to view records are needed when obtaining data of live apps.
     * App Management Permissions are needed when obtaining data of pre-live settings.
     * API Tokens cannot be used with this API.
     *
     * @param app             app of the updateGeneralSettings
     * @param generalSettings generalSettings of the updateGeneralSettings
     * @return BasicResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public BasicResponse updateGeneralSettings(Integer app, GeneralSettings generalSettings)
            throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_SETTINGS_PREVIEW;

        UpdateGeneralSettingsRequest updateGeneralSettingsRequest = new UpdateGeneralSettingsRequest(app,
                generalSettings);
        String requestBody = parser.parseObject(updateGeneralSettingsRequest);
        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return (BasicResponse) parser.parseJson(response, BasicResponse.class);
    }
}
