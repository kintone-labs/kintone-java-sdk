# Member

General information of the member(user/group/organization) on the kintone application


## Constructor

**Parameter**

| Name| Type| Description |
| --- | --- | --- |
| code | String | The user/group/organization code.
| name | String | The user/group/organization name.

**Sample code**

<details class="tab-container" open>
<Summary>Initial member class</Summary>

** Source code **

```java
String code = "usercode";
String name = "username";

Member member = new Member(code, name);
```

</details>

## Methods

### getCode()

> Get the code of the user/group/organization

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>Get the code of the user/group/organization</Summary>

** Source code **

```java

Record recordManagement = new Record(connection);

GetRecordResponse response = recordManagement.getRecord(APP_ID, id);
HashMap<String, FieldValue> resultRecord = response.getRecord();
Object fv = resultRecord.get("fieldCode").getValue();
Member member = (Member)fv;
String userCode = member.getCode();
String userName = member.getName();
```

</details>

### setCode()

> Set the code of the user/group/organization

**Parameter**

| Name| Type| Description |
| --- | --- | --- |
| code | String | The user/group/organization code.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Set the code of the user/group/organization</Summary>

** Source code **

```java
Member member = new Member
member.setCode("usercode");
member.setName("username");

Record recordManagement = new Record(connection);

Integer appID = 1;
HashMap<String, FieldValue> addRecord = new HashMap<String, FieldValue>();
FieldValue fv = new FieldValue();
fv.setType(FieldType.USER_SELECT);
fv.setValue(member);
addRecord.put("user", fv);

AddRecordResponse response = recordManagerment.addRecord(appID, addRecord);
```

</details>


### getName()

> Get the name of the user/group/organization

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>Get the name of the user/group/organization</Summary>

** Source code **

```java

Record recordManagement = new Record(connection);

GetRecordResponse response = recordManagement.getRecord(APP_ID, id);
HashMap<String, com.cybozu.kintone.client.model.record.field.FieldValue> resultRecord = response.getRecord();
Object fv = resultRecord.get("fieldCode").getValue();
Member member = (Member)fv;
String userCode = member.getCode();
String userName = member.getName();
```

</details>

### setName()

> Set the name of the user/group/organization

**Parameter**

| Name| Type| Description |
| --- | --- | --- |
| name | String | The user/group/organization name.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Set the name of the user/group/organization</Summary>

** Source code **

```java
Member member = new Member
member.setCode("usercode");
member.setName("username");

Record recordManagement = new Record(connection);
Integer appID = 1;
HashMap<String, FieldValue> addRecord = new HashMap<String, FieldValue>();
FieldValue fv = new FieldValue();
fv.setType(FieldType.USER_SELECT);
fv.setValue(member);
addRecord.put("user", fv);

AddRecordResponse response = recordManagerment.addRecord(appID, addRecord);
```

</details>
