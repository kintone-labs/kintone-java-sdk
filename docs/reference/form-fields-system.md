# System

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## AssigneeField

!!! warning
    - extend the abstract class  "[AbstractProcessManagementField](#abstractprocessmanagementfield)"

### Methods

(none)

## CategoryField

!!! warning
    - extend the abstract class  "[AbstractSystemField](#abstractsystemfield)"

### Methods

#### getEnabled()

> Get the enabled

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Enabled</Summary>

** Source code **

```java
Boolean enabled = categoryField.getEnabled();
```

</details>

## CreatedTimeField

!!! warning
    - extend the abstract class  "[AbstractSystemInfoField](#abstractsysteminfofield)"

### Methods

(none)

## CreatorField

!!! warning
    - extend the abstract class  "[AbstractSystemInfoField](#abstractsysteminfofield)"

### Methods

(none)

## ModifierField

!!! warning
    - extend the abstract class  "[AbstractSystemInfoField](#abstractsysteminfofield)"

### Methods

(none)

## RecordNumberField

!!! warning
    - extend the abstract class  "[AbstractSystemInfoField](#abstractsysteminfofield)"

### Methods

(none)

## StatusField

!!! warning
    - extend the abstract class  "[AbstractProcessManagementField](#abstractprocessmanagementfield)"

### Methods

(none)

## UpdatedTimeField

!!! warning
    - extend the abstract class  "[AbstractSystemInfoField](#abstractsysteminfofield)"

### Methods

(none)

## AbstractSystemField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field)"
    - This class is an abstract class.

### Methods

#### getLabel()

> Get the label

**Parameter**

(none)

**Return**

String

## AbstractProcessManagementField

!!! warning
    - extend the abstract class  "[AbstractSystemField](#abstractsystemfield)"
    - This class is an abstract class.

### Methods

#### getEnabled()

> Get the enabled

**Parameter**

(none)

**Return**

Boolean

## AbstractSystemInfoField

!!! warning
    - extend the abstract class  "[AbstractSystemField](#abstractsystemfield)"
    - This class is an abstract class.

### Methods

#### getNoLabel()

> Get the noLabel

**Parameter**

(none)

**Return**

Boolean

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`