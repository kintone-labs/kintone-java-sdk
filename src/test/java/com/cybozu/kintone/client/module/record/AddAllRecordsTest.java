package com.cybozu.kintone.client.module.record;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.BulksException;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.bulk_request.BulkRequestResponses;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.record.response.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordsResponse;
import com.cybozu.kintone.client.module.file.File;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AddAllRecordsTest {

    private static Integer APP_ID = 11;
    private static Integer GUEST_APP_ID = 15;
    private static Integer GUEST_SPACE_ID = TestConstants.GUEST_SPACE_ID;
    private static String API_TOKEN = TestConstants.API_TOKEN;
    private static String API_TOKEN_GUEST = TestConstants.HAAPI_TOKEN;
    private static String ADMIN_USER_NAME = TestConstants.ADMIN_USERNAME;
    private static String ADMIN_USER_PASSWORD = TestConstants.ADMIN_PASSWORD;
    private static String NO_PERMISSION_USER_NAME = TestConstants.USERNAME;
    private static String NO_PERMISSION_USER_PASSWORD = TestConstants.PASSWORD;
    private static String BASIC_USER_NAME = TestConstants.BASIC_USERNAME;
    private static String BASIC_USER_PASSWORD = TestConstants.BASIC_PASSWORD;

    private Record passwordAuthRecord;
    private Record tokenAuthRecord;
    private Record tokenGuestAuthRecord;
    private Record basicUserAuthRecord;
    private File attachment;
    private Connection guestConnection;
    private String requiredField = "文字列__1行";
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        // create passwordAuthRecord
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(ADMIN_USER_NAME, ADMIN_USER_PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        this.passwordAuthRecord = new Record(passwordAuthConnection);
        this.attachment = new File(passwordAuthConnection);

        String query = "order by 数値 desc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.passwordAuthRecord.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        if (resultRecords.size() > 0) {
            String num = (String) resultRecords.get(0).get("数値").getValue();
            if (!num.isEmpty()) {
                this.uniqueKey += Integer.parseInt(num);
            }
        }

        // create tokenAuthRecord
        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenAuthConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        this.tokenAuthRecord = new Record(tokenAuthConnection);

        // create tokenAuthRecord
        Auth tokenGuestAuth = new Auth();
        tokenGuestAuth.setApiToken(API_TOKEN_GUEST);
        Connection tokenGuestAuthConnection = new Connection(TestConstants.DOMAIN, tokenGuestAuth, GUEST_SPACE_ID);
        this.tokenGuestAuthRecord = new Record(tokenGuestAuthConnection);

        // create guest connection
        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(ADMIN_USER_NAME, ADMIN_USER_PASSWORD);
        this.guestConnection = new Connection(TestConstants.DOMAIN, guestAuth, GUEST_SPACE_ID);

        // create passwordAuthRecord
        Auth basicUserAuth = new Auth();
        basicUserAuth.setPasswordAuth(BASIC_USER_NAME, BASIC_USER_PASSWORD);
        Connection basicUserAuthConnection = new Connection(TestConstants.DOMAIN, basicUserAuth);
        this.basicUserAuthRecord = new Record(basicUserAuthConnection);
    }

        @Test
        public void testAddAllRecordsShouldSuccess() throws BulksException, KintoneAPIException {
            int totalRecordToAdd = 6000;
            int numBulkRequest = 3;
            int numRequestPerBulk = 20;
            ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
            int i = 0;
            while (i < totalRecordToAdd) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                i++;
            }
            BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
            assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
            for (int j = 0; j < numBulkRequest; j++) {
                assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
            }
        }

        @Test
        public void testAddAllRecordsShouldSuccessToken() throws BulksException, KintoneAPIException {
            int totalRecordToAdd = 100;
            int numBulkRequest = 1;
            int numRequestPerBulk = 1;
            ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
            int i = 0;
            while (i < totalRecordToAdd) {
                HashMap<String, FieldValue> testRecord = createTestRecord();
                records.add(testRecord);
                i++;
            }
            BulkRequestResponses response = this.tokenAuthRecord.addAllRecords(APP_ID, records);
            assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
            for (int j = 0; j < numBulkRequest; j++) {
                assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
            }
        }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailInvalidAppID() throws BulksException, KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> testRecord = createTestRecord();
        records.add(testRecord);
        this.passwordAuthRecord.addAllRecords(-1, records);
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithInvalidField() throws BulksException, KintoneAPIException {
        int totalRecordToAdd = 10;
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < totalRecordToAdd) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            addField(testRecord, "invlid code", FieldType.NUMBER, 10);
            records.add(testRecord);
            i++;
        }
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithInvalidFieldValue() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "数値2", FieldType.NUMBER, "text");
        records.add(testRecord);

        this.passwordAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithDeplicateValue() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, requiredField, FieldType.SINGLE_LINE_TEXT, "test single text");
        testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1);
        records.add(testRecord);

        this.passwordAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithLargeNumber() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "数値2", FieldType.NUMBER, 10000);
        records.add(testRecord);
        this.passwordAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithoutAppID() throws BulksException, KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> testRecord = createTestRecord();
        records.add(testRecord);
        this.passwordAuthRecord.addAllRecords(null, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithoutRecords() throws BulksException, KintoneAPIException {
        // Preprocessing
        this.passwordAuthRecord.addAllRecords(APP_ID, null);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithoutRequiredField() throws BulksException, KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "文字列__複数行", FieldType.MULTI_LINE_TEXT, "test multi text");
        records.add(testRecord);
        this.passwordAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithTableField() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
        addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test single text");
        addField(tableitemvalue, "文字列__複数行_テーブル", FieldType.MULTI_LINE_TEXT, "test multi text");
        addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");

        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue);
        subTable.add(tablelist1);
        addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);

        records.add(testRecord);
        // Preprocessing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithTableFieldToken() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
        SubTableValueItem tablelist1 = new SubTableValueItem();

        HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
        addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test single text");
        addField(tableitemvalue, "文字列__複数行_テーブル", FieldType.MULTI_LINE_TEXT, "test multi text");
        addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");

        tablelist1.setID(1);
        tablelist1.setValue(tableitemvalue);
        subTable.add(tablelist1);
        addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);

        records.add(testRecord);
        // Preprocessing
        BulkRequestResponses response = this.tokenAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithAttachment() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        FileModel file = this.attachment.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        records.add(testRecord);

        // Main Test processing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        assertEquals(true, response.getResponses().get(0) instanceof AddRecordsResponse);
    }

    @Test
    public void testAddAllRecordsShouldSuccessOnGuest() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        Record guestRecord = new Record(this.guestConnection);
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> testRecord = createTestRecord();
        records.add(testRecord);
        BulkRequestResponses response = guestRecord.addAllRecords(GUEST_APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithAttachmentOnGuest() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        Record guestRecord = new Record(this.guestConnection);
        File attachment = new File(this.guestConnection);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        FileModel file = attachment.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        records.add(testRecord);

        // Main Test processing
        BulkRequestResponses response = guestRecord.addAllRecords(GUEST_APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        assertEquals(true, response.getResponses().get(0) instanceof AddRecordsResponse);
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithAttachmentOnGuestToken() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        File attachment = new File(this.guestConnection);

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        FileModel file = attachment.upload("src/test/resources/record/ValidRecordValue.txt");
        ArrayList<FileModel> al = new ArrayList<>();
        al.add(file);
        HashMap<String, FieldValue> testRecord = createTestRecord();
        testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
        records.add(testRecord);

        // Main Test processing
        BulkRequestResponses response = tokenGuestAuthRecord.addAllRecords(GUEST_APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        assertEquals(true, response.getResponses().get(0) instanceof AddRecordsResponse);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithNoPermission() throws BulksException, KintoneAPIException {

        Auth auth = new Auth();
        auth.setPasswordAuth(NO_PERMISSION_USER_NAME, NO_PERMISSION_USER_PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        Record recordWitnNoPermission = new Record(connection);
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> testRecord = createTestRecord();
        records.add(testRecord);
        recordWitnNoPermission.addAllRecords(APP_ID, records);
    }

    @Test
    public void testAddAllRecordsShouldSuccessLimitation() throws BulksException, KintoneAPIException {
        int totalRecordToAdd = 2000;
        int numBulkRequest = 1;
        int numRequestPerBulk = 20;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        int i = 0;
        while (i < totalRecordToAdd) {
            HashMap<String, FieldValue> testRecord = createTestRecord();
            records.add(testRecord);
            i++;
        }
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

//    @Test
//    public void testAddAllRecordsShouldNotRollBack() throws BulksException, KintoneAPIException {
//        int totalRecordToAdd = 6000;
//        int numRequestPerBulk = 20;
//        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
//        int i = 0;
//        while (i < numRequestPerBulk * 100) {
//            HashMap<String, FieldValue> testRecord = createTestRecord();
//            records.add(testRecord);
//            i++;
//        }
//        HashMap<String, FieldValue> invalidRecord = createTestRecord();
//        addField(invalidRecord, "数値2", FieldType.NUMBER, "invalid value");
//        records.add(invalidRecord);
//        i++;
//        while (i < totalRecordToAdd) {
//            HashMap<String, FieldValue> testRecord = createTestRecord();
//            records.add(testRecord);
//            i++;
//        }
//        try {
//            this.passwordAuthRecord.addAllRecords(APP_ID, records);
//        } catch (BulksException e) {
//            ArrayList<Object> results = (ArrayList<Object>)e.getResults();
//            assertEquals(21, results.size());
//            for (int j = 0; j < numRequestPerBulk; j++) {
//                assertEquals(true, results.get(j) instanceof AddRecordsResponse);
//            }
//            assertEquals(true, results.get(20) instanceof KintoneAPIException);
//        }
//    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithCreatedBy() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        Member user = new Member(BASIC_USER_NAME, BASIC_USER_NAME);
        addField(testRecord, "作成者", FieldType.CREATOR, user);
        records.add(testRecord);
        // Preprocessing
        this.basicUserAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithCreatedDateTime() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-02T02:30:00Z");
        records.add(testRecord);
        // Preprocessing
        this.basicUserAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithupdatedBy() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        Member user = new Member(BASIC_USER_NAME, BASIC_USER_NAME);
        addField(testRecord, "更新者", FieldType.MODIFIER, user);
        records.add(testRecord);
        // Preprocessing
        this.basicUserAuthRecord.addAllRecords(APP_ID, records);
    }

    @Test(expected = BulksException.class)
    public void testAddAllRecordsShouldFailWithUpdatedDateTime() throws BulksException, KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T02:30:00Z");
        records.add(testRecord);
        // Preprocessing
        this.basicUserAuthRecord.addAllRecords(APP_ID, records);
    }


    @Test
    public void testAddAllRecordsShouldSuccessWithCreatedBy() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        Member user = new Member(BASIC_USER_NAME, BASIC_USER_NAME);
        addField(testRecord, "作成者", FieldType.CREATOR, user);
        records.add(testRecord);

        // Preprocessing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithCreatedDateTime() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-02T02:30:00Z");
        records.add(testRecord);

        // Preprocessing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }
    @Test
    public void testAddAllRecordsShouldSuccessWithUpdatedBy() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        Member user = new Member(BASIC_USER_NAME, BASIC_USER_NAME);
        addField(testRecord, "更新者", FieldType.CREATOR, user);
        records.add(testRecord);

        // Preprocessing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    @Test
    public void testAddAllRecordsShouldSuccessWithUpdatedDateTime() throws BulksException, KintoneAPIException {
        int numBulkRequest = 1;
        int numRequestPerBulk = 1;
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();

        HashMap<String, FieldValue> testRecord = createTestRecord();
        addField(testRecord, "更新日時", FieldType.CREATED_TIME, "2018-01-02T02:30:00Z");
        records.add(testRecord);

        // Preprocessing
        BulkRequestResponses response = this.passwordAuthRecord.addAllRecords(APP_ID, records);
        assertEquals((numRequestPerBulk * numBulkRequest), response.getResponses().size());
        for (int j = 0; j < numBulkRequest; j++) {
            assertEquals(true, response.getResponses().get(j) instanceof AddRecordsResponse);
        }
    }

    public HashMap<String, FieldValue> addField(HashMap<String, FieldValue> record, String code, FieldType type,
            Object value) {
        FieldValue newField = new FieldValue();
        newField.setType(type);
        newField.setValue(value);
        record.put(code, newField);
        return record;
    }

    public HashMap<String, FieldValue> createTestRecord() {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        addField(testRecord, requiredField, FieldType.SINGLE_LINE_TEXT, "test single text");
        addField(testRecord, "数値", FieldType.NUMBER, this.uniqueKey);
        this.uniqueKey += 1;
        addField(testRecord, "文字列__複数行", FieldType.MULTI_LINE_TEXT, "test multi text");
        addField(testRecord, "リッチエディター", FieldType.RICH_TEXT, "<div>test rich text<br /></div>");

        ArrayList<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("sample1");
        selectedItemList.add("sample2");
        addField(testRecord, "チェックボックス", FieldType.CHECK_BOX, selectedItemList);
        addField(testRecord, "ラジオボタン", FieldType.RADIO_BUTTON, "sample2");
        addField(testRecord, "ドロップダウン", FieldType.DROP_DOWN, "sample2");
        addField(testRecord, "複数選択", FieldType.MULTI_SELECT, selectedItemList);
        addField(testRecord, "リンク", FieldType.LINK, "http://cybozu.co.jp/");
        addField(testRecord, "日付", FieldType.DATE, "2018-01-01");
        addField(testRecord, "時刻", FieldType.TIME, "12:34");
        addField(testRecord, "日時", FieldType.DATETIME, "2018-01-02T02:30:00Z");
        return testRecord;
    }

}
