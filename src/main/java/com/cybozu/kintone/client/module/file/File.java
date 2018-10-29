/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.file;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.file.DownloadRequest;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.module.parser.FileParser;
import com.google.gson.JsonElement;

public class File {

    private Connection connection;
    private static final FileParser parser = new FileParser();

    /**
     * Constructor
     * @param connection
     */
    public File(Connection connection) {
        this.connection = connection;
    }

    /**
     * Upload file on kintone.
     * @param filePath
     * @return
     * @throws KintoneAPIException
     */
    public FileModel upload(String filePath) throws KintoneAPIException {
        JsonElement response = this.connection.uploadFile(filePath);
        return (FileModel) parser.parseJson(response, FileModel.class);
    }

    /**
     * Download file from kintone.
     * @param fileKey
     * @param outPutFilePath
     * @throws KintoneAPIException
     */
    public void download(String fileKey, String outPutFilePath) throws KintoneAPIException {
        DownloadRequest request = new DownloadRequest(fileKey);
        String requestBody = parser.parseObject(request);
        this.connection.downloadFile(requestBody, outPutFilePath);
    }

}
