# Record Model

General record response, using for data response from the kintone app

## GetRecordResponse

### Methods

#### getRecord()

> get the Record data response.

**Parameter**

(none)

**Return**

HashMap<String, [FieldValue](../record-field-model#fieldvalue)\>


**Sample code**

<details class="tab-container" open>
<Summary>get the Record data response.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD API
Integer appID = 1;
Integer recordID =1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);

HashMap<String, FieldValue> resultRecord = response.getRecord();
</pre>

</details>

## GetRecordsResponse

### Methods

#### getRecords()

> get the Records data response.

**Parameter**

(none)

**Return**

ArrayList<HashMap<String, [FieldValue](../record-field-model#fieldvalue)\>\>

**Sample code**

<details class="tab-container" open>
<Summary>get the Records data response</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORDS API
Integer appID = 1;
String query = "$id >=" +  1 + "and $id <=" + 10 + "order by $id asc";
GetRecordsResponse response = kintoneRecordManager.getRecords(appID, query, null, true);

ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
</pre>

</details>

#### getTotalCount()

> get the number of records response.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the number of records response</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORDS API
Integer appID = 1;
String query = "$id >=" +  1 + "and $id <=" + 10 + "order by $id asc";
GetRecordsResponse response = kintoneRecordManager.getRecords(appID, query, null, true);

Integer count =  response.getTotalCount();
</pre>

</details>

## AddRecordResponse

### Methods

#### getID()

> get the the ID of record added.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the the ID of record added</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD API
Integer appID = 1;
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");
record.put("FieldCode1", fv);

AddRecordResponse response = kintoneRecordManager.addRecord(appID, record);

Integer resultID = response.getID();
</pre>

</details>

#### getRevision()

> get the revision number of record added.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record added</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD API
Integer appID = 1;
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");
record.put("FieldCode1", fv);

AddRecordResponse response = kintoneRecordManager.addRecord(appID, record);

Integer resultRevision = response.getRevision();
</pre>

</details>

## AddRecordsResponse

### Methods

#### getIDs()

> get the array of added records ID.

**Parameter**

(none)

**Return**

ArrayList<Integer\>

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records ID</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORDS API
Integer appID = 1;
ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("sample_AddRecords2");

record1.put("FieldCode1", fv);
record2.put("FieldCode1", fv2);

records.add(record1);
records.add(record2);

AddRecordsResponse response = kintoneRecordManager.addRecords(appID, records);

ArrayList<Integer> resultIDs = response.getIDs();
</pre>

</details>

#### getRevisions()

> get the array of added records revision number.

**Parameter**

(none)

**Return**

ArrayList<Integer\>

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records revision number</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORDS API
Integer appID = 1;
ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("sample_AddRecords2");

record1.put("FieldCode1", fv);
record2.put("FieldCode1", fv2);

records.add(record1);
records.add(record2);

AddRecordsResponse response = kintoneRecordManager.addRecords(appID, records);

ArrayList<Integer> resultRevisions = response.getRevisions();
</pre>

</details>

## UpdateRecordResponse

### Methods

#### getRevision()

> get the revision number of record updated.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record updated</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORD API
Integer appID = 1;
Integer recordID = 1;
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
Integer revision = 1;

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_updateRecordById");

record.put("FieldCode1", fv);

UpdateRecordResponse response = kintoneRecordManager.updateRecordByID(appID, recordID, record, revision);

Integer resultRevision = response.getRevision();
</pre>

</details>

## UpdateRecordsResponse

### Methods

#### getRecords()

> get the array of added records ID with revision.

**Parameter**

(none)

**Return**

ArrayList<[RecordUpdateResponseItem](#recordupdateresponseitem)\>

**Sample code**

<details class="tab-container" open>
<Summary>get the array of added records ID with revision</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORDS API
Integer appID = 1;
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer recordId1 = 1;
Integer recordId2 = 2;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
records.add(new RecordUpdateItem(recordId1, null, null, record1));
records.add(new RecordUpdateItem(recordId2, null, null, record2));

UpdateRecordsResponse response = kintoneRecordManager.updateRecords(appID, records);

ArrayList<RecordUpdateResponseItem> resultRuris = response.getRecords();
</pre>

</details>

## RecordUpdateResponseItem

### Methods

#### getID()

> get the the ID of record updated.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the the ID of record updated</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORDS API
Integer appID = 1;
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer recordId1 = 1;
Integer recordId2 = 2;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

ArrayList<RecordUpdateItem> records = ArrayList<RecordUpdateItem>();
records.add(new RecordUpdateItem(recordId1, null, null, record1));
records.add(new RecordUpdateItem(recordId2, null, null, record2));

UpdateRecordsResponse response = kintoneRecordManager.updateRecords(appID, records);

ArrayList<RecordUpdateResponseItem> resultRuris = response.getRecords();
RecordUpdateResponseItem resultRusi = resultRuris.get(0);

Integer resultID = resultRusi.getID();
</pre>

</details>

#### getRevision()

> get the revision number of record updated.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the revision number of record updated</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORDS API
Integer appID = 1;
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer recordId1 = 1;
Integer recordId2 = 2;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
records.add(new RecordUpdateItem(recordId1, null, null, record1));
records.add(new RecordUpdateItem(recordId2, null, null, record2));

UpdateRecordsResponse response = kintoneRecordManager.updateRecords(appID, records);

ArrayList<RecordUpdateResponseItem> resultRuris = response.getRecords();
RecordUpdateResponseItem resultRusi = resultRuris.get(0);

Integer resultRevision = resultRusi.getRevision();
</pre>

</details>

## RecordUpdateItem

### Constructor

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| id | Integer  | The ID of the record.
| revision | Integer  | The revision number of the record.
| updateKey | [RecordUpdateKey](#recordupdatekey)  |  The unique key of the record to be updated. Required, if id will not be specified. To specify this field, the field must have the "Prohibit duplicate values" option turned on.
| record | HashMap<String, [FieldValue](../record-field-model#fieldvalue)\>  | The data to update record.

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateItem class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORDS API
Integer appID = 1;
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer recordId1 = 1;
Integer recordId2 = 2;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

RecordUpdateItem updateItem1 = new RecordUpdateItem(recordId1, null, null, record1);
RecordUpdateItem updateItem2 = new RecordUpdateItem(recordId2, null, null, record2);

ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
records.add(updateItem1);
records.add(updateItem2);

UpdateRecordsResponse response = kintoneRecordManager.updateRecords(appID, records);
</pre>

</details>

### Methods

(none)

## RecordUpdateKey

### Constructor

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| field | String  | The field code of unique key in the kintone app.
| value | String  | The field value in the record.

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateKey class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

String field = "field_code";
String value = "unique_value1";
RecordUpdateKey uKey = new RecordUpdateKey(field, value);
</pre>

</details>

### Methods

(none)

## RecordUpdateStatusItem

### Constructor

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| action | String  | The Action name of the action you want.
| assignee | String  |  (Optional) The next Assignee. Specify the Assignee's log in name..
| id | Integer  |   The record ID.
| revision | Integer  |  (Optional) The revision number of the record before updating the status.If the specified revision is not the latest revision, the request will result in an error.

**Sample code**

<details class="tab-container" open>
<Summary>init RecordUpdateStatusItem class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute UPDATE RECORDS API
Integer appID = 1;
String action = "処理開始";
String assignee = "sample_user1";
Integer recordID =1;
Integer revision = 1;

ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();

RecordUpdateStatusItem updateStatusItem = new RecordUpdateStatusItem(action, assignee, recordID, revision);

rusi.add(updateStatusItem);
UpdateRecordsResponse response = kintoneRecordManager.updateRecordsStatus(appID, rusi);
</pre>

</details>

### Methods

(none)