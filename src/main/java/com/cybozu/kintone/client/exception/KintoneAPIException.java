/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.exception;

import com.cybozu.kintone.client.model.bulkrequest.BulkRequestItem;

import java.util.ArrayList;

/**
 * Exception will occur in using kintone RestAPI.
 */
public class KintoneAPIException extends Exception {
    private static final long serialVersionUID = 1L;
    private int httpErrorCode;
    private Object errorResponse;
    private ArrayList<BulkRequestItem> request;

    /**
     * @param httpErrorCode httpErrorCode of the KintoneAPIException
     * @param errorResponse errorResponse of the KintoneAPIException
     * @param cause         cause of the KintoneAPIException
     */
    public KintoneAPIException(int httpErrorCode, Object errorResponse, Throwable cause) {
        super(errorResponse.toString(), cause);
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    /**
     * @param httpErrorCode httpErrorCode of the KintoneAPIException
     * @param errorResponse errorResponse of the KintoneAPIException
     */
    public KintoneAPIException(int httpErrorCode, Object errorResponse) {
        super(errorResponse.toString());
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    public KintoneAPIException(Object errorResponse, Throwable cause) {
        super(errorResponse.toString(), cause);
        this.errorResponse = errorResponse;
    }

    public KintoneAPIException(Object errorResponse) {
        super(errorResponse.toString());
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
    public Object getErrorResponse() {
        if (errorResponse.getClass().getSimpleName().contains("BulksErrorResponse")) {
            BulksErrorResponse bulksErrorResponse = (BulksErrorResponse) errorResponse;
            return bulksErrorResponse;
        } else {
            ErrorResponse response = (ErrorResponse) errorResponse;
            return response;
        }
    }

    @Override
    public String toString() {
        if (errorResponse == null && request == null) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder();
        String className = errorResponse.getClass().getSimpleName();

        switch (className) {
            case "BulksErrorResponse":
                BulksErrorResponse bulksErrorResponse = (BulksErrorResponse) errorResponse;
                ArrayList<Object> errorsList = bulksErrorResponse.getResults();
                Integer count = 1;
                for (Object errorResponse : errorsList) {
                    if (errorResponse != null) {
                        ErrorResponse response = (ErrorResponse) errorResponse;
                        sb.append("api_no: " + count.toString());
                        sb.append(", method: " + request.get(count - 1).getMethod());
                        sb.append(", api_name: " + request.get(count - 1).getApi());
                        sb.append(", id: " + response.getId());
                        sb.append(", code: " + response.getCode());
                        sb.append(", message: " + response.getMessage());
                        sb.append(", errors: " + response.getErrors());
                    }
                    count++;
                }
                sb.append(", status: " + httpErrorCode);
                break;
            case "ErrorResponse":
                ErrorResponse errorResponseObj = (ErrorResponse) errorResponse;
                sb.append("id: " + errorResponseObj.getId());
                sb.append(", code: " + errorResponseObj.getCode());
                sb.append(", message: " + errorResponseObj.getMessage());
                sb.append(", errors: " + errorResponseObj.getErrors());
                sb.append(", status: " + httpErrorCode);
                break;
        }

        return sb.toString();
    }
}

