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
            BulkErrorResponse bulkError = (BulkErrorResponse)bulkResponse;
            for(Object bulkRequestSingleResponse: bulkError.getResults()) {
                KintoneAPIException bulkRequestError = (KintoneAPIException)bulkRequestSingleResponse;
                if(bulkRequestError.getMessage() == "") {
                    failedBulkRequestIndex++;
                } else {
                    sb.append("bulk_request_index: " + failedBulkRequestIndex.toString());
                    sb.append(", " + bulkRequestError.toString());
                }
            }
        }
        return sb.toString();
    }
}
