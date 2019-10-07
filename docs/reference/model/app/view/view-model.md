# ViewModel

General information of the member(user/group/organization) on the kintone application

## Methods

### getBuiltinType

Get the code of the user/group/organization

**Declaration**
```
public BuiltinType getBuiltinType()
```

**Parameter**

(none)

### setCode

The [BuiltinType](#BuiltinType) of the built-in View.

**Declaration**
```
public void setSort(String sort)
```

**Parameter**

None

### getDate

The field code set for the Date Field. Responded for Calendar Views.

**Declaration**
```
public String getDate()
```

**Parameter**

None

### getFields

The list of field codes for the fields displayed in the View.
> Responded for List Views.

**Declaration**
```
public ArrayList<String> getFields()
```

**Parameter**

None

### getFilterCond

The filter condition as a query.


**Declaration**
```
public String getFilterCond()
```

**Parameter**

None

### getHtml

The HTML code set for the View. 
> Responded for Custom Views.

**Declaration**
```
public String getHtml()
```

**Parameter**

None

### getId

The View ID.

**Declaration**
```
public Integer getId()
```

**Parameter**

None

### getIndex

The display order (ascending) of the View, when listed with other views.

**Declaration**
```
public Integer getIndex()
```

**Parameter**

None

### getName

The name of the View.

**Declaration**
```
public String getName()
```

**Parameter**

None

### getPager

The pagination settings. 
Responded for Custom Views.

**Declaration**
```
public Boolean getPager()
```

**Parameter**

None

### getSort

The sort order as a query.

**Declaration**
```
public String getSort()
```

**Parameter**

None

### getTitle

The field code set for the Title Field. 
Responded for Calendar Views.

**Declaration**
```
public String getTitle()
```

**Parameter**

None

### getType

The type of View in [Type](#type)

**Declaration**
```
public ViewType getType()
```

**Parameter**

None

## Enum

### Type

| Name | Type | Value | Description |
| --- | --- | --- | --- |
| LIST | String | LIST | List View
| CALENDAR | String | CALENDAR | Custom View
| CUSTOM | String | CUSTOM | Custom View

### BuiltinType

| Name | Type | Value | Description |
| --- | --- | --- | --- |
| ASSIGNEE | String | ASSIGNEE | The "Assigned to me" View.(This list is automatically created if the Process Management settings have been enabled in the app.)
