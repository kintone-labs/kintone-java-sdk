package com.cybozu.kintone.client.connection;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.module.app.App;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ProxyTest {

    private int APP_ID = 2;
    private Auth auth;

    public static void clearAuthCache() {
        try {
            // this is evil, but there is no official way to clear the authentication cache...
            // use of Reflection API so no sun classes are imported
            Class<?> authCacheValueClass = Class.forName("sun.net.www.protocol.http.AuthCacheValue");
            Class<?> authCacheClass = Class.forName("sun.net.www.protocol.http.AuthCache");
            Class<?> authCacheImplClass = Class.forName("sun.net.www.protocol.http.AuthCacheImpl");
            Constructor<?> authCacheImplConstructor = authCacheImplClass.getConstructor();
            Method setAuthCacheMethod = authCacheValueClass.getMethod("setAuthCache", authCacheClass);
            setAuthCacheMethod.invoke(null, authCacheImplConstructor.newInstance());
        } catch (Throwable t) {
        }
    }

    @Before
    public void setup() {
        clearAuthCache();
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        this.auth = auth;
    }

    @Test(expected = KintoneAPIException.class)
    public void checkAppWithWrongAccount() throws KintoneAPIException {
        Connection connection2 = new Connection(TestConstants.DOMAIN, auth);
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

    @Test
    public void checkAppWithRightAccount() throws KintoneAPIException {
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT, TestConstants.PROXY_USERNAME, TestConstants.PROXY_PASSWORD);
        App app = new App(connection);

        Assert.assertNotNull(app.getApp(APP_ID).getAppId());
    }

}
