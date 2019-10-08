package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.record.response.AddRecordResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordsResponse;
import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.module.file.File;

public class addRecordTest {
    private static Integer APP_ID;
    private static Integer APP_ID2;
    private static String API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String BLANK_APP_API_TOKEN = "xxx";
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
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noAddPermissionTokenReocrdManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record blankAppApiTokenRecordManagerment;
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

        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection gusetConnection = new Connection(TestConstants.DOMAIN, guestAuth, TestConstants.GUEST_SPACE_ID);
        this.guestAuthRecordManagerment = new Record(gusetConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth2 = new Auth();
        tokenAuth2.setApiToken(BLANK_APP_API_TOKEN);
        Connection tokenConnection2 = new Connection(TestConstants.DOMAIN, tokenAuth2);
        this.blankAppApiTokenRecordManagerment = new Record(tokenConnection2);

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
    public void testAddRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, testman1);
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-01T09:00:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, testman2);
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T18:00:00Z");
        // Main Test processing
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse getResponse = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> resultRecord = getResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testAddRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, testman1);
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-01T09:00:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, testman2);
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T18:00:00Z");
        // Main Test processing
        AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse getResponse = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> resultRecord = getResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testAddRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, testman1);
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-01T09:00:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, testman2);
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T18:00:00Z");
        // Main Test processing
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse getResponse = this.certRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> resultRecord = getResponse.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testAddRecordWithoutRecord() throws KintoneAPIException {
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, null);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordWithoutRecordToken() throws KintoneAPIException {
        AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, null);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordWithoutRecordCert() throws KintoneAPIException {
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, null);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachment() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachment = new File(connection);

        FileModel file = attachment.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachmentToken() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachmentCert() throws KintoneAPIException {
        // Preprocessing
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        File attachmet = new File(connection);

        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = this.certRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordDataWithTable() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
        tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue);
        subTable.add(tablelist1);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordDataWithTableToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
        tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue);
        subTable.add(tablelist1);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordDataWithTableCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
        tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue);
        subTable.add(tablelist1);
        // Main Test processing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
        GetRecordResponse rp = this.certRecordManagerment.getRecord(APP_ID, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachmentInGuest() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(360, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachmentInGuestToken() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(360, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordWithAttachmentInGuestCert() throws KintoneAPIException {
        // Preprocessing
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(360, response.getID());
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.FILE == record.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @Test
    public void testAddRecordInGuest() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse response = this.guestAuthRecordManagerment.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse response = this.tokenGuestRecordManagerment.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse response = this.certGuestRecordManagerment.addRecord(360, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordWithoutApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(null, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordWithoutAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(null, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordWithoutAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(null, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecord(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecord(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecord(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdNegative() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecord(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdNegativeToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecord(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdNegativeCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecord(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecord(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecord(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordAppIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecord(0, null);
    }

    @Test
    public void testAddRecordInvalidFieldShouldSkipped() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不存在的", FieldType.SINGLE_LINE_TEXT, 123);
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test
    public void testAddRecordInvalidFieldShouldSkippedToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不存在的", FieldType.SINGLE_LINE_TEXT, 123);
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test
    public void testAddRecordInvalidFieldShouldSkippedCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不存在的", FieldType.SINGLE_LINE_TEXT, 123);
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInputStringToNumberFieldInvalidField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInputStringToNumberFieldInvalidFieldToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInputStringToNumberFieldInvalidFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.prohibitDuplicateTokenRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.passwordAuthRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.prohibitDuplicateTokenRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidValueOverMaximumCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.certRecordManagerment.addRecord(1636, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidLookupValue() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "agdagsgasdg");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidLookupValueToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "agdagsgasdg");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailInvalidLookupValueCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "agdagsgasdg");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetCategories() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, "テスト１");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetCategoriesToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, "テスト１");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetCategoriesCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, "テスト１");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetStatus() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "処理中");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetStatusToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "処理中");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetStatusCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "処理中");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetAssignee() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, new Member("user1", "user1"));
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetAssigneeToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, new Member("user1", "user1"));
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetAssigneeCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, new Member("user1", "user1"));
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCreator() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCreatorToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCreatorCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedModifier() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedModifierToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedModifierCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCeratedTime() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCeratedTimeToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedCeratedTimeCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedUpdatedTime() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedUpdatedTimeToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetNonexistedUpdatedTimeCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.requiredFieldTokenRecordManagerment.addRecord(1640, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotSetRequiredFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(1640, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetUnexistedFileKey() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetUnexistedFileKeyToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSetUnexistedFileKeyCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.addRecord(APP_ID2, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.noAddPermissionTokenReocrdManagerment.addRecord(APP_ID, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.addRecord(APP_ID2, testRecord);
    }

    @Test
    public void testAddRecordShouldSuccessWhenDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessWhenDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.addNoViewTokenRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessWhenDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.passwordAuthRecordManagerment.addRecord(1635, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.certRecordManagerment.addRecord(1635, testRecord);
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.blankAppApiTokenRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }
}
