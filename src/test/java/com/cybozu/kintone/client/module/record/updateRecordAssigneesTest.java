package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
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

public class updateRecordAssigneesTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String HA_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String REQUIRED_FIELD_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";
    private static String NO_EDIT_PERMISSION_API_TOKEN = "xxx";
    private static String NO_MANAGE_PERMISSION_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record requiredFieldTokenRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
    private Record noEditPermissionRecordManagerment;
    private Record noManagePermissionRecordManagerment;
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

        Auth tokenAuth4 = new Auth();
        tokenAuth4.setApiToken(REQUIRED_FIELD_API_TOKEN);
        Connection tokenConnection4 = new Connection(TestConstants.DOMAIN, tokenAuth4);
        this.requiredFieldTokenRecordManagerment = new Record(tokenConnection4);

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstants.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

        Auth tokenAuth9 = new Auth();
        tokenAuth9.setApiToken(NO_EDIT_PERMISSION_API_TOKEN);
        Connection tokenConnection9 = new Connection(TestConstants.DOMAIN, tokenAuth9);
        this.noEditPermissionRecordManagerment = new Record(tokenConnection9);

        Auth tokenAuth12 = new Auth();
        tokenAuth12.setApiToken(NO_MANAGE_PERMISSION_API_TOKEN);
        Connection tokenConnection12 = new Connection(TestConstants.DOMAIN, tokenAuth12);
        this.noManagePermissionRecordManagerment = new Record(tokenConnection12);

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
    public void testUpdateRecordAssignees() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    // users "user1 - user100" need to be added to domain
    @Ignore
    @Test
    public void testUpdateRecordAssigneesShouldSuccessAddHundredAssignees() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.HADOMAIN, passwordAuth);
        passwordAuthRecordManagerment = new Record(passwordAuthConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(1, id, action, null, revision);
        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i < 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(1, id, assignees,
                null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    // users "user1 - user100" need to be added to domain
    @Ignore
    @Test
    public void testUpdateRecordAssigneesShouldSuccessAddHundredAssigneesToken() throws KintoneAPIException {
        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(HA_API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.HADOMAIN, tokenAuth);
        tokenRecordManagerment = new Record(tokenConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(1, id, action, null, revision);
        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i < 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(1, id, assignees, null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    // users "user1 - user100" need to be added to domain
    @Ignore
    @Test
    public void testUpdateRecordAssigneesShouldSuccessAddHundredAssigneesCert() throws KintoneAPIException {
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.HACLIENT_CERT_PATH, TestConstants.HACLIENT_CERT_PASSWORD);
        Connection CertConnection = new Connection(TestConstants.HASECURE_DOMAIN, certauth);
        certRecordManagerment = new Record(CertConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(1, id, action, null, revision);
        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i < 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(1, id, assignees, null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailAddOverHundredAssignees() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.HADOMAIN, passwordAuth);
        passwordAuthRecordManagerment = new Record(passwordAuthConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.passwordAuthRecordManagerment.updateRecordStatus(1, id, action, null, revision);

        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i <= 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(1, id, assignees,
                null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailAddOverHundredAssigneesToken() throws KintoneAPIException {
        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(HA_API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.HADOMAIN, tokenAuth);
        tokenRecordManagerment = new Record(tokenConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.tokenRecordManagerment.updateRecordStatus(1, id, action, null, revision);

        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i <= 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(1, id, assignees, null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailAddOverHundredAssigneesCert() throws KintoneAPIException {
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.HACLIENT_CERT_PATH, TestConstants.HACLIENT_CERT_PASSWORD);
        Connection CertConnection = new Connection(TestConstants.HASECURE_DOMAIN, certauth);
        certRecordManagerment = new Record(CertConnection);
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        this.certRecordManagerment.updateRecordStatus(1, id, action, null, revision);

        ArrayList<String> assignees = new ArrayList<String>();
        for (int i = 1; i <= 101; i++) {
            assignees.add("user" + i);
        }
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(1, id, assignees, null);
        assertEquals((Integer) (revision + 3), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailThanMultiAssignees() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman2.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailThanMultiAssigneesToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman2.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailThanMultiAssigneesCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman2.getCode());
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
    }

    @Test
    public void testUpdateRecordAssigneesSuccessRevisionNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesSuccessRevisionNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesSuccessRevisionNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesWithoutRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesWithoutRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesWithoutRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailWhenNotHasManageAppPermissionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.noManagePermissionRecordManagerment.addRecord(1667, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.noManagePermissionRecordManagerment.updateRecordAssignees(1667, id,
                assignees, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 111111);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 111111);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 111111);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionNegativeTwo() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionNegativeTwoToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionNegativeTwoCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesRevisionZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWrongUserCode() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("aaaaaaaaaaaaaaaaaaa");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWrongUserCodeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("aaaaaaaaaaaaaaaaaaa");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWrongUserCodeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("aaaaaaaaaaaaaaaaaaa");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserInactive() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Brian");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserInactiveToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Brian");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserInactiveCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Brian");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserIsDeleted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Duc");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserIsDeletedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Duc");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesUserIsDeletedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("Duc");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
    }

    @Test
    public void testUpdateRecordAssigneesUserDuplicate() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesUserDuplicateToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesUserDuplicateCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        assignees.add(testman1.getCode());
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordAssignees(360, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordAssignees(360, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordAssignees(360, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesProcessOFF() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.passwordAuthRecordManagerment.updateRecordAssignees(1640, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesProcessOFFToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.requiredFieldTokenRecordManagerment.updateRecordAssignees(1640, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesProcessOFFCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.certRecordManagerment.updateRecordAssignees(1640, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailNotHavePermissionApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1658, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.passwordAuthRecordManagerment.updateRecordAssignees(1658, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailNotHavePermissionAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.noDeletePermissionRecordManagerment.addRecord(1658, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.noDeletePermissionRecordManagerment.updateRecordAssignees(1658, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldFailNotHavePermissionAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1658, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.certRecordManagerment.updateRecordAssignees(1658, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldSuccessWhenNotHavePermissionId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1659, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.passwordAuthRecordManagerment.updateRecordAssignees(1659, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldSuccessWhenNotHavePermissionIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.noEditPermissionRecordManagerment.addRecord(1659, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.noEditPermissionRecordManagerment.updateRecordAssignees(1659, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesShouldSuccessWhenNotHavePermissionIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1659, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.certRecordManagerment.updateRecordAssignees(1659, id, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdUnexisted() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.passwordAuthRecordManagerment.updateRecordAssignees(100000, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdUnexistedToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.tokenRecordManagerment.updateRecordAssignees(100000, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdUnexistedCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.certRecordManagerment.updateRecordAssignees(100000, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdNegativeNumber() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.passwordAuthRecordManagerment.updateRecordAssignees(-1, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdNegativeNumberToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.tokenRecordManagerment.updateRecordAssignees(-1, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdNegativeNumberCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.certRecordManagerment.updateRecordAssignees(-1, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdZero() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.passwordAuthRecordManagerment.updateRecordAssignees(0, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdZeroToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.tokenRecordManagerment.updateRecordAssignees(0, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesAppIdZeroCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.certRecordManagerment.updateRecordAssignees(0, 1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdUnexisted() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, 100000, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdUnexistedToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, 100000, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdUnexistedCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, 100000, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdNegativeNumber() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, -1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdNegativeNumberToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, -1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdNegativeNumberCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, -1, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdZero() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, 0, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdZeroToken() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, 0, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIdZeroCert() throws KintoneAPIException {
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("testman1");
        this.certRecordManagerment.updateRecordAssignees(APP_ID, 0, assignees, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIWithoutAssignees() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, null,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIWithoutAssigneesToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, null, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesIWithoutAssigneesCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordAssignees(APP_ID, id, null, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, null, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(APP_ID, null, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.certRecordManagerment.updateRecordAssignees(APP_ID, null, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.passwordAuthRecordManagerment.updateRecordAssignees(null, id, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.tokenRecordManagerment.updateRecordAssignees(null, id, assignees, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordAssigneesWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add(testman1.getCode());
        this.certRecordManagerment.updateRecordAssignees(null, id, assignees, revision);
    }
}
