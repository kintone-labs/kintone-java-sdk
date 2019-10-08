package com.cybozu.kintone.client.model.app.basic.request;

import com.cybozu.kintone.client.model.app.views.View;

import java.util.HashMap;

public class UpdateViewsRequest {
    private Integer app;
    private HashMap<String, View> views;
    private Integer revision;

    public UpdateViewsRequest(Integer app, HashMap<String, View> views, Integer revision) {
        this.app = app;
        this.views = views;
        this.revision = revision;
    }

    public Integer getApp() {
        return app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public HashMap<String, View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, View> views) {
        this.views = views;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

}
