# Member

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## DepartmentSelectionField

!!! warning
    - extend the abstract class  "[AbstractMemberSelectField](#abstractmemberselectfield)"

### Methods

(none)

## GroupSelectionField

!!! warning
    - extend the abstract class  "[AbstractMemberSelectField](#abstractmemberselectfield)"

### Methods

(none)

## MemberSelectEntity

### Methods

#### getCode()

> Get the code

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

** Source code **

```java
String code = memberSelectEntity.getCode();
```

</details>

#### getType()

> Get the type

**Parameter**

(none)

**Return**

MemberSelectEntityType

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

** Source code **

```java
MemberSelectEntityType type = memberSelectEntity.getType();
```

</details>

## UserSelectionField

!!! warning
    - extend the abstract class  "[AbstractMemberSelectField](#abstractmemberselectfield)"

### Methods

(none)

## AbstractMemberSelectField

!!! warning
    - extend the abstract class "[AbstractInputField](../form-fields-input/#abstractinputfield)"
    - This class is an abstract class.

### Methods

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

List<[MemberSelectEntity](#memberselectentity)\>

#### getEntites()

> Get the entites

**Parameter**

(none)

**Return**

List<[MemberSelectEntity](#memberselectentity)\>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`