# kintoneAPIException

Handle error responses from kintone Rest API

## Methods

### getHttpErrorCode()

**Parameter **

(none)

**Return**

int

**Sample code**

<details class="tab-container" open>
<Summary>Get http error code</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appId = 1
Integer id = -1
try {
	GetRecordResponse response = recordManagerment.getRecord(appId, id);
} catch(kintoneException ke) {
	int errorCode = ke.getHttpErrorCode();
}

```

</details>

### getErrorResponse()

**Parameter **

(none)

**Return**

[ErrorResponse](https://developer.kintone.io/hc/en-us/articles/212495188#responses)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps with error response</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appId = 1
Integer id = -1
try {
	GetRecordResponse response = recordManagerment.getRecord(appId, id);
} catch(kintoneException ke) {
	String id = ke.getErrorResponse().getId();
	String message = ke.getErrorResponse().getMessage();
	String code = ke.getErrorResponse().getCode();
}

```

</details>

### getErrorResponses()

**Parameter **

(none)

**Return**

ArrayList&lt;[ErrorResponse](https://developer.kintone.io/hc/en-us/articles/212495188#responses)&gt;

**Sample code**

<details class="tab-container" open>
<Summary>Get apps with error responses</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appId = 1
Integer id = -1

HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("test_AddRecord");

BulkRequest bulkRequestManager = new BulkRequest(connection);
bulkRequestManager.addRecord(appId, record);
try {
	BulkRequestResponse responses = bulkRequestManager.execute();
} catch(kintoneException ke) {
	for(ErrorResponse errorResponse : ke.getErrorResponses()) {
		String id = errorResponse.getId();
		String message = errorResponse.getMessage();
		String code = errorResponse.getCode();
	}
}

```

</details>