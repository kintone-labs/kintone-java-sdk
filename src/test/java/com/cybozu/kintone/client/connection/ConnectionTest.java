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
    private static String USERNAME = "xxxxx";
    private static String PASSWORD = "xxxxx";
    private static int APP_ID = 0;
    private static int GUEST_SPACE_APP_ID = 0;
    private static String UPLOAD_PATH = "xxxxx";
    private static String DOWNLOAD_PATH = "xxxxx";

    private static String VIEW_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static String POST_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    @Test
    public void testGetRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }
    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongDomain() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongUsername() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongPassword() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }

    @Test
    public void testGetRequestWithTokenAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithTokenAuthenticationShouldFail() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test
    public void testGetRequestWithPassAuthenticationShouldSuccessWhenTokenAuthenticationNotAllow() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(POST_API_TOKEN);
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
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
        auth.setApiToken(VIEW_API_TOKEN);
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test
    public void testGetRequestWithBasicAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        auth.setBasicAuth("", "");
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", 4);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithPasswordAuthenticationShouldFailWithBasicAuthenticationSite() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", 4);
        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Ignore
    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccessWhenGivenBasicAuthentication() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        auth.setBasicAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", APP_ID);
        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
    }

    @Test
    public void testPostRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testPostRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, "");
    }

    @Test
    public void testPutRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject body = new JsonObject();
        body.addProperty("app", APP_ID);
        body.addProperty("id", 2);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("文字列__1行", textField);

        body.add("record", recordElement);

        JsonElement result = connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testPutRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, "");
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, "");
    }

    @Test
    public void testDeleteRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();

        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }

    @Test
    public void testGetRequestInGuestSpaceShouldSuccessWithPasswordAuthentication() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", GUEST_SPACE_APP_ID);

        JsonElement result = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("appId"));
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestInGuestSpaceShouldFailWhenGivenInvalidSpaceId() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, 1);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonObject body = new JsonObject();
        body.addProperty("id", GUEST_SPACE_APP_ID);

        connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, body.toString());
    }

    @Test
    public void testPostRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject body = new JsonObject();
        body.addProperty("app", GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test
    public void testPutRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject body = new JsonObject();
        body.addProperty("app", GUEST_SPACE_APP_ID);
        body.addProperty("id", 2);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        JsonObject recordElement = new JsonObject();
        recordElement.add("text", textField);

        body.add("record", recordElement);

        JsonElement result = connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD, body.toString());
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("revision"));
    }

    @Test
    public void testDeleteRequestInGuestSpaceShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();

        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", GUEST_SPACE_APP_ID);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD, postBody.toString());
        assertNotNull(postResult);
        assertTrue(postResult.getAsJsonObject().has("revision"));

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", GUEST_SPACE_APP_ID);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }

    @Test
    public void testUploadFileShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        JsonElement result = connection.uploadFile(UPLOAD_PATH + "test.txt");
        assertNotNull(result);
        assertTrue(result.getAsJsonObject().has("fileKey"));
    }

  @Test
  public void testDownloadFileShouldSuccess() throws KintoneAPIException {
      Auth auth = new Auth();
      auth.setPasswordAuth(USERNAME, PASSWORD);
      Connection connection = new Connection(TestConstants.DOMAIN, auth);
      connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
      String fileKey = "xxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      connection.downloadFile(requestBody,DOWNLOAD_PATH + "1/test.xlsx");
  }

  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenNoFileName() throws KintoneAPIException {
      Auth auth = new Auth();
      auth.setPasswordAuth(USERNAME, PASSWORD);
      Connection connection = new Connection(TestConstants.DOMAIN, auth);
      connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
      String fileKey = "xxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      connection.downloadFile(requestBody,DOWNLOAD_PATH + "1");
  }

  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenUnexistedKey() throws KintoneAPIException {
      Auth auth = new Auth();
      auth.setPasswordAuth(USERNAME, PASSWORD);
      Connection connection = new Connection(TestConstants.DOMAIN, auth);
      connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
      String fileKey = "xxxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      connection.downloadFile(requestBody,DOWNLOAD_PATH + "1/test.xlsx");
  }

  @Test(expected = KintoneAPIException.class)
  public void testDownloadFileShouldFailWhenHasNoPermission() throws KintoneAPIException {
      Auth auth = new Auth();
      auth.setPasswordAuth(USERNAME, PASSWORD);
      Connection connection = new Connection(TestConstants.DOMAIN, auth);
      connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
      String fileKey = "xxxxxxx";
      DownloadRequest request = new DownloadRequest(fileKey);
      FileParser parser = new FileParser();
      String requestBody = parser.parseObject(request);
      connection.downloadFile(requestBody,DOWNLOAD_PATH + "1/test.xlsx");
  }

    @Test
    public void testGetPathURIWithBaseURLShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
        String pathURI = connection.getPathURI(ConnectionConstants.APP_CUSTOMIZE_PREVIEW);
        assertEquals("/k/v1/preview/app/customize.json", pathURI);
    }

    @Test
    public void testGetPathURIWhenGuestSpaceIdBiggerThanzeroShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth,163);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        String pathURI = connection.getPathURI(ConnectionConstants.APP_DEPLOY_PREVIEW);
        assertEquals("/k/guest/163/v1/preview/app/deploy.json", pathURI);
    }

    @Test
    public void testGetPathURIWhenGuestSpaceIdSmallerThanzeroShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth,-1);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        String pathURI = connection.getPathURI(ConnectionConstants.APP_DEPLOY_PREVIEW);
        assertEquals("/k/v1/preview/app/deploy.json", pathURI);

    }
}
