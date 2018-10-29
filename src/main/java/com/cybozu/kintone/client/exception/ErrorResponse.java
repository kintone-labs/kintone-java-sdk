/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.exception;

public class ErrorResponse {
    private String message;
    private String id;
    private String code;
    private Object errors;

    /**
     * @param message
     * @param id
     * @param code
     */
    public ErrorResponse(String message, String id, String code, Object errors) {
        this.message = message;
        this.id = id;
        this.code = code;
        this.errors = errors;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the errors
     */
    public Object getErrors() {
        return this.errors;
    }

    /**
     * @param errors
     *            the errors to set
     */
    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
