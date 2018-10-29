/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app;

public class GetFormFieldsRequest {

    private Integer app;
    private LanguageSetting lang;

    public GetFormFieldsRequest(Integer app, LanguageSetting lang) {
        this.app = app;
        this.lang = lang;
    }

}
