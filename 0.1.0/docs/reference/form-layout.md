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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String revision = layout.getRevision();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<ItemLayout> itemLayoutList = layout.getLayout();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String type = fieldLayout.getType();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String code = fieldLayout.getCode();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String elementId = fieldLayout.getElementId();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String label = fieldLayout.getLabel();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
FieldSize size = fieldLayout.getSize();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String width = fieldSize.getWidth();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String height = fieldSize.getHeight();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String innerHeight = fieldSize.getInnerHeight();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String code = groupLayout.getCode();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<RowLayout> layout = groupLayout.getLayout();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String fields = rowLayout.getFields();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String code = subTableLayout.getCode();
</pre>

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