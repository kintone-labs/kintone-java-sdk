# RecordCursor

This module provide functions to work with Record Cursor.

>
The user or API Token must have permission to view the records.

## Constructor

**Declaration**
```
    public RecordCursor(Connection connection)

```

### **Parameter**

| Name| Description |
| --- | --- |
| connection | The connection module of this SDK. ([Connection](../connection))

### **Sample code**

<details class="tab-container" open>
<Summary>Init app module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Auth kintoneAuth = new Auth();
    String username = "your_username";
    String password = "your_password";;
    kintoneAuth.setPasswordAuth(username, password);

    String myDomainName = "your_domain";
    Connection connection = new Connection(myDomainName, kintoneAuth);
    kintoneAuth = kintoneAuth.setPasswordAuth(username, password);

    RecordCursor recordCursor = new RecordCursor(connection);

</pre>

</details>

## Methods

### createCursor(Integer app, Array<String\> fields, String query, Integer size)

> Create a cursor.

**Declaration**
```
	public CreateRecordCursorResponse createCursor(Integer app, ArrayList<String> fields, String query, Integer size) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| appId | The kintone app ID
| fields | Fields of record to return
| query |  Query condition
| size | Number of records to retrieve per request. <br> Default: 100. <br>Maximum: 500.

**Sample code**

<details class="tab-container" open>
<Summary>create Cursor</Summary>

<strong class="tab-name">Source code</strong>
<pre class="inline-code">
    try {
        Auth kintoneAuth = new Auth();
        String username = "your_username";
        String password = "your_password";;
        kintoneAuth.setPasswordAuth(username, password);
        String myDomainName = "your_domain";
        Connection connection = new Connection(myDomainName, kintoneAuth);
        kintoneAuth = kintoneAuth.setPasswordAuth(username, password);
        RecordCursor recordCursor = new RecordCursor(connection);
        
        int appID = 110;
        int size = 500;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor;
        cursor = recordCursor.createCursor(appID, fields, query, size);
        System.out.println("cursorID: " + cursor.getId());
        System.out.println("totalRecord: " + cursor.getTotalCount());
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

</pre>

</details>

### getRecords(String cursorID)

> Get one block of records ([GetRecordCursorResponse](../model/cursor/record-cursor#GetRecordCursorResponse))

**Declaration**
```
    public GetRecordCursorResponse getRecords(String cursorID) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| cursorID | The cursor ID.

**Sample code**

<details class="tab-container" open>
<Summary>Get Records</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        Auth kintoneAuth = new Auth();
        String username = "your_username";
        String password = "your_password";;
        kintoneAuth.setPasswordAuth(username, password);
        String myDomainName = "your_domain";
        Connection connection = new Connection(myDomainName, kintoneAuth);
        kintoneAuth = kintoneAuth.setPasswordAuth(username, password);
        RecordCursor recordCursor = new RecordCursor(connection);
        
        int appID = 1;
        int size = 500;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);
        String cursorId = cursor.getId();
        GetRecordCursorResponse response = recordCursor.getRecords(cursorId);

        List<HashMap<String,FieldValue>>  listRecords = response.getRecords();
        for (Object record : listRecords) {
            for (Entry entry : ((HashMap<String, FieldValue>) record).entrySet()) {
                System.out.println(entry.getKey() + " " + ((FieldValue) entry.getValue()).getValue());
            }
        }
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
</pre>

</details>

### getAllRecords(String cursorID)

> Get all records

**Declaration**
```
    public GetRecordsResponse getAllRecords(String cursorID) throws KintoneAPIException
```

**Parameter**

| Name | Description |
| --- | --- |
| cursorID  | The cursor ID.

**Sample code**

<details class="tab-container" open>
<Summary>get Apps By IDs</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        Auth kintoneAuth = new Auth();
        String username = "your_username";
        String password = "your_password";;
        kintoneAuth.setPasswordAuth(username, password);
        String myDomainName = "your_domain";
        Connection connection = new Connection(myDomainName, kintoneAuth);
        kintoneAuth = kintoneAuth.setPasswordAuth(username, password);
        RecordCursor recordCursor = new RecordCursor(connection);
        
        int appID = 1;
        int size = 500;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);
        String cursorId = cursor.getId();

        GetRecordsResponse response = recordCursor.getAllRecords(cursorId);
        List<HashMap<String, FieldValue>> listRecords = response.getRecords();
        for (Object record : listRecords) {
            for (Entry entry : ((HashMap<String, FieldValue>) record).entrySet()) {
                System.out.println(entry.getKey() + " " + ((FieldValue) entry.getValue()).getValue());
            }
        }
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
</pre>

</details>

### deleteCursor(String cursorID)

> Delete a cursor

**Declaration**
```
    public void deleteCursor(String cursorID) throws KintoneAPIException
```

**Parameter**

| Name | Description |
| --- | --- |
| cursorID | The cursor ID.

**Sample code**

<details class="tab-container" open>
<Summary>Delete Cursor</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
        Auth kintoneAuth = new Auth();
        String username = "your_username";
        String password = "your_password";
        ;
        kintoneAuth.setPasswordAuth(username, password);
        String myDomainName = "your_domain";
        Connection connection = new Connection(myDomainName, kintoneAuth);
        kintoneAuth = kintoneAuth.setPasswordAuth(username, password);
        RecordCursor recordCursor = new RecordCursor(connection);

        int appID = 1;
        int size = 500;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);

        String cursorId = cursor.getId();
        recordCursor.deleteCursor(cursorId);
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
</pre>

</details>
