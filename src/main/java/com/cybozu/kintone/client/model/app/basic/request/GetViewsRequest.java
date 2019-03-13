package com.cybozu.kintone.client.model.app.basic.request;

import com.cybozu.kintone.client.model.app.LanguageSetting;

public class GetViewsRequest {
    private Integer app;
    private LanguageSetting lang;

    public GetViewsRequest(Integer app, LanguageSetting lang) {
        this.app = app;
        this.lang = lang;
    }

    public Integer getApp() {
        return app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public LanguageSetting getLang() {
        return lang;
    }

    public void setLang(LanguageSetting lang) {
        this.lang = lang;
    }
}
