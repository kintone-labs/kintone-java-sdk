# Field Model

## FieldValue

General Field's value of the kintone app

### Constructor

**Parameter**

(none)

### Methods

#### getType

> get the type of field.

**Declaration**
```
public FieldType getType()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the type of field.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD API
Integer appID = 1;
Integer recordID =1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);

HashMap<String, FieldValue> resultRecord = response.getRecord();
FieldValue fv = resultRecord.get("sample field");
FieldType fieldType = fv.getType();
</pre>

</details>

#### setType

> set the type of field.

**Declaration**
```
public void setType(FieldType type)
```

**Parameter**

| Name| Description |
| ---  | --- |
| type | The type of field - kintone-sdk FieldType constants.

**Sample code**

<details class="tab-container" open>
<Summary>set the type of field.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer appID = 1;
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");
record.put("FieldCode1", fv);

AddRecordResponse response = kintoneRecordManager.addRecord(appID, record);
</pre>

</details>

#### getValue

> get the value of field in the record.

**Declaration**
```
public Object getValue()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the value of field in the record.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD API
Integer appID = 1;
Integer recordID =1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);

HashMap<String, FieldValue> resultRecord = response.getRecord();
FieldValue fv = resultRecord.get("sample field");
Object fieldValue = fv.getValue();
</pre>

</details>

#### setValue

> set the value of field in the record.

**Declaration**
```

```

**Parameter**

| Name| Description |
| --- | --- |
| value | The value of field in the record, read more at [Field Type here](https://developer.kintone.io/hc/en-us/articles/212494818/#responses).

**Sample code**

<details class="tab-container" open>
<Summary>set the value of field in the record.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer appID = 1;
HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

FieldValue fv = new FieldValue();
fv.setType(FieldType.SINGLE_LINE_TEXT);
fv.setValue("sample_AddRecord");
record.put("FieldCode1", fv);

AddRecordResponse response = kintoneRecordManager.addRecord(appID, record);
</pre>

</details>

##SubTableValueItem

### Constructor

**Parameter**

(none)

### Methods

#### getID

> get the ID of item in table.

**Declaration**
```
public Integer getID()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD API
Integer appID = 1;
Integer recordID =1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);

HashMap<String, FieldValue> resultRecord = response.getRecord();
FieldValue fv = resultRecord.get("SubTable field");
Object fieldValue = fv.getValue();
ArrayList<SubTableValueItem> subTable = (ArrayList<SubTableValueItem>)fieldValue;
Integer itemID = subTable.get(0).getID();
</pre>

</details>


#### setID

> set the ID of table.

**Declaration**
```
public void setID(Integer id)
```

**Parameter**

| Name | Description |
| --- | --- |
| id | The ID of table .

**Sample code**

<details class="tab-container" open>
<Summary>set the ID of item in table.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
SubTableValueItem tableItem = new SubTableValueItem();
tableItem.setID(00000);
HashMap<String, FieldValue> tableItemValue = new HashMap<String, FieldValue>();
FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("sample_text1");
FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("sample_text2");
tableItemValue.put("sample field1", fv1);
tableItemValue.put("sample field2", fv2);
tableItem.setValue(tableItemValue);  
</pre>

</details>

#### getValue

> get the value of field in the record.

**Declaration**
```
public HashMap<String, FieldValue> getValue()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD API
Integer appID = 1;
Integer recordID =1;
GetRecordResponse response = kintoneRecordManager.getRecord(appID, recordID);

HashMap<String, FieldValue> resultRecord = response.getRecord();
FieldValue fv = resultRecord.get("SubTable field");
Object fieldValue = fv.getValue();
ArrayList<SubTableValueItem> subTable = (ArrayList<SubTableValueItem>)fieldValue;
HashMap<String, FieldValue> itemValue= subTable.get(0).getValue();
</pre>

</details>

#### setValue

> set the value of field in the record.

**Declaration**
```
public void setValue(HashMap<String, FieldValue> value)
```

**Parameter**

| Name | Description |
| --- | --- |
| value | The row data of table. (HashMap<String, [FieldValue](#fieldvalue)\>)

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of item in table.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
SubTableValueItem tableItem = new SubTableValueItem();
tableItem.setID(00000);
HashMap<String, FieldValue> tableItemValue = new HashMap<String, FieldValue>();
FieldValue fv1 = new FieldValue();
fv1.setType(FieldType.SINGLE_LINE_TEXT);
fv1.setValue("sample_text1");
FieldValue fv2 = new FieldValue();
fv2.setType(FieldType.SINGLE_LINE_TEXT);
fv2.setValue("sample_text2");
tableItemValue.put("sample field1", fv1);
tableItemValue.put("sample field2", fv2);
tableItem.setValue(tableItemValue); 
</pre>

</details>


