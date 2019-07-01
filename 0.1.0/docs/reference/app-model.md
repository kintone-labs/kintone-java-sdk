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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
AppModel appModel = new AppModel();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer addId = appModel.getAppId();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String code = appModel.getCode();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String name = appModel.getName();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String description = appModel.getDescription();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer spaceId = appModel.getSpaceId();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Integer threadId = appModel.getThreadId();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Member member = appModel.getCreator();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Member member = appModel.getModifier();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Date date = appModel.getCreatedAt();
</pre>

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

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
Date date = appModel.getModifiedAt();
</pre>

</details>

## Reference

- [Get App](https://developer.kintone.io/hc/en-us/articles/212494888)`on developer network`
- [Get Apps](https://developer.kintone.io/hc/en-us/articles/115005336727)`on developer network`
- [Get Form fields](https://developer.kintone.io/hc/en-us/articles/115005509288)`on developer network`
- [Get Form Layout](https://developer.kintone.io/hc/en-us/articles/115005509068)`on developer network`