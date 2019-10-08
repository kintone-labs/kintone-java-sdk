# Authentication

Authentication module will be used by [Connection](../connection).
This module allows authentication with kintone app by password authenticator or API token authenticator. This module also supports basic authenticator.

!!! warning

    - If both the Token and Password Authentication are specified, the Token Authentication will be ignored and the Password authentication will be used.

## Constructor
**Declaration**
```
public Auth() {}
```

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

### setPasswordAuth

> Set password authentication for Authentication module.

**Declaration**
```
public Auth setPasswordAuth(String username, String password)
```

**Parameter**

| Name| Description |
| --- | --- |
| username | The username for authentication with kintone app.
| password | The password for authentication with kintone app.

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

### setApiToken

> Set Api Token for Authentication module.

**Declaration**
```
public Auth setApiToken(String apiToken)
```
**Parameter**

| Name| Description |
| --- |--- |
| apiToken | The API token used to authenticate with kintone app.

<details class="tab-container" open>
<Summary>Set APIToken authentication</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    String apiToken = "YOUR_API_TOKEN";
    Auth kintoneAuth = new Auth();
    kintoneAuth.setApiToken(apiToken);

</pre>
</details>

### setBasicAuth

> Set Basic authentication for Authentication module.

**Declaration**
```
public Auth setBasicAuth(String username, String password)
```

**Parameter**

| Name| Description |
| --- | --- |
| username | The username used for basic authentication with kintone.
| password | The password used for basic authentication with kintone.

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

### createHeaderCredentials

> Provide the list of HTTP Headers for authentication.

**Declaration**
```
public ArrayList<HTTPHeader> createHeaderCredentials() 
```

**Parameter**

(none)

**Sample code**

<details class="tab-container" open>
<Summary>Provide the list of HTTP Headers which use to authentication</Summary>

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

### setClientCert

> Set certificate by certificate data

**Declaration**
```
public Auth setClientCert(InputStream cert, String password) throws KintoneAPIException 
```
**Parameter**

| Name| Description |
| --- | --- |
| cert | Data read from certificate file and receive from kintone
| password | The password from kintone to decode the cert file

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

### setClientCertByPath
> Set certificate by path

**Declaration**
```
public Auth setClientCertByPath(String filePath, String password) throws KintoneAPIException
```

**Parameter**

| Name| Description |
| --- | --- | 
| filePath | Path to kintone certificate file
| password | The password from kintone to decode the cert file

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
