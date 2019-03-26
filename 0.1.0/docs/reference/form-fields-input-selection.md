# Selection

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## CheckboxField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign()

> Get the align

**Parameter**

(none)

**Return**

AlignLayout

**Sample code**

<details class="tab-container" open>
<Summary>get Align</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
AlignLayout align = checkboxField.getAlign();
</pre>

</details>

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

List<String\>

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<String> defaultValue = checkboxField.getDefaultValue();
</pre>

</details>

## DropDownField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

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
String defaultValue = dropDownField.getDefaultValue();
</pre>

</details>

## MultipleSelectField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getDefaultValue()

> Get the defaultValue

**Parameter**

(none)

**Return**

List<String\>

**Sample code**

<details class="tab-container" open>
<Summary>get Default Value</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
List<String> defaultValue = multipleSelectField.getDefaultValue();
</pre>

</details>

## OptionData

### Methods

#### getIndex()

> Get the index

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Index</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer index = optionData.getIndex();
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
String label = optionData.getLabel();
</pre>

</details>

## RadioButtonField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign()

> Get the align

**Parameter**

(none)

**Return**

AlignLayout

**Sample code**

<details class="tab-container" open>
<Summary>get Align</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
AlignLayout align = radioButtonField.getAlign();
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
String defaultValue = radioButtonField.getDefaultValue();
</pre>

</details>

## AbstractSelectionField

!!! warning
    - extend the abstract class "[AbstractInputField](./form-fields-input/#abstractinputfield)"
    - This class is an abstract class.

### Methods

#### getOptions()

> Get the options

**Parameter**

(none)

**Return**

Map<String, [OptionData](#optiondata)\>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`