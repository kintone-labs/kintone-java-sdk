package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.file.File;

public class addRecordsTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String PROHIBIT_DUPLICATE_API_TOKEN = "xxx";
    private static String NO_ADMIN_PERMISSION_API_TOKEN = "xxx";

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
    private Record prohibitDuplicateTokenRecordManagerment;
    private Record noAdminPermissionRecordManagerment;
    private Record tokenGuestRecordManagerment;
    private Record certRecordManagerment;
    private Record certGuestRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        // passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
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

        Auth tokenAuth7 = new Auth();
        tokenAuth7.setApiToken(NO_ADMIN_PERMISSION_API_TOKEN);
        Connection tokenConnection7 = new Connection(TestConstants.DOMAIN, tokenAuth7);
        this.noAdminPermissionRecordManagerment = new Record(tokenConnection7);

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
    public void testAddRecords() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.certRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachment() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachmentToken() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachmentCert() throws KintoneAPIException {
        // Preprocessing
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.certRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsDataWithTable() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
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
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsDataWithTableToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
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
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsDataWithTableCert() throws KintoneAPIException {
        // Preprocessing
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
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
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.certRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachmentInGuest() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(360, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(360, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachmentInGuestToken() throws KintoneAPIException {
        // Preprocessing
        Auth auth = new Auth();
        auth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(360, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(360, response.getIDs().get(1));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testAddRecordsWithAttachmentInGuestCert() throws KintoneAPIException {
        // Preprocessing
        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth, TestConstants.GUEST_SPACE_ID);
        Record guestRecord = new Record(connection);

        File attachmet = new File(connection);
        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);

        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(360, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(360, response.getIDs().get(1));
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
    public void testAddRecordsInGuest() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.guestAuthRecordManagerment.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.tokenGuestRecordManagerment.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = this.certGuestRecordManagerment.addRecords(360, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecords(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecords(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecords(100000, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdNegative() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecords(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdNegativeToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecords(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdNegativeCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecords(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecords(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecords(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsAppIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecords(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutApp() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecords(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutAppToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecords(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutAppCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecords(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutRecords() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.addRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutRecordsToken() throws KintoneAPIException {
        this.tokenRecordManagerment.addRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsWithoutRecordsCert() throws KintoneAPIException {
        this.certRecordManagerment.addRecords(APP_ID, null);
    }

    @Test
    public void testAddRecordsInvalidFieldShouldSkipped() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不存在的字符串", FieldType.SINGLE_LINE_TEXT, "123");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不存在的数值", FieldType.NUMBER, 123);
        records.add(testRecord2);

        AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsInvalidFieldShouldSkippedToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不存在的字符串", FieldType.SINGLE_LINE_TEXT, "123");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不存在的数值", FieldType.NUMBER, 123);
        records.add(testRecord2);

        AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test
    public void testAddRecordsInvalidFieldShouldSkippedCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不存在的字符串", FieldType.SINGLE_LINE_TEXT, "123");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不存在的数值", FieldType.NUMBER, 123);
        records.add(testRecord2);

        AddRecordsResponse response = this.certRecordManagerment.addRecords(APP_ID, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInputStringToNumberField() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 123);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 123);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInputStringToNumberFieldCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 123);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "aaa");
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "aaa");
        records.add(testRecord2);
        this.prohibitDuplicateTokenRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "aaa");
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.prohibitDuplicateTokenRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailInvalidValueOverMaximumCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(1636, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCreator() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCreatorToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCreatorCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedModifier() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedModifierToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedModifierCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCeretedTime() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCeretedTimeToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedCeretedTimeCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedUpdatedTime() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedUpdatedTimeToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetNonexistedUpdatedTimeCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(1640, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenDoNotSetRequiredFieldCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(1640, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetUnexistedFileKey() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetUnexistedFileKeyToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        records.add(testRecord2);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSetUnexistedFileKeyCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord2);
        this.passwordAuthRecordManagerment.addRecords(1632, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord2);
        this.noAddPermissionTokenReocrdManagerment.addRecords(1632, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord1);

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        records.add(testRecord2);
        this.certRecordManagerment.addRecords(1632, records);
    }

    @Test
    public void testAddRecordsShouldSuccessOfHundred() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(100, iDs.size());
    }

    @Test
    public void testAddRecordsShouldSuccessOfHundredToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addRecords = this.tokenRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(100, iDs.size());
    }

    @Test
    public void testAddRecordsShouldSuccessOfHundredCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        AddRecordsResponse addRecords = this.certRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(100, iDs.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailThenOverHundred() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailThenOverHundredToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        this.tokenRecordManagerment.addRecords(APP_ID, records);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailThenOverHundredCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
        }
        this.certRecordManagerment.addRecords(APP_ID, records);
    }

    @Test
    public void testAddRecordsShouldSuccessUesAdminToSetFields() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);

        AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }

    @Test
    public void testAddRecordsShouldSuccessUesAdminToSetFieldsToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);

        AddRecordsResponse addRecords = this.tokenRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }

    @Test
    public void testAddRecordsShouldSuccessUesAdminToSetFieldsCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);

        AddRecordsResponse addRecords = this.certRecordManagerment.addRecords(APP_ID, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailDoNotAdminToSetFields() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);
        AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(1637, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailDoNotAdminToSetFieldsToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);
        AddRecordsResponse addRecords = this.noAdminPermissionRecordManagerment.addRecords(1637, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailDoNotAdminToSetFieldsCert() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord1);
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("yfang", "yfang"));
        records.add(testRecord2);
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        records.add(testRecord3);
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("yfang", "yfang"));
        records.add(testRecord4);
        AddRecordsResponse addRecords = this.certRecordManagerment.addRecords(1637, records);
        ArrayList<Integer> iDs = addRecords.getIDs();
        assertEquals(4, iDs.size());
    }
}
