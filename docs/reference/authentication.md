# Authentication

Authentication module will be used by [Connection](../connection).
This module allows we authenticate with kintone app by password authenticator or API token authenticator. This module also supports basic authenticator.

!!! warning

    - If both the Token and Password Authentication are specified, the Token Authentication will be ignored and the Password authentication will be used.

## Constructor

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Init authentication module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    Auth kintoneAuth = new Auth();

</pre>

</details>

## Methods

### setPasswordAuth(username, password)

> Set password authentication for Authentication module.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| username | String | Yes | The username is able to authenticate on kintone app.
| password | String | Yes | The password is able to authenticate on kintone app.

**Return**

[Auth](../authentication)

**Sample code**

<details class="tab-container" open>
<Summary>Set password authentication</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

</pre>

</details>


### setApiToken(apiTokenString)

> Set Api Token for Authentication module.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| apiToken | String | Yes | The API token is able to authenticate on kintone app.

**Return**

[Auth](../authentication)


<details class="tab-container" open>
<Summary>Set APIToken authentication</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String apiToken = "YOUR_API_TOKEN";
    Auth kintoneAuth = new Auth();
    kintoneAuth.setApiToken(apiToken);

</pre>

</details>


### setBasicAuth(username, password)

> Set Basic authentication for Authentication module.

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| username | String | Yes | The username is able to authenticate on kintone app.
| password | String | Yes | The password is able to authenticate on kintone app.

**Return**

[Auth](../authentication)

**Sample code**

<details class="tab-container" open>
<Summary>Set basic authentication</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    Auth kintoneAuth = new Auth();
    kintoneAuth.setBasicAuth(username, password);

</pre>

</details>


### createHeaderCredentials()

> Provide the list of HTTP Headers which use to authentication.

**Parameter**

(none)

**Return**

Array&lt;HTTPHeader&gt;

**Sample code**

<details class="tab-container" open>
<Summary>Provide the list of HTTP Headers which use to authentication in</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    String apiToken = "YOUR_API_TOKEN";
    kintoneAuth.setApiToken(apiToken);

    kintoneAuth.setBasicAuth(username, password);

    for (HTTPHeader header : kintoneAuth.createHeaderCredentials()) {
        System.out.println("key: " + header.getKey());
        System.out.println("value: " + header.getValue());
    }   

    // Expected Output:
    /*
    // For function setPasswordAuth
    key: X-Cybozu-Authorization
    value: WU9VUl9VU0VSTkFNRTpZT1VSX1BBU1NXT1JE

    // For function setApiToken
    key: X-Cybozu-API-Token
    value: YOUR_API_TOKEN

    // For function setBasicAuth
    key: Authorization
    value: Basic WU9VUl9VU0VSTkFNRTpZT1VSX1BBU1NXT1JE
    */
</pre>

</details>

### setClientCert(cert, password)

Set certificate by certificate data

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| cert | InputStream | Yes | Data read from certificate file and receive from kintone
| password | String | Yes | The password from kintone to decode the cert file

**Return**

[Auth](../authentication)

**Sample code**

<details class="tab-container" open>
<Summary>Set certificate by certificate data</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    String certPassword = "YOUR_CERT_PASSWORD";
    String certPath = "YOUR_CERT_PATH";
    try {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(username, password);
        InputStream cert = new FileInputStream(certPath);
        certAuth.setClientCert(cert, certPassword);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>

### setClientCertByPath(filePath, password)

Set certificate by path

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| filePath | String | Yes | Path to kintone certificate file
| password | String | Yes | The password from kintone to decode the cert file

**Return**

[Auth](../authentication)

**Sample code**

<details class="tab-container" open>
<Summary>Set certificate by path</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    String certPassword = "YOUR_CERT_PASSWORD";
    String certPath = "YOUR_CERT_PATH";
    try {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(username, password);
        certAuth.setClientCertByPath(certPath, certPassword);
    } catch (KintoneAPIException e) {
        e.printStackTrace();
    }
</pre>

</details>
