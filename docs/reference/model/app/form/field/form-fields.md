# Form Fields

Get a list of fields and their settings.

- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## FormFields

### Methods

#### getApp

Get the app

**Declaration**
```
public Integer getApp()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Integer app = formfields.getApp();

</pre>

</details>

#### getRevision

Get the revision

**Declaration**
```
public Integer getRevision()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Revision</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    int revision = formfields.getRevision();

</pre>

</details>

#### getProperties

Get the properties

**Declaration**
```
public HashMap<String, Field> getProperties()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Properties</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Map<String, Field> properties = formfields.getProperties();

</pre>

</details>

## FieldGroup

!!! warning
    - extend the abstract class  "[Field](#field)"

### Methods

#### getLabel

Get the label

**Declaration**
```
public String getLabel()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Label</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String label = fieldGroup.getLabel();

</pre>

</details>

#### getNoLabel

Get the noLabel

**Declaration**
```
public Boolean getNoLabel()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get NoLabel</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Boolean noLabel = fieldGroup.getNoLabel();

</pre>

</details>

#### getOpenGroup

Get the openGroup

**Declaration**
```
public Boolean getOpenGroup()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get OpenGroup</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Boolean openGroup = fieldGroup.getOpenGroup();

</pre>

</details>

## FieldMapping

### Methods

#### getField

Get the field

**Declaration**
```
public String getField()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Field</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String field = fieldMapping.getField();

</pre>

</details>

#### getRelatedFields

Get the relatedFields

**Declaration**
```
public String getRelatedFields()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Related Fields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String relatedFields = fieldMapping.getRelatedFields();

</pre>

</details>

## SubTableField

!!! warning
    - extend the abstract class  "[Field](#field)"

### Methods

</details>

#### getFields

Get the fields

**Declaration**
```
public HashMap<String, AbstractInputField> getFields()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Fields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Map<String, AbstractInputField> fields = subTableField.getFields();
    
</pre>

</details>

## Field

!!! warning
    - This class is an abstract class.

### Methods

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

**Return**

String

#### getType

Get the type

**Declaration**
```
public FieldType getType()
```

**Parameter**

(none)

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)