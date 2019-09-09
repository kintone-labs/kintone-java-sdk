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
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.CommentMention;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class addCommentTest {
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
    private static Member testTokenAdimin = new Member("xxx", "xxx");
    private static Member testCertAdimin = new Member("xxx", "xxx");

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
        AddCommentResponse response = this.guestAuthRecordManagerment.addComment(360, id, comment);
		assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentsInGuestToken() throws KintoneAPIException {
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
        AddCommentResponse response = this.tokenGuestRecordManagerment.addComment(360, id, comment);
        assertNotNull(response.getId());
    }

    @Test
    public void testAddCommentsInGuestCert() throws KintoneAPIException {
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
        AddCommentResponse response = this.certGuestRecordManagerment.addComment(360, id, comment);
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
}
