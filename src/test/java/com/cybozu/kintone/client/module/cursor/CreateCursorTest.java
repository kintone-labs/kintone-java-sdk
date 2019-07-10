package com.cybozu.kintone.client.module.cursor;

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
import com.cybozu.kintone.client.model.cursor.CreateRecordCursorResponse;
import com.cybozu.kintone.client.model.cursor.GetRecordCursorResponse;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.record.Record;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class CreateCursorTest {
	private static Integer APP_ID = 110;
	private static Integer APP_ID2 = 110;
    private static String API_TOKEN = "xxx";

	private static Member testman1 = new Member("xxx", "xxx");
    private static Member testman2 = new Member("xxx", "xxx");
    private static Member testgroup1 = new Member("xxx", "xxx");
    private static Member testgroup2 = new Member("xxx", "xxx");

	private RecordCursor passwordAuthRecordCursor;
    private Record tokenRecordManagerment;
	private Record recordManagerment;
	private Integer uniqueKey = 1;
	@Before
    public void setup() throws KintoneAPIException {
		Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordCursor = new RecordCursor(passwordAuthConnection);
        this.recordManagerment = new Record(passwordAuthConnection);

        Auth tokenAuth = new Auth();
        tokenAuth.setApiToken(API_TOKEN);
        Connection tokenConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
        tokenConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.tokenRecordManagerment = new Record(tokenConnection);

        // get maximum "数値"field value in all records and set it uniqueKey.
        String query = "order by 数値 desc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, query, 500);

        GetRecordsResponse response = this.passwordAuthRecordCursor.getAllRecords(cursor.getId());
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        this.uniqueKey += Integer.parseInt((String) resultRecords.get(0).get("数値").getValue());
    }

	@Test
    public void testCreateCursolShouldSuccessWhenFileldCodeUnincludeInApp() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Test");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
    }

	@Test
    public void testCreateCursolShouldSuccessOnlySpecifiedField() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        String query = "order by 数値 desc";
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, query, null);
        assertTrue(!cursor.getId().isEmpty());
    }

	@Test
    public void testCreateCursolShouldSuccessIgnoreFieldCode() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        fields.add("Test");
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
        assertTrue(!cursor.getId().isEmpty());
    }

	@Test
    public void testCreateCursorShouldSuccess() throws KintoneAPIException {
        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, null, null);
        assertTrue(!cursor.getId().isEmpty());
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitSizeAndDefaultVal() throws KintoneAPIException {
        int size = 0;
		this.passwordAuthRecordCursor.createCursor(APP_ID, null, null, size);
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenLimitOrDffsetInQuery() throws KintoneAPIException {
        String query = "limit 0, 100 order by 数値 desc";
		this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, null);
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenOverThousandFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        int i = 0;
        int totalcount = 1002;
        while ( i < totalcount ){
        	fields.add("数値" + i );
        	 i++ ;
        }
		this.passwordAuthRecordCursor.createCursor(APP_ID, fields, null, null);
	}

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenUnspecifiedApp() throws KintoneAPIException {
		this.passwordAuthRecordCursor.createCursor(null, null, null, null);
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailAuthByAPI() throws KintoneAPIException {
		this.tokenRecordManagerment.createCursor(APP_ID, null, null, null);
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailWhenDontBrowsingAuth() throws KintoneAPIException {
        this.passwordAuthRecordCursor.createCursor(APP_ID2, null, null, null);
    }

	@Test(expected = KintoneAPIException.class)
    public void testCreateCursorShowFailGivenInvalidAppID() throws KintoneAPIException {
		this.passwordAuthRecordCursor.createCursor(-1, null, null, null);
    }


	@Test(expected = KintoneAPIException.class)
    public void testCreateCursolShouldFailDontCreateOver11CursolInSameTime() throws KintoneAPIException {
	    int i = 0;
	    int totalcount = 11;
	    while ( i < totalcount ){
			this.passwordAuthRecordCursor.createCursor( APP_ID, null, null, null);
	    	 i++ ;
	    }
    }

	@Test
    public void testGetRecordsShouldSuccess() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        records.add(testRecord1);
        records.add(testRecord2);
        records.add(testRecord3);
		this.recordManagerment.addRecords(APP_ID, records);

		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
        Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 100);
        GetRecordCursorResponse response = this.passwordAuthRecordCursor.getRecords(cursor.getId());

        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        assertEquals(3, response.getRecords().size());
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
    }

	@Test
    public void testGetAllRecordsCursorShouldSuccess() throws KintoneAPIException {
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
        	this.recordManagerment.addRecords(APP_ID, recordsToAdd);
        	i ++;
        }
        // Main Test processing
        Integer lowerLimit = (Integer) records.get(0).get("数値").getValue();
        Integer upperLimit = (Integer) records.get(records.size() - 1).get("数値").getValue();
        String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";

        CreateRecordCursorResponse cursor = this.passwordAuthRecordCursor.createCursor(APP_ID, null, query, 500);
        GetRecordsResponse response = this.passwordAuthRecordCursor.getAllRecords(cursor.getId());
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

}
