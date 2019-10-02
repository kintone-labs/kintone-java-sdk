# Comment Model

General comment structure of the record on kintone restAPI

## Comment

### Constructor

**Parameter**

(none)

### Methods

#### getId

Get the Comment ID.

**Declaration**
```
public Integer getId()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the Comment ID.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    // execute GET RECORD_COMMENTS  API
    int appID = 1;
    int recordID = 1;
    String order = "asc";
    int offsset = 1;
    int limit = 2;

    GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, offsset);

    ArrayList<Comment> resultComments = response.getComments();
    Comment comment = resultComments.get(0);
    int commentID = comment.getId();

</pre>

</details>

#### getText

Get the comment including the line feed codes.

**Declaration**
```
public String getText()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>get the comment including the line feed codes.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    // execute GET RECORD_COMMENTS  API
    int appID = 1;
    int recordID = 1;
    int order = "asc";
    int offsset = 1;
    Integer limit = 2;

    GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, limit);

    ArrayList<Comment> resultComments = response.getComments();
    Comment comment = resultComments.get(0);
    String commentText = comment.getText();

</pre>

</details>

#### getCreatedAt

Get the created date and time of the comment.

**Declaration**
```
public Date getCreatedAt()
```

**Parameter**

(none)

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

#### getCreator

Get an object including information of the comment creator.

**Declaration**
```
public Member getCreator()
```

**Parameter**

(none)

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

#### getMentions

Get an array including information of mentioned users.

**Declaration**
```
public ArrayList<CommentMention> getMentions()
```

**Parameter**

(none)

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

#### setText

> set the comment including the line feed codes.

**Declaration**
```
public void setText(String text)
```

**Parameter**

| Name|Description |
| --- | ---  |
| text | The comment including the line feed codes.

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

#### setMentions

Get an array including information of mentioned users.

**Declaration**
```
public void setMentions(ArrayList<CommentMention> mentions)
```

**Parameter**

| Name| Description |
| --- | ---  |
| mentions| An array including information of mentioned users.

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

#### getCode

Get the code of the mentioned user, group or organization.

**Declaration**
```
public String getCode()
```

**Parameter**

(none)

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

#### setCode

> set the comment including the line feed codes.

**Declaration**
```
public void setCode(String code)
```

**Parameter**

| Name| Description |
| ---  | --- |
| code | The code of the mentioned user, group or organization.

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

#### getType

Get the type of the mentioned user, group or organization.

**Declaration**
```
public String getType()
```

**Parameter**

(none)

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

    GetCommentsResponse response = kintoneRecordManager.getComments(appID, recordID, order, offsset, offsset);

    ArrayList<Comment> resultComments = response.getComments();
    Comment comment = resultComments.get(0);
    List<CommentMention> commentMentions = comment.getMentions();
    CommentMention mention = commentMentions.get(0);
    String mentionUserType= mention.getType();

</pre>

</details>

#### setType

Get an array including information of mentioned users.

**Declaration**
```
public void setType(String type)
```

**Parameter**

| Name| Description |
| ---  | --- |
| type | The type of the mentioned user, group or organization.

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

#### getComments

Get the comments List on a record.

**Declaration**
```
public ArrayList<Comment> getComments()
```

**Parameter**

(none)

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

#### getOlder

Get information of older comments.

**Declaration**
```
public Boolean getOlder()
```

**Parameter**

(none)

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

#### getNewer

Get information of newer comments.

**Declaration**
```
public Boolean getNewer()
```

**Parameter**

(none)

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

#### getId

Get the ID of comment which have just created.

**Declaration**
```
public Integer getId()
```

**Parameter**

(none)

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