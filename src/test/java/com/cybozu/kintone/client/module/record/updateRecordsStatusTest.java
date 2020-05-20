package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateResponseItem;
import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class updateRecordsStatusTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noViewPermissionTokenRecordManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
    private Record tokenGuestRecordManagerment;
    private Record certRecordManagerment;
    private Record certGuestRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        //passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);

        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection gusetConnection = new Connection(TestConstantsSample.DOMAIN, guestAuth, TestConstantsSample.GUEST_SPACE_ID);
        this.guestAuthRecordManagerment = new Record(gusetConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth1 = new Auth();
        tokenAuth1.setApiToken(NO_VIEW_PERMISSION_API_TOKEN);
        Connection tokenConnection1 = new Connection(TestConstantsSample.DOMAIN, tokenAuth1);
        this.noViewPermissionTokenRecordManagerment = new Record(tokenConnection1);

        Auth tokenAuth6 = new Auth();
        tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
        Connection tokenConnection6 = new Connection(TestConstantsSample.DOMAIN, tokenAuth6);
        this.addNoViewTokenRecordManagerment = new Record(tokenConnection6);

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstantsSample.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

        Auth tokenGuestAuth = new Auth();
        tokenGuestAuth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection tokenGuestConnection = new Connection(TestConstantsSample.DOMAIN, tokenGuestAuth,
                TestConstantsSample.GUEST_SPACE_ID);
        this.tokenGuestRecordManagerment = new Record(tokenGuestConnection);

        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection certConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certAuth);
        this.certRecordManagerment = new Record(certConnection);

        Auth certGuestAuth = new Auth();
        certGuestAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certGuestAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection CertGuestConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certGuestAuth,
                TestConstantsSample.GUEST_SPACE_ID);
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
    public void testUpdateRecordsStatus() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusInGuest() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.guestAuthRecordManagerment.addRecords(360, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.guestAuthRecordManagerment.updateRecordsStatus(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenGuestRecordManagerment.addRecords(360, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.tokenGuestRecordManagerment.updateRecordsStatus(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(360, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);

        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecordsStatus(360, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsStatusHundred() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i < 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(100, results.size());
    }

    @Test
    public void testUpdateRecordsStatusHundredToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i < 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(100, results.size());
    }

    @Test
    public void testUpdateRecordsStatusHundredCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i < 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(100, results.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusOverHundred() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i <= 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusOverHundredToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i <= 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusOverHundredCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        for (int i = 0; i <= 100; i++) {
            RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(),
                    addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
            updateItems.add(item);
        }
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusProcessOff() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1658, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(1658, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusProcessOffToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.noDeletePermissionRecordManagerment.addRecords(1658, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.noDeletePermissionRecordManagerment.updateRecordsStatus(1658, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusProcessOffCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1658, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(1658, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionApp() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, 1, -1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, 2, -1);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionAppToken() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, 1, -1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, 2, -1);
        updateItems.add(item1);
        updateItems.add(item2);
        this.noViewPermissionTokenRecordManagerment.updateRecordsStatus(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionAppCert() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, 1, -1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, 2, -1);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1634, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.addNoViewTokenRecordManagerment.addRecords(1634, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.addNoViewTokenRecordManagerment.updateRecordsStatus(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusNotHavePermissionRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1634, records);
        // Main Test processing
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameBlank() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameBlankToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameBlankCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameInvalid() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("aaa", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameInvalidToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("aaa", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongActionNameInvalidCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("aaa", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAssigneeInvalid() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "ssssssssssssss", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAssigneeInvalidToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "ssssssssssssss", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAssigneeInvalidCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "ssssssssssssss", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDInvalid() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), -1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDInvalidToken() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), -1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDInvalidCert() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), -1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDUnexisted() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 100000, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDUnexistedToken() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 100000, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongIDUnexistedCert() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 100000, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionInvalid() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, -2);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionInvalidToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, -2);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionInvalidCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, -2);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionUnexisted() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, 100000);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionUnexistedToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, 100000);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongRevisionUnexistedCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, 100000);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithInactiveUser() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "XXXXX", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithInactiveUserToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "XXXXX", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithInactiveUserCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "XXXXX", id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdUnexisted() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(10000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdUnexistedToken() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(10000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdUnexistedCert() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(10000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdNegativeNumber() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdNegativeNumberToken() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdNegativeNumberCert() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdZero() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdZeroToken() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithWrongAppIdZeroCert() throws KintoneAPIException {
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutItems() throws KintoneAPIException {
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
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutItemsToken() throws KintoneAPIException {
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
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutItemsCert() throws KintoneAPIException {
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
        this.certRecordManagerment.updateRecordsStatus(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutApp() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutAppToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutAppCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutRecord() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutRecordToken() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutRecordCert() throws KintoneAPIException {
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
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutAssignee() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutAssigneeToken() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsStatusWithoutAssigneeCert() throws KintoneAPIException {
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
        Integer id1 = addResponse.getIDs().get(0);
        Integer revision1 = addResponse.getRevisions().get(0);
        Integer id2 = addResponse.getIDs().get(1);
        Integer revision2 = addResponse.getRevisions().get(1);
        ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
        RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
        RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
    }
}
