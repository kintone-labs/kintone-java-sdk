/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.app;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.*;
import com.cybozu.kintone.client.model.app.app.AppModel;
import com.cybozu.kintone.client.model.app.basic.PreviewApp;
import com.cybozu.kintone.client.model.app.basic.request.*;
import com.cybozu.kintone.client.model.app.basic.response.*;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.general_settings.GeneralSettings;
import com.cybozu.kintone.client.model.app.views.View;
import com.cybozu.kintone.client.module.parser.AppParser;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

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
     * Get app by app id
     *
     * @param appId id of kintone App
     * @return AppModel
     * @throws KintoneAPIException
     */
    public AppModel getApp(Integer appId) throws KintoneAPIException {
        GetAppRequest getAppRequest = new GetAppRequest(appId);
        String requestBody = parser.parseObject(getAppRequest);
        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, requestBody);
        return parser.parseApp(response);
    }


    /**
     * Get list app by offset and limit
     *
     * @param offset The offset of data result
     * @param limit  The limit number of result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getApps(Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, limit);
    }

    /**
     * Get list app
     *
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */

    public ArrayList<AppModel> getApps() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    /**
     * Get list app by offset
     *
     * @param offset The offset of data result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */

    public ArrayList<AppModel> getApps(Integer offset) throws KintoneAPIException {
        return getApps(null, null, null, null, offset, null);
    }

    /**
     * Get list app by list id, offset and limit
     *
     * @param ids    list id of kintone App
     * @param offset The offset of data result
     * @param limit  The limit number of result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, limit);
    }

    /**
     * Get list app
     *
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByIDs() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    /**
     * Get list app by list id
     *
     * @param ids list id of kintone App
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids) throws KintoneAPIException {
        return getApps(ids, null, null, null, null, null);
    }

    /**
     * Get list app by list id, offset
     *
     * @param ids    list id of kintone App
     * @param offset The offset of data result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset) throws KintoneAPIException {
        return getApps(ids, null, null, null, offset, null);
    }

    /**
     * Get list app by list codes, offset and limit
     *
     * @param codes  list codes of kintone App
     * @param offset The offset of data result
     * @param limit  The limit number of result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset, Integer limit)
            throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, limit);
    }

    /**
     * Get list app by list codes, offset and limit
     *
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByCodes() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    /**
     * Get list app by list codes, offset and limit
     *
     * @param codes list codes of kintone App
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes) throws KintoneAPIException {
        return getApps(null, codes, null, null, null, null);
    }

    /**
     * Get list app by list codes, offset and limit
     *
     * @param codes  list codes of kintone App
     * @param offset The offset of data result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset) throws KintoneAPIException {
        return getApps(null, codes, null, null, offset, null);
    }

    /**
     * Get list app by LIKE name, offset and limit
     *
     * @param name   The name of kintone App
     * @param offset The offset of data result
     * @param limit  The limit number of result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByName(String name, Integer offset, Integer limit) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, limit);
    }

    /**
     * Get list app by LIKE name, offset and limit
     *
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByName() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    /**
     * Get list app by LIKE name, offset and limit
     *
     * @param name The name of kintone App
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByName(String name) throws KintoneAPIException {
        return getApps(null, null, name, null, null, null);
    }

    /**
     * Get list app by LIKE name, offset and limit
     *
     * @param name   The name of kintone App
     * @param offset The offset of data result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsByName(String name, Integer offset) throws KintoneAPIException {
        return getApps(null, null, name, null, offset, null);
    }

    /**
     * Get list app by list space ids, offset and limit
     *
     * @param spaceIds List space ids of kintone App
     * @param offset   The offset of data result
     * @param limit    The limit number of result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset, Integer limit)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, limit);
    }

    /**
     * Get list app by list space ids, offset and limit
     *
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsBySpaceIDs() throws KintoneAPIException {
        return getApps(null, null, null, null, null, null);
    }

    /**
     * Get list app by list space ids, offset and limit
     *
     * @param spaceIds List space ids of kintone App
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, null, null);
    }

    /**
     * Get list app by list space ids, offset and limit
     *
     * @param spaceIds List space ids of kintone App
     * @param offset   The offset of data result
     * @return ArrayList<AppModel>
     * @throws KintoneAPIException
     */
    public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset)
            throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, offset, null);
    }

    /**
     * Get the list of fields and field settings of an App.
     *
     * @param appId     appId of kintone App
     * @param lang      language of LanguageSetting (EN, JP, ZH)
     * @param isPreview option preview
     * @return FormFields
     * @throws KintoneAPIException
     */
    public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        return getFormFieldsApp(appId, lang, isPreview);
    }

    /**
     * Get the list of fields and field settings of an App.
     *
     * @param appId appId of kintone App
     * @return FormFields
     * @throws KintoneAPIException
     */
    public FormFields getFormFields(Integer appId) throws KintoneAPIException {
        return getFormFieldsApp(appId, null, false);
    }

    /**
     * Get the list of fields and field settings of an App.
     *
     * @param appId appId of kintone App
     * @param lang  language of LanguageSetting (EN, JP, ZH)
     * @return FormFields
     * @throws KintoneAPIException
     */
    public FormFields getFormFields(Integer appId, LanguageSetting lang) throws KintoneAPIException {
        return getFormFieldsApp(appId, lang, false);
    }

    /**
     * Get the list of fields and field settings of an App.
     *
     * @param appId     appId of kintone App
     * @param isPreview option preview
     * @return FormFields
     * @throws KintoneAPIException
     */
    public FormFields getFormFields(Integer appId, Boolean isPreview) throws KintoneAPIException {
        return getFormFieldsApp(appId, null, isPreview);
    }


    /**
     * Add Form Fields into Application.
     *
     * @param appId  appId of kintone App
     * @param fields formFields which will add to form of kintone app
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException {
        return addFormFieldsApp(appId, fields, null);
    }

    /**
     * Add Form Fields into Application.
     *
     * @param appId    appId of kintone App
     * @param fields   formFields which will add to form of kintone app
     * @param revision specify the revision number of the settings that will be deployed
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {
        return addFormFieldsApp(appId, fields, revision);
    }

    /**
     * Update Form Fields into Application.
     *
     * @param appId  appId of kintone App
     * @param fields formFields which will add to form of kintone app
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException {
        return updateFormFieldsApp(appId, fields, null);
    }

    /**
     * Update Form Fields into Application.
     *
     * @param appId    appId of kintone App
     * @param fields   formFields which will add to form of kintone app
     * @param revision specify the revision number of the settings that will be deployed
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {
        return updateFormFieldsApp(appId, fields, revision);
    }

    /**
     * Delete fields from a form of an App.
     *
     * @param app    appId of kintone App
     * @param fields formFields which will add to form of kintone app
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields) throws KintoneAPIException {
        return deleteFormFieldsApp(app, fields, null);
    }

    /**
     * Delete fields from a form of an App.
     *
     * @param app      appId of kintone App
     * @param fields   formFields which will add to form of kintone app
     * @param revision specify the revision number of the settings that will be deployed
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields, Integer revision)
            throws KintoneAPIException {
        return deleteFormFieldsApp(app, fields, revision);
    }

    /**
     * Get the field layout info of a form in an App.
     *
     * @param appId appId of kintone App
     * @return FormLayout
     * @throws KintoneAPIException
     */
    public FormLayout getFormLayout(Integer appId) throws KintoneAPIException {
        return getFormLayoutApp(appId, false);
    }

    /**
     * Get the field layout info of a form in an App.
     *
     * @param appId     appId of kintone App
     * @param isPreview option preview
     * @return FormLayout
     * @throws KintoneAPIException
     */
    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        return getFormLayoutApp(appId, isPreview);
    }

    /**
     * Update the field layout info of a form in an App.
     *
     * @param app    appId of kintone App
     * @param layout list item layout of kintone App
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout) throws KintoneAPIException {
        return updateFormLayoutApp(app, layout, null);
    }

    /**
     * Update the field layout info of a form in an App.
     *
     * @param app      appId of kintone App
     * @param layout   list item layout of kintone App
     * @param revision specify the revision number of the settings that will be deployed
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout, Integer revision)
            throws KintoneAPIException {
        return updateFormLayoutApp(app, layout, revision);
    }

    /**
     * Creates a preview App.
     *
     * @param name name of kintone App
     * @return AddPreviewAppResponse
     * @throws KintoneAPIException
     */
    public PreviewApp addPreviewApp(String name) throws KintoneAPIException {
        return addPreviewAppResult(name, null, null);
    }

    /**
     * Creates a preview App.
     *
     * @param name  name of kintone App
     * @param space id of kintone App
     * @return AddPreviewAppResponse
     * @throws KintoneAPIException
     */
    public PreviewApp addPreviewApp(String name, Integer space) throws KintoneAPIException {
        return addPreviewAppResult(name, space, null);
    }

    /**
     * Creates a preview App.
     *
     * @param name   name of kintone App
     * @param space  id of kintone App
     * @param thread thread id of kintone App
     * @return AddPreviewAppResponse
     * @throws KintoneAPIException
     */
    public PreviewApp addPreviewApp(String name, Integer space, Integer thread) throws KintoneAPIException {
        return addPreviewAppResult(name, space, thread);
    }

    /**
     * Update the settings of a pre-live App to the live App.
     *
     * @param apps list preview app
     * @throws KintoneAPIException
     */
    public void deployAppSettings(ArrayList<PreviewApp> apps) throws KintoneAPIException {
        deployAppSettingsResult(apps, false);
    }

    /**
     * Update the settings of a pre-live App to the live App.
     *
     * @param apps   list preview app
     * @param revert option revert
     * @throws KintoneAPIException
     */
    public void deployAppSettings(ArrayList<PreviewApp> apps, Boolean revert) throws KintoneAPIException {
        deployAppSettingsResult(apps, revert);
    }

    /**
     * Update the settings of a pre-live App to the live App.
     *
     * @param apps list app i
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
     * Get the View settings of a an App.
     *
     * @param app app id of kintone App
     * @return GetViewsResponse
     * @throws KintoneAPIException
     */
    public GetViewsResponse getViews(Integer app) throws KintoneAPIException {
        return getViewsApp(app, null, false);
    }

    /**
     * Get the View settings of a an App.
     *
     * @param app  app id of kintone App
     * @param lang language of LanguageSetting
     * @return GetViewsResponse
     * @throws KintoneAPIException
     */
    public GetViewsResponse getViews(Integer app, LanguageSetting lang) throws KintoneAPIException {
        return getViewsApp(app, lang, false);
    }

    /**
     * Get the View settings of a an App.
     *
     * @param app       app id of kintone App
     * @param isPreview option preview
     * @return GetViewsResponse
     * @throws KintoneAPIException
     */
    public GetViewsResponse getViews(Integer app, Boolean isPreview) throws KintoneAPIException {
        return getViewsApp(app, null, isPreview);
    }

    /**
     * Get the View settings of a an App.
     *
     * @param app       app id of kintone App
     * @param lang      language of LanguageSetting
     * @param isPreview option preview
     * @return GetViewsResponse
     * @throws KintoneAPIException
     */
    public GetViewsResponse getViews(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        return getViewsApp(app, lang, isPreview);
    }

    /**
     * Update the View settings of a an App.
     *
     * @param app   app id of kintone App
     * @param views list views of kintone App
     * @return UpdateViewsResponse
     * @throws KintoneAPIException
     */
    public UpdateViewsResponse updateViews(Integer app, HashMap<String, View> views) throws KintoneAPIException {
        return updateViewsApp(app, views, null);
    }

    /**
     * Update the View settings of a an App.
     *
     * @param app      app id of kintone App
     * @param views    list views of kintone App
     * @param revision specify the revision number of the settings that will be deployed.
     * @return UpdateViewsResponse
     * @throws KintoneAPIException
     */
    public UpdateViewsResponse updateViews(Integer app, HashMap<String, View> views, Integer revision)
            throws KintoneAPIException {
        return updateViewsApp(app, views, revision);
    }

    /**
     * Get the description, name, icon, revision and color theme of an App.
     *
     * @param app       app id of kintone App
     * @return GeneralSettings
     * @throws KintoneAPIException
     */
    public GeneralSettings getGeneralSettings(Integer app) throws KintoneAPIException {
        return getGeneralSettingsApp(app, null, false);
    }

    /**
     * Get the description, name, icon, revision and color theme of an App.
     *
     * @param app  app id of kintone App
     * @param lang language of LanguageSetting
     * @return GeneralSettings
     * @throws KintoneAPIException
     */
    public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang) throws KintoneAPIException {
        return getGeneralSettingsApp(app, lang, false);
    }

    /**
     * Get the description, name, icon, revision and color theme of an App.
     *
     * @param app       app id of kintone App
     * @param isPreview option preview
     * @return GeneralSettings
     * @throws KintoneAPIException
     */
    public GeneralSettings getGeneralSettings(Integer app, Boolean isPreview) throws KintoneAPIException {
        return getGeneralSettingsApp(app, null, isPreview);
    }

    /**
     * Get the description, name, icon, revision and color theme of an App.
     *
     * @param app       app id of kintone App
     * @param lang      language of LanguageSetting
     * @param isPreview option preview
     * @return GeneralSettings
     * @throws KintoneAPIException
     */
    public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {
        return getGeneralSettingsApp(app, lang, isPreview);
    }

    /**
     * Update the description, name, icon, revision and color theme of an App.
     *
     * @param app             app id of kintone App
     * @param generalSettings generalSettings of the updateGeneralSettings
     * @return BasicResponse
     * @throws KintoneAPIException
     */
    public BasicResponse updateGeneralSettings(Integer app, GeneralSettings generalSettings) throws KintoneAPIException {
        String apiRequest = ConnectionConstants.APP_SETTINGS_PREVIEW;

        UpdateGeneralSettingsRequest updateGeneralSettingsRequest = new UpdateGeneralSettingsRequest(app,
                generalSettings);
        String requestBody = parser.parseObject(updateGeneralSettingsRequest);
        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return (BasicResponse) parser.parseJson(response, BasicResponse.class);
    }

    /**
     * PRIVATE FUNCTIONS
     */
    private ArrayList<AppModel> getApps(ArrayList<Integer> ids, ArrayList<String> codes, String name, ArrayList<Integer> spaceIds,
                                        Integer offset, Integer limit) throws KintoneAPIException {
        GetAppsRequest getAppsRequest = new GetAppsRequest(ids, codes, name, spaceIds, offset, limit);
        String requestBody = parser.parseObject(getAppsRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APPS,
                requestBody);
        return parser.parseApps(response);
    }

    private FormFields getFormFieldsApp(Integer appId, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {
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

    private BasicResponse addFormFieldsApp(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    private BasicResponse updateFormFieldsApp(Integer appId, HashMap<String, Field> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        AddUpdateFormFieldsRequest addFormFields = new AddUpdateFormFieldsRequest(appId, fields, revision);
        String requestBody = parser.parseObject(addFormFields);

        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    private BasicResponse deleteFormFieldsApp(Integer app, ArrayList<String> fields, Integer revision)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        DeleteFormFieldsRequest deleteFormFieldsCode = new DeleteFormFieldsRequest(app, fields, revision);
        String requestBody = parser.parseObject(deleteFormFieldsCode);

        JsonElement response = connection.request(ConnectionConstants.DELETE_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

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

    private BasicResponse updateFormLayoutApp(Integer app, ArrayList<ItemLayout> layout, Integer revision)
            throws KintoneAPIException {

        UpdateFormLayoutRequest updateFormLayoutRequest = new UpdateFormLayoutRequest(app, layout, revision);

        String apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        String requestBody = parser.parseObject(updateFormLayoutRequest);

        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);
        return parser.parseBasicResponse(response);
    }

    public PreviewApp addPreviewAppResult(String name, Integer space, Integer thread) throws KintoneAPIException {
        AddPreviewAppRequest addPreviewAppRequest = new AddPreviewAppRequest(name, space, thread);
        String apiRequest = ConnectionConstants.APP_PREVIEW;
        String requestBody = parser.parseObject(addPreviewAppRequest);

        JsonElement response = connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
        return parser.parseAddPreviewAppResponse(response);
    }

    public void deployAppSettingsResult(ArrayList<PreviewApp> apps, Boolean revert) throws KintoneAPIException {
        DeployAppSettingsRequest deployAppSettingsRequest = new DeployAppSettingsRequest(apps, revert);
        String apiRequest = ConnectionConstants.APP_DEPLOY_PREVIEW;
        String requestBody = parser.parseObject(deployAppSettingsRequest);

        connection.request(ConnectionConstants.POST_REQUEST, apiRequest, requestBody);
    }

    private GetViewsResponse getViewsApp(Integer app, LanguageSetting lang, Boolean isPreview)
            throws KintoneAPIException {

        String apiRequest = ConnectionConstants.APP_VIEWS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        }

        GetViewsRequest getViewsRequest = new GetViewsRequest(app, lang);
        String requestBody = parser.parseObject(getViewsRequest);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, requestBody);

        return (GetViewsResponse) parser.parseJson(response, GetViewsResponse.class);
    }

    private UpdateViewsResponse updateViewsApp(Integer app, HashMap<String, View> views, Integer revision)
            throws KintoneAPIException {

        UpdateViewsRequest updateViewsRequest = new UpdateViewsRequest(app, views, revision);

        String requestBody = parser.parseObject(updateViewsRequest);

        String apiRequest = ConnectionConstants.APP_VIEWS_PREVIEW;
        JsonElement response = connection.request(ConnectionConstants.PUT_REQUEST, apiRequest, requestBody);

        return (UpdateViewsResponse) parser.parseJson(response, UpdateViewsResponse.class);
    }

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
}
