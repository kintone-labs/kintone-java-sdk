/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.comment.AddCommentRecordRequest;
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.DeleteCommentRecordRequest;
import com.cybozu.kintone.client.model.comment.GetCommentsRecordRequest;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.record.AddRecordRequest;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsRequest;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.DeleteRecordsRequest;
import com.cybozu.kintone.client.model.record.GetRecordRequest;
import com.cybozu.kintone.client.model.record.GetRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsRequest;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateItem;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;
import com.cybozu.kintone.client.model.record.UpdateRecordAssigneesRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordStatusRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordsRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordsStatusRequest;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.parser.RecordParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Record {

    private static final RecordParser parser = new RecordParser();
    private Connection connection;

    /**
     * Constractor
     * @param connection
     */
    public Record(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get a record from kintone APP
     * @param app
     * @param id
     * @return
     * @throws KintoneAPIException
     */
    public GetRecordResponse getRecord(Integer app, Integer id) throws KintoneAPIException {
        // execute GET RECORD API
        GetRecordRequest getRecordRequest = new GetRecordRequest(app, id);
        String requestBody = parser.parseObject(getRecordRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORD,
                requestBody);
        // get record as JsonObject
        JsonObject recordJson = response.getAsJsonObject().getAsJsonObject("record");

        // convert JsonObject to HashMap<String, FieldValue> class
        HashMap<String, FieldValue> record = new HashMap<>();
        for (Entry<String, JsonElement> entry : recordJson.entrySet()) {
            String fieldType = entry.getValue().getAsJsonObject().get("type").getAsString();
            JsonElement fieldValue = entry.getValue().getAsJsonObject().get("value");
            FieldValue field = parser.parseField(fieldType, fieldValue);
            record.put(entry.getKey(), field);
        }

        // return response as GetRecordResponse class
        GetRecordResponse getRecordResponse = new GetRecordResponse();
        getRecordResponse.setRecord(record);
        return getRecordResponse;
    }

    /**
     * Get records from kintone APP by query
     * @param app
     * @param query
     * @param fields
     * @param totalCount
     * @return
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getRecords(Integer app, String query, ArrayList<String> fields, Boolean totalCount)
            throws KintoneAPIException {
        // execute GET RECORDS API
        GetRecordsRequest getRecordsRequest = new GetRecordsRequest(fields, app, query, totalCount);
        String requestBody = parser.parseObject(getRecordsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORDS,
                requestBody);
        // get records as JsonObject
        JsonArray recordsJson = response.getAsJsonObject().getAsJsonArray("records");
        JsonElement recordsCount = response.getAsJsonObject().get("totalCount");

        ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
        // processing for each record
        for (JsonElement jsonElement : recordsJson) {
            // convert JsonObject to HashMap<String, FieldValue> class
            HashMap<String, FieldValue> record = new HashMap<>();
            JsonObject recordJson = jsonElement.getAsJsonObject();
            for (Entry<String, JsonElement> entry : recordJson.entrySet()) {
                String fieldType = entry.getValue().getAsJsonObject().get("type").getAsString();
                JsonElement fieldValue = entry.getValue().getAsJsonObject().get("value");
                FieldValue field = parser.parseField(fieldType, fieldValue);
                record.put(entry.getKey(), field);
            }
            records.add(record);
        }

        // return response as GetRecordsResponse class
        GetRecordsResponse getRecordsResponse = new GetRecordsResponse();
        getRecordsResponse.setRecords(records);
        getRecordsResponse.setTotalCount((Integer) parser.parseJson(recordsCount, Integer.class));
        return getRecordsResponse;
    }

    /**
     * Add a record to kintone APP
     * @param app
     * @param record
     * @return
     * @throws KintoneAPIException
     */
    public AddRecordResponse addRecord(Integer app, HashMap<String, FieldValue> record) throws KintoneAPIException {
        // execute POST RECORD API
        AddRecordRequest addRecordRequest = new AddRecordRequest(app, record);
        String requestBody = parser.parseObject(addRecordRequest);
        JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORD,
                requestBody);
        // return response as AddRecordResponse class
        return (AddRecordResponse) parser.parseJson(response, AddRecordResponse.class);
    }

    /**
     * Add records to kintone APP
     * @param app
     * @param records
     * @return
     * @throws KintoneAPIException
     */
    public AddRecordsResponse addRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records)
            throws KintoneAPIException {
        // execute POST RECORDS API
        AddRecordsRequest addRecordsRequest = new AddRecordsRequest(app, records);
        String requestBody = parser.parseObject(addRecordsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORDS,
                requestBody);
        // return response as AddRecordsResponse class
        return (AddRecordsResponse) parser.parseJson(response, AddRecordsResponse.class);
    }

    /**
     * Update a record on kintone APP by ID
     * @param app
     * @param id
     * @param record
     * @param revision
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordResponse updateRecordByID(Integer app, Integer id, HashMap<String, FieldValue> record,
            Integer revision) throws KintoneAPIException {
        // execute PUT RECORD API
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest(app, id, null, revision, record);
        String requestBody = parser.parseObject(updateRecordRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD,
                requestBody);
        // return response as UpdateRecordResponse class
        return (UpdateRecordResponse) parser.parseJson(response, UpdateRecordResponse.class);
    }

    /**
     * Update a record on kintone APP by UpdateKey
     * @param app
     * @param updateKey
     * @param record
     * @param revision
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordResponse updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey,
            HashMap<String, FieldValue> record, Integer revision) throws KintoneAPIException {
        // execute PUT RECORD API
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest(app, null, updateKey, revision, record);
        String requestBody = parser.parseObject(updateRecordRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORD,
                requestBody);
        // return response as UpdateRecordResponse class
        return (UpdateRecordResponse) parser.parseJson(response, UpdateRecordResponse.class);
    }

    /**
     * Update records on kintone APP
     * @param app
     * @param records
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordsResponse updateRecords(Integer app, ArrayList<RecordUpdateItem> records)
            throws KintoneAPIException {
        // execute PUT RECORDS API
        UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest(app, records);
        String requestBody = parser.parseObject(updateRecordsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST, ConnectionConstants.RECORDS,
                requestBody);
        // return response as UpdateRecordsResponse class
        return (UpdateRecordsResponse) parser.parseJson(response, UpdateRecordsResponse.class);
    }

    /**
     * Delete records on kintone APP
     * @param app
     * @param ids
     * @return
     * @throws KintoneAPIException
     */
    public void deleteRecords(Integer app, ArrayList<Integer> ids) throws KintoneAPIException {
        // execute DELETE RECORDS API
        DeleteRecordsRequest deleteRecordsRequest = new DeleteRecordsRequest(app, ids, null);
        String requestBody = parser.parseObject(deleteRecordsRequest);
        this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, requestBody);
    }

    /**
     * Delete records on kintone APP with revision
     * @param app
     * @param idsWithRevision
     * @return
     * @throws KintoneAPIException
     */
    public void deleteRecordsWithRevision(Integer app, HashMap<Integer, Integer> idsWithRevision)
            throws KintoneAPIException {
        // check parameter
        if (idsWithRevision == null) {
            throw new KintoneAPIException("invalid param");
        }
        // split idsWithRevision into key list and value list
        ArrayList<Integer> keys = new ArrayList<Integer>();
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (Entry<Integer, Integer> entry : idsWithRevision.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        // execute DELETE RECORDS API
        DeleteRecordsRequest deleteRecordsRequest = new DeleteRecordsRequest(app, keys, values);
        String requestBody = parser.parseObject(deleteRecordsRequest);
        this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, requestBody);
    }

    /**
     * Update assignees of record on kintone APP
     * @param app
     * @param id
     * @param assignees
     * @param revision
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordResponse updateRecordAssignees(Integer app, Integer id, ArrayList<String> assignees,
            Integer revision) throws KintoneAPIException {
        // execute PUT RECORD_ASSIGNEES API
        UpdateRecordAssigneesRequest updateRecordAssigneesRequest = new UpdateRecordAssigneesRequest(app, id, assignees,
                revision);
        String requestBody = parser.parseObject(updateRecordAssigneesRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST,
                ConnectionConstants.RECORD_ASSIGNEES,
                requestBody);
        // return response as UpdateRecordResponse class
        return (UpdateRecordResponse) parser.parseJson(response, UpdateRecordResponse.class);
    }

    /**
     * Update status of record on kintone APP
     * @param app
     * @param id
     * @param action
     * @param assignee
     * @param revision
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordResponse updateRecordStatus(Integer app, Integer id, String action, String assignee,
            Integer revision) throws KintoneAPIException {
        // execute PUT RECORD_STATUS API
        UpdateRecordStatusRequest updateRecordStatusRequest = new UpdateRecordStatusRequest(action, app, assignee, id,
                revision);
        String requestBody = parser.parseObject(updateRecordStatusRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST,
                ConnectionConstants.RECORD_STATUS,
                requestBody);
        // return response as UpdateRecordResponse class
        return (UpdateRecordResponse) parser.parseJson(response, UpdateRecordResponse.class);
    }

    /**
     * Update statuses of records on kintone APP
     * @param app
     * @param records
     * @return
     * @throws KintoneAPIException
     */
    public UpdateRecordsResponse updateRecordsStatus(Integer app, ArrayList<RecordUpdateStatusItem> records)
            throws KintoneAPIException {
        // execute PUT RECORDS_STATUS API
        UpdateRecordsStatusRequest updateRecordsStatusRequest = new UpdateRecordsStatusRequest(app, records);
        String requestBody = parser.parseObject(updateRecordsStatusRequest);
        JsonElement response = this.connection.request(ConnectionConstants.PUT_REQUEST,
                ConnectionConstants.RECORDS_STATUS,
                requestBody);
        // return response as UpdateRecordsResponse class
        return (UpdateRecordsResponse) parser.parseJson(response, UpdateRecordsResponse.class);
    }

    /**
     * Get comments of a record on kintone APP
     * @param app
     * @param record
     * @param order
     * @param offset
     * @param limit
     * @return
     * @throws KintoneAPIException
     */
    public GetCommentsResponse getComments(Integer app, Integer record, String order, Integer offset, Integer limit)
            throws KintoneAPIException {
        // execute GET RECORD_COMMENTS API
        GetCommentsRecordRequest getCommentsRequest = new GetCommentsRecordRequest(app, record, order, offset, limit);
        String requestBody = parser.parseObject(getCommentsRequest);
        JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST,
                ConnectionConstants.RECORD_COMMENTS,
                requestBody);
        // return response as GetCommentsResponse class
        return (GetCommentsResponse) parser.parseJson(response, GetCommentsResponse.class);
    }

    /**
     * Add a comment to record on kintone APP
     * @param app
     * @param record
     * @param comment
     * @return
     * @throws KintoneAPIException
     */
    public AddCommentResponse addComment(Integer app, Integer record, CommentContent comment)
            throws KintoneAPIException {
        // execute POST RECORD_COMMENT API
        AddCommentRecordRequest addCommentRequest = new AddCommentRecordRequest(app, record, comment);
        String requestBody = parser.parseObject(addCommentRequest);
        JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST,
                ConnectionConstants.RECORD_COMMENT,
                requestBody);
        // return response as AddCommentResponse class
        return (AddCommentResponse) parser.parseJson(response, AddCommentResponse.class);
    }

    /**
     * Delete a comment in record on kintone APP
     * @param app
     * @param record
     * @param comment
     * @return
     * @throws KintoneAPIException
     */
    public void deleteComment(Integer app, Integer record, Integer comment) throws KintoneAPIException {
        // execute DELETE RECORD_COMMENT API
        DeleteCommentRecordRequest deleteCommentRequest = new DeleteCommentRecordRequest(app, record, comment);
        String requestBody = parser.parseObject(deleteCommentRequest);
        this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORD_COMMENT, requestBody);
        ;
    }

}
