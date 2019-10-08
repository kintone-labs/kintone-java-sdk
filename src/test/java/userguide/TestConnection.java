package userguide;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.app.AppModel;
import com.cybozu.kintone.client.model.app.basic.response.GetAppDeployStatusResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetViewsResponse;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.field.*;
import com.cybozu.kintone.client.model.app.form.field.input.AbstractInputField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.RadioButtonField;
import com.cybozu.kintone.client.model.app.form.field.related_record.ReferenceTable;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedRecordsField;
import com.cybozu.kintone.client.model.app.form.field.system.CategoryField;
import com.cybozu.kintone.client.model.app.form.layout.FieldLayout;
import com.cybozu.kintone.client.model.app.form.layout.FieldSize;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.form.layout.RowLayout;
import com.cybozu.kintone.client.model.bulk_request.BulkRequestResponse;
import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.record.response.AddRecordsResponse;
import com.cybozu.kintone.client.module.app.App;
import com.cybozu.kintone.client.module.record.Record;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestConnection {
    public static void main(String[] args) {
        // Init authenticationAuth
//        String username = "YOUR_USERNAME";
//        String password = "YOUR_PASSWORD";
//
//        Auth kintoneAuth = new Auth();
//        kintoneAuth.setPasswordAuth(username, password);
//
//        String kintoneDomain = "YOUR_DOMAIN.COM";
//        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);
//
//        // Init Record Module
//        Record kintoneRecordManager = new Record(kintoneConnection);
//
//        // execute GET RECORD API
//        int appID = 0; // Input app id
//        String query = "YOUR_QUERY";
//
//        try {
//            GetRecordsResponse response = kintoneRecordManager.getAllRecordsByQuery(appID, query);
//            System.out.println("Record: size records " + response.getRecords().size());
//            System.out.println("List Record Ids:");
//            for (int i = 0; i < response.getRecords().size(); i++) {
//                System.out.print(response.getRecords().get(i).get("$id").getValue() + ", ");
//            }
//
//            /*
//             * Expected Output:
//             *  Record: size records {SIZE_OF_RECORDS}
//             *  List Record Ids:
//             *  {RECORD_ID_1, RECORD_ID_2,...}
//             */
//        } catch (KintoneAPIException e) {
//            System.out.println("Error: " + e.getMessage());
//
//            /*
//             * Expected Output:
//             *  Error: {ERROR_MESSAGES}
//             */
//        }

        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        int appID = 0; // Input app id

        try {
            // Init App Module
            App kintoneApp = new App(kintoneConnection);
            AppModel app = kintoneApp.getApp(appID);
            System.out.println("App Name: " + app.getName());

            boolean isPreview = true;
            LanguageSetting languageSetting = LanguageSetting.EN; // LanguageSetting( EN | JA | ZH ). Ex: LanguageSetting.JA

            GetViewsResponse listViews = kintoneApp.getViews(appID, languageSetting, isPreview);
            System.out.println("App List Views: " + listViews.getViews());

            /*
             * Expected Output:
             *  App Name: {APP_NAME}
             *  App List Views: {LIST_VIEWS_OF_APP}
             */
        } catch (KintoneAPIException e) {
            System.out.println("Error: " + e.getMessage());

            /*
             * Expected Output:
             *  Error: {ERROR_MESSAGES}
             */
        }

    }

    @Test
    public void testGeneralSettings() throws KintoneAPIException {
        String username = "cybozu";
        String password = "cybozu";
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "https://test1-1.cybozu-dev.com/";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init App Module
        App kintoneApp = new App(kintoneConnection);

        Integer appId = 40;  // App Id
        LanguageSetting lang = LanguageSetting.EN;
        Boolean isPreview = true;
//        GeneralSettings res = kintoneApp.getGeneralSettings(appId, lang, isPreview);
//        System.out.println(res.getName());

//        GeneralSettings appGeneralSetting = new GeneralSettings();
//        appGeneralSetting.setName("UpdateGeneralSettings Sample code");
//        appGeneralSetting.setDescription("<div>Sample code of updateGeneralSettings</div>");
//        appGeneralSetting.setTheme(GeneralSettings.IconTheme.BLACK);
//
//        BasicResponse res = kintoneApp.updateGeneralSettings(appId, appGeneralSetting);
//        System.out.println(res.getRevision());

//        GetViewsResponse res = kintoneApp.getViews(appId);

        HashMap<String, com.cybozu.kintone.client.model.app.view.View> updateView = new HashMap<>();
        com.cybozu.kintone.client.model.app.view.View updateViewModel = new com.cybozu.kintone.client.model.app.view.View();
        updateViewModel.setName("ViewTest");
        updateViewModel.setSort("Record_number desc");
        updateViewModel.setType(com.cybozu.kintone.client.model.app.view.View.ViewType.LIST);
        updateViewModel.setIndex(1);

        // Create/Update fields of view
        ArrayList<String> fieldsView = new ArrayList<>();
        fieldsView.add("Record_number");
        fieldsView.add("Booth_Name");
        fieldsView.add("Created_datetime");

        updateViewModel.setFields(fieldsView);
        updateView.put("ViewTest", updateViewModel);

        AppModel appModel = new AppModel();

//        UpdateViewsResponse updateViews = kintoneApp.updateViews(appId, updateView);
//        updateViews.getViews().forEach((t, i)-> System.out.println(i.getName() + " : " + i.getId()));

    }

    @Test
    public void testForm() throws KintoneAPIException {
        String username = "cybozu";
        String password = "cybozu";
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "https://test1-1.cybozu-dev.com/";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init App Module
        App kintoneApp = new App(kintoneConnection);

        Integer appId = 40;  // App Id
        Integer revision = 0; // Latest_revision_of_the_settings

        // List fields of app
        ArrayList<ItemLayout> itemLayouts = new ArrayList<>();

        // Row Layout
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldLayouts = new ArrayList<>();

        FieldLayout fieldLayout = new FieldLayout();
        fieldLayout.setCode("Record_number");
        fieldLayout.setType(FieldType.RECORD_NUMBER.name());

        FieldSize fieldSize = new FieldSize();

        fieldSize.setWidth("200");
        fieldLayout.setSize(fieldSize);

        fieldLayouts.add(fieldLayout);

        fieldLayout = new FieldLayout();
        fieldLayout.setCode("Booth_Name");
        fieldLayout.setType(FieldType.SINGLE_LINE_TEXT.name());

        fieldSize = new FieldSize();

        fieldSize.setWidth("200");
        fieldLayout.setSize(fieldSize);

        fieldLayouts.add(fieldLayout);

        rowLayout.setFields(fieldLayouts);

        // Append layout
        itemLayouts.add(rowLayout);
//        BasicResponse res = kintoneApp.updateFormLayout(appId, itemLayouts);

//        FormLayout listForms = kintoneApp.getFormLayout(appId);

//        System.out.println(listForms.getLayout().get(0));
//        System.out.println(res.getRevision());

    }


    @Test
    public void testApp() throws KintoneAPIException {

        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init App Module
        App kintoneApp = new App(kintoneConnection);
        ArrayList<Integer> appIds = new ArrayList<>();
        appIds.add(2);
        appIds.add(41);

        GetAppDeployStatusResponse res = kintoneApp.getAppDeployStatus(appIds);
        for (int i = 0; i < res.getApps().size(); i++) {
            System.out.println(res.getApps().get(i).getApp());
        }
//        Integer appId = 2;
//        Integer revision = 0;
//        ArrayList<PreviewAppRequest> previewAppRequests = new ArrayList<>();
//        PreviewAppRequest appPreview = new PreviewAppRequest(appId, revision);
//        previewAppRequests.add(appPreview);
//
//        kintoneApp.deployAppSettings(previewAppRequests);

//        Integer offset = 10;
//        Integer limit = 50;
//
//        List<AppModel> listApps = kintoneApp.getApps(offset, limit);
//        ArrayList<Integer> spaceIds = new ArrayList();
//        spaceIds.add(7);


//        Integer spaceId = 7; // Space will add this app
//        Integer threadId = 7; // Thread will add this app
//
//        AddPreviewAppResponse addPreviewApp = kintoneApp.addPreviewApp("new app", spaceId, threadId);
//
//        System.out.println(addPreviewApp.getApp());
//        for (AppModel app: listAppsBySpaceIds) {
//            System.out.println(app.getName() + " : " + app.getAppId() + " : " + app.getSpaceId());
//        }


    }


    @Test
    public void testAuth() throws KintoneAPIException {
        String username = "cybozu";
        String password = "cybozu";
        Integer appId = 22;
        LanguageSetting languageSetting = LanguageSetting.EN; // LanguageSetting( EN | JA | ZH ). Ex: LanguageSetting.JA
        boolean isPreview = true;
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "https://test1-1.cybozu-dev.com/";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init App Module
        App kintoneApp = new App(kintoneConnection);
        AppModel app = kintoneApp.getApp(appId);
        System.out.println("App Name: " + app.getName());
//        GetViewsResponse listViews = kintoneApp.getViews(appId, languageSetting, isPreview);
//        System.out.println("App List Views: " + listViews.getViews());
        /*
         * Expected Output:
         *  App Name: {APP_NAME}
         *  App List Views: {LIST_VIEWS_OF_APP}
         */
//
//        ArrayList<Integer> deleteIds = new ArrayList<>();
//        deleteIds.add(recordId);
//        DeleteRecordsRequest deleteRecordsRequest = (DeleteRecordsRequest) kintoneBulkRequest
//                .deleteRecords(appId, deleteIds).execute().getResults().get(0);
//        System.out.println("Delete record : " + deleteRecordsRequest.toString());


//        String key = "X-HTTP-Method-Override";
//        String value = "GET";
//
//        // Init authenticationAuth
//        Auth kintoneAuth = new Auth();
//        String username = "YOUR_USERNAME";
//        String password = "YOUR_PASSWORD";
//        kintoneAuth.setPasswordAuth(username, password);
//
//        String kintoneDomain = "YOUR_DOMAIN.COM";
//        Connection connection = new Connection(kintoneDomain, kintoneAuth);
//        connection.setHeader(key, value);

//        String username = "YOUR_USERNAME";
//        String password = "YOUR_PASSWORD";
//        String proxyHost = "YOUR_PROXY_HOST";
//        Integer proxyPort = 1111; // Input your proxy port
//
//        // Init authenticationAuth
//        Auth kintoneAuth = new Auth();
//        kintoneAuth.setPasswordAuth(username, password);
//
//        String kintoneDomain = "YOUR_DOMAIN.COM";
//        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);
//
//        // Set ssl-secured proxy without proxyUsername & proxyPassword
//        kintoneConnection.setHttpsProxy(proxyHost, proxyPort);
//
//        // Set ssl-secured proxy with proxyUsername & proxyPassword
//        String proxyUsername = "YOUR_PROXY_USERNAME";
//        String proxyPassword = "YOUR_PROXY_PASSWORD";
//        kintoneConnection.setHttpsProxy(proxyHost, proxyPort, proxyUsername, proxyPassword);

    }

    @Test
    public void quickStart() {
        // Init authenticationAuth
        String username = "cybozu";
        String password = "cybozu";
        String kintoneDomain = "https://test1-1.cybozu-dev.com/";

        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneConnection);

        // execute GET RECORD API
        int appID = 2; // Input app id
        String query = "YOUR_QUERY";
        try {
//            GetRecordsResponse response = kintoneRecordManager.addAllRecords(appID);
//            System.out.println("Record: size records " + response.getRecords().size());
//            System.out.println("List Record Ids:");
//            for (int i = 0; i < response.getRecords().size(); i++) {
//                System.out.print(response.getRecords().get(i).get("$id").getValue() + ", ");
//            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void bulkAdd() {

        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init Record Module
        Record kintoneRecord = new Record(kintoneConnection);

        Integer appID = 0;  // Input your app id
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> record = new HashMap<>();
        FieldValue fv = new FieldValue();

        fv.setType(FieldType.SINGLE_LINE_TEXT); // Input your field type
        fv.setValue("VALUE_OF_FIELD_CODE");
        record.put("YOUR_APP_FIELD_CODE", fv);
        records.add(record);
        try {
            BulkRequestResponse bulkRequestResponse = kintoneRecord.addAllRecords(appID, records);
            AddRecordsResponse addRecordsResponse = (AddRecordsResponse) bulkRequestResponse.getResults().get(0);
            System.out.println("New record ID: " + addRecordsResponse.getIDs().get(0));

            /*
             * Expected Output:
             *  New record ID: {RECORD_ID}
             */
        } catch (KintoneAPIException e) {
            System.out.println("Error: " + e.getErrorResponse());

            /*
             * Expected Output:
             *  Error: {ERROR_MESSAGE}
             */
        }
    }

    @Test
    public void getRecord() {
        RelatedRecordsField recordsField = new RelatedRecordsField("YOUR_FIELD_CODE");
        ReferenceTable referenceTable = recordsField.getReferenceTable();

        CategoryField categoryField = new CategoryField("YOUR_FIELD_CODE");
        Boolean isEnabled = categoryField.getEnabled();
//        System.out.println(code);

        Integer appID = 0;  // Input your app id
        Integer revision = 0; // Latest_revision_of_the_settings
        HashMap<String, Field> properties = new HashMap<>();
        properties.put("YOUR_FIELD_CODE", new RadioButtonField("YOUR_FIELD_CODE"));
        FormFields formFields = new FormFields(appID, properties, revision);

        HashMap<String, Field> propertiesFields = formFields.getProperties();

        FieldGroup fieldGroup = new FieldGroup("YOUR_FIELD_CODE");
        Boolean openGroup = fieldGroup.getOpenGroup();

        FieldMapping fieldMapping = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_RELATED_CODE");
        String relatedFields = fieldMapping.getRelatedFields();

        SubTableField subTableField = new SubTableField("YOUR_FIELD_CODE");
        HashMap<String, AbstractInputField> fields = subTableField.getFields();

    }
}
