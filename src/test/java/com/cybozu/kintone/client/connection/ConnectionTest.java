/**
 * Copyright 2017 Cybozu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cybozu.kintone.client.connection;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.file.DownloadRequest;
import com.cybozu.kintone.client.module.parser.FileParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConnectionTest {
    private static int APP_ID = 4;
    private static String API_TOKEN = "QqIuBdBDf0UtGonvajiQXfwJj3cXQw68Dbu66ppt";
    
	private Connection passwordConnection;
	private Connection certConnection;
	private Connection guestSpaceConnection;
	private Connection guestSpaceCertConnection;
	private Connection tokenAuthConnection;

    @Before
    public void setup() throws KintoneAPIException {
	
    Auth auth = new Auth();
    auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    this.passwordConnection = new Connection(TestConstants.DOMAIN, auth);
    this.passwordConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    
    Auth tokenAuth = new Auth();
    tokenAuth.setApiToken(API_TOKEN);
    this.tokenAuthConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
    this.tokenAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
	Auth certAuth = new Auth();
	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
	this.certConnection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
	this.certConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
	
    Auth guestSpaceAuth = new Auth();
    guestSpaceAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    this.guestSpaceConnection = new Connection(TestConstants.DOMAIN, guestSpaceAuth, TestConstants.GUEST_SPACE_ID);
    this.guestSpaceConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    
    Auth guestSpaceCertAuth = new Auth();
    guestSpaceCertAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    guestSpaceCertAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    this.guestSpaceCertConnection = new Connection(TestConstants.DOMAIN, guestSpaceCertAuth, TestConstants.GUEST_SPACE_ID);
    this.guestSpaceCertConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
}
    @Test
    public void testGetRequestShouldSuccess() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = this.passwordConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
   }
 
    @Test
    public void testGetRequestShouldSuccessWithTokenAuthentication() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = this.tokenAuthConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test
    public void testGetRequestShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = this.certConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test
    public void testGetRequestShouldSuccessInGuestSpace() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);
        JsonElement result = this.guestSpaceConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
   }
    
    @Test
    public void testGetRequestShouldSuccessInGuestSpaceWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);
        JsonElement result = this.guestSpaceCertConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
   }
    
    @Test
    public void testGetRequestSetAuthShouldSuccess() throws KintoneAPIException {	
	    Auth auth = new Auth();
	    auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
	    Connection connection = this.passwordConnection.setAuth(auth);
	    connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test
    public void testGetRequestSetAuthShouldSuccessWithClientCert() throws KintoneAPIException {
		Auth certAuth = new Auth();
		certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
		certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
		Connection connection = this.certConnection.setAuth(certAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    //KINTONE-13919
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testDownloadShouldFailWhenWrongUserWithClientCert() throws KintoneAPIException {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection= new Connection(TestConstants.SECURE_DOMAIN, certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        String fileKey = "20180806013856AF9D0EFCA84B40CEAAB5D9882D9E4700212";
        DownloadRequest request = new DownloadRequest(fileKey);
        FileParser parser = new FileParser();
        String requestBody = parser.parseObject(request);
        connection.downloadFile(requestBody);
    }
    
    //KINTONE-13919
    @Ignore   
    @Test(expected = KintoneAPIException.class)
    public void testUploadShouldFailWhenWrongUserWithClientCert() throws Exception {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection= new Connection(TestConstants.SECURE_DOMAIN, certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        File uploadFile = new File(TestConstants.UPLOAD_PATH + "test.txt");
        this.certConnection.uploadFile(uploadFile.getName(), new FileInputStream(uploadFile.getAbsolutePath()));
    }
    
    //KINTONE-13919
    @Ignore   
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithWrongUserClientCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithOtherUserPasswordCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, "123");
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithOtherUserUsernameCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("yfang1", TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithExpiredClientCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("user2", "user2");
    	certAuth.setClientCertByPath(TestConstants.EXPIRED_CLIENT_CERT_PATH, TestConstants.EXPIRED_CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithExpiredClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("user2", "user2");
    	InputStream cert = new FileInputStream(TestConstants.EXPIRED_CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.EXPIRED_CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test
    public void testGetRequestShouldSuccessWithInputStreamCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamExpiredClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("user2", "user2");
    	InputStream cert = new FileInputStream(TestConstants.EXPIRED_CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.EXPIRED_CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWrongPasswordCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, "123");
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    //KINTONE-13919
    @Ignore   
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithOtherUserClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithWrongLoginNameClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("abc", TestConstants.PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithWrongLoginPasswordClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, "abc");
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithOtherUserPasswordClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.ADMIN_PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithInputStreamWithOtherUserNameClientCert() throws KintoneAPIException, FileNotFoundException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.PASSWORD);
    	InputStream cert = new FileInputStream(TestConstants.CLIENT_CERT_PATH);
    	certAuth.setClientCert(cert, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithSecureLinkWithoutCert() throws KintoneAPIException {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection= new Connection(TestConstants.SECURE_DOMAIN, certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongDomain() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.WRONG_DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongDomainWithClientCert() throws KintoneAPIException {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection= new Connection(TestConstants.WRONG_DOMAIN, certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestInGuestSpaceShouldFailWhenGivenWrongDomain() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.WRONG_DOMAIN, auth,TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestInGuestSpaceShouldFailWhenGivenWrongDomainWithClientCert() throws KintoneAPIException {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.WRONG_DOMAIN, certAuth,TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongUsername() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dddd", TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongUsernameWithClientCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth("dddd",TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestsetAuthShouldFailWhenGivenWrongUsername() throws KintoneAPIException {
	    Auth auth = new Auth();
	    auth.setPasswordAuth("dddd", TestConstants.PASSWORD);
	    Connection connection= this.passwordConnection.setAuth(auth);
	    connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
	    
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestsetAuthShouldFailWhenGivenWrongUsernameWithClientCert() throws KintoneAPIException {
		Auth certAuth = new Auth();
		certAuth.setPasswordAuth("dddd", TestConstants.PASSWORD);
		certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
		Connection connection = this.certConnection.setAuth(certAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongPassword() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, "dddd");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongPasswordWithClientCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, "dddd");
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestSetAuthShouldFailWhenGivenWrongPassword() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, "dddd");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestSetAuthShouldFailWhenGivenWrongPasswordWithClientCert() throws KintoneAPIException {
		Auth certAuth = new Auth();
		certAuth.setPasswordAuth(TestConstants.USERNAME, "dddd");
		certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
		Connection connection = this.certConnection.setAuth(certAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongSpaceId() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", 10000);
        this.passwordConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongSpaceIdWithClientCert() throws KintoneAPIException {
        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection= new Connection(TestConstants.SECURE_DOMAIN,certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", 10000);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithWrongTokenAuthentication() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithWrongClientCertPathCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.WRONG_CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWithWrongClientPasswordCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, "111");
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test
    public void testGetRequestShouldSuccessWhenTokenAuthenticationNotAllowWithPassAuthentication() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(API_TOKEN);
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST,  ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test
    public void testGetRequestShouldSuccessWhenTokenAuthenticationNotAllowWithClientCert() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setApiToken(API_TOKEN);
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST,  ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithInvalidPassAuthenticationShouldFailWhenTokenAuthenticationAllow() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(API_TOKEN);
        auth.setPasswordAuth("ABC", TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithInvalidClientCertShouldFailWhenTokenAuthenticationAllow() throws KintoneAPIException {
    	Auth certAuth = new Auth();
    	certAuth.setApiToken(API_TOKEN);
    	certAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
    	certAuth.setClientCertByPath(TestConstants.WRONG_CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
    	Connection connection = new Connection(TestConstants.SECURE_DOMAIN, certAuth);
    	connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
    	
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
   
    @Test
    public void testGetRequestShouldSuccessWithBasicAuthentication() throws KintoneAPIException {
        
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
        auth.setBasicAuth(TestConstants.BASIC_USERNAME, TestConstants.BASIC_PASSWORD);
        Connection connection = new Connection(TestConstants.BASIC_DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", 1);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithPasswordAuthenticationShouldFailWithBasicAuthenticationSite() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
        Connection connection = new Connection(TestConstants.BASIC_DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", 1);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithClientCertShouldFailWithBasicAuthenticationSite() throws KintoneAPIException {
		Auth certAuth = new Auth();
		certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
		certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection = new Connection(TestConstants.BASIC_DOMAIN, certAuth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        
        JsonObject body = new JsonObject();
        body.addProperty("id", 1);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test
    public void testGetRequestShouldSuccessWhenGivenBasicAuthenticationWithClientCert() throws KintoneAPIException {
		Auth certAuth = new Auth();
		certAuth.setPasswordAuth(TestConstants.ADMIN_USERNAME, TestConstants.ADMIN_PASSWORD);
		certAuth.setBasicAuth(TestConstants.BASIC_USERNAME, TestConstants.BASIC_PASSWORD);
		certAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
		Connection connection = new Connection(TestConstants.BASIC_DOMAIN, certAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		
        JsonObject body = new JsonObject();
        body.addProperty("id",1);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
    }
    
    @Test
    public void testPostRequestShouldSuccess() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = this.passwordConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }
    
    @Test
    public void testPostRequestShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = this.certConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testPostRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        this.passwordConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, "");
    }

    @Test(expected = KintoneAPIException.class)
    public void testPostRequestShouldFailWhenGivenWrongBodyWithClientCert() throws KintoneAPIException {
    	 this.certConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, "");
    }
    
    @Test
    public void testPutRequestShouldSuccess() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);
        body.addProperty("id", 2);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("文字列__1行", textField);

        body.add("record", recordElement);

        JsonElement result = this.passwordConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }
    
    @Test
    public void testPutRequestShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);
        body.addProperty("id", 2);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("文字列__1行", textField);

        body.add("record", recordElement);

        JsonElement result = this.certConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testPutRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        this.passwordConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, "");
    }

    @Test(expected = KintoneAPIException.class)
    public void testPutRequestShouldFailWhenGivenWrongBodyWithClientCert() throws KintoneAPIException {
        this.certConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, "");
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        this.passwordConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, "");
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testDeleteRequestShouldFailWhenGivenWrongBodyWithClientCert() throws KintoneAPIException {
        this.certConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, "");
    }

    @Test
    public void testDeleteRequestShouldSuccess() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = this.passwordConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = this.passwordConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }
    
    @Test
    public void testDeleteRequestShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = this.certConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = this.certConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }

    @Test
    public void testGetRequestInGuestSpaceShouldSuccessWithPasswordAuthentication() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);

        JsonElement result = this.guestSpaceConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }

    @Test
    public void testGetRequestInGuestSpaceShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("id", TestConstants.GUEST_SPACE_APP_ID);

        JsonElement result = this.guestSpaceCertConnection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestInGuestSpaceShouldFailWhenGivenInvalidSpaceId() throws KintoneAPIException {
        Auth guestSpaceAuth = new Auth();
        guestSpaceAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection= new Connection(TestConstants.DOMAIN, guestSpaceAuth, 10000);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id",TestConstants.GUEST_SPACE_APP_ID);

        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestInGuestSpaceShouldFailWhenGivenInvalidSpaceIdWithClientCert() throws KintoneAPIException {
        Auth guestSpaceCertAuth = new Auth();
        guestSpaceCertAuth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        guestSpaceCertAuth.setClientCertByPath(TestConstants.CLIENT_CERT_PATH, TestConstants.CLIENT_CERT_PASSWORD);
        Connection connection= new Connection(TestConstants.SECURE_DOMAIN, guestSpaceCertAuth, 10000);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id",TestConstants.GUEST_SPACE_APP_ID);

        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }
    
    @Test
    public void testPostRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", TestConstants.GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = this.guestSpaceConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }
    
    @Test
    public void testPostRequestInGuestSpaceShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app", TestConstants.GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = this.guestSpaceCertConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test
    public void testPutRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app",TestConstants.GUEST_SPACE_APP_ID);
        body.addProperty("id", 806);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("text", textField);

        body.add("record", recordElement);

        JsonElement result = this.guestSpaceConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test
    public void testPutRequestInGuestSpaceShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject body = new JsonObject();
        body.addProperty("app",TestConstants.GUEST_SPACE_APP_ID);
        body.addProperty("id", 826);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("text", textField);

        body.add("record", recordElement);

        JsonElement result = this.guestSpaceCertConnection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test
    public void testDeleteRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app",TestConstants.GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);
        
       
        JsonElement postResult = this.guestSpaceConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
       
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
             String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", TestConstants.GUEST_SPACE_APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = this.guestSpaceConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }

    @Test
    public void testDeleteRequestInGuestSpaceShouldSuccessWithClientCert() throws KintoneAPIException {
        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", TestConstants.GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = this.guestSpaceCertConnection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", TestConstants.GUEST_SPACE_APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = this.guestSpaceCertConnection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }
    
    @Test
    public void testUploadFileShouldSuccess() throws KintoneAPIException {

        File uploadFile = new File(TestConstants.UPLOAD_PATH + "test.txt");

        JsonElement result = null;
		try {
			result = this.passwordConnection.uploadFile(uploadFile.getName(), new FileInputStream(uploadFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("fileKey"));
    }

    @Test
    public void testUploadFileShouldSuccessWithTokenAuthentication() throws KintoneAPIException {
   
    	File uploadFile = new File(TestConstants.UPLOAD_PATH + "test.txt");

        JsonElement result = null;
		try {
			result = this.tokenAuthConnection.uploadFile(uploadFile.getName(), new FileInputStream(uploadFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("fileKey"));
    }
    
    @Test
    public void testUploadFileShouldSuccessWithClientCert() throws KintoneAPIException {
    	
        File uploadFile = new File(TestConstants.UPLOAD_PATH + "test.txt");

        JsonElement result = null;
		try {
			result = this.certConnection.uploadFile(uploadFile.getName(), new FileInputStream(uploadFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("fileKey"));
    }
    
    @Test
    public void testDownloadFileShouldSuccess() throws KintoneAPIException {
        String fileKey = "20180806013856AF9D0EFCA84B40CEAAB5D9882D9E4700212";
    	DownloadRequest request = new DownloadRequest(fileKey);
    	FileParser parser = new FileParser();
    	String requestBody = parser.parseObject(request);
    	InputStream is = this.passwordConnection.downloadFile(requestBody);
    	assertNotNull(is);
    }
      
    @Test
    public void testDownloadFileShouldSuccessWithTokenAuthentication() throws KintoneAPIException {
        String fileKey = "201901020944324CBED31FBAB64CFAB2C9240F0AA2671C042";
    	DownloadRequest request = new DownloadRequest(fileKey);
    	FileParser parser = new FileParser();
    	String requestBody = parser.parseObject(request);
    	InputStream is = this.tokenAuthConnection.downloadFile(requestBody);
    	assertNotNull(is);
    }
    
    @Test
    public void testDownloadFileShouldSuccessWithClientCert() throws KintoneAPIException {
        String fileKey = "20180806013856AF9D0EFCA84B40CEAAB5D9882D9E4700212";
    	DownloadRequest request = new DownloadRequest(fileKey);
    	FileParser parser = new FileParser();
    	String requestBody = parser.parseObject(request);
    	InputStream is = this.certConnection.downloadFile(requestBody);
    	assertNotNull(is);
    }
    
  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenUnexistedKey() throws KintoneAPIException {
      String fileKey = "xxxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      InputStream is = this.passwordConnection.downloadFile(requestBody);
      assertNotNull(is);
  }
  
  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenUnexistedKeyWithClientCert() throws KintoneAPIException {
      String fileKey = "xxxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      InputStream is = this.certConnection.downloadFile(requestBody);
      assertNotNull(is);
  }

  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenHasNoPermission() throws KintoneAPIException {
      String fileKey = "20181227070638A7C7CDD3F2A94CAB85FD2CFC8F3497E8158";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      InputStream is = this.passwordConnection.downloadFile(requestBody);
      assertNotNull(is);
  }

  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenHasNoPermissionWithClientCert() throws KintoneAPIException {
      String fileKey = "20181227070638A7C7CDD3F2A94CAB85FD2CFC8F3497E8158";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      InputStream is = this.certConnection.downloadFile(requestBody);
      assertNotNull(is);
  }
  
    @Test
    public void testGetPathURIWithBaseURLShouldSuccess() throws KintoneAPIException {
        String pathURI = this.passwordConnection.getPathURI(ConnectionConstants.APP_CUSTOMIZE_PREVIEW);
        assertEquals("/k/v1/preview/app/customize.json", pathURI);
    }
    
    @Test
    public void testGetPathURIWhenGuestSpaceIdBiggerThanZeroShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth,163);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        String pathURI = connection.getPathURI(ConnectionConstants.APP_DEPLOY_PREVIEW);
        assertEquals("/k/guest/163/v1/preview/app/deploy.json", pathURI);
    }
    
    @Test
    public void testGetPathURIWhenGuestSpaceIdSmallerThanZeroShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstants.USERNAME, TestConstants.PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth,-1);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        String pathURI = connection.getPathURI(ConnectionConstants.APP_DEPLOY_PREVIEW);
        assertEquals("/k/v1/preview/app/deploy.json", pathURI);
    }
}
