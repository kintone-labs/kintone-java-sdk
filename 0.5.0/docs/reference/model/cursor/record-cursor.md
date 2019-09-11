# RecordCursor Model

General record cursor response, using for data response from the kintone app

## CreateRecordCursorResponse

### Methods

#### getId()

> get the The cursor ID.

**Parameter**

(none)

**Return**

String

#### getTotalCount()

> get Total of the records.

**Parameter**

(none)

**Return**

Integer

## GetRecordCursorResponse

### Methods

#### getRecords()

> get the Records data response.

**Parameter**

(none)

**Return**

ArrayList<HashMap<String, [FieldValue](../record-field-model#fieldvalue)\>\>

#### getNext()

> States whether there are more records that can be acquired from the cursor. <br>
<b>true</b>: There are still records to be acquired.<br>
<b>false</b>: There are no more records to be acquired.

**Parameter**

(none)

**Return**

Boolean