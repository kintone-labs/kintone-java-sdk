package com.cybozu.kintone.client.module.record;


import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.BulksException;
import org.junit.Before;
import org.junit.Test;

public class DeleteAllRecordsByQueryTest {
    private int APP_ID = 2;
    private Connection connection;

    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT, TestConstants.PROXY_USERNAME, TestConstants.PROXY_PASSWORD);
    }

    @Test
    public void deleteAllRecordsWithoutQuery() throws BulksException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID, "");
    }

    @Test
    public void deleteAllRecordsWithQuery() throws BulksException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID, "rb_status in (\"Done\")");
    }

    @Test(expected = BulksException.class)
    public void deleteAllRecordsWithoutApp() throws BulksException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(null, "rb_status in (\"Todo\")");
    }

}
