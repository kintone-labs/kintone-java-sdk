# Related Record

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## RelatedApp

### Methods

#### getApp

Get the app

**Declaration**
```
public String getApp()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    String app = relatedApp.getApp();

</pre>

</details>

#### getCode

Get the code

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    String code = relatedApp.getCode();

</pre>

</details>

## ReferenceTable

### Methods

#### getCondition

Get the condition

**Declaration**
```
public FieldMapping getCondition()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Condition</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    FieldMapping referenceTableCondition = referenceTable.getCondition();

</pre>

</details>

#### getFilterCond

Get the filterCond

**Declaration**
```
public String getFilterCond()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get FilterCond</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    String referenceTableFilterCond = referenceTable.getFilterCond();

</pre>

</details>

#### getRelatedApp

Get the relatedApp

**Declaration**
```
public RelatedApp getRelatedApp()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Related App</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    RelatedApp referenceTableRelatedApp = referenceTable.getRelatedApp();

</pre>

</details>

#### getSize

Get the size

**Declaration**
```
public Integer getSize()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Size</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    Integer referenceTableSize = referenceTable.getSize();

</pre>

</details>

#### getDisplayFields

Get the displayFields

**Declaration**
```
public ArrayList<String> getDisplayFields()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Display Fields</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    ArrayList<String> referenceTableDisplayFields = referenceTable.getDisplayFields();

</pre>

</details>

#### getSort

Get the sort

**Declaration**
```
public String getSort()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Sort</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedApp relatedApp = new RelatedApp("YOUR_APP_ID", "YOUR_APP_CODE");
    ArrayList<String> displayFields = new ArrayList<>();
    displayFields.add("YOUR_FIELD_CODE");
    String filterCondition = "YOUR_FILTER_CONDITION";
    FieldMapping condition = new FieldMapping("YOUR_FIELD_CODE", "YOUR_FIELD_CODE_RELATED");
    int size = 0;    // Input size of referenceTable

    ReferenceTable referenceTable = new ReferenceTable(condition, filterCondition, relatedApp, size, displayFields);
    String referenceTableSort = referenceTable.getSort();

</pre>

</details>

## RelatedRecordsField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field)"

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

    RelatedRecordsField recordsField = new RelatedRecordsField("YOUR_FIELD_CODE");
    String label = recordsField.getLabel();

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

    RelatedRecordsField recordsField = new RelatedRecordsField("YOUR_FIELD_CODE");
    Boolean noLabel = recordsField.getNoLabel();

</pre>

</details>

#### getReferenceTable

Get the referenceTable

**Declaration**
```
public ReferenceTable getReferenceTable()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get Reference Table</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    RelatedRecordsField recordsField = new RelatedRecordsField("YOUR_FIELD_CODE");
    ReferenceTable referenceTable = recordsField.getReferenceTable();
    
</pre>

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)