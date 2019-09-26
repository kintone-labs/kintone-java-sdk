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
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.CommentMention;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class getCommentsTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
    private static String ADD_NO_VIEW_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String NO_DELETE_PERMISSION_API_TOKEN = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");
    private static Member testAdimin = new Member("xxx", "xxx");
    private static Member testTokenAdimin = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record guestAuthRecordManagerment;
    private Record tokenRecordManagerment;
    private Record noViewPermissionTokenRecordManagerment;
    private Record addNoViewTokenRecordManagerment;
    private Record noDeletePermissionRecordManagerment;
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

        Auth tokenAuth6 = new Auth();
        tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
        Connection tokenConnection6 = new Connection(TestConstants.DOMAIN, tokenAuth6);
        this.addNoViewTokenRecordManagerment = new Record(tokenConnection6);

        Auth tokenAuth8 = new Auth();
        tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
        Connection tokenConnection8 = new Connection(TestConstants.DOMAIN, tokenAuth8);
        this.noDeletePermissionRecordManagerment = new Record(tokenConnection8);

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
        AddRecordResponse addResponse = this.guestAuthRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("testman1");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.guestAuthRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment2");
        this.guestAuthRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment3");
        this.guestAuthRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment4");
        this.guestAuthRecordManagerment.addComment(360, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.guestAuthRecordManagerment.getComments(360, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("testman1" + " \ntest comment2 ", response.getComments().get(0).getText());
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
        AddRecordResponse addResponse = this.tokenGuestRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("testman1");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.tokenGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment2");
        this.tokenGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment3");
        this.tokenGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment4");
        this.tokenGuestRecordManagerment.addComment(360, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.tokenGuestRecordManagerment.getComments(360, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("testman1" + " \ntest comment2 ", response.getComments().get(0).getText());
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
        AddRecordResponse addResponse = this.certGuestRecordManagerment.addRecord(360, testRecord);

        Integer id = addResponse.getID();
        CommentContent comment = new CommentContent();
        ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
        CommentMention mention = new CommentMention();
        mention.setCode("testman1");
        mention.setType("USER");
        mentionList.add(mention);
        comment.setText("test comment1");
        comment.setMentions(mentionList);
        this.certGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment2");
        this.certGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment3");
        this.certGuestRecordManagerment.addComment(360, id, comment);
        comment.setText("test comment4");
        this.certGuestRecordManagerment.addComment(360, id, comment);
        // Main Test processing
        GetCommentsResponse response = this.certGuestRecordManagerment.getComments(360, id, "asc", 1, 2);
        assertEquals(2, response.getComments().size());
        assertNotNull(response.getComments().get(0).getId());
        assertNotNull(response.getComments().get(0).getCreatedAt());
        assertEquals("testman1" + " \ntest comment2 ", response.getComments().get(0).getText());
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
}
