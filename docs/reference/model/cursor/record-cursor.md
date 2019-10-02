# RecordCursor Model

General record cursor response, using for data response from the kintone app

## CreateRecordCursorResponse

### Methods

#### getId()

> get the The cursor ID.

**Declaration**
```
  public String getId()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get Id Sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
			String username = "your_username";
			String password = "your_password";
			// Init authenticationAuth
			Auth kintoneAuth = new Auth();
			kintoneAuth.setPasswordAuth(username, password);

			String myDomainName = "your_domain";
			Connection connection = new Connection(myDomainName, kintoneAuth);
			RecordCursor recordCursor = new RecordCursor(connection);

			// get filekey
			Integer appID = 18;
			int size = 500;
			String query = "your_query";
			ArrayList fields = new ArrayList();
			fields.add("your_field");

			CreateRecordCursorResponse cursor;
			cursor = recordCursor.createCursor(appID, fields, query, size);
			System.out.println("cursorID: " + cursor.getId());
		} catch (KintoneAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
</pre>

</details>

#### getTotalCount()

> get Total of the records.

**Declaration**
```
  public Integer getTotalCount()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get Total Count Sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    try {
			String username = "your_username";
			String password = "your_password";
			// Init authenticationAuth
			Auth kintoneAuth = new Auth();
			kintoneAuth.setPasswordAuth(username, password);

			String myDomainName = "your_domain";
			Connection connection = new Connection(myDomainName, kintoneAuth);
			RecordCursor recordCursor = new RecordCursor(connection);

			// get filekey
			Integer appID = your_appId;
			int size = your_sizeCursor;
			String query = "your_query";
			ArrayList fields = new ArrayList();
			fields.add("your_field");

			CreateRecordCursorResponse cursor;
			cursor = recordCursor.createCursor(appID, fields, query, size);
			System.out.println("totalRecord: " + cursor.getTotalCount());
		} catch (KintoneAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
</pre>

</details>

## GetRecordCursorResponse

### Methods

#### getRecords()

> get the Records data response.

**Declaration**
```
  public ArrayList<HashMap<String, FieldValue>> getRecords()
```

**Parameter**

(none)

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
        
        int appID = your_appId;
        int size = your_size_recordCursor;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);
        String cursorId = cursor.getId();
        GetRecordCursorResponse response = recordCursor.getRecords(cursorId);
        ArrayList<HashMap<String, FieldValue>> listRecord = response.getRecords();
        System.out.println("Records: " + listRecord);
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
</pre>

</details>

#### getNext()

> States whether there are more records that can be acquired from the cursor. <br>
<b>true</b>: There are still records to be acquired.<br>
<b>false</b>: There are no more records to be acquired.

**Declaration**
```
  public Boolean getNext()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get Next</Summary>

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
        
        int appID = your_appId;
        int size = your_size_recordCursor;
        String query = "your_query";
        ArrayList fields = new ArrayList();
        fields.add("your_field");

        CreateRecordCursorResponse cursor = recordCursor.createCursor(appID, fields, query, size);
        String cursorId = cursor.getId();
        GetRecordCursorResponse response = recordCursor.getRecords(cursorId);
        Boolean status = response.getNext();
        System.out.println("Status Next: " + status);
    } catch (KintoneAPIException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
</pre>

</details>