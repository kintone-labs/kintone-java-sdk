package com.cybozu.kintone.client.module.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.cursor.GetRecordCursorResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CursorParser extends Parser{
	private static final RecordParser parser = new RecordParser();
	
	public HashMap<String, FieldValue> parseRecordJson(JsonElement jsonElement) throws KintoneAPIException {
        try {
        	HashMap<String, FieldValue> record = new HashMap<>();
            JsonObject recordJson = jsonElement.getAsJsonObject();
            for (Entry<String, JsonElement> entry : recordJson.entrySet()) {
                String fieldType = entry.getValue().getAsJsonObject().get("type").getAsString();
                JsonElement fieldValue = entry.getValue().getAsJsonObject().get("value");
                FieldValue field = parser.parseField(fieldType, fieldValue);
                record.put(entry.getKey(), field);
            }
            return record;
            
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }
	
	public ArrayList<HashMap<String, FieldValue>> parseRecordsJson(JsonArray jsonArray) throws KintoneAPIException {
        try {
        	ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
            for (JsonElement jsonElement : jsonArray) {
                HashMap<String, FieldValue> record = this.parseRecordJson(jsonElement);
                records.add(record);
            }
            return records;
            
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }
	
	public GetRecordCursorResponse parseForGetRecordCursorResponse(JsonElement jsonElement) throws KintoneAPIException {
        try {
        	JsonArray recordsJson = jsonElement.getAsJsonObject().getAsJsonArray("records");
            JsonElement next = jsonElement.getAsJsonObject().get("next");
            
            GetRecordCursorResponse getRecordCursorResponse = new GetRecordCursorResponse();
            getRecordCursorResponse.setRecords(this.parseRecordsJson(recordsJson));
            getRecordCursorResponse.setNext(next.getAsBoolean());
            return getRecordCursorResponse;
            
        } catch (Exception e) {
            throw new KintoneAPIException("Parse error");
        }
    }
}
