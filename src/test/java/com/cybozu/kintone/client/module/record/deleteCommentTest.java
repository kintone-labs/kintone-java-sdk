package com.cybozu.kintone.client.module.record;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.CommentMention;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class deleteCommentTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
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
    private Record noViewPermissionTokenRecordManagerment;
    private Record addNoViewTokenRecordManagerment;
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

        Auth tokenAuth1 = new Auth();
        tokenAuth1.setApiToken(NO_VIEW_PERMISSION_API_TOKEN);
        Connection tokenConnection1 = new Connection(TestConstants.DOMAIN, tokenAuth1);
        this.noViewPermissionTokenRecordManagerment = new Record(tokenConnection1);

        Auth tokenAuth4 = new Auth();
        tokenAuth4.setApiToken(REQUIRED_FIELD_API_TOKEN);
        Connection tokenConnection4 = new Connection(TestConstants.DOMAIN, tokenAuth4);
        this.requiredFieldTokenRecordManagerment = new Record(tokenConnection4);

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

        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("cybozu");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.guestAuthRecordManagerment.addComment(360, id, comment);
        this.guestAuthRecordManagerment.deleteComment(360, id, addComment.getId());
    }

    @Test
    public void testDeleteCommentsInGuestToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("cybozu");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.tokenGuestRecordManagerment.addComment(360, id, comment);
        this.tokenGuestRecordManagerment.deleteComment(360, id, addComment.getId());
    }

    @Test
    public void testDeleteCommentsInGuestCert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = new HashMap<>();
        testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("cybozu");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment");
        comment.setMentions(mentionList);
        AddCommentResponse addComment = this.certGuestRecordManagerment.addComment(360, id, comment);
        this.certGuestRecordManagerment.deleteComment(360, id, addComment.getId());
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
