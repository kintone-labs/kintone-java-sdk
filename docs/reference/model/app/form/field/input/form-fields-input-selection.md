# Selection

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## CheckboxField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign()

Get the align

**Parameter**

(none)

**Return**

AlignLayout

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

List<String\>

## DropDownField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

## MultipleSelectField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

List<String\>

## OptionData

### Methods

#### getIndex()

Get the index

**Parameter**

(none)

**Return**

Integer

#### getLabel()

Get the label

**Parameter**

(none)

**Return**

String

## RadioButtonField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign()

Get the align

**Parameter**

(none)

**Return**

AlignLayout

#### getDefaultValue()

Get the defaultValue

**Parameter**

(none)

**Return**

String

## AbstractSelectionField

!!! warning
    - extend the abstract class "[AbstractInputField](./form-fields-input/#abstractinputfield)"
    - This class is an abstract class.

### Methods

#### getOptions()

Get the options

**Parameter**

(none)

**Return**

Map<String, [OptionData](#optiondata)\>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)