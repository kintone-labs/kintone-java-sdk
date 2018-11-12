# App

Gets general information of an App, including the name, description, related Space, creator and updater information.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## Constructor

### **Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| connection | [Connection](./connection) | yes | The connection module of this SDK.

### **Sample code**

<details class="tab-container" open>
<Summary>Init app module</Summary>

** Source code **

```java
App appManagerment = new App(connection);
```

</details>

## Methods

### getApp(appId)

> Get single app

#### **Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| appId | Integer | yes | The kintone app ID

**Return**

[AppModel](./app-model#appmodel)

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

** Source code **

```java
Integer appID = 1;

App appManagerment = new App(connection);
AppModel app = appManagerment.getApp(appID);
```

</details>

### getApps(offset, limit)

> Get multiple apps

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| offset | Integer | (optional) | The offset off data result
| limit | Integer | (optional) | The limit number of result

**Return**

List<[AppModel](./app-model#appmodel)>

**Sample code**

<details class="tab-container" open>
<Summary>Get Apps</Summary>

** Source code **

```java
Integer offset = 10;
Integer limit = 50;

App appManagerment = new App(connection);
List<AppModel> app_list = appManagerment.getApps(offset, limit);
```

</details>

### getAppsByIDs(ids, offset, limit)

> Get multiple apps by list of ids

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| ids | List<Integer\> | yes | The array of app ids
| offset | Integer | (optional) | The offset off data result
| limit | Integer | (optional) | The limit number of result

**Return**

List<[AppModel](./app-model#appmodel)>

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By IDs</Summary>

** Source code **

```java
Integer offset = 1;
Integer limit = 10;
List<Integer> ids = new List<Integer>();

ids.add(1);
ids.add(2);
ids.add(3);

App appManagerment = new App(connection);
List<AppModel> app_list = appManagerment.getAppsByIDs(ids, offset, limit);
```

</details>

### getAppsByCodes(codes, offset, limit)

> Get multiple apps by a list of codes

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| codes | List<String\> | yes | The array of app codes
| offset | Integer | (optional) | The offset off data result
| limit | Integer | (optional) | The limit number of result

**Return**

List<[AppModel](./app-model#appmodel)>

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Codes</Summary>

** Source code **

```java
Integer offset = 1;
Integer limit = 10;
List<String> codes = new List<String>();

codes.add('APP_CODE_1');
codes.add('APP_CODE_2');

App appManagerment = new App(connection);
List<AppModel> app_list = appManagerment.getAppsByCodes(codes, offset, limit);
```

</details>

### getAppsByName(name, offset, limit)

> Get multiple apps by name

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| name | String | yes | The app name
| offset | Integer | (optional) | The offset off data result
| limit | Integer | (optional) | The limit number of result

**Return**

List<[AppModel](./app-model#appmodel)>

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Name</Summary>

** Source code **

```java
String name = "sample_app_name";
Integer offset = 1;
Integer limit = 10;

App appManagerment = new App(connection);
List<AppModel> app_list = appManagerment.getAppsByName(name, offset, limit);
```

</details>

### getAppsBySpaceIDs(spaceIds, offset, limit)

> Get multiple apps by list of space's ids

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| spaceIds | List<Integer\> | yes | The array of space ids
| offset | Integer | (optional) | The offset off data result
| limit | Integer | (optional) | The limit number of result

**Return**

List<[AppModel](./app-model#appmodel)>

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By Space IDs</Summary>

** Source code **

```java
Integer offset = 1;
Integer limit = 10;
List<Integer> spaceIds = new List<Integer>();

App appManagerment = new App(connection);
List<AppModel> app_list = appManagerment.getAppsBySpaceIDs(spaceIds, offset, limit);
```

</details>

### getFormFields(appId, lang, isPreview)

> Get field of form in kintone app

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| appId | Integer | yes | The app ID
| lang | LanguageSetting | (optional) | The language code. Support: <ul><li>DEFAULT: Default language setting of system </li><li>JA: Japanese language setting</li><li>ZH: Chinese language setting</li><li>EN: English language setting</li> |
| isPreview | Boolean | (optional) | Get the app form fields with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Return**

[FormFields](./form-fields/#formfields)

**Sample code**

<details class="tab-container" open>
<Summary>get FormFields</Summary>

** Source code **

```java
Integer appID = 1;
LanguageSetting lang = LanguageSetting.JA;
Boolean isPreview = true;

App appManagerment = new App(connection);
FormFields fields = appManagerment.getFormFields(appID, lang, isPreview);
```

</details>

### getFormLayout(appId, isPreview)

> Get layout of form in kintone app

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| appId | Integer | yes | The kintone app id
| isPreview | Boolean | (optional) | Get the app form layout with a [pre-live settings](https://developer.kintone.io/hc/en-us/articles/115005509288).

**Return**

[FormLayout](./form-layout#formlayout)

**Sample code**

<details class="tab-container" open>
<Summary>get FormLayout</Summary>

** Source code **

```java
Integer appID = 1;
Boolean isPreview = true;

App appManagerment = new App(connection);
FormFields fields = appManagerment.getFormLayout(appID, isPreview);
```

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`