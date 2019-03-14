# Related Record

Get a list of fields and their settings.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## RelatedApp

### Methods

#### getApp()

Get the app

**Parameter**

(none)

**Return**

 String

**Sample code**

<details class="tab-container" open>
<Summary>get App</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    String app = relatedApp.getApp();

</pre>

</details>

#### getCode()

Get the code

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    String app = relatedApp.getCode();

</pre>

</details>

## ReferenceTable

### Methods

#### getCondition()

Get the condition

**Parameter**

(none)

**Return**

[FieldMapping](../form-fields/#fieldmapping)

**Sample code**

<details class="tab-container" open>
<Summary>get Condition</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    FieldMapping condition = referenceTable.getCondition();

</pre>

</details>

#### getFilterCond()

Get the filterCond

**Parameter**

(none)

**Return**

 String

**Sample code**

<details class="tab-container" open>
<Summary>get FilterCond</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    String filterCond = referenceTable.getFilterCond();

</pre>

</details>

#### getRelatedApp()

Get the relatedApp

**Parameter**

(none)

**Return**

[RelatedApp](#relatedapp)

**Sample code**

<details class="tab-container" open>
<Summary>get Related App</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    RelatedApp relatedApp = referenceTable.getRelatedApp();

</pre>

</details>

#### getSize()

Get the size

**Parameter**

(none)

**Return**

 Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Size</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    Integer size = referenceTable.getSize();

</pre>

</details>

#### getDisplayFields()

Get the displayFields

**Parameter**

(none)

**Return**

 List<String\>

**Sample code**

<details class="tab-container" open>
<Summary>get Display Fields</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    ArrayList<String> displayFields = referenceTable.getDisplayFields();

</pre>

</details>

#### getSort()

Get the sort

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Sort</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    String sort = referenceTable.getSort();

</pre>

</details>

## RelatedRecordsField

!!! warning
    - extend the abstract class  "[Field](../form-fields/#field)"

### Methods

#### getLabel()

Get the label

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Label</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    String label = relatedRecordsField.getLabel();

</pre>

</details>

#### getNoLabel()

Get the noLabel

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get NoLabel</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    Boolean noLabel = relatedRecordsField.getNoLabel();

</pre>

</details>

#### getReferenceTable()

Get the referenceTable

**Parameter**

(none)

**Return**

[ReferenceTable](#referencetable)

**Sample code**

<details class="tab-container" open>
<Summary>get Reference Table</Summary>

<strong class="tab-name">Source code</strong>

</pre>

    ReferenceTable referenceTable = relatedRecordsField.getReferenceTable();
    
</pre>

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)