package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class getRecordsTest {
    private static Integer APP_ID;
    private static String API_TOKEN = "xxx";
    private static String NO_VIEW_PERMISSION_API_TOKEN = "xxx";
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
    private Record tokenGuestRecordManagerment;
    private Record certRecordManagerment;
    private Record certGuestRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        //passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);

        Auth guestAuth = new Auth();
        guestAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection gusetConnection = new Connection(TestConstantsSample.DOMAIN, guestAuth, TestConstantsSample.GUEST_SPACE_ID);
        this.guestAuthRecordManagerment = new Record(gusetConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstantsSample.DOMAIN, tokenAuth);
        this.tokenRecordManagerment = new Record(tokenConnection);

        Auth tokenAuth1 = new Auth();
        tokenAuth1.setApiToken(NO_VIEW_PERMISSION_API_TOKEN);
        Connection tokenConnection1 = new Connection(TestConstantsSample.DOMAIN, tokenAuth1);
        this.noViewPermissionTokenRecordManagerment = new Record(tokenConnection1);

        Auth tokenGuestAuth = new Auth();
        tokenGuestAuth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection tokenGuestConnection = new Connection(TestConstantsSample.DOMAIN, tokenGuestAuth,
                TestConstantsSample.GUEST_SPACE_ID);
        this.tokenGuestRecordManagerment = new Record(tokenGuestConnection);

        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection certConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certAuth);
        this.certRecordManagerment = new Record(certConnection);

        Auth certGuestAuth = new Auth();
        certGuestAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certGuestAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection CertGuestConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certGuestAuth,
                TestConstantsSample.GUEST_SPACE_ID);
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
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        for (HashMap<String, FieldValue> hashMap : resultRecords) {
            assertEquals(24, hashMap.size());
        }
    }

    @Test
    public void testGetRecordsNoPermissionFieldDoNotDisplayCert() throws KintoneAPIException {
        GetRecordsResponse response = this.certRecordManagerment.getRecords(APP_ID, null, null, null);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        for (HashMap<String, FieldValue> hashMap : resultRecords) {
            assertEquals(24, hashMap.size());
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
}
