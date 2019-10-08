/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment.request;

public class GetCommentsRecordRequest {

    private Integer app;
    private Integer record;
    private String order;
    private Integer offset;
    private Integer limit;

    /**
     * Constructor
     * @param app app of the GetCommentsRecordRequest
     * @param record record of the GetCommentsRecordRequest
     * @param order order of the GetCommentsRecordRequest
     * @param offset offset of the GetCommentsRecordRequest
     * @param limit limit of the GetCommentsRecordRequest
     */
    public GetCommentsRecordRequest(Integer app, Integer record, String order, Integer offset, Integer limit) {
        this.app = app;
        this.record = record;
        this.order = order;
        this.offset = offset;
        this.limit = limit;
    }

}

