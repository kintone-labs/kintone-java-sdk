# Related Record

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## RelatedApp

### Methods

#### getApp()

> Get the app

**Parameter**

(none)

**Return**

 String

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

** Source code **

```java
String app = relatedApp.getApp();
```

</details>

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
String app = relatedApp.getCode();
```

</details>

## ReferenceTable

### Methods

#### getCondition()

> Get the condition

**Parameter**

(none)

**Return**

[FieldMapping](../form-fields/#fieldmapping)

**Sample code**

<details class="tab-container" open>
<Summary>get Condition</Summary>

** Source code **

```java
FieldMapping condition = referenceTable.getCondition();
```

</details>

#### getFilterCond()

> Get the filterCond

**Parameter**

(none)

**Return**

 String

**Sample code**

<details class="tab-container" open>
<Summary>get FilterCond</Summary>

** Source code **

```java
String filterCond = referenceTable.getFilterCond();
```

</details>

#### getRelatedApp()

> Get the relatedApp

**Parameter**

(none)

**Return**

[RelatedApp](#relatedapp)

**Sample code**

<details class="tab-container" open>
<Summary>get Related App</Summary>

** Source code **

```java
RelatedApp relatedApp = referenceTable.getRelatedApp();
```

</details>

#### getSize()

> Get the size

**Parameter**

(none)

**Return**

 Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Size</Summary>

** Source code **

```java
Integer size = referenceTable.getSize();
```

</details>

#### getDisplayFields()

> Get the displayFields

**Parameter**

(none)

**Return**

 List<String\>

**Sample code**

<details class="tab-container" open>
<Summary>get Display Fields</Summary>

** Source code **

```java
List<String> displayFields = referenceTable.getDisplayFields();
```

</details>

#### getSort()

> Get the sort

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Sort</Summary>

** Source code **

```java
String sort = referenceTable.getSort();
```

</details>

## RelatedRecordsField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field)"

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
String label = relatedRecordsField.getLabel();
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
Boolean noLabel = relatedRecordsField.getNoLabel();
```

</details>

#### getReferenceTable()

> Get the referenceTable

**Parameter**

(none)

**Return**

[ReferenceTable](#referencetable)

**Sample code**

<details class="tab-container" open>
<Summary>get Reference Table</Summary>

** Source code **

```java
ReferenceTable referenceTable = relatedRecordsField.getReferenceTable();
```

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`