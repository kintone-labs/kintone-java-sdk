# BulkRequest

The Bulk Request API allows multiple API requests to run on multiple kintone apps. The below API can be used with the Bulk Request API:

- Add Record
- Add Records
- Update Record
- Update Records
- Delete Records
- Update Status
- Update Statuses
- Update Assignees

## Constructor

**Declaration**
```
public BulkRequest(Connection connection) 
```

**Parameter**

| Name| Description |
| --- | --- | 
| connection | The connection module of this SDK ([Connection](../connection)).

**Sample code**

<details class="tab-container" open>
<Summary>Init bulk request module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
</pre>

</details>

## Methods

> All below methods (excluded `execute()` method) will add request to queue, you must execute the `execute()` function to get result of BulkRequest.

### addRecord

**Declaration**
```
public BulkRequest addRecord(Integer app)
public BulkRequest addRecord(Integer app, HashMap<String, FieldValue> record)
```

**Parameter**

See at [Record - addRecord](../record#addrecordapp-record)

**Sample code**

<details class="tab-container" open>
<Summary>add Record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

Integer appID = 1;


FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");

record.put("FieldCode1", fv);

bulkRequestManager.addRecord(appID, record);
</pre>

</details>

### addRecords

**Declaration**
```
public BulkRequest addRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records)
```

**Parameter**

See at [Record - addRecords](../record#addrecordsapp-records)

**Sample code**

<details class="tab-container" open>
<Summary>add multiple Records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer appID = 1;

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

bulkRequestManager.addRecords(appID, records);
</pre>

</details>

### updateRecordByID

**Declaration**
```
public BulkRequest updateRecordByID(Integer app, Integer id)
public BulkRequest updateRecordByID(Integer app, Integer id, HashMap<String, FieldValue> record) 
public BulkRequest updateRecordByID(Integer app, Integer id, Integer revision) 
public BulkRequest updateRecordByID(Integer app, Integer id, HashMap<String, FieldValue> record, Integer revision)
```

**Parameter**

See at [Record - updateRecordByID](../record#updaterecordbyidapp-id-record-revision)

**Sample code**

<details class="tab-container" open>
<Summary>update Record By ID</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

Integer appID = 1;
Integer recordID = 1;
Integer revision = 1;

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_updateRecordById");

record.put("FieldCode1", fv);

bulkRequestManager.updateRecordByID(appID, recordID, record, revision);
</pre>

</details>

### updateRecordByUpdateKey

**Declaration**
```
public BulkRequest updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey)
public BulkRequest updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey, HashMap<String, FieldValue> record) 
public BulkRequest updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey, Integer revision)
public BulkRequest updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey, HashMap<String, FieldValue> record, Integer revision)

```

**Parameter**

See at [Record - updateRecordByUpdateKey](../record#updaterecordbyupdatekeyapp-updatekey-record-revision)

**Sample code**

<details class="tab-container" open>
<Summary>update Record By UpdateKey</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", "unique_value1");

Integer appID = 1;
Integer revision = 1;

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_updateRecordByUpdateKey");

record.put("FieldCode1", fv);

bulkRequestManager.updateRecordByUpdateKey(appID, uKey, record, revision);
</pre>

</details>

### updateRecords

**Declaration**
```
public BulkRequest updateRecords(Integer app)
public BulkRequest updateRecords(Integer app, ArrayList<RecordUpdateItem> records)
```

**Parameter**

See at [Record - updateRecords](../record#updaterecordsapp-records)

**Sample code**

<details class="tab-container" open>
<Summary>update multiple Records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

Integer appID = 1;
Integer recordId1 = 1;
Integer recordId2 = 2;
Integer revision = 1;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
records.add(new RecordUpdateItem(recordId1, revision, null, record1));
records.add(new RecordUpdateItem(recordId2, null, null, record2));

bulkRequestManager.updateRecords(appID, records);
</pre>

</details>

### deleteRecords

**Declaration**
```
public BulkRequest deleteRecords(Integer app, ArrayList<Integer> ids)
```

**Parameter**

See at [Record - deleteRecords](../record#deleterecordsapp-ids)

**Sample code**

<details class="tab-container" open>
<Summary>Bulk Delete Records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);

Integer appID = 1;

ArrayList<Integer> ids = new ArrayList<Integer>();
ids.add(1);
ids.add(2);


bulkRequestManager.deleteRecords(appID, ids);
</pre>

</details>

### deleteRecordsWithRevision

**Declaration**
```
public BulkRequest deleteRecordsWithRevision(Integer app, HashMap<Integer, Integer> idsWithRevision) 
```

**Parameter**

See at [Record - deleteRecordsWithRevision](../record#deleterecordswithrevisionapp-idswithrevision)

**Sample code**

<details class="tab-container" open>
<Summary>delete Records With Revision</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();

Integer appID = 1;

idsWithRevision.put(1, 1);
idsWithRevision.put(2, null);
idsWithRevision.put(3, -1);

bulkRequestManager.deleteRecordsWithRevision(appID, idsWithRevision);
</pre>

</details>

### updateRecordAssignees

**Declaration**
```
public BulkRequest updateRecordAssignees(Integer app, Integer record, ArrayList<String> assignees) 
public BulkRequest updateRecordAssignees(Integer app, Integer record, ArrayList<String> assignees, Integer revision)
```

**Parameter**

See at [Record - updateRecordAssignees](../record#updaterecordassigneesapp-id-assignees-revision)

**Sample code**

<details class="tab-container" open>
<Summary>Update the Assignees for the record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);

Integer appID = 1;
Integer recordID =1;
Integer revision = 1;

ArrayList<String> assignees = new ArrayList<String>();
assignees.add("sample_user");

bulkRequestManager.updateRecordAssignees(appID, recordID, assignees, revision);
</pre>

</details>

### updateRecordStatus

**Declaration**
```
public BulkRequest updateRecordStatus(Integer app, Integer id, String action)
public BulkRequest updateRecordStatus(Integer app, Integer id, String action, String assignee)
public BulkRequest updateRecordStatus(Integer app, Integer id, String action, Integer revision) 
public BulkRequest updateRecordStatus(Integer app, Integer id, String action, String assignee, Integer revision) 
```

**Parameter**

See at [Record - updateRecordStatus](../record#updaterecordstatusapp-id-action-assignee-revision)

**Sample code**

<details class="tab-container" open>
<Summary>Update the status of a single record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);

Integer appID = 1;
Integer recordID =1;
String action = "処理開始";
String assignee = "sample_user";
Integer revision = 1;

bulkRequestManager.updateRecordStatus(appID, recordID, action, assignee, revision);
</pre>

</details>

### updateRecordsStatus

**Declaration**
```
public BulkRequest updateRecordsStatus(Integer app, ArrayList<RecordUpdateStatusItem> records)
```

**Parameter**

See at [Record - updateRecordsStatus](../record#updaterecordsstatusapp-records)

**Sample code**

<details class="tab-container" open>
<Summary>Update the status of multiple records in bulk</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();

Integer appID = 1;
String action = "処理開始";
String assignee = "sample_user1";
Integer recordID1 =1;
Integer recordID2 =2;
Integer recordID3 =3;
Integer revision1 = 1;
Integer revision2 = null;
Integer revision3 = -1;

rusi.add(new RecordUpdateStatusItem(action, assignee, recordID1, revision1));
rusi.add(new RecordUpdateStatusItem(action, assignee, recordID2, revision2));
rusi.add(new RecordUpdateStatusItem(action, assignee, recordID3, revision3));

bulkRequestManager.updateRecordsStatus(appID, rusi);
</pre>

</details>

### execute

> Execute the bulk request and get data response

**Declaration**
```
public BulkRequestResponse execute() throws KintoneAPIException 
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Execute bulk request</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
BulkRequest bulkRequestManager = new BulkRequest(connection);
bulkRequestManager.addRecord(/*[Args]*/);
bulkRequestManager.addRecords(/*[Args]*/);
bulkRequestManager.updateRecords(/*[Args]*/);
bulkRequestManager.deleteRecords(/*[Args]*/);

try {
  BulkRequestResponse responses = bulkRequestManager.execute();
} catch (Exception e) {
  System.out.println(e.getMessage());
}
</pre>

</details>

## Reference

- [Get Record](https://developer.kintone.io/hc/en-us/articles/213149287/) `on developer network`