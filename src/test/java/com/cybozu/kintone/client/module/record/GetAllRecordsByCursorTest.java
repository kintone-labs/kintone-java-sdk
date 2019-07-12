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
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;

public class GetAllRecordsByCursorTest {
    private static Integer APP_ID;
    private static String CURSOR_API_TOKEN = "xxx";
    private static String CURSOR_API_TOKEN2 = "xxx";
    private static String CURSOR_API_TOKEN3 = "xxx";
    private static String CURSOR_API_TOKEN4 = "xxx";
    private static String CURSOR_API_TOKEN5 = "xxx";

    private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");
    private static Member testorg1 = new Member("xxx", "xxx");
    private static Member testorg2 = new Member("xxx", "xxx");

    private Record passwordAuthRecordManagerment;
    private Record cursorPasswordAuthRecordManagerment;
    private Record cursorTokenRecordManagerment;
    private Record cursorRestrictedTokenRecordManagerment;
    private Record cursorAddOnlyTokenRecordManagerment;
    private Record cursorReadOnlyTokenRecordManagerment;
    private Record cursorManageOnlyTokenRecordManagerment;
    private Record cursorCertRecordManagerment;
    private Record cursorRestrictedCertRecordManagerment;
    private Integer uniqueKey = 1;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);
        
        Auth passwordAuth2 = new Auth();
        passwordAuth2.setPasswordAuth("testman2", "cybozu");
        Connection passwordAuthConnection2 = new Connection(TestConstants.DOMAIN, passwordAuth2);
        this.cursorPasswordAuthRecordManagerment = new Record(passwordAuthConnection2);
        
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
        
        Auth cursorCertauth = new Auth();
        cursorCertauth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        cursorCertauth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection cursorCertConnection = new Connection(TestConstants.SECURE_DOMAIN, cursorCertauth);
        this.cursorCertRecordManagerment = new Record(cursorCertConnection);
        
        Auth cursorCertauth2 = new Auth();
        cursorCertauth2.setPasswordAuth("xxx", "xxx");
        cursorCertauth2.setClientCertByPath("src/test/resources/certificates/testUser/xxx.pfx", "xxx");
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
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldSuccessWhenHasNoPermission() throws KintoneAPIException {
        this.cursorPasswordAuthRecordManagerment.getAllRecordsByCursor(14, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldSuccessWhenHasNoPermissionCert() throws KintoneAPIException {
        this.cursorRestrictedCertRecordManagerment.getAllRecordsByCursor(14, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithUnexistedAppID() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getAllRecordsByCursor(99999, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetAllRecordsByCursorShouldFailWithUnexistedAppIDToken() throws KintoneAPIException {
        this.cursorTokenRecordManagerment.getAllRecordsByCursor(99999, null, null);
    }
    
    @Test(expected = KintoneAPIException.class)
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
}
