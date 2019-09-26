# RecordCursor

This module provide functions to work with Record Cursor.

>
The user or API Token must have permission to view the records.

## Constructor

### **Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| connection | [Connection](../connection) | yes | The connection module of this SDK.

### **Sample code**

<details class="tab-container" open>
<Summary>Init app module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    // Init authentication
    Auth kintoneAuth = new Auth()
    
    // Password Authentication
    String username = "your_username"
    String password = "your_password"
    kintoneAuth = kintoneAuth.setPasswordAuth(username, password)
    Connection connection = Connection( "your_domain", kintoneAuth )
    RecordCursor recordCursor = new RecordCursor(connection);

</pre>

</details>

## Methods

### createCursor(Integer app, Array<String\> fields, String query, Integer size)

> Create a cursor.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| appId | Integer | yes | The kintone app ID
| fields | Array<String\> | (optional) | Fields of record to return
| query | String | (optional) | Query condition
| size | Integer | (optional) | Number of records to retrieve per request. <br> Default: 100. <br>Maximum: 500.

**Return**

[CreateRecordCursorResponse](../model/cursor/record-cursor#CreateRecordCursorResponse)

**Sample code**

<details class="tab-container" open>
<Summary>create Cursor</Summary>

<strong class="tab-name">Source code</strong>
<pre class="inline-code">

    RecordCursor recordCursor = new RecordCursor(connection);
 
    int appID = 110;
    int size = 500;
    String query = "order by 数値 desc";
    ArrayList<String> fields = new ArrayList<String>();
    fields.add("数値");
        
    CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);
    System.out.println("cursorID: " + cursor.getId());
    System.out.println("totalRecord: " + cursor.getTotalCount());
    
    /*
    output:
    cursorID: "your_cursor_id"
    totalRecord: 50
    */

</pre>

</details>

### getRecords(String cursorID)

> Get one block of records.

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| cursorID | String | (optional) | The cursor ID.

**Return**

[GetRecordCursorResponse](../model/cursor/record-cursor#GetRecordCursorResponse)

**Sample code**

<details class="tab-container" open>
<Summary>Get Records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    RecordCursor recordCursor = new RecordCursor(connection);
    
    String cursorId = "dc24fc1f-4195-41b3-a55d-7e4547d45de1";
    GetRecordCursorResponse response = recordCursor.getRecords(cursorId);
    
    ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
    for (HashMap<String, FieldValue> record : resultRecords) {
        for (Entry<String, FieldValue> entry : record.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getValue());
        }
    }
    /*
    output:
    数値 7669
    数値 7668
    数値 7667
    数値 7666
    数値 7665
    */
</pre>

</details>

### getAllRecords(String cursorID)

> Get all records

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| cursorID | String | yes | The cursor ID.

**Return**

[GetRecordsResponse](../model/record/record-model#GetRecordsResponse)

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    RecordCursor recordCursor = new RecordCursor(connection);
    
    String cursorId = "dc24fc1f-4195-41b3-a55d-7e4547d45de1";
    GetRecordsResponse response = recordCursor.getAllRecords(cursorId);
    
    ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
    for (HashMap<String, FieldValue> record : resultRecords) {
        for (Entry<String, FieldValue> entry : record.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getValue());
        }
    }
    
    /*
    output:
    数値 7669
    数値 7668
    数値 7667
    数値 7666
    数値 7665
    */
</pre>

</details>

### deleteCursor(String cursorID)

> Delete a cursor

**Parameter **

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| cursorID | String | yes | The cursor ID.

**Return**

none

**Sample code**

<details class="tab-container" open>
<Summary>Delete Cursor</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    RecordCursor recordCursor = new RecordCursor(connection);
    
    String cursorId = "dc24fc1f-4195-41b3-a55d-7e4547d45de1";
    recordCursor.deleteCursor(cursorId);
</pre>

</details>
