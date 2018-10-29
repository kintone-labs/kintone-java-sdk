/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.model.file;

public class DownloadRequest {

    private String fileKey;

    /**
     * Constructor
     * @param fileKey
     */
    public DownloadRequest(String fileKey) {
        this.fileKey = fileKey;
    }

}
