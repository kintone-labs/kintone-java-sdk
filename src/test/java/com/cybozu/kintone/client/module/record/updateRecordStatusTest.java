package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class updateRecordStatusTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";
    private static String LOCAL_LANGUAGE_API_TOKEN = "xxx";
    private static String NO_SET_ASSIGNEE_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noAddPermissionTokenReocrdManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
    private Record localLanguageRecordManagerment;
    private Record noSetAssigneeRecordManagerment;
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

        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection gusetConnection = new Connection(TestConstants.DOMAIN, guestAuth, TestConstants.GUEST_SPACE_ID);
        this.guestAuthRecordManagerment = new Record(gusetConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth5 = new Auth();
        tokenAuth5.setApiToken(NO_ADD_PERMISSION_API_TOKEN);
        Connection tokenConnection5 = new Connection(TestConstants.DOMAIN, tokenAuth5);
        this.noAddPermissionTokenReocrdManagerment = new Record(tokenConnection5);

        Auth tokenAuth6 = new Auth();
        tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
        Connection tokenConnection6 = new Connection(TestConstants.DOMAIN, tokenAuth6);
        this.addNoViewTokenRecordManagerment = new Record(tokenConnection6);

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstants.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

        Auth tokenAuth10 = new Auth();
        tokenAuth10.setApiToken(LOCAL_LANGUAGE_API_TOKEN);
        Connection tokenConnection10 = new Connection(TestConstants.DOMAIN, tokenAuth10);
        this.localLanguageRecordManagerment = new Record(tokenConnection10);

        Auth tokenAuth11 = new Auth();
        tokenAuth11.setApiToken(NO_SET_ASSIGNEE_API_TOKEN);
        Connection tokenConnection11 = new Connection(TestConstants.DOMAIN, tokenAuth11);
        this.noSetAssigneeRecordManagerment = new Record(tokenConnection11);

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

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyAction() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyActionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyActionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusActionPlusAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusActionPlusAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusActionPlusAssigneeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusLocalLanguage() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "testman1";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusLocalLanguageToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.localLanguageRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "testman1";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.localLanguageRecordManagerment.updateRecordStatus(APP_ID, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusLocalLanguageCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "testman1";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "かいし";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.noSetAssigneeRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "かいし";
        UpdateRecordResponse response = this.noSetAssigneeRecordManagerment.updateRecordStatus(APP_ID, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssigneeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "かいし";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusCurrentUserToChangeStatus() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());

        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth("testman1", "cybozu");
        Connection connection = new Connection(TestConstants.DOMAIN, passwordAuth);
        Record record = new Record(connection);
        String action2 = "完了する";
        UpdateRecordResponse response1 = record.updateRecordStatus(APP_ID, id, action2, null, revision + 2);
        assertEquals((Integer) (revision + 4), response1.getRevision());
    }

    @Test
    public void testUpdateRecordStatusCurrentUserToChangeStatusToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());

        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth("testman1", "cybozu");
        Connection connection = new Connection(TestConstants.DOMAIN, passwordAuth);
        Record record = new Record(connection);
        String action2 = "完了する";
        UpdateRecordResponse response1 = record.updateRecordStatus(APP_ID, id, action2, null, revision + 2);
        assertEquals((Integer) (revision + 4), response1.getRevision());
    }

    @Test
    public void testUpdateRecordStatusCurrentUserToChangeStatusCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());

        Auth certauth = new Auth();
        certauth.setPasswordAuth("testman1", "cybozu");
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        Record record = new Record(connection);
        String action2 = "完了する";
        UpdateRecordResponse response1 = record.updateRecordStatus(APP_ID, id, action2, null, revision + 2);
        assertEquals((Integer) (revision + 4), response1.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "aaaaaaaaaaa";
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "aaaaaaaaaaa";
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAssigneeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "aaaaaaaaaaa";
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
    }

    @Test
    public void testUpdateRecordStatusRevisionNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action,
                assignee, -1);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusRevisionNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,
                -1);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusRevisionNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -1);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWhenComplete() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5665, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWhenCompleteToken() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5665, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWhenCompleteCert() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, 5665, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyAssigneeCanChage() throws KintoneAPIException {
        String action = "完了する";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5662, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyAssigneeCanChageToken() throws KintoneAPIException {
        String action = "完了する";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5662, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusOnlyAssigneeCanChageCert() throws KintoneAPIException {
        String action = "完了する";
        this.certRecordManagerment.updateRecordStatus(APP_ID, 5662, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAssigneeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAction() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, null, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutActionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, null, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutActionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        this.certRecordManagerment.updateRecordStatus(APP_ID, id, null, assignee, revision);
    }

    @Test
    public void testUpdateRecordStatusWithoutRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "処理開始";
        String assignee = testman1.getCode();
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, null);
    }

    @Test
    public void testUpdateRecordStatusWithoutRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "処理開始";
        String assignee = testman1.getCode();
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, null);
    }

    @Test
    public void testUpdateRecordStatusWithoutRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "処理開始";
        String assignee = testman1.getCode();
        this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, null, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, null, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, null, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(null, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(null, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(null, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAlreadyHasAssignee() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5664, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAlreadyHasAssigneeToken() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5664, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAlreadyHasAssigneeCert() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, 5664, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionApp() throws KintoneAPIException {
        String action = "开始处理";
        this.passwordAuthRecordManagerment.updateRecordStatus(1632, 1, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionAppToken() throws KintoneAPIException {
        String action = "开始处理";
        this.noAddPermissionTokenReocrdManagerment.updateRecordStatus(1632, 1, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionAppCert() throws KintoneAPIException {
        String action = "开始处理";
        this.certRecordManagerment.updateRecordStatus(1632, 1, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1634, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "开始处理";
        this.passwordAuthRecordManagerment.updateRecordStatus(1634, id, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.addNoViewTokenRecordManagerment.addRecord(1634, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "开始处理";
        this.addNoViewTokenRecordManagerment.updateRecordStatus(1634, id, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusNotHavePermissionRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1634, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        String action = "开始处理";
        this.certRecordManagerment.updateRecordStatus(1634, id, action, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAppId() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(100000, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAppIdToken() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(100000, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAppIdCert() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(100000, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdNegativeOne() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(-1, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdNegativeOneToken() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(-1, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdNegativeOneCert() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(-1, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdZreo() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(0, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdZreoToken() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(0, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusAppIdZreoCert() throws KintoneAPIException {
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(0, 1, action, assignee, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        String assignee = testman1.getCode();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedAction() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理1開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(1662, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedActionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.noSetAssigneeRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理1開始";
        this.noSetAssigneeRecordManagerment.updateRecordStatus(1662, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusUnexistedActionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = testman1.getCode();
        String action = "処理1開始";
        this.certRecordManagerment.updateRecordStatus(1662, id, action, assignee, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusProcessOff() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "文字列1行");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1658, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(1658, id, action, null, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusProcessOffToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "文字列1行");
        AddRecordResponse addResponse = this.noDeletePermissionRecordManagerment.addRecord(1658, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.noDeletePermissionRecordManagerment.updateRecordStatus(1658, id, action, null, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusProcessOffCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "文字列1行");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1658, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(1658, id, action, null, revision);
    }

    @Test
    public void testUpdateRecordStatusInGuest() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordStatus(360, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordStatus(360, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordStatus(360, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }
}
