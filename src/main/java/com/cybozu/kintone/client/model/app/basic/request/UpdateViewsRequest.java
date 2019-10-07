package com.cybozu.kintone.client.model.app.basic.request;

import java.util.HashMap;

public class UpdateViewsRequest {
    private Integer app;
    private HashMap<String, com.cybozu.kintone.client.model.app.view.View> views;
    private Integer revision;

    public UpdateViewsRequest(Integer app, HashMap<String, com.cybozu.kintone.client.model.app.view.View> views, Integer revision) {
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

    public HashMap<String, com.cybozu.kintone.client.model.app.view.View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, com.cybozu.kintone.client.model.app.view.View> views) {
        this.views = views;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

}
