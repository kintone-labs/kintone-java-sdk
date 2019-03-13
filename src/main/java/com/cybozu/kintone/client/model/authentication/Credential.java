/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.authentication;

public class Credential {
    private String username;
    private String password;


    /**
     * @param username username of the Credential
     * @param password password of the Credential
     */
    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }
}
