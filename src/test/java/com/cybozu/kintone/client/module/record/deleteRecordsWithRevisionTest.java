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

public class deleteRecordsWithRevisionTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
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
    public void testDeleteRecordsWithRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void testDeleteRecordsWithRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void testDeleteRecordsWithRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdNotExisted() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(111111111, addResponse.getRevisions().get(1));
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdNotExistedToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(111111111, addResponse.getRevisions().get(1));
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdNotExistedCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(111111111, addResponse.getRevisions().get(1));
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void testDeleteRecordsWithRevisionWhenRevisionNegativeOne() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void testDeleteRecordsWithRevisionWhenRevisionNegativeOneToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void testDeleteRecordsWithRevisionWhenRevisionNegativeOneCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.guestAuthRecordManagerment.addRecords(360, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.guestAuthRecordManagerment.deleteRecordsWithRevision(360, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionInGuestToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.tokenGuestRecordManagerment.deleteRecordsWithRevision(360, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionInGuestCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.certGuestRecordManagerment.deleteRecordsWithRevision(360, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionSuccessIDsIsHundred() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            idsWithRevision.put(addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
        }
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionSuccessIDsIsHundredToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            idsWithRevision.put(addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
        }
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test
    public void tesDeleteRecordWithRevisionSuccessIDsIsHundredCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i < addResponse.getIDs().size(); i++) {
            idsWithRevision.put(addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
        }
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordWithRevisionSuccessIDsOverHundred() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i <= 100; i++) {
            idsWithRevision.put(i, i);
        }
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordWithRevisionSuccessIDsOverHundredToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i <= 100; i++) {
            idsWithRevision.put(i, i);
        }
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordWithRevisionSuccessIDsOverHundredCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        for (int i = 0; i <= 100; i++) {
            idsWithRevision.put(i, i);
        }
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailNotHaveDeletePermission() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(1658, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailNotHaveDeletePermissionToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.noDeletePermissionRecordManagerment.deleteRecordsWithRevision(1658, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailNotHaveDeletePermissionCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.certRecordManagerment.deleteRecordsWithRevision(1658, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessNotHaveDeletePermissionOfRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1634, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(1634, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessNotHaveDeletePermissionOfRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.addNoViewTokenRecordManagerment.addRecords(1634, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.addNoViewTokenRecordManagerment.deleteRecordsWithRevision(1634, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsSuccessNotHaveDeletePermissionOfRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1634, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
        this.certRecordManagerment.deleteRecordsWithRevision(1634, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdUnexisted() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(10000, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdUnexistedToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.tokenRecordManagerment.deleteRecordsWithRevision(10000, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdUnexistedCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.certRecordManagerment.deleteRecordsWithRevision(10000, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdNegativeNumber() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(-1, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdNegativeNumberToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.tokenRecordManagerment.deleteRecordsWithRevision(-1, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdNegativeNumberCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.certRecordManagerment.deleteRecordsWithRevision(-1, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdZero() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(0, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdZeroToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.tokenRecordManagerment.deleteRecordsWithRevision(0, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionAppIdZeroCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1);
        idsWithRevision.put(2, 1);
        this.certRecordManagerment.deleteRecordsWithRevision(0, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionUnexistedRevision() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1000);
        idsWithRevision.put(2, 1000);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionUnexistedRevisionToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1000);
        idsWithRevision.put(2, 1000);
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionUnexistedRevisionCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 1000);
        idsWithRevision.put(2, 1000);
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionNegativeNumberRevision() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, -2);
        idsWithRevision.put(2, -3);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionNegativeNumberRevisionToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, -2);
        idsWithRevision.put(2, -3);
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionNegativeNumberRevisionCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, -2);
        idsWithRevision.put(2, -3);
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionZeroRevision() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 0);
        idsWithRevision.put(2, 0);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionZeroRevisionToken() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 0);
        idsWithRevision.put(2, 0);
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionZeroRevisionCert() throws KintoneAPIException {
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(1, 0);
        idsWithRevision.put(2, 0);
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdDuplicate() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addresponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord1);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision());
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision() + 1);
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdDuplicateToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addresponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord1);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision());
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision() + 1);
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionRecordIdDuplicateCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addresponse = this.certRecordManagerment.addRecord(APP_ID, testRecord1);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision());
        idsWithRevision.put(addresponse.getID(), addresponse.getRevision() + 1);
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutRevision() throws KintoneAPIException {
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
        HashMap<Integer, Integer> hm = new HashMap<>();
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, hm);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutRevisionToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> hm = new HashMap<>();
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, hm);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithoutRevisionCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> hm = new HashMap<>();
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, hm);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithNullRevision() throws KintoneAPIException {
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
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithNullRevisionToken() throws KintoneAPIException {
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
        this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithNullRevisionCert() throws KintoneAPIException {
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
        this.certRecordManagerment.deleteRecordsWithRevision(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionWithoutApp() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(1));
        this.passwordAuthRecordManagerment.deleteRecordsWithRevision(null, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionWithoutAppToken() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(1));
        this.tokenRecordManagerment.deleteRecordsWithRevision(null, idsWithRevision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionWithoutAppCert() throws KintoneAPIException {
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
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
        idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(1));
        this.certRecordManagerment.deleteRecordsWithRevision(null, idsWithRevision);
    }
}
