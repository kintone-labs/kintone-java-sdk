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

    // Init authentication
    Auth kintoneAuth = new Auth()
    
    // Password Authentication
    String username = "your_usernamr"
    String password = "your_password"
    kintoneAuth = kintoneAuth.setPasswordAuth(username, password)
    Connection connection = Connection( "your_domain", kintoneAuth )
    App app = new App(connection)

</pre>

</details>

## Methods

### getApp(Integer appId)

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

    Integer appId = {your_app_id};
    App appManagerment = new App(connection);
    AppModel app = appManagerment.getApp(appId);

</pre>

</details>

### getApps(Integer offset, Integer limit)

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
| offset | The offset off data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>Get Apps</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer offset = 10;
Integer limit = 50;

App appManagerment = new App(connection);
List<AppModel> appList = appManagerment.getApps(offset, limit);
</pre>

</details>

### getAppsByIDs(List<Integer> ids, Integer offset, Integer limit)

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
| offset | The offset off data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<Integer> appIds = new List<Integer>();
appIds.add({your_app_id});
appIds.add({your_app_id});
Integer offset = {your_limit};
Integer limit = {your_offset};

App appManagerment = new App(connection);
List<AppModel> appList = appManagerment.getAppsByIDs(appIds, offset, limit);
</pre>

</details>

### getAppsByCodes(List<String> codes, Integer offset, Integer limit)

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
| offset | The offset off data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Codes</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<String> appCode = new List<String>();
appCode.add({your_app_code});
appCode.add({your_app_code});
Integer limit = {your_limit};
Integer offset = {your_offset};

App appManagerment = new App(connection);
List<AppModel> appList = appManagerment.getAppsByCodes(appCode, offset, limit);
</pre>

</details>

### getAppsByName(String name, Integer offset, Integer limit)

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
| name | The app name
| offset | The offset off data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Name</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appName = {your_app_name};
Integer offset = {your_offset};
Integer limit = {your_limit};

App appManagerment = new App(connection);
List<AppModel> appLlist = appManagerment.getAppsByName(name, offset, limit);
</pre>

</details>

### getAppsBySpaceIDs(List<Integer> spaceIds, Integer offset, Integer limit)

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
| offset | The offset off data result
| limit | The limit number of result

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Space IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<Integer> spaceIds = new List<Integer>();
spaseIds.add({your_space_id});
spaseIds.add({your_space_id});
Integer offset = {your_offset};
Integer limit = {your_litmit};

App appManagerment = new App(connection);
List<AppModel> appList = appManagerment.getAppsBySpaceIDs(spaceIds, offset, limit);
</pre>

</details>

### addPreviewApp(String name, Integer space, Integer thread)

Creates a preview App.

**Declaration**
```
public AddPreviewAppResponse addPreviewApp(String name) throws KintoneAPIException 
public AddPreviewAppResponse addPreviewApp(String name, Integer space) throws KintoneAPIException 
public AddPreviewAppResponse addPreviewApp(String name, Integer space, Integer thread) throws KintoneAPIException 
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

    Integer spaceId = {your_space_id} // Space will add this app
    Integer threadId = {your_thread_id} // Thread will add this app

    App appManagerment = new App(connection);
    appManagerment.addPreviewApp(appName, spaceId, threadId);

</pre>

</details>


### deployAppSettings(Array<PreviewApp> apps, Boolean revert)

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
| revert | Default value: **false**. Specify **true** to cancel all changes made to the pre-live settings. The pre-live settings will be reverted back to the current settings of the live app.|

**Sample code**

<details class="tab-container" open>
<Summary>deploy AppSettings</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer appId = {your_app_id}
    Integer revision = {your_revision} // Revision of application to deploy
    ArrayList<appPreview> appPreviewList = new ArrayList<appPreview>();
    PreviewApp appPreview = new PreviewApp(appId, revision)
    
    App appManagerment = new App(connection);
    appManagerment.deployAppSettings(appPreviewList, false)

</pre>

</details>

### getAppDeployStatus(List<Integer> apps)

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

    List<Integer> appIds = new List<Integer>();
    appIds.add({your_app_id});
    appIds.add({your_app_id});
    
    App appManagerment = new App(connection);
    GetAppDeployStatusResponse res = appManagerment.getAppDeployStatus(appIds);
    for (int i = 0; i < res.getApp.length; i++) {
        print(res.getApp.get(i));
    }

</pre>

</details>


### getFormFields(Integer app, LanguageSetting lang, Boolean isPreview)

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
| isPreview | Default value: **false**. Get the app form fields with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer appId = {your_app_id} // Integer
    LanguageSetting lang = {language_code} // LanguageSetting .Ex: LanguageSetting.JA
    Boolean isPreview = true;

    App appManagerment = new App(connection);
    FormFields fields appManagerment.getFormFields(appId, lang, isPreview)

</pre>

</details>

### addFormFields(Integer app, HashMap<String, Field> fields, Integer revision)

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
| fields | The formFields which will add to form of kintone app <br> *Note:* <br> [String: Field]: <ul><li>Key: The field code of field on kintone app</li><li> Value:  The field settings of form field on kintone app </li> </ul>|
| revision | Specify the revision number of the settings that will be deployed.<br>The request will fail if the revision number is not the latest revision.<br>The revision will not be checked if this parameter is ignored, or -1 is specified.

**Sample code**

<details class="tab-container" open>
<Summary>add FormFields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer appId = {your_app_id} // App Id
    String fieldCode = {field_code_string} // Field code of new Field. It must be not as same as any fields in Pre-Live App Setttings
    Integer revision = {latest_revision_of_the_settings} // Integer
    
    // Create Radio field instance and set properties
    RadioButtonField addNewField = new RadioButtonField(fieldCode)
    HashMap<String, OptionData> optionArray = new HashMap<String, OptionData>()
    optionArray["1"] = OptionData("1","1")
    optionArray["2"] = OptionData("2","2")
    optionArray["3"] = OptionData("3","3")
    addNewField.setOptions(optionArray)
    addNewField.setNoLabel(false)
    addNewField.setRequired(true)
    addNewField.setLabel("Label Radio")
    addNewField.setAlign(AlignLayout.VERTICAL)
    
    // Add Field object into dictionary with key is Field Code
    HashMap<String, Field> properties = new HashMap<String, Field>()
    properties.put(fieldCode, addNewField);
    // Another add field here

    App appManagerment = new App(connection);
    BasicResponse res = appManagerment.addFormFields(appId, properties, revision);

</pre>

</details>

### updateFormFields(Integer app, HashMap<String, Field> fields, Integer revision)

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

    Integer appId = {your_app_id} // Integer
    String fieldCode = {field_code_string} // String | fieldCode of exist fields in Pre-Live App Setttings
    Integer revision = {latest_revision_of_the_settings} // Integer
    
    // Create Field Object to Update
    SingleLineTextField updateField = new SingleLineTextField(fieldCode)
    updateField.setDefaultValue("Hello Kintone")
    updateField.setRequired(true)
    
    // Add Update Field object into dictionary with key is Field Code
    HashMap<String, Field> properties = new HashMap<String, Field>()
    properties.put(fieldCode, updateField);
    
    App appManagerment = new App(connection);
    BasicResponse res = appManagerment.updateFormFields(appId, properties, revision);

</pre>

</details>

### deleteFormFields(Integer app, ArrayList<String> fields, Integer revision)

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

    int appId = {your_app_id} // Integer
    ArrayList<String> fieldCodeArray = [{field_code_string}] // Array<String> | Array of fieldCodes of exist fields in Pre-Live App Setttings
    int revision = {latest_revision_of_the_settings} // Integer

    App appManagerment = new App(connection);
    BasicResponse res = appManagerment.deleteFormFields(appId, fieldCodeArray, revision);

</pre>

</details>

### getFormLayout(Integer appId, Boolean isPreview)

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
| isPreview | Default value: **false**. Get the app form layout with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get FormLayout</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    int appId = {your_app_id} // Integer
    boolean isPreview = true

    // Get a pre-live (preview) form fields
    App appManagerment = new App(connection);
    FormLayout res = appManagerment.getFormLayout(appId, isPreview)

</pre>

</details>

### updateFormLayout(Integer app, ArrayList<ItemLayout> layout, Integer revision)

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

    int appId = {your_app_id} // Integer
    ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        
    // Row Layout
    RowLayout rowLayout1 = new RowLayout();
    ArrayList<FieldLayout> fieldsRowLayout1: new ArrayList<FieldLayout>();
    
    FieldLayout singleFieldRowLayout1 = new FieldLayout();
    singleFieldRowLayout1.setCode("Text");
    singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.rawValue);
    FieldSize singleFieldSizeRowLayout1 = new FieldSize();
    singleFieldSizeRowLayout1.setWidth("193");
    singleFieldRowLayout.setSize(singleFieldSizeRowLayout1);
    fieldsRowLayout1.add(singleFieldRowLayout1!);
    
    FieldLayout richTextFieldRowLayout1 = new FieldLayout();
    richTextFieldRowLayout1.setCode("Rich_text");
    richTextFieldRowLayout1.setType(FieldType.RICH_TEXT.rawValue);
    FieldSize richTextFieldSizeRowLayout1 = new FieldSize();
    richTextFieldSizeRowLayout1.setWidth("315")
    richTextFieldSizeRowLayout1.setInnerHeight("125")
    richTextFieldRowLayout1.setSize(richTextFieldSizeRowLayout1)
    fieldsRowLayout1.add(richTextFieldRowLayout1!)
    
    rowLayout1.setFields(fieldsRowLayout1)
    
    // Subtable Layout
    SubTableLayout subTableLayout = new SubTableLayout();
    ArrayList<FieldLayout> fieldSubTableLayout = new ArrayList<FieldLayout>();
    
    FieldLayout singleFieldSubTableLayout1 = new FieldLayout();
    singleFieldSubTableLayout1.setCode("Text_0");
    singleFieldSubTableLayout1.setType(FieldType.SINGLE_LINE_TEXT.rawValue);
    FieldSize singleFieldSizeSubTableLayout1 = new FieldSize();
    singleFieldSizeSubTableLayout1.setWidth("193");
    singleFieldSubTableLayout1.setSize(singleFieldSizeSubTableLayout1);
    
    fieldSubTableLayout.add(singleFieldSubTableLayout1!);
    subTableLayout.setFields(fieldSubTableLayout);
    subTableLayout.setCode("Table");
    
    // GROUP Layout
    GroupLayout groupLayout = new GroupLayout();
    ArrayList<RowLayout> rowLayoutInGroup = new ArrayList<RowLayout>();
    // Row Layout
    RowLayout firstRowLayoutInGroup = new RowLayout();
    ArrayList<FieldLayout> fieldsInRowLayoutInGroup = new ArrayList<FieldLayout>()
    // Numeric Field Layout
    FieldLayout numericFieldInRowLayoutInGroup = new FieldLayout();
    numericFieldInRowLayoutInGroup.setCode("Number");
    numericFieldInRowLayoutInGroup.setType(FieldType.NUMBER.rawValue);
    // field size
    FieldSize numericFieldSizeInRowLayoutInGroup = new FieldSize();
    numericFieldSizeInRowLayoutInGroup.setWidth("200")
    numericFieldInRowLayoutInGroup.setSize(numericFieldSizeInRowLayoutInGroup)
    
    fieldsInRowLayoutInGroup.append(numericFieldInRowLayoutInGroup!)
    firstRowLayoutInGroup.setFields(fieldsInRowLayoutInGroup)
    rowLayoutInGroup.append(firstRowLayoutInGroup!)
    groupLayout.setLayout(rowLayoutInGroup)
    groupLayout.setCode("Field_group")
    
    // Append layout
    itemLayoutRequest.add(rowLayout1!)
    itemLayoutRequest.add(subTableLayout!)
    itemLayoutRequest.add(groupLayout!)
    
    App appManagerment = new App(connection);
    BasicResponse res = appManagerment.updateFormLayout(appId, itemLayoutRequest);

</pre>

</details>

### getGeneralSettings(Integer app, LanguageSetting lang, Boolean isPreview)

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
| isPreview | Default value: **fasle** Get general settings of the app with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Sample code**

<details class="tab-container" open>
<Summary>get GeneralSettings</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    int appId = {your_app_id};
    LanguageSetting lang = {your_language_code} // LanguageSetting( EN | JA | ZH ). Ex: LanguageSetting.JA
    boolean isPreview = true
    
    App appManagerment = new App(connection);
    GeneralSettings res = appManagerment.getGeneralSettings(appId, lang, isPreview);

</pre>

</details>

### updateGeneralSettings(Integer app, GeneralSettings generalSettings, Integer revision)

Updates the description, name, icon, revision and color theme of an App.

**Declaration**
```
public BasicResponse updateGeneralSettings(Integer app) throws KintoneAPIException
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

    int appId = {your_app_id};
    
    GeneralSettings appGeneralSetting = new GeneralSettings();
    appGeneralSetting.setName("GetViewsApp_Test");
    appGeneralSetting.setDescription("<div>A list of great places to go!</div>");
    Icon iconModel = new Icon("APP39", Icon.IconType.PRESET);
    appGeneralSetting.setIcon(iconModel);
    appGeneralSetting.setTheme(GeneralSettings.IconTheme.WHITE);
    
    App appManagerment = new App(connection);
    BasicResponse res = appManagerment.updateGeneralSettings(appId, appGeneralSetting);

</pre>

</details>

### getViews(Integer app, LanguageSetting lang, Boolean isPreview)

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
| isPreview | Default value: **false** Get views of the app with a pre-live settings when isPreview param is set <b>true</b>.

**Sample code**

<details class="tab-container" open>
<Summary>get Views</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    int appId = {your_app_id}
    LanguageSetting lang = LanguageSetting.EN // LanguageSetting( EN | JA | ZH ). Ex: LanguageSetting.JA
    boolean isPreview = true
    
    App appManagerment = new App(connection);
    GetViewsResponse res = app.getViews(appId, lang, isPreview);

</pre>

</details>

### updateViews(Integer app, HashMap<String, ViewModel> views, Integer revision)

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

    int appId = {your_app_id)}
    int revision = {your_lastest_revision} //default: revision = -1
    
    HashMap<String, ViewModel> viewEntry = new HashMap<String: ViewModel>();
    ViewModel updateViewModel = new ViewModel();
    updateViewModel.setName("ViewTest")
    updateViewModel.setSort("Record_number desc")
    updateViewModel.setType(ViewModel.ViewType.LIST)
    updateViewModel.setFilterCond("Created_datetime = LAST_WEEK()")
    updateViewModel.setIndex(1)
    ArrayList<String> fieldsViews = new ArrayList<String>();
    fieldsViews.add("Text");
    fieldsViews.add("Text_Area");
    fieldsViews.add("Created_datetime");
    updateViewModel.setFields(fieldsViews);
    viewEntry.put("ViewTest", updateViewModel);
    
    ViewModel updateViewModel2 = new ViewModel();
    updateViewModel2.setName("ViewTest2")
    updateViewModel2.setSort("Record_number asc")
    updateViewModel2.setType(ViewModel.ViewType.LIST)
    updateViewModel2.setFilterCond("Created_datetime > LAST_WEEK()")
    updateViewModel2.setIndex(0)
    
    ArrayList<String> fieldsInViews2 = new ArrayList<String>();
    fieldsInViews2.add("Text_Area");
    fieldsInViews2.add("Created_datetime");
    updateViewModel2.setFields(fieldsInViews2);

    viewEntry.put("ViewTest2", updateViewModel2);
    
    App appManagerment = new App(connection);
    UpdateViewsResponse res = appManagerment.updateViews(appId, viewEntry, revision);
    
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