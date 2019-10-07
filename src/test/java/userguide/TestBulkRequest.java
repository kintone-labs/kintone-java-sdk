package userguide;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.module.record.Record;
import com.google.gson.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestBulkRequest {

    @Test
    public void getFieldModel() {
        String username = "cybozu";
        String password = "cybozu";
        String kintoneDomain = "https://test1-1.cybozu-dev.com";

        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        Integer appId = 22;
        // Init BulkRequest Module
        Record kintoneRecord = new Record(kintoneConnection);
//        BulkRequest kintoneBulkRequest = new BulkRequest(kintoneConnection);

//        fv.setType(FieldType.SINGLE_LINE_TEXT);
//        fv.setValue("FIELD_VALUE11111");
//        record.put("txt_taskTitle", fv);
//
//        RecordUpdateKey updateKey = new RecordUpdateKey("txt_projectTitle", "update 1234");
//        ArrayList<RecordsUpsertItem> recordsUpsertItems = new ArrayList<>();
//
//        recordsUpsertItems.add(new RecordsUpsertItem(updateKey, record));
//        kintoneRecord.upsertRecords(appId, recordsUpsertItems);

//        try {
//
//            HashMap<String, FieldValue> record = new HashMap<>();
//            FieldValue fv = new FieldValue();
//            fv.setType(FieldType.SINGLE_LINE_TEXT);
//            fv.setValue("FIELD_VALUE11111");
//            record.put("txt_taskTitle", fv);
//
//            FieldValue fv1 = new FieldValue();
//            fv1.setType(FieldType.SINGLE_LINE_TEXT);
//            fv1.setValue("FIELD_VALUE11111");
//            record.put("txt_projectTitle", fv);
//
//            HashMap<String, FieldValue> record1 = new HashMap<>();
//            FieldValue fv2 = new FieldValue();
//            fv2.setType(FieldType.SINGLE_LINE_TEXT);
//            fv2.setValue("FIELD_VALUE11111");
//            record1.put("txt_taskTitle", fv);

//            FieldValue fv3 = new FieldValue();
//            fv3.setType(FieldType.SINGLE_LINE_TEXT);
//            fv3.setValue("FIELD_VALUE11111");
//            record1.put("txt_projectTitle", fv);

//            ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
//            for (int i = 0; i < 110; i++) {
//                records.add(record);
//                if (i == 109)
//                    records.add(record1);
//            }
//            kintoneRecord.addAllRecords(appId, records);
//        } catch (KintoneAPIException e) {
//            System.out.println(e.toString());
//        }

    }

    @Test
    public void checkError() {
        String s = "[{\"httpErrorCode\":400,\"errorResponse\":{\"results\":[{},{\"code\":\"CB_VA01\",\"id\":\"tZiGKxdjUOqfYP4d718o\",\"message\":\"Missing or invalid input.\",\"errors\":{\"records[10].txt_projectTitle.value\":{\"messages\":[\"Required.\"]}}}]},\"detailMessage\":\"[{},{\\\"code\\\":\\\"CB_VA01\\\",\\\"id\\\":\\\"tZiGKxdjUOqfYP4d718o\\\",\\\"message\\\":\\\"Missing or invalid input.\\\",\\\"errors\\\":{\\\"records[10].txt_projectTitle.value\\\":{\\\"messages\\\":[\\\"Required.\\\"]}}}]\",\"stackTrace\":[],\"suppressedExceptions\":[]}]";
        JsonElement jsonElement = new JsonParser().parse(s);
        JsonArray jarray = jsonElement.getAsJsonArray();
        JsonObject jobject = jarray.get(0).getAsJsonObject();
        jobject = jobject.getAsJsonObject("errorResponse");
        String t = jobject.toString();
        System.out.println(t);

    }
}