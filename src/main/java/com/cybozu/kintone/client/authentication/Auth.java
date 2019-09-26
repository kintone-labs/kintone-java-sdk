/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.authentication;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.authentication.Credential;
import com.cybozu.kintone.client.model.http.HTTPHeader;

/**
 * Authentication allows you to use kintone REST APIs.
 * The Authentication class is use for {@link com.cybozu.kintone.client.connection.Connection}
 * for authenticate user by username/password or apiToken.
 *
 * If both the Token and Password Authentication are specified,
 * the Token Authentication will be ignored and the Password authentication will be used.
 *
 */
public class Auth {

    /*
     * Basic authentication crendential.
     */
    private Credential basicAuth;

    /*
     * Username/Password authentication credential.
     */
    private Credential passwordAuth;

    /*
     * Api token string which provided by kintone domain.
     */
    private String apiToken;

    /**
     * Client certification for kintone
     */
    private SSLContext clientCert;

    /**
     * default constructor
     */
    public Auth() {}

    /**
     * @return the basicAuth
     */
    public Credential getBasicAuth() {
        return basicAuth;
    }

    /**
     * @param username
     *            the username to set
     * @param password
     *            the password to set
     * @return auth
     */
    public Auth setBasicAuth(String username, String password) {
        basicAuth = new Credential(username, password);
        return this;
    }

    /**
     * @return the passwordAuth
     */
    public Credential getPasswordAuth() {
        return passwordAuth;
    }

    /**
     * @param username
     *            the username to set
     * @param password
     *            the password to set
     * @return auth
     *            the Auth object which contains username/password.
     */
    public Auth setPasswordAuth(String username, String password) {
        passwordAuth = new Credential(username, password);
        return this;
    }

    /**
     * @return the apiToken
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     * @param apiToken
     *            the apiToken to set
     * @return auth
     *            the Auth object which contains api token string.
     */
    public Auth setApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    /**
     * @return the client certification
     */
    public SSLContext getClientCert() {
        return clientCert;
    }

    /**
     *  set the client certification by file path
     * @param filePath
     *           the filePath to set
     * @param password
     *           the password to set
     * @return auth
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public Auth setClientCertByPath(String filePath, String password) throws KintoneAPIException {
        try {
            InputStream cert = new FileInputStream(filePath);
            return setClientCert(cert, password);
        } catch (Exception e) {
            throw new KintoneAPIException("Certificate error", e);
        }
    }

    /**
     * set the client certification by InputStream
     * @param cert
     *           the cert to set
     * @param password
     *           the password to set
     * @return auth
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public Auth setClientCert(InputStream cert, String password) throws KintoneAPIException {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            KeyStore key_store = KeyStore.getInstance(AuthenticationConstants.KEY_STORE_TYPE);
            char[] key_pass = password.toCharArray();
            key_store.load(cert, key_pass);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(key_store, key_pass);
            clientCert = SSLContext.getInstance(AuthenticationConstants.SOCKET_PROTOCOL);
            clientCert.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
            return this;
        } catch (Exception e) {
            throw new KintoneAPIException("Certificate error", e);
        }
    }

    /**
     * Provide the list of HTTP Headers which use to authentication in
     * {@link com.cybozu.kintone.client.connection.Connection}
     * @return headers list
     */
    public ArrayList<HTTPHeader> createHeaderCredentials() {
        ArrayList<HTTPHeader> headers = new ArrayList<>();

        if (passwordAuth != null) {
            String passwordAuthString = passwordAuth.getUsername() + ":" + passwordAuth.getPassword();
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_PASSWORD,
                    Base64.getEncoder().encodeToString(passwordAuthString.getBytes())));
        }

        if (apiToken != null) {
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_APITOKEN, apiToken));
        }

        if (basicAuth != null) {
            String basicAuthString = basicAuth.getUsername() + ":" + basicAuth.getPassword();
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_BASIC,
                    AuthenticationConstants.AUTH_BASIC_PREFIX
                            + Base64.getEncoder().encodeToString(basicAuthString.getBytes())));
        }

        return headers;
    }
}
