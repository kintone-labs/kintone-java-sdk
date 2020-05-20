package com.cybozu.kintone.client.module.file;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.record.Record;

public class FileTest {
    private static int APP_ID;
    private static int RESTRICT_APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_DOWNLOAD_PERMISSION_API_TOKEN = "xxx";

    private Connection connection;
    private Connection tokenConnection;
    private Connection certConnection;
    private Connection noPermissionTokenConnection;
    private File file;
    private File tokenFile;
    private File certFile;
    private File noDownloadPermissionTokenFile;

    @Before
    public void setup() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        this.connection = new Connection(TestConstantsSample.DOMAIN, auth);
        this.connection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.file = new File(this.connection);

        Auth tokenauth = new Auth();
        tokenauth.setApiToken(API_TOKEN);
        this.tokenConnection = new Connection(TestConstantsSample.DOMAIN, tokenauth);
        this.tokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.tokenFile = new File(this.tokenConnection);

        Auth noPermissiontokenauth = new Auth();
        noPermissiontokenauth.setApiToken(NO_DOWNLOAD_PERMISSION_API_TOKEN);
        this.noPermissionTokenConnection = new Connection(TestConstantsSample.DOMAIN, noPermissiontokenauth);
        this.noPermissionTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.noDownloadPermissionTokenFile = new File(this.noPermissionTokenConnection);

        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        this.certConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certAuth);
        this.certConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.certFile = new File(this.certConnection);
    }

    @Test
    public void testUploadSuccessForSingleFile() throws KintoneAPIException {
        HashMap<String, FieldValue> addRecord = new HashMap<>();
        Record recordManagement = new Record(connection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, addRecord);
        Integer id = addResponse.getID();

        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");

        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);

        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForSingleFileToken() throws KintoneAPIException {
        HashMap<String, FieldValue> addRecord = new HashMap<>();
        Record recordManagement = new Record(tokenConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, addRecord);
        Integer id = addResponse.getID();

        FileModel fileModel = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");

        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);

        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForSingleFileCert() throws KintoneAPIException {
        HashMap<String, FieldValue> addRecord = new HashMap<>();
        Record recordManagement = new Record(certConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, addRecord);
        Integer id = addResponse.getID();

        FileModel fileModel = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");

        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);

        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    // Java Memory Error
    @Ignore
    @Test
    public void testUploadSuccessForSingleOver1GBFile() throws KintoneAPIException {
        FileModel fileModel = this.file.upload("xxxxxxxxxxxxxxxxxxxxxxx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(connection);
        recordManagement.updateRecordByID(APP_ID, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUploadFailForNonExistPath() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "1");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(connection);
        recordManagement.updateRecordByID(APP_ID, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUploadFailForNonExistPathToken() throws KintoneAPIException {
        FileModel fileModel = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "1");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(tokenConnection);
        recordManagement.updateRecordByID(APP_ID, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUploadFailForNonExistPathCert() throws KintoneAPIException {
        FileModel fileModel = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "1");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(certConnection);
        recordManagement.updateRecordByID(APP_ID, 1, testRecord, null);
    }

    @Test
    public void testUploadSuccessForMultiFileInMultiClass() throws KintoneAPIException {
        FileModel fileModel1 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(fileModel2);
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.FILE);
        fv2.setValue(al2);

        ArrayList<FileModel> al3 = new ArrayList<>();
        al3.add(fileModel3);
        FieldValue fv3 = new FieldValue();
        fv3.setType(FieldType.FILE);
        fv3.setValue(al3);

        testRecord.put("attachment1", fv);
        testRecord.put("attachment2", fv2);
        testRecord.put("attachment3", fv3);
        Record recordManagement = new Record(connection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForMultiFileInMultiClassToken() throws KintoneAPIException {
        FileModel fileModel1 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(fileModel2);
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.FILE);
        fv2.setValue(al2);

        ArrayList<FileModel> al3 = new ArrayList<>();
        al3.add(fileModel3);
        FieldValue fv3 = new FieldValue();
        fv3.setType(FieldType.FILE);
        fv3.setValue(al3);

        testRecord.put("attachment1", fv);
        testRecord.put("attachment2", fv2);
        testRecord.put("attachment3", fv3);
        Record recordManagement = new Record(tokenConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForMultiFileInMultiClassCert() throws KintoneAPIException {
        FileModel fileModel1 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(fileModel2);
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.FILE);
        fv2.setValue(al2);

        ArrayList<FileModel> al3 = new ArrayList<>();
        al3.add(fileModel3);
        FieldValue fv3 = new FieldValue();
        fv3.setType(FieldType.FILE);
        fv3.setValue(al3);

        testRecord.put("attachment1", fv);
        testRecord.put("attachment2", fv2);
        testRecord.put("attachment3", fv3);
        Record recordManagement = new Record(certConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForMultiFileInOneClass() throws KintoneAPIException {
        FileModel fileModel1 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        al.add(fileModel2);
        al.add(fileModel3);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(connection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(3, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForMultiFileInOneClassToken() throws KintoneAPIException {

        FileModel fileModel1 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.tokenFile.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        al.add(fileModel2);
        al.add(fileModel3);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(tokenConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(3, alf.size());
            }
        }
    }

    @Test
    public void testUploadSuccessForMultiFileInOneClassCert() throws KintoneAPIException {

        FileModel fileModel1 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.certFile.upload(TestConstantsSample.UPLOAD_PATH + "test.xlsx");
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        ArrayList<FileModel> al = new ArrayList<>();
        al.add(fileModel1);
        al.add(fileModel2);
        al.add(fileModel3);

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.FILE);
        fv.setValue(al);

        testRecord.put("attachment1", fv);
        Record recordManagement = new Record(certConnection);
        AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, new HashMap<>());
        Integer id = addResponse.getID();
        recordManagement.updateRecordByID(APP_ID, id, testRecord, null);

        GetRecordResponse recordResponse = recordManagement.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = recordResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(3, alf.size());
            }
        }
    }

    @Test
    public void testUploadFailForUnexistFileKey() throws KintoneAPIException {
        try {
            // ファイルのアップロード
            this.file.upload("xxxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (KintoneAPIException ke) {
            assertEquals("cannot open file", ke.getMessage());
        }
    }

    @Test
    public void testUploadFailForUnexistFileKeyToken() throws KintoneAPIException {
        try {
            // ファイルのアップロード
            this.tokenFile.upload("xxxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (KintoneAPIException ke) {
            assertEquals("cannot open file", ke.getMessage());
        }
    }

    @Test
    public void testUploadFailForUnexistFileKeyCert() throws KintoneAPIException {
        try {
            // ファイルのアップロード
            this.certFile.upload("xxxxxxxxxxxxxxxxxxxxxxxxx");
        } catch (KintoneAPIException ke) {
            assertEquals("cannot open file", ke.getMessage());
        }
    }

    @Test
    public void testDownloadSuccessForSingleFile() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        FileModel fdata = fileList.get(0);
        this.file.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test
    public void testDownloadSuccessForSingleFileToken() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(tokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        FileModel fdata = fileList.get(0);
        this.tokenFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test
    public void testDownloadSuccessForSingleFileCert() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        FileModel fdata = fileList.get(0);
        this.certFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test(expected = KintoneAPIException.class)
    public void testDownloadFailForNoPermission() throws KintoneAPIException {
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(RESTRICT_APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        FileModel fdata = fileList.get(0);
        this.file.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test(expected = KintoneAPIException.class)
    public void testDownloadFailForNoPermissionToken() throws KintoneAPIException {
        Record record = new Record(noPermissionTokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        FileModel fdata = fileList.get(0);
        noDownloadPermissionTokenFile.download(fdata.getFileKey(),
                TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test(expected = KintoneAPIException.class)
    public void testDownloadFailForNoPermissionCert() throws KintoneAPIException {
        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(RESTRICT_APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        FileModel fdata = fileList.get(0);
        File noPermissionCertFile = new File(certConnection);
        noPermissionCertFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test
    public void testDownloadSuccessForMultiFile() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        for (int i = 0; i < fileList.size(); i++) {
            FileModel fdata = fileList.get(i);
            this.file.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "2/" + i + ".txt");
        }
    }

    @Test
    public void testDownloadSuccessForMultiFileToken() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(tokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        for (int i = 0; i < fileList.size(); i++) {
            FileModel fdata = fileList.get(i);
            this.tokenFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "2/" + i + ".txt");
        }
    }

    @Test
    public void testDownloadSuccessForMultiFileCert() throws KintoneAPIException {
        // レコード取得
        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();
        // ファイルダウンロード
        for (int i = 0; i < fileList.size(); i++) {
            FileModel fdata = fileList.get(i);
            this.certFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "2/" + i + ".txt");
        }
    }

    @Test
    public void testDownloadFailForUnexistFileKey() throws KintoneAPIException {
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            // ファイルのダウンロード
            FileModel fdata = fileList.get(0);
            this.file.download("12345678910abcdefghijklmnopqrstuvwxyz",
                    TestConstantsSample.DOWNLOAD_PATH + "2/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: 12345678910abcdefghijklmnopqrstuvwxyz）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForUnexistFileKeyToken() throws KintoneAPIException {
        Record record = new Record(tokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            // ファイルのダウンロード
            FileModel fdata = fileList.get(0);
            this.tokenFile.download("12345678910abcdefghijklmnopqrstuvwxyz",
                    TestConstantsSample.DOWNLOAD_PATH + "2/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: 12345678910abcdefghijklmnopqrstuvwxyz）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForUnexistFileKeyCert() throws KintoneAPIException {

        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 3);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            // ファイルのダウンロード
            FileModel fdata = fileList.get(0);
            this.certFile.download("12345678910abcdefghijklmnopqrstuvwxyz",
                    TestConstantsSample.DOWNLOAD_PATH + "2/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: 12345678910abcdefghijklmnopqrstuvwxyz）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNoFileKey() throws KintoneAPIException {
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            this.file.download(null, TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: null）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNoFileKeyToken() throws KintoneAPIException {
        Record record = new Record(tokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            tokenFile.download(null, TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: null）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNoFileKeyCert() throws KintoneAPIException {
        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            this.certFile.download(null, TestConstantsSample.DOWNLOAD_PATH + "1/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("指定したファイル（id: null）が見つかりません。", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNonExistPath() throws KintoneAPIException {
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            this.file.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "3/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("an error occurred while receiving data", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNonExistPathToken() throws KintoneAPIException {
        Record record = new Record(tokenConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            this.tokenFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "3/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("an error occurred while receiving data", ke.getMessage());
        }
    }

    @Test
    public void testDownloadFailForNonExistPathCert() throws KintoneAPIException {
        Record record = new Record(certConnection);
        GetRecordResponse recordJson = record.getRecord(APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        try {
            FileModel fdata = fileList.get(0);
            this.certFile.download(fdata.getFileKey(), TestConstantsSample.DOWNLOAD_PATH + "3/" + fdata.getName());
        } catch (KintoneAPIException ke) {
            assertEquals("an error occurred while receiving data", ke.getMessage());
        }
    }
}
