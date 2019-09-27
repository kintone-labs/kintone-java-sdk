# Connection

[Connection](#) module will used as a connector to connect to kintone Rest API

## Constructor
**Declaration**
```
public Connection(String domain, Auth auth)
public Connection(String domain, Auth auth, int guestSpaceID)
```
**Parameter**

| Name| Description |
| --- | --- |
| domain |The Domain name or FQDN
| auth | The authentication object ([Auth](../authentication))
| guestSpaceID | The guest space id. This parameter is used for connect to kintone guest space.

**Sample code**

<details class="tab-container" open>
<Summary>Init Connection module</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

    // Define Authentication object
    Auth kintoneAuth = new Auth();
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    kintoneAuth.setPasswordAuth(username, password);

    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Define connection that included guest space
    int guestSpaceId = 0; // Input your guest space id number
    Connection kintoneConnectionWithGuestSpaceDemo =
                new Connection(kintoneDomain, kintoneAuth, guestSpaceId);

</pre>

</details>

## Methods

### setHeader(key, value)

> Set new header of the [Connection](../connection)

**Declaration**
```
public Connection setHeader(String key, String value)
```
**Parameter**

| Name| Description |
| --- | --- |
| key | The header's `key` name
| value | The header's value of `key`

**Sample code**

<details class="tab-container" open>
<Summary>Set header of the Connection</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    
    String key = "X-HTTP-Method-Override";
    String value = "GET";

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    kintoneAuth.setPasswordAuth(username, password);

    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);
    kintoneConnection.setHeader(key, value);

</pre>

</details>

### setProxy(host, port, username, password)

> Set the proxy of the HTTP request  

**Declaration**
```
public void setProxy(String host, Integer port)
public void setProxy(String host, Integer port, String username, String password) 
```
**Parameter**

| Name| Description |
| --- | --- |
| host | The proxy host name
| port | The proxy port number
| username | Username of the proxy
| password | String | Optional | Password of the proxy

<strong class="tab-name">Source code</strong>

<details class="tab-container" open>
<Summary>Set the proxy of the request</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    String proxyHost = "YOUR_PROXY_HOST";
    Integer proxyPort = 1111; // Input your proxy port

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Set proxy without proxyUsername & proxyPassword
    kintoneConnection.setProxy(proxyHost, proxyPort);

    // Set proxy with proxyUsername & proxyPassword
    String proxyUsername = "YOUR_PROXY_USERNAME";
    String proxyPassword = "YOUR_PROXY_PASSWORD";
    kintoneConnection.setProxy(proxyHost, proxyPort, proxyUsername, proxyPassword);
</pre>

</details>

### setHttpsProxy(host, port, username, password)

> Set the SSL-secured proxy of the HTTPS request   

**Declaration**
```
public void setHttpsProxy(String host, Integer port) 
public void setHttpsProxy(String host, Integer port, String username, String password)
```
**Parameter**

| Name| Description |
| --- | --- |
| host |The proxy host name
| port | The proxy port number
| username | Username of the proxy
| password | Password of the proxy

<strong class="tab-name">Source code</strong>

<details class="tab-container" open>
<Summary>Set the proxy of the request</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
    String username = "YOUR_USERNAME";
    String password = "YOUR_PASSWORD";
    String proxyHost = "YOUR_PROXY_HOST";
    Integer proxyPort = 1111; // Input your proxy port

    // Init authenticationAuth
    Auth kintoneAuth = new Auth();
    kintoneAuth.setPasswordAuth(username, password);

    String kintoneDomain = "YOUR_DOMAIN.COM";
    Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

    // Set ssl-secured proxy without proxyUsername & proxyPassword
    kintoneConnection.setHttpsProxy(proxyHost, proxyPort);

    // Set ssl-secured proxy with proxyUsername & proxyPassword
    String proxyUsername = "YOUR_PROXY_USERNAME";
    String proxyPassword = "YOUR_PROXY_PASSWORD";
    kintoneConnection.setHttpsProxy(proxyHost, proxyPort, proxyUsername, proxyPassword);
</pre>

</details>
