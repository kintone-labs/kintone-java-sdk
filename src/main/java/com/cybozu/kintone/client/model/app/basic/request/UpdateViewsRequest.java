package com.cybozu.kintone.client.model.app.basic.request;

import java.util.HashMap;

import com.cybozu.kintone.client.model.app.view.ViewModel;

public class UpdateViewsRequest {
    private Integer app;
    private HashMap<String, ViewModel> views;
    private Integer revision;

    public UpdateViewsRequest(Integer app, HashMap<String, ViewModel> views, Integer revision) {
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

    public HashMap<String, ViewModel> getViews() {
        return views;
    }

    public void setViews(HashMap<String, ViewModel> views) {
        this.views = views;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

}
