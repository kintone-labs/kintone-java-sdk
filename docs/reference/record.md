# Record

Provide manipulate functions on records: get, update, delete, update the record status & assignees in the kintone app

## Constructor

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| connection | [Connection](../connection) | yes | The connection module of this SDK.

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
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init Record Module
Record kintoneRecordManager = new Record(kintoneOnDemoDomain);
</pre>

</details>

## Methods

### getRecord(app, id)

> Retrieves details of 1 record from an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| id | Integer | yes | The record ID in kintone app


**Return**

[GetRecordResponse](../record-model/#getrecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get record</Summary>

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

// execute GET RECORD API
Integer appID = 1;
Integer recordID = 1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);
</pre>

</details>

### getRecords(app, query, fields, totalCount)

> Retrieves details of multiple records from an app using a query string.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| query | String | (optional) | [The query string](https://developer.kintone.io/hc/en-us/articles/213149287#getrecords) that will specify what records will be responded.
| fields | ArrayList<String\>| (optional) | List of field codes you want in the response.
| totalCount | Boolean | (optional) | If "true", the request will retrieve total count of records match with query conditions.

**Return**

[GetRecordsResponse](../record-model/#getrecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get records</Summary>

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

// execute GET RECORDS API
Integer appID = 1;
String query = "$id >=" +  1 + "and $id <=" + 10 + "order by $id asc";
GetRecordsResponse response = kintoneRecordManager.getRecords(appID, query, null, true);
</pre>

</details>

### getAllRecordsByCursor(Integer app, String query, Array<String\> fields)

> Retrieves details of all records from an app using a query string.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| query | String | (optional) | [The query string](https://developer.kintone.io/hc/en-us/articles/213149287#getrecords) that will specify what records will be responded.
| fields | ArrayList<String\>| (optional) | List of field codes you want in the response.

**Return**

[GetRecordsResponse](../record-model/#getrecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get all records by cursor</Summary>

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

    // execute GET RECORDS API
    Integer appID = 1;
    String query = "$id >=" +  1 + "and $id <=" + 10 + "order by $id asc";
    GetRecordsResponse response = kintoneRecordManager.getAllRecordsByCursor(appID, query, null);
</pre>

</details>

### addRecord(app, record)

>Add one record to an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| record | HashMap&lt;String, [FieldValue](../record-field-model#fieldvalue)\> | (optional) | The record data to be add to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page

**Return**

[AddRecordResponse](../record-model/#addrecordresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Add record</Summary>

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

// execute ADD RECORD API
Integer appID = 1;
HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");
record.put("FieldCode1", fv);

AddRecordResponse response = kintoneRecordManager.addRecord(appID, record);
</pre>

</details>

### addRecords(app, records)

>Add multiple records to an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | ArrayList<HashMap&lt;String, [FieldValue](../record-field-model#fieldvalue)\>\> | yes | List of records data to be add to kintone app. About the format, please look the sample below or [reference](#reference) at the end of this page.

**Return**

[AddRecordsResponse](../record-model/#addrecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Add multi records</Summary>

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

// execute ADD RECORDS API
Integer appID = 1;
ArrayList&lt;HashMap&lt;String, FieldValue&gt;> records = new ArrayList&lt;HashMap&lt;String, FieldValue&gt;>();
HashMap&lt;String, FieldValue&gt; record1 = new HashMap&lt;String, FieldValue&gt;();
HashMap&lt;String, FieldValue&gt; record2 = new HashMap&lt;String, FieldValue&gt;();

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

### deleteRecords(app, ids)

> Deletes multiple records in an app.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| ids | ArrayList<Integer\> | yes | The list ids of record will be delete.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Delete multi record</Summary>

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

// execute DELETE RECORDS API
Integer appID = 1;
ArrayList&lt;Integer&gt; ids = new ArrayList&lt;Integer&gt;();
ids.add(1);
ids.add(2);

kintoneRecordManager.deleteRecords(appID, ids);
</pre>

</details>

### deleteRecordsWithRevision(app, idsWithRevision)

> Deletes multiple records in an app with revision.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| idsWithRevision | HashTable<Integer, Integer\> | yes |  (**key**: `The Id of record`, **value**: `The Revision of record.`)

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Delete record with revision</Summary>

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
</pre>

</details>

### getComments(app, record, order, offset, limit)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| record | Integer | yes | The kintone app ID
| order | String | (optional) | The sort order of the Comment ID. Please select **asc** or **desc**
| offset | Integer | (optional) | The number of first comments will be ignored.
| limit | Integer | (optional) | The number of records to retrieve.

**Return**

[GetCommentsResponse](../record-comment-model/#getcommentsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get comments</Summary>

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

// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);
</pre>

</details>

### addComment(app, record, comment)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID |
| record | Integer | yes | The kintone app ID |
| comment | [CommentContent](../record-comment-model/#commentcontent) | yes | About the format, please look the sample below or [reference](#reference) at the end of this page.|

**Return**

[AddCommentResponse](../record-comment-model/#addcommentresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Add comment</Summary>

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

// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList&lt;CommentMention&gt; mentionList = new ArrayList&lt;CommentMention&gt;();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);

AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);
</pre>

</details>

### deleteComment(app, record, comment)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| record | Integer | yes | The record ID on kintone app
| comment | Integer | yes | The comment ID on kintone record

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Delete comment</Summary>

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

// execute DELETE RECORD_COMMENT API
Integer app = 1;
Integer record = 1;
Integer comment = 1;

kintoneRecordManager.deleteComment(app, record, comment);
</pre>

</details>

### getAllRecordsByQuery(app, query, fields, totalCount)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| query | String | (optional) | The query string that will specify what records will be responded.
| fields | ArrayList&lt;String&gt; | (optional) | List of field codes you want in the response.
| totalCount | Boolean | (optional) | If "true", the request will retrieve total count of records match with query conditions.

**Return**

[GetRecordsResponse](../model/record/record-model/#getrecordsresponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get all records by query</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    Integer appID = {YOUR_APP_ID};
    String query = {YOUR_QUERY};
    ArrayList&lt;String&gt; fields = new ArrayList&lt;String&gt;();
    GetRecordsResponse getAllRecords = kintoneRecordManager.getAllRecordsByQuery(appID, query, fields);
</pre>
</details>

### deleteAllRecordsByQuery(Integer app, String query)

> Delete all records by indicating query. Can delete over 2000 records, but can't do rollback.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| query | String | (optional) | The query string that will specify what records will be responded. If nothing is specified, fields will be returned from all accessible records. The query detail can't indicate **limit** and **offset**.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Delete all records by query</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
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

### addAllRecords(Integer app, ArrayList<HashMap&lt;String, [FieldValue](../model/record/record-field-model#fieldvalue)\>\> records)
> Add all records to the kintone app

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| app | Integer | yes | The kintone app ID
| records | Array&lt;HashTable&lt;String, [FieldValue](../model/record/record-field-model#fieldvalue)&gt;&gt; | yes | The records data which will add to kintone app

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>update all records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer appID = 114;
ArrayList&lt;HashMap&lt;String, FieldValue&gt;&gt; records = new ArrayList&lt;HashMap&lt;String, FieldValue&gt;&gt;();
 
HashMap&lt;String, FieldValue&gt; record = new HashMap&lt;String, FieldValue&gt;();
 
FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("test_updateRecords3x");
 
record.put("文字列__1行", fv);
records.add(record);
try {
    BulkRequestResponse bulkRequestResponse = this.passwordAuthRecordManagerment.addAllRecords(appID, records);
    AddRecordsResponse addRecordsResponse =  (AddRecordsResponse) bulkRequestResponse.getResults().get(0);
    System.out.println("record ID: " + addRecordsResponse.getIDs().get(0));
    System.out.println("revision: " + addRecordsResponse.getRevisions().get(0));
    /*
    output:
        record ID: 58179   // record ID
        revision: 1  // revision
    */
} catch (BulksException e) {
    System.out.println(e.getResults());
 
    // Ex: User update 6000 records:
    // Case 1: If there error occur in record 0
    // err response:[KintoneAPIException]
    
    // Case 2: the error occur in record 4000
    // err response:
    //  [
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    AddRecordsResponse,
    //    KintoneAPIException
    //  ]
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
    
    RecordUpdateKey updateKey = new RecordUpdateKey("title", "update 123");
    
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
