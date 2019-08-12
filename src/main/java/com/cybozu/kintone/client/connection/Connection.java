/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.exception.ErrorResponse;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestItem;
import com.cybozu.kintone.client.model.http.HTTPHeader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Connection object to working with rest api.
 * This class provide core request function for all modules management classes.
 */
public class Connection {
    /*
     * HTTP header content-type for getting json data from rest api.
     */
    private static final String JSON_CONTENT = "application/json";

    /*
     * Json parser for parsing the request's result.
     */
    private static final JsonParser jsonParser = new JsonParser();

    /*
     * Json string to object converter.
     */
    private static final Gson gson = new Gson();

    /*
     * User agent http header.
     */
    public static String userAgent = ConnectionConstants.USER_AGENT_VALUE;

    /*
     * Object contains user's credential.
     */
    private Auth auth;

    /*
     * Kintone domain url.
     */
    private String domain;

    /*
     * Guest space number in kintone domain.
     * User describe it when connect data in guest space.
     */
    private int guestSpaceId = -1;

    /*
     * Contains addition headers user set.
     */
    private ArrayList<HTTPHeader> headers = new ArrayList<HTTPHeader>();

    /*
     * Contains information for bypass proxy.
     */
    private String proxyHost = null;
    private Integer proxyPort = null;
    private Boolean isHttpsProxy = false;
    private String proxyUser = null;
    private String proxyPass = null;
    private Authenticator proxyAuthenticator = null;

    /**
     * Constructor for init a connection object to connect to guest space.
     *
     * @param domain       Kintone domain url
     * @param auth         Credential information
     * @param guestSpaceId Guest space number in kintone domain.
     */
    public Connection(String domain, Auth auth, int guestSpaceId) {
        this.domain = domain;
        this.auth = auth;
        this.guestSpaceId = guestSpaceId;
        userAgent += "/" + getProperties().getProperty("version");
    }

    /**
     * Constructor for init a connection object to connect to normal space.
     *
     * @param domain Kintone domain url
     * @param auth   Credential information
     */
    public Connection(String domain, Auth auth) {
        this(domain, auth, -1);
    }

    /**
     * Rest http request.
     * This method is low level api, use the correspondence methods in module package instead.
     *
     * @param method  rest http method. Only accept "GET", "POST", "PUT", "DELETE" value.
     * @param apiName api name
     * @param body    body of http request. In case "GET" method, the parameters.
     * @return json object
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public JsonElement request(String method, String apiName, String body) throws KintoneAPIException {
        HttpsURLConnection connection = null;
        String response = null;

        boolean isGet = false;
        if (ConnectionConstants.GET_REQUEST.equals(method)) {
            isGet = true;
        }

        URL url = null;
        try {
            url = this.getURL(apiName, null);
        } catch (MalformedURLException e) {
            throw new KintoneAPIException("Invalid URL");
        }

        try {
            connection = openApiConnection(url);
            this.setHTTPHeaders(connection);
            connection.setRequestMethod(method);
        } catch (Exception e) {
            throw new KintoneAPIException("can not open connection");
        }

        connection.setDoOutput(true);
        connection.setRequestProperty(ConnectionConstants.CONTENT_TYPE_HEADER, JSON_CONTENT);
        if (isGet) {
            connection.setRequestProperty(ConnectionConstants.METHOD_OVERRIDE_HEADER, ConnectionConstants.GET_REQUEST);
        }

        try {
            connection.connect();
        } catch (IOException e) {
            throw new KintoneAPIException(" cannot connect to host");
        }

        // send request
        OutputStream os;
        try {
            os = connection.getOutputStream();
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while sending data");
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            writer.write(body);
            writer.close();
        } catch (IOException e) {
            throw new KintoneAPIException("socket error");
        }

        try {
            checkStatus(connection, body);
            InputStream is = connection.getInputStream();
            try {
                response = readStream(is);
            } finally {
                is.close();
            }
        } catch (Exception e) {
            throw (KintoneAPIException) e;
        }


        return jsonParser.parse(response);
    }

    /**
     * Rest http request.
     * This method is execute file download
     *
     * @param body the body of the downloadfile
     * @return inputstream
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public InputStream downloadFile(String body) throws KintoneAPIException {
        HttpsURLConnection connection = null;
        URL url;

        try {
            url = this.getURL(ConnectionConstants.FILE, null);
        } catch (MalformedURLException e1) {
            throw new KintoneAPIException("invalid url");
        }

        try {
            connection = openApiConnection(url);
            this.setHTTPHeaders(connection);
            connection.setRequestMethod(ConnectionConstants.GET_REQUEST);
        } catch (Exception e) {
            throw new KintoneAPIException("can not open connection");
        }

        connection.setDoOutput(true);
        connection.setRequestProperty(ConnectionConstants.CONTENT_TYPE_HEADER, JSON_CONTENT);
        connection.setRequestProperty(ConnectionConstants.METHOD_OVERRIDE_HEADER, ConnectionConstants.GET_REQUEST);

        try {
            connection.connect();
        } catch (IOException e) {
            throw new KintoneAPIException("cannot connect to host");
        }

        // send request
        OutputStream os;
        try {
            os = connection.getOutputStream();
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while sending data");
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            writer.write(body);
            writer.close();
        } catch (IOException e) {
            throw new KintoneAPIException("socket error");
        }

        // receive response
        try {
            checkStatus(connection, body);
            InputStream is = connection.getInputStream();
            return is;
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while receiving data");
        }
    }

    /**
     * Rest http request.
     * This method is execute file upload.
     *
     * @param fileName upload file name
     * @param fis      file inputstream
     * @return json object
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public JsonElement uploadFile(String fileName, InputStream fis) throws KintoneAPIException {

        HttpsURLConnection connection;
        String response = null;

        URL url = null;
        try {
            url = this.getURL(ConnectionConstants.FILE, null);
        } catch (MalformedURLException e) {
            throw new KintoneAPIException("Invalid URL");
        }

        try {
            connection = openApiConnection(url);
            this.setHTTPHeaders(connection);
            connection.setRequestMethod(ConnectionConstants.POST_REQUEST);
        } catch (Exception e) {
            throw new KintoneAPIException("can not open connection");
        }

        connection.setDoOutput(true);
        connection.setRequestProperty(ConnectionConstants.CONTENT_TYPE_HEADER,
                "multipart/form-data; boundary=" + ConnectionConstants.BOUNDARY);

        try {
            connection.connect();
        } catch (IOException e) {
            throw new KintoneAPIException("cannot connect to host");
        }

        OutputStream os;
        try {
            os = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
            writer.write("--" + ConnectionConstants.BOUNDARY + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + fileName + "\"\r\n");
            writer.write(ConnectionConstants.CONTENT_TYPE_HEADER + ": " + ConnectionConstants.DEFAULT_CONTENT_TYPE
                    + "\r\n\r\n");
            writer.flush();
            byte[] buffer = new byte[8192];
            int n = 0;
            while (-1 != (n = fis.read(buffer))) {
                os.write(buffer, 0, n);
            }
            os.flush();
            writer.write("\r\n--" + ConnectionConstants.BOUNDARY + "--\r\n");
            os.flush();
            writer.close();
            fis.close();
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while sending data");
        }

        // receive response
        try {
            checkStatus(connection, null);
            InputStream is = connection.getInputStream();
            try {
                response = readStream(is);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while receiving data");
        }

        return jsonParser.parse(response);
    }


    /**
     * Get url string from domain name, api name and parameters.
     *
     * @param apiName
     * @param parameters
     * @return url
     * @throws MalformedURLException
     * @throws KintoneAPIException
     */
    private URL getURL(String apiName, String parameters) throws MalformedURLException, KintoneAPIException {
        if (this.domain == null || this.domain.isEmpty()) {
            throw new NullPointerException("domain is empty");
        }

        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("api is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!this.domain.contains(ConnectionConstants.HTTPS_PREFIX)) {
            sb.append(ConnectionConstants.HTTPS_PREFIX);
        }
        if (this.domain.contains(ConnectionConstants.SECURE_ACCESS_SYMBOL) && this.auth.getClientCert() == null) {
            throw new KintoneAPIException("client-cert is not set");
        }
        sb.append(this.domain);

        String urlString = ConnectionConstants.BASE_URL;
        if (this.guestSpaceId >= 0) {
            urlString = ConnectionConstants.BASE_GUEST_URL.replaceAll("\\{GUEST_SPACE_ID\\}", this.guestSpaceId + "");
        }
        urlString = urlString.replaceAll("\\{API_NAME\\}", apiName);

        sb.append(urlString);
        if (parameters != null) {
            sb.append(parameters);
        }

        return new URL(sb.toString().replaceAll("\\s", "%20"));
    }

    /**
     * Set HTTP headers for connections.
     *
     * @param connection
     */
    private void setHTTPHeaders(HttpURLConnection connection) {
        for (HTTPHeader header : this.auth.createHeaderCredentials()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        connection.setRequestProperty(ConnectionConstants.USER_AGENT_KEY, userAgent);
        for (HTTPHeader header : this.headers) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }

    /**
     * Set addition header when connect.
     *
     * @param key   the key to set
     * @param value the value to set
     * @return connection
     * Connection object.
     */
    public Connection setHeader(String key, String value) {
        this.headers.add(new HTTPHeader(key, value));
        return this;
    }

    /**
     * Set authentication for connection
     *
     * @param auth the auth to set
     * @return connection
     * Connection object.
     */
    public Connection setAuth(Auth auth) {
        this.auth = auth;
        return this;
    }

    /**
     * Parse input stream to string.
     *
     * @param is InputStream
     * @return result
     * String data
     */
    private String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            char[] buffer = new char[1024];
            int line = -1;
            while ((line = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, line);
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }

        return sb.toString();
    }

    /**
     * Get kintone domain url of connection.
     *
     * @return kintone domain
     */
    public String getDomain() {
        return this.domain;
    }

    public int getGuestSpaceId() {
        return this.guestSpaceId;
    }

    public Auth getAuth() {
        return this.auth;
    }

    /**
     * Get pom.properties
     *
     * @return pom properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream inStream = null;
        try {
            inStream = this.getClass().getResourceAsStream("/pom.properties");
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    /**
     * Checks the status code of the response.
     *
     * @param conn a connection object
     * @param body
     */
    private void checkStatus(HttpURLConnection conn, String body) throws IOException, KintoneAPIException {
        int statusCode = conn.getResponseCode();
        if (statusCode == 404) {
            ErrorResponse response = getErrorResponse(conn);
            if (response == null) {
                throw new KintoneAPIException("not found");
            } else {
                throw new KintoneAPIException(statusCode, response);
            }
        }

        if (statusCode == 401) {
            throw new KintoneAPIException("401 Unauthorized");
        }

        if (statusCode != 200) {
            if (conn.getURL().getFile().toString().equals(this.getPathURI(ConnectionConstants.BULK_REQUEST))) {
                ArrayList<ErrorResponse> responses = getErrorResponses(conn);
                if (responses == null) {
                    throw new KintoneAPIException("http status error(" + statusCode + ")");
                } else {
                    JsonObject jobject = new Gson().fromJson(body, JsonObject.class);
                    JsonArray jarray = jobject.getAsJsonArray("requests");

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<BulkRequestItem>>() {
                    }.getType();
                    ArrayList<BulkRequestItem> requestlist = gson.fromJson(jarray, listType);

                    throw new KintoneAPIException(statusCode, requestlist, responses);
                }
            } else {
                ErrorResponse response = getErrorResponse(conn);
                if (response == null) {
                    throw new KintoneAPIException("http status error(" + statusCode + ")");
                } else {
                    throw new KintoneAPIException(statusCode, response);
                }
            }
        }
    }

    /**
     * Creates an error response object.
     *
     * @param conn
     * @return ErrorResponse object. return null if any error occurred
     */
    private ErrorResponse getErrorResponse(HttpURLConnection conn) {
        InputStream err = conn.getErrorStream();

        String response;
        try {
            if (err == null) {
                err = conn.getInputStream();
            }
            response = parseString(err);
        } catch (IOException e) {
            return null;
        }

        try {
            return gson.fromJson(response, ErrorResponse.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Creates an error response list object.
     *
     * @param conn
     * @return ErrorResponse list object. return null if any error occurred
     */
    private ArrayList<ErrorResponse> getErrorResponses(HttpURLConnection conn) {
        InputStream err = conn.getErrorStream();

        String response;
        try {
            if (err == null) {
                err = conn.getInputStream();
            }
            response = parseString(err);
        } catch (IOException e) {
            return null;
        }

        JsonElement jsonele = jsonParser.parse(response);
        JsonObject object = jsonele.getAsJsonObject();
        JsonArray array = object.getAsJsonArray("results");

        Type type = new TypeToken<ArrayList<ErrorResponse>>() {
        }.getType();
        ArrayList<ErrorResponse> errorResponseList = gson.fromJson(array, type);
        return errorResponseList;
    }

    /**
     * An utility method converts a stream object to string.
     *
     * @param is input stream
     * @return string
     * @throws IOException
     */
    private String parseString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        try {
            char[] buffer = new char[1024];
            int dataOffset;

            while (0 <= (dataOffset = reader.read(buffer))) {
                sb.append(buffer, 0, dataOffset);
            }
        } finally {
            reader.close();
        }

        return sb.toString();
    }

    /**
     * Sets the proxy.
     *
     * @param host proxy host
     * @param port proxy port
     */
    public void setProxy(String host, Integer port) {
        this.proxyHost = host;
        this.proxyPort = port;
        this.isHttpsProxy = false;
    }

    /**
     * Sets the proxy with authentication.
     *
     * @param host proxy host
     * @param port proxy port
     * @param username proxy user
     * @param password proxy password
     */
    public void setProxy(String host, Integer port, String username, String password) {
        this.proxyHost = host;
        this.proxyPort = port;
        this.proxyUser = username;
        this.proxyPass = password;
        this.isHttpsProxy = false;

        //allowAuthenticationForHttps. This is required only for jdk > 8u11
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        this.proxyAuthenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
            }
        };
    }

    /**
     * Sets the SSL-secured proxy.
     *
     * @param host proxy host
     * @param port proxy port
     */
    public void setHttpsProxy(String host, Integer port) {
        this.proxyHost = host;
        this.proxyPort = port;
        this.isHttpsProxy = true;
    }

    /**
     * Sets the SSL-secured proxy with authentication.
     *
     * @param host proxy host
     * @param port proxy port
     * @param username proxy user
     * @param password proxy password
     */
    public void setHttpsProxy(String host, Integer port, String username, String password) {
        this.proxyHost = host;
        this.proxyPort = port;
        this.proxyUser = username;
        this.proxyPass = password;
        this.isHttpsProxy = true;
    }

    /**
     * Get uri string from api name.
     *
     * @param apiName api name
     * @return pathURI
     */
    public String getPathURI(String apiName) {
        String pathURI = "";

        if (this.guestSpaceId >= 0) {
            pathURI = ConnectionConstants.BASE_GUEST_URL.replaceAll("\\{GUEST_SPACE_ID\\}", this.guestSpaceId + "");
        } else {
            pathURI = ConnectionConstants.BASE_URL;
        }
        pathURI = pathURI.replaceAll("\\{API_NAME\\}", apiName);

        return pathURI;
    }

    private HttpsURLConnection openApiConnection(URL apiEndpoint) throws Exception {
        HttpsURLConnection connection = null;
        SSLContext sslcontext = null;
        SSLSocketFactoryForHttpsProxy sslSocketFactory = null;
        Proxy proxy = null;
        try {
            // if there is client certificate get the ssl context
            if (this.auth.getClientCert() != null) {
                sslcontext = this.auth.getClientCert();
            }

            // set proxy if any is present
            if(proxyHost != null && proxyPort != null) {
                if(this.isHttpsProxy && sslcontext != null) {
                    // forward factory from ssl context with client certificate
                    sslSocketFactory = new SSLSocketFactoryForHttpsProxy(sslcontext.getSocketFactory());
                } else if( this.isHttpsProxy && sslcontext == null) {
                    sslSocketFactory = new SSLSocketFactoryForHttpsProxy();
                } else {
                    // normal http proxy 
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                }

                // if sslSocketFactory exists it means proxy is https and needs to set proxy info
                if(sslSocketFactory != null) {
                    sslSocketFactory.setProxyHost(proxyHost);
                    sslSocketFactory.setProxyPort(proxyPort);
                    if(proxyUser != null && proxyPass != null) {
                        sslSocketFactory.setProxyUserName(proxyUser);
                        sslSocketFactory.setProxyPassword(proxyPass);
                    }
                }
            } else if(sslcontext != null) {
                // no ssl proxy, but there is client certificate so set factory to factory of ssl context

            }
        } catch (Exception err) {
            throw err;
        }

        // set http proxy for connection
        if(proxy != null) {
            connection = (HttpsURLConnection) apiEndpoint.openConnection(proxy);
            if(this.proxyAuthenticator != null) {
                connection.setAuthenticator(this.proxyAuthenticator);
            }
        } else {
            connection = (HttpsURLConnection) apiEndpoint.openConnection();
        }

        // set ssl socket factory for connection to connect to ssl secured proxy
        if(sslSocketFactory != null) {
            connection.setSSLSocketFactory(sslSocketFactory);
        }
        return connection;
    }
}
