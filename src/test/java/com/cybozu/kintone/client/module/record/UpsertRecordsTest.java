package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.model.record.FieldValue;
import com.cybozu.kintone.client.model.record.record.response.GetRecordsResponse;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.RecordsUpsertItem;

public class UpsertRecordsTest {
    private static Integer APP_ID = 3;
    private static Integer RECORDS_DATA_LENGTH = 20;

    private Record passwordAuthRecordManagerment;

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);
    }

    public HashMap<String, FieldValue> createTestRecord(String value) {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "title", FieldType.SINGLE_LINE_TEXT, value);
        
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
    public void testUpsertRecordsAllInsert() throws KintoneAPIException {
        ArrayList<RecordsUpsertItem> upsertRecords = new ArrayList<RecordsUpsertItem>();

        // Build data
        for (int i = 0; i < RECORDS_DATA_LENGTH; i++) {
            RecordUpdateKey updateKey = new RecordUpdateKey("title", "update " + i);
            HashMap<String, FieldValue> record = this.createTestRecord("update " + i);

            HashMap<String, FieldValue> recordForPost = this.createTestRecord("add " + i);

            upsertRecords.add(new RecordsUpsertItem(updateKey, record));
            upsertRecords.add(new RecordsUpsertItem(recordForPost));
        }
    
        this.passwordAuthRecordManagerment.upsertRecords(APP_ID, upsertRecords);
        GetRecordsResponse allRecords = this.passwordAuthRecordManagerment.getAllRecordsByQuery(APP_ID, "", new ArrayList<String>(), true);

        assertEquals(allRecords.getTotalCount().intValue(), upsertRecords.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpsertRecordsFail() throws KintoneAPIException {
        ArrayList<RecordsUpsertItem> upsertRecords = new ArrayList<RecordsUpsertItem>();
        for (int i = 0; i < RECORDS_DATA_LENGTH + 1; i++) {
            RecordUpdateKey updateKey = new RecordUpdateKey("title", "update " + i);
            HashMap<String, FieldValue> record = this.createTestRecord("update " + i);
            upsertRecords.add(new RecordsUpsertItem(updateKey, record));
        }

        this.passwordAuthRecordManagerment.upsertRecords(-1, upsertRecords);
    }

    @After
    public void cleanData() throws KintoneAPIException {
        
    }
}