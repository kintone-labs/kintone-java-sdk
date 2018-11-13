# Input

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## AttachmentField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getThumbnailSize()

> Get the thumbnailSize

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Thumbnail Size</Summary>

** Source code **

```java
Integer thumbnailSize = attachmentField.getThumbnailSize();
```

</details>

## CalculatedField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getExpression()

> Get the expression

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Expression</Summary>

** Source code **

```java
String expression = calculatedField.getExpression();
```

</details>

#### getHideExpression()

> Get the hideExpression

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Hide Expression</Summary>

** Source code **

```java
Boolean hideExpression = calculatedField.getHideExpression();
```

</details>

#### getDisplayScale()

> Get the displayScale

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Display Scale</Summary>

** Source code **

```java
Integer displayScale = calculatedField.getDisplayScale();
```

</details>

#### getUnit()

> Get the unit

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Unit</Summary>

** Source code **

```java
String unit = calculatedField.getUnit();
```

</details>

#### getUnitPosition()

> Get the unitPosition

**Parameter**

(none)

**Return**

UnitPosition

**Sample code**

<details class="tab-container" open>
<Summary>get Unit Position</Summary>

** Source code **

```java
UnitPosition unitPosition = calculatedField.getUnitPosition();
```

</details>

#### getFormat()

> Get the format

**Parameter**

(none)

**Return**

NumberFormat

**Sample code**

<details class="tab-container" open>
<Summary>get Format</Summary>

** Source code **

```java
NumberFormat format = calculatedField.getFormat();
```

</details>

## LinkField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

** Source code **

```java
String defaultValue = linkField.getDefaultValue();
```

</details>

#### getUnique()

> Get the unique

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Unique</Summary>

** Source code **

```java
Boolean unique = linkField.getUnique();
```

</details>

#### getMaxLength()

> Get the maxLength

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Max Length</Summary>

** Source code **

```java
Integer maxLength = linkField.getMaxLength();
```

</details>

#### getMinLength()

> Get the minLength

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Min Length</Summary>

** Source code **

```java
Integer minLength = linkField.getMinLength();
```

</details>

#### getProtocol()

> Get the protocol

**Parameter**

(none)

**Return**

LinkProtocol

**Sample code**

<details class="tab-container" open>
<Summary>get Protocol</Summary>

** Source code **

```java
LinkProtocol protocol = linkField.getProtocol();
```

</details>

## MultiLineTextField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

** Source code **

```java
String defaultValue = multiLineTextField.getDefaultValue();
```

</details>

## NumberField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDisplayScale()

> Get the displayScale

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Display Scale</Summary>

** Source code **

```java
Integer displayScale = numberField.getDisplayScale();
```

</details>

#### getUnit()

> Get the unit

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Unit</Summary>

** Source code **

```java
String unit = numberField.getUnit();
```

</details>

#### getUnitPosition()

> Get the unitPosition

**Parameter**

(none)

**Return**

UnitPosition

**Sample code**

<details class="tab-container" open>
<Summary>get Unit Position</Summary>

** Source code **

```java
UnitPosition unitPosition = numberField.getUnitPosition();
```

</details>

#### getDigit()

> Get the digit

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Digit</Summary>

** Source code **

```java
Boolean digit = numberField.getDigit();
```

</details>

#### getMaxValue()

> Get the maxValue

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Max Value</Summary>

** Source code **

```java
Integer maxValue = numberField.getMaxValue();
```

</details>

#### getMinValue()

> Get the minValue

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Min Value</Summary>

** Source code **

```java
Integer minValue = numberField.getMinValue();
```

</details>

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

** Source code **

```java
String defaultValue = numberField.getDefaultValue();
```

</details>

#### getUnique()

> Get the unique

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Unique</Summary>

** Source code **

```java
Boolean unique = numberField.getUnique();
```

</details>

## RichTextField

!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

** Source code **

```java
String defaultValue = richTextField.getDefaultValue();
```

</details>

## SingleLineTextField


!!! warning
    - extend the abstract class  "[AbstractInputField](#abstractinputfield)"

### Methods

#### getMinLength()

> Get the minLength

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Min Length</Summary>

** Source code **

```java
Integer minLength = singleLineTextField.getMinLength();
```

</details>

#### getMaxLength()

> Get the maxLength

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Max Length</Summary>

** Source code **

```java
Integer maxLength = singleLineTextField.getMaxLength();
```

</details>

#### getExpression()

> Get the expression

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Expression</Summary>

** Source code **

```java
String expression = singleLineTextField.getExpression();
```

</details>

#### getHideExpression()

> Get the hideExpression

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Hide Expression</Summary>

** Source code **

```java
Boolean hideExpression = singleLineTextField.getHideExpression();
```

</details>

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

** Source code **

```java
String defaultValue = singleLineTextField.getDefaultValue();
```

</details>

#### getUnique()

> Get the unique

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get Unique</Summary>

** Source code **

```java
Boolean unique = singleLineTextField.getUnique();
```

</details>

## AbstractInputField

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

#### getNoLabel()

> Get the noLabel

**Parameter**

(none)

**Return**

Boolean

#### getRequired()

> Get the required

**Parameter**

(none)

**Return**

Boolean

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`