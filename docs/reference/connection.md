# Connection

[Connection](#) module will used as a connector to connect to kintone Rest API

## Constructor

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| domain | String | yes | The Domain name or FQDN
| auth | [Auth](../authentication) | yes | The authentication object
| guestSpaceID | Integer | (optional) | The guest space id. Use this parameter to connect to kintone guest space.

**Sample code**

<details class="tab-container" open>
<Summary>Init Connection module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

// Define Authentication object
Auth kintoneAuth = new Auth();
String username = "cybozu";
String password = "cybozu";
kintoneAuth.setPasswordAuth(username, password);

String myDomainName = "sample.cybozu.com";
Connection connection = new Connection(myDomainName, kintoneAuth);


// Define connection that included guest space
int guestSpaceID = 1;
Connection kintoneConnectionWithGuestSpaceDemo =
    new Connection(myDomainName, kintoneAuth, guestSpaceID);

</pre>

</details>

## Methods

### setHeader(key, value)

> Set new header of the [Connection](../connection)

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| key | String | yes | The header's `key` name
| value | String | yes | The header's value of `key`

**Return**

[Connection](../connection)

**Sample code**

<details class="tab-container" open>
<Summary>Set header of the Connection</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String username = "cybozu";
String password = "cybozu";
String key = "X-HTTP-Method-Override";
String value = "GET";

// Init authenticationAuth
Auth kintoneAuth = new Auth();
kintoneAuth.setPasswordAuth(username, password);

String myDomainName = "sample.cybozu.com";
Connection connection = new Connection(myDomainName, kintoneAuth);
connection.setHeader(key, value);
</pre>

</details>

### setProxy(proxyHost, proxyPort, proxyUsername, proxyPassword)

> Set the proxy of the request

**Parameter**

| Name| Type| Required| Description |
| --- | --- | --- | --- |
| proxyHost | String | yes | The proxy host name
| proxyPort | Integer | yes | The proxy port number
| proxyUsername | String | (optional) | User name of the proxy
| proxyPassword | String | (optional) | Password of the proxy

**Return**

[Connection](../connection)

<strong class="tab-name">Source code</strong>

<details class="tab-container" open>
<Summary>Set the proxy of the request</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
String username = "cybozu";
String password = "cybozu";
String proxyHost = "xxxx";
Integer proxyPort = 1234;
  
// Init authenticationAuth
Auth kintoneAuth = new Auth();
kintoneAuth.setPasswordAuth(username, password);

String myDomainName = "sample.cybozu.com";
Connection connection = new Connection(myDomainName, kintoneAuth);

// Set proxy without proxyUsername & proxyPassword
connection.setProxy(proxyHost, proxyPort);

// Set proxy with proxyUsername & proxyPassword
String proxyUsername = "xxxx";
String proxyPassword = "xxxx";
connection.setProxy(proxyHost, proxyPort, proxyUsername, proxyPassword);
</pre>

</details>
