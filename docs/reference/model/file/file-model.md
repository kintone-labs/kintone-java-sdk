# File Model

Holds file information obtained from kintone.

## Methods

### getContentType()

Get the content type of the file.

**Declaration**
```
  public String getContentType()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username";
  String password = "your_password";
  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  String myDomainName = "tgbn0.kintone.com";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);
  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);
  // Init Record Module
  Record kintonRecordManager = new Record(kintoneOnDemoDomain);

  // get filekey
  Integer appID = your_appId;
  Integer recordID = your_recordId;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    System.out.println("fileVal" +fileVal.getValue());
    ArrayList fileList =  (ArrayList) fileVal.getValue();

    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        System.out.println("Content type: " + fdata.getContentType());
    }
  } catch (KintoneAPIException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
</pre>

</details>

### getFileKey()

Get the file key of the file.

**Declaration**
```
  public String getFileKey()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username";
  String password = "your_password";
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
  Integer appID = your_appId;
  Integer recordID = your_recordId;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    System.out.println("fileVal" +fileVal.getValue());
    ArrayList fileList =  (ArrayList) fileVal.getValue();

    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        System.out.println("File key: " + fdata.getFileKey());
    }
  } catch (KintoneAPIException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
</pre>

</details>

### getName()

Get the name of the file.

**Declaration**
```
  public String getName()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username";
  String password = "your_password";
  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  String myDomainName = "tgbn0.kintone.com";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);
  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);
  // Init Record Module
  Record kintonRecordManager = new Record(kintoneOnDemoDomain);

  // get filekey
  Integer appID = your_appId;
  Integer recordID = your_recordId;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    System.out.println("fileVal" +fileVal.getValue());
    ArrayList fileList =  (ArrayList) fileVal.getValue();

    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        System.out.println("Name: " + fdata.getName());
    }
  } catch (KintoneAPIException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
</pre>

</details>


### getSize()

Get the size of the file.


**Declaration**
```
  public String getSize()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
  String username = "your_username";
  String password = "your_password";
  // Init authenticationAuth
  Auth kintoneAuth = new Auth();
  kintoneAuth.setPasswordAuth(username, password);

  String myDomainName = "tgbn0.kintone.com";
  Connection kintoneOnDemoDomain = new Connection(myDomainName, kintoneAuth);
  // Init File Module
  File kintoneFileManager = new File(kintoneOnDemoDomain);
  // Init Record Module
  Record kintonRecordManager = new Record(kintoneOnDemoDomain);

  // get filekey
  Integer appID = your_appId;
  Integer recordID = your_recordId;
  GetRecordResponse recordJson;
  try {
    recordJson = kintonRecordManager.getRecord(appID, recordID);
    HashMap recordVal = recordJson.getRecord();
    FieldValue fileVal = (FieldValue) recordVal.get("file");
    System.out.println("fileVal" +fileVal.getValue());
    ArrayList fileList =  (ArrayList) fileVal.getValue();

    for (int i = 0; i < fileList.size(); i++) {
        FileModel fdata =  (FileModel) fileList.get(i);
        System.out.println("Size: " + fdata.getSize());
    }
  } catch (KintoneAPIException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
</pre>

</details>
