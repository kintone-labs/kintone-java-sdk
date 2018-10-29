/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.authentication;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
     * default constructor
     */
    public Auth() {

    }

    /**
     * @return the basicAuth
     */
    public Credential getBasicAuth() {
        return this.basicAuth;
    }

    /**
     * @param basicAuth
     *            the basicAuth to set
     */
    public Auth setBasicAuth(String username, String password) {
        this.basicAuth = new Credential(username, password);
        return this;
    }

    /**
     * @return the passwordAuth
     */
    public Credential getPasswordAuth() {
        return this.passwordAuth;
    }

    /**
     * @param passwordAuth
     *            the passwordAuth to set
     * @return auth
     *            the Auth object which contains username/password.
     */
    public Auth setPasswordAuth(String username, String password) {
        this.passwordAuth = new Credential(username, password);
        return this;
    }

    /**
     * @return the apiToken
     */
    public String getApiToken() {
        return this.apiToken;
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
     * Provide the list of HTTP Headers which use to authentication in
     * {@link com.cybozu.kintone.client.connection.Connection}
     * @return headers list
     */
    public List<HTTPHeader> createHeaderCredentials() {
        List<HTTPHeader> headers = new ArrayList<HTTPHeader>();

        if (this.passwordAuth != null) {
            String passwordAuthString = this.passwordAuth.getUsername() + ":" + this.passwordAuth.getPassword();
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_PASSWORD, Base64.getEncoder().encodeToString(passwordAuthString.getBytes())));
        }

        if (this.apiToken != null) {
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_APITOKEN, this.apiToken));
        }

        if (this.basicAuth != null) {
            String basicAuthString = this.basicAuth.getUsername() + ":" + this.basicAuth.getPassword();
            headers.add(new HTTPHeader(AuthenticationConstants.HEADER_KEY_AUTH_BASIC, AuthenticationConstants.AUTH_BASIC_PREFIX + Base64.getEncoder().encodeToString(basicAuthString.getBytes())));
        }

        return headers;
    }
}
