/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
     * @param connection connection of the File
     */
    public File(Connection connection) {
        this.connection = connection;
    }

    /**
     * Upload file on kintone.
     * @param filePath filePath of the upload
     * @return FileModel
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public FileModel upload(String filePath) throws KintoneAPIException {
        InputStream fis = null;
        String fileName;
        try {
            java.io.File uploadFile = new java.io.File(filePath);
            fileName = uploadFile.getName();
            fis = new FileInputStream(uploadFile.getAbsolutePath());
        } catch (FileNotFoundException e1) {
            throw new KintoneAPIException("cannot open file");
        }
        JsonElement response = this.connection.uploadFile(fileName, fis);
        return (FileModel) parser.parseJson(response, FileModel.class);
    }

    /**
     * Download file from kintone.
     * @param fileKey fileKey of the download
     * @param outPutFilePath outPutFilePath of the download
     * @throws KintoneAPIException
     *           the KintoneAPIException to throw
     */
    public void download(String fileKey, String outPutFilePath) throws KintoneAPIException {
        DownloadRequest request = new DownloadRequest(fileKey);
        String requestBody = parser.parseObject(request);
        InputStream is = this.connection.downloadFile(requestBody);
        try {
            try {
                if (outPutFilePath != null) {
                    OutputStream fos = new FileOutputStream(outPutFilePath);
                    try {
                        byte[] buffer = new byte[8192];
                        int n = 0;
                        while (-1 != (n = is.read(buffer))) {
                            fos.write(buffer, 0, n);
                        }
                    } finally {
                        fos.close();
                    }
                }
            } finally {
                is.close();
            }
        } catch (Exception e) {
            throw new KintoneAPIException("an error occurred while receiving data");
        }
    }

}
