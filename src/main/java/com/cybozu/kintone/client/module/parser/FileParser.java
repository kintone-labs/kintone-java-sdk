/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.parser;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class FileParser {

    private static final Gson gson = new Gson();

    /**
     * Convert Json to designated class
     * @param json
     * @param type
     * @return
     * @throws KintoneAPIException
     */
    public Object parseJson(JsonElement json, Class<?> type) throws KintoneAPIException {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }

    /**
     * Convert Object to jsonString
     * @param obj
     * @return
     * @throws KintoneAPIException
     */
    public String parseObject(Object obj) throws KintoneAPIException {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }

}
