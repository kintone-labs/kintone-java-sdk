package com.cybozu.kintone.client.model.app.basic.request;

public class AddPreviewAppRequest {
    private String name;
    private Integer space;
    private Integer thread;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpace() {
        return this.space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    public Integer getThread() {
        return this.thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    public AddPreviewAppRequest(String name, Integer space, Integer thread) {
        this.name = name;
        this.space = space;
        this.thread = thread;
    }
}
