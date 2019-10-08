# Record

Provide manipulate functions on records: get, update, delete, update the record status & assignees in the kintone app

## Constructor

**Declaration**
```
    public Record(Connection connection)
```

**Parameter**

| Name | Description |
| --- | --- |
| connection | The connection module of this SDK.([Connection](../connection))

**Sample code**

<details class="tab-container" open>
<Summary>Init record module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String USERNAME = "YOUR_USERNAME";
    String PASSWORD = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuthWithPassword = new Auth();
    kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

    // Init Connection without "guest space ID"
    Connection kintoneOnDemoDomain = new Connection("YOUR_DOMAIN", kintoneAuthWithPassword);

    // Init Record Module
    Record kintoneRecordManager = new Record(kintoneOnDemoDomain);
</pre>

</details>

## Methods

### getRecord(app, id)

> Retrieves details of 1 record from an app.

**Declaration**
```
    public GetRecordResponse getRecord(Integer app, Integer id) throws KintoneAPIException 
```

**Parameter**

| Name|  Description |
| --- | --- |
| app | The kintone app ID
| id | The record ID in kintone app

**Sample code**

<details class="tab-container" open>
<Summary>Get record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    try {
        String USERNAME = "YOUR_USERNAME";
        String PASSWORD = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuthWithPassword = new Auth();
        kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

        // Init Connection without "guest space ID"
        Connection kintoneOnDemoDomain = new Connection("YOUR_DOMAIN", kintoneAuthWithPassword);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

        // execute GET RECORD API
        Integer appID = YOUR_APPID;
        Integer recordID = YOUR_RECORD_ID;
        GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
	}
</pre>

</details>

### getRecords(app, query, fields, totalCount)

> Retrieves details of multiple records from an app using a query string.

**Declaration**
```
    public GetRecordsResponse getRecords(Integer app, String query, ArrayList<String> fields, Boolean totalCount) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app ID
| query |  [The query string](https://developer.kintone.io/hc/en-us/articles/213149287#getrecords) that will specify what records will be responded.
| fields | List of field codes you want in the response.
| totalCount | If "true", the request will retrieve total count of records match with query conditions.

**Sample code**

<details class="tab-container" open>
<Summary>Get records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    try {
        String USERNAME = "YOUR_USERNAME";
        String PASSWORD = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuthWithPassword = new Auth();
        kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

        // Init Connection without "guest space ID"
        Connection kintoneOnDemoDomain = new Connection("YOUR_DOMAIN", kintoneAuthWithPassword);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

        // execute GET RECORDS API
        Integer appID = YOUR_APPID;
        String query = "YOUR_QUERY";
        GetRecordsResponse response = kintoneRecordManager.getRecords(appID, query, null, true);
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
	}
   
</pre>

</details>

### getAllRecordsByCursor(Integer app, String query, Array<String\> fields)

> Retrieves details of all records from an app using a query string.

**Declaration**
```
    public GetRecordsResponse getAllRecordsByCursor(Integer app, String query, ArrayList<String> fields) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app ID
| query | [The query string](https://developer.kintone.io/hc/en-us/articles/213149287#getrecords) that will specify what records will be responded.
| fields | List of field codes you want in the response.

**Sample code**

<details class="tab-container" open>
<Summary>Get all records by cursor</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        String USERNAME = "YOUR_USERNAME";
        String PASSWORD = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuthWithPassword = new Auth();
        kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

        // Init Connection without "guest space ID"
        Connection kintoneOnDemoDomain = new Connection("YOUR_DOMAIN", kintoneAuthWithPassword);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

        // execute GET RECORDS API
        Integer appID = YOUR_APPID;
        String query = "YOR_QUERY";
        GetRecordsResponse response = kintoneRecordManager.getAllRecordsByCursor(appID, query, null);
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
	}
    
</pre>

</details>

### addRecord

>Add one record to an app.

**Declaration**
```
public AddRecordResponse addRecord(Integer app) throws KintoneAPIException
public AddRecordResponse addRecord(Integer app, HashMap<String, FieldValue> record) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app ID
| record | The record data to be add to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page. (HashMap<String, [FieldValue](../model/record/record-field-model#fieldvalue)\>)

**Sample code**

<details class="tab-container" open>
<Summary>Add record</Summary>

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
    HashMap<String, FieldValue> record = new HashMap<>();

    FieldValue fv = new FieldValue();
    fv.setType(FieldType.SINGLE_LINE_TEXT);
    fv.setValue("FIELD_VALUE");
    record.put("YOUR_FIELD_CODE", fv);
    try {
        AddRecordResponse response = kintoneRecord.addRecord(appID, record);
    } catch (KintoneAPIException e) {
        System.out.println(e.toString());
    }
</pre>

</details>

### addRecords

>Add multiple records to an app.

**Declaration**
```
public AddRecordsResponse addRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records) throws KintoneAPIException
```
**Parameter**

| Name| Description |
| --- | --- | 
| app | The kintone app ID
| records | List of records data to be add to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page. (ArrayList<HashMap<String, [FieldValue](../model/record/record-field-model#fieldvalue)\>\>)

**Sample code**

<details class="tab-container" open>
<Summary>Add multi records</Summary>

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
    ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
    HashMap<String, FieldValue> record1 = new HashMap<>();
    HashMap<String, FieldValue> record2 = new HashMap<>();

    FieldValue fv1 = new FieldValue();
    fv1.setType(FieldType.SINGLE_LINE_TEXT);
    fv1.setValue("FIELD_VALUE_1");

    FieldValue fv2 = new FieldValue();
    fv2.setType(FieldType.SINGLE_LINE_TEXT);
    fv2.setValue("sample_AddRecords2");

    record1.put("FIELD_CODE_OF_RECORD_1", fv1);
    record2.put("FIELD_CODE_OF_RECORD_2", fv2);

    records.add(record1);
    records.add(record2);

    try {
        AddRecordsResponse response = kintoneRecord.addRecords(appID, records);
    } catch (KintoneAPIException e) {
        System.out.println(e.toString());
    }
</pre>

</details>

### updateRecordByID(app, id, record, revision)

> Updates details of 1 record in an app by specifying its record number.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| id | Integer | yes | The record ID on kintone app
| record | HashMap&lt;String, [FieldValue](../record-field-model#fieldvalue)\> | yes | The record data to be update in  kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.
| revision | Integer | (optional) | The revision number of record

**Return**

[UpdateRecordResponse](../record-model/#updaterecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Update record by ID</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String USERNAME = "YOUR_USERNAME";
String PASSWORD = "YOUR_PASSWORD";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

// execute UPDATE RECORD API
Integer appID = 1;
Integer recordID = 1;
Integer revision = 1;

HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_updateRecordById");

record.put("FieldCode1", fv);

UpdateRecordResponse response = kintoneRecordManager.updateRecordByID(appID, recordID, record, revision);
</pre>

</details>

### updateRecordByUpdateKey(app, updateKey, record, revision)

Updates details of 1 record in an app by unique key.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| updateKey | [RecordUpdateKey](../record-model/#recordupdatekey) | yes | The unique key of the record to be updated. About the format, please look the sample below or [reference](#reference) at the end of this page.
| record | HashMap&lt;String, [FieldValue](../record-field-model#fieldvalue)\>  | yes | The record data will be added to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.
| revision | Integer | (optional) | The revision number of record

**Return**

[UpdateRecordResponse](../record-model/#updaterecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Update record by UpdateKey</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String USERNAME = "YOUR_USERNAME";
String PASSWORD = "YOUR_PASSWORD";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

// execute UPDATE RECORD API
Integer appID = 1;
HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();
RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", "unique_value1");
Integer revision = 1;

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_updateRecordByUpdateKey");

record.put("FieldCode1", fv);

UpdateRecordResponse response = kintoneRecordManager.updateRecordByUpdateKey(appID, uKey, record, revision);
</pre>

</details>

### updateRecords(app, records)

> Updates details of multiple records in an app, by specifying their record number, or a different unique key.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | ArrayList<[RecordUpdateItem](../record-model/#recordupdateitem)\> | yes | The record data will be added to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.

**Return**

[UpdateRecordsResponse](../record-model/#updaterecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Update multi records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String USERNAME = "YOUR_USERNAME";
String PASSWORD = "YOUR_PASSWORD";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

// execute UPDATE RECORDS API
Integer appID = 1;
HashMap&lt;String, FieldValue&gt; record1 = new HashMap&lt;String, FieldValue&gt;();
HashMap&lt;String, FieldValue&gt; record2 = new HashMap&lt;String, FieldValue&gt;();

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("test_updateRecords1");

FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("test_updateRecords2");

record1.put("FieldCode1", fv1);
record2.put("FieldCode1", fv2);

ArrayList&lt;RecordUpdateItem&gt; records = new ArrayList&lt;RecordUpdateItem&gt;();
records.add(new RecordUpdateItem(1, null, null, record1));
records.add(new RecordUpdateItem(2, null, null, record2));

UpdateRecordsResponse response = kintoneRecordManager.updateRecords(appID, records);
</pre>

</details>

### deleteRecords

> Delete multiple records in an app.

**Declaration**
```
public void deleteRecords(Integer app, ArrayList<Integer> ids) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The ID of kintone app
| ids | Array of record IDs that will be deleted

**Sample code**

<details class="tab-container" open>
<Summary>Delete multi record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String USERNAME = "YOUR_USERNAME";
    String PASSWORD = "YOUR_PASSWORD";
    String DOMAIN = "YOUR_DOMAIN.COM";

    // Init authenticationAuth
    Auth kintoneAuthWithPassword = new Auth();
    kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

    // Init Connection without "guest space ID"
    Connection kintoneConnection = new Connection(DOMAIN, kintoneAuthWithPassword);

    // Init Record Module
    Record kintoneRecordManager = new Record(kintoneConnection);

    // execute DELETE RECORDS API
    Integer appID = 1;
    ArrayList&lt;Integer&gt; ids = new ArrayList&lt;Integer&gt;();
    ids.add(1);
    ids.add(2);

    kintoneRecordManager.deleteRecords(appID, ids);
</pre>

</details>

### deleteRecordsWithRevision

> Delete multiple records in an app with revision.

**Declaration**
```
public void deleteRecordsWithRevision(Integer app, HashMap<Integer, Integer> idsWithRevision) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The ID of kintone app
| idsWithRevision | <ul><li>HashTable<Integer, Integer>:<ul><li>Key: The Id of record</li><li>Value: The Revision of record.</li></ul><li>The Expected revision number.<ul><li>The id number will correspond to the revision number in each HashTable's item.</li><li>If the revision number does not match, an error will occur and no records will be deleted.</li><li>If the revision number is left blank or is -1, the revision number will not be checked for the corresponding record ID.</li></ul></li></ul>

**Sample code**

<details class="tab-container" open>
<Summary>Delete record with revision</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String USERNAME = "YOUR_USERNAME";
    String PASSWORD = "YOUR_PASSWORD";
    String DOMAIN = "YOUR_DOMAIN.COM";

    // Init authenticationAuth
    Auth kintoneAuthWithPassword = new Auth();
    kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

    // Init Connection without "guest space ID"
    Connection kintoneConnection = new Connection(DOMAIN, kintoneAuthWithPassword);

    // Init Record Module
    Record kintoneRecordManager = new Record(kintoneConnection);

    // execute DELETE RECORDS API
    Integer appID = 1;
    HashMap&lt;Integer, Integer&gt; idsWithRevision = new HashMap&lt;Integer, Integer&gt;();

    idsWithRevision.put(1, 1);
    idsWithRevision.put(2, null);
    idsWithRevision.put(3, -1);

    kintoneRecordManager.deleteRecordsWithRevision(appID, idsWithRevision);
</pre>

</details>

### updateRecordAssignees(app, id, assignees, revision)

> Update assignees of a record.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app  | Integer | yes | The kintone app ID
| id  | Integer | yes | The record ID of kintone app
| assignees | ArrayList<String\> | yes | The user code(s) of the assignee(s)
| revision | Integer | (option) | The revision number of record

**Return**

[UpdateRecordResponse](../record-model/#updaterecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>update record Assignees</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String USERNAME = "YOUR_USERNAME";
String PASSWORD = "YOUR_PASSWORD";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

// execute UPDATE RECORD API
Integer appID = 1;
Integer recordID =1;
ArrayList&lt;String&gt; assignees = new ArrayList&lt;String&gt;();
assignees.add("sample_user");
Integer revision = 1;

UpdateRecordResponse response = kintoneRecordManager.updateRecordAssignees(appID, recordID, assignees, revision);
</pre>

</details>

### updateRecordStatus(app, id, action, assignee, revision)

> Updates the Status of a record of an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID.
| id | Integer | yes | The record ID on kintone app.
| action | String | yes | The Action name will be run.
| assignee | String | (Conditionally required) | The next Assignee. Specify the Assignee's log in name.<br>Required, if the "Assignee List" of the current status is set to "User chooses one assignee from the list to take action", and a selectable assignee exists.
| revision | Integer | (optional) | The revision of record

**Return**

[UpdateRecordResponse](../record-model/#updaterecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Update record status</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String USERNAME = "YOUR_USERNAME";
String PASSWORD = "YOUR_PASSWORD";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

// execute UPDATE RECORD API
Integer appID = 1;
Integer recordID =1;
String assignee = "sample_user";
String action = "処理開始";
Integer revision = 1;

UpdateRecordResponse response = kintoneRecordManager.updateRecordStatus(appID, recordID, action, assignee, revision);
</pre>

</details>

### updateRecordsStatus(app, records)

> Updates the Status of multiple records of an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | ArrayList<[RecordUpdateStatusItem](../record-model/#recordupdatestatusitem)\> | yes | The recod status data. See belowsample codee or [reference](#reference) at the end of this page to know format.

**Return**

[UpdateRecordsResponse](../record-model/#updaterecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Update multi record status</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        String USERNAME = "YOUR_USERNAME";
        String PASSWORD = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuthWithPassword = new Auth();
        kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

        // Init Connection without "guest space ID"
        Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

        // execute UPDATE RECORDS API
        Integer appID = 1;
        ArrayList&lt;RecordUpdateStatusItem&gt; rusi = new ArrayList&lt;RecordUpdateStatusItem&gt;();

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
        UpdateRecordsResponse response = kintoneRecordManager.updateRecordsStatus(appID, rusi);
    } catch(Exception e) {
	    System.out.println(e);
    }
</pre>

</details>

### getComments(app, record, order, offset, limit)

> Retrieves multiple comments from a record in an app.

**Declaration**
```
    public GetCommentsResponse getComments(Integer app, Integer record, String order, Integer offset, Integer limit) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app ID
| record | The kintone app ID
| order | The sort order of the Comment ID. Please select **asc** or **desc**
| offset | The number of first comments will be ignored.
| limit | The number of records to retrieve.

**Sample code**

<details class="tab-container" open>
<Summary>Get comments</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        String USERNAME = "YOUR_USERNAME";
        String PASSWORD = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuthWithPassword = new Auth();
        kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

        // Init Connection without "guest space ID"
        Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneOnDemoDomain);

        // execute GET RECORD_COMMENTS  API
        Integer appID = your_appId;
        Integer recordID = your_recordId;
        String order = "your_order";

        GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, null, null);
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
	}
</pre>

</details>

### addComment

**Declaration**
```
public AddCommentResponse addComment(Integer app, Integer record, CommentContent comment) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app |  The kintone app ID |
| record | The kintone record ID |
| comment | About the format, please look the sample below or [reference](#reference) at the end of this page. ([CommentContent](../model/record/record-comment-model/#commentcontent))|

**Sample code**

<details class="tab-container" open>
<Summary>Add comment</Summary>

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
    Integer recordId = 0;   // Input your record id
    CommentContent comment = new CommentContent();
    ArrayList<CommentMention> mentionList = new ArrayList<>();
    CommentMention mention = new CommentMention();
    mention.setCode("YOUR_USER_CODE");
    mention.setType("USER");
    mentionList.add(mention);
    comment.setText("YOUR_COMMENT_CONTENT");
    comment.setMentions(mentionList);

    try {
        AddCommentResponse response = kintoneRecord.addComment(appID, recordId, comment);
    } catch (KintoneAPIException e) {
        System.out.println(e.toString());
    }
</pre>

</details>

### deleteComment

> Delete comment of record in an app.

**Declaration**
```
public void deleteComment(Integer app, Integer record, Integer comment) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The ID of kintone app
| record | The ID of record
| comment | The ID of comment

**Sample code**

<details class="tab-container" open>
<Summary>Delete comment</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String USERNAME = "YOUR_USERNAME";
    String PASSWORD = "YOUR_PASSWORD";
    String DOMAIN = "YOUR_DOMAIN.COM";

    // Init authenticationAuth
    Auth kintoneAuthWithPassword = new Auth();
    kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

    // Init Connection without "guest space ID"
    Connection kintoneConnection = new Connection(DOMAIN, kintoneAuthWithPassword);

    // Init Record Module
    Record kintoneRecordManager = new Record(kintoneConnection);

    // execute DELETE RECORD_COMMENT API
    Integer app = 1;
    Integer record = 1;
    Integer comment = 1;

    kintoneRecordManager.deleteComment(app, record, comment);
</pre>

</details>

### getAllRecordsByQuery(app, query, fields, totalCount)

> Retrieves details of multiple records from an App by specifying the App ID and a query string.

**Declaration**
```
    public GetRecordsResponse getAllRecordsByQuery(Integer app, String query, ArrayList<String> fields, Boolean totalCount) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The kintone app ID
| query | The query string that will specify what records will be responded.
| fields | List of field codes you want in the response.
| totalCount | If "true", the request will retrieve total count of records match with query conditions.

**Sample code**

<details class="tab-container" open>
<Summary>Get all records by query</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
			String USERNAME = "your_username";
			String PASSWORD = "your_password";

			// Init authenticationAuth
			Auth kintoneAuthWithPassword = new Auth();
			kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

			// Init Connection without "guest space ID"
			Connection kintoneOnDemoDomain = new Connection("your_domain", kintoneAuthWithPassword);

			// Init Record Module
			Record kintoneRecordManager = new Record(kintoneOnDemoDomain);
			Integer appID = your_appId;
			Integer recordID = your_recordId;
			String query = "your_query";
			ArrayList listField= new ArrayList<String>();
		    GetRecordsResponse getAllRecords = kintoneRecordManager.getAllRecordsByQuery(appID, query, listField);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
</pre>
</details>

### deleteAllRecordsByQuery

> Delete all records by indicating query. Can delete over 2000 records, but can't do rollback.

**Declaration**
```
public BulkRequestResponse deleteAllRecordsByQuery(Integer app) throws BulksException, KintoneAPIException
public BulkRequestResponse deleteAllRecordsByQuery(Integer app, String query) throws BulksException, KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| app | The ID of kintone app
| query | The query string that will specify what records will be responded. If nothing is specified, fields will be returned from all accessible records. The query detail can't indicate **limit** and **offset**.

**Sample code**

<details class="tab-container" open>
<Summary>Delete all records by query</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String USERNAME = "YOUR_USERNAME";
    String PASSWORD = "YOUR_PASSWORD";
    String DOMAIN = "YOUR_DOMAIN.COM";

    // Init authenticationAuth
    Auth kintoneAuthWithPassword = new Auth();
    kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

    // Init Connection without "guest space ID"
    Connection kintoneConnection = new Connection(DOMAIN, kintoneAuthWithPassword);

    Integer appID = 114;
    String query = "$id >=" +  1 + "and $id <=" + 10 + "order by $id asc";
    try {
        // Init Record Module
        Record kintoneRecord = new Record(kintoneConnection);
        BulkRequestResponse bulkRequestResponse = kintoneRecord.deleteAllRecordsByQuery(appID, query);
    } catch (BulksException e) {
        System.out.println(e.getResults());
        // Ex: If User delete 6000 records:
        // Case 1: If there error occur in record 0
        // Err response:
        // [KintoneAPIException]
        // Case 2: the error occur in record 4000
        // err response
        // [
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    BulkRequestResponse,
        //    KintoneAPIException
        //  ]
    }
</pre>
</details>

### updateAllRecords(Integer app, ArrayList<RecordUpdateItem\> records)
> Update all records to the kintone app

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | Array<[RecordUpdateItem](../model/record/record-model/#recordupdateitem)> | yes | The records data which will update to kintone app

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>update all records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer appID = 114;
HashMap &lt;String, FieldValue&gt; record = new HashMap();
 
FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("test_updateRecords3x");
 
record.put("文字列__1行", fv);
 
ArrayList&lt;RecordUpdateItem&gt; records = new ArrayList&lt;RecordUpdateItem&gt;();
records.add(new RecordUpdateItem(58178, null, null, record));
try {
    BulkRequestResponse bulkRequestResponse = this.passwordAuthRecordManagerment.updateAllRecords(appID, records);
    UpdateRecordsResponse updateRecordsResponse =  (UpdateRecordsResponse) bulkRequestResponse.getResults().get(0);
    System.out.println("record ID: " + updateRecordsResponse.getRecords().get(0).getID());
    System.out.println("revision: " + updateRecordsResponse.getRecords().get(0).getRevision());
    /*
    output:
        record ID: 58178   // record ID
        revision: 5  // revision
    */
} catch (BulksException e) {
    System.out.println(e.getResults());
 
    // Ex: If User update 6000 records:
    // Case 1: If there error occur in record 0
    // Err response:
    // [KintoneAPIException]
    // Case 2: the error occur in record 4000
    // err response
    // [
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    UpdateRecordsResponse,
    //    KintoneAPIException
    //  ]
}
</pre>

</details>

### addAllRecords
> Add all records to the kintone app

**Declaration**
```
public BulkRequestResponses addAllRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records) throws KintoneAPIException, BulksException
```

**Parameter**

| Name| Description |
| --- | --- | 
| app | The kintone app ID
| records | The records data which will add to kintone app

**Sample code**

<details class="tab-container" open>
<Summary>update all records</Summary>

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
        HashMap<String, FieldValue> record1 = new HashMap<>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("FIELD_VALUE_1");
        record.put("FIELD_CODE_1", fv);

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("FIELD_VALUE_2");
        record.put("FIELD_CODE_2", fv);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        records.add(record);
        records.add(record1);
        kintoneRecord.addAllRecords(appID, records);
    } catch (KintoneAPIException e) {
        System.out.println("KintoneAPIException " + e.toString());
    } catch (BulksException e) {
        System.out.println("BulksException " + e.toString());
    }
</pre>

</details>

### upsertRecord(app, updateKey, record, revision)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| updateKey | [RecordUpdateKey](../model/record/record-model/#recordupdatekey) | yes | The unique key of the record to be updated. About the format, please look the sample below or [reference](#reference) at the end of this page.
| record | HashMap<String, [FieldValue](../model/record/record-field-model#fieldvalue)\>  | yes | The record data will be added to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.
| revision | Integer | (optional) | The revision number of record

**Return**

[AddRecordResponse](../model/record/record-model/#addrecordresponse) or [UpdateRecordResponse](../model/record/record-model/#updaterecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Upsert record</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer appID = {YOUR_APP_ID};
    FieldValue fv = new FieldValue();
    fv.setType(FieldType.SINGLE_LINE_TEXT);
    fv.setValue( {YOUR_FIELD_VALUE} );
    
    HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();
    record.put("title", fv);
    
    RecordUpdateKey updateKey = new RecordUpdateKey("detail", "update 123");
    
    kintoneRecordManager.upsertRecord(appID, updateKey, record, 1);

</pre>

</details>

### upsertRecords(app, updateKey, records, revision)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | ArrayList<[RecordUpdateItem](../model/record/record-model/#recordupdateitem)\> | yes | The record data will be added to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.

**Return**

[BulkRequestResponse](../model/bulk-request/bulk-request-response)

**Sample code**

<details class="tab-container" open>
<Summary>Upsert records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer appID = {YOUR_APP_ID};
    ArrayList&lt;RecordsUpsertItem&gt; upsertRecords = new ArrayList&lt;RecordsUpsertItem&gt;();
    
    ArrayList&lt;HashMap&lt;String, FieldValue&gt; &gt; records = new ArrayList&lt;HashMap&lt;String, FieldValue&gt; &gt;();
    
    FieldValue fv = new FieldValue();
    fv.setType(FieldType.SINGLE_LINE_TEXT);
    fv.setValue("Title 123");
    
    HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();
    record.put("title", fv);
    
    RecordUpdateKey updateKey = new RecordUpdateKey("title", "update 123");
    
    upsertRecords.add(new RecordsUpsertItem(updateKey, record));
    kintoneRecordManager.upsertRecords(appID, upsertRecords);

</pre>
</details>

## Reference

- [Get Record](https://developer.kintone.io/hc/en-us/articles/213149287/) `on developer network`
- [Add Record](https://developer.kintone.io/hc/en-us/articles/212494628/)`on developer network`
- [Update Record](https://developer.kintone.io/hc/en-us/articles/213149027/)`on developer network`
- [Delete Record](https://developer.kintone.io/hc/en-us/articles/212494558/)`on developer network`
- [Get Comments](https://developer.kintone.io/hc/en-us/articles/219105188)`on developer network`
- [Add Comment](https://developer.kintone.io/hc/en-us/articles/219501367)`on developer network`
- [Delete Comment](https://developer.kintone.io/hc/en-us/articles/219562607)`on developer network`
- [Update Record Status](https://developer.kintone.io/hc/en-us/articles/213149747)`on developer network`
- [Update Record Assignees](https://developer.kintone.io/hc/en-us/articles/219563427)`on developer network`
