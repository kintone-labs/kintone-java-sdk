package com.cybozu.kintone.client.module.parser;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.cursor.GetRecordCursorResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CursorParserTest {
    private static final JsonParser jsonParser = new JsonParser();

    @BeforeClass
    public static void setup() {
    }

    private static String readInput(String file) {
        URL url = CursorParserTest.class.getResource(file);
        if (url == null) {
            return null;
        }

        String result = null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new FileReader(new File(url.getFile())));
            char[] buffer = new char[1024];
            int size = -1;
            while ((size = reader.read(buffer, 0, buffer.length)) >= 0) {
                sb.append(buffer, 0, size);
            }
            result = sb.toString();
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Test
    public void testParseRecordShouldSuccess() throws KintoneAPIException {
        String invalidValue = readInput("/cursor/ValidCursorRecord.txt");
        CursorParser parser = new CursorParser();
        JsonElement parse = jsonParser.parse(invalidValue);
        JsonElement jsonElement = parse.getAsJsonObject().get("record");
        HashMap<String, FieldValue> parseRecordsJson = parser.parseRecordJson(jsonElement);
        FieldValue fieldValue = parseRecordsJson.get("レコード番号");
        assertEquals("RECORD_NUMBER", fieldValue.getType().toString());
        assertEquals("1", fieldValue.getValue());
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseRecordShouldFailWithInvalidData() throws KintoneAPIException {

        String invalidValue = readInput("/cursor/InvalidCursorRecord.txt");
        CursorParser parser = new CursorParser();
        parser.parseRecordJson(jsonParser.parse(invalidValue));
    }

    @Test
    public void testParseRecordsShouldSuccess() throws KintoneAPIException {
        String invalidValue = readInput("/cursor/ValidCursorRecords.txt");
        CursorParser parser = new CursorParser();
        JsonElement parse = jsonParser.parse(invalidValue);
        JsonArray asJsonArray = parse.getAsJsonObject().get("records").getAsJsonArray();
        ArrayList<HashMap<String, FieldValue>> parseRecordsJson = parser.parseRecordsJson(asJsonArray);
        HashMap<String, FieldValue> hashMap = parseRecordsJson.get(0);
        FieldValue fieldValue = hashMap.get("レコード番号");
        assertEquals("RECORD_NUMBER", fieldValue.getType().toString());
        assertEquals("1", fieldValue.getValue());
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseRecordsShouldFailWithInvalidData() throws KintoneAPIException {
        String invalidValue = readInput("/cursor/InvalidCursorRecords.txt");
        CursorParser parser = new CursorParser();
        JsonElement parse = jsonParser.parse(invalidValue);
        JsonArray asJsonArray = parse.getAsJsonObject().get("records").getAsJsonArray();
        parser.parseRecordsJson(asJsonArray);
    }

    @Test
    public void testParseForGetRecordCursorResponseShouldSuccess() throws KintoneAPIException {
        String validValue = readInput("/cursor/ValidCursorGetRecord.txt");
        CursorParser parser = new CursorParser();
        JsonElement parse = jsonParser.parse(validValue);
        GetRecordCursorResponse parseForGetRecordCursorResponse = parser.parseForGetRecordCursorResponse(parse);
        assertEquals(false, parseForGetRecordCursorResponse.getNext());

        ArrayList<HashMap<String, FieldValue>> records = parseForGetRecordCursorResponse.getRecords();
        HashMap<String, FieldValue> hashMap = records.get(0);
        FieldValue fieldValue = hashMap.get("レコード番号");
        assertEquals("RECORD_NUMBER", fieldValue.getType().toString());
        assertEquals("1", fieldValue.getValue());
    }

    @Test(expected = KintoneAPIException.class)
    public void testParseForGetRecordCursorResponseShouldFailWithInvalidData() throws KintoneAPIException {
        String invalidValue = readInput("/cursor/InvalidCursorGetRecord.txt");
        CursorParser parser = new CursorParser();
        parser.parseForGetRecordCursorResponse(jsonParser.parse(invalidValue));
    }
}
