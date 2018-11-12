# Form Layout

Get the layout of a form.
Length and width of the field,
The fields you set on the table and their order,
Put fields in the Group field and their layout,
label, space, and border settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## FormLayout

### Methods

#### getRevision()

> Get the revision

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Revision</Summary>

** Source code **

```java
String revision = layout.getRevision();
```

</details>

#### getLayout()

> Get the layout

**Parameter **

(none)

**Return**

List<[ItemLayout](#itemlayout)\>

**Sample code**

<details class="tab-container" open>
<Summary>get Layout</Summary>

** Source code **

```java
List<ItemLayout> itemLayoutList = layout.getLayout();
```

</details>

## FieldLayout

### Methods

#### getType()

> Get the type

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Type</Summary>

** Source code **

```java
String type = fieldLayout.getType();
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
String code = fieldLayout.getCode();
```

</details>

#### getElementId()

> Get the elementId

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Element Id</Summary>

** Source code **

```java
String elementId = fieldLayout.getElementId();
```

</details>

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
String label = fieldLayout.getLabel();
```

</details>

#### getSize()

> Get the size

**Parameter**

(none)

**Return**

[FieldSize](#fieldsize)

**Sample code**

<details class="tab-container" open>
<Summary>get Size</Summary>

** Source code **

```java
FieldSize size = fieldLayout.getSize();
```

</details>

## FieldSize

### Methods

#### getWidth()

> Get the width

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Width</Summary>

** Source code **

```java
String width = fieldSize.getWidth();
```

</details>

#### getHeight()

> Get the height

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Height</Summary>

** Source code **

```java
String height = fieldSize.getHeight();
```

</details>

#### getInnerHeight()

> Get the innerHeight

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Inner Height</Summary>

** Source code **

```java
String innerHeight = fieldSize.getInnerHeight();
```

</details>

## GroupLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

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
String code = groupLayout.getCode();
```

</details>

#### getLayout()

> Get the layout

**Parameter**

(none)

**Return**

List<[RowLayout](#rowlayout)>

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

** Source code **

```java
List<RowLayout> layout = groupLayout.getLayout();
```

</details>

## RowLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getFields()

> Get the fields

**Parameter**

(none)

**Return**

List<[FieldLayout](#fieldlayout)>

**Sample code**

<details class="tab-container" open>
<Summary>get Fields</Summary>

** Source code **

```java
String fields = rowLayout.getFields();
```

</details>

## SubTableLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

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
String code = subTableLayout.getCode();
```

</details>

## ItemLayout

!!! warning
    - This class is an abstract class.

### Methods

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