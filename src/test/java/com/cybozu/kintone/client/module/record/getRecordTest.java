package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.model.record.FieldValue;
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

public class getRecordTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
    private static String BLANK_APP_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";

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
    private Record blankAppApiTokenRecordManagerment;
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

        Auth tokenAuth1 = new Auth();
        tokenAuth1.setApiToken(NO_VIEW_PERMISSION_API_TOKEN);
        Connection tokenConnection1 = new Connection(TestConstants.DOMAIN, tokenAuth1);
        this.noViewPermissionTokenRecordManagerment = new Record(tokenConnection1);

        Auth tokenAuth2 = new Auth();
        tokenAuth2.setApiToken(BLANK_APP_API_TOKEN);
        Connection tokenConnection2 = new Connection(TestConstants.DOMAIN, tokenAuth2);
        this.blankAppApiTokenRecordManagerment = new Record(tokenConnection2);

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
    public void testGetRecord() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();

        ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
        FileModel file1 = new FileModel();
        file1.setContentType("text/plain");
        file1.setFileKey("xxx");
        file1.setName("xxx.txt");
        file1.setSize("0");
        cbFileList.add(file1);
        FileModel file2 = new FileModel();
        file2.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        file2.setFileKey("xxx");
        file2.setName("RecordModuleTest.xlsx");
        file2.setSize("6577");
        cbFileList.add(file2);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, cbFileList);

        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tableItem1 = new SubTableValueItem();
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(testman1);
        userList.add(testman2);
        addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
        tableItem1.setID(3016494);
        HashMap<String, FieldValue> tableItemValue1 = new HashMap<String, FieldValue>();
        tableItemValue1 = addField(tableItemValue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tableItemValue1 = addField(tableItemValue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue1 = addField(tableItemValue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text1");
        tableItem1.setValue(tableItemValue1);
        subTable.add(tableItem1);
        SubTableValueItem tableItem2 = new SubTableValueItem();
        tableItem2.setID(3016497);
        HashMap<String, FieldValue> tableItemValue2 = new HashMap<String, FieldValue>();
        tableItemValue2 = addField(tableItemValue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");
        tableItemValue2 = addField(tableItemValue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue2 = addField(tableItemValue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text2");
        tableItem2.setValue(tableItemValue2);
        subTable.add(tableItem2);
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1234);
        testRecord = addField(testRecord, "計算_数値", FieldType.CALC, 1234);

        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("テスト１－１");
        categoryList.add("テスト１");
        categoryList.add("テスト２");
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, categoryList);
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "未処理");

        ArrayList<Member> assigneeList = new ArrayList<Member>();
        assigneeList.add(testman1);
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, assigneeList);
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "lookup1");
        // Main Test processing
        Integer id = 1;
        GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();

        ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
        FileModel file1 = new FileModel();
        file1.setContentType("text/plain");
        file1.setFileKey("xxx");
        file1.setName("xxx.txt");
        file1.setSize("0");
        cbFileList.add(file1);
        FileModel file2 = new FileModel();
        file2.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        file2.setFileKey("xxx");
        file2.setName("xxx.xlsx");
        file2.setSize("6577");
        cbFileList.add(file2);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, cbFileList);

        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tableItem1 = new SubTableValueItem();
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(testman1);
        userList.add(testman2);
        addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
        tableItem1.setID(3016494);
        HashMap<String, FieldValue> tableItemValue1 = new HashMap<String, FieldValue>();
        tableItemValue1 = addField(tableItemValue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tableItemValue1 = addField(tableItemValue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue1 = addField(tableItemValue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text1");
        tableItem1.setValue(tableItemValue1);
        subTable.add(tableItem1);
        SubTableValueItem tableItem2 = new SubTableValueItem();
        tableItem2.setID(3016497);
        HashMap<String, FieldValue> tableItemValue2 = new HashMap<String, FieldValue>();
        tableItemValue2 = addField(tableItemValue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");
        tableItemValue2 = addField(tableItemValue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue2 = addField(tableItemValue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text2");
        tableItem2.setValue(tableItemValue2);
        subTable.add(tableItem2);
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1234);
        testRecord = addField(testRecord, "計算_数値", FieldType.CALC, 1234);

        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("テスト１－１");
        categoryList.add("テスト１");
        categoryList.add("テスト２");
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, categoryList);
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "未処理");

        ArrayList<Member> assigneeList = new ArrayList<Member>();
        assigneeList.add(testman1);
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, assigneeList);
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "lookup1");
        // Main Test processing
        Integer id = 1;
        GetRecordResponse response = this.tokenRecordManagerment.getRecord(APP_ID, id);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();

        ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
        FileModel file1 = new FileModel();
        file1.setContentType("text/plain");
        file1.setFileKey("xxx");
        file1.setName("xxx.txt");
        file1.setSize("0");
        cbFileList.add(file1);
        FileModel file2 = new FileModel();
        file2.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        file2.setFileKey("xxx");
        file2.setName("xxx.xlsx");
        file2.setSize("6577");
        cbFileList.add(file2);
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, cbFileList);

        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tableItem1 = new SubTableValueItem();
        ArrayList<Member> userList = new ArrayList<Member>();
        userList.add(testman1);
        userList.add(testman2);
        addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
        tableItem1.setID(3016494);
        HashMap<String, FieldValue> tableItemValue1 = new HashMap<String, FieldValue>();
        tableItemValue1 = addField(tableItemValue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
        tableItemValue1 = addField(tableItemValue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue1 = addField(tableItemValue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text1");
        tableItem1.setValue(tableItemValue1);
        subTable.add(tableItem1);
        SubTableValueItem tableItem2 = new SubTableValueItem();
        tableItem2.setID(3016497);
        HashMap<String, FieldValue> tableItemValue2 = new HashMap<String, FieldValue>();
        tableItemValue2 = addField(tableItemValue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");
        tableItemValue2 = addField(tableItemValue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
        tableItemValue2 = addField(tableItemValue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text2");
        tableItem2.setValue(tableItemValue2);
        subTable.add(tableItem2);
        testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1234);
        testRecord = addField(testRecord, "計算_数値", FieldType.CALC, 1234);

        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("テスト１－１");
        categoryList.add("テスト１");
        categoryList.add("テスト２");
        testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, categoryList);
        testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "未処理");

        ArrayList<Member> assigneeList = new ArrayList<Member>();
        assigneeList.add(testman1);
        testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, assigneeList);
        testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "lookup1");
        // Main Test processing
        Integer id = 1;
        GetRecordResponse response = this.certRecordManagerment.getRecord(APP_ID, id);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordDefaultBlankApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "$id", FieldType.__ID__, 1);
        testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-08-29T08:41:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-08-29T08:41:00Z");
        testRecord = addField(testRecord, "レコード番号", FieldType.RECORD_NUMBER, 1);

        // Main Test processing
        Integer recordId = 1;
        GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(APP_ID, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordDefaultBlankAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "$id", FieldType.__ID__, 1575);
        testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-09-02T05:52:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-09-02T05:52:00Z");
        testRecord = addField(testRecord, "レコード番号", FieldType.RECORD_NUMBER, 1575);

        // Main Test processing
        Integer recordId = 1575;
        GetRecordResponse response = this.blankAppApiTokenRecordManagerment.getRecord(APP_ID, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordDefaultBlankAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "$id", FieldType.__ID__, 1575);
        testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
        testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-09-02T05:52:00Z");
        testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("cybozu", "cybozu"));
        testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-09-02T05:52:00Z");
        testRecord = addField(testRecord, "レコード番号", FieldType.RECORD_NUMBER, 1575);

        // Main Test processing
        Integer recordId = 1575;
        GetRecordResponse response = this.certRecordManagerment.getRecord(APP_ID, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
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
    public void testGetRecordNoPermissionFieldDoNotDisplay() throws KintoneAPIException {
        Integer recordId = 1;
        GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(APP_ID, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
        assertNull(resultRecord.get("数值"));
        assertEquals(9, resultRecord.size());
    }

    @Test
    public void testGetRecordNoPermissionFieldDoNotDisplayCert() throws KintoneAPIException {
        Integer recordId = 1;
        GetRecordResponse response = this.certRecordManagerment.getRecord(APP_ID, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
        assertNull(resultRecord.get("数值"));
        assertEquals(9, resultRecord.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutApp() throws KintoneAPIException {
        Integer id = 1;
        this.passwordAuthRecordManagerment.getRecord(null, id);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutAppToken() throws KintoneAPIException {
        Integer id = 1;
        this.tokenRecordManagerment.getRecord(null, id);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutAppCert() throws KintoneAPIException {
        Integer id = 1;
        this.certRecordManagerment.getRecord(null, id);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(100000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(100000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(100000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdNegative() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(-1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdNegativeToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(-1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdNegativeCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(-1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordAppIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutRecord() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutRecordToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWithoutRecordCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(APP_ID, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(APP_ID, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(APP_ID, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdNegative() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(APP_ID, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdNegativeToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(APP_ID, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdNegativeCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(APP_ID, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(APP_ID, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecord(APP_ID, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordRecordIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(APP_ID, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWhenDoNotHavePermissionOfApp() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecord(1632, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWhenDoNotHavePermissionOfAppToken() throws KintoneAPIException {
        this.noViewPermissionTokenRecordManagerment.getRecord(1632, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWhenDoNotHavePermissionOfAppCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecord(1632, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWhenDoNotHavePermissionOfRecord() throws KintoneAPIException {
        Integer appId = 1634;
        Integer recordId = 1;
        this.passwordAuthRecordManagerment.getRecord(appId, recordId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordWhenDoNotHavePermissionOfRecordCert() throws KintoneAPIException {
        Integer appId = 1634;
        Integer recordId = 1;
        this.certRecordManagerment.getRecord(appId, recordId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailInGuestSpace() throws KintoneAPIException {
        this.guestAuthRecordManagerment.getRecord(APP_ID, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailInGuestSpaceToken() throws KintoneAPIException {
        this.tokenGuestRecordManagerment.getRecord(APP_ID, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailInGuestSpaceCert() throws KintoneAPIException {
        this.certGuestRecordManagerment.getRecord(APP_ID, 1);
    }
}
