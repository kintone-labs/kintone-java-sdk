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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);

        int commentId = comment.getId();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        String commentText = comment.getText();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        Date commentCreatedAt = comment.getCreatedAt();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        Member commentCreator = comment.getCreator();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        List<CommentMention> commentMentions = comment.getMentions();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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
| --- | --- |
| text | The comment including the line feed codes.

**Sample code**

<details class="tab-container" open>
<Summary>set the comment including the line feed codes.</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        CommentContent commentContent = new CommentContent();
        commentContent.setText("TEXT_OF_COMMENT");
        AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        CommentContent commentContent = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<>();
        CommentMention mention = new CommentMention();

        mention.setCode("YOUR_USER_MENTION_CODE");
        mention.setType("USER");
        mentionList.add(mention);
        commentContent.setText("TEXT_OF_COMMENT");
        commentContent.setMentions(mentionList);

        AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;

    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);

        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        List<CommentMention> commentMentions = comment.getMentions();
        CommentMention mention = commentMentions.get(0);
        String mentionUserCode = mention.getCode();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        CommentContent commentContent = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<>();
        CommentMention mention = new CommentMention();

        mention.setCode("YOUR_USER_MENTION_CODE");
        mention.setType("USER");
        mentionList.add(mention);
        commentContent.setText("TEXT_OF_COMMENT");
        commentContent.setMentions(mentionList);
        AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    String order = "asc";
    int offset = 0;
    int limit = 10;

    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
        Comment comment = resultComments.get(0);
        List<CommentMention> commentMentions = comment.getMentions();
        CommentMention mention = commentMentions.get(0);
        String mentionUserType = mention.getType();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        CommentContent commentContent = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<>();
        CommentMention mention = new CommentMention();
        mention.setCode("YOUR_USER_MENTION_CODE");
        mention.setType("USER");
        mentionList.add(mention);
        commentContent.setText("TEXT_OF_COMMENT");
        commentContent.setMentions(mentionList);
        AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        ArrayList<Comment> resultComments = response.getComments();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        Boolean resultOlderFlg = response.getOlder();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        GetCommentsResponse response = kintoneRecord.getComments(appID, recordID, order, offset, limit);
        Boolean resultNewerFlg = response.getNewer();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }

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

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    // Init Connection without "guest space ID"
    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Init Record Module
    Record kintoneRecord = new Record(kintoneConnection);
    Integer appID = 0;  // Input your app id
    Integer recordID = 0;   // Input your record id
    try {
        CommentContent commentContent = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<>();
        CommentMention mention = new CommentMention();

        mention.setCode("YOUR_USER_MENTION_CODE");
        mention.setType("USER");
        mentionList.add(mention);
        commentContent.setText("TEXT_OF_COMMENT");
        commentContent.setMentions(mentionList);
        AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);
        int resultId = response.getId();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
    
</pre>

</details>