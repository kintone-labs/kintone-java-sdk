package com.cybozu.kintone.client.module.bulk_request;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestResponse;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateItem;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.bulkrequest.BulkRequest;
import com.cybozu.kintone.client.module.record.Record;

public class BulkRequestTest {
    private static String USERNAME = "xxxxx";
    private static String PASSWORD = "xxxxx";
	private static String API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    private static int APP_ID = 1618;
    private static int INVALID_APP_ID = -1;

    private BulkRequest bulkRequest;
    private BulkRequest bulktokenRequest;
    private Connection connection;
    private Connection tokenConnection;
    
    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        this.connection = new Connection(TestConstants.DOMAIN, auth);
        this.connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.bulkRequest = new BulkRequest(this.connection);
        
        Auth tokenauth = new Auth();
        tokenauth.setApiToken(API_TOKEN);
        this.tokenConnection = new Connection(TestConstants.DOMAIN, tokenauth);
        this.tokenConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
    }

    @Test
    public void testAddRecordSuccess() throws KintoneAPIException {
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord");
        record.put("FieldCode1", fv);
        this.bulkRequest.addRecord(APP_ID, record);

        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordResponse);
        AddRecordResponse result1 = (AddRecordResponse)results.get(0);
        assertTrue(result1.getID() instanceof Integer);
        assertSame(1, result1.getRevision());
    }
    
    @Test
    public void testAddRecordSuccessToken() throws KintoneAPIException {
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.addRecord(APP_ID, record);

        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordResponse);
        AddRecordResponse result1 = (AddRecordResponse)results.get(0);
        assertTrue(result1.getID() instanceof Integer);
        assertSame(1, result1.getRevision());
    }

    @Test
    public void testAddRecordSuccessWhenRecordNull() throws KintoneAPIException {
        this.bulkRequest.addRecord(APP_ID, null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordResponse);
        AddRecordResponse result1 = (AddRecordResponse)results.get(0);
        assertTrue(result1.getID() instanceof Integer);
        assertSame(1, result1.getRevision());
    }
    
    @Test
    public void testAddRecordSuccessWhenRecordNullToken() throws KintoneAPIException {
        this.bulktokenRequest.addRecord(APP_ID, null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordResponse);
        AddRecordResponse result1 = (AddRecordResponse)results.get(0);
        assertTrue(result1.getID() instanceof Integer);
        assertSame(1, result1.getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenAppIDisNull() throws KintoneAPIException {
        this.bulkRequest.addRecord(null, null);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenAppIDisNullToken() throws KintoneAPIException {
        this.bulktokenRequest.addRecord(null, null);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testShouldFailWhenRequestListisNull() throws KintoneAPIException {
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testShouldFailWhenRequestListisNullToken() throws KintoneAPIException {
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
    	for (int i = 0; i <= 20; i++) {
    		this.bulkRequest.addRecord(APP_ID, null);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
    	for (int i = 0; i <= 20; i++) {
    		this.bulktokenRequest.addRecord(APP_ID, null);
    	}
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailForInvalidAppId() throws KintoneAPIException {
        this.bulkRequest.addRecord(INVALID_APP_ID, null);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        this.bulktokenRequest.addRecord(INVALID_APP_ID, null);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord");
        record.put("FieldCode1", fv);
        this.bulkRequest.addRecord(APP_ID, record);
        this.bulkRequest.addRecord(INVALID_APP_ID, record);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.addRecord(APP_ID, record);
        this.bulktokenRequest.addRecord(INVALID_APP_ID, record);
        this.bulktokenRequest.execute();
    }

    @Test
    public void testAddRecordsSuccess() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecords1");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_AddRecords2");

        record1.put("FieldCode1", fv);
        record2.put("FieldCode1", fv2);
        records.add(record1);
        records.add(record2);
        records.add(null);

        this.bulkRequest.addRecords(APP_ID, records);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordsResponse);
        AddRecordsResponse result1 = (AddRecordsResponse)results.get(0);

        assertSame(3, result1.getIDs().size());
        assertTrue(result1.getIDs().get(0) instanceof Integer);
        assertTrue(result1.getIDs().get(1) instanceof Integer);
        assertTrue(result1.getIDs().get(2) instanceof Integer);
        assertSame(3, result1.getRevisions().size());
        assertSame(1, result1.getRevisions().get(0));
        assertSame(1, result1.getRevisions().get(1));
        assertSame(1, result1.getRevisions().get(2));
    }
    
    @Test
    public void testAddRecordsSuccessToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecords1");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_AddRecords2");

        record1.put("FieldCode1", fv);
        record2.put("FieldCode1", fv2);
        records.add(record1);
        records.add(record2);
        records.add(null);

        this.bulktokenRequest.addRecords(APP_ID, records);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();
        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof AddRecordsResponse);
        AddRecordsResponse result1 = (AddRecordsResponse)results.get(0);

        assertSame(3, result1.getIDs().size());
        assertTrue(result1.getIDs().get(0) instanceof Integer);
        assertTrue(result1.getIDs().get(1) instanceof Integer);
        assertTrue(result1.getIDs().get(2) instanceof Integer);
        assertSame(3, result1.getRevisions().size());
        assertSame(1, result1.getRevisions().get(0));
        assertSame(1, result1.getRevisions().get(1));
        assertSame(1, result1.getRevisions().get(2));
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenAppIDisNull() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        this.bulkRequest.addRecords(null, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenAppIDisNullToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        this.bulktokenRequest.addRecords(null, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailForInvalidAppId() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        this.bulkRequest.addRecords(INVALID_APP_ID, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        this.bulktokenRequest.addRecords(INVALID_APP_ID, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 20; i++) {
        this.bulkRequest.addRecords(APP_ID, records);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.addRecords(APP_ID, records);
    	}
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecords111");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_AddRecords2");

        record1.put("FieldCode1", fv);
        record2.put("FieldCode1", fv2);
        records.add(record1);
        records.add(record2);
        records.add(null);

        this.bulkRequest.addRecords(APP_ID, records);
        this.bulkRequest.addRecords(INVALID_APP_ID, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testAddRecordsShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecords111");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_AddRecords2");

        record1.put("FieldCode1", fv);
        record2.put("FieldCode1", fv2);
        records.add(record1);
        records.add(record2);
        records.add(null);

        this.bulktokenRequest.addRecords(APP_ID, records);
        this.bulktokenRequest.addRecords(INVALID_APP_ID, records);
        this.bulktokenRequest.execute();
    }
    
    @Test
    public void testUpdateRecordByIdSuccess() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(APP_ID, preId, record, 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByIdSuccessToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulktokenRequest.updateRecordByID(APP_ID, preId, record, 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateRecordByIdSuccessWhenRecordNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordByID(APP_ID, preId, null, 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByIdSuccessWhenRecordNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordByID(APP_ID, preId, null, 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateRecordByIdSuccessWhenRevisionNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById_revisionNull");
        record.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(APP_ID, preId, record, null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByIdSuccessWhenRevisionNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById_revisionNull");
        record.put("FieldCode1", fv);

        this.bulktokenRequest.updateRecordByID(APP_ID, preId, record, null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenAppIdNull() throws KintoneAPIException {
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(null, 1887, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulktokenRequest.updateRecordByID(null, 1887, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailForInvalidAppId() throws KintoneAPIException {
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(INVALID_APP_ID, 1887, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulktokenRequest.updateRecordByID(INVALID_APP_ID, 1887, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(APP_ID, 1887, record, 1);
        this.bulkRequest.updateRecordByID(INVALID_APP_ID, 1887, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);

        this.bulktokenRequest.updateRecordByID(APP_ID, 1887, record, 1);
        this.bulktokenRequest.updateRecordByID(INVALID_APP_ID, 1887, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);
    	for (int i = 0; i <= 20; i++) {
        this.bulkRequest.updateRecordByID(APP_ID, 1887, record, 1);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByIdShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordById");
        record.put("FieldCode1", fv);
    	for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.updateRecordByID(APP_ID, 1887, record, 1);
    	}
        this.bulktokenRequest.execute();
    }

    @Test
    public void testUpdateRecordByUpdateKeySuccess() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByUpdateKeySuccessToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeySuccessWhenRecordNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, null, 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByUpdateKeySuccessWhenRecordNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, null, 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateRecordByUpdateKeySuccessWhenRevisionNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey_revisionNull");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordByUpdateKeySuccessWhenRevisionNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();

        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey_revisionNull");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulkRequest.updateRecordByUpdateKey(null, uKey, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.updateRecordByUpdateKey(null, uKey, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidAppId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulkRequest.updateRecordByUpdateKey(INVALID_APP_ID, uKey, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.updateRecordByUpdateKey(INVALID_APP_ID, uKey, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        this.bulkRequest.updateRecordByUpdateKey(INVALID_APP_ID, uKey, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        this.bulktokenRequest.updateRecordByUpdateKey(INVALID_APP_ID, uKey, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidUpdateKey() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
    	this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("Error Field", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidUpdateKeyToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
    	this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("Error Field", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
    	this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
    	for (int i = 0; i <= 20; i++) {
        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
    	this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);
        
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey");
        record.put("FieldCode1", fv);
    	for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1);
    	}
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey_revisionNull");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulkRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1000);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordByUpdateKeyShouldFailForInvalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre1 = new FieldValue();
        FieldValue fv_pre2 = new FieldValue();
        fv_pre1.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value = UUID.randomUUID().toString();
        fv_pre1.setValue(unique_value);
        record_pre.put("文字列__1行__0", fv_pre1);
        fv_pre2.setValue("test_value");
        record_pre.put("FieldCode1", fv_pre2);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record = new HashMap<String, FieldValue>();

        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_updateRecordByUpdateKey_revisionNull");
        record.put("FieldCode1", fv);
        RecordUpdateKey uKey = new RecordUpdateKey("文字列__1行__0", unique_value);

        this.bulktokenRequest.updateRecordByUpdateKey(APP_ID, uKey, record, 1000);
        this.bulktokenRequest.execute();
    }

    @Test
    public void testUpdateRecordsSuccess() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record4_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record5_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record6_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_value3");
        FieldValue fv4_1_pre = new FieldValue();
        fv4_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv4_1_pre.setValue("test_value4");
        FieldValue fv4_2_pre = new FieldValue();
        fv4_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_4_2 = UUID.randomUUID().toString();
        fv4_2_pre.setValue(unique_value_4_2);
        FieldValue fv5_1_pre = new FieldValue();
        fv5_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv5_1_pre.setValue("test_value5");
        FieldValue fv5_2_pre = new FieldValue();
        fv5_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_5_2 = UUID.randomUUID().toString();
        fv5_2_pre.setValue(unique_value_5_2);
        FieldValue fv6_1_pre = new FieldValue();
        fv6_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv6_1_pre.setValue("test_value6");
        FieldValue fv6_2_pre = new FieldValue();
        fv6_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_6_2 = UUID.randomUUID().toString();
        fv6_2_pre.setValue(unique_value_6_2);

        record1_pre.put("FieldCode1", fv1_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        record4_pre.put("FieldCode1", fv4_1_pre);
        record4_pre.put("文字列__1行__0", fv4_2_pre);
        record5_pre.put("FieldCode1", fv5_1_pre);
        record5_pre.put("文字列__1行__0", fv5_2_pre);
        record6_pre.put("FieldCode1", fv6_1_pre);
        record6_pre.put("文字列__1行__0", fv6_2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);
        records_pre.add(record4_pre);
        records_pre.add(record5_pre);
        records_pre.add(record6_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        Integer preId2 = result1_pre.getIDs().get(1);
        Integer preId3 = result1_pre.getIDs().get(2);
        Integer preId4 = result1_pre.getIDs().get(3);
        Integer preId5 = result1_pre.getIDs().get(4);
        Integer preId6 = result1_pre.getIDs().get(5);
        RecordUpdateKey uKey4 = new RecordUpdateKey("文字列__1行__0", unique_value_4_2);
        RecordUpdateKey uKey5 = new RecordUpdateKey("文字列__1行__0", unique_value_5_2);
        RecordUpdateKey uKey6 = new RecordUpdateKey("文字列__1行__0", unique_value_6_2);

        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record4 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record5 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_updateRecords2");
        FieldValue fv4 = new FieldValue();
        fv4.setType(FieldType.SINGLE_LINE_TEXT);
        fv4.setValue("test_updateRecords4");
        FieldValue fv5 = new FieldValue();
        fv5.setType(FieldType.SINGLE_LINE_TEXT);
        fv5.setValue("test_updateRecords5");

        record1.put("FieldCode1", fv1);
        record2.put("FieldCode1", fv2);
        record4.put("FieldCode1", fv4);
        record5.put("FieldCode1", fv5);

        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));
        records.add(new RecordUpdateItem(preId2, null, null, record2));
        records.add(new RecordUpdateItem(preId3, null, null, null));
        records.add(new RecordUpdateItem(null, 1, uKey4, record4));
        records.add(new RecordUpdateItem(null, null, uKey5, record5));
        records.add(new RecordUpdateItem(null, null, uKey6, null));

        this.bulkRequest.updateRecords(APP_ID, records);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordsResponse);
        UpdateRecordsResponse result1 = (UpdateRecordsResponse)results.get(0);

        assertSame(6, result1.getRecords().size());
        assertEquals(preId1, result1.getRecords().get(0).getID());
        assertEquals(preId2, result1.getRecords().get(1).getID());
        assertEquals(preId3, result1.getRecords().get(2).getID());
        assertEquals(preId4, result1.getRecords().get(3).getID());
        assertEquals(preId5, result1.getRecords().get(4).getID());
        assertEquals(preId6, result1.getRecords().get(5).getID());
        assertSame(2, result1.getRecords().get(0).getRevision());
        assertSame(2, result1.getRecords().get(1).getRevision());
        assertSame(2, result1.getRecords().get(2).getRevision());
        assertSame(2, result1.getRecords().get(3).getRevision());
        assertSame(2, result1.getRecords().get(4).getRevision());
        assertSame(2, result1.getRecords().get(5).getRevision());
    }
    
    @Test
    public void testUpdateRecordsSuccessToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record4_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record5_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record6_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_value3");
        FieldValue fv4_1_pre = new FieldValue();
        fv4_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv4_1_pre.setValue("test_value4");
        FieldValue fv4_2_pre = new FieldValue();
        fv4_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_4_2 = UUID.randomUUID().toString();
        fv4_2_pre.setValue(unique_value_4_2);
        FieldValue fv5_1_pre = new FieldValue();
        fv5_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv5_1_pre.setValue("test_value5");
        FieldValue fv5_2_pre = new FieldValue();
        fv5_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_5_2 = UUID.randomUUID().toString();
        fv5_2_pre.setValue(unique_value_5_2);
        FieldValue fv6_1_pre = new FieldValue();
        fv6_1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv6_1_pre.setValue("test_value6");
        FieldValue fv6_2_pre = new FieldValue();
        fv6_2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        String unique_value_6_2 = UUID.randomUUID().toString();
        fv6_2_pre.setValue(unique_value_6_2);

        record1_pre.put("FieldCode1", fv1_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        record4_pre.put("FieldCode1", fv4_1_pre);
        record4_pre.put("文字列__1行__0", fv4_2_pre);
        record5_pre.put("FieldCode1", fv5_1_pre);
        record5_pre.put("文字列__1行__0", fv5_2_pre);
        record6_pre.put("FieldCode1", fv6_1_pre);
        record6_pre.put("文字列__1行__0", fv6_2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);
        records_pre.add(record4_pre);
        records_pre.add(record5_pre);
        records_pre.add(record6_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        Integer preId2 = result1_pre.getIDs().get(1);
        Integer preId3 = result1_pre.getIDs().get(2);
        Integer preId4 = result1_pre.getIDs().get(3);
        Integer preId5 = result1_pre.getIDs().get(4);
        Integer preId6 = result1_pre.getIDs().get(5);
        RecordUpdateKey uKey4 = new RecordUpdateKey("文字列__1行__0", unique_value_4_2);
        RecordUpdateKey uKey5 = new RecordUpdateKey("文字列__1行__0", unique_value_5_2);
        RecordUpdateKey uKey6 = new RecordUpdateKey("文字列__1行__0", unique_value_6_2);

        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record4 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record5 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_updateRecords2");
        FieldValue fv4 = new FieldValue();
        fv4.setType(FieldType.SINGLE_LINE_TEXT);
        fv4.setValue("test_updateRecords4");
        FieldValue fv5 = new FieldValue();
        fv5.setType(FieldType.SINGLE_LINE_TEXT);
        fv5.setValue("test_updateRecords5");

        record1.put("FieldCode1", fv1);
        record2.put("FieldCode1", fv2);
        record4.put("FieldCode1", fv4);
        record5.put("FieldCode1", fv5);

        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));
        records.add(new RecordUpdateItem(preId2, null, null, record2));
        records.add(new RecordUpdateItem(preId3, null, null, null));
        records.add(new RecordUpdateItem(null, 1, uKey4, record4));
        records.add(new RecordUpdateItem(null, null, uKey5, record5));
        records.add(new RecordUpdateItem(null, null, uKey6, null));

        this.bulktokenRequest.updateRecords(APP_ID, records);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordsResponse);
        UpdateRecordsResponse result1 = (UpdateRecordsResponse)results.get(0);

        assertSame(6, result1.getRecords().size());
        assertEquals(preId1, result1.getRecords().get(0).getID());
        assertEquals(preId2, result1.getRecords().get(1).getID());
        assertEquals(preId3, result1.getRecords().get(2).getID());
        assertEquals(preId4, result1.getRecords().get(3).getID());
        assertEquals(preId5, result1.getRecords().get(4).getID());
        assertEquals(preId6, result1.getRecords().get(5).getID());
        assertSame(2, result1.getRecords().get(0).getRevision());
        assertSame(2, result1.getRecords().get(1).getRevision());
        assertSame(2, result1.getRecords().get(2).getRevision());
        assertSame(2, result1.getRecords().get(3).getRevision());
        assertSame(2, result1.getRecords().get(4).getRevision());
        assertSame(2, result1.getRecords().get(5).getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulkRequest.updateRecords(null, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulktokenRequest.updateRecords(null, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulkRequest.updateRecords(APP_ID, records);
        this.bulkRequest.updateRecords(INVALID_APP_ID, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulktokenRequest.updateRecords(APP_ID, records);
        this.bulktokenRequest.updateRecords(INVALID_APP_ID, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailForInvalidId() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulkRequest.updateRecords(INVALID_APP_ID, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailForInvalidIdToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));

        this.bulktokenRequest.updateRecords(INVALID_APP_ID, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailForInvalidRecords() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1000, null, record1));

        this.bulkRequest.updateRecords(APP_ID, records);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailForInvalidRecordsToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1000, null, record1));

        this.bulktokenRequest.updateRecords(APP_ID, records);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));
    	for (int i = 0; i <= 20; i++) {
        this.bulkRequest.updateRecords(APP_ID, records);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordsShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv1_pre = new FieldValue();
        fv1_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv1_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv1_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        Integer preId1 = result1_pre.getIDs().get(0);
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecords1");
        record1.put("FieldCode1", fv1);
        ArrayList<RecordUpdateItem> records = new ArrayList<RecordUpdateItem>();
        records.add(new RecordUpdateItem(preId1, 1, null, record1));
    	for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.updateRecords(APP_ID, records);
    	}
        this.bulktokenRequest.execute();
    }

    @Test
    public void testDeleteRecordsSuccess() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        ids.add(result1_pre.getIDs().get(1));

        this.bulkRequest.deleteRecords(APP_ID, ids);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof HashMap);
        assertTrue(((HashMap)results.get(0)).isEmpty());
    }
    
    @Test
    public void testDeleteRecordsSuccessToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();

        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        ids.add(result1_pre.getIDs().get(1));

        this.bulktokenRequest.deleteRecords(APP_ID, ids);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof HashMap);
        assertTrue(((HashMap)results.get(0)).isEmpty());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        this.bulkRequest.deleteRecords(null, ids);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        this.bulktokenRequest.deleteRecords(null, ids);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<Integer> id1 = new ArrayList<Integer>();
        ArrayList<Integer> id2 = new ArrayList<Integer>();
        id1.add(result1_pre.getIDs().get(0));
        id2.add(result1_pre.getIDs().get(1));

        this.bulkRequest.deleteRecords(APP_ID, id1);
        this.bulkRequest.deleteRecords(INVALID_APP_ID, id2);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<Integer> id1 = new ArrayList<Integer>();
        ArrayList<Integer> id2 = new ArrayList<Integer>();
        id1.add(result1_pre.getIDs().get(0));
        id2.add(result1_pre.getIDs().get(1));

        this.bulktokenRequest.deleteRecords(APP_ID, id1);
        this.bulktokenRequest.deleteRecords(INVALID_APP_ID, id2);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailForInvalidAppId() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        this.bulkRequest.deleteRecords(INVALID_APP_ID, ids);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(result1_pre.getIDs().get(0));
        this.bulktokenRequest.deleteRecords(INVALID_APP_ID, ids);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
            HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
            FieldValue fv_pre = new FieldValue();
            fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
            fv_pre.setValue("test_value1");
            record1_pre.put("FieldCode1", fv_pre);
            records_pre.add(record1_pre);
		}
        Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        
    	for (int i = 0; i <= 20; i++) {
    		ArrayList<Integer> ids = new ArrayList<Integer>();
    		ids.add(addRecords.getIDs().get(i));
    		this.bulkRequest.deleteRecords(APP_ID, ids);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
            HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
            FieldValue fv_pre = new FieldValue();
            fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
            fv_pre.setValue("test_value1");
            record1_pre.put("FieldCode1", fv_pre);
            records_pre.add(record1_pre);
		}
        Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        
    	for (int i = 0; i <= 20; i++) {
    		ArrayList<Integer> ids = new ArrayList<Integer>();
    		ids.add(addRecords.getIDs().get(i));
    		this.bulktokenRequest.deleteRecords(APP_ID, ids);
    	}
        this.bulktokenRequest.execute();
    }

    @Test
    public void testDeleteRecordsWithRevisionSuccess() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_value3");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);
        idsWithRevision.put(result1_pre.getIDs().get(1), null);
        idsWithRevision.put(result1_pre.getIDs().get(2), -1);

        this.bulkRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof HashMap);
        assertTrue(((HashMap)results.get(0)).isEmpty());
    }
    
    @Test
    public void testDeleteRecordsWithRevisionSuccessToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_value3");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);
        idsWithRevision.put(result1_pre.getIDs().get(1), null);
        idsWithRevision.put(result1_pre.getIDs().get(2), -1);

        this.bulktokenRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof HashMap);
        assertTrue(((HashMap)results.get(0)).isEmpty());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);

        this.bulkRequest.deleteRecordsWithRevision(null, idsWithRevision);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);

        this.bulktokenRequest.deleteRecordsWithRevision(null, idsWithRevision);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailForInvalidAppIDs() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);

        this.bulkRequest.deleteRecordsWithRevision(INVALID_APP_ID, idsWithRevision);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailForInvalidAppIDsToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 1);

        this.bulktokenRequest.deleteRecordsWithRevision(INVALID_APP_ID, idsWithRevision);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<Integer, Integer> idsWithRevision1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> idsWithRevision2 = new HashMap<Integer, Integer>();
        idsWithRevision1.put(result1_pre.getIDs().get(0), 1);
        idsWithRevision2.put(result1_pre.getIDs().get(1), null);

        this.bulkRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision1);
        this.bulkRequest.deleteRecordsWithRevision(INVALID_APP_ID, idsWithRevision2);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_value2");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<Integer, Integer> idsWithRevision1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> idsWithRevision2 = new HashMap<Integer, Integer>();
        idsWithRevision1.put(result1_pre.getIDs().get(0), 1);
        idsWithRevision2.put(result1_pre.getIDs().get(1), null);

        this.bulktokenRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision1);
        this.bulktokenRequest.deleteRecordsWithRevision(INVALID_APP_ID, idsWithRevision2);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailForInvalidRevision() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 100);

        this.bulkRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailForInvalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_value1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
        idsWithRevision.put(result1_pre.getIDs().get(0), 100);

        this.bulktokenRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
            HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
            FieldValue fv_pre = new FieldValue();
            fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
            fv_pre.setValue("test_value1");
            record1_pre.put("FieldCode1", fv_pre);
            records_pre.add(record1_pre);
		}
        Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
    	for (int i = 0; i <= 20; i++) {
    		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
    		idsWithRevision.put(addRecords.getIDs().get(i), 1);
    		this.bulkRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRecordsWithRevisionShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
            HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
            FieldValue fv_pre = new FieldValue();
            fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
            fv_pre.setValue("test_value1");
            record1_pre.put("FieldCode1", fv_pre);
            records_pre.add(record1_pre);
		}
        Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
    	for (int i = 0; i <= 20; i++) {
    		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
    		idsWithRevision.put(addRecords.getIDs().get(i), 1);
    		this.bulktokenRequest.deleteRecordsWithRevision(APP_ID, idsWithRevision);
    	}
        this.bulktokenRequest.execute();
    }

    @Test
    public void testUpdateAssigneesRecordSuccess() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxx");

        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateAssigneesRecordSuccessToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxx");

        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateAssigneesRecordSuccessWhenRevisionNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees_revisionNull");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxx");
        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateAssigneesRecordSuccessWhenRevisionNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees_revisionNull");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxx");
        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }

    @Test
    public void testUpdateAssigneesRecordSuccessWhenAssigneesNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees_assigneesNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);

        this.bulkRequest.updateRecordAssignees(APP_ID, preId, new ArrayList<String>(), -1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test
    public void testUpdateAssigneesRecordSuccessWhenAssigneesNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees_assigneesNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);

        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, new ArrayList<String>(), -1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(2, result1.getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxx");

        this.bulkRequest.updateRecordAssignees(null, preId, assignees, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxx");

        this.bulktokenRequest.updateRecordAssignees(null, preId, assignees, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxx");
    	for (int i = 0; i <= 20; i++) {
        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
    	}
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxx");
    	for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
    	}
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxxx");
        
        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        assignees.add("xxxxxxxxxx");
        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxx");
        
        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        assignees.add("xxxxxxx");
        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, null);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidAppId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxx");

        this.bulkRequest.updateRecordAssignees(INVALID_APP_ID, preId, assignees, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxx");

        this.bulktokenRequest.updateRecordAssignees(INVALID_APP_ID, preId, assignees, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxx");

        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxxxxx");

        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulkRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxxxx");

        this.bulkRequest.updateRecordAssignees(APP_ID, preId, assignees, 100);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateAssigneesRecordShouldFailForInvalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordAssignees");
        record_pre.put("FieldCode1", fv_pre);

        this.bulktokenRequest.addRecord(APP_ID, record_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<String> assignees = new ArrayList<String>();
        assignees.add("xxxxxxxxxxx");

        this.bulktokenRequest.updateRecordAssignees(APP_ID, preId, assignees, 100);
        this.bulktokenRequest.execute();
    }
    
    @Test
    public void testUpdateRecordStatusSuccess() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);

        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理開始", "tomoya-takaki", 1);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(3, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordStatusSuccessToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);

        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxxx", 1);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(3, result1.getRevision());
    }

    @Test
    public void testUpdateRecordStatusSuccessWhenRevisionNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus_revisionNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);

        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxxxx", null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(3, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordStatusSuccessWhenRevisionNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus_revisionNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);

        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxxxxxx", null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(3, result1.getRevision());
    }

    @Test
    public void testUpdateRecordStatusSuccessWhenAssigneesNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus_AssigneesNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);

        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxxxxxxx", null);
        this.bulkRequest.execute();
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(APP_ID, preId, "レビューする", null, null);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(5, result1.getRevision());
    }
    
    @Test
    public void testUpdateRecordStatusSuccessWhenAssigneesNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();
        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus_AssigneesNull");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);

        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxx", null);
        this.bulktokenRequest.execute();
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "レビューする", null, null);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordResponse);
        UpdateRecordResponse result1 = (UpdateRecordResponse)results.get(0);
        assertSame(5, result1.getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(null, preId, "処理開始", "xxxxxxxxxxx", 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(null, preId, "処理開始", "xxxxxxxxxxxxx", 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
    	ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 20; i++) {
    		HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
    		FieldValue fv_pre = new FieldValue();
    		fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
    		fv_pre.setValue("test_updateRecordStatus");
    		record1_pre.put("FieldCode1", fv_pre);
    		records_pre.add(record1_pre);
    	}
    	Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        for (int i = 0; i <= 20; i++) {
        this.bulkRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(i), "処理開始", "xxxxxxxxxx", addRecords.getRevisions().get(i));
        }
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
    	ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 20; i++) {
    		HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
    		FieldValue fv_pre = new FieldValue();
    		fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
    		fv_pre.setValue("test_updateRecordStatus");
    		record1_pre.put("FieldCode1", fv_pre);
    		records_pre.add(record1_pre);
    	}
    	Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        for (int i = 0; i <= 20; i++) {
        this.bulktokenRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(i), "処理開始", "xxxxxxxxxxxx", addRecords.getRevisions().get(i));
        }
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenSecondRequestIsWrong() throws KintoneAPIException {
        // Preprocessing
    	ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 1; i++) {
    		HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
    		FieldValue fv_pre = new FieldValue();
    		fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
    		fv_pre.setValue("test_updateRecordStatus");
    		record1_pre.put("FieldCode1", fv_pre);
    		records_pre.add(record1_pre);
    	}
    	Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        this.bulkRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(0), "処理開始", "xxxxxxxxxxx", addRecords.getRevisions().get(0));
        this.bulkRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(1), "処理開始1", "xxxxxxxxxx", addRecords.getRevisions().get(1));
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailWhenSecondRequestIsWrongToken() throws KintoneAPIException {
        // Preprocessing
    	ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
    	for (int i = 0; i <= 1; i++) {
    		HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
    		FieldValue fv_pre = new FieldValue();
    		fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
    		fv_pre.setValue("test_updateRecordStatus");
    		record1_pre.put("FieldCode1", fv_pre);
    		records_pre.add(record1_pre);
    	}
    	Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        this.bulktokenRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(0), "処理開始", "xxxxxxxxxxx", addRecords.getRevisions().get(0));
        this.bulktokenRequest.updateRecordStatus(APP_ID, addRecords.getIDs().get(1), "処理開始1", "xxxxxxxxxx", addRecords.getRevisions().get(1));
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidAppId() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(INVALID_APP_ID, preId, "処理開始", "xxxxxxxxxxxx", 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(INVALID_APP_ID, preId, "処理開始", "xxxxxxxxxx", 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidAssignee() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxx", 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxx", 1);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxx", 100);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理開始", "xxxxxxxxxx", 100);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidStatus() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        this.bulkRequest.updateRecordStatus(APP_ID, preId, "処理1開始", "xxxxxxxxxx", 1);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRecordStatusShouldFailForInvalidStatusToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordStatus");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        this.bulktokenRequest.updateRecordStatus(APP_ID, preId, "処理1開始", "xxxxxxxxxxx", 1);
        this.bulktokenRequest.execute();
    }

    @Test
    public void testUpdateStatusRecordsSuccess() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_updateRecordsStatus2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_updateRecordsStatus3");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", result1_pre.getIDs().get(0), 1));
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", result1_pre.getIDs().get(1), null));
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", result1_pre.getIDs().get(2), -1));

        this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordsResponse);
        UpdateRecordsResponse result1 = (UpdateRecordsResponse)results.get(0);

        assertSame(3, result1.getRecords().size());
        assertEquals(result1_pre.getIDs().get(0), result1.getRecords().get(0).getID());
        assertEquals(result1_pre.getIDs().get(1), result1.getRecords().get(1).getID());
        assertEquals(result1_pre.getIDs().get(2), result1.getRecords().get(2).getID());
        assertSame(3, result1.getRecords().get(0).getRevision());
        assertSame(3, result1.getRecords().get(1).getRevision());
        assertSame(3, result1.getRecords().get(2).getRevision());
    }
    
    @Test
    public void testUpdateStatusRecordsSuccessToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record2_pre = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> record3_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        FieldValue fv2_pre = new FieldValue();
        fv2_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv2_pre.setValue("test_updateRecordsStatus2");
        FieldValue fv3_pre = new FieldValue();
        fv3_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv3_pre.setValue("test_updateRecordsStatus3");

        record1_pre.put("FieldCode1", fv_pre);
        record2_pre.put("FieldCode1", fv2_pre);
        record3_pre.put("FieldCode1", fv3_pre);
        records_pre.add(record1_pre);
        records_pre.add(record2_pre);
        records_pre.add(record3_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxx", result1_pre.getIDs().get(0), 1));
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxx", result1_pre.getIDs().get(1), null));
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxx", result1_pre.getIDs().get(2), -1));

        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        BulkRequestResponse responses = this.bulktokenRequest.execute();
        ArrayList<Object> results = responses.getResults();

        assertNotNull(results);
        assertSame(1, results.size());
        assertTrue(results.get(0) instanceof UpdateRecordsResponse);
        UpdateRecordsResponse result1 = (UpdateRecordsResponse)results.get(0);

        assertSame(3, result1.getRecords().size());
        assertEquals(result1_pre.getIDs().get(0), result1.getRecords().get(0).getID());
        assertEquals(result1_pre.getIDs().get(1), result1.getRecords().get(1).getID());
        assertEquals(result1_pre.getIDs().get(2), result1.getRecords().get(2).getID());
        assertSame(3, result1.getRecords().get(0).getRevision());
        assertSame(3, result1.getRecords().get(1).getRevision());
        assertSame(3, result1.getRecords().get(2).getRevision());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenAppIdNull() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulkRequest.updateRecordsStatus(null, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenAppIdNullToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulktokenRequest.updateRecordsStatus(null, rusi);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidAppId() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulkRequest.updateRecordsStatus(INVALID_APP_ID, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidAppIdToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulktokenRequest.updateRecordsStatus(INVALID_APP_ID, rusi);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidAssignee() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidAssigneeToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(0), 1));
        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidRecord() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("重复禁止");
        record1_pre.put("文字列__1行__0", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(1), 1));
        this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidRecordToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("重复禁止");
        record1_pre.put("文字列__1行__0", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", result1_pre.getIDs().get(1), 1));
        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenSecondListIsWrong() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 1; i++) {
        	HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        	FieldValue fv_pre = new FieldValue();
        	fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        	fv_pre.setValue("test_updateRecordsStatus1");
        	record1_pre.put("FieldCode1", fv_pre);
        	records_pre.add(record1_pre);
        }
        Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        ArrayList<RecordUpdateStatusItem> rusi1 = new ArrayList<RecordUpdateStatusItem>();
        rusi1.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", addRecords.getIDs().get(0), null));
        this.bulkRequest.updateRecordsStatus(APP_ID, rusi1);
        
        ArrayList<RecordUpdateStatusItem> rusi2 = new ArrayList<RecordUpdateStatusItem>();
        rusi2.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", addRecords.getIDs().get(1), null));
        this.bulkRequest.updateRecordsStatus(APP_ID, rusi2);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenSecondListIsWrongToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 1; i++) {
        	HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        	FieldValue fv_pre = new FieldValue();
        	fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        	fv_pre.setValue("test_updateRecordsStatus1");
        	record1_pre.put("FieldCode1", fv_pre);
        	records_pre.add(record1_pre);
        }
        Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        ArrayList<RecordUpdateStatusItem> rusi1 = new ArrayList<RecordUpdateStatusItem>();
        rusi1.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", addRecords.getIDs().get(0), null));
        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi1);
        
        ArrayList<RecordUpdateStatusItem> rusi2 = new ArrayList<RecordUpdateStatusItem>();
        rusi2.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", addRecords.getIDs().get(1), null));
        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi2);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenRequestNumbersAreOverflow() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
        	HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        	FieldValue fv_pre = new FieldValue();
        	fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        	fv_pre.setValue("test_updateRecordsStatus1");
        	record1_pre.put("FieldCode1", fv_pre);
        	records_pre.add(record1_pre);
        }
        Record record = new Record(this.connection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        for (int i = 0; i <= 20; i++) {
        	ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        	rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", addRecords.getIDs().get(i), null));
        	this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        }
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailWhenRequestNumbersAreOverflowToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        for (int i = 0; i <= 20; i++) {
        	HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();
        	FieldValue fv_pre = new FieldValue();
        	fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        	fv_pre.setValue("test_updateRecordsStatus1");
        	record1_pre.put("FieldCode1", fv_pre);
        	records_pre.add(record1_pre);
        }
        Record record = new Record(this.tokenConnection);
        AddRecordsResponse addRecords = record.addRecords(APP_ID, records_pre);
        for (int i = 0; i <= 20; i++) {
        	ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        	rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", addRecords.getIDs().get(i), null));
        	this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        }
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidStatus() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開", "xxxxxxxxxx", result1_pre.getIDs().get(0), 1));

        this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvalidStatusToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開", "xxxxxxxxx", result1_pre.getIDs().get(0), 1));

        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulktokenRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvlalidRevision() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulkRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulkRequest = new BulkRequest(this.connection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxx", result1_pre.getIDs().get(0), 100));
        this.bulkRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulkRequest.execute();
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testUpdateStatusRecordsShouldFailForInvlalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records_pre = new ArrayList<HashMap<String, FieldValue>>();
        HashMap<String, FieldValue> record1_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordsStatus1");
        record1_pre.put("FieldCode1", fv_pre);
        records_pre.add(record1_pre);

        this.bulktokenRequest.addRecords(APP_ID, records_pre);
        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordsResponse result1_pre = (AddRecordsResponse)results_pre.get(0);
        // Main Test processing
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        ArrayList<RecordUpdateStatusItem> rusi = new ArrayList<RecordUpdateStatusItem>();
        rusi.add(new RecordUpdateStatusItem("処理開始", "xxxxxxxxxx", result1_pre.getIDs().get(0), 100));
        this.bulktokenRequest.updateRecordsStatus(APP_ID, rusi);
        this.bulktokenRequest.execute();
    }

    @Test
    public void testUpdateRecordByIdShouldFailForInvalidRevision() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordFail1");
        record_pre.put("FieldCode1", fv_pre);
        this.bulkRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulkRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulkRequest = new BulkRequest(this.connection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecordFail2");
        record1.put("FieldCode1", fv1);

        this.bulkRequest.addRecord(APP_ID, record1);
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_updateRecordById");
        record2.put("FieldCode1", fv2);

        this.bulkRequest.updateRecordByID(APP_ID, preId, record2, 100);
        HashMap<String, FieldValue> record3 = new HashMap<String, FieldValue>();

        FieldValue fv3 = new FieldValue();
        fv3.setType(FieldType.SINGLE_LINE_TEXT);
        fv3.setValue("test_updateRecordFail3");
        record3.put("FieldCode1", fv3);

        this.bulkRequest.addRecord(APP_ID, record3);

        try {
            this.bulkRequest.execute();
        } catch(KintoneAPIException ex) {
            String[] exinfo = ex.toString().split(", ", 0);
            assertEquals("api_no: 2", exinfo[0]);
            assertEquals("method: PUT", exinfo[1]);
            assertEquals("api_name: /k/v1/record.json", exinfo[2]);
            assertEquals("code: GAIA_CO02", exinfo[4]);
            assertEquals("message: 指定したrevisionは最新ではありません。ほかのユーザーがレコードを更新した可能性があります。", exinfo[5]);
            assertEquals("status: 409", exinfo[7]);
        }
    }
    
    @Test
    public void testUpdateRecordByIdShouldFailForInvalidRevisionToken() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> record_pre = new HashMap<String, FieldValue>();

        FieldValue fv_pre = new FieldValue();
        fv_pre.setType(FieldType.SINGLE_LINE_TEXT);
        fv_pre.setValue("test_updateRecordFail1");
        record_pre.put("FieldCode1", fv_pre);
        this.bulktokenRequest.addRecord(APP_ID, record_pre);

        BulkRequestResponse responses_pre = this.bulktokenRequest.execute();
        ArrayList<Object> results_pre = responses_pre.getResults();
        AddRecordResponse result_pre = (AddRecordResponse)results_pre.get(0);
        // Main Test processing
        Integer preId = result_pre.getID();
        this.bulktokenRequest = new BulkRequest(this.tokenConnection);
        HashMap<String, FieldValue> record1 = new HashMap<String, FieldValue>();

        FieldValue fv1 = new FieldValue();
        fv1.setType(FieldType.SINGLE_LINE_TEXT);
        fv1.setValue("test_updateRecordFail2");
        record1.put("FieldCode1", fv1);

        this.bulktokenRequest.addRecord(APP_ID, record1);
        HashMap<String, FieldValue> record2 = new HashMap<String, FieldValue>();

        FieldValue fv2 = new FieldValue();
        fv2.setType(FieldType.SINGLE_LINE_TEXT);
        fv2.setValue("test_updateRecordById");
        record2.put("FieldCode1", fv2);

        this.bulktokenRequest.updateRecordByID(APP_ID, preId, record2, 100);
        HashMap<String, FieldValue> record3 = new HashMap<String, FieldValue>();

        FieldValue fv3 = new FieldValue();
        fv3.setType(FieldType.SINGLE_LINE_TEXT);
        fv3.setValue("test_updateRecordFail3");
        record3.put("FieldCode1", fv3);

        this.bulktokenRequest.addRecord(APP_ID, record3);

        try {
            this.bulktokenRequest.execute();
        } catch(KintoneAPIException ex) {
            String[] exinfo = ex.toString().split(", ", 0);
            assertEquals("api_no: 2", exinfo[0]);
            assertEquals("method: PUT", exinfo[1]);
            assertEquals("api_name: /k/v1/record.json", exinfo[2]);
            assertEquals("code: GAIA_CO02", exinfo[4]);
            assertEquals("message: 指定したrevisionは最新ではありません。ほかのユーザーがレコードを更新した可能性があります。", exinfo[5]);
            assertEquals("status: 409", exinfo[7]);
        }
    }
}
