package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.record.response.AddRecordResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.record.response.UpdateRecordResponse;
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

public class updateRecordByIDTest {
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
    private Record guestAuthRecordManagerment;
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

        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection gusetConnection = new Connection(TestConstants.DOMAIN, guestAuth, TestConstants.GUEST_SPACE_ID);
        this.guestAuthRecordManagerment = new Record(gusetConnection);

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
    public void testUpdateRecordById() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testUpdateRecordByIDWithAttachment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);

        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
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
    public void testUpdateRecordByIDWithAttachmentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);

        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
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
    public void testUpdateRecordByIDWithAttachmentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();

        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        File attachmet = new File(connection);

        FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);

        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.certRecordManagerment.getRecord(APP_ID, id);
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
    public void testUpdateRecordByIDDataWithTable() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
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
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
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
    public void testUpdateRecordByIDDataWithTableToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
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
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
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
    public void testUpdateRecordByIDDataWithTableCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
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
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());

        GetRecordResponse rp = this.certRecordManagerment.getRecord(APP_ID, id);
        HashMap<String, FieldValue> record = rp.getRecord();
        for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
            assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    public void tesUpdateRecordByIDInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordByID(360, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void tesUpdateRecordByIDInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordByID(360, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void tesUpdateRecordByIDInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordByID(360, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdRevisionNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdRevisionNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdRevisionNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdRevisionShouldFailLessThanNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdRevisionShouldFailLessThanNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdRevisionShouldFailLessThanNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailRevisionZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDUnexisted() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(10000, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDUnexistedToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(10000, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDUnexistedCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(10000, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDNegativeNumber() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(-1, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(-1, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIDNegativeNumberCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(-1, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIdZero() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(0, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIdZeroToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(0, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailAppIdZeroCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(0, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdUnexisted() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, 100000, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdUnexistedToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, 100000, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdUnexistedCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, 100000, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdNegativeNumber() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, -1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdNegativeNumberToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, -1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdNegativeNumberCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, -1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdZero() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, 0, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdZeroToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, 0, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailIdZeroCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, 0, testRecord, null);
    }

    @Test
    public void testUpdateRecordByIdInvalidFieldWillSkip() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdInvalidFieldWillSkipToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdInvalidFieldWillSkipCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, null, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, null, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByIdWithoutRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByID(APP_ID, id, null, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInputStringToNumberField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInputStringToNumberFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.passwordAuthRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailInvalidValueOverMaximumCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
        this.certRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 111);
        this.passwordAuthRecordManagerment.updateRecordByID(1640, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 111);
        this.requiredFieldTokenRecordManagerment.updateRecordByID(1640, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenDoNotSetRequiredFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 111);
        this.certRecordManagerment.updateRecordByID(1640, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdChangeCreatorEtc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdChangeCreatorEtcToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdChangeCreatorEtcCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.certRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.noAddPermissionTokenReocrdManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.addNoViewTokenRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.passwordAuthRecordManagerment.updateRecordByID(1635, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.certRecordManagerment.updateRecordByID(1635, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByID(null, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByID(null, id, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByID(null, id, testRecord, revision);
    }
}
