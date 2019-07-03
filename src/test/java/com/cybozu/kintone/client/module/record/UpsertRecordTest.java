package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;

public class UpsertRecordTest {
    private static Integer APP_ID = 1;

    private Record passwordAuthRecordManagerment;
    private ArrayList<Integer> recordsToDelete = new ArrayList<Integer>();

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);
    }

    public HashMap<String, FieldValue> createTestRecord(String suffix) {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "title", FieldType.SINGLE_LINE_TEXT, "Title" + suffix);
        testRecord = addField(testRecord, "description", FieldType.SINGLE_LINE_TEXT, "Description");
        
        return testRecord;
    }

    public HashMap<String, FieldValue> createUpsertRecord() {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();
        testRecord = addField(testRecord, "description", FieldType.SINGLE_LINE_TEXT, "Description");
        
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
    public void testUpsertRecordInsert() throws KintoneAPIException {
        // Preprocessing
        HashMap<String, FieldValue> testUniqueRecord1 = createTestRecord( String.valueOf(System.currentTimeMillis()) );

        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testUniqueRecord1);
        this.recordsToDelete.add(addResponse.getID());
        Integer revision = addResponse.getRevision();
        // Test 
        String uniqueValue = String.valueOf(System.currentTimeMillis() + 1);
        HashMap<String, FieldValue> testNewRecord = createUpsertRecord();
        RecordUpdateKey updateKey = new RecordUpdateKey("title", uniqueValue);
        Object upsertRecordsResponse = this.passwordAuthRecordManagerment.upsertRecord(APP_ID, updateKey, testNewRecord, revision);
        this.recordsToDelete.add(((AddRecordResponse) upsertRecordsResponse).getID());
        assertThat(upsertRecordsResponse, instanceOf(AddRecordResponse.class));
    }

    @Test
    public void testUpsertRecordUpdate() throws KintoneAPIException {
        // Preprocessing
        String timeStamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, FieldValue> testUniqueRecord1 = createTestRecord( timeStamp );

        AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testUniqueRecord1);
        this.recordsToDelete.add(addResponse.getID());
        Integer revision = addResponse.getRevision();
        // Test 
        HashMap<String, FieldValue> testNewRecord = createUpsertRecord();
        RecordUpdateKey updateKey = new RecordUpdateKey("title", "Title" + timeStamp);
        Object upsertRecordsResponse = this.passwordAuthRecordManagerment.upsertRecord(APP_ID, updateKey, testNewRecord, revision);
        assertThat(upsertRecordsResponse, instanceOf(UpdateRecordResponse.class));
    }

    @After
    public void cleanData() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, this.recordsToDelete);
    }
}