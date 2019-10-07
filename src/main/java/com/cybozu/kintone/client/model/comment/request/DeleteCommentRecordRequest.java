/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

public class DeleteCommentRecordRequest {
    private Integer app;
    private Integer record;
    private Integer comment;

    /**
     * Constructor
     * @param app app of the DeleteCommentRecordRequest
     * @param record record of the DeleteCommentRecordRequest
     * @param comment comment of the DeleteCommentRecordRequest
     */
    public DeleteCommentRecordRequest(Integer app, Integer record, Integer comment) {

        this.app = app;
        this.record = record;
        this.comment = comment;
    }
}
