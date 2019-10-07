package com.cybozu.kintone.client.module.parser;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.bulk_request.BulkRequestResponse;
import com.cybozu.kintone.client.model.record.*;
import com.cybozu.kintone.client.model.record.record.request.AddRecordRequest;
import com.cybozu.kintone.client.model.record.record.response.*;
import com.cybozu.kintone.client.module.bulkrequest.BulkRequest;
import com.cybozu.kintone.client.module.record.Record;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class BulkRequestParserTest {
    private static int APP_ID;

    private BulkRequest bulkRequest;
    private Connection connection;
    private Record passwordAuthRecordManagerment;

    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        this.connection = new Connection(TestConstants.DOMAIN, auth);
        this.connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        this.bulkRequest = new BulkRequest(this.connection);
        this.passwordAuthRecordManagerment = new Record(connection);
    }

    public HashMap<String, FieldValue> createTestRecord() {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord");
        testRecord.put("FieldCode1", fv);
        return testRecord;
    }

    @Test
    public void testParseObjectReturnAddRecordResponseSuccess() throws KintoneAPIException {
        HashMap<String, FieldValue> record = createTestRecord();
        this.bulkRequest.addRecord(APP_ID, record);

        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        Object object = results.get(0);
        BulkRequestParser brp = new BulkRequestParser();
        String parseObject = brp.parseObject(object);
        assertTrue(parseObject.contains("id"));
    }

    @Test
    public void testParseObjectReturnAddRecordsResponseSuccess() throws KintoneAPIException {
        HashMap<String, FieldValue> records1 = new HashMap<String, FieldValue>();
        HashMap<String, FieldValue> records2 = new HashMap<String, FieldValue>();
        ArrayList<HashMap<String, FieldValue>> al = new ArrayList<HashMap<String, FieldValue>>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord1");
        records1.put("FieldCode1", fv);
        al.add(records1);
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord2");
        records2.put("FieldCode1", fv);
        al.add(records2);
        this.bulkRequest.addRecords(APP_ID, al);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();
        Object object = results.get(0);
        BulkRequestParser brp = new BulkRequestParser();
        String parseObject = brp.parseObject(object);
        assertTrue(parseObject.contains("ids"));
    }

    @Test
    public void testParseObjectReturnUpdateRecordResponseSuccess() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testRecord = createTestRecord();
        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
        // Main Test processing
        Integer id = addResponse.getID();
        Integer revision = addResponse.getRevision();

        HashMap<String, FieldValue> updateRecord = new HashMap<>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord111");
        updateRecord.put("FieldCode1", fv);

        this.bulkRequest.updateRecordByID(APP_ID, id, updateRecord, revision);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        Object object = results.get(0);
        BulkRequestParser brp = new BulkRequestParser();
        String parseObject = brp.parseObject(object);
        assertTrue(parseObject.contains("revision"));
    }

    @Test
    public void testParseObjectReturnUpdateRecordsResponseSuccess() throws KintoneAPIException {
        // Preprocessing
        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
        HashMap<String, FieldValue> testRecord1 = createTestRecord();
        HashMap<String, FieldValue> testRecord2 = createTestRecord();
        records.add(testRecord1);
        records.add(testRecord2);
        AddRecordsResponse addRecordsResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);

        HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
        FieldValue fv = new FieldValue();
        fv.setType(FieldType.SINGLE_LINE_TEXT);
        fv.setValue("test_AddRecord111");
        updateRecord1.put("FieldCode1", fv);

        HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
        updateRecord2.put("FieldCode1", fv);

        ArrayList<RecordUpdateItem> updateRecords = new ArrayList<>();
        RecordUpdateItem item1 = new RecordUpdateItem(addRecordsResponse.getIDs().get(0), null, null, updateRecord1);
        RecordUpdateItem item2 = new RecordUpdateItem(addRecordsResponse.getIDs().get(1), null, null, updateRecord2);
        updateRecords.add(item1);
        updateRecords.add(item2);

        this.bulkRequest.updateRecords(APP_ID, updateRecords);
        BulkRequestResponse responses = this.bulkRequest.execute();
        ArrayList<Object> results = responses.getResults();

        BulkRequestParser brp = new BulkRequestParser();
        Object object1 = results.get(0);
        String parseObject1 = brp.parseObject(object1);
        assertTrue(parseObject1.contains("records"));
    }

    @Test
    public void testParseJsonReturnAddRecordResponseSuccess() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", APP_ID);
        JsonObject value = new JsonObject();
        value.addProperty("value", "123456");
        JsonObject textField = new JsonObject();
        textField.add("text", value);
        postBody.add("record", textField);

        JsonElement je = this.connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD,
                postBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je, AddRecordResponse.class);
        assertTrue(parseJson instanceof AddRecordResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("id"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseJsonReturnAddRecordResponseShouldFailWithInvalidJson() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", "\"\"");

        BulkRequestParser brp = new BulkRequestParser();
        brp.parseJson(postBody, AddRecordRequest.class);
    }

    @Test
    public void testParseJsonReturnAddRecordsResponseSuccess() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", APP_ID);
        JsonObject value1 = new JsonObject();
        value1.addProperty("value", "123456");
        JsonObject value2 = new JsonObject();
        value2.addProperty("value", "654321");
        JsonObject textField1 = new JsonObject();
        textField1.add("text", value1);
        JsonObject textField2 = new JsonObject();
        textField2.add("text", value2);

        JsonArray ja = new JsonArray();
        ja.add(textField1.getAsJsonObject());
        ja.add(textField2.getAsJsonObject());
        postBody.add("records", ja);

        JsonElement je = this.connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORDS,
                postBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je, AddRecordsResponse.class);
        assertTrue(parseJson instanceof AddRecordsResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("ids"));
    }

    @Test
    public void testParseJsonReturnUpdateRecordResponseSuccess() throws KintoneAPIException {
        JsonObject putBody = new JsonObject();
        putBody.addProperty("app", APP_ID);
        putBody.addProperty("id", 57);
        JsonObject value = new JsonObject();
        value.addProperty("value", "test111");
        JsonObject textField = new JsonObject();
        textField.add("text", value);
        putBody.add("record", textField);

        JsonElement je1 = this.connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD,
                putBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je1, UpdateRecordResponse.class);
        assertTrue(parseJson instanceof UpdateRecordResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("revision"));
    }

    @Test
    public void testParseJsonReturnUpdateRecordsResponseSuccess() throws KintoneAPIException {
        JsonObject putBody = new JsonObject();
        putBody.addProperty("app", APP_ID);

        JsonObject textField1 = new JsonObject();
        JsonObject textField2 = new JsonObject();

        textField1.addProperty("id", 57);
        JsonObject value1 = new JsonObject();
        value1.addProperty("value", "1234");
        JsonObject tf1 = new JsonObject();
        tf1.add("text", value1);
        textField1.add("record", tf1);

        textField2.addProperty("id", 56);
        JsonObject value2 = new JsonObject();
        value2.addProperty("value", "4321");
        JsonObject tf2 = new JsonObject();
        tf2.add("text", value2);
        textField2.add("record", tf2);

        JsonArray ja = new JsonArray();
        ja.add(textField1.getAsJsonObject());
        ja.add(textField2.getAsJsonObject());
        putBody.add("records", ja);

        JsonElement je1 = this.connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORDS,
                putBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je1, UpdateRecordsResponse.class);
        assertTrue(parseJson instanceof UpdateRecordsResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("records"));
    }

    @Test
    public void testParseJsonReturnGetRecordResponseSuccess() throws KintoneAPIException {
        JsonObject getBody = new JsonObject();
        getBody.addProperty("app", APP_ID);
        getBody.addProperty("id", 57);

        JsonElement je = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORD,
                getBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je, GetRecordResponse.class);
        assertTrue(parseJson instanceof GetRecordResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("1234"));
    }

    @Test
    public void testParseJsonReturnGetRecordsResponseSuccess() throws KintoneAPIException {
        JsonObject getBody = new JsonObject();
        getBody.addProperty("app", APP_ID);

        JsonElement je = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORDS,
                getBody.toString());
        BulkRequestParser brp = new BulkRequestParser();
        Object parseJson = brp.parseJson(je, GetRecordsResponse.class);
        assertTrue(parseJson instanceof GetRecordsResponse);
        String parseObject = brp.parseObject(parseJson);
        assertTrue(parseObject.contains("records"));
    }
}
