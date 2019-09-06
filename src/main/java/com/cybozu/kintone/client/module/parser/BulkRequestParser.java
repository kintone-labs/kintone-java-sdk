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

public class BulkRequestParser {

    private static final Gson gson = new Gson();

    /**
     * Convert Json to designated class
     * @param json json of the parseJson
     * @param type type of the parseJson
     * @return Object
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public Object parseJson(JsonElement json, Class<?> type) throws KintoneAPIException {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error", e);
        }
    }

    /**
     * Convert Object to jsonString
     * @param obj obj of the parseObject
     * @return String
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public String parseObject(Object obj) throws KintoneAPIException {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error", e);
        }
    }

}
