# AppModel

Gets the basic information about the app.

>
- Permissions to view the App is needed.
- API Tokens cannot be used with this API.

## Constructor

### **Parameter**

(none)

### **Sample code**

<details class="tab-container" open>
<Summary>Init App Model</Summary>

** Source code **

```java
AppModel appModel = new AppModel();
```

</details>

## Methods

### getAppId()

> Get the appId

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get App Id</Summary>

** Source code **

```java
Integer addId = appModel.getAppId();
```

</details>

### getCode()

> Get the code

**Parameter **

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Code</Summary>

** Source code **

```java
String code = appModel.getCode();
```

</details>

### getName()

> Get the name

**Parameter **

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Name</Summary>

** Source code **

```java
String name = appModel.getName();
```

</details>

### getDescription()

> Get the description

**Parameter **

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get Description</Summary>

** Source code **

```java
String description = appModel.getDescription();
```

</details>

### getSpaceId()

> Get the spaceId

**Parameter **

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Space Id</Summary>

** Source code **

```java
Integer spaceId = appModel.getSpaceId();
```

</details>

### getThreadId()

> Get the threadId

**Parameter **

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get Thread Id</Summary>

** Source code **

```java
Integer threadId = appModel.getThreadId();
```

</details>

### getCreator()

> Get the creator

**Parameter **

(none)

**Return**

[Member](../member)

**Sample code**

<details class="tab-container" open>
<Summary>get Creator</Summary>

** Source code **

```java
Member member = appModel.getCreator();
```

</details>

### getModifier()

> Get the modifier

**Parameter **

(none)

**Return**

[Member](../member)

**Sample code**

<details class="tab-container" open>
<Summary>get Modifier</Summary>

** Source code **

```java
Member member = appModel.getModifier();
```

</details>

### getCreatedAt()

> Get the createdAt

**Parameter **

(none)

**Return**

Date

**Sample code**

<details class="tab-container" open>
<Summary>get Create Date</Summary>

** Source code **

```java
Date date = appModel.getCreatedAt();
```

</details>

### getModifiedAt()

> Get the modifiedAt

**Parameter **

(none)

**Return**

Date

**Sample code**

<details class="tab-container" open>
<Summary>get Modified Date</Summary>

** Source code **

```java
Date date = appModel.getModifiedAt();
```

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`