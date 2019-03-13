
/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */


package com.cybozu.kintone.client.model.app.basic.response;

public class BasicResponse {
    private Integer revision;

    public BasicResponse() {
    }

    public BasicResponse(Integer revision) {
        this.revision = revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
    
    public Integer getRevision() {
        return this.revision;
    }

}
