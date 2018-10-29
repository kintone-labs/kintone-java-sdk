/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.app;

import java.util.List;

public class GetAppsRequest {

    private List<Integer> ids;
    private List<String> codes;
    private String name;
    private List<Integer> spaceIds;
    private Integer offset;
    private Integer limit;

    public GetAppsRequest(List<Integer> ids, List<String> codes, String name, List<Integer> spaceIds, Integer offset,
            Integer limit) {
        this.ids = ids;
        this.codes = codes;
        this.name = name;
        this.spaceIds = spaceIds;
        this.offset = offset;
        this.limit = limit;
    }
}
