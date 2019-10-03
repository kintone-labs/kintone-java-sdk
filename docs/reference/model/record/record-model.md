# Record Model

General record response, using for data response from the kintone app

## GetRecordResponse

### Methods

#### getRecord

> get the Record data response.

**Declaration**
```
public HashMap<String, FieldValue> getRecord()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the Record data response.</Summary>

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
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

## GetRecordsResponse

### Methods

#### getRecords

> get the Records data response.

**Declaration**
```
public ArrayList<HashMap<String, FieldValue>> getRecords()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the Records data response</Summary>

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
    String query = "YOUR_QUERY";
    try {
        GetRecordsResponse response = kintoneRecord.getRecords(appID, query, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

</pre>

</details>

#### getTotalCount

> get the number of records response.

**Declaration**
```
public Integer getTotalCount()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the number of records response</Summary>

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
    String query = "YOUR_QUERY";
    try {
        GetRecordsResponse response = kintoneRecord.getRecords(appID, query, null, true);
        int totalCount = response.getTotalCount();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

</pre>

</details>

## AddRecordResponse

### Methods

#### getID

> get the the ID of record added.

**Declaration**
```
public Integer getID()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the the ID of record added</Summary>

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
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("FIELD_VALUE");

        HashMap<String, FieldValue> record = new HashMap<>();
        record.put("YOUR_FIELD_CODE", fv);

        AddRecordResponse response = kintoneRecord.addRecord(appID, record);
        Integer resultID = response.getID();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

#### getRevision

> get the revision number of record added.

**Declaration**
```
public Integer getRevision()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record added</Summary>

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
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("FIELD_VALUE");

        HashMap<String, FieldValue> record = new HashMap<>();
        record.put("YOUR_FIELD_CODE", fv);

        AddRecordResponse response = kintoneRecord.addRecord(appID, record);
        Integer resultRevision = response.getRevision();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

## AddRecordsResponse

### Methods

#### getIDs

> get the array of added records ID.

**Declaration**
```
public ArrayList<Integer> getIDs()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records ID</Summary>

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
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();

        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        records.add(record1);
        records.add(record2);

        AddRecordsResponse response = kintoneRecord.addRecords(appID, records);
        List<Integer> resultIds = response.getIDs();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

#### getRevisions

> get the array of added records revision number.

**Declaration**
```
public ArrayList<Integer> getRevisions()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records revision number</Summary>

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
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();

        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        records.add(record1);
        records.add(record2);

        AddRecordsResponse response = kintoneRecord.addRecords(appID, records);
        List<Integer> resultRevision = response.getRevisions();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

## UpdateRecordResponse

### Methods

#### getRevision

> get the revision number of record updated.

**Declaration**
```
public Integer getRevision()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record updated</Summary>

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
    Integer revision = 0; // Latest revision of the settings
    try {

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("FIELD_VALUE_1");

        HashMap<String, FieldValue> record = new HashMap<>();
        record.put("YOUR_FIELD_CODE_1", fv);

        UpdateRecordResponse response = kintoneRecord.updateRecordByID(appID, recordID, record, revision);
        Integer resultRevision = response.getRevision();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

## UpdateRecordsResponse

### Methods

#### getRecords

> get the array of added records ID with revision.

**Declaration**
```
public ArrayList<RecordUpdateResponseItem> getRecords()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records ID with revision</Summary>

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
    Integer recordId1 = 0;   // Input your record id
    Integer recordId2 = 0;   // Input your record id
    try {

        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        ArrayList<RecordUpdateItem> records = new ArrayList<>();
        records.add(new RecordUpdateItem(recordId1, null, null, record1));
        records.add(new RecordUpdateItem(recordId2, null, null, record2));

        UpdateRecordsResponse response = kintoneRecord.updateRecords(appID, records);

        ArrayList<RecordUpdateResponseItem> updatedRecord = response.getRecords();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

## RecordUpdateResponseItem

### Methods

#### getID

> get the the ID of record updated.

**Declaration**
```
public Integer getID()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the the ID of record updated</Summary>

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
    Integer recordId1 = 0;   // Input your record id
    Integer recordId2 = 0;   // Input your record id
    try {

        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        ArrayList<RecordUpdateItem> records = new ArrayList<>();
        records.add(new RecordUpdateItem(recordId1, null, null, record1));
        records.add(new RecordUpdateItem(recordId2, null, null, record2));

        UpdateRecordsResponse response = kintoneRecord.updateRecords(appID, records);

        ArrayList<RecordUpdateResponseItem> updatedRecord = response.getRecords();

        RecordUpdateResponseItem responseItem = updatedRecord.get(0);

        Integer resultID = responseItem.getID();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

#### getRevision

> get the revision number of record updated.

**Declaration**
```
public Integer getRevision()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record updated</Summary>

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
    Integer recordId1 = 0;   // Input your record id
    Integer recordId2 = 0;   // Input your record id
    try {
        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        ArrayList<RecordUpdateItem> records = new ArrayList<>();
        records.add(new RecordUpdateItem(recordId1, null, null, record1));
        records.add(new RecordUpdateItem(recordId2, null, null, record2));

        UpdateRecordsResponse response = kintoneRecord.updateRecords(appID, records);

        ArrayList<RecordUpdateResponseItem> updatedRecord = response.getRecords();

        RecordUpdateResponseItem responseItem = updatedRecord.get(0);

        Integer resultRevision = responseItem.getRevision();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

</pre>

</details>

## RecordUpdateItem

### Constructor

**Declaration**
```
public RecordUpdateItem(RecordUpdateKey updateKey, HashMap<String, FieldValue> record) 
public RecordUpdateItem(Integer id, Integer revision, RecordUpdateKey updateKey, HashMap<String, FieldValue> record)
```

**Parameter**

| Name| Description |
| --- | ---  | 
| id | The ID of the record.
| revision | The revision number of the record.
| updateKey | The unique key of the record to be updated. Required, if id will not be specified. To specify this field, the field must have the "Prohibit duplicate values" option turned on. ([RecordUpdateKey](#recordupdatekey))
| record | The data to update record. (HashMap<String, [FieldValue](../record-field-model#fieldvalue)\>)

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateItem class</Summary>

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
    Integer recordId1 = 0;   // Input your record id
    Integer recordId2 = 0;   // Input your record id
    try {
        FieldValue fvOfRecord1 = new FieldValue();
        fvOfRecord1.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord1.setValue("FIELD_VALUE_1");
        FieldValue fvOfRecord2 = new FieldValue();
        fvOfRecord2.setType(FieldType.SINGLE_LINE_TEXT);
        fvOfRecord2.setValue("FIELD_VALUE_2");

        HashMap<String, FieldValue> record1 = new HashMap<>();
        record1.put("YOUR_FIELD_CODE_1", fvOfRecord1);
        HashMap<String, FieldValue> record2 = new HashMap<>();
        record2.put("YOUR_FIELD_CODE_2", fvOfRecord2);

        RecordUpdateItem recordUpdateItem1 = new RecordUpdateItem(recordId1, null, null, record1);
        RecordUpdateItem recordUpdateItem2 = new RecordUpdateItem(recordId2, null, null, record2);

        ArrayList<RecordUpdateItem> records = new ArrayList<>();
        records.add(recordUpdateItem1);
        records.add(recordUpdateItem2);

        UpdateRecordsResponse response = kintoneRecord.updateRecords(appID, records);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

### Methods

(none)

## RecordUpdateKey

### Constructor

**Declaration**
```
public RecordUpdateKey(String field, String value)
```

**Parameter**

| Name| Description |
| --- | --- |
| field | The field code of unique key in the kintone app.
| value | The field value in the record.

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateKey class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String field = "YOUR_FIELD_CODE";
    String value = "FIELD_VALUE";
    RecordUpdateKey recordUpdateKey = new RecordUpdateKey(field, value);
</pre>

</details>

### Methods

(none)

## RecordUpdateStatusItem

### Constructor

**Declaration**
```
public RecordUpdateStatusItem(String action, String assignee, Integer id, Integer revision)
```

**Parameter**

| Name| Description |
| --- | ---  | 
| action | The Action name of the action you want.
| assignee | The next Assignee. Specify the Assignee's log in name..
| id | The record ID.
| revision | The revision number of the record before updating the status. If the specified revision is not the latest revision, the request will result in an error.

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateStatusItem class</Summary>

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
    Integer revision = 0;  // Input your revision
    String action = "YOUR_ACTION";
    String assignee = "YOUR_USER_CODE";
    try {
        ArrayList<RecordUpdateStatusItem> recordUpdateStatusItems = new ArrayList<>();
        RecordUpdateStatusItem updateStatusItem = new RecordUpdateStatusItem(action, assignee, recordID, revision);
        recordUpdateStatusItems.add(updateStatusItem);
        UpdateRecordsResponse response = kintoneRecord.updateRecordsStatus(appID, recordUpdateStatusItems);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

### Methods

(none)