package com.cybozu.kintone.client.model.app.basic.response;

import java.util.HashMap;

import com.cybozu.kintone.client.model.app.view.ViewModel;

public class GetViewsResponse {
    private Integer revision;
    private HashMap<String, ViewModel> views;

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public HashMap<String, ViewModel> getViews() {
        return views;
    }

    public void setViews(HashMap<String, ViewModel> views) {
        this.views = views;
    }
}
