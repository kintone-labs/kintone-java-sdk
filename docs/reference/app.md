# App

Gets general information of an App, including the name, description, related Space, creator and updater information.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## Constructor

**Declaration**
```
public App(Connection connection)
```

**Parameter**

| Name| Description |
| --- | --- |
| connection | The connection module of this SDK ([Connection](../connection)).

**Sample code**

<details class="tab-container" open>
<Summary>Init app module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
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

</pre>

</details>

## Methods

### getApp

> Get single app

**Declaration**
```
public AppModel getApp(Integer appId) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| appId | The kintone app id

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

<strong class="tab-name">Source code</strong>
<pre class="inline-code">

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
    Integer appId = 0; // Input your app id
    
    AppModel app = kintoneApp.getApp(appId);

</pre>

</details>

### getApps

> Get multiple apps

**Declaration**
```
public ArrayList<AppModel> getApps() throws KintoneAPIException
public ArrayList<AppModel> getApps(Integer offset) throws KintoneAPIException
public ArrayList<AppModel> getApps(Integer offset, Integer limit) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| offset | The offset of data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>Get Apps</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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
    
    // Get all apps 
    List<AppModel> allApps = kintoneApp.getApps();

    // Get list apps based on offset, limit
    Integer offset = 0;    // Input your offset number  
    Integer limit = 5; // Input your limit number 
    List<AppModel> listApps = kintoneApp.getApps(offset, limit);

</pre>

</details>

### getAppsByIDs

> Get multiple apps by list of ids

**Declaration**
```
public ArrayList<AppModel> getAppsByIDs() throws KintoneAPIException
public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids) throws KintoneAPIException
public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset) throws KintoneAPIException
public ArrayList<AppModel> getAppsByIDs(ArrayList<Integer> ids, Integer offset, Integer limit) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| ids | The array of app ids
| offset | The offset of data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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
    
    // Add list ids
    ArrayList<Integer> ids = new ArrayList();
    ids.add(0); 
    ids.add(1);
    ids.add(2);

    // Get list apps by list ids
    List<AppModel> listAppsByIds = kintoneApp.getAppsByIDs(ids);

    // Get list apps by ids, offset, limit
    Integer offset = 0;    // Input your offset number  
    Integer limit = 5; // Input your limit number 
    List<AppModel> listApps = kintoneApp.getAppsByIDs(ids, offset, limit);
</pre>

</details>

### getAppsByCodes

> Get multiple apps by a list of codes

**Declaration**
```
public ArrayList<AppModel> getAppsByCodes() throws KintoneAPIException
public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes) throws KintoneAPIException 
public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset) throws KintoneAPIException 
public ArrayList<AppModel> getAppsByCodes(ArrayList<String> codes, Integer offset, Integer limit) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| codes | The array of app codes
| offset | The offset of data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Codes</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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
    
    // Add list codes
    ArrayList<String> codes = new ArrayList();
    codes.add("YOUR_APP_CODE");
    codes.add("YOUR_APP_CODE");

    // Get list apps by list code
    List<AppModel> listAppsByCodes = kintoneApp.getAppsByCodes(codes);

    // Get list apps by code, offset, limit
    Integer offset = 0;    // Input your offset number  
    Integer limit = 5; // Input your limit number 
    List<AppModel> listApps = kintoneApp.getAppsByCodes(codes, offset, limit);

</pre>

</details>

### getAppsByName

> Get multiple apps by name

**Declaration**
```
public ArrayList<AppModel> getAppsByName() throws KintoneAPIException
public ArrayList<AppModel> getAppsByName(String name) throws KintoneAPIException
public ArrayList<AppModel> getAppsByName(String name, Integer offset) throws KintoneAPIException 
public ArrayList<AppModel> getAppsByName(String name, Integer offset, Integer limit) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| name | The App Name.<br>A partial search will be used, and the search will be case insensitive.<br>The localized name of the App in the user's locale will also be included in the search.
| offset | The offset of data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Name</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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
    
    String name = "YOUR_APP_NAME";

    // Get list apps by list name
    List<AppModel> listAppsByName = kintoneApp.getAppsByName(name);

    // Get by name, offset, limit
    Integer offset = 0;    // Input your offset number  
    Integer limit = 5; // Input your limit number 
    List<AppModel> listApps = kintoneApp.getAppsByName(name, offset, limit);
    
</pre>

</details>

### getAppsBySpaceIDs

> Get multiple apps by list of space's ids

**Declaration**
```
public ArrayList<AppModel> getAppsBySpaceIDs() throws KintoneAPIException
public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds) throws KintoneAPIException 
public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset) throws KintoneAPIException
public ArrayList<AppModel> getAppsBySpaceIDs(ArrayList<Integer> spaceIds, Integer offset, Integer limit) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| spaceIds | The array of space ids
| offset | The offset of data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Space IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
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
    
    ArrayList<Integer> spaceIds = new ArrayList();
    spaceIds.add(0);    // Input your space id

    // Get list apps by list space id
    List<AppModel> listAppsBySpaceIds = kintoneApp.getAppsBySpaceIDs(spaceIds);

    // Get by space id, offset, limit
    Integer offset = 0;    // Input your offset number  
    Integer limit = 5; // Input your limit number 
    List<AppModel> listApps = kintoneApp.getAppsBySpaceIDs(spaceIds, offset, limit);
</pre>

</details>

### addPreviewApp

Creates a preview App.

**Declaration**
```
public PreviewApp addPreviewApp(String name) throws KintoneAPIException 
public PreviewApp addPreviewApp(String name, Integer space) throws KintoneAPIException 
public PreviewApp addPreviewApp(String name, Integer space, Integer thread) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| name |The App name.<br>The maximum length is 64 characters.|
| space | The Space ID of where the App will be created.|
| thread | The Thread ID of the thread in the Space where the App will be created.<br>It is recommended to ignore this parameter so that Apps are created in the default thread. <br>There is currently no helpful reason to create Apps in threads other than the default thread, as there are no visual representations in kintone of Apps being related to threads.<br> There are only visual representations of Apps being related to Spaces.|

**Sample code**

<details class="tab-container" open>
<Summary>add PreviewApp</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    String name = "YOUR_APP_NAME";

    // Add preview app by name
    PreviewApp addPreviewAppByName = kintoneApp.addPreviewApp(name);

    // add preview app
    Integer spaceId = 0; // Space will add this app
    Integer threadId = 0; // Thread will add this app
    PreviewApp addPreviewApp = kintoneApp.addPreviewApp(name, spaceId, threadId);

</pre>

</details>


### deployAppSettings

Updates the settings of a pre-live App to the live App.

**Declaration**
```
public void deployAppSettings(ArrayList<PreviewAppRequest> apps) throws KintoneAPIException 
public void deployAppSettings(ArrayList<PreviewAppRequest> apps, Boolean revert) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| apps | The list of Apps to deploy the pre-live settings to the live Apps. The Maximum limit is 300.<br>If Apps are being deployed to Guest Spaces, Apps can only be deployed to the same Guest Space..|
| revert | Default value: **false**.<br> Specify **true** to cancel all changes made to the pre-live settings. The pre-live settings will be reverted back to the current settings of the live app.|

**Sample code**

<details class="tab-container" open>
<Summary>deploy AppSettings</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id
    Integer revision = 0;   // Revision of application to deploy
    ArrayList<PreviewAppRequest> previewAppRequests = new ArrayList<>();
    PreviewAppRequest appPreview = new PreviewAppRequest(appId, revision);
    previewAppRequests.add(appPreview);

    kintoneApp.deployAppSettings(previewAppRequests);

    // Deploy with revert option 
    kintoneApp.deployAppSettings(previewAppRequests, true);
</pre>

</details>

### getAppDeployStatus

Updates the settings of a pre-live App to the live App.

**Declaration**
```
public GetAppDeployStatusResponse getAppDeployStatus(ArrayList<Integer> apps) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| apps | The list of Apps to check the deploy statuses of. The Maximum limit is 300.<br>If Apps in Guest Spaces are specified, all Apps specified in the request must belong to that Guest Space.|

**Sample code**

<details class="tab-container" open>
<Summary>get App DeployStatus</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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
    appIds.add(0);  // Input list app id

    GetAppDeployStatusResponse res = kintoneApp.getAppDeployStatus(appIds);

</pre>

</details>


### getFormFields

Get field of the form in the kintone app

**Declaration**
```
public FormFields getFormFields(Integer appId) throws KintoneAPIException 
public FormFields getFormFields(Integer appId, LanguageSetting lang) throws KintoneAPIException 
public FormFields getFormFields(Integer appId, Boolean isPreview) throws KintoneAPIException 
public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| appId | The app ID
| lang | The language code. Support: <ul><li>DEFAULT: Default language setting of system </li><li>JA: Japanese language setting</li><li>ZH: Chinese language setting</li><li>EN: English language setting</li> |
| isPreview | Default value: **false**.<br> Get the app form fields with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id
    // get all form fields of app by id
    FormFields fieldsById = kintoneApp.getFormFields(appId);

    // get all form fields of app by id, lang, isPreview
    LanguageSetting lang = LanguageSetting.EN; // LanguageSetting .Ex: LanguageSetting.JA
    FormFields fields = kintoneApp.getFormFields(appId, lang, true);

</pre>

</details>

### addFormFields

Adds fields to a form of an App.

**Declaration**
```
public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException 
public BasicResponse addFormFields(Integer appId, HashMap<String, Field> fields, Integer revision) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| appId | The app ID
| fields | The formFields which will add to form of kintone app <br> **Note:** <br> [String: Field]: <ul><li>Key: The field code of field on kintone app</li><li> Value:  The field settings of form field on kintone app </li> </ul>|
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>add FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // Field code of new Field. It must be not as same as any fields in Pre-Live App Settings
    String fieldCode = "YOUR_NEW_FIELD_CODE"; 

    // Create Radio field instance and set properties
    RadioButtonField addNewField = new RadioButtonField(fieldCode);

    HashMap<String, OptionData> optionArray = new HashMap<>();
    optionArray.put("1", new OptionData(1, "1"));
    optionArray.put("2", new OptionData(2, "2"));
    optionArray.put("3", new OptionData(3, "3"));

    // Setting new radio field
    addNewField.setOptions(optionArray);
    addNewField.setNoLabel(false);
    addNewField.setRequired(true);
    addNewField.setLabel("Sample code addFormFields");
    addNewField.setAlign(AlignLayout.VERTICAL);

    // Add Field object into dictionary with key is Field Code
    HashMap<String, Field> properties = new HashMap<>();
    properties.put(fieldCode, addNewField);

    // Add form field
    BasicResponse res = kintoneApp.addFormFields(appId, properties);

    // Add form field with revision
    Integer revision = 0; // Latest revision of the settings
    BasicResponse responseWithRevision = kintoneApp.addFormFields(appId, properties, revision);


</pre>

</details>

### updateFormFields

Updates the field settings of fields in a form of an App.

**Declaration**
```
public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields) throws KintoneAPIException 
public BasicResponse updateFormFields(Integer appId, HashMap<String, Field> fields, Integer revision) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The app ID
| fields | The formFields which will add to form of kintone app <br> *Note:* <br> [String: Field]: <ul><li>Key: The field code of field on kintone app</li><li> Value:  The field settings of form field on kintone app </li> </ul>|
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>update FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // Field code of new Field. It must be not as same as any fields in Pre-Live App Settings
    String fieldCode = "YOUR_UPDATE_FIELD_CODE"; 

    // Update Radio field instance and set properties
    RadioButtonField updateField = new RadioButtonField(fieldCode);

    HashMap<String, OptionData> optionArray = new HashMap<>();
    optionArray.put("1", new OptionData(1, "1"));
    optionArray.put("B", new OptionData(2, "B"));
    optionArray.put("C", new OptionData(3, "C"));

    // Update Field object into dictionary with key is Field Code
    HashMap<String, Field> properties = new HashMap<>();
    properties.put(fieldCode, updateField);

    // Update field here
    BasicResponse res = kintoneApp.updateFormFields(appId, properties);

    // Update form field with revision
    Integer revision = 0; // Latest revision of the settings
    BasicResponse responseWithRevision = kintoneApp.addFormFields(appId, properties, revision);

</pre>

</details>

### deleteFormFields

> Deletes fields from a form of an App.

**Declaration**
```
public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields) throws KintoneAPIException
public BasicResponse deleteFormFields(Integer app, ArrayList<String> fields, Integer revision) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The app ID
| fields | The list of field codes of the fields to delete.<br>Up to 100 field codes can be specified.|
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>delete FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id
    ArrayList<String> fieldCodeDelete = new ArrayList<>();
    fieldCodeDelete.add("YOUR_DELETE_FIELD_CODE");

    BasicResponse res = kintoneApp.deleteFormFields(appId, fieldCodeDelete);
    // Delete form field with revision
    Integer revision = 0; // Latest revision of the settings
    BasicResponse res = kintoneApp.deleteFormFields(appId, fieldCodeDelete, revision);

</pre>

</details>

### getFormLayout

Get the layout of form in kintone app

**Declaration**
```
public FormLayout getFormLayout(Integer appId) throws KintoneAPIException 
public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| app |The kintone app id
| isPreview | Default value: **false**.<br> Get the app form layout with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get FormLayout</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // get form layout by app id
    FormLayout res = kintoneApp.getFormLayout(appId);

    // get form layout by app id and isPreview
    FormLayout res = kintoneApp.getFormLayout(appId, true);

</pre>

</details>

### updateFormLayout

Updates the field layout info of a form in an App.

**Declaration**
```
public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout) throws KintoneAPIException
public BasicResponse updateFormLayout(Integer app, ArrayList<ItemLayout> layout, Integer revision) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| app |  The kintone app id
| layout | A list of field layouts for each row.
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>update FormLayout</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // List item layout of app
    ArrayList<ItemLayout> itemLayouts = new ArrayList<>();

    // Row Layout
    RowLayout rowLayout = new RowLayout();
    ArrayList<FieldLayout> fieldLayouts = new ArrayList<>();

    // Field Information
    FieldLayout fieldLayout = new FieldLayout();
    fieldLayout.setCode("Record_number");   // Input your field code
    fieldLayout.setType(FieldType.RECORD_NUMBER.name());    // Input your field code type

    // Set size of field
    FieldSize fieldSize = new FieldSize();
    fieldSize.setWidth("200");  // Input width of your field code
    fieldLayout.setSize(fieldSize);

    // Add field into layout
    fieldLayouts.add(fieldLayout);

    // Add layout into row
    rowLayout.setFields(fieldLayouts);

    // Append row into item layout
    itemLayouts.add(rowLayout);
    
    // Update form layout
    BasicResponse res = kintoneApp.updateFormLayout(appId, itemLayouts);

    // Update form layout with revision
    Integer revision = 0; // Latest revision of the settings
    BasicResponse res = kintoneApp.updateFormLayout(appId, itemLayouts, revision);

</pre>

</details>

### getGeneralSettings

Gets the description, name, icon, revision and color theme of an App.

**Declaration**
```
public GeneralSettings getGeneralSettings(Integer app) throws KintoneAPIException 
public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang) throws KintoneAPIException
public GeneralSettings getGeneralSettings(Integer app, Boolean isPreview) throws KintoneAPIException 
public GeneralSettings getGeneralSettings(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app id
| lang | The localized language to retrieve the data in language constants
| isPreview | Default value: **false**. <br> Get general settings of the app with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get GeneralSettings</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // Get general settings by app id
    GeneralSettings res = kintoneApp.getGeneralSettings(appId);

    // Get general settings by app id, language and isPreivew
    LanguageSetting lang = LanguageSetting.EN;
    Boolean isPreview = true;
    GeneralSettings res = kintoneApp.getGeneralSettings(appId, lang, isPreview);

</pre>

</details>

### updateGeneralSettings

Updates the description, name, icon, revision and color theme of an App.

**Declaration**
```
public BasicResponse updateGeneralSettings(Integer app, GeneralSettings generalSettings) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app id
| generalSettings | The description, name, icon, revision and color theme of an App.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>update general settings</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    GeneralSettings appGeneralSetting = new GeneralSettings();
    appGeneralSetting.setName("UpdateGeneralSettings Sample code");
    appGeneralSetting.setDescription("Sample code of updateGeneralSettings");
    appGeneralSetting.setTheme(GeneralSettings.IconTheme.BLACK);

    BasicResponse res = kintoneApp.updateGeneralSettings(appId, appGeneralSetting);

</pre>

</details>

### getViews

Gets the View settings of an App.

**Declaration**
```
public GetViewsResponse getViews(Integer app) throws KintoneAPIException
public GetViewsResponse getViews(Integer app, LanguageSetting lang) throws KintoneAPIException
public GetViewsResponse getViews(Integer app, Boolean isPreview) throws KintoneAPIException
public GetViewsResponse getViews(Integer app, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app id
| lang | The localized language to retrieve the data in language constants
| isPreview | Default value: **false**.<br> Get views of the app with a pre-live settings when isPreview param is set **true**.

**Sample code**

<details class="tab-container" open>
<Summary>get Views</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id

    // Get list view by app id
    GetViewsResponse res = kintoneApp.getViews(appId);

    // Get list view by app id, language and isPreview
    LanguageSetting lang = LanguageSetting.EN;
    Boolean isPreview = true;
    GetViewsResponse res = kintoneApp.getViews(appId, lang, isPreview);

</pre>

</details>

### updateViews

Updates the View settings of an App.

**Declaration**
```
public UpdateViewsResponse updateViews(Integer app, HashMap<String, ViewModel> views) throws KintoneAPIException 
public UpdateViewsResponse updateViews(Integer app, HashMap<String, ViewModel> views, Integer revision) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app id
| views | An object of data of Views.
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>update Views</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

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

    Integer appId = 0;  // Input your app id
    
    // Create/Update fields of view
    ArrayList<String> fieldsView = new ArrayList<>();
    fieldsView.add("YOUR_VIEW_FIELDS");

    // Setting view model
    ViewModel updateViewModel = new ViewModel();
    updateViewModel.setFields(fieldsView);
    updateViewModel.setName("ViewTest");
    updateViewModel.setSort("Record_number desc");
    updateViewModel.setType(ViewModel.ViewType.LIST);
    updateViewModel.setIndex(1);

    HashMap<String, ViewModel> views = new HashMap<>();
    views.put("ViewTest", updateViewModel);

    // Update views with revision
    Integer revision = 0; // Latest revision of the settings
    UpdateViewsResponse updateViews = kintoneApp.updateViews(appId, views, revision);
    
</pre>

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Add Form fields](https://developer.kintone.io/hc/en-us/articles/115005506868)
- [Update Form fields](https://developer.kintone.io/hc/en-us/articles/115005507788)
- [Delete Form fields](https://developer.kintone.io/hc/en-us/articles/115005341187)

- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)
- [Update Form Layout](https://developer.kintone.io/hc/en-us/articles/115005341427)

- [Add Preview App](https://developer.kintone.io/hc/en-us/articles/115004712547)
- [Deploy App Settings](https://developer.kintone.io/hc/en-us/articles/115004881348)
- [Get App Deploy Status](https://developer.kintone.io/hc/en-us/articles/115004890947)
- [Get Views](https://developer.kintone.io/hc/en-us/articles/115004401787)

- [Update Views](https://developer.kintone.io/hc/en-us/articles/115004880588)

- [Get General Settings](https://developer.kintone.io/hc/en-us/articles/115004811668)
- [Update General Settings](https://developer.kintone.io/hc/en-us/articles/115004868628)