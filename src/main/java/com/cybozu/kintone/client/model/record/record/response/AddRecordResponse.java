/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.record.record.response;

import com.cybozu.kintone.client.model.app.basic.response.BasicResponse;

public class AddRecordResponse extends BasicResponse {

    private Integer id;

    /**
     * Get record id
     *
     * @return id
     */
    public Integer getID() {
        return this.id;
    }

    /**
     * Set record id
     *
     * @param id the id to set
     */
    public void setID(Integer id) {
        this.id = id;
    }

}
