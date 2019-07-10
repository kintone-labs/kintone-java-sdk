package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.CommentMention;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateItem;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.RecordUpdateResponseItem;
import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.file.File;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class RecordTest {
    private static Integer APP_ID = 13;
    private static String API_TOKEN = "xxx";
    private static String HA_API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
    private static String NO_ADD_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String BLANK_APP_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String ANOTHER_GUEST_SPACE_API_TOKEN = "xxx";
    private static String PROHIBIT_DUPLICATE_API_TOKEN = "xxx";
    private static String REQUIRED_FIELD_API_TOKEN = "xxx";
    private static String NO_ADMIN_PERMISSION_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";
    private static String NO_EDIT_PERMISSION_API_TOKEN = "xxx";
    private static String LOCAL_LANGUAGE_API_TOKEN = "xxx";
    private static String NO_SET_ASSIGNEE_API_TOKEN = "xxx";
    private static String NO_MANAGE_PERMISSION_API_TOKEN = "xxx";
    private static String CURSOR_API_TOKEN = "7vaIstX8BZXZtx4gpMOsPAre0MRMMT27EmXU7xRe";
    private static String CURSOR_API_TOKEN2 = "ejr6E9N1bMdjxE6mfPUrcoyF18G7uiib6Gx4SYLh";
    private static String CURSOR_API_TOKEN3 = "Bks8u0SBzs5PnxbpWe9Th8RBtfnPNprAfc16ztJZ";
    private static String CURSOR_API_TOKEN4 = "XnJVb1xNJRyj9EzXaztgRfwbjCnIGYnoCeeDTlZQ";
    private static String CURSOR_API_TOKEN5 = "p6y25NVbNLAHJPi2j3KqJVTMGtf385LiVJjZ2OIZ";

    private static Member testman1 = new Member("testman1", "testman1");
    private static Member testman2 = new Member("testman2", "testman2");
    private static Member testgroup1 = new Member("testgroup1", "testgroup1");
    private static Member testgroup2 = new Member("testgroup2", "testgroup2");
    private static Member testorg1 = new Member("testorg1", "testorg1");
    private static Member testorg2 = new Member("testorg2", "testorg2");
    private static Member testAdimin = new Member("xxx", "xxx");
    private static Member testTokenAdimin = new Member("xxx", "xxx");
    private static Member testCertAdimin = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record cursorPasswordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noViewPermissionTokenRecordManagerment;
    private Record noAddPermissionTokenReocrdManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record blankAppApiTokenRecordManagerment;
    private Record prohibitDuplicateTokenRecordManagerment;
    private Record requiredFieldTokenRecordManagerment;
    private Record noAdminPermissionRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
    private Record noEditPermissionRecordManagerment;
    private Record localLanguageRecordManagerment;
    private Record noSetAssigneeRecordManagerment;
    private Record noManagePermissionRecordManagerment;
    private Record tokenGuestRecordManagerment;
    private Record certRecordManagerment;
    private Record certGuestRecordManagerment;
    private Record cursorTokenRecordManagerment;
    private Record cursorRestrictedTokenRecordManagerment;
    private Record cursorAddOnlyTokenRecordManagerment;
    private Record cursorReadOnlyTokenRecordManagerment;
    private Record cursorManageOnlyTokenRecordManagerment;
    private Record cursorCertRecordManagerment;
    private Record cursorRestrictedCertRecordManagerment;
    private RecordCursor tokenAuthRecordCursor;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);
        
        Auth passwordAuth2 = new Auth();
        passwordAuth2.setPasswordAuth("testman2", "cybozu");
        Connection passwordAuthConnection2 = new Connection(TestConstants.DOMAIN, passwordAuth2);
        this.cursorPasswordAuthRecordManagerment = new Record(passwordAuthConnection2);

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

        Auth tokenAuth7 = new Auth();
        tokenAuth7.setApiToken(NO_ADMIN_PERMISSION_API_TOKEN);
        Connection tokenConnection7 = new Connection(TestConstants.DOMAIN, tokenAuth7);
        this.noAdminPermissionRecordManagerment = new Record(tokenConnection7);

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstants.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

        Auth tokenAuth9 = new Auth();
        tokenAuth9.setApiToken(NO_EDIT_PERMISSION_API_TOKEN);
        Connection tokenConnection9 = new Connection(TestConstants.DOMAIN, tokenAuth9);
        this.noEditPermissionRecordManagerment = new Record(tokenConnection9);

        Auth tokenAuth10 = new Auth();
        tokenAuth10.setApiToken(LOCAL_LANGUAGE_API_TOKEN);
        Connection tokenConnection10 = new Connection(TestConstants.DOMAIN, tokenAuth10);
        this.localLanguageRecordManagerment = new Record(tokenConnection10);

        Auth tokenAuth11 = new Auth();
        tokenAuth11.setApiToken(NO_SET_ASSIGNEE_API_TOKEN);
        Connection tokenConnection11 = new Connection(TestConstants.DOMAIN, tokenAuth11);
        this.noSetAssigneeRecordManagerment = new Record(tokenConnection11);

        Auth tokenAuth12 = new Auth();
        tokenAuth12.setApiToken(NO_MANAGE_PERMISSION_API_TOKEN);
        Connection tokenConnection12 = new Connection(TestConstants.DOMAIN, tokenAuth12);
        this.noManagePermissionRecordManagerment = new Record(tokenConnection12);
        
        Auth tokenAuth13 = new Auth();
        tokenAuth13.setApiToken(CURSOR_API_TOKEN);
        Connection tokenConnection13 = new Connection(TestConstants.DOMAIN, tokenAuth13);
        this.cursorTokenRecordManagerment = new Record(tokenConnection13);
        
        Auth tokenAuth14 = new Auth();
        tokenAuth14.setApiToken(CURSOR_API_TOKEN2);
        Connection tokenConnection14 = new Connection(TestConstants.DOMAIN, tokenAuth14);
        this.cursorRestrictedTokenRecordManagerment = new Record(tokenConnection14);
        
        Auth tokenAuth15 = new Auth();
        tokenAuth15.setApiToken(CURSOR_API_TOKEN3);
        Connection tokenConnection15 = new Connection(TestConstants.DOMAIN, tokenAuth15);
        this.cursorAddOnlyTokenRecordManagerment = new Record(tokenConnection15);
        
        Auth tokenAuth16 = new Auth();
        tokenAuth16.setApiToken(CURSOR_API_TOKEN4);
        Connection tokenConnection16 = new Connection(TestConstants.DOMAIN, tokenAuth16);
        this.cursorReadOnlyTokenRecordManagerment = new Record(tokenConnection16);
        
        Auth tokenAuth17 = new Auth();
        tokenAuth17.setApiToken(CURSOR_API_TOKEN5);
        Connection tokenConnection17 = new Connection(TestConstants.DOMAIN, tokenAuth17);
        this.cursorManageOnlyTokenRecordManagerment = new Record(tokenConnection17);

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
        
        Auth cursorCertauth = new Auth();
        cursorCertauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        cursorCertauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection cursorCertConnection = new Connection(TestConstants.SECURE_DOMAIN, cursorCertauth);
        this.cursorCertRecordManagerment = new Record(cursorCertConnection);
        
        Auth cursorCertauth2 = new Auth();
        cursorCertauth2.setPasswordAuth("testman2", "cybozu");
        cursorCertauth2.setClientCertByPath("src/test/resources/certificates/testUser/testman2.pfx", "jvnunx0x");
        Connection cursorCertConnection2 = new Connection(TestConstants.SECURE_DOMAIN, cursorCertauth2);
        this.cursorRestrictedCertRecordManagerment = new Record(cursorCertConnection2);
        
        

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
        testRecord = addField(testRecord, "创建人", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "创建时间", FieldType.CREATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "更新人", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新时间", FieldType.UPDATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "记录编号", FieldType.RECORD_NUMBER, 1);

        // Main Test processing
        Integer appid = 1633;
        Integer recordId = 1;
        GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(appid, recordId);
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
        testRecord = addField(testRecord, "$id", FieldType.__ID__, 1);
        testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
        testRecord = addField(testRecord, "创建人", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "创建时间", FieldType.CREATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "更新人", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新时间", FieldType.UPDATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "记录编号", FieldType.RECORD_NUMBER, 1);

        // Main Test processing
        Integer appid = 1633;
        Integer recordId = 1;
        GetRecordResponse response = this.blankAppApiTokenRecordManagerment.getRecord(appid, recordId);
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
        testRecord = addField(testRecord, "$id", FieldType.__ID__, 1);
        testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
        testRecord = addField(testRecord, "创建人", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "创建时间", FieldType.CREATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "更新人", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        testRecord = addField(testRecord, "更新时间", FieldType.UPDATED_TIME, "2018-08-22T06:30:00Z");
        testRecord = addField(testRecord, "记录编号", FieldType.RECORD_NUMBER, 1);

        // Main Test processing
        Integer appid = 1633;
        Integer recordId = 1;
        GetRecordResponse response = this.certRecordManagerment.getRecord(appid, recordId);
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
        Integer appid = 1635;
        Integer recordId = 1;
        GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(appid, recordId);
        HashMap<String, FieldValue> resultRecord = response.getRecord();
        assertNull(resultRecord.get("数值"));
        assertEquals(9, resultRecord.size());
    }

    @Test
    public void testGetRecordNoPermissionFieldDoNotDisplayCert() throws KintoneAPIException {
        Integer appid = 1635;
        Integer recordId = 1;
        GetRecordResponse response = this.certRecordManagerment.getRecord(appid, recordId);
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

    @Test
    public void testGetRecords() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testGetRecordsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testGetRecordsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, query, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        this.passwordAuthRecordManagerment.getRecords(null, query, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        this.tokenRecordManagerment.getRecords(null, query, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        this.certRecordManagerment.getRecords(null, query, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecords(100000, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecords(100000, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecords(100000, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdNegative() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecords(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdNegativeToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecords(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdNegativeCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecords(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecords(0, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecords(0, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsAppIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecords(0, null, null, null);
    }

    @Test
    public void testGetRecordsWithoutQuery() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertTrue((Integer) 3 <= response.getTotalCount());
        assertTrue(3 <= resultRecords.size());
    }

    @Test
    public void testGetRecordsWithoutQueryToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, null, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertTrue((Integer) 3 <= response.getTotalCount());
        assertTrue(3 <= resultRecords.size());
    }

    @Test
    public void testGetRecordsWithoutQueryCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, null, null, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertTrue((Integer) 3 <= response.getTotalCount());
        assertTrue(3 <= resultRecords.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidQuery() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getRecords(APP_ID, "aaa", null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidQueryToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getRecords(APP_ID, "aaa", null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidQueryCert() throws KintoneAPIException {
        this.certRecordManagerment.getRecords(APP_ID, "aaa", null, null);
    }

    @Test
    public void testGetRecordsWithFields() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        assertEquals(1, resultRecords.get(0).size());
        assertEquals(String.valueOf(testRecord1.get("数値").getValue()), resultRecords.get(0).get("数値").getValue());
        assertEquals(1, resultRecords.get(1).size());
        assertEquals(String.valueOf(testRecord2.get("数値").getValue()), resultRecords.get(1).get("数値").getValue());
        assertEquals(1, resultRecords.get(2).size());
        assertEquals(String.valueOf(testRecord3.get("数値").getValue()), resultRecords.get(2).get("数値").getValue());
    }

    @Test
    public void testGetRecordsWithFieldsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        assertEquals(1, resultRecords.get(0).size());
        assertEquals(String.valueOf(testRecord1.get("数値").getValue()), resultRecords.get(0).get("数値").getValue());
        assertEquals(1, resultRecords.get(1).size());
        assertEquals(String.valueOf(testRecord2.get("数値").getValue()), resultRecords.get(1).get("数値").getValue());
        assertEquals(1, resultRecords.get(2).size());
        assertEquals(String.valueOf(testRecord3.get("数値").getValue()), resultRecords.get(2).get("数値").getValue());
    }

    @Test
    public void testGetRecordsWithFieldsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) 3, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        assertEquals(1, resultRecords.get(0).size());
        assertEquals(String.valueOf(testRecord1.get("数値").getValue()), resultRecords.get(0).get("数値").getValue());
        assertEquals(1, resultRecords.get(1).size());
        assertEquals(String.valueOf(testRecord2.get("数値").getValue()), resultRecords.get(1).get("数値").getValue());
        assertEquals(1, resultRecords.get(2).size());
        assertEquals(String.valueOf(testRecord3.get("数値").getValue()), resultRecords.get(2).get("数値").getValue());
    }

    @Test
    public void testGetRecordByBigBody() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            fields.add("test");
        }
        this.passwordAuthRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test
    public void testGetRecordByBigBodyToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            fields.add("test");
        }
        this.tokenRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test
    public void testGetRecordByBigBodyCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            fields.add("test");
        }
        this.certRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.passwordAuthRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidFieldsToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.tokenRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordShouldFailWhenGivenInvalidFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.certRecordManagerment.getRecords(APP_ID, null, fields, false);
    }

    @Test
    public void testGetRecordsWithoutTotal() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(null, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testGetRecordsWithoutTotalToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(null, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testGetRecordsWithoutTotalCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(null, response.getTotalCount());
        assertEquals(3, resultRecords.size());
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
        }
        for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
            assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
            Object expectedValue;
            if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                expectedValue = entry.getValue().getValue();
            } else {
                expectedValue = String.valueOf(entry.getValue().getValue());
            }
            assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
        }
    }

    @Test
    public void testGetRecordsWhenCountFalse() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, false);
        assertNotNull(recordRep);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertNotNull(records);
        assertNull(recordRep.getTotalCount());
    }

    @Test
    public void testGetRecordsWhenCountFalseToken() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.tokenRecordManagerment.getRecords(APP_ID, null, null, false);
        assertNotNull(recordRep);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertNotNull(records);
        assertNull(recordRep.getTotalCount());
    }

    @Test
    public void testGetRecordsWhenCountFalseCert() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.certRecordManagerment.getRecords(APP_ID, null, null, false);
        assertNotNull(recordRep);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertNotNull(records);
        assertNull(recordRep.getTotalCount());
    }

    @Test
    public void testGetRecordsLimitZeroAndCountTrue() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.passwordAuthRecordManagerment.getRecords(APP_ID, "limit 0", null, true);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertEquals(0, records.size());
        assertNotNull(recordRep.getTotalCount());
    }

    @Test
    public void testGetRecordsLimitZeroAndCountTrueToken() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.tokenRecordManagerment.getRecords(APP_ID, "limit 0", null, true);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertEquals(0, records.size());
        assertNotNull(recordRep.getTotalCount());
    }

    @Test
    public void testGetRecordsLimitZeroAndCountTrueCert() throws KintoneAPIException {
        GetRecordsResponse recordRep = this.certRecordManagerment.getRecords(APP_ID, "limit 0", null, true);
        ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
        assertEquals(0, records.size());
        assertNotNull(recordRep.getTotalCount());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWhenDoNotHavePermissionOfApp() throws KintoneAPIException {
        Integer appId = 1632;
        this.passwordAuthRecordManagerment.getRecords(appId, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWhenDoNotHavePermissionOfAppToken() throws KintoneAPIException {
        Integer appId = 1632;
        this.noViewPermissionTokenRecordManagerment.getRecords(appId, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsWhenDoNotHavePermissionOfAppCert() throws KintoneAPIException {
        Integer appId = 1632;
        this.certRecordManagerment.getRecords(appId, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailInGuestSpace() throws KintoneAPIException {
        this.guestAuthRecordManagerment.getRecords(APP_ID, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailInGuestSpaceToken() throws KintoneAPIException {
        this.tokenGuestRecordManagerment.getRecords(APP_ID, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailInGuestSpaceCert() throws KintoneAPIException {
        this.certGuestRecordManagerment.getRecords(APP_ID, null, null, null);
    }

    @Test
    public void testGetRecordsNoPermissionFieldDoNotDisplay() throws KintoneAPIException {
        Integer appid = 1635;
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(appid, null, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        for (HashMap<String, FieldValue> hashMap : resultRecords) {
            assertEquals(9, hashMap.size());
        }
    }

    @Test
    public void testGetRecordsNoPermissionFieldDoNotDisplayCert() throws KintoneAPIException {
        Integer appid = 1635;
        GetRecordsResponse response = this.certRecordManagerment.getRecords(appid, null, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        for (HashMap<String, FieldValue> hashMap : resultRecords) {
            assertEquals(9, hashMap.size());
        }
    }

    @Test
    public void testGetRecordsTheTotalCountShould500() throws KintoneAPIException {
        String query = "limit 500";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(500, resultRecords.size());
    }

    @Test
    public void testGetRecordsTheTotalCountShould500Token() throws KintoneAPIException {
        String query = "limit 500";
        GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(500, resultRecords.size());
    }

    @Test
    public void testGetRecordsTheTotalCountShould500Cert() throws KintoneAPIException {
        String query = "limit 500";
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, query, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(500, resultRecords.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailThenLimitOver500() throws KintoneAPIException {
        String query = "limit 501";
        this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailThenLimitOver500Token() throws KintoneAPIException {
        String query = "limit 501";
        this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShouldFailThenLimitOver500Cert() throws KintoneAPIException {
        String query = "limit 501";
        this.certRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidLimit() throws KintoneAPIException {
        String query = "limit -1";
        this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidLimitToken() throws KintoneAPIException {
        String query = "limit -1";
        this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidLimitCert() throws KintoneAPIException {
        String query = "limit -1";
        this.certRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidOffset() throws KintoneAPIException {
        String query = "offset -1";
        this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidOffsetToken() throws KintoneAPIException {
        String query = "offset -1";
        this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInvalidOffsetCert() throws KintoneAPIException {
        String query = "offset -1";
        this.certRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInactiveUser() throws KintoneAPIException {
        String query = "ユーザー選択 in (\" USER\", \"xxx xxx\")";
        this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInactiveUserToken() throws KintoneAPIException {
        String query = "ユーザー選択 in (\" USER\", \"xxx xxx\")";
        this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRecordsShowFailGivenInactiveUserCert() throws KintoneAPIException {
        String query = "ユーザー選択 in (\" USER\", \"xxx xxx\")";
        this.certRecordManagerment.getRecords(APP_ID, query, null, null);
    }
    
    @Test
    public void testGetAllRecordsByCursor() throws KintoneAPIException {
        // Before processing
    	int totalRecordToAdd = 600;
		int limitRecordToAddPerResquest = 100;
    	ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd/limitRecordToAddPerResquest)) {
        	int j = 0;
        	ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
        	while (j < limitRecordToAddPerResquest) {
            	HashMap<String, FieldValue> testRecord = createTestRecord();
            	records.add(testRecord);
            	recordsToAdd.add(testRecord);
    			j ++;
    		}
        	j = 0;
        	this.passwordAuthRecordManagerment.addRecords(APP_ID, recordsToAdd);
        	i ++;
        }
        
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getAllRecordsByCursor(APP_ID, query, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
        	for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
        	index ++;
		}
    }
    
    @Test
    public void testGetAllRecordsByCursorToken() throws KintoneAPIException {
        // Before processing
        int totalRecordToAdd = 600;
        int limitRecordToAddPerResquest = 100;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd/limitRecordToAddPerResquest)) {
            int j = 0;
            ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
            while (j < limitRecordToAddPerResquest) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                recordsToAdd.add(testRecord);
                j ++;
            }
            j = 0;
            this.cursorTokenRecordManagerment.addRecords(APP_ID, recordsToAdd);
            i ++;
        }
        
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.cursorTokenRecordManagerment.getAllRecordsByCursor(APP_ID, query, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
            for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
            index ++;
        }
    }
    
    @Test
    public void testGetAllRecordsByCursorCert() throws KintoneAPIException {
        // Before processing
        int totalRecordToAdd = 600;
        int limitRecordToAddPerResquest = 100;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < (totalRecordToAdd/limitRecordToAddPerResquest)) {
            int j = 0;
            ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
            while (j < limitRecordToAddPerResquest) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                recordsToAdd.add(testRecord);
                j ++;
            }
            j = 0;
            this.cursorCertRecordManagerment.addRecords(APP_ID, recordsToAdd);
            i ++;
        }
        
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse response = this.cursorCertRecordManagerment.getAllRecordsByCursor(APP_ID, query, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals((Integer) records.size(), response.getTotalCount());
        assertEquals(records.size(), resultRecords.size());
        int index = 0;
        for (HashMap<String, FieldValue> record : records) {
            for (Entry<String, FieldValue> entry : record.entrySet()) {
                assertEquals(entry.getValue().getType(), resultRecords.get(index).get(entry.getKey()).getType());
                Object expectedValue;
                if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
                    expectedValue = entry.getValue().getValue();
                } else {
                    expectedValue = String.valueOf(entry.getValue().getValue());
                }
                assertEquals(expectedValue, resultRecords.get(index).get(entry.getKey()).getValue());
            }
            index ++;
        }
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWithSelectedFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行");
        String query = "数値 =2";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getAllRecordsByCursor(14, query, fields);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();

        assertEquals(1, resultRecords.size());
        HashMap<String, FieldValue> hashMap = resultRecords.get(0);
        assertTrue(hashMap.containsKey("文字列__1行"));
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWithSelectedFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行");
        String query = "数値 =2";
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getAllRecordsByCursor(14, query, fields);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();

        assertEquals(1, resultRecords.size());
        HashMap<String, FieldValue> hashMap = resultRecords.get(0);
        assertTrue(hashMap.containsKey("文字列__1行"));
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWithUnexistedFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__2行");
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getAllRecordsByCursor(14, null, fields);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWithUnexistedFieldsToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行");
        GetRecordsResponse response = this.cursorRestrictedTokenRecordManagerment.getAllRecordsByCursor(14, null, fields);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(0, resultRecords.size());
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWithUnexistedFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行");
        GetRecordsResponse response = this.cursorCertRecordManagerment.getAllRecordsByCursor(14, null, fields);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(1, resultRecords.size());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithInvalidQuery() throws KintoneAPIException {
        String query = new String();
        query = "offset 0";
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(13, query, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithInvalidQueryToken() throws KintoneAPIException {
        String query = new String();
        query = "offset 0";
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(13, query, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithInvalidQueryCert() throws KintoneAPIException {
        String query = new String();
        query = "offset 0";
        this.cursorCertRecordManagerment.getAllRecordsByCursor(13, query, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppOverThousand() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(13, null, fields);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppOverThousandToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(13, null, fields);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppOverThousandCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        for (int i = 0; i <= 1000; i++) {
            fields.add("test");
        }
        this.cursorCertRecordManagerment.getAllRecordsByCursor(13, null, fields);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppNull() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(null, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppNullToken() throws KintoneAPIException {
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(null, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenAppNullCert() throws KintoneAPIException {
        this.cursorCertRecordManagerment.getAllRecordsByCursor(null, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenUsingAddToken() throws KintoneAPIException {
        this.cursorAddOnlyTokenRecordManagerment.getAllRecordsByCursor(APP_ID, null, null);
    }
    
    @Test
    public void testGetAllRecordsByCursorShouldSuccessWhenUsingReadToken() throws KintoneAPIException {
        this.cursorReadOnlyTokenRecordManagerment.getAllRecordsByCursor(APP_ID, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWhenUsingManageToken() throws KintoneAPIException {
        this.cursorManageOnlyTokenRecordManagerment.getAllRecordsByCursor(APP_ID, null, null);
    }
    
    @Test//(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldSuccessWhenHasNoPermission() throws KintoneAPIException {
        this.cursorPasswordAuthRecordManagerment.getAllRecordsByCursor(14, null, null);
    }
    
    @Test//(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldSuccessWhenHasNoPermissionCert() throws KintoneAPIException {
        this.cursorRestrictedCertRecordManagerment.getAllRecordsByCursor(14, null, null);
    }
    
    @Test//(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithUnexistedAppID() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(99999, null, null);
    }
    
    @Test//(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithUnexistedAppIDToken() throws KintoneAPIException {
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(99999, null, null);
    }
    
    @Test//(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithUnexistedAppIDCert() throws KintoneAPIException {
        this.cursorCertRecordManagerment.getAllRecordsByCursor(99999, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailGivenInvalidAppID() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(-1, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailGivenInvalidAppIDToken() throws KintoneAPIException {
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(-1, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailGivenInvalidAppIDCert() throws KintoneAPIException {
        this.cursorCertRecordManagerment.getAllRecordsByCursor(-1, null, null);
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
        testRecord = addField(testRecord, "附件", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(1631, response.getID());
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
        testRecord = addField(testRecord, "附件", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(1631, response.getID());
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
        testRecord = addField(testRecord, "附件", FieldType.FILE, al);
        // Main Test processing
        AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());

        GetRecordResponse rp = guestRecord.getRecord(1631, response.getID());
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
        AddRecordResponse response = this.guestAuthRecordManagerment.addRecord(1631, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse response = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse response = this.certGuestRecordManagerment.addRecord(1631, testRecord);
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
    public void testAddRecordShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.addRecord(1632, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.noAddPermissionTokenReocrdManagerment.addRecord(1632, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.addRecord(1632, testRecord);
    }

    @Test
    public void testAddRecordShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(1634, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.addNoViewTokenRecordManagerment.addRecord(1634, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessWheDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        AddRecordResponse response = this.certRecordManagerment.addRecord(1634, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.passwordAuthRecordManagerment.addRecord(1635, testRecord);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        this.certRecordManagerment.addRecord(1635, testRecord);
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankApp() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(1633, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.blankAppApiTokenRecordManagerment.addRecord(1633, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
    }

    @Test
    public void testAddRecordShouldSuccessUseBlankAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
        AddRecordResponse response = this.certRecordManagerment.addRecord(1633, testRecord);
        assertTrue(response.getID() instanceof Integer);
        assertEquals((Integer) 1, response.getRevision());
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
        testRecord1 = addField(testRecord1, "附件", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "附件", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(1631, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(1631, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(1631, response.getIDs().get(1));
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
        testRecord1 = addField(testRecord1, "附件", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "附件", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(1631, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(1631, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(1631, response.getIDs().get(1));
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
        testRecord1 = addField(testRecord1, "附件", FieldType.FILE, al1);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "附件", FieldType.FILE, al2);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        // Main Test processing
        AddRecordsResponse response = guestRecord.addRecords(1631, records);
        assertEquals(2, response.getIDs().size());
        assertTrue(response.getIDs().get(0) instanceof Integer);
        assertTrue(response.getIDs().get(1) instanceof Integer);
        assertEquals(2, response.getRevisions().size());
        assertEquals((Integer) 1, response.getRevisions().get(0));
        assertEquals((Integer) 1, response.getRevisions().get(1));

        GetRecordResponse rp1 = guestRecord.getRecord(1631, response.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = guestRecord.getRecord(1631, response.getIDs().get(1));
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
        AddRecordsResponse response = this.guestAuthRecordManagerment.addRecords(1631, records);
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
        AddRecordsResponse response = this.tokenGuestRecordManagerment.addRecords(1631, records);
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
        AddRecordsResponse response = this.certGuestRecordManagerment.addRecords(1631, records);
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
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordByID(1631, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void tesUpdateRecordByIDInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordByID(1631, id, testRecord,
                revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void tesUpdateRecordByIDInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        // Main Test processing
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordByID(1631, id, testRecord,
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
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
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
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.noAddPermissionTokenReocrdManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.certRecordManagerment.updateRecordByID(1632, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.addNoViewTokenRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
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
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1656, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordByUpdateKey(1656, updateKey,
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
        AddRecordResponse addResponse = guestRecord.addRecord(1656, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = guestRecord.updateRecordByUpdateKey(1656, updateKey, updateRecord, revision);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeyInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1656, testRecord);
        Integer revision = addResponse.getRevision();
        // Main Test processing
        RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordByUpdateKey(1656, updateKey,
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
        updateRecord = addField(updateRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
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
        updateRecord = addField(updateRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
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
        updateRecord = addField(updateRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
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
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        this.prohibitDuplicateTokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
        HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
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
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.noAddPermissionTokenReocrdManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.certRecordManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecordToken()
            throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.addNoViewTokenRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecordCert()
            throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.certRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
        this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1635, updateKey, updateRecord, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
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

    @Test
    public void testUpdateRecords() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKey() throws KintoneAPIException {
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
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKeyToken() throws KintoneAPIException {
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
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsByKeyCert() throws KintoneAPIException {
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
        RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
        RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

        HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
        updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
        updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOne() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOneToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsRevisionNegativeOneCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWrongRevisionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtcToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsChangeCreatorEtcCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
        HashMap<String, FieldValue> testRecord4 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        records.add(testRecord4);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
        testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("cyuan", "cyuan"));
        testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
        testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("cyuan", "cyuan"));
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
        RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
        updateItems.add(item1);
        updateItems.add(item2);
        updateItems.add(item3);
        updateItems.add(item4);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.noAddPermissionTokenReocrdManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfAppCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1632, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.addNoViewTokenRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecordCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1634, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "数值", FieldType.NUMBER, 123);
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "数值", FieldType.NUMBER, 123);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1635, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        updateRecord1 = addField(updateRecord1, "数值", FieldType.NUMBER, 123);
        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2 = addField(updateRecord2, "数值", FieldType.NUMBER, 123);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1635, updateItems);
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkip() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkipToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsInvalidFieldWillSkipCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certRecordManagerment.updateRecords(APP_ID, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void testUpdateRecordsWithAttachment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
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
    public void testUpdateRecordsWithAttachmentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);

        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
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
    public void testUpdateRecordsWithAttachmentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);

        Auth certauth = new Auth();
        certauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        File attachmet = new File(connection);

        FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al1 = new ArrayList<>();
        al1.add(file1);
        testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
        FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al2 = new ArrayList<>();
        al2.add(file2);
        testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
                ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
                assertEquals(1, alf.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
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
    public void testUpdateRecordsDataWithTable() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
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
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    public void testUpdateRecordsDataWithTableToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
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
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    public void testUpdateRecordsDataWithTableCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
        tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
        ArrayList<Member> userList1 = new ArrayList<Member>();
        userList1.add(new Member("cyuan", "cyuan"));
        tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
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
        // Main Test processing
        testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
        testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);

        GetRecordResponse rp1 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record1 = rp1.getRecord();
        for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
            assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
        GetRecordResponse rp2 = this.certRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
        HashMap<String, FieldValue> record2 = rp2.getRecord();
        for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
            assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
            if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
                ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
                assertEquals(1, al.size());
            }
        }
    }

    @Test
    public void tesUpdateRecordsInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(1631, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecords(1631, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void tesUpdateRecordsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenGuestRecordManagerment.addRecords(1631, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.tokenGuestRecordManagerment.updateRecords(1631, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test
    public void tesUpdateRecordsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(1631, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
        testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecords(1631, updateItems);
        ArrayList<RecordUpdateResponseItem> results = response.getRecords();
        assertEquals(2, results.size());
        assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
        assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
        assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailInputStringToNumberFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(APP_ID, records);
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(APP_ID, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.prohibitDuplicateTokenRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailFieldProhibitDuplicateCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.prohibitDuplicateTokenRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsdShouldFailInvalidValueOverMaximumcert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1636, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.requiredFieldTokenRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenDoNotSetRequiredFieldCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        // Main Test processing
        testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
        testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(1640, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexisted() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexistedToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDUnexistedCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(100000, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumber() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDNegativeNumberCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(-1, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZero() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZeroToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailAppIDZeroCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), 0, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), 0, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(0, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItems() throws KintoneAPIException {
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
        this.passwordAuthRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItemsToken() throws KintoneAPIException {
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
        this.tokenRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutItemsCert() throws KintoneAPIException {
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
        this.certRecordManagerment.updateRecords(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutApp() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.passwordAuthRecordManagerment.updateRecords(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutAppToken() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.tokenRecordManagerment.updateRecords(null, updateItems);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsWithoutAppCert() throws KintoneAPIException {
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
        testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
        testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
        ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
        RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
        updateItems.add(item1);
        updateItems.add(item2);
        this.certRecordManagerment.updateRecords(null, updateItems);
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1659, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(1659, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveEditPermissionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.noEditPermissionRecordManagerment.addRecords(1659, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.noEditPermissionRecordManagerment.deleteRecords(1659, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveEditPermissionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1659, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(1659, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveViewEditPermissionOfField() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);

        AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1635, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.passwordAuthRecordManagerment.deleteRecords(1635, ids);
    }

    @Test
    public void testDeleteRecordsSuccessNotHaveViewEditPermissionOfFieldCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);

        AddRecordsResponse addResponse = this.certRecordManagerment.addRecords(1635, records);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getIDs().get(0));
        ids.add(addResponse.getIDs().get(1));
        this.certRecordManagerment.deleteRecords(1635, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);

        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.guestAuthRecordManagerment.deleteRecords(1631, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.tokenGuestRecordManagerment.deleteRecords(1631, ids);
    }

    @Test
    public void tesDeleteRecordByIDInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(addResponse.getID());
        this.certGuestRecordManagerment.deleteRecords(1631, ids);
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
        AddRecordsResponse addResponse = this.guestAuthRecordManagerment.addRecords(1631, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.guestAuthRecordManagerment.deleteRecordsWithRevision(1631, idsWithRevision);
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
        AddRecordsResponse addResponse = this.tokenGuestRecordManagerment.addRecords(1631, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.tokenGuestRecordManagerment.deleteRecordsWithRevision(1631, idsWithRevision);
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
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(1631, records);
        // Main Test processing
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(addResponse.getIDs().get(0), -1);
        idsWithRevision.put(addResponse.getIDs().get(1), -1);
        this.certGuestRecordManagerment.deleteRecordsWithRevision(1631, idsWithRevision);
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        HashMap<String, FieldValue> testRecord2 = new HashMap<>();
        testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordAssignees(1631, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordAssignees(1631, id, assignees,
                null);
        assertEquals((Integer) (revision + 1), response.getRevision());
    }

    @Test
    public void testUpdateRecordAssigneesInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord1 = new HashMap<>();
        testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord1);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("yfang");
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordAssignees(1631, id, assignees,
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
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
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1661, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "yfang";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(1661, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusLocalLanguageToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.localLanguageRecordManagerment.addRecord(1661, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "yfang";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.localLanguageRecordManagerment.updateRecordStatus(1661, id, action,
                assignee, revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusLocalLanguageCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1661, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String assignee = "yfang";
        String action = "しょりかいし";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(1661, id, action, assignee,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(1662, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.noSetAssigneeRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.noSetAssigneeRecordManagerment.updateRecordStatus(1662, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusDoNotSetAssigneeCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1662, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "処理開始";
        UpdateRecordResponse response = this.certRecordManagerment.updateRecordStatus(1662, id, action, null, revision);
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
        passwordAuth.setPasswordAuth("user1", "user1");
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
        passwordAuth.setPasswordAuth("user1", "user1");
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
        certauth.setPasswordAuth("user1", "user1");
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
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "开始处理";
        UpdateRecordResponse response = this.guestAuthRecordManagerment.updateRecordStatus(1631, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusInGuestToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "开始处理";
        UpdateRecordResponse response = this.tokenGuestRecordManagerment.updateRecordStatus(1631, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
    }

    @Test
    public void testUpdateRecordStatusInGuestCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();
        String action = "开始处理";
        UpdateRecordResponse response = this.certGuestRecordManagerment.updateRecordStatus(1631, id, action, null,
                revision);
        assertEquals((Integer) (revision + 2), response.getRevision());
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
        AddRecordsResponse addResponse = this.guestAuthRecordManagerment.addRecords(1631, records);
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

        UpdateRecordsResponse response = this.guestAuthRecordManagerment.updateRecordsStatus(1631, updateItems);
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
        AddRecordsResponse addResponse = this.tokenGuestRecordManagerment.addRecords(1631, records);
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

        UpdateRecordsResponse response = this.tokenGuestRecordManagerment.updateRecordsStatus(1631, updateItems);
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
        AddRecordsResponse addResponse = this.certGuestRecordManagerment.addRecords(1631, records);
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

        UpdateRecordsResponse response = this.certGuestRecordManagerment.updateRecordsStatus(1631, updateItems);
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

    @Test
    public void testAddComment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentsInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.guestAuthRecordManagerment.addComment(1631, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse response = this.certGuestRecordManagerment.addComment(1631, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentAndCheckComment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentAndCheckCommentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
        assertEquals(testman2.getName() + " \ntest comment ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentAndCheckCommentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addCommentResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testCertAdimin, response.getComments().get(0).getCreator());
        assertEquals(testman2.getName() + " \ntest comment ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithGroup() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();

        CommentMention groupMention = new CommentMention();
        groupMention.setCode(testgroup1.getCode());
        groupMention.setType("GROUP");
        mentionList.add(groupMention);
        comment.setText("test comment group");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testgroup1.getName() + " \ntest comment group ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithGroupToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();

        CommentMention groupMention = new CommentMention();
        groupMention.setCode(testgroup1.getCode());
        groupMention.setType("GROUP");
        mentionList.add(groupMention);
        comment.setText("test comment group");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testgroup1.getName() + " \ntest comment group ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithGroupCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();

        CommentMention groupMention = new CommentMention();
        groupMention.setCode(testgroup1.getCode());
        groupMention.setType("GROUP");
        mentionList.add(groupMention);
        comment.setText("test comment group");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testgroup1.getName() + " \ntest comment group ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithOrganization() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention orgMention = new CommentMention();
        orgMention.setCode(testorg1.getCode());
        orgMention.setType("ORGANIZATION");
        mentionList.add(orgMention);
        comment.setText("test comment organization");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testorg1.getName() + " \ntest comment organization ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithOrganizationToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention orgMention = new CommentMention();
        orgMention.setCode(testorg1.getCode());
        orgMention.setType("ORGANIZATION");
        mentionList.add(orgMention);
        comment.setText("test comment organization");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testorg1.getName() + " \ntest comment organization ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithOrganizationCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention orgMention = new CommentMention();
        orgMention.setCode(testorg1.getCode());
        orgMention.setType("ORGANIZATION");
        mentionList.add(orgMention);
        comment.setText("test comment organization");
        comment.setMentions(mentionList);

        AddCommentResponse addCommentResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testorg1.getName() + " \ntest comment organization ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithoutMetion() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("test comment no metion");

        AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("test comment no metion ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithoutMetionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("test comment no metion");

        AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("test comment no metion ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentWithoutMetionCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("test comment no metion");

        AddCommentResponse addCommentResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("test comment no metion ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentSpecialCharacter() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("テスト");
        AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("テスト ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentSpecialCharacterToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("テスト");
        AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("テスト ", response.getComments().get(0).getText());
    }

    @Test
    public void testAddCommentSpecialCharacterCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("テスト");
        AddCommentResponse addCommentResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        assertNotNull(addCommentResponse.getId());

        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(1, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("テスト ", response.getComments().get(0).getText());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentBlank() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentBlankToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentBlankCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentNullCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMaxCharacter() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 65535; i++) {
            sb.append("a");
        }
        comment.setText(sb.toString());
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMaxCharacterToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 65535; i++) {
            sb.append("a");
        }
        comment.setText(sb.toString());
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMaxCharacterCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 65535; i++) {
            sb.append("a");
        }
        comment.setText(sb.toString());
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedUser() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedUserToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedUserCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMetionOverTen() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        for (int i = 0; i <= 10; i++) {
            CommentMention mention = new CommentMention();
            mention.setCode(testman1.getCode());
            mention.setType("USER");
            mentionList.add(mention);
        }
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMetionOverTenToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        for (int i = 0; i <= 10; i++) {
            CommentMention mention = new CommentMention();
            mention.setCode(testman1.getCode());
            mention.setType("USER");
            mentionList.add(mention);
        }
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentMetionOverTenCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        for (int i = 0; i <= 10; i++) {
            CommentMention mention = new CommentMention();
            mention.setCode(testman1.getCode());
            mention.setType("USER");
            mentionList.add(mention);
        }
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedGroup() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("GROUP");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedGroupToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("GROUP");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedGroupCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("GROUP");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedOrganization() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("ORGANIZATION");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedOrganizationToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("ORGANIZATION");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentUnexistedOrganizationCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("aaaaaaaaaaaa");
        mention.setType("ORGANIZATION");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(null, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(null, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(null, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppUnexisted() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(100000, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppUnexistedToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(100000, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppUnexistedCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(100000, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppNegativeNumber() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(-1, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppNegativeNumberToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(-1, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppNegativeNumberCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(-1, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppZero() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(0, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppZeroToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(0, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentAppZeroCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(0, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, null, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, null, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, null, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordUnexisted() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(APP_ID, 100000, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordUnexistedToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(APP_ID, 100000, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordUnexistedCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(APP_ID, 100000, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordNegativeNumber() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(APP_ID, -1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordNegativeNumberToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(APP_ID, -1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordNegativeNumberCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(APP_ID, -1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordZero() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.passwordAuthRecordManagerment.addComment(APP_ID, 0, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordZeroToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.tokenRecordManagerment.addComment(APP_ID, 0, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentRecordZeroCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("test comment");
        this.certRecordManagerment.addComment(APP_ID, 0, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutComment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutCommentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        this.tokenRecordManagerment.addComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentWithoutCommentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        this.certRecordManagerment.addComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionApp() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.passwordAuthRecordManagerment.addComment(1632, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionAppToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.noViewPermissionTokenRecordManagerment.addComment(1632, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionAppCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.certRecordManagerment.addComment(1632, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionRecord() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.passwordAuthRecordManagerment.addComment(1634, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionRecordToken() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.addNoViewTokenRecordManagerment.addComment(1634, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentDoNotHavePermissionRecordCert() throws KintoneAPIException {
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.certRecordManagerment.addComment(1634, 1, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentCommentOff() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.passwordAuthRecordManagerment.addComment(1640, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentCommentOffToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.requiredFieldTokenRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.requiredFieldTokenRecordManagerment.addComment(1640, id, comment);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddCommentCommentOffCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        comment.setText("yfang");
        this.certRecordManagerment.addComment(1640, id, comment);
    }

    @Test
    public void testGetComments() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.guestAuthRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment2");
        this.guestAuthRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment3");
        this.guestAuthRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment4");
        this.guestAuthRecordManagerment.addComment(1631, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.guestAuthRecordManagerment.getComments(1631, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("yfang" + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment2");
        this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment3");
        this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment4");
        this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenGuestRecordManagerment.getComments(1631, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("yfang" + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment2");
        this.certGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment3");
        this.certGuestRecordManagerment.addComment(1631, id, comment);
        comment.setText("test comment4");
        this.certGuestRecordManagerment.addComment(1631, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certGuestRecordManagerment.getComments(1631, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("yfang" + " \ntest comment2 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertTrue(response.getOlder());
        assertTrue(response.getNewer());
    }

    @Test
    public void testGetCommentsAsc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", null, null);
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsAscToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", null, null);
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsAscCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, "asc", null, null);
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDesc() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "desc", null, null);
        assertEquals(Integer.valueOf(2), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDescToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "desc", null, null);
        assertEquals(Integer.valueOf(2), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDescCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, "desc", null, null);
        assertEquals(Integer.valueOf(2), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetOffset() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, 5, null);
        assertEquals(1, response.getComments().size());
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetOffsetToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, 5, null);
        assertEquals(1, response.getComments().size());
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetOffsetCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.certRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, 5, null);
        assertEquals(1, response.getComments().size());
        assertEquals(Integer.valueOf(1), response.getComments().get(0).getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsOffsetLessThanZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsOffsetLessThanZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.tokenRecordManagerment.getComments(APP_ID, id, null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsOffsetLessThanZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.certRecordManagerment.getComments(APP_ID, id, null, -1, null);
    }

    @Test
    public void testGetCommentsSetLimit() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetLimitToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetLimitCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 6; i++) {
            this.certRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsSetLimitNoComment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(1, response.getComments().size());
    }

    @Test
    public void testGetCommentsSetLimitNoCommentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(1, response.getComments().size());
    }

    @Test
    public void testGetCommentsSetLimitNoCommentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, 5);
        assertEquals(1, response.getComments().size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsSetLimitOverTen() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 11);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsSetLimitOverTenToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 11);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsSetLimitOverTenCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.certRecordManagerment.getComments(APP_ID, id, null, null, 11);
    }

    @Test
    public void testGetCommentsAscOfflitLimit() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsAscOfflitLimitToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsAscOfflitLimitCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.certRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, "asc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDescOffsetLimit() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "desc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(5), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDescOffsetLimitToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "desc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(5), response.getComments().get(0).getId());
    }

    @Test
    public void testGetCommentsDescOffsetLimitCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        for (int i = 0; i < 10; i++) {
            this.certRecordManagerment.addComment(APP_ID, id, comment);
        }
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, "desc", 5, 5);
        assertEquals(5, response.getComments().size());
        assertEquals(Integer.valueOf(5), response.getComments().get(0).getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsInvalidOrder() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.passwordAuthRecordManagerment.getComments(APP_ID, id, "test", null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsInvalidOrderToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.tokenRecordManagerment.getComments(APP_ID, id, "test", null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsInvalidOrderCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.certRecordManagerment.getComments(APP_ID, id, "test", null, null);
    }

    @Test
    public void testGetCommentsWithoutOptions() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(4, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment4 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertFalse(response.getOlder());
        assertFalse(response.getNewer());
    }

    @Test
    public void testGetCommentsWithoutOptionsToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(4, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment4 ", response.getComments().get(0).getText());
        assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertFalse(response.getOlder());
        assertFalse(response.getNewer());
    }

    @Test
    public void testGetCommentsWithoutOptionsCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certRecordManagerment.getComments(APP_ID, id, null, null, null);
        assertEquals(4, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals(testman2.getName() + " \ntest comment4 ", response.getComments().get(0).getText());
        assertEquals(testAdimin, response.getComments().get(0).getCreator());
        assertEquals(mentionList, response.getComments().get(0).getMentions());
        assertFalse(response.getOlder());
        assertFalse(response.getNewer());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsCommentOff() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(1658, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsCommentOffToken() throws KintoneAPIException {
        this.noDeletePermissionRecordManagerment.getComments(1658, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsCommentOffCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(1658, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionApp() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(1632, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionAppToken() throws KintoneAPIException {
        this.noViewPermissionTokenRecordManagerment.getComments(1632, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionAppCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(1632, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionRecord() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(1634, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionRecordToken() throws KintoneAPIException {
        this.addNoViewTokenRecordManagerment.getComments(1634, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsDoNotHavePermissionRecordCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(1634, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutAppId() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(null, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutAppIdToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(null, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutAppIdCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(null, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(100000, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(100000, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(100000, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdNegativeNumber() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(-1, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdNegativeNumberToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(-1, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdNegativeNumberCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(-1, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(0, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(0, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsAppIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(0, 1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, 10000, null, null, null);
    }

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, 1000000, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, 10000, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdNegativeNumber() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, -1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdNegativeNumberToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, -1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdNegativeNumberCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, -1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, 0, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, 0, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsRecordIdZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, 0, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWrongOffset() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWrongOffsetToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, 1, null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWrongOffsetCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, 1, null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitNegativeNumber() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, null, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitNegativeNumberToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, 1, null, null, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitNegativeNumberCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, 1, null, null, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitOverTen() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, null, 11);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitOverTenToken() throws KintoneAPIException {
        this.tokenRecordManagerment.getComments(APP_ID, 1, null, null, 11);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsLimitOverTenCert() throws KintoneAPIException {
        this.certRecordManagerment.getComments(APP_ID, 1, null, null, 11);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.passwordAuthRecordManagerment.getComments(APP_ID, null, "asc", 1, 2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.tokenRecordManagerment.getComments(APP_ID, null, "asc", 1, 2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetCommentsWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment2");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment3");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        comment.setText("test comment4");
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.certRecordManagerment.getComments(APP_ID, null, "asc", 1, 2);
    }

    @Test
    public void testDeleteComment() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, commentId);
    }

    @Test
    public void testDeleteCommentToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.tokenRecordManagerment.deleteComment(APP_ID, id, commentId);
    }

    @Test
    public void testDeleteCommentCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.certRecordManagerment.deleteComment(APP_ID, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionApp() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(1632, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionAppToken() throws KintoneAPIException {
        this.noViewPermissionTokenRecordManagerment.deleteComment(1632, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionAppCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(1632, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionRecord() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(1634, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionRecordToken() throws KintoneAPIException {
        this.addNoViewTokenRecordManagerment.deleteComment(1634, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentDoNotHavePermissionRecordCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(1634, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutCommentId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutCommentIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.tokenRecordManagerment.deleteComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutCommentIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        this.certRecordManagerment.deleteComment(APP_ID, id, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDUnexisted() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDUnexistedToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.tokenRecordManagerment.deleteComment(APP_ID, id, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDUnexistedCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.certRecordManagerment.deleteComment(APP_ID, id, 100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDNegativeNumber() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDNegativeNumberToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.tokenRecordManagerment.deleteComment(APP_ID, id, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDNegativeNumberCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.certRecordManagerment.deleteComment(APP_ID, id, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDZero() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDZeroToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.tokenRecordManagerment.deleteComment(APP_ID, id, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentIDZeroCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);
        Integer id = addResponse.getID();
        this.certRecordManagerment.deleteComment(APP_ID, id, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutRecordId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, null, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutRecordIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.tokenRecordManagerment.deleteComment(APP_ID, null, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutRecordIdCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.certRecordManagerment.deleteComment(APP_ID, null, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, 1000000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(APP_ID, 1000000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(APP_ID, 1000000, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordNegativeNumber() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, -1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordNegativeNumberToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(APP_ID, -1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordNegativeNumberCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(APP_ID, -1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, 0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(APP_ID, 0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentRecordZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(APP_ID, 0, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutApp() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.passwordAuthRecordManagerment.deleteComment(null, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutAppToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.tokenRecordManagerment.deleteComment(null, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentWithoutAppCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        this.certRecordManagerment.deleteComment(null, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppUnexisted() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(10000, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppUnexistedToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(10000, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppUnexistedCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(10000, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppNegativeNumber() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(-1, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppNegativeNumberToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(-1, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppNegativeNumberCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(-1, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppZero() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteComment(0, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppZeroToken() throws KintoneAPIException {
        this.tokenRecordManagerment.deleteComment(0, 1, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentAppZeroCert() throws KintoneAPIException {
        this.certRecordManagerment.deleteComment(0, 1, 1);
    }

    @Test
    public void testDeleteCommentsInGuest() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.guestAuthRecordManagerment.addComment(1631, id, comment);
        this.guestAuthRecordManagerment.deleteComment(1631, id, addComment.getId());
    }

    @Test
    public void testDeleteCommentsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.tokenGuestRecordManagerment.addComment(1631, id, comment);
        this.tokenGuestRecordManagerment.deleteComment(1631, id, addComment.getId());
    }

    @Test
    public void testDeleteCommentsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(1631, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("yfang");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.certGuestRecordManagerment.addComment(1631, id, comment);
        this.certGuestRecordManagerment.deleteComment(1631, id, addComment.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentCommentOff() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        this.passwordAuthRecordManagerment.deleteComment(1640, id, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentCommentOffToken() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.requiredFieldTokenRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        this.requiredFieldTokenRecordManagerment.deleteComment(1640, id, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentCommentOffCert() throws KintoneAPIException {
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(1640, testRecord);
        Integer id = addResponse.getID();
        this.certRecordManagerment.deleteComment(1640, id, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentOtherUser() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        Auth auth = new Auth();
        auth.setPasswordAuth("cyuan", "cyuan");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        this.passwordAuthRecordManagerment = new Record(connection);
        this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentOtherUserToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        Auth auth = new Auth();
        auth.setPasswordAuth("cyuan", "cyuan");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        this.tokenRecordManagerment = new Record(connection);
        this.tokenRecordManagerment.deleteComment(APP_ID, id, commentId);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteCommentOtherUserCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.certRecordManagerment.addRecord(APP_ID, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode(testman2.getCode());
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse commentAddResponse = this.certRecordManagerment.addComment(APP_ID, id, comment);
        // Main Test processing
        Integer commentId = commentAddResponse.getId();
        Auth certauth = new Auth();
        certauth.setPasswordAuth("cyuan", "cyuan");
        certauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certauth);
        this.certRecordManagerment = new Record(connection);
        this.certRecordManagerment.deleteComment(APP_ID, id, commentId);
    }
}
