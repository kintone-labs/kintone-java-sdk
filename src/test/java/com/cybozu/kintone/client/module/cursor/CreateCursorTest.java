package com.cybozu.kintone.client.module.cursor;

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
import com.cybozu.kintone.client.model.cursor.CreateRecordCursorResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.record.Record;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class CreateCursorTest {
    private static Integer APP_ID = 7;
    private static Integer APP_ID2 = 8;
    private static Integer APP_ID3 = 9;
    private static Integer APP_ID4 = 10;
    private static String apiTokenCanReadRec = "X6pbAeoJ7QadGFEboF5jq69fnjzOlTtEFxOoGozd";
    private static String apiTokenCanReadRec2="ThfpOyj3gRSRX63eXfVtWDdWPZPy5tR8CqiKZfY0";
    private static String LOCALUSERNAME = "LOCALUSER";
    private static String LOCALPASSWORD ="cybozu123";
//    private static String apiTokenCanReadRec = TestConstants.API_TOKEN;
//    private static String apiTokenCanReadRec2 = TestConstants.HAAPI_TOKEN;
//    private static String LOCALUSERNAME = TestConstants.BASIC_USERNAME;
//    private static String LOCALPASSWORD = TestConstants.BASIC_PASSWORD;

    private RecordCursor passwordAuthRecordCursor;
    private RecordCursor passwordAuthRecordCursor2;
    private RecordCursor passwordAuthRecordCursorCert;
    private RecordCursor apiTokenAuthRecordCursor;
    private RecordCursor apiTokenAuthRecordCursor2;
    private Record recordManagerment;
    private Integer uniqueKey = 1;

    HashMap<String, FieldValue> testRecord1;
    HashMap<String, FieldValue> testRecord2;
    HashMap<String, FieldValue> testRecord3;

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
        testRecord = addField(testRecord, "ドロップダウン", FieldType.DROP_DOWN, "sample2");
        testRecord = addField(testRecord, "複数選択", FieldType.MULTI_SELECT, selectedItemList);
        testRecord = addField(testRecord, "リンク", FieldType.LINK, "http://cybozu.co.jp/");
        testRecord = addField(testRecord, "日付", FieldType.DATE, "2018-01-01");
        testRecord = addField(testRecord, "時刻", FieldType.TIME, "12:34");
        testRecord = addField(testRecord, "日時", FieldType.DATETIME, "2018-01-02T02:30:00Z");

        return testRecord;
    }

    @Before
    public void setup() throws KintoneAPIException {

        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);
        this.recordManagerment = new Record(passwordAuthConnection);

        Auth passwordAuth2 = new Auth();
        passwordAuth2.setPasswordAuth(LOCALUSERNAME, LOCALPASSWORD);
        Connection passwordAuthConnection2 = new Connection(TestConstants.DOMAIN, passwordAuth2);
        this.passwordAuthRecordCursor2 = new RecordCursor(passwordAuthConnection2);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(apiTokenCanReadRec);
        Connection apiAuthConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        this.apiTokenAuthRecordCursor = new RecordCursor(apiAuthConnection);

        Auth tokenAuth2 = new Auth();
        tokenAuth2.setApiToken(apiTokenCanReadRec2);
        Connection apiAuthConnection2 = new Connection(TestConstants.DOMAIN, tokenAuth2);
        this.apiTokenAuthRecordCursor2 = new RecordCursor(apiAuthConnection2);

        Auth passwordAuthCert = new Auth();
        passwordAuthCert.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        passwordAuthCert.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection passwordAuthConnectionCert = new Connection(TestConstants.DOMAIN, passwordAuthCert);
        this.passwordAuthRecordCursorCert = new RecordCursor(passwordAuthConnectionCert);

        this.testRecord1 = createTestRecord();
        this.testRecord2 = createTestRecord();
        this.testRecord3 = createTestRecord();
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
        Integer lowerLimit = (Integer) this.testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) this.testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
        GetRecordsResponse result = this.recordManagerment.getRecords(APP_ID, query, null, true);
        if (result.getRecords().size() == 0) {
            this.recordManagerment.addRecords(APP_ID, records);
        }
        GetRecordsResponse result2 = this.recordManagerment.getRecords(APP_ID2, query, null, true);
        if (result2.getRecords().size() == 0) {
            this.recordManagerment.addRecords(APP_ID2, records);
        }
    }

    @Test
    public void testCreateCursolShouldSuccessWhenFileldCodeUnincludeInApp() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("dummy");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessWhenFileldCodeUnincludeInAppToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("dummy");
        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.apiTokenAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessWhenFileldCodeUnincludeInAppCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("dummy");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursorCert.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessOnlySpecifiedField() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        String query = "order by 数値 desc";
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, query, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessOnlySpecifiedFieldToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        String query = "order by 数値 desc";
        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, fields, query, null);
        assertTrue(!cursor.getId().isEmpty());
        this.apiTokenAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessOnlySpecifiedFieldCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        String query = "order by 数値 desc";
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, fields, query, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursorCert.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessDontSpecifyQuery() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessDontSpecifyQueryToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.apiTokenAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessDontSpecifyQueryCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursorCert.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessIgnoreFieldCode() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        fields.add("Test");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessIgnoreFieldCodeToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        fields.add("Test");
        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.apiTokenAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursolShouldSuccessIgnoreFieldCodeCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        fields.add("Test");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursorCert.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursorShouldSuccess() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursorShouldSuccessToken() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.apiTokenAuthRecordCursor.deleteCursor(cursor.getId());
    }

    @Test
    public void testCreateCursorShouldSuccessCert() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, null, null);
        assertTrue(!cursor.getId().isEmpty());
        this.passwordAuthRecordCursorCert.deleteCursor(cursor.getId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitSizeAndDefaultVal() throws KintoneAPIException {
        Integer size = 0;
        this.passwordAuthRecordCursor.createCursor(APP_ID, null, null, size);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitSizeAndDefaultValToken() throws KintoneAPIException {
        Integer size = 0;
        this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, null, size);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitSizeAndDefaultValCert() throws KintoneAPIException {
        Integer size = 0;
        this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, null, size);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitOrDffsetInQuery() throws KintoneAPIException {
        String query = "limit 0, 100 order by 数値 desc";
        this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitOrDffsetInQueryToken() throws KintoneAPIException {
        String query = "limit 0, 100 order by 数値 desc";
        this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, query, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitOrDffsetInQueryCert() throws KintoneAPIException {
        String query = "limit 0, 100 order by 数値 desc";
        this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, query, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenOverThousandFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        Integer i = 0;
        Integer totalcount = 1002;
        while (i < totalcount) {
            fields.add("数値_" + i);
            i++;
        }
        this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenOverThousandFieldsToken() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        Integer i = 0;
        Integer totalcount = 1002;
        while (i < totalcount) {
            fields.add("数値_" + i);
            i++;
        }
        this.apiTokenAuthRecordCursor.createCursor(APP_ID, fields, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenOverThousandFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        Integer i = 0;
        Integer totalcount = 1002;
        while (i < totalcount) {
            fields.add("数値_" + i);
            i++;
        }
        this.passwordAuthRecordCursorCert.createCursor(APP_ID3, fields, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenUnspecifiedApp() throws KintoneAPIException {
        this.passwordAuthRecordCursor.createCursor(null, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenUnspecifiedAppToken() throws KintoneAPIException {
        this.apiTokenAuthRecordCursor.createCursor(null, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenUnspecifiedAppCert() throws KintoneAPIException {
        this.passwordAuthRecordCursorCert.createCursor(null, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenDontBrowsingAuth() throws KintoneAPIException {
        this.passwordAuthRecordCursor2.createCursor(APP_ID2, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenDontBrowsingAuthToken() throws KintoneAPIException {
        this.apiTokenAuthRecordCursor2.createCursor(APP_ID2, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenDontBrowsingAuthCert() throws KintoneAPIException {
        this.passwordAuthRecordCursorCert.createCursor(APP_ID4, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursorShowFailGivenInvalidAppID() throws KintoneAPIException {
        this.passwordAuthRecordCursor.createCursor(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursorShowFailGivenInvalidAppIDToken() throws KintoneAPIException {
        this.apiTokenAuthRecordCursor.createCursor(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursorShowFailGivenInvalidAppIDCert() throws KintoneAPIException {
        this.passwordAuthRecordCursorCert.createCursor(-1, null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailDontCreateOver11CursolInSameTime() throws KintoneAPIException {
        Integer i = 0;
        Integer totalcount = 11;
        ArrayList<String> cursorIDs = new ArrayList<String>();
        try {
            while (i < totalcount) {
                CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, null,
                        null);
                cursorIDs.add(cursor.getId());
                i++;
            }
        } catch (Exception e) {
            for (String id : cursorIDs) {
                this.passwordAuthRecordCursor.deleteCursor(id);
            }
            throw e;
        }
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailDontCreateOver11CursolInSameTimeToken() throws KintoneAPIException {
        Integer i = 0;
        Integer totalcount = 11;
        ArrayList<String> cursorIDs = new ArrayList<String>();
        try {
            while (i < totalcount) {
                CreateRecordCursorResponse cursor = this.apiTokenAuthRecordCursor.createCursor(APP_ID, null, null,
                        null);
                cursorIDs.add(cursor.getId());
                i++;
            }
        } catch (Exception e) {
            for (String id : cursorIDs) {
                this.apiTokenAuthRecordCursor.deleteCursor(id);
            }
            throw e;
        }
    }

    @Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailDontCreateOver11CursolInSameTimeCert() throws KintoneAPIException {
        Integer i = 0;
        Integer totalcount = 11;
        ArrayList<String> cursorIDs = new ArrayList<String>();
        try {
            while (i < totalcount) {
                CreateRecordCursorResponse cursor = this.passwordAuthRecordCursorCert.createCursor(APP_ID, null, null,
                        null);
                cursorIDs.add(cursor.getId());
                i++;
            }
        } catch (Exception e) {
            for (String id : cursorIDs) {
                this.passwordAuthRecordCursorCert.deleteCursor(id);
            }
            throw e;
        }
    }

}
