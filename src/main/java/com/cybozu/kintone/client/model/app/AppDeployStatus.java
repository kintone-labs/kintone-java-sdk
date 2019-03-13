package com.cybozu.kintone.client.model.app;

public class AppDeployStatus {
    private Integer app;
    private Status status;

    public enum Status {
        PROCESSING, SUCCESS, FAIL, CANCEL
    }

    public Integer getApp() {
        return this.app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AppDeployStatus(Integer app, Status status) {
        this.app = app;
        this.status = status;
    }

    public AppDeployStatus() {

    }

}
