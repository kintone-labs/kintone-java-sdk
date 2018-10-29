/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment;

public class AddCommentRecordRequest {

    private Integer app;
    private Integer record;
    private CommentContent comment;

    /**
     * Constructor
     * @param app
     * @param record
     * @param comment
     */
    public AddCommentRecordRequest(Integer app, Integer record, CommentContent comment) {
        this.app = app;
        this.record = record;
        this.comment = comment;
    }
}
