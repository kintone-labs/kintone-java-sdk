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
  String username = "username";
  String password = "password";
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
  String username = "name";
  String password = "password";

  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  String myDomainName = "domain";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);

  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);
  // Init Record Module
  Record kintonRecordManager = new Record(kintoneOnDemoDomain);

  // get filekey
  Integer appID = 18;
  Integer recordID =134;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    ArrayList fileList =  (ArrayList) fileVal.getValue();
  // execute download file API
    String downloadPath = "C:/Users/h001587/directory/";
    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        kintoneFileManager.download(fdata.getFileKey(), downloadPath + fdata.getName());
    }
  } catch (KintoneAPIException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
</pre>

</details>
