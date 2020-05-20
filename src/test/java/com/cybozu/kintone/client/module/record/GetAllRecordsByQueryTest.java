package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;

public class GetAllRecordsByQueryTest {
    private static Integer APP_ID = 1;
    private static Integer NUMBER_OF_RECORD = 10;
    private Record passwordAuthRecordManagerment;

    private ArrayList<Integer> recordsToDelete = new ArrayList<Integer>();

    public HashMap<String, FieldValue> addField(HashMap<String, FieldValue> record, String code, FieldType type,
            Object value) {
        FieldValue newField = new FieldValue();
        newField.setType(type);
        newField.setValue(value);
        record.put(code, newField);
        return record;
    }

    public HashMap<String, FieldValue> createTestRecord(String suffix) {
        HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

        testRecord = addField(testRecord, "title", FieldType.SINGLE_LINE_TEXT, "Title " + suffix);
        testRecord = addField(testRecord, "description", FieldType.SINGLE_LINE_TEXT, "Description " + suffix);
        
        return testRecord;
    }

    @Before
    public void setup() throws KintoneAPIException {
        Auth passwordAuth = new Auth();
        passwordAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, passwordAuth);
        this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);

        ArrayList<HashMap<String, FieldValue>> recordsToInsert = new ArrayList<HashMap<String, FieldValue>>();
        AddRecordsResponse responseBlock = new AddRecordsResponse();

        for (int i = 0; i < NUMBER_OF_RECORD; i++) {
            recordsToInsert.add(this.createTestRecord(String.valueOf(i)));
            if (recordsToInsert.size() == 100) {
                responseBlock = this.passwordAuthRecordManagerment.addRecords(APP_ID, recordsToInsert);
                this.recordsToDelete.addAll(responseBlock.getIDs());
                recordsToInsert = new ArrayList<HashMap<String, FieldValue>>();
            }
        }
        if (recordsToInsert.size() > 0) {
            responseBlock = this.passwordAuthRecordManagerment.addRecords(APP_ID, recordsToInsert);
            this.recordsToDelete.addAll(responseBlock.getIDs());
        }
    }

    @Test
    public void getAllRecordsWithTotalCountSuccess() throws KintoneAPIException {
        GetRecordsResponse getAllRecords = this.passwordAuthRecordManagerment.getAllRecordsByQuery(APP_ID, "", new ArrayList<String>(), true);
        assertEquals(NUMBER_OF_RECORD, getAllRecords.getTotalCount());
    }

    @Test
    public void getAllRecordsSuccess() throws KintoneAPIException {
        GetRecordsResponse getAllRecords = this.passwordAuthRecordManagerment.getAllRecordsByQuery(APP_ID, "", new ArrayList<String>(), false);
        assertEquals(NUMBER_OF_RECORD.intValue(), getAllRecords.getRecords().size());
    }

    @Test(expected = KintoneAPIException.class)
    public void getAllRecordsFailWrongAppID() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.getAllRecordsByQuery(-1, "", new ArrayList<String>(), true);
    }

    @After
    public void cleanData() throws KintoneAPIException {
        this.passwordAuthRecordManagerment.deleteRecords(APP_ID, this.recordsToDelete);
    }
}