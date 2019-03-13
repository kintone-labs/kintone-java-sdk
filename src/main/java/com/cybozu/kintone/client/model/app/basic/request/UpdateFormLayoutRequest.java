package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;

public class UpdateFormLayoutRequest {
    private Integer app;
    private Integer revision;
    private ArrayList<ItemLayout> layout;

    public Integer getApp() {
        return this.app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public ArrayList<ItemLayout> getLayout() {
        return this.layout;
    }

    public void setLayout(ArrayList<ItemLayout> layout) {
        this.layout = layout;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public UpdateFormLayoutRequest(Integer app, ArrayList<ItemLayout> layout, Integer revision) {
        this.app = app;
        this.layout = layout;
        this.revision = revision;
    }
}
