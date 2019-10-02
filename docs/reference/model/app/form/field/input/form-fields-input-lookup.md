# Lookup

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## LookupField

!!! warning
    - extend the abstract class  "[AbstractInputField](../form-fields-input/#abstractinputfield.md)"

### Methods

#### getLookup

Get the lookup

**Declaration**
```
public LookupItem getLookup()
```

**Parameter**

(none)

## LookupItem

### Methods

#### getFieldMapping

Get the fieldMapping

**Declaration**
```
public ArrayList<FieldMapping> getFieldMapping()
```

**Parameter**

(none)

#### getFilterCond

Get the filterCond

**Declaration**
```
public String getFilterCond()
```

**Parameter**

(none)

#### getLookupPickerFields

Get the lookupPickerFields

**Declaration**
```
public ArrayList<String> getLookupPickerFields()
```

**Parameter**

(none)

#### getRelatedApp

Get the relatedApp

**Declaration**
```
public RelatedApp getRelatedApp()
```

**Parameter**

(none)

#### getRelatedKeyField

Get the relatedKeyField

**Declaration**
```
public String getRelatedKeyField()
```

**Parameter**

(none)

#### getSort

Get the sort

**Declaration**
```
public String getSort()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)