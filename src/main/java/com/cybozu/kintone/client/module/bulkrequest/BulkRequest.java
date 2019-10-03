/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.bulkrequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestConstants;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestItem;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestModel;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestResponse;
import com.cybozu.kintone.client.model.record.AddRecordRequest;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsRequest;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.DeleteRecordsRequest;
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
import com.cybozu.kintone.client.module.parser.BulkRequestParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BulkRequest {
    private static final BulkRequestParser parser = new BulkRequestParser();
    private Connection connection;
    private BulkRequestModel bulkRequests;

    /**
     * Constructor function of BulkRequest.
     *
     * @param connection connection of the BulkRequest
     */
    public BulkRequest(Connection connection) {
        this.connection = connection;
        this.bulkRequests = new BulkRequestModel();
    }

    /**
     * Add the record.
     *
     * @param app    app of the addRecord
     * @param record record of the addRecord
     * @return this
     */
    public BulkRequest addRecord(Integer app, HashMap<String, FieldValue> record) {
        AddRecordRequest addRecordRequest = new AddRecordRequest(app, record);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.POST_REQUEST, connection.getPathURI(ConnectionConstants.RECORD), addRecordRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Add multi records.
     *
     * @param app     app of the addRecords
     * @param records records of the addRecords
     * @return this
     */
    public BulkRequest addRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records) {

        ArrayList<HashMap<String, FieldValue>> tempRecords = new ArrayList<HashMap<String, FieldValue>>();
        for (HashMap<String, FieldValue> record : records) {
            if (record == null) {
                tempRecords.add(new HashMap<String, FieldValue>());
            } else {
                tempRecords.add(record);
            }
        }

        AddRecordsRequest addRecordsRequest = new AddRecordsRequest(app, tempRecords);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.POST_REQUEST, connection.getPathURI(ConnectionConstants.RECORDS), addRecordsRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update the specific record by ID.
     *
     * @param app      app of the updateRecordByID
     * @param id       id of the updateRecordByID
     * @param record   record of the updateRecordByID
     * @param revision revision of the updateRecordByID
     * @return this
     */
    public BulkRequest updateRecordByID(Integer app, Integer id, HashMap<String, FieldValue> record, Integer revision) {
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest(app, id, null, revision, record);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORD), updateRecordRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update the specific record by updateKey.
     *
     * @param app       app of the updateRecordByUpdateKey
     * @param updateKey updateKey of the updateRecordByUpdateKey
     * @param record    record of the updateRecordByUpdateKey
     * @param revision  revision of the updateRecordByUpdateKey
     * @return this
     */
    public BulkRequest updateRecordByUpdateKey(Integer app, RecordUpdateKey updateKey, HashMap<String, FieldValue> record, Integer revision) {
        UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest(app, null, updateKey, revision, record);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORD), updateRecordRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update multi records.
     *
     * @param app     app of the updateRecords
     * @param records records of the updateRecords
     * @return this
     */
    public BulkRequest updateRecords(Integer app, ArrayList<RecordUpdateItem> records) {
        UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest(app, records);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORDS), updateRecordsRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Delete multi records.
     *
     * @param app app of the deleteRecords
     * @param ids ids of the deleteRecords
     * @return this
     */
    public BulkRequest deleteRecords(Integer app, ArrayList<Integer> ids) {
        DeleteRecordsRequest deleteRecordsRequest = new DeleteRecordsRequest(app, ids, null);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.DELETE_REQUEST, connection.getPathURI(ConnectionConstants.RECORDS), deleteRecordsRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Delete records at the specific revision.
     *
     * @param app             app of the deleteRecordsWithRevision
     * @param idsWithRevision idsWithRevision of the deleteRecordsWithRevision
     * @return this
     */
    public BulkRequest deleteRecordsWithRevision(Integer app, HashMap<Integer, Integer> idsWithRevision) {

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Integer> revisions = new ArrayList<Integer>();

        for (Entry<Integer, Integer> entry : idsWithRevision.entrySet()) {
            ids.add(entry.getKey());
            revisions.add(entry.getValue());
        }

        DeleteRecordsRequest deleteRecordsRequest = new DeleteRecordsRequest(app, ids, revisions);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.DELETE_REQUEST, connection.getPathURI(ConnectionConstants.RECORDS), deleteRecordsRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update assignees of the specific record.
     *
     * @param app       app of the updateRecordAssignees
     * @param record    record of the updateRecordAssignees
     * @param assignees assignees of the updateRecordAssignees
     * @param revision  revision of the updateRecordAssignees
     * @return this
     */
    public BulkRequest updateRecordAssignees(Integer app, Integer record, ArrayList<String> assignees, Integer revision) {

        UpdateRecordAssigneesRequest updateRecordAssigneesRequest = new UpdateRecordAssigneesRequest(app, record, assignees, revision);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORD_ASSIGNEES), updateRecordAssigneesRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update status of the specific record.
     *
     * @param app      app of the updateRecordStatus
     * @param id       id of the updateRecordStatus
     * @param action   action of the updateRecordStatus
     * @param assignee assignee of the updateRecordStatus
     * @param revision revision of the updateRecordStatus
     * @return this
     */
    public BulkRequest updateRecordStatus(Integer app, Integer id, String action, String assignee, Integer revision) {

        UpdateRecordStatusRequest updateRecordStatusRequest = new UpdateRecordStatusRequest(action, app, assignee, id, revision);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORD_STATUS), updateRecordStatusRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Update status of the multi records.
     *
     * @param app     app of the updateRecordsStatus
     * @param records records of the updateRecordsStatus
     * @return this
     */
    public BulkRequest updateRecordsStatus(Integer app, ArrayList<RecordUpdateStatusItem> records) {

        UpdateRecordsStatusRequest updateRecordsStatusRequest = new UpdateRecordsStatusRequest(app, records);
        BulkRequestItem bulkRequestItem = new BulkRequestItem(ConnectionConstants.PUT_REQUEST, connection.getPathURI(ConnectionConstants.RECORDS_STATUS), updateRecordsStatusRequest);
        this.bulkRequests.addRequest(bulkRequestItem);
        return this;
    }

    /**
     * Execute the BulkRequest and get data which is returned from kintone.
     *
     * @return BulkRequestResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public BulkRequestResponse execute() throws KintoneAPIException {
        BulkRequestResponse responses = new BulkRequestResponse();
        ArrayList<BulkRequestItem> requests = this.bulkRequests.getRequests();

        JsonElement response = connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.BULK_REQUEST, parser.parseObject(this.bulkRequests));

        JsonObject object = response.getAsJsonObject();
        JsonArray array = object.getAsJsonArray("results");

        Integer count = 0;
        for (BulkRequestItem request : requests) {
            switch (request.getPayload().getClass().getSimpleName()) {
                case BulkRequestConstants.ADD_RECORD:
                    responses.addResponse(parser.parseJson(array.get(count), AddRecordResponse.class));
                    break;
                case BulkRequestConstants.ADD_RECORDS:
                    responses.addResponse(parser.parseJson(array.get(count), AddRecordsResponse.class));
                    break;
                case BulkRequestConstants.UPDATE_RECORD:
                    responses.addResponse(parser.parseJson(array.get(count), UpdateRecordResponse.class));
                    break;
                case BulkRequestConstants.UPDATE_RECORDS:
                    responses.addResponse(parser.parseJson(array.get(count), UpdateRecordsResponse.class));
                    break;
                case BulkRequestConstants.DELETE_RECORDS:
                    responses.addResponse(parser.parseJson(array.get(count), HashMap.class));
                    break;
                case BulkRequestConstants.UPDATE_STATUS_RECORD:
                    responses.addResponse(parser.parseJson(array.get(count), UpdateRecordResponse.class));
                    break;
                case BulkRequestConstants.UPDATE_STATUS_RECORDS:
                    responses.addResponse(parser.parseJson(array.get(count), UpdateRecordsResponse.class));
                    break;
                case BulkRequestConstants.UPDATE_ASSIGNEE_RECORDS:
                    responses.addResponse(parser.parseJson(array.get(count), UpdateRecordResponse.class));
                    break;
                default:
                    throw new KintoneAPIException("Invalid Request Command");
            }
            count++;
        }
        return responses;
    }
}
