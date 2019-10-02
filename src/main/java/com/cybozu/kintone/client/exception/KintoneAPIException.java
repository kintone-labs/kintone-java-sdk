/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.exception;

/**
 * Exception will occur in using kintone RestAPI.
 *
 */
public class KintoneAPIException extends Exception {
    private static final long serialVersionUID = 1L;
    private int httpErrorCode;
    private ErrorResponse errorResponse;

    /**
     * @param httpErrorCode httpErrorCode of the KintoneAPIException
     * @param errorResponse errorResponse of the KintoneAPIException
     * @param cause cause of the KintoneAPIException
     */
    public KintoneAPIException(int httpErrorCode, ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.getMessage(), cause);
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    /**
     * @param httpErrorCode httpErrorCode of the KintoneAPIException
     * @param errorResponse errorResponse of the KintoneAPIException
     */
    public KintoneAPIException(int httpErrorCode, ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    /**
     * @param error the error of the KintoneAPIException
     * @param cause cause of the KintoneAPIException
     */
    public KintoneAPIException(String error, Throwable cause) {
        super(error, cause);
    }

    /**
     * @param error the error of the KintoneAPIException
     */
    public KintoneAPIException(String error) {
        super(error);
    }

    /**
     * @return the httpErrorCode
     */
    public int getHttpErrorCode() {
        return this.httpErrorCode;
    }

    /**
     * @return the errorResponse
     */
    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }

}
