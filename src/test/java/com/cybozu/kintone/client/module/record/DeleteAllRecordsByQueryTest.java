package com.cybozu.kintone.client.module.record;


import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
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

    @Test(expected = KintoneAPIException.class)
    public void deleteAllRecordsWithoutQuery() throws KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID, "");
    }

    @Test(expected = KintoneAPIException.class)
    public void deleteAllRecordsWithQuery() throws KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(APP_ID, "rb_status in (\"Todo\")");
    }

    @Test(expected = KintoneAPIException.class)
    public void deleteAllRecordsWithoutApp() throws KintoneAPIException {
        Record record = new Record(connection);
        record.deleteAllRecordsByQuery(null, "rb_status in (\"Todo\")");
    }

}
