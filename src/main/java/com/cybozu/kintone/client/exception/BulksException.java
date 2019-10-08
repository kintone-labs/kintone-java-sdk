package com.cybozu.kintone.client.exception;

import java.util.ArrayList;

/**
 * Exception will occur when using multiple bulk request.
 */
public class BulksException extends Exception {
    private static final long serialVersionUID = 1L;
    private ArrayList<Object> responses;

    public BulksException(ArrayList<Object> object) {
        this.responses = object;
    }

    public ArrayList<Object> getResponses() {
        return responses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Integer failedBulkRequestIndex = 1;
        for (Object bulkResponse : responses) {
            if (bulkResponse.getClass().getSimpleName().equals("KintoneAPIException")) {
                KintoneAPIException bulkError = (KintoneAPIException) bulkResponse;
                sb.append("BulkRequest index: " + failedBulkRequestIndex.toString());
                sb.append(", " + bulkError.toString());
            } else {
                failedBulkRequestIndex++;
            }
        }
        return sb.toString();
    }
}
