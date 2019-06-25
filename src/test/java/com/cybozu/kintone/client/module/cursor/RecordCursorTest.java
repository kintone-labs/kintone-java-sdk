package com.cybozu.kintone.client.module.cursor;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.cursor.CreateRecordCursorResponse;
import com.cybozu.kintone.client.model.cursor.GetRecordCursorResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.module.app.App;
import com.cybozu.kintone.client.module.file.File;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;

public class RecordCursorTest {
	private RecordCursor recordCursor;
	@Before
    public void setup() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, auth);
//        passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.recordCursor = new RecordCursor(passwordAuthConnection);

    }
	
	@Test
    public void testCreateCursorShouldSuccess() throws KintoneAPIException {
//        ArrayList<String> fields = new ArrayList<String>();
//        fields.add("field_!");
        Integer app = 107;
//        String query = "";
//        Integer size = 500;
        
        CreateRecordCursorResponse cursor = this.recordCursor.createCursor(app, null, null, null);
        System.out.println(cursor.getId());
        System.out.println(cursor.getTotalCount());
        assertTrue(!cursor.getId().isEmpty());
    }
	
	@Test
    public void testGetRecordsCursorShouldSuccess() throws KintoneAPIException {
		Integer app = 107;
		CreateRecordCursorResponse cursor = this.recordCursor.createCursor(app, null, null, null);
        
        GetRecordCursorResponse records = this.recordCursor.getRecords(cursor.getId());
        	System.out.println(records.getNext());
			System.out.println(records.getRecords().get(0));
			System.out.println(records.getRecords().size());
    }
	
	@Test
    public void testGetAllRecordsCursorShouldSuccess() throws KintoneAPIException {
		Integer app = 107;
		CreateRecordCursorResponse cursor = this.recordCursor.createCursor(app, null, null, null);
        
        GetRecordsResponse records = this.recordCursor.getAllRecords(cursor.getId());
			System.out.println(records.getRecords().get(0));
			System.out.println(records.getRecords().size());
    }
	
	@Test (expected = KintoneAPIException.class)
    public void testDeleteCursorShouldSuccess() throws KintoneAPIException {
		Integer app = 107;
		CreateRecordCursorResponse cursor = this.recordCursor.createCursor(app, null, null, null);
		this.recordCursor.deleteCursor(cursor.getId());
        this.recordCursor.getRecords(cursor.getId());
    }
	
}
