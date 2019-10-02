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

#### getRevision

Get the revision

**Declaration**
```
public String getRevision()
```

**Parameter**

(none)

#### getLayout

Get the layout

**Declaration**
```
public ArrayList<ItemLayout> getLayout()
```

**Parameter**

(none)

## FieldLayout

### Methods

#### getType

Get the type

**Declaration**
```
public String getType()
```

**Parameter**

(none)

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

#### getElementId

Get the elementId

**Declaration**
```
public String getElementId()
```

**Parameter**

(none)

#### getLabel

Get the label

**Declaration**
```
public String getLabel()
```

**Parameter**

(none)

#### getSize

Get the size

**Declaration**
```
public FieldSize getSize()
```

**Parameter**

(none)

## FieldSize

### Methods

#### getWidth

Get the width

**Declaration**
```
public String getWidth()
```

**Parameter**

(none)

#### getHeight

Get the height

**Declaration**
```
public String getHeight()
```

**Parameter**

(none)

#### getInnerHeight

Get the innerHeight

**Declaration**
```
public String getInnerHeight()
```

**Parameter**

(none)

## GroupLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

#### getLayout

Get the layout

**Declaration**
```
public ArrayList<RowLayout> getLayout()
```

**Parameter**

(none)

## RowLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getFields

Get the fields

**Declaration**
```
public ArrayList<FieldLayout> getFields()
```

**Parameter**

(none)

## SubTableLayout

!!! warning
    - extend the abstract class  "[ItemLayout](#itemlayout)"

### Methods

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

## ItemLayout

!!! warning
    - This class is an abstract class.

### Methods

#### getType

Get the type

**Declaration**
```
public LayoutType getType()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)