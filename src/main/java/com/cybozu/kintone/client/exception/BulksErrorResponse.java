package com.cybozu.kintone.client.exception;

import com.google.gson.*;

import java.util.ArrayList;

public class BulksErrorResponse {
    private ArrayList<Object> results;

    public BulksErrorResponse(ArrayList<Object> listErrors) {
        setResults(listErrors);
    }

    public ArrayList<Object> getResults() {
        return results;
    }

    public void setResults(ArrayList<Object> resultsErrors) {
        String jsonStr = parseKintoneAPIException(resultsErrors);
        JsonElement jsonElement = new JsonParser().parse(jsonStr);
        JsonObject jsonObject1 = jsonElement.getAsJsonObject();
        JsonArray jarray = jsonObject1.getAsJsonArray("results");

        ArrayList<Object> results = new ArrayList<>();
        if (jarray != null) {
            for (JsonElement elementResponse : jarray) {
                if (elementResponse.getAsJsonObject().entrySet().isEmpty()) {
                    results.add(null);
                } else if (elementResponse.getAsJsonObject().get("code") != null) {
                    results.add(new Gson().fromJson(elementResponse, ErrorResponse.class));
                } else {
                    results.add(null);
                }
            }
        }
        this.results = results;
    }

    public String parseKintoneAPIException(ArrayList<Object> resultsErrors) {
        String s = new Gson().toJson(resultsErrors);
        JsonElement jsonElement = new JsonParser().parse(s);
        JsonArray jarray = jsonElement.getAsJsonArray();
        String t = "";
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jobject = jarray.get(i).getAsJsonObject();
            jobject = jobject.getAsJsonObject("errorResponse");
            t = jobject.toString();
        }
        return t;
    }
}

