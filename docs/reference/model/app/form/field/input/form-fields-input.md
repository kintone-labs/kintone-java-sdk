# Input

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## AttachmentField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getThumbnailSize()

Get the thumbnailSize

**Parameter**

(none)

**Return**

Integer

## CalculatedField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getExpression()

Get the expression

**Parameter**

(none)

**Return**

String

#### getHideExpression()

Get the hideExpression

**Parameter**

(none)

**Return**

Boolean

#### getDisplayScale()

Get the displayScale

**Parameter**

(none)

**Return**

Integer

#### getUnit()

Get the unit

**Parameter**

(none)

**Return**

String

#### getUnitPosition()

Get the unitPosition

**Parameter**

(none)

**Return**

UnitPosition

#### getFormat()

Get the format

**Parameter**

(none)

**Return**

NumberFormat

## LinkField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

#### getUnique()

Get the unique

**Parameter**

(none)

**Return**

Boolean

#### getMaxLength()

Get the maxLength

**Parameter**

(none)

**Return**

Integer

#### getMinLength()

Get the minLength

**Parameter**

(none)

**Return**

Integer

#### getProtocol()

Get the protocol

**Parameter**

(none)

**Return**

LinkProtocol

## MultiLineTextField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

## NumberField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDisplayScale()

Get the displayScale

**Parameter**

(none)

**Return**

Integer

#### getUnit()

Get the unit

**Parameter**

(none)

**Return**

String

#### getUnitPosition()

Get the unitPosition

**Parameter**

(none)

**Return**

UnitPosition

#### getDigit()

Get the digit

**Parameter**

(none)

**Return**

Boolean

#### getMaxValue()

Get the maxValue

**Parameter**

(none)

**Return**

Integer

#### getMinValue()

Get the minValue

**Parameter**

(none)

**Return**

Integer

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

#### getUnique()

Get the unique

**Parameter**

(none)

**Return**

Boolean

## RichTextField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

## SingleLineTextField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getMinLength()

Get the minLength

**Parameter**

(none)

**Return**

Integer

#### getMaxLength()

Get the maxLength

**Parameter**

(none)

**Return**

Integer

#### getExpression()

Get the expression

**Parameter**

(none)

**Return**

String

#### getHideExpression()

Get the hideExpression

**Parameter**

(none)

**Return**

Boolean

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

#### getUnique()

Get the unique

**Parameter**

(none)

**Return**

Boolean

## AbstractInputField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field)"
    - This class is an abstract class.

### Methods

#### getLabel()

Get the label

**Parameter**

(none)

**Return**

String

#### getNoLabel()

Get the noLabel

**Parameter**

(none)

**Return**

Boolean

#### getRequired()

Get the required

**Parameter**

(none)

**Return**

Boolean

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)