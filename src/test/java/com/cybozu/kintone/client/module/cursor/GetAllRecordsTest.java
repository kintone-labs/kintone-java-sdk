package com.cybozu.kintone.client.module.cursor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.record.Record;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class GetAllRecordsTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String API_TOKEN2 = "xxx";
    private static String API_TOKEN3 = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");

    private RecordCursor passwordAuthRecordCursor;
    private RecordCursor restrictedPasswordAuthRecordCursor;
    private RecordCursor noViewPasswordAuthRecordCursor;
    private RecordCursor tokenAuthRecordCursor;
    private RecordCursor tokenAuthRecordCursor2;
    private RecordCursor noViewtokenAuthRecordCursor;
    private RecordCursor certAuthRecordCursor;
    private RecordCursor noViewCertAuthRecordCursor;
    private RecordCursor noViewFieldCertAuthRecordCursor;
    private Record recordManagerment;
    private Record tokenRecordManagerment;
    private Record certRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        passwordAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);
        this.recordManagerment = new Record(passwordAuthConnection);

        Auth restrictedPasswordAuth = new Auth();
        restrictedPasswordAuth.setPasswordAuth("xxx", "xxx");
        Connection restrictedPasswordAuthConnection = new Connection(TestConstantsSample.DOMAIN, restrictedPasswordAuth);
        this.restrictedPasswordAuthRecordCursor = new RecordCursor(restrictedPasswordAuthConnection);

        Auth restrictedPasswordAuth2 = new Auth();
        restrictedPasswordAuth2.setPasswordAuth("xxx", "xxx");
        Connection restrictedPasswordAuthConnection2 = new Connection(TestConstantsSample.DOMAIN, restrictedPasswordAuth2);
        this.noViewPasswordAuthRecordCursor = new RecordCursor(restrictedPasswordAuthConnection2);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        this.tokenAuthRecordCursor = new RecordCursor(tokenConnection);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth2 = new Auth();
        tokenAuth2.setApiToken(API_TOKEN2);
        Connection tokenConnection2 = new Connection(TestConstantsSample.DOMAIN, tokenAuth2);
        this.tokenAuthRecordCursor2 = new RecordCursor(tokenConnection2);

        Auth tokenAuth3 = new Auth();
        tokenAuth3.setApiToken(API_TOKEN3);
        Connection tokenConnection3 = new Connection(TestConstantsSample.DOMAIN, tokenAuth3);
        this.noViewtokenAuthRecordCursor = new RecordCursor(tokenConnection3);

        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certauth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection certConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certauth);
        this.certAuthRecordCursor = new RecordCursor(certConnection);
        this.certRecordManagerment = new Record(certConnection);

        Auth certauth2 = new Auth();
        certauth2.setPasswordAuth("xxx", "xxx");
        certauth2.setClientCertByPath("xxx", "xxx");
        Connection certConnection2 = new Connection(TestConstantsSample.SECURE_DOMAIN, certauth2);
        this.noViewCertAuthRecordCursor = new RecordCursor(certConnection2);

        Auth certauth3 = new Auth();
        certauth3.setPasswordAuth("xxx", "xxx");
        certauth3.setClientCertByPath("xxx", "xxx");
        Connection certConnection3 = new Connection(TestConstantsSample.SECURE_DOMAIN, certauth3);
        this.noViewFieldCertAuthRecordCursor = new RecordCursor(certConnection3);

        // get maximum "数値"field value in all records and set it uniqueKey.
        String query = "order by 数値 desc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, query, 500);

        GetRecordsResponse response = this.passwordAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        this.uniqueKey += Integer.parseInt((String) resultRecords.get(0).get("数値").getValue());
    }

    @Test
    public void testGetAllRecordsCursorShouldSuccess() throws KintoneAPIException {
        // Before processing
        int totalRecordToAdd = 600;
        int limitRecordToAddPerResquest = 100;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd / limitRecordToAddPerResquest)) {
            int j = 0;
            ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
            while (j < limitRecordToAddPerResquest) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                recordsToAdd.add(testRecord);
                j++;
            }
            j = 0;
            this.recordManagerment.addRecords(APP_ID, recordsToAdd);
            i++;
        }
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 500);
        GetRecordsResponse response = this.passwordAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
            for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
            index++;
        }
    }

    @Test
    public void testGetAllRecordsCursorShouldSuccessToken() throws KintoneAPIException {
        // Before processing
        int totalRecordToAdd = 600;
        int limitRecordToAddPerResquest = 100;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd / limitRecordToAddPerResquest)) {
            int j = 0;
            ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
            while (j < limitRecordToAddPerResquest) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                recordsToAdd.add(testRecord);
                j++;
            }
            j = 0;
            this.tokenRecordManagerment.addRecords(APP_ID, recordsToAdd);
            i++;
        }
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.tokenAuthRecordCursor.createCursor(APP_ID, null, query, 500);
        GetRecordsResponse response = this.tokenAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
            for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
            index++;
        }
    }

    @Test
    public void testGetAllRecordsCursorShouldSuccessCert() throws KintoneAPIException {
        // Before processing
        int totalRecordToAdd = 600;
        int limitRecordToAddPerResquest = 100;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd / limitRecordToAddPerResquest)) {
            int j = 0;
            ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
            while (j < limitRecordToAddPerResquest) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                recordsToAdd.add(testRecord);
                j++;
            }
            j = 0;
            this.certRecordManagerment.addRecords(APP_ID, recordsToAdd);
            i++;
        }
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.certAuthRecordCursor.createCursor(APP_ID, null, query, 500);
        GetRecordsResponse response = this.certAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
            for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
            index++;
        }
    }

    @Test
    public void testGetAllRecordsShouldSuccessWhenHasNoPermissionOfField() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.restrictedPasswordAuthRecordCursor.createCursor(APP_ID, null, null,
                null);
        GetRecordsResponse response = this.restrictedPasswordAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        HashMap<String, FieldValue> hashMap = resultRecords.get(0);
        assertFalse(hashMap.containsKey("リッチエディター"));
    }

    @Test
    public void testGetAllRecordsShouldSuccessWhenHasNoPermissionOfFieldCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.noViewFieldCertAuthRecordCursor.createCursor(APP_ID, null, null, null);
        GetRecordsResponse response = this.noViewFieldCertAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        HashMap<String, FieldValue> hashMap = resultRecords.get(0);
        assertFalse(hashMap.containsKey("リッチエディター"));
    }

    @Test
    public void testGetAllRecordsShouldSuccessWhenHasNoPermissionOfRecord() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(14, null, null, null);
        GetRecordsResponse response = this.passwordAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }

    @Test
    public void testGetAllRecordsShouldSuccessWhenHasNoPermissionOfRecordCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.certAuthRecordCursor.createCursor(14, null, null, null);
        GetRecordsResponse response = this.certAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenHasNoPermissionOfRecordInAppSetting() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.noViewPasswordAuthRecordCursor.createCursor(14, null, null, null);
        this.noViewPasswordAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenHasNoPermissionOfRecordInAppSettingToken() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.noViewtokenAuthRecordCursor.createCursor(14, null, null, null);
        this.noViewtokenAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenHasNoPermissionOfRecordInAppSettingCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.noViewCertAuthRecordCursor.createCursor(14, null, null, null);
        this.noViewCertAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenCursorAtEnd() throws KintoneAPIException {
        CreateRecordCursorResponse cursor1 = this.passwordAuthRecordCursor.createCursor(15, null, null, 1);
        this.passwordAuthRecordCursor.getAllRecords(cursor1.getId());
        this.passwordAuthRecordCursor.getAllRecords(cursor1.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenCursorAtEndToken() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.tokenAuthRecordCursor2.createCursor(14, null, null, 1);
        this.tokenAuthRecordCursor2.getAllRecords(cursor.getId());
        this.tokenAuthRecordCursor2.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenCursorAtEndCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.certAuthRecordCursor.createCursor(14, null, null, null);
        this.certAuthRecordCursor.getAllRecords(cursor.getId());
        this.certAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenUserNotSameWithCreator() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(13, null, null, null);
        this.restrictedPasswordAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShouldFailWhenUserNotSameWithCreatorCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.certAuthRecordCursor.createCursor(13, null, null, null);
        this.noViewCertAuthRecordCursor.getAllRecords(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShowFailGivenInvalidCursorID() throws KintoneAPIException {
        this.passwordAuthRecordCursor.getAllRecords("invalid cursor id");
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShowFailGivenInvalidCursorIDToken() throws KintoneAPIException {
        this.tokenAuthRecordCursor.getAllRecords("invalid cursor id");
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsShowFailGivenInvalidCursorIDCert() throws KintoneAPIException {
        this.certAuthRecordCursor.getAllRecords("invalid cursor id");
    }

    public HashMap<String, FieldValue> createTestRecord() {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, this.uniqueKey);
        this.uniqueKey += 1;
        testRecord = addField(testRecord, "文字列__複数行", FieldType.MULTI_LINE_TEXT, "test multi text");
        testRecord = addField(testRecord, "リッチエディター", FieldType.RICH_TEXT, "<div>test rich text<br /></div>");

        ArrayList<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("sample1");
        selectedItemList.add("sample2");
        testRecord = addField(testRecord, "チェックボックス", FieldType.CHECK_BOX, selectedItemList);
        testRecord = addField(testRecord, "ラジオボタン", FieldType.RADIO_BUTTON, "sample2");
        testRecord = addField(testRecord, "ドロップダウン", FieldType.DROP_DOWN, "sample3");
        testRecord = addField(testRecord, "複数選択", FieldType.MULTI_SELECT, selectedItemList);
        testRecord = addField(testRecord, "リンク", FieldType.LINK, "http://cybozu.co.jp/");
        testRecord = addField(testRecord, "日付", FieldType.DATE, "2018-01-01");
        testRecord = addField(testRecord, "時刻", FieldType.TIME, "12:34");
        testRecord = addField(testRecord, "日時", FieldType.DATETIME, "2018-01-02T02:30:00Z");

        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(testman1);
        userList.add(testman2);
        addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
        ArrayList<Member> groupList = new ArrayList<Member>();
        groupList.add(testgroup1);
        groupList.add(testgroup2);
        addField(testRecord, "グループ選択", FieldType.GROUP_SELECT, groupList);
        return testRecord;
    }

    public HashMap<String, FieldValue> addField(HashMap<String, FieldValue> record, String code, FieldType type,
            Object value) {
        FieldValue newField = new FieldValue();
        newField.setType(type);
        newField.setValue(value);
        record.put(code, newField);
        return record;
    }
}
