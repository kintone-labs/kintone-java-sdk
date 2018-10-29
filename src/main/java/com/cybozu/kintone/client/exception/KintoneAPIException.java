/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.exception;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.bulkrequest.BulkRequestItem;

/**
 * Exception will occur in using kintone RestAPI.
 *
 */
public class KintoneAPIException extends Exception {
    private static final long serialVersionUID = 1L;
    private int httpErrorCode;
    private ErrorResponse errorResponse;
    private ArrayList<BulkRequestItem> request;
    private ArrayList<ErrorResponse> errorResponses;

    /**
     * @param httpErrorCode
     * @param errorResponse
     */
    public KintoneAPIException(int httpErrorCode, ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    public KintoneAPIException(int httpErrorCode, ArrayList<BulkRequestItem> request, ArrayList<ErrorResponse> errorResponses) {
        this.httpErrorCode = httpErrorCode;
        this.errorResponses = errorResponses;
        this.request = request;
    }

    /**
     * @param error
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

    /**
     * @return the errorResponses
     */
    public ArrayList<ErrorResponse> getErrorResponses() {
        return this.errorResponses;
    }

    @Override
    public String toString() {
        if (this.errorResponse == null) {
            if (this.request == null || this.errorResponses == null) {
                return super.toString();
            }
        }

        StringBuilder sb = new StringBuilder();

        if (this.errorResponses == null) {
            sb.append("id: " + errorResponse.getId());
            sb.append(", code: " + errorResponse.getCode());
            sb.append(", message: " + errorResponse.getMessage());
            sb.append(", errors: " + errorResponse.getErrors());
        } else {
            Integer count = 1;
            for(ErrorResponse errorResponse : errorResponses) {
                if (errorResponse.getId() != null) {
                    sb.append("api_no: " + count.toString());
                    sb.append(", method: " + this.request.get(count - 1).getMethod());
                    sb.append(", api_name: " + this.request.get(count - 1).getApi());
                    sb.append(", id: " + errorResponse.getId());
                    sb.append(", code: " + errorResponse.getCode());
                    sb.append(", message: " + errorResponse.getMessage());
                    sb.append(", errors: " + errorResponse.getErrors());
                }
                count++;
            }
        }

        sb.append(", status: " + this.httpErrorCode);

        return sb.toString();
    }
}
