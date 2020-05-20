package com.cybozu.kintone.client.module.cursor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.cursor.CreateRecordCursorResponse;
import com.cybozu.kintone.client.model.cursor.GetRecordCursorResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.record.Record;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class GetRecordsTest {
    private static Integer APP_ID = 5;

    String apiTokenCanReadRec = TestConstantsSample.API_TOKEN;
    String apiTokenHasNoPermission = TestConstantsSample.HAAPI_TOKEN;

    String nameOfUserHasLimitedPermission = TestConstantsSample.ADMIN_USERNAME;
    String passOfUserHasLimitedPermission = TestConstantsSample.ADMIN_PASSWORD;
    String certPathOfUserHasLimitedPermission = TestConstantsSample.CLIENT_CERT_PATH;
    String certPassOfUserHasLimitedPermission = TestConstantsSample.CLIENT_CERT_PASSWORD;

    String nameOfUserHasNoPermission = TestConstantsSample.BASIC_USERNAME;
    String passOfUserHasNoPermission = TestConstantsSample.BASIC_PASSWORD;
    String certPathOfUserHasNoPermission = TestConstantsSample.HACLIENT_CERT_PATH;
    String certPassOfUserHasNoPermission = TestConstantsSample.HACLIENT_CERT_PASSWORD;

    private RecordCursor passwordAuthRecordCursor;
    private RecordCursor passwordAuthRecordCursorCert;
    private RecordCursor apiTokenAuthRecordCursor;
    private Record recordManagerment;
    private Integer uniqueKey = 1;

    HashMap<String, FieldValue> testRecord1;
    HashMap<String, FieldValue> testRecord2;
    HashMap<String, FieldValue> testRecord3;
    String denyField = "更新者";

    public HashMap<String, FieldValue> addField(HashMap<String, FieldValue> record, String code, FieldType type,
            Object value) {
        FieldValue newField = new FieldValue();
        newField.setType(type);
        newField.setValue(value);
        record.put(code, newField);
        return record;
    }

    public HashMap<String, FieldValue> createTestRecord() {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, this.uniqueKey);
        this.uniqueKey += 1;
        testRecord = addField(testRecord, "リッチエディター", FieldType.RICH_TEXT, "<div>test rich text<br /></div>");

        ArrayList<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("sample1");
        selectedItemList.add("sample2");
        testRecord = addField(testRecord, "チェックボックス", FieldType.CHECK_BOX, selectedItemList);
        testRecord = addField(testRecord, "ラジオボタン", FieldType.RADIO_BUTTON, "sample2");
        testRecord = addField(testRecord, "ドロップダウン", FieldType.DROP_DOWN, "sample2");
        testRecord = addField(testRecord, "複数選択", FieldType.MULTI_SELECT, selectedItemList);
        testRecord = addField(testRecord, "リンク", FieldType.LINK, "http://cybozu.co.jp/");
        testRecord = addField(testRecord, "日付", FieldType.DATE, "2018-01-01");
        testRecord = addField(testRecord, "時刻", FieldType.TIME, "12:34");
        testRecord = addField(testRecord, "日時", FieldType.DATETIME, "2018-01-02T02:30:00Z");

        return testRecord;
    }


    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);
        this.recordManagerment = new Record(passwordAuthConnection);

        Auth passwordAuthCert = new Auth();
        passwordAuthCert.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        passwordAuthCert.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection passwordAuthConnectionCert = new Connection(TestConstantsSample.DOMAIN, passwordAuthCert);
        this.passwordAuthRecordCursorCert = new RecordCursor(passwordAuthConnectionCert);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(apiTokenCanReadRec);
        Connection apiAuthConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        this.apiTokenAuthRecordCursor = new RecordCursor(apiAuthConnection);

        this.testRecord1 = createTestRecord();
        this.testRecord2 = createTestRecord();
        this.testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse result =  this.recordManagerment.getRecords(APP_ID, query, null, true);
        if (result.getRecords().size() == 0) {
            this.recordManagerment.addRecords(APP_ID, records);
        }
    }

    @Test
    // 閲覧権限がないフィールドは返されない
    public void testGetRecordsShouldSuccess() throws KintoneAPIException {

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = this.passwordAuthRecordCursor.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            FieldType type = entry.getValue().getType();
            FieldType expectedType = resultRecords.get(0).get(entry.getKey()).getType();
            assertEquals(type, expectedType);
            Object value = String.valueOf(resultRecords.get(0).get(entry.getKey()).getValue());
            Object expectedValue;
            expectedValue = String.valueOf(entry.getValue().getValue());
            assertEquals(expectedValue, value);
        }
        assertEquals(null, resultRecords.get(0).get(denyField));
    }

    @Test
    // 閲覧権限がないフィールドは返されない
    public void testGetRecordsShouldSuccessCert() throws KintoneAPIException {

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = this.passwordAuthRecordCursorCert.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            FieldType type = entry.getValue().getType();
            FieldType expectedType = resultRecords.get(0).get(entry.getKey()).getType();
            assertEquals(type, expectedType);
            Object value = String.valueOf(resultRecords.get(0).get(entry.getKey()).getValue());
            Object expectedValue;
            expectedValue = String.valueOf(entry.getValue().getValue());
            assertEquals(expectedValue, value);
        }
        assertEquals(null, resultRecords.get(0).get(denyField));
    }

    @Test
    // 閲覧権限がないレコードは返されない
    public void testGetRecordsShouldSuccessWithOneRecord() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(nameOfUserHasLimitedPermission, passOfUserHasLimitedPermission);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = this.passwordAuthRecordCursor.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }

    @Test
    // 閲覧権限がないレコードは返されない
    public void testGetRecordsShouldSuccessWithOneRecordCert() throws KintoneAPIException {
        Auth passwordAuthCert = new Auth();
        passwordAuthCert.setPasswordAuth(nameOfUserHasLimitedPermission, passOfUserHasLimitedPermission);
        passwordAuthCert.setClientCertByPath(certPathOfUserHasLimitedPermission, certPassOfUserHasLimitedPermission);
        Connection passwordAuthConnectionCert = new Connection(TestConstantsSample.DOMAIN, passwordAuthCert);
        RecordCursor passwordAuthRecordCursorCert = new RecordCursor(passwordAuthConnectionCert);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = passwordAuthRecordCursorCert.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }

    @Test
    // 閲覧権限がないレコードは返されない
    public void testGetRecordsShouldSuccessWithOneRecordToken() throws KintoneAPIException {
        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = this.apiTokenAuthRecordCursor.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }

    @Test(expected = KintoneAPIException.class)
    // アプリのアクセス権のレコード閲覧権限がない
    public void testGetRecordsShouldFailWithInvalidUser() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(nameOfUserHasNoPermission, passOfUserHasNoPermission);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        this.passwordAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // アプリのアクセス権のレコード閲覧権限がない
    public void testGetRecordsShouldFailWithInvalidUserCert() throws KintoneAPIException {
        Auth passwordAuthCert = new Auth();
        passwordAuthCert.setPasswordAuth(nameOfUserHasNoPermission, passOfUserHasNoPermission);
        passwordAuthCert.setClientCertByPath(certPathOfUserHasNoPermission, certPassOfUserHasNoPermission);
        Connection passwordAuthConnectionCert = new Connection(TestConstantsSample.DOMAIN, passwordAuthCert);
        RecordCursor passwordAuthRecordCursorCert = new RecordCursor(passwordAuthConnectionCert);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        this.passwordAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // アプリのアクセス権のレコード閲覧権限がない
    public void testGetRecordsShouldFailWithInvalidToken() throws KintoneAPIException {
        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(apiTokenHasNoPermission);
        Connection tokenAuthConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        RecordCursor tokenAuthRecordCursor = new RecordCursor(tokenAuthConnection);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = tokenAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        tokenAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが既に終端まで到達している
    public void testGetRecordsShouldFailWithEmptyCursor() throws KintoneAPIException {

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        this.passwordAuthRecordCursor.getRecords(cursor.getId());
        this.passwordAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが既に終端まで到達している
    public void testGetRecordsShouldFailWithEmptyCursorCert() throws KintoneAPIException {

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        this.passwordAuthRecordCursorCert.getRecords(cursor.getId());
        this.passwordAuthRecordCursorCert.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが既に終端まで到達している
    public void testGetRecordsShouldFailWithEmptyCursorToken() throws KintoneAPIException {

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        this.apiTokenAuthRecordCursor.getRecords(cursor.getId());
        this.apiTokenAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルの作成者が実行ユーザーではない
    public void testGetRecordsShouldFailWithStrangeUser() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(nameOfUserHasLimitedPermission, passOfUserHasLimitedPermission);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        RecordCursor recordCursor = new RecordCursor(passwordAuthConnection);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        recordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルの作成者が実行ユーザーではない
    public void testGetRecordsShouldFailWithStrangeUserCert() throws KintoneAPIException {
        Auth passwordAuthCert = new Auth();
        passwordAuthCert.setPasswordAuth(nameOfUserHasLimitedPermission, passOfUserHasLimitedPermission);
        passwordAuthCert.setClientCertByPath(certPassOfUserHasLimitedPermission, certPassOfUserHasLimitedPermission);
        Connection passwordAuthConnectionCert = new Connection(TestConstantsSample.DOMAIN, passwordAuthCert);
        RecordCursor passwordAuthRecordCursorCert = new RecordCursor(passwordAuthConnectionCert);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        passwordAuthRecordCursorCert.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルの作成者が実行ユーザーではない
    public void testGetRecordsShouldFailWithStrangeUserToken() throws KintoneAPIException {
        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(apiTokenCanReadRec);
        Connection tokenAuthConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        RecordCursor tokenAuthRecordCursor = new RecordCursor(tokenAuthConnection);

        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, 100);
        tokenAuthRecordCursor.getRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが存在しない
    public void testGetRecordsShouldFailWithInvalidCursorId() throws KintoneAPIException {
        this.passwordAuthRecordCursor.getRecords("6");
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが存在しない
    public void testGetRecordsShouldFailWithInvalidCursorIdCert() throws KintoneAPIException {
        this.passwordAuthRecordCursorCert.getRecords("6");
    }

    @Test(expected = KintoneAPIException.class)
    // カーソルが存在しない
    public void testGetRecordsShouldFailWithInvalidCursorIdToken() throws KintoneAPIException {
        this.apiTokenAuthRecordCursor.getRecords("6");
    }
}
