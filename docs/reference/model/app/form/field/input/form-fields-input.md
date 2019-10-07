# Input

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## AttachmentField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getThumbnailSize

Get the thumbnailSize

**Declaration**
```
public String getThumbnailSize()
```

**Parameter**

(none)

## CalculatedField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getExpression

Get the expression

**Declaration**
```
public String getExpression()
```

**Parameter**

(none)

#### getHideExpression

Get the hideExpression

**Declaration**
```
public Boolean getHideExpression()
```

**Parameter**

(none)

#### getDisplayScale

Get the displayScale

**Declaration**
```
public String getDisplayScale()
```

**Parameter**

(none)

#### getUnit

Get the unit

**Declaration**
```
public String getUnit()
```

**Parameter**

(none)

**Return**

String

#### getUnitPosition

Get the unitPosition

**Declaration**
```
public UnitPosition getUnitPosition()
```

**Parameter**

(none)

#### getFormat

Get the format

**Declaration**
```
public NumberFormat getFormat()
```

**Parameter**

(none)

## LinkField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

#### getUnique

Get the unique

**Declaration**
```
public Boolean getUnique()
```

**Parameter**

(none)

#### getMaxLength

Get the maxLength

**Declaration**
```
public String getMaxLength()
```

**Parameter**

(none)

#### getMinLength

Get the minLength

**Declaration**
```
public String getMinLength()
```

**Parameter**

(none)

#### getProtocol

Get the protocol

**Declaration**
```
public LinkProtocol getProtocol()
```

**Parameter**

(none)

## MultiLineTextField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

## NumberField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDisplayScale

Get the displayScale

**Declaration**
```
public String getDisplayScale()
```

**Parameter**

(none)

#### getUnit

Get the unit

**Declaration**
```
public String getUnit()
```

**Parameter**

(none)

#### getUnitPosition

Get the unitPosition

**Declaration**
```
public UnitPosition getUnitPosition()
```

**Parameter**

(none)

#### getDigit

Get the digit

**Declaration**
```
public Boolean getDigit()
```

**Parameter**

(none)

#### getMaxValue

Get the maxValue

**Declaration**
```
public String getMaxValue()
```

**Parameter**

(none)

#### getMinValue

Get the minValue

**Declaration**
```
public String getMinValue()
```

**Parameter**

(none)

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

#### getUnique

Get the unique

**Declaration**
```
public Boolean getUnique()
```

**Parameter**

(none)

## RichTextField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

## SingleLineTextField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getMinLength

Get the minLength

**Declaration**
```
public String getMinLength()
```

**Parameter**

(none)

#### getMaxLength

Get the maxLength

**Declaration**
```
public String getMaxLength()
```

**Parameter**

(none)

#### getExpression

Get the expression

**Declaration**
```
public String getExpression()
```

**Parameter**

(none)

#### getHideExpression

Get the hideExpression

**Declaration**
```
public Boolean getHideExpression()
```

**Parameter**

(none)

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

#### getUnique

Get the unique

**Declaration**
```
public Boolean getUnique()
```

**Parameter**

(none)

## AbstractInputField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field.md)"
    - This class is an abstract class.

### Methods

#### getLabel

Get the label

**Declaration**
```
public String getLabel()
```

**Parameter**

(none)

#### getNoLabel

Get the noLabel

**Declaration**
```
public Boolean getNoLabel()
```

**Parameter**

(none)

#### getRequired

Get the required

**Declaration**
```
public Boolean getRequired()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)