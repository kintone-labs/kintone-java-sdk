# Field Model

## FieldValue

General Field's value of the kintone app

### Constructor

**Parameter**

(none)

### Methods

#### getType

> get the type of field.

**Declaration**
```
public FieldType getType()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the type of field.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        GetRecordResponse response = kintoneRecord.getRecord(appID, recordID);

        HashMap<String, FieldValue> resultRecord = response.getRecord();
        FieldValue fv = resultRecord.get("YOUR_FIELD_CODE");
        FieldType fieldType = fv.getType();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

#### setType

> set the type of field.

**Declaration**
```
public void setType(FieldType type)
```

**Parameter**

| Name| Description |
| ---  | --- |
| type | The type of field - kintone-sdk FieldType constants.

**Sample code**

<details class="tab-container" open>
<Summary>set the type of field.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        HashMap<String, FieldValue> record = new HashMap<>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT); // Input field type
        fv.setValue("FIELD_VALUE");
        record.put("YOUR_FIELD_CODE", fv);
        AddRecordResponse response = kintoneRecord.addRecord(appID, record);
        System.out.println(response.getID());
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

</pre>

</details>

#### getValue

> get the value of field in the record.

**Declaration**
```
public Object getValue()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the value of field in the record.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        GetRecordResponse response = kintoneRecord.getRecord(appID, recordID);

        HashMap<String, FieldValue> resultRecord = response.getRecord();
        FieldValue fv = resultRecord.get("txt_projectTitle");
        Object fieldValue = fv.getValue();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

</pre>

</details>

#### setValue

> set the value of field in the record.

**Declaration**
```

```

**Parameter**

| Name| Description |
| --- | --- |
| value | The value of field in the record, read more at [Field Type here](https://developer.kintone.io/hc/en-us/articles/212494818/#responses).

**Sample code**

<details class="tab-container" open>
<Summary>set the value of field in the record.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id

    try {
        HashMap<String, FieldValue> record = new HashMap<>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("FIELD_VALUE");
        record.put("FieldCode1", fv);

        AddRecordResponse response = kintoneRecord.addRecord(appID, record);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

##SubTableValueItem

### Constructor

**Parameter**

(none)

### Methods

#### getID

> get the ID of item in table.

**Declaration**
```
public Integer getID()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        GetRecordResponse response = kintoneRecord.getRecord(appID, recordID);

        HashMap<String, FieldValue> resultRecord = response.getRecord();
        SubTableValueItem subTableValueItem = new SubTableValueItem();
        subTableValueItem.setValue(resultRecord);
        ArrayList<SubTableValueItem> subTable = new ArrayList<>();
        subTable.add(subTableValueItem);

        Integer itemID = subTable.get(0).getID();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>


#### setID

> set the ID of table.

**Declaration**
```
public void setID(Integer id)
```

**Parameter**

| Name | Description |
| --- | --- |
| id | The ID of table .

**Sample code**

<details class="tab-container" open>
<Summary>set the ID of item in table.</Summary>

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

    SubTableValueItem tableItem = new SubTableValueItem();
    tableItem.setID(0); // Input table item id
    HashMap<String, FieldValue> tableItemValue = new HashMap<>();
    FieldValue fv1 = new FieldValue();
    fv1.setType(FieldType.SINGLE_LINE_TEXT);
    fv1.setValue("FIELD_VALUE");
    tableItem.setValue(tableItemValue);
</pre>

</details>

#### getValue

> get the value of field in the record.

**Declaration**
```
public HashMap<String, FieldValue> getValue()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        GetRecordResponse response = kintoneRecord.getRecord(appID, recordID);

        HashMap<String, FieldValue> resultRecord = response.getRecord();
        SubTableValueItem subTableValueItem = new SubTableValueItem();
        subTableValueItem.setValue(resultRecord);
        ArrayList<SubTableValueItem> subTable = new ArrayList<>();
        subTable.add(subTableValueItem);

        HashMap<String, FieldValue> value = subTable.get(0).getValue();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

#### setValue

> set the value of field in the record.

**Declaration**
```
public void setValue(HashMap<String, FieldValue> value)
```

**Parameter**

| Name | Description |
| --- | --- |
| value | The row data of table. (HashMap<String, [FieldValue](#fieldvalue)\>)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

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

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id

    try {
        GetRecordResponse response = kintoneRecord.getRecord(appID, recordID);

        HashMap<String, FieldValue> resultRecord = response.getRecord();
        SubTableValueItem subTableValueItem = new SubTableValueItem();
        subTableValueItem.setValue(resultRecord);

    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>


