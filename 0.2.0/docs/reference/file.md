# File

Provide manipulate functions on file: file download & file upload in the kintone app.


## Constructor

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| connection | [Connection](../connection) | yes | The connection module of this SDK.

**Sample code**

<details class="tab-container" open>
<Summary>Initial file class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

File fileManagement = new File(connection);
</pre>

</details>

## Methods

### upload(filePath)

> Upload file kintone via Rest API

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| filePath | String | yes | The full path of file on your environment

**Return**

[FileModel](../file-model)

**Sample code**

<details class="tab-container" open>
<Summary>Get app sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String username = "cybozu";
String password = "cybozu";

// Init authenticationAuth
Auth kintoneAuth = new Auth();
kintoneAuth.setPasswordAuth(username, password);

// Init Connection
String myDomainName = "sample.cybozu.com";
Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);

// Init File Module
File kintoneFileManager = new File(kintoneOnDemoDomain);

// execute upload file API
String uploadPath = "C:/Users/Administrator/Desktop/upload";
FileModel fileModel = kintoneFileManager .upload(uploadPath + "test.txt");
</pre>

</details>

### download(fileKey, outPutFilePath)

> Download file kintone via Rest API

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| fileKey | String | yes | The file key of the uploaded file on kintone
| outPutFilePath | String | yes | The full path of output file on your environment

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String username = "cybozu";
String password = "cybozu";

// Init authenticationAuth
Auth kintoneAuth = new Auth();
kintoneAuth.setPasswordAuth(username, password);

String myDomainName = "sample.cybozu.com";
Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);

// Init File Module
File kintoneFileManager = new File(kintoneOnDemoDomain);
// Init Record Module
Record kintonRecordManager = new Record(kintoneOnDemoDomain);

// get filekey
Integer appID = 1;
Integer recordID =1;
GetRecordResponse recordJson = kintonRecordManager.getRecord(appID, recordID);
HashMap<String, FieldValue> recordVal = recordJson.getRecord();
FieldValue fileVal = recordVal.get("TempFile");
ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

// execute download file API
String downloadPath = "C:/Users/Administrator/Desktop/";
for (int i = 0; i < fileList.size(); i++) {
    FileModel fdata = fileList.get(i);
    kintoneFileManager.download(fdata.getFileKey(), downloadPath + fdata.getName());
}
</pre>

</details>
