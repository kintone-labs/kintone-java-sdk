# Bulk Request Model

Store a list of requests and responses for a bulk request.

## BulkRequestModel

### Constructor

**Parameter**

(none)

### Methods

#### addRequest(bulkRequestItem)

**Parameter**

| Name| Type| Description |
| --- | --- | --- |
| bulkRequestItem | [BulkRequestItem](#bulkrequestitem) | The BulkRequest Item.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>add Request</Summary>

** Source code **

```java
BulkRequestModel bulkRequestModel = new BulkRequestModel();

Integer appID = 1;

HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
FieldValue fv = new FieldValue();
fv.setType("SINGLE_LINE_TEXT");
fv.setValue("test_value");
record.put("sample_FieldCode", fv);

AddRecordRequest addRecordRequest = new AddRecordRequest(appID, record);
BulkRequestItem bulkRequestItem = new BulkRequestItem("POST", "/k/v1/record.json", addRecordRequest);

bulkRequestModel.addRequest(bulkRequestItem);
```

</details>

##BulkRequestItem

### Constructor

**Parameter**

| Name| Type| Description |
| --- | --- | --- |
| method | String | The API method name.
| api | String | The path of the API.
| payload | Object | The parameters to be passed onto the API.Contents and formats will change depending on the API.

**Sample code**

<details class="tab-container" open>
<Summary>Init Bulk Request Item</Summary>

** Source code **

```java

Integer appID = 1;

HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
FieldValue fv = new FieldValue();
fv.setType("SINGLE_LINE_TEXT");
fv.setValue("test_value");
record.put("sample_FieldCode", fv);

AddRecordRequest addRecordRequest = new AddRecordRequest(appID, record);
BulkRequestItem bulkRequestItem = new BulkRequestItem("POST", "/k/v1/record.json", addRecordRequest);
```

</details>

##BulkRequestResponse

### Constructor

**Parameter**

(none)

### Methods

#### getResults()

**Parameter**

(none)

**Return**

ArrayList<Object\>

**Sample code**

<details class="tab-container" open>
<Summary>get Results</Summary>

** Source code **

```java
BulkRequest bulkRequestManager = new BulkRequest(connection);

Integer appID = 1;
Integer recordID =1;
Integer revision = 1;

ArrayList<String> assignees = new ArrayList<String>();
assignees.add("sample_user");

bulkRequestManager.updateRecordAssignees(appID, recordID, assignees, revision);
BulkRequestResponse responses = bulkRequestManager.execute();

// get results
ArrayList<Object> results = responses.getResults();
UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
```

</details>

