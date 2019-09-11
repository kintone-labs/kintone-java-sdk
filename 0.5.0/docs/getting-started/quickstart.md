# Quickstart

## Requirement

* [Java SE](https://adoptopenjdk.net/) (Version 9 or later)
* [maven](https://maven.apache.org/download.cgi) (Version 3.5.4 or later)
* [kintone-java-sdk](https://github.com/kintone/kintone-java-sdk)

## Code example

<details class="tab-container" open>
<Summary>Get record sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
import com.cybozu.kintone.client.authentication.*;
import com.cybozu.kintone.client.connection.*;
import com.cybozu.kintone.client.model.record.*;
import com.cybozu.kintone.client.module.record.*;

String USERNAME = "cybozu";
String PASSWORD = "cybozu";

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

try {
  GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);
} catch (Exception e) {
  System.out.println(e.getMessage());
}
</pre>

<strong class="tab-name">Response success</strong>

<pre class="inline-code">
{
    "record":{
        // record data should be here
    }
}
</pre>

<strong class="tab-name">Response error</strong>

<pre class="inline-code">
{
    id: '{ID}',
    code: '{CODE}',
    message: '{Message string}',
}
</pre>

</details>

<details class="tab-container" open>
<Summary>Bulk request sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
import java.util.*;
import com.cybozu.kintone.client.authentication.*;
import com.cybozu.kintone.client.connection.*;
import com.cybozu.kintone.client.constant.*;
import com.cybozu.kintone.client.model.record.*;
import com.cybozu.kintone.client.model.record.field.*;
import com.cybozu.kintone.client.module.record.*;
import com.cybozu.kintone.client.model.bulkrequest.*;
import com.cybozu.kintone.client.module.bulkrequest.*;

String USERNAME = "cybozu";
String PASSWORD = "cybozu";

// Init authenticationAuth
Auth kintoneAuthWithPassword = new Auth();
kintoneAuthWithPassword.setPasswordAuth(USERNAME, PASSWORD);

// Init Connection without "guest space ID"
Connection kintoneOnDemoDomain = new Connection("sample.domain.dot", kintoneAuthWithPassword);

// Init BulkRequest Module
BulkRequest bulkRequestManager = new BulkRequest(kintoneOnDemoDomain);

// update record & delete records with bulk request
HashMap&lt;String, FieldValue&gt; record1 = new HashMap&lt;String, FieldValue&gt;();
ArrayList&lt;RecordUpdateItem&gt; dataUpdate = new ArrayList&lt;RecordUpdateItem&gt;();

Integer recordID = 1;
Integer revision = 1;

FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.NUMBER);
fv1.setValue("2222");
record1.put("FieldCode1", fv1);
dataUpdate.add(new RecordUpdateItem(recordID, revision, null, record1));

ArrayList&lt;Integer&gt; recordIDsDelete = new ArrayList&lt;Integer&gt;();
recordIDsDelete.add(1);
recordIDsDelete.add(2);
recordIDsDelete.add(3);

Integer appID = 1;

bulkRequest.deleteRecords(appID, recordIDsDelete);
bulkRequest.updateRecords(appID, dataUpdate);

// execute BulkRequest
try {
  BulkRequestResponse responses = bulkRequestManager.execute();

  // get results
  ArrayList&lt;Object&gt; results = responses.getResults();
  HashMap result1 = (HashMap)results.get(0);
  UpdateRecordsResponse result2 = (UpdateRecordsResponse)results.get(1);

  // data Response of the delete request
  System.out.println("delete request: " + result1.toString());

  // data Response of the update request
  ArrayList&lt;RecordUpdateResponseItem&gt; result21 = result2.getRecords();
  System.out.println("update request ID: " + result21.get(0).getID());
  System.out.println("update request Revison: " + result21.get(0).getRevision());
} catch (Exception e) {
  System.out.println(e.getMessage());
}
</pre>

<strong class="tab-name">Response success</strong>

<pre class="inline-code">
{
    "record":{
        // record data should be here
    }
}
</pre>

<strong class="tab-name">Response error</strong>

<pre class="inline-code">
{
    id: '{ID}',
    code: '{CODE}',
    message: '{Message string}',
}
</pre>

</details>
