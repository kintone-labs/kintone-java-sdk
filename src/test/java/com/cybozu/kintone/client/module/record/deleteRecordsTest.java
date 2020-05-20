package com.cybozu.kintone.client.module.record;

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
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class deleteRecordsTest {
    private static Integer APP_ID;
    private static Integer APP_ID2;
    private static String API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";
    private static String NO_EDIT_PERMISSION_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
    private Record noEditPermissionRecordManagerment;
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

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstantsSample.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

        Auth tokenAuth9 = new Auth();
        tokenAuth9.setApiToken(NO_EDIT_PERMISSION_API_TOKEN);
        Connection tokenConnection9 = new Connection(TestConstantsSample.DOMAIN, tokenAuth9);
        this.noEditPermissionRecordManagerment = new Record(tokenConnection9);

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
    public void testDeleteRecords() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsToken() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsCert() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsOnlyOneId() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsOnlyOneIdToken() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsOnlyOneIdCert() throws KintoneAPIException {
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
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutIds() throws KintoneAPIException {
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
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutIdsToken() throws KintoneAPIException {
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
        this.tokenRecordManagerment.deleteRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutIdsCert() throws KintoneAPIException {
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
        this.certRecordManagerment.deleteRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailNotHaveDeletePermission() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1658, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(1658, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailNotHaveDeletePermissionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.noDeletePermissionRecordManagerment.addRecords(1658, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.noDeletePermissionRecordManagerment.deleteRecords(1658, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailNotHaveDeletePermissionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1658, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(1658, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveEditPermission() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID2, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID2, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveEditPermissionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.noEditPermissionRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.noEditPermissionRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveEditPermissionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID2, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(APP_ID2, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveViewEditPermissionOfField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);

        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID2, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID2, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveViewEditPermissionOfFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);

        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID2, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(APP_ID2, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);

        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.guestAuthRecordManagerment.deleteRecords(360, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.tokenGuestRecordManagerment.deleteRecords(360, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.certGuestRecordManagerment.deleteRecords(360, ids);
    }

    @Test
    public void testDeleteRecordsSuccessIDsIsHundred() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            ids.add(addResponse.getIDs().get(i));
        }
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsSuccessIDsIsHundredToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            ids.add(addResponse.getIDs().get(i));
        }
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test
    public void testDeleteRecordsSuccessIDsIsHundredCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            ids.add(addResponse.getIDs().get(i));
        }
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessIDsOverHundred() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i <= 100; i++) {
            ids.add(i);
        }
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessIDsOverHundredToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i <= 100; i++) {
            ids.add(i);
        }
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessIDsOverHundredCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i <= 100; i++) {
            ids.add(i);
        }
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdUnexisted() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.passwordAuthRecordManagerment.deleteRecords(10000, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdUnexistedToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.tokenRecordManagerment.deleteRecords(10000, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdUnexistedCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.certRecordManagerment.deleteRecords(10000, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdNegativeNumber() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.passwordAuthRecordManagerment.deleteRecords(-1, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdNegativeNumberToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.tokenRecordManagerment.deleteRecords(-1, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdNegativeNumberCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.certRecordManagerment.deleteRecords(-1, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdZero() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.passwordAuthRecordManagerment.deleteRecords(0, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdZeroToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.tokenRecordManagerment.deleteRecords(0, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsAppIdZeroCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        this.certRecordManagerment.deleteRecords(0, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutApp() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        this.passwordAuthRecordManagerment.deleteRecords(null, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutAppToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        this.tokenRecordManagerment.deleteRecords(null, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutAppCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        this.certRecordManagerment.deleteRecords(null, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsUnexistedIds() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(100000);
        ids.add(200000);
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsUnexistedIdsToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(100000);
        ids.add(200000);
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsUnexistedIdsCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(100000);
        ids.add(200000);
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsNegativeNumbertIds() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(-1);
        ids.add(-2);
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsNegativeNumbertIdsToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(-1);
        ids.add(-2);
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsNegativeNumbertIdsCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(-1);
        ids.add(-2);
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsZeroIds() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(0);
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsZeroIdsToken() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(0);
        this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsZeroIdsCert() throws KintoneAPIException {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(0);
        this.certRecordManagerment.deleteRecords(APP_ID, ids);
    }
}
