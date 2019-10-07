package com.cybozu.kintone.client.model.app.basic;

public class PreviewApp {
    private Integer app;
    private Integer revision;

    public Integer getApp() {
        return this.app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public PreviewApp(Integer app, Integer revision) {
        this.app = app;
        this.revision = revision;
    }

    public PreviewApp() {
    }

}
