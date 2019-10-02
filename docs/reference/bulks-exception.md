# BulksException

Handle error responses when using multiple bulk request

## Methods

### getResults()

**Declaration**
```
public ArrayList<Object> getResults()
```
**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get responses if exist error when using multiple bulk request</Summary>

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
    HashMap<String, FieldValue> record = new HashMap<>();
    FieldValue fv = new FieldValue();

    fv.setType(FieldType.SINGLE_LINE_TEXT); // Input your field type
    fv.setValue("VALUE_OF_FIELD_CODE");
    record.put("YOUR_APP_FIELD_CODE", fv);
    records.add(record);
    try {
        BulkRequestResponse bulkRequestResponse = kintoneRecord.addAllRecords(appID, records);
        AddRecordsResponse addRecordsResponse = (AddRecordsResponse) bulkRequestResponse.getResults().get(0);
        System.out.println("New record ID: " + addRecordsResponse.getIDs().get(0));

        /*
        * Expected Output:
        *  New record ID: {RECORD_ID}
        */
    } catch (BulksException e) {
        System.out.println("Error: " + e.getResults());

        /*
        * Expected Output:
        *  Error: {ERROR_MESSAGE}
        */
    }
</pre>

</details>