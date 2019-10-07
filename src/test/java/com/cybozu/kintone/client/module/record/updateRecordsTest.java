package com.cybozu.kintone.client.module.record;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.*;
import com.cybozu.kintone.client.model.record.record.response.*;
import com.cybozu.kintone.client.module.file.File;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;

public class updateRecordsTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String PROHIBIT_DUPLICATE_API_TOKEN = "xxx";
    private static String REQUIRED_FIELD_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noAddPermissionTokenReocrdManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record prohibitDuplicateTokenRecordManagerment;
    private Record requiredFieldTokenRecordManagerment;
    private Record tokenGuestRecordManagerment;
    private Record certRecordManagerment;
    private Record certGuestRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        //passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth3 = new Auth();
        tokenAuth3.setApiToken(PROHIBIT_DUPLICATE_API_TOKEN);
        Connection tokenConnection3 = new Connection(TestConstants.DOMAIN, tokenAuth3);
        this.prohibitDuplicateTokenRecordManagerment = new Record(tokenConnection3);

        Auth tokenAuth4 = new Auth();
        tokenAuth4.setApiToken(REQUIRED_FIELD_API_TOKEN);
        Connection tokenConnection4 = new Connection(TestConstants.DOMAIN, tokenAuth4);
        this.requiredFieldTokenRecordManagerment = new Record(tokenConnection4);

        Auth tokenAuth5 = new Auth();
        tokenAuth5.setApiToken(NO_ADD_PERMISSION_API_TOKEN);
        Connection tokenConnection5 = new Connection(TestConstants.DOMAIN, tokenAuth5);
        this.noAddPermissionTokenReocrdManagerment = new Record(tokenConnection5);

        Auth tokenAuth6 = new Auth();
        tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
        Connection tokenConnection6 = new Connection(TestConstants.DOMAIN, tokenAuth6);
        this.addNoViewTokenRecordManagerment = new Record(tokenConnection6);

        Auth tokenGuestAuth = new Auth();
        tokenGuestAuth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection tokenGuestConnection = new Connection(TestConstants.DOMAIN, tokenGuestAuth,
                TestConstants.GUEST_SPACE_ID);
        this.tokenGuestRecordManagerment = new Record(tokenGuestConnection);

        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection certConnection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
        this.certRecordManagerment = new Record(certConnection);

        Auth certGuestAuth = new Auth();
        certGuestAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certGuestAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection CertGuestConnection = new Connection(TestConstants.SECURE_DOMAIN, certGuestAuth,
                TestConstants.GUEST_SPACE_ID);
        this.certGuestRecordManagerment = new Record(CertGuestConnection);

        // get maximum "数値"field value in all records and set it uniqueKey.
        String query = "order by 数値 desc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        this.uniqueKey += Integer.parseInt((String) resultRecords.get(0).get("数値").getValue());
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
        ArrayList<Member> orgList = new ArrayList<Member>();
        orgList.add(testorg1);
        orgList.add(testorg2);
        addField(testRecord, "組織選択", FieldType.ORGANIZATION_SELECT, orgList);
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

    @Test
    public void testUpdateRecords() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKey() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKeyToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKeyCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtcToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtcCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.noAddPermissionTokenReocrdManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.addNoViewTokenRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "数值", FieldType.NUMBER, 123);
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "数值", FieldType.NUMBER, 123);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1635, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "数值", FieldType.NUMBER, 123);
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "数值", FieldType.NUMBER, 123);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1635, updateItems);
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkip() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkipToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkipCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateRecordsWithAttachment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    @SuppressWarnings({ "unchecked", "unused" })
    public void testUpdateRecordsWithAttachmentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateRecordsWithAttachmentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);

        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateRecordsDataWithTable() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
        tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue1);
        subTable1.add(tablelist1);

        ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist2 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
        tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList2 = new ArrayList<Member>();
        userList2.add(new Member("cyuan", "cyuan"));
        tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
        tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist2.setID(1);
        tablelist2.setValue(tableitemvalue2);
        subTable2.add(tablelist2);
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    @SuppressWarnings({ "unchecked", "unused" })
    public void testUpdateRecordsDataWithTableToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
        tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue1);
        subTable1.add(tablelist1);

        ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist2 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
        tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList2 = new ArrayList<Member>();
        userList2.add(new Member("cyuan", "cyuan"));
        tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
        tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist2.setID(1);
        tablelist2.setValue(tableitemvalue2);
        subTable2.add(tablelist2);
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateRecordsDataWithTableCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
        tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue1);
        subTable1.add(tablelist1);

        ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist2 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
        tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList2 = new ArrayList<Member>();
        userList2.add(new Member("cyuan", "cyuan"));
        tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
        tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tablelist2.setID(1);
        tablelist2.setValue(tableitemvalue2);
        subTable2.add(tablelist2);
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    public void tesUpdateRecordsInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(360, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecords(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void tesUpdateRecordsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenGuestRecordManagerment.addRecords(360, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenGuestRecordManagerment.updateRecords(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void tesUpdateRecordsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(360, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecords(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.prohibitDuplicateTokenRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.prohibitDuplicateTokenRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximumcert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.requiredFieldTokenRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumber() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumberCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItems() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItemsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        this.tokenRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItemsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        this.certRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(null, updateItems);
    }
}
