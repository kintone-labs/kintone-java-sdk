/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.basic.request;

import com.cybozu.kintone.client.model.app.LanguageSetting;

public class GetFormFieldsRequest {

    private Integer app;
    private LanguageSetting lang;

    public GetFormFieldsRequest(Integer app, LanguageSetting lang) {
        this.app = app;
        this.lang = lang;
    }

}
