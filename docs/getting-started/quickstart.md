# Quickstart

## Requirement

* [Java SE](https://adoptopenjdk.net/) (Version 9 or later)
* [maven](https://maven.apache.org/download.cgi) (Version 3.5.4 or later)
* [kintone-java-sdk](https://github.com/kintone/kintone-java-sdk)

## Code example

<details class="tab-container" open>
<Summary>Get all records by query sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.module.record.Record;

public class QuickStart {
    public static void main(String[] args) {
        // Init authenticationAuth
        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";

        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init Record Module
        Record kintoneRecordManager = new Record(kintoneConnection);

        // execute GET RECORD API
        int appID = 0; // Input app id
        String query = "YOUR_QUERY";
        try {
            GetRecordsResponse response = kintoneRecordManager.getAllRecordsByQuery(appID, query);
            System.out.println("Record: size records " + response.getRecords().size());
            System.out.println("List Record Ids:");
            for (int i = 0; i < response.getRecords().size(); i++) {
                System.out.print(response.getRecords().get(i).get("$id").getValue() + ", ");
            }

            /*
             * Expected Output:
             *  Record: size records {SIZE_OF_RECORDS}
             *  List Record Ids:
             *  {RECORD_ID_1, RECORD_ID_2,...}
             */
        } catch (KintoneAPIException e) {
            System.out.println("Error: " + e.getMessage());

            /*
             * Expected Output:
             *  Error: {ERROR_MESSAGES}
             */
        }
    }
}
</pre>

</details>

<details class="tab-container" open>
<Summary>Get list views of app sample</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.basic.response.GetViewsResponse;
import com.cybozu.kintone.client.module.app.App;

public class QuickStart {
    public static void main(String[] args) {
        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);
        int appID = 0; // Input app id

        try {
            // Init App Module
            App kintoneApp = new App(kintoneConnection);

            boolean isPreview = true;
            LanguageSetting languageSetting = LanguageSetting.EN; // LanguageSetting( EN | JA | ZH ). Ex: LanguageSetting.JA

            GetViewsResponse listViews = kintoneApp.getViews(appID, languageSetting, isPreview);
            System.out.println("App List Views: " + listViews.getViews());

            /*
             * Expected Output:
             *  App Name: {APP_NAME}
             *  App List Views: {LIST_VIEWS_OF_APP}
             */
        } catch (KintoneAPIException e) {
            System.out.println("Error: " + e.getMessage());

            /*
             * Expected Output:
             *  Error: {ERROR_MESSAGES}
             */
        }
    }
}

</pre>
</details>
