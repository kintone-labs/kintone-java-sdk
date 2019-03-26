# File Model

Holds file information obtained from kintone.

## Methods

### getFileKey()

> Get the file key of the uploaded file.

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>Get the file key of the uploaded file</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appId = 1;
Integer recordID = 1;
Integer revision = -1;
String uploadPath = "C:/Users/Administrator/Desktop/upload";

// file upload
File file = new File(connection);
FileModel fileModel = file.upload(uploadPath + "test.txt");

// update reocrd
fileParam.put("fileKey", fileModel.getFileKey());
fkeyList.add(fileParam);
fval.setValue(fkeyList);
updateParam.put("attachment1", fval);
Record recordManagement = new Record(connection);
UpdateRecordResponse updRes = recordManagement.updateRecordByID(appId, recordID, updateParam, revision);


</pre>

</details>

### getName()

> Get the name of the uploaded file.

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>Get the name of the uploaded file</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String appId = 1;
Integer recordID = 3;
String downloadPath = "C:/Users/Administrator/Desktop/download";

// get record
Record record = new Record(connection);
GetRecordResponse recordJson = record.getRecord(appId, recordID);
HashMap<String, FieldValue> recordVal = recordJson.getRecord();
FieldValue fileVal = recordVal.get("attachment1");
ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

// donwnload file
FileModel fdata = fileList.get(0);
File file = new File(connection);
file.download(fdata.getFileKey(), downloadPath + fdata.getName());

</pre>

</details>
