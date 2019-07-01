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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer thumbnailSize = attachmentField.getThumbnailSize();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String expression = calculatedField.getExpression();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean hideExpression = calculatedField.getHideExpression();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer displayScale = calculatedField.getDisplayScale();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String unit = calculatedField.getUnit();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
UnitPosition unitPosition = calculatedField.getUnitPosition();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
NumberFormat format = calculatedField.getFormat();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String defaultValue = linkField.getDefaultValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean unique = linkField.getUnique();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer maxLength = linkField.getMaxLength();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer minLength = linkField.getMinLength();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
LinkProtocol protocol = linkField.getProtocol();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String defaultValue = multiLineTextField.getDefaultValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer displayScale = numberField.getDisplayScale();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String unit = numberField.getUnit();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
UnitPosition unitPosition = numberField.getUnitPosition();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean digit = numberField.getDigit();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer maxValue = numberField.getMaxValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer minValue = numberField.getMinValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String defaultValue = numberField.getDefaultValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean unique = numberField.getUnique();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String defaultValue = richTextField.getDefaultValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer minLength = singleLineTextField.getMinLength();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer maxLength = singleLineTextField.getMaxLength();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String expression = singleLineTextField.getExpression();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean hideExpression = singleLineTextField.getHideExpression();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String defaultValue = singleLineTextField.getDefaultValue();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Boolean unique = singleLineTextField.getUnique();
</pre>

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