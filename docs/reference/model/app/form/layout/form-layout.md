# Form Layout

Get the layout of a form.
Length and width of the field,
The fields you set on the table and their order,
Put fields in the Group field and their layout,
label, space, and border settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## FormLayout

### Methods

#### getRevision()

Get the revision

**Parameter**

(none)

**Return**

String

#### getLayout()

Get the layout

**Parameter **

(none)

**Return**

List<[ItemLayout](#itemlayout)\>

## FieldLayout

### Methods

#### getType()

Get the type

**Parameter**

(none)

**Return**

String

#### getCode()

Get the code

**Parameter**

(none)

**Return**

String

#### getElementId()

Get the elementId

**Parameter**

(none)

**Return**

String

#### getLabel()

Get the label

**Parameter**

(none)

**Return**

String

#### getSize()

Get the size

**Parameter**

(none)

**Return**

[FieldSize](#fieldsize)

## FieldSize

### Methods

#### getWidth()

Get the width

**Parameter**

(none)

**Return**

String

#### getHeight()

Get the height

**Parameter**

(none)

**Return**

String

#### getInnerHeight()

Get the innerHeight

**Parameter**

(none)

**Return**

String

## GroupLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getCode()

Get the code

**Parameter**

(none)

**Return**

String

#### getLayout()

Get the layout

**Parameter**

(none)

**Return**

List<[RowLayout](#rowlayout)>

## RowLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getFields()

Get the fields

**Parameter**

(none)

**Return**

List<[FieldLayout](#fieldlayout)>

## SubTableLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getCode()

Get the code

**Parameter**

(none)

**Return**

String

## ItemLayout

!!! warning
    - This class is an abstract class.

### Methods

#### getType()

Get the type

**Parameter**

(none)

**Return**

LayoutType

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)