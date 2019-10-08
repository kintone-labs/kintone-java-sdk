package com.cybozu.kintone.client.model.app.basic.response;

import com.cybozu.kintone.client.model.app.views.View;

import java.util.HashMap;

public class GetViewsResponse {
    private Integer revision;
    private HashMap<String, View> views;

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public HashMap<String, View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, View> views) {
        this.views = views;
    }
}
