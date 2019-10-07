# Selection

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## CheckboxField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign

Get the align

**Declaration**
```
public AlignLayout getAlign()
```

**Parameter**

(none)

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public ArrayList<String> getDefaultValue()
```

**Parameter**

(none)

## DropDownField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public String getDefaultValue()
```

**Parameter**

(none)

## MultipleSelectField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getDefaultValue

Get the defaultValue

**Declaration**
```
public ArrayList<String> getDefaultValue()
```

**Parameter**

(none)

## OptionData

### Methods

#### getIndex

Get the index

**Declaration**
```
public Integer getIndex()
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

## RadioButtonField

!!! warning
    - extend the abstract class  "[AbstractSelectionField](#abstractselectionfield)"

### Methods

#### getAlign

Get the align

**Declaration**
```
public AlignLayout getAlign()
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

## AbstractSelectionField

!!! warning
    - extend the abstract class "[AbstractInputField](./form-fields-input/#abstractinputfield.md)"
    - This class is an abstract class.

### Methods

#### getOptions

Get the options

**Declaration**
```
public HashMap<String, OptionData> getOptions()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)