/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

public class GetAppsRequest {

    private ArrayList<Integer> ids;
    private ArrayList<String> codes;
    private String name;
    private ArrayList<Integer> spaceIds;
    private Integer offset;
    private Integer limit;

    public GetAppsRequest(ArrayList<Integer> ids, ArrayList<String> codes, String name, ArrayList<Integer> spaceIds,
                          Integer offset, Integer limit) {
        this.ids = ids;
        this.codes = codes;
        this.name = name;
        this.spaceIds = spaceIds;
        this.offset = offset;
        this.limit = limit;
    }
}
