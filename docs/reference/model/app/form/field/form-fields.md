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

    Integer appID = 0;  // Input your app id
    Integer revision = 0; // Latest_revision_of_the_settings
    HashMap<String, Field> properties = new HashMap<>();
    properties.put("YOUR_FIELD_CODE", new RadioButtonField("YOUR_FIELD_CODE"));
    FormFields formFields = new FormFields(appID, properties, revision);

    Integer app = formFields.getApp();

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

    Integer appID = 0;  // Input your app id
    Integer revision = 0; // Latest_revision_of_the_settings
    HashMap<String, Field> properties = new HashMap<>();
    properties.put("YOUR_FIELD_CODE", new RadioButtonField("YOUR_FIELD_CODE"));
    FormFields formFields = new FormFields(appID, properties, revision);

    Integer revisionFields = formFields.getRevision();

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

    Integer appID = 0;  // Input your app id
    Integer revision = 0; // Latest_revision_of_the_settings
    HashMap<String, Field> properties = new HashMap<>();
    properties.put("YOUR_FIELD_CODE", new RadioButtonField("YOUR_FIELD_CODE"));
    FormFields formFields = new FormFields(appID, properties, revision);

    HashMap<String, Field> propertiesFields = formFields.getProperties();
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

    FieldGroup fieldGroup = new FieldGroup("YOUR_FIELD_CODE");
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

    FieldGroup fieldGroup = new FieldGroup("YOUR_FIELD_CODE");
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

    FieldGroup fieldGroup = new FieldGroup("YOUR_FIELD_CODE");
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

    FieldMapping fieldMapping = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_RELATED_CODE");
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

    FieldMapping fieldMapping = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_RELATED_CODE");
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

    SubTableField subTableField = new SubTableField("YOUR_FIELD_CODE");
    HashMap<String, AbstractInputField> fields = subTableField.getFields();
    
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