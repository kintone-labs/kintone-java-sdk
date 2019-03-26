# Comment Model

General comment structure of the record on kintone restAPI

## Comment

### Constructor

**Parameter**

(none)

### Methods

#### getId()

> get the Comment ID.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the Comment ID.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, offsset);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
Integer commentID = comment.getId();
</pre>

</details>

#### getText()

> get the comment including the line feed codes.

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get the comment including the line feed codes.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
String commentText = comment.getText();
</pre>

</details>

#### getCreatedAt()

> get the created date and time of the comment.

**Parameter**

(none)

**Return**

Date

**Sample code**

<details class="tab-container" open>
<Summary>get the created date and time of the comment.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
Date commentCreatedAt = comment.getCreatedAt();
</pre>

</details>

#### getCreator()

> get an object including information of the comment creator.

**Parameter**

(none)

**Return**

[Member](../member/#member)

**Sample code**

<details class="tab-container" open>
<Summary>get an object including information of the comment creator.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
Member commentCreator = comment.getCreator();
</pre>

</details>

#### getMentions()

> get an array including information of mentioned users.

**Parameter**

(none)

**Return**

List<[CommentMention](#commentmention)\>

**Sample code**

<details class="tab-container" open>
<Summary>get an array including information of mentioned users.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
List<CommentMention> commentMentions = comment.getMentions();
</pre>

</details>

## CommentContent

### Constructor

**Parameter**

(none)

### Methods

#### setText(String text)

> set the comment including the line feed codes.

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| text | String  | The comment including the line feed codes.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>set the comment including the line feed codes.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);
AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);
</pre>

</details>

#### setMentions(List<[CommentMention](#commentmention)\> mentions)

> get an array including information of mentioned users.

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| mentions | List<[CommentMention](#commentmention)\>  | An array including information of mentioned users.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get an array including information of mentioned users.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);
AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);
</pre>

</details>

## CommentMention

### Constructor

**Parameter**

(none)

### Methods

#### getCode()

> get the code of the mentioned user, group or organization.

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get the code of the mentioned user, group or organization.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
List<CommentMention> commentMentions = comment.getMentions();
CommentMention mention = commentMentions.get(0);
String mentionUserCode = mention.getCode();
</pre>

</details>

#### setCode(String code)

> set the comment including the line feed codes.

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| code | String  | The code of the mentioned user, group or organization.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>set the comment including the line feed codes.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);
AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);
</pre>

</details>

#### getType()

> get the type of the mentioned user, group or organization.

**Parameter**

(none)

**Return**

String

**Sample code**

<details class="tab-container" open>
<Summary>get the type of the mentioned user, group or organization.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;
v
GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, offsset);

ArrayList<Comment> resultComments = response.getComments();
Comment comment = resultComments.get(0);
List<CommentMention> commentMentions = comment.getMentions();
CommentMention mention = commentMentions.get(0);
String mentionUserType= mention.getType();
</pre>

</details>

#### setType(String type)

> get an array including information of mentioned users.

**Parameter**

| Name| type| Description |
| --- | ---  | --- |
| type | String  | The type of the mentioned user, group or organization.

**Return**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get an array including information of mentioned users.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);
AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);
</pre>

</details>

## GetCommentsResponse

### Constructor

**Parameter**

(none)

### Methods

#### getComments()

> get the comments List on a record.

**Parameter**

(none)

**Return**

ArrayList<[Comment](#comment)\>

**Sample code**

<details class="tab-container" open>
<Summary>get the comments List on a record.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

ArrayList<Comment> resultComments = response.getComments();
</pre>

</details>

#### getOlder()

> get information of older comments.

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get information of older comments.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

 Boolean resultOlderFlg = response.getOlder();
</pre>

</details>

#### getNewer()

> get information of newer comments.

**Parameter**

(none)

**Return**

Boolean

**Sample code**

<details class="tab-container" open>
<Summary>get information of newer comments.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute GET RECORD_COMMENTS  API
Integer appID = 1;
Integer recordID = 1;
String order = "asc";
Integer offsset = 1;
Integer limit = 2;

GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

Boolean resultNewerFlg = response.getNewer();
</pre>

</details>

## AddCommentResponse

### Constructor

**Parameter**

(none)

### Methods

#### getId()

> get the ID of comment which have just created.

**Parameter**

(none)

**Return**

Integer

**Sample code**

<details class="tab-container" open>
<Summary>get the ID of comment which have just created.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
// execute ADD RECORD_COMMENT  API
Integer app = 1;
Integer record = 1;
CommentContent comment = new CommentContent();
ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
CommentMention mention = new CommentMention();
mention.setCode("sample_user");
mention.setType("USER");
mentionList.add(mention);
comment.setText("test comment");
comment.setMentions(mentionList);
AddCommentResponse response = kintoneRecordManager.addComment(app, record, comment);

Integer resultId = response.getId();
</pre>

</details>