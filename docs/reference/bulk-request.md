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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id

	HashMap<String, FieldValue> record = new HashMap<>();
	FieldValue fv = new FieldValue();

	fv.setType(FieldType.SINGLE_LINE_TEXT);
	fv.setValue("FIELD_VALUE");
	record.put("YOUR_FIELD_CODE", fv);

	kintoneBulkRequest.addRecord(appID, record);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id

	ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
	HashMap<String, FieldValue> record = new HashMap<>();
	FieldValue fv = new FieldValue();

	fv.setType(FieldType.SINGLE_LINE_TEXT);
	fv.setValue("FIELD_VALUE");
	record.put("YOUR_FIELD_CODE", fv);

	records.add(record);
	kintoneBulkRequest.addRecords(appID, records);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer recordID = 0; // Input your record id
	Integer revision = 0; // Latest revision of the settings

	HashMap<String, FieldValue> record = new HashMap<>();
	FieldValue fv = new FieldValue();

	fv.setType(FieldType.SINGLE_LINE_TEXT);
	fv.setValue("FIELD_VALUE");
	record.put("YOUR_FIELD_CODE", fv);

	kintoneBulkRequest.updateRecordByID(appID, recordID, record, revision);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer revision = 0; // Latest revision of the settings

	RecordUpdateKey updateKey = new RecordUpdateKey("YOUR_UPDATE_KEY_FIELD", "YOUR_UPDATE_KEY_VALUE");

	HashMap<String, FieldValue> record = new HashMap<>();
	FieldValue fv = new FieldValue();

	fv.setType(FieldType.SINGLE_LINE_TEXT);
	fv.setValue("FIELD_VALUE");
	record.put("YOUR_FIELD_CODE", fv);

	kintoneBulkRequest.updateRecordByUpdateKey(appID, updateKey, record, revision);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer revision = 0; // Latest revision of the settings
	Integer recordId1 = 0; // Input your record id
	Integer recordId2 = 0; // Input your record id

	HashMap<String, FieldValue> record1 = new HashMap<>();
	HashMap<String, FieldValue> record2 = new HashMap<>();

	FieldValue fv1 = new FieldValue();
	fv1.setType(FieldType.SINGLE_LINE_TEXT);
	fv1.setValue("FIELD_VALUE_1");
	record1.put("YOUR_FIELD_CODE_1", fv1);

	FieldValue fv2 = new FieldValue();
	fv2.setType(FieldType.SINGLE_LINE_TEXT);
	fv2.setValue("FIELD_VALUE_2");
	record2.put("YOUR_FIELD_CODE_2", fv2);

	ArrayList<RecordUpdateItem> records = new ArrayList<>();
	records.add(new RecordUpdateItem(recordId1, revision, null, record1));
	records.add(new RecordUpdateItem(recordId2, null, null, record2));

	kintoneBulkRequest.updateRecords(appID, records);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id

	ArrayList<Integer> deleteIds = new ArrayList<>();
	deleteIds.add(1);   // Input deleted record id
	deleteIds.add(2);   // Input deleted record id

	kintoneBulkRequest.deleteRecords(appID, deleteIds);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id

	HashMap<Integer, Integer> idsWithRevision = new HashMap<>();

	idsWithRevision.put(1, 1);  // Input recordId, revision
	idsWithRevision.put(2, null);   // Input recordId, revision

	kintoneBulkRequest.deleteRecordsWithRevision(appID, idsWithRevision);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer recordID = 0;   //Input your record id
	Integer revision = 0; // Latest revision of the settings
	
	ArrayList<String> assignees = new ArrayList<>();
	assignees.add("YOUR_USER_CODE");
	kintoneBulkRequest.updateRecordAssignees(appID, recordID, assignees, revision);
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
	/*
	* Enable process management for using this function
	*/
	
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer recordID = 0;   //Input your record id
	Integer revision = 0; // Latest revision of the settings
	String action = "YOUR_ACTION_NAME";
	String assignee = "YOUR_USER_CODE";

	kintoneBulkRequest.updateRecordStatus(appID, recordID, action, assignee, revision);
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
	/*
	* Enable process management for using this function
	*/

	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);
	Integer appID = 0;  // Input your app id
	Integer recordID1 = 1;  // Input your record id
	Integer recordID2 = 2;  // Input your record id
	Integer revision1 = 1;   // Latest revision of the settings
	Integer revision2 = null;   // Latest revision of the settings
	String action = "YOUR_ACTION_NAME";
	String assignee = "YOUR_USER_CODE";

	ArrayList<RecordUpdateStatusItem> updateStatusItems = new ArrayList<>();

	updateStatusItems.add(new RecordUpdateStatusItem(action, assignee, recordID1, revision1));
	updateStatusItems.add(new RecordUpdateStatusItem(action, assignee, recordID2, revision2));

	kintoneBulkRequest.updateRecordsStatus(appID, updateStatusItems);
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
	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	// Init authenticationAuth
	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	// Init Connection without "guest space ID"
	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	// Init BulkRequest Module
	BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);

	kintoneBulkRequest.addRecord(/*[Args]*/);
	kintoneBulkRequest.addRecords(/*[Args]*/);
	kintoneBulkRequest.updateRecords(/*[Args]*/);
	kintoneBulkRequest.deleteRecords(/*[Args]*/);

	try {
		BulkRequestResponse responses = kintoneBulkRequest.execute();
	} catch (Exception e) {
		e.printStackTrace();
	}
</pre>

</details>

## Reference

- [Get Record](https://developer.kintone.io/hc/en-us/articles/213149287/) `on developer network`