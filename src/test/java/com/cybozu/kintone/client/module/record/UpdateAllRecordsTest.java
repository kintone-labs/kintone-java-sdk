package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.model.bulk_request.BulkRequestResponse;
import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.record.response.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.record.response.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.record.response.RecordUpdateResponseItem;
import com.cybozu.kintone.client.model.record.record.response.UpdateRecordsResponse;
import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.BulksException;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.record.RecordUpdateItem;

public class UpdateAllRecordsTest {
	private static Integer APP_ID = 114;
	private Record passwordAuthRecordManagerment;
	private Integer uniqueKey = 1;
	@Before
    public void setup() throws KintoneAPIException {
		Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);
        
//		// get maximum "数値"field value in all records and set it uniqueKey.
        String query = "order by 数値 desc";
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("数値");
        GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
        if (resultRecords.size()> 0) {
        	this.uniqueKey += Integer.parseInt((String) resultRecords.get(0).get("数値").getValue());
		}
    }
	
	@Test
	public void testUpdateAllRecordsShouldSuccess() throws BulksException, KintoneAPIException {
		int numberRecordToUpdate = 2010;
		int numBulkRequest = 2;
		ArrayList<HashMap<String, FieldValue>> recordsToUpdate = new ArrayList<HashMap<String, FieldValue>>();
		ArrayList<HashMap<String, FieldValue>> recordsToAdd = new ArrayList<HashMap<String, FieldValue>>();
		int i = 0;  
		while (i < numberRecordToUpdate) {
			HashMap<String, FieldValue> record = createTestRecord();
			record = addField(record, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
			recordsToAdd.add(record);
			record = addField(record, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "update record");
			recordsToUpdate.add(record);
			i++;
		}
		BulkRequestResponse addResponse = this.passwordAuthRecordManagerment.addAllRecords(APP_ID, recordsToAdd);
		
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		i = 0;
		for (int j = 0; j < numBulkRequest; j++) {
			AddRecordsResponse addRecordsResponse = (AddRecordsResponse) addResponse.getResults().get(j);
			for (int id: addRecordsResponse.getIDs()) {
				RecordUpdateItem item = new RecordUpdateItem(id, null, null, recordsToUpdate.get(i));
				updateItems.add(item);
				i++;
			}
		}
		BulkRequestResponse response = this.passwordAuthRecordManagerment.updateAllRecords(APP_ID, updateItems);
		assertEquals(numBulkRequest, response.getResults().size());
		
		for (int j = 0; j < numBulkRequest; j++) {
			UpdateRecordsResponse updateRecordsResponse = (UpdateRecordsResponse) response.getResults().get(j);
			AddRecordsResponse addRecordsResponse = (AddRecordsResponse) addResponse.getResults().get(j);
			i = 0;
			for (RecordUpdateResponseItem recordUpdateResponseItem: updateRecordsResponse.getRecords()) {
				assertEquals(addRecordsResponse.getIDs().get(i),recordUpdateResponseItem.getID());
				assertEquals((Integer) (addRecordsResponse.getRevisions().get(i) + 1),recordUpdateResponseItem.getRevision());
				i++;
			}
		}
	}
	
	@Test(expected = BulksException.class)
    public void testUpdateAllRecordsShouldFailWrongRevision() throws BulksException, KintoneAPIException {
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
        this.passwordAuthRecordManagerment.updateAllRecords(APP_ID, updateItems);
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
        return testRecord;
    }

}
