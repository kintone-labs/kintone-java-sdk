package com.cybozu.kintone.client.module.file;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
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
    private static String USERNAME = "xxxxx";
    private static String PASSWORD = "xxxxx";
    private static int APP_ID = 1651;
    private static int RESTRICT_APP_ID = 1654;
    private static String UPLOAD_PATH = "src/test/resources/file/Upload/";
    private static String DOWNLOAD_PATH = "src/test/resources/file/Download/";

    private Connection connection;
    private File file;


    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        this.connection = new Connection(TestConstants.DOMAIN, auth);
        this.connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.file = new File(this.connection);
    }

    @Test
    public void testUploadSuccessForSingleFile() throws KintoneAPIException {
    	HashMap<String, FieldValue> addRecord = new HashMap<>();
    	Record recordManagement = new Record(connection);
    	AddRecordResponse addResponse = recordManagement.addRecord(APP_ID, addRecord);
    	Integer id = addResponse.getID();

    	FileModel fileModel = this.file.upload(UPLOAD_PATH + "test.txt");

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
        assertNotNull(fileModel.getFileKey());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUploadFailForNonExistPath() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(UPLOAD_PATH + "1");
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

    @Test
    public void testUploadSuccessForMultiFileInMultiClass() throws KintoneAPIException {

        FileModel fileModel1 = this.file.upload(UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.file.upload(UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.file.upload(UPLOAD_PATH + "test.xlsx");
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
    public void testUploadSuccessForMultiFileInOneClass() throws KintoneAPIException {

        FileModel fileModel1 = this.file.upload(UPLOAD_PATH + "test.txt");
        FileModel fileModel2 = this.file.upload(UPLOAD_PATH + "test.pptx");
        FileModel fileModel3 = this.file.upload(UPLOAD_PATH + "test.xlsx");
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
    public void testUploadFailForUnexistFileKey() throws KintoneAPIException {

        try {
            // ファイルのアップロード
            this.file.upload("xxxxxxxxxxxxxxxxxxxxxxxxx");
        } catch(KintoneAPIException ke) {
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
        File file = new File(connection);
        file.download(fdata.getFileKey(), DOWNLOAD_PATH + "1/" + fdata.getName());
    }

    @Test(expected = KintoneAPIException.class)
    public void testDownloadFailForNoPermission() throws KintoneAPIException {
        Record record = new Record(connection);
        GetRecordResponse recordJson = record.getRecord(RESTRICT_APP_ID, 1);
        HashMap<String, FieldValue> recordVal = recordJson.getRecord();
        FieldValue fileVal = recordVal.get("attachment1");
        ArrayList<FileModel> fileList = (ArrayList<FileModel>) fileVal.getValue();

        FileModel fdata = fileList.get(0);
        File file = new File(connection);
        file.download(fdata.getFileKey(), DOWNLOAD_PATH + "1/" + fdata.getName());
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
            this.file.download(fdata.getFileKey(), DOWNLOAD_PATH + "2/" + i + ".txt");
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
            this.file.download("12345678910abcdefghijklmnopqrstuvwxyz", DOWNLOAD_PATH + "2/" + fdata.getName());
        } catch(KintoneAPIException ke) {
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
            File file = new File(connection);
            file.download(null, DOWNLOAD_PATH + "1/" + fdata.getName());
        } catch(KintoneAPIException ke) {
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
            File file = new File(connection);
            file.download(fdata.getFileKey(), DOWNLOAD_PATH + "3/" + fdata.getName());
        } catch(KintoneAPIException ke) {
            assertEquals("an error occurred while receiving data", ke.getMessage());
        }
    }

}
