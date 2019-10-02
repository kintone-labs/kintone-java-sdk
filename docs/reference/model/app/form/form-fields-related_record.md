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

    String app = relatedApp.getCode();

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

    FieldMapping condition = referenceTable.getCondition();

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

    String filterCond = referenceTable.getFilterCond();

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

    RelatedApp relatedApp = referenceTable.getRelatedApp();

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

    Integer size = referenceTable.getSize();

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

    ArrayList<String> displayFields = referenceTable.getDisplayFields();

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

    String sort = referenceTable.getSort();

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

    String label = relatedRecordsField.getLabel();

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

    Boolean noLabel = relatedRecordsField.getNoLabel();

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

    ReferenceTable referenceTable = relatedRecordsField.getReferenceTable();
    
</pre>

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)