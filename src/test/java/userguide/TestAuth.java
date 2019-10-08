package userguide;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.http.HTTPHeader;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestAuth {

    @Test
    public void testAuth() {
        Auth kintoneAuth = new Auth();
        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        kintoneAuth.setPasswordAuth(username, password);

        String apiToken = "YOUR_API_TOKEN";
        kintoneAuth.setApiToken(apiToken);

        kintoneAuth.setBasicAuth(username, password);

        for (HTTPHeader header : kintoneAuth.createHeaderCredentials()) {
            System.out.println("key: " + header.getKey());
            System.out.println("value: " + header.getValue());
        }
        // Expected Output:
        /*
        // For function setPasswordAuth
        key: X-Cybozu-Authorization
        value: WU9VUl9VU0VSTkFNRTpZT1VSX1BBU1NXT1JE

        // For function setApiToken
        key: X-Cybozu-API-Token
        value: YOUR_API_TOKEN

        // For function setBasicAuth
        key: Authorization
        value: Basic WU9VUl9VU0VSTkFNRTpZT1VSX1BBU1NXT1JE
        */
    }

    @Test
    public void testSetClientCert() {
        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        String certPassword = "YOUR_CERT_PASSWORD";
        String certPath = "YOUR_CERT_PATH";
        try {
            Auth certAuth = new Auth();
            certAuth.setPasswordAuth(username, password);
            certAuth.setClientCertByPath(certPath, certPassword);
        } catch (KintoneAPIException e) {
            e.printStackTrace();
        }
    }

}
