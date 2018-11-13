# Form Fields

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## FormFields

### Methods

#### getApp()

> Get the app

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

** Source code **

```java
Integer app = formfields.getApp();
```

</details>

#### getRevision()

> Get the revision

**Parameter **

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Revision</Summary>

** Source code **

```java
Integer revision = formfields.getRevision();
```

</details>

#### getProperties()

> Get the properties

**Parameter **

(none)

**Return**

 Map<String, [Field](#field)\>

**Sample code**

<details class="tab-container" open>
<Summary>get Properties</Summary>

** Source code **

```java
Map<String, Field> properties = formfields.getProperties();
```

</details>

## FieldGroup

!!! warning
    - extend the abstract class  "[Field](#field)"

### Methods

#### getLabel()

> Get the label

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Label</Summary>

** Source code **

```java
String label = fieldGroup.getLabel();
```

</details>

#### getNoLabel()

> Get the noLabel

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get NoLabel</Summary>

** Source code **

```java
Boolean noLabel = fieldGroup.getNoLabel();
```

</details>

#### getOpenGroup()

> Get the openGroup

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get OpenGroup</Summary>

** Source code **

```java
Boolean openGroup = fieldGroup.getOpenGroup();
```

</details>

## FieldMapping

### Methods

#### getField()

> Get the field

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Field</Summary>

** Source code **

```java
String field = fieldMapping.getField();
```

</details>

#### getRelatedFields()

> Get the relatedFields

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Related Fields</Summary>

** Source code **

```java
String relatedFields = fieldMapping.getRelatedFields();
```

</details>

## SubTableField

!!! warning
    - extend the abstract class  "[Field](#field)"

### Methods

</details>

#### getFields()

> Get the fields

**Parameter**

(none)

**Return**

 Map<String, [AbstractInputField](../form-fields-input/#abstractinputfield)\>

**Sample code**

<details class="tab-container" open>
<Summary>get Fields</Summary>

** Source code **

```java
 Map<String, AbstractInputField> fields = subTableField.getFields();
```

</details>

## Field

!!! warning
    - This class is an abstract class.

### Methods

#### getCode()

> Get the code

**Parameter**

(none)

**Return**

String

#### getType()

> Get the type

**Parameter**

(none)

**Return**

LayoutType

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`