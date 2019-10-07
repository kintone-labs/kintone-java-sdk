package com.cybozu.kintone.client.model.app.basic.response;

import java.util.HashMap;

public class UpdateViewsResponse {
    private Integer revision;
    private HashMap<String, com.cybozu.kintone.client.model.app.view.View> views;

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public HashMap<String, com.cybozu.kintone.client.model.app.view.View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, com.cybozu.kintone.client.model.app.view.View> views) {
        this.views = views;
    }

}
