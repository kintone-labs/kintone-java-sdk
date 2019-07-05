package com.cybozu.kintone.client.connection;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.module.app.App;
import org.junit.Before;
import org.junit.Test;

public class ProxyTest {

    private int APP_ID = 2;
    private Auth auth;


    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        this.auth = auth;
    }

    @Test(expected = KintoneAPIException.class)
    public void checkAppWithWrongAccount() throws KintoneAPIException {
        Auth auth2 = new Auth();
        auth2.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection2 = new Connection(TestConstants.DOMAIN, auth2);
        connection2.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT, "hello", "bye");

        App app2 = new App(connection2);
        app2.getApp(APP_ID).getAppId();

    }


    @Test(expected = KintoneAPIException.class)
    public void checkAppWithEmptyAccount() throws KintoneAPIException {
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT, "", "");

        App app = new App(connection);
        app.getApp(APP_ID).getAppId();

    }

    @Test(expected = KintoneAPIException.class)
    public void checkAppWithoutAccount() throws KintoneAPIException {
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        App app = new App(connection);
        app.getApp(APP_ID).getAppId();
       }

    @Test(expected = KintoneAPIException.class)
    public void checkAppWithHttpServer() throws KintoneAPIException {
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST_HTTP, TestConstants.PROXY_PORT_HTTP, TestConstants.PROXY_USERNAME, TestConstants.PROXY_PASSWORD);
        App app = new App(connection);

        app.getApp(APP_ID).getAppId();
    }

//    @Test
//    public void checkAppWithRightAccount() throws KintoneAPIException {
//        Connection connection = new Connection(TestConstants.DOMAIN, auth);
//        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT, "qasd", "Cybozu@123");
//        App app = new App(connection);
//
//        Assert.assertNotNull(app.getApp(APP_ID).getAppId());
//    }

}
