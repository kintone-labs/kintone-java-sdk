package com.cybozu.kintone.client.module.record;


import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.BulksException;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import org.junit.Before;
import org.junit.Test;

public class DeleteAllRecordsByQueryTest {
    private int APP_ID;
    private String query = "xxx";
    private int INVALID_APP_ID;
    private Connection connection;

    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        connection = new Connection(TestConstantsSample.DOMAIN, auth);
        connection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT, TestConstantsSample.PROXY_USERNAME, TestConstantsSample.PROXY_PASSWORD);
    }

    @Test
    public void deleteAllRecordsWithoutQuery() throws BulksException, KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID);
    }

    @Test
    public void deleteAllRecordsWithQuery() throws BulksException, KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID, query);
    }

    @Test(expected = KintoneAPIException.class)
    public void deleteAllRecordsInvalidAppId() throws BulksException, KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(INVALID_APP_ID, query);
    }

}
