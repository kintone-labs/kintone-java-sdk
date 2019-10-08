/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.comment.response;

public class AddCommentResponse {
    private Integer id;

    /**
     * Get record id
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Set rocord id
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
