# File

Provide manipulate functions on file: file download & file upload in the kintone app.


## Constructor

**Declaration**
```
  public File(Connection connection)
```


**Parameter**

| Name| Description |
| --- | --- |
| connection | The connection module of this SDK.

**Sample code**

<details class="tab-container" open>
<Summary>Initial file class</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

  Auth kintoneAuth = new Auth();
  String username = "your_username";
  String password = "your_password";
  kintoneAuth.setPasswordAuth(username, password);
  String myDomainName = "domain";
  Connection connection = new Connection(myDomainName, kintoneAuth);
  File fileManagement = new File(connection);

</pre>

</details>

## Methods

### upload(filePath)

> Upload file kintone via Rest API

**Declaration**
```
  public FileModel upload(String filePath) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- |
| filePath | The full path of file on your environment

**Sample code**

<details class="tab-container" open>
<Summary>Get app sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username"
  String password = "your_password"

  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  // Init Connection
  String myDomainName = "your_domain";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);

  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);

  // execute upload file API
  String uploadPath = "your_path";
  try {
			FileModel fileModel = kintoneFileManager.upload(uploadPath + "test.txt");
	} catch (KintoneAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
</pre>

</details>

### download(fileKey, outPutFilePath)

> Download file kintone via Rest API

**Declaration**
```
  public void download(String fileKey, String outPutFilePath) throws KintoneAPIException 
```

**Parameter**

| Name| Description |
| --- | --- |
| fileKey | The file key of the uploaded file on kintone
| outPutFilePath | The full path of output file on your environment

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username"
  String password = "your_password"

  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  String myDomainName = "your_domain";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);

  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);
  // Init Record Module
  Record kintonRecordManager = new Record(kintoneOnDemoDomain);

  // get filekey
  Integer appID = your_id;
  Integer recordID = your_record_id;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    ArrayList fileList =  (ArrayList) fileVal.getValue();
  // execute download file API
    String downloadPath = "your_path";
    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        kintoneFileManager.download(fdata.getFileKey(), downloadPath + fdata.getName());
    }
  } catch (KintoneAPIException e) {
    e.printStackTrace();
  }
</pre>

</details>
