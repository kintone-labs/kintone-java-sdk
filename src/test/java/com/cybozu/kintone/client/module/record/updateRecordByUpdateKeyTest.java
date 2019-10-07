package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

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
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.module.file.File;

public class updateRecordByUpdateKeyTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String ANOTHER_GUEST_SPACE_API_TOKEN = "xxx";
    private static String PROHIBIT_DUPLICATE_API_TOKEN = "xxx";

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

        Auth tokenAuth5 = new Auth();
        tokenAuth5.setApiToken(NO_ADD_PERMISSION_API_TOKEN);
        Connection tokenConnection5 = new Connection(TestConstants.DOMAIN, tokenAuth5);
        this.noAddPermissionTokenReocrdManagerment = new Record(tokenConnection5);

        Auth tokenAuth6 = new Auth();
        tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
        Connection tokenConnection6 = new Connection(TestConstants.DOMAIN, tokenAuth6);
        this.addNoViewTokenRecordManagerment = new Record(tokenConnection6);

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
    public void testUpdateRecordByUpdateKey() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyRevisionNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyRevisionNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyRevisionNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, -1);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyMultiUpdateKeys() throws KintoneAPIException {
        // 1622のアプリの二つの重複禁止のフィールドを設定している
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyMultiUpdateKeysToken() throws KintoneAPIException {
        // 1622のアプリの二つの重複禁止のフィールドを設定している
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyMultiUpdateKeysCert() throws KintoneAPIException {
        // 1622のアプリの二つの重複禁止のフィールドを設定している
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        Integer revision = addResponse.getRevision();
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testUpdateRecordByUpdateKeyWithAttachment() throws KintoneAPIException {
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

        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "添付ファイル", FieldType.FILE, al);
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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
    public void testUpdateRecordByUpdateKeyWithAttachmentToken() throws KintoneAPIException {
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

        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "添付ファイル", FieldType.FILE, al);
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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
    public void testUpdateRecordByUpdateKeyWithAttachmentCert() throws KintoneAPIException {
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

        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "添付ファイル", FieldType.FILE, al);
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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
    public void testUpdateRecordByUpdateKeyDataWithTable() throws KintoneAPIException {
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
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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
    public void testUpdateRecordByUpdateKeyDataWithTableToken() throws KintoneAPIException {
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
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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
    public void testUpdateRecordByUpdateKeyDataWithTableCert() throws KintoneAPIException {
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
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
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

    @Test
    public void testUpdateRecordByUpdateKeyInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordByUpdateKey(360, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInGuestToken() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setApiToken(ANOTHER_GUEST_SPACE_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
        AddRecordResponse addResponse = guestRecord.addRecord(360, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = guestRecord.updateRecordByUpdateKey(360, updateKey, updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordByUpdateKey(360, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInvalidFildWillSkip() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInvalidFildWillSkipToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInvalidFildWillSkipCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailLessThanNegativeOne() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailLessThanNegativeOneToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailLessThanNegativeOneCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWrongUpdatekey() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("リンク", String.valueOf(testRecord.get("リンク").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWrongUpdatekeyToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("リンク", String.valueOf(testRecord.get("リンク").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWrongUpdatekeyCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("リンク", String.valueOf(testRecord.get("リンク").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(10000, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(10000, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(10000, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDNegativeNumber() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(-1, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(-1, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDNegativeNumberCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(-1, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(0, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(0, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailAppIDZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(0, updateKey, updateRecord, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutKey() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutKeyToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutKeyCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer revision = addResponse.getRevision();
        testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, null, testRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInputStringToNumberField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数値", FieldType.NUMBER, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数値", FieldType.NUMBER, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInputStringToNumberFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数値", FieldType.NUMBER, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值", FieldType.NUMBER, 11);
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值", FieldType.NUMBER, 11);
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailInvalidValueOverMaximumCert() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值", FieldType.NUMBER, 11);
        this.certRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailUpdatekeyNotUnique() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "9");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值_0", FieldType.NUMBER, 12);
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailUpdatekeyNotUniqueToken() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "9");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值_0", FieldType.NUMBER, 12);
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailUpdatekeyNotUniqueCert() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "9");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "数值_0", FieldType.NUMBER, 12);
        this.certRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyChangeCreatorEtc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        updateRecord = addField(updateRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyChangeCreatorEtcToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        updateRecord = addField(updateRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyChangeCreatorEtCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        updateRecord = addField(updateRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        updateRecord = addField(updateRecord, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.noAddPermissionTokenReocrdManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.certRecordManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecordToken()
            throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.addNoViewTokenRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecordCert()
            throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.certRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1635, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.certRecordManagerment.updateRecordByUpdateKey(1635, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(null, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.tokenRecordManagerment.updateRecordByUpdateKey(null, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
        this.certRecordManagerment.updateRecordByUpdateKey(null, updateKey, updateRecord, null);
    }

    @Test
    public void testUpdateRecordByUpdateKeyWithoutRecordData() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey,
                null, null);
        assertEquals((Integer) (addResponse.getRevision() + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyWithoutRecordDataToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, null,
                null);
        assertEquals((Integer) (addResponse.getRevision() + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyWithoutRecordDataCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, null,
                null);
        assertEquals((Integer) (addResponse.getRevision() + 1), response.getRevision());
    }
}
