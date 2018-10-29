/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

public class GetCommentsRecordRequest {

    private Integer app;
    private Integer record;
    private String order;
    private Integer offset;
    private Integer limit;

    /**
     * Constructor
     * @param app
     * @param record
     * @param order
     * @param offset
     * @param limit
     */
    public GetCommentsRecordRequest(Integer app, Integer record, String order, Integer offset, Integer limit) {
        this.app = app;
        this.record = record;
        this.order = order;
        this.offset = offset;
        this.limit = limit;
    }

}

