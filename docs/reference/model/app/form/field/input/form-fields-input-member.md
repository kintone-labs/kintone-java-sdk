# Member

Get a list of fields and their settings.

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

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

#### getType

Get the type

**Declaration**
```
public MemberSelectEntityType getType()
```

**Parameter**

(none)

## UserSelectionField

!!! warning
    - extend the abstract class  "[AbstractMemberSelectField](#abstractmemberselectfield)"

### Methods

(none)

## AbstractMemberSelectField

!!! warning
    - extend the abstract class "[AbstractInputField](../form-fields-input/#abstractinputfield.md)"
    - This class is an abstract class.

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public ArrayList<MemberSelectEntity> getDefaultValue()
```

**Parameter**

(none)

#### getEntites

Get the entites

**Declaration**
```
public ArrayList<MemberSelectEntity> getEntities()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)