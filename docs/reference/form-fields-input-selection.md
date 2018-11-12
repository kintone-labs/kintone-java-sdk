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

** Source code **

```java
AlignLayout align = checkboxField.getAlign();
```

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

** Source code **

```java
List<String> defaultValue = checkboxField.getDefaultValue();
```

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

** Source code **

```java
String defaultValue = dropDownField.getDefaultValue();
```

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

** Source code **

```java
List<String> defaultValue = multipleSelectField.getDefaultValue();
```

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

** Source code **

```java
Integer index = optionData.getIndex();
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
String label = optionData.getLabel();
```

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

** Source code **

```java
AlignLayout align = radioButtonField.getAlign();
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
String defaultValue = radioButtonField.getDefaultValue();
```

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