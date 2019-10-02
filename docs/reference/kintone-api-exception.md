# kintoneAPIException

Handle error responses from kintone Rest API

## Methods

### getHttpErrorCode()

**Declaration**
```
public int getHttpErrorCode() 
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get http error code</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	Integer appID = 0;  // Input your app id
	// Init App Module
	try {
		App kintoneApp = new App(kintoneConnection);
		kintoneApp.getApp(appID);
	} catch (KintoneAPIException e) {
		System.out.println("HTTP ERROR CODE: " + e.getHttpErrorCode());
	}

</pre>

</details>

### getErrorResponse()

**Declaration**
```
public ErrorResponse getErrorResponse()
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Get apps with error response</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

	String username = "YOUR_USERNAME";
	String password = "YOUR_PASSWORD";

	Auth kintoneAuth = new Auth();
	kintoneAuth.setPasswordAuth(username, password);

	String kintoneDomain = "YOUR_DOMAIN.COM";
	Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

	Integer appID = 0;  // Input your app id
	// Init App Module
	try {
		App kintoneApp = new App(kintoneConnection);
		kintoneApp.getApp(appID);
	} catch (KintoneAPIException e) {
		System.out.println("ERROR RESPONSE - Id: " + e.getErrorResponse().getId());
		System.out.println("ERROR RESPONSE - Code: " + e.getErrorResponse().getCode());
		System.out.println("ERROR RESPONSE - Message: " + e.getErrorResponse().getMessage());
		System.out.println("ERROR RESPONSE - Errors: " + e.getErrorResponse().getErrors());
	}

</pre>

</details>
