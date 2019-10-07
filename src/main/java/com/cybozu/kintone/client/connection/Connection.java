/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.connection;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.exception.BulksErrorResponse;
import com.cybozu.kintone.client.exception.ErrorResponse;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.http.HTTPHeader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

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
    private int guestSpaceID = -1;

    /*
     * Contains addition headers user set.
     */
    private ArrayList<HTTPHeader> headers = new ArrayList<>();

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
     * @param guestSpaceID Guest space number in kintone domain.
     */
    public Connection(String domain, Auth auth, int guestSpaceID) {
        this.domain = domain;
        this.auth = auth;
        this.guestSpaceID = guestSpaceID;
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

    private HttpsURLConnection setConnectionRequest(String method, String apiName) throws KintoneAPIException {
        try {
            HttpsURLConnection connection = openApiConnection(getURL(apiName, null));
            setHTTPHeaders(connection);
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty(ConnectionConstants.CONTENT_TYPE_HEADER, JSON_CONTENT);

            boolean overrideMethod = ConnectionConstants.GET_REQUEST.equals(method);
            if (overrideMethod) {
                connection.setRequestProperty(ConnectionConstants.METHOD_OVERRIDE_HEADER, ConnectionConstants.GET_REQUEST);
            }
            connection.connect();

            return connection;
        } catch (KintoneAPIException kintoneError) {
            throw kintoneError;
        } catch (Exception e) {
            throw new KintoneAPIException(e.getMessage(), e);
        }
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
        HttpsURLConnection connection = setConnectionRequest(method, apiName);
        String response = null;
        try (
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        ) {
            writer.write(body);
            writer.close();
            checkStatus(connection);
            try (InputStream inputStream = connection.getInputStream()) {
                response = readStream(inputStream);
            }
        } catch (KintoneAPIException kintoneError) {
            throw kintoneError;
        } catch (Exception e) {
            throw new KintoneAPIException(e.getMessage(), e);
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
        HttpsURLConnection connection = setConnectionRequest(ConnectionConstants.GET_REQUEST, ConnectionConstants.FILE);

        try (
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        ) {
            writer.write(body);
            writer.close();
            checkStatus(connection);
            return connection.getInputStream();
        } catch (KintoneAPIException kintoneError) {
            throw kintoneError;
        } catch (Exception e) {
            throw new KintoneAPIException(e.getMessage(), e);
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
        HttpsURLConnection connection = setConnectionRequest(ConnectionConstants.POST_REQUEST, ConnectionConstants.FILE);
        String response;
        try (
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8")
        ) {
            writer.write("--" + ConnectionConstants.BOUNDARY + "\r\n");
            writer.write("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
            writer.write(ConnectionConstants.CONTENT_TYPE_HEADER + ": " + ConnectionConstants.DEFAULT_CONTENT_TYPE
                    + "\r\n\r\n");
            writer.flush();
            byte[] buffer = new byte[8192];
            int n = 0;

            while (-1 != (n = fis.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }

            outputStream.flush();
            writer.write("\r\n--" + ConnectionConstants.BOUNDARY + "--\r\n");
            outputStream.flush();

            writer.close();
            fis.close();

            checkStatus(connection);
            try (InputStream inputStream = connection.getInputStream()) {
                response = readStream(inputStream);
            }
        } catch (KintoneAPIException kintoneError) {
            throw kintoneError;
        } catch (Exception e) {
            throw new KintoneAPIException(e.getMessage(), e);
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
        if (domain == null || domain.isEmpty()) {
            throw new NullPointerException("domain is empty");
        }

        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("api is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!domain.contains(ConnectionConstants.HTTPS_PREFIX)) {
            sb.append(ConnectionConstants.HTTPS_PREFIX);
        }
        if (domain.contains(ConnectionConstants.SECURE_ACCESS_SYMBOL) && auth.getClientCert() == null) {
            throw new KintoneAPIException("client-cert is not set");
        }
        sb.append(domain);

        String urlString = ConnectionConstants.BASE_URL;
        if (guestSpaceID >= 0) {
            urlString = ConnectionConstants.BASE_GUEST_URL.replaceAll("\\{GUEST_SPACE_ID\\}", guestSpaceID + "");
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
        for (HTTPHeader header : auth.createHeaderCredentials()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        connection.setRequestProperty(ConnectionConstants.USER_AGENT_KEY, userAgent);
        for (HTTPHeader header : headers) {
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
        headers.add(new HTTPHeader(key, value));
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            char[] buffer = new char[1024];
            int line = -1;
            while ((line = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, line);
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    /**
     * Get kintone domain url of connection.
     *
     * @return kintone domain
     */
    public String getDomain() {
        return domain;
    }

    public int getGuestSpaceID() {
        return guestSpaceID;
    }

    public Auth getAuth() {
        return auth;
    }

    /**
     * Get pom.properties
     *
     * @return pom properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inStream = getClass().getResourceAsStream("/pom.properties")) {
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Checks the status code of the response.
     *
     * @param conn a connection object
     */
    private void checkStatus(HttpURLConnection conn) throws IOException, KintoneAPIException {
        int statusCode = conn.getResponseCode();
        if (statusCode != 200) {
            Object response = getErrorResponse(conn);
            if (response == null) {
                switch (statusCode) {
                    case 401:
                        throw new KintoneAPIException("401 Unauthorized");
                    case 404:
                        throw new KintoneAPIException("404 Not Found");
                    default:
                        throw new KintoneAPIException("http status code: " + statusCode);
                }
            } else {
                if (response.getClass().getSimpleName().contains("BulksErrorResponse")) {
                    BulksErrorResponse bulksErrorResponse = (BulksErrorResponse) response;
                    throw new KintoneAPIException(statusCode, bulksErrorResponse);
                } else if (response.getClass().getSimpleName().contains("ErrorResponse")){
                    ErrorResponse errorResponse = (ErrorResponse) response;
                    throw new KintoneAPIException(statusCode, errorResponse);
                }
                throw new KintoneAPIException(statusCode, response);
            }
        }
    }

    /**
     * Creates an error response object.
     *
     * @param conn
     * @return ErrorResponse object. return null if any error occurred
     */
    private Object getErrorResponse(HttpURLConnection conn) {
        InputStream err = conn.getErrorStream();
        String response;
        try {
            if (err == null) err = conn.getInputStream();
            response = parseString(err);
            if (response.contains("results")) {
                return gson.fromJson(response, BulksErrorResponse.class);
            }
            return gson.fromJson(response, ErrorResponse.class);
        } catch (IOException e) {
            return null;
        } catch (JsonSyntaxException e) {
            return null;
        }
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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            char[] buffer = new char[1024];
            int dataOffset;
            while (0 <= (dataOffset = reader.read(buffer))) {
                sb.append(buffer, 0, dataOffset);
            }
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
        proxyHost = host;
        proxyPort = port;
        isHttpsProxy = false;
    }

    /**
     * Sets the proxy with authentication.
     *
     * @param host     proxy host
     * @param port     proxy port
     * @param username proxy user
     * @param password proxy password
     */
    public void setProxy(String host, Integer port, String username, String password) {
        proxyHost = host;
        proxyPort = port;
        proxyUser = username;
        proxyPass = password;
        isHttpsProxy = false;

        //allowAuthenticationForHttps. This is required only for jdk > 8u11
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        proxyAuthenticator = new Authenticator() {
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
        proxyHost = host;
        proxyPort = port;
        isHttpsProxy = true;
    }

    /**
     * Sets the SSL-secured proxy with authentication.
     *
     * @param host     proxy host
     * @param port     proxy port
     * @param username proxy user
     * @param password proxy password
     */
    public void setHttpsProxy(String host, Integer port, String username, String password) {
        proxyHost = host;
        proxyPort = port;
        proxyUser = username;
        proxyPass = password;
        isHttpsProxy = true;
    }

    /**
     * Get uri string from api name.
     *
     * @param apiName api name
     * @return pathURI
     */
    public String getPathURI(String apiName) {
        String pathURI = "";

        if (guestSpaceID >= 0) {
            pathURI = ConnectionConstants.BASE_GUEST_URL.replaceAll("\\{GUEST_SPACE_ID\\}", guestSpaceID + "");
        } else {
            pathURI = ConnectionConstants.BASE_URL;
        }
        pathURI = pathURI.replaceAll("\\{API_NAME\\}", apiName);

        return pathURI;
    }

    private HttpsURLConnection openApiConnection(URL apiEndpoint) throws Exception {
        SSLContext sslcontext = null;
        SSLSocketFactoryForHttpsProxy sslSocketFactory = null;
        Proxy proxy = null;

        try {
            // if there is client certificate get the ssl context
            if (auth.getClientCert() != null) {
                sslcontext = auth.getClientCert();
            }
            // set proxy if any is present
            if (proxyHost != null && proxyPort != null) {
                if (isHttpsProxy && sslcontext != null) {
                    // forward factory from ssl context with client certificate
                    sslSocketFactory = new SSLSocketFactoryForHttpsProxy(sslcontext.getSocketFactory());
                } else if (isHttpsProxy && sslcontext == null) {
                    sslSocketFactory = new SSLSocketFactoryForHttpsProxy();
                } else {
                    // normal http proxy 
                    proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                }

                // if sslSocketFactory exists it means proxy is https and needs to set proxy info
                if (sslSocketFactory != null) {
                    sslSocketFactory.setProxyHost(proxyHost);
                    sslSocketFactory.setProxyPort(proxyPort);
                    if (proxyUser != null && proxyPass != null) {
                        sslSocketFactory.setProxyUserName(proxyUser);
                        sslSocketFactory.setProxyPassword(proxyPass);
                    }
                }
            }
        } catch (Exception err) {
            throw err;
        }

        HttpsURLConnection connection = null;
        // set http proxy for connection
        if (proxy != null) {
            connection = (HttpsURLConnection) apiEndpoint.openConnection(proxy);
            if (proxyAuthenticator != null) connection.setAuthenticator(proxyAuthenticator);
        } else {
            connection = (HttpsURLConnection) apiEndpoint.openConnection();
        }
        // set ssl socket factory for connection to connect to ssl secured proxy
        if (sslSocketFactory != null) {
            connection.setSSLSocketFactory(sslSocketFactory);
        }
        return connection;
    }
}
