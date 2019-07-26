/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.module.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.BulksException;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.exception.ErrorResponse;
import com.cybozu.kintone.client.model.bulkrequest.BulkRequestResponse;
import com.cybozu.kintone.client.model.comment.AddCommentRecordRequest;
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.DeleteCommentRecordRequest;
import com.cybozu.kintone.client.model.comment.GetCommentsRecordRequest;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.cursor.CreateRecordCursorResponse;
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
import com.cybozu.kintone.client.model.record.RecordsUpsertItem;
import com.cybozu.kintone.client.model.record.UpdateRecordAssigneesRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordStatusRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordsRequest;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordsStatusRequest;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.bulkrequest.BulkRequest;
import com.cybozu.kintone.client.module.parser.RecordParser;
import com.cybozu.kintone.client.module.recordCursor.RecordCursor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Record {

    private static final RecordParser parser = new RecordParser();
    private Connection connection;
    private static final Integer LIMIT_GET_RECORD = 500;
    private static final Integer LIMIT_POST_RECORD = 100;
    private static final Integer LIMIT_UPDATE_RECORD = 100;
    private static final Integer LIMIT_DELETE_RECORD = 100;
    private static final Integer LIMIT_UPSERT_RECORD = 1500;
    private static final Integer NUM_BULK_REQUEST = 20;

    /**
     * Constractor
     *
     * @param connection connection of the Record
     */
    public Record(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get a record from kintone APP
     *
     * @param app app of the getRecord
     * @param id  id of the getRecord
     * @return GetRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app        app of the getRecords
     * @param query      query of the getRecords
     * @param fields     fields of the getRecords
     * @param totalCount totalCount of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     * Get all records by cursor
     *
     * @param app
     * @param query
     * @param fields
     * @return
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByCursor(Integer app, String query, ArrayList<String> fields)
            throws KintoneAPIException {
        RecordCursor recordCursor = new RecordCursor(this.connection);
        CreateRecordCursorResponse cursor = recordCursor.createCursor(app, fields, query, LIMIT_GET_RECORD);

        return recordCursor.getAllRecords(cursor.getId());
    }

    /**
     * Fetch 1 block of records from kintone APP by query
     *
     * @param app        app to fetch records
     * @param query      query condition
     * @param fields     fields to get
     * @param totalCount return totalCount or not
     * @param offset     offset
     * @param records    initial list of records
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */

    private GetRecordsResponse fetchRecords(Integer app, String query, ArrayList<String> fields, Boolean totalCount, Integer offset, ArrayList<HashMap<String, FieldValue>> records) throws KintoneAPIException {
        String validQuery;
        if (query.length() != 0) {
            validQuery = query + " limit " + LIMIT_GET_RECORD + " offset " + offset;
        } else {
            validQuery = "limit " + LIMIT_GET_RECORD + " offset " + offset;
        }
        GetRecordsResponse fetchBlock = this.getRecords(app, validQuery, fields, totalCount);
        records.addAll(fetchBlock.getRecords());
        if (fetchBlock.getRecords().size() < LIMIT_GET_RECORD) {
            fetchBlock.setRecords(records);
            return fetchBlock;
        }
        offset = offset + LIMIT_GET_RECORD;
        return this.fetchRecords(app, query, fields, totalCount, offset, records);
    }

    /**
     * Get all records from kintone APP
     *
     * @param app        app of the getRecords
     * @param query      query of the getRecords
     * @param fields     fields of the getRecords
     * @param totalCount totalCount of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, String query, ArrayList<String> fields, Boolean totalCount) throws KintoneAPIException {
        return this.fetchRecords(app, query, fields, totalCount, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app        app of the getRecords
     * @param fields     fields of the getRecords
     * @param totalCount totalCount of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, ArrayList<String> fields, Boolean totalCount) throws KintoneAPIException {
        return this.fetchRecords(app, "", fields, totalCount, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app        app of the getRecords
     * @param query      query of the getRecords
     * @param totalCount totalCount of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, String query, Boolean totalCount) throws KintoneAPIException {
        return this.fetchRecords(app, query, new ArrayList<String>(), totalCount, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app    app of the getRecords
     * @param query  query of the getRecords
     * @param fields fields of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, String query, ArrayList<String> fields) throws KintoneAPIException {
        return this.fetchRecords(app, query, fields, false, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app   app of the getRecords
     * @param query query of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, String query) throws KintoneAPIException {
        return this.fetchRecords(app, query, new ArrayList<String>(), false, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app    app of the getRecords
     * @param fields fields of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, ArrayList<String> fields) throws KintoneAPIException {
        return this.fetchRecords(app, "", fields, false, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app        app of the getRecords
     * @param totalCount totalCount of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app, Boolean totalCount) throws KintoneAPIException {
        return this.fetchRecords(app, "", new ArrayList<String>(), totalCount, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Get all records from kintone APP
     *
     * @param app app of the getRecords
     * @return GetRecordsResponse
     * @throws KintoneAPIException
     */
    public GetRecordsResponse getAllRecordsByQuery(Integer app) throws KintoneAPIException {
        return this.fetchRecords(app, "", new ArrayList<String>(), false, 0, new ArrayList<HashMap<String, FieldValue>>());
    }

    /**
     * Add a record to kintone APP
     *
     * @param app    app of the addRecord
     * @param record record of the addRecord
     * @return AddRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app     app of the addRecords
     * @param records records of the addRecords
     * @return AddRecordsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app      app of the updateRecordByID
     * @param id       id of the updateRecordByID
     * @param record   record of the updateRecordByID
     * @param revision revision of the updateRecordByID
     * @return UpdateRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app       app of the updateRecordByUpdateKey
     * @param updateKey updateKey of the updateRecordByUpdateKey
     * @param record    record of the updateRecordByUpdateKey
     * @param revision  revision of the updateRecordByUpdateKey
     * @return UpdateRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     * Upsert record on kintone APP
     *
     * @param app       app of the updateRecords
     * @param updateKey updateKey of the updateRecordByUpdateKey
     * @param record    record of the updateRecordByUpdateKey
     * @param revision  revision of the updateRecordByUpdateKey
     * @return UpdateRecordResponse or AddRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public Object upsertRecord(Integer app, RecordUpdateKey updateKey,
                               HashMap<String, FieldValue> record, Integer revision) throws KintoneAPIException {
        try {
            UpdateRecordResponse updateRecordResponse = this.updateRecordByUpdateKey(app, updateKey, record, revision);
            return updateRecordResponse;
        } catch (KintoneAPIException e) {
            String NO_RECORD_FOUND = "GAIA_RE20";
            ErrorResponse error = e.getErrorResponse();
            if (!error.getCode().equals(NO_RECORD_FOUND)) {
                throw e;
            }
            AddRecordResponse addRecordResponse = this.addRecord(app, record);
            return addRecordResponse;
        }
    }

    /**
     * Upsert record on kintone APP
     *
     * @param app       app of the updateRecords
     * @param updateKey updateKey of the updateRecordByUpdateKey
     * @param record    record of the updateRecordByUpdateKey
     * @return UpdateRecordResponse or AddRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public Object upsertRecord(Integer app, RecordUpdateKey updateKey,
                               HashMap<String, FieldValue> record) throws KintoneAPIException {
        try {
            UpdateRecordResponse updateRecordResponse = this.updateRecordByUpdateKey(app, updateKey, record, -1);
            return updateRecordResponse;
        } catch (KintoneAPIException e) {
            String NO_RECORD_FOUND = "GAIA_RE20";
            ErrorResponse error = e.getErrorResponse();
            if (!error.getCode().equals(NO_RECORD_FOUND)) {
                throw e;
            }
            AddRecordResponse addRecordResponse = this.addRecord(app, record);
            return addRecordResponse;
        }
    }

    private Boolean doesExistSameFieldValue(ArrayList<HashMap<String, FieldValue>> allRecords, RecordsUpsertItem comparedRecord) throws KintoneAPIException {
        if (comparedRecord.getUpdateKey() == null) {
            return false;
        }
        if (comparedRecord.getUpdateKey().getValue().length() == 0) {
            return false;
        }
        String fieldKey = comparedRecord.getUpdateKey().getField();
        for (int i = 0; i < allRecords.size(); i++) {
            if (allRecords.get(i).get(fieldKey).getValue() == comparedRecord.getUpdateKey().getValue()) {
                return true;
            }
        }

        return false;
    }

    private BulkRequest makePUTBulkReq(Integer app, BulkRequest bulkRequest, ArrayList<RecordUpdateItem> records) {
        int length = records.size();
        int loopTimes = (int) Math.ceil((double) length / (double) Record.LIMIT_UPDATE_RECORD);

        for (int i = 0; i < loopTimes; i++) {
            int begin = i * Record.LIMIT_UPDATE_RECORD;
            int end = length;
            if (length - begin >= Record.LIMIT_UPDATE_RECORD) {
                end = begin + Record.LIMIT_UPDATE_RECORD;
            }
            ArrayList<RecordUpdateItem> recordsPerRequest = (ArrayList<RecordUpdateItem>) records.subList(begin, end);
            bulkRequest.updateRecords(app, recordsPerRequest);
        }
        return bulkRequest;
    }

    private BulkRequest makePOSTBulkReq(Integer app, BulkRequest bulkRequest, ArrayList<HashMap<String, FieldValue>> records) {
        int length = records.size();
        int loopTimes = (int) Math.ceil((double) length / (double) Record.LIMIT_POST_RECORD);

        for (int i = 0; i < loopTimes; i++) {
            int begin = i * Record.LIMIT_POST_RECORD;
            int end = length;
            if (length - begin >= Record.LIMIT_POST_RECORD) {
                end = begin + Record.LIMIT_POST_RECORD;
            }
            ArrayList<HashMap<String, FieldValue>> recordsPerRequest = new ArrayList<HashMap<String, FieldValue>>(records.subList(begin, end));
            bulkRequest.addRecords(app, recordsPerRequest);
        }
        return bulkRequest;
    }

    private BulkRequestResponse executeUpsertBulkRequest(Integer app, ArrayList<HashMap<String, FieldValue>> recordsForPost, ArrayList<RecordUpdateItem> recordsForPut) throws KintoneAPIException {
        BulkRequest bulkRequest = new BulkRequest(this.connection);
        bulkRequest = this.makePOSTBulkReq(app, bulkRequest, recordsForPost);
        bulkRequest = this.makePUTBulkReq(app, bulkRequest, recordsForPut);
        return bulkRequest.execute();
    }

    public BulkRequestResponse upsertRecords(Integer app, ArrayList<RecordsUpsertItem> records) throws KintoneAPIException {
        if (records.size() > Record.LIMIT_UPSERT_RECORD) {
            throw new Error("upsertRecords can't handle over " + Record.LIMIT_UPSERT_RECORD + " records.");
        }
        ArrayList<HashMap<String, FieldValue>> allRecords = this.getAllRecordsByQuery(app, "", new ArrayList<>(), false).getRecords();
        ArrayList<HashMap<String, FieldValue>> recordsForPost = new ArrayList<HashMap<String, FieldValue>>();
        ArrayList<RecordUpdateItem> recordsForPut = new ArrayList<RecordUpdateItem>();

        for (int i = 0; i < records.size(); i++) {
            if (doesExistSameFieldValue(allRecords, records.get(i))) {
                recordsForPut.add(new RecordUpdateItem(records.get(i).getUpdateKey(), records.get(i).getRecord()));
            } else {
                recordsForPost.add(records.get(i).getRecord());
            }
        }
        return executeUpsertBulkRequest(app, recordsForPost, recordsForPut);
    }

    /**
     * Update records on kintone APP
     *
     * @param app     app of the updateRecords
     * @param records records of the updateRecords
     * @return UpdateRecordsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app app of the deleteRecords
     * @param ids ids of the deleteRecords
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public void deleteRecords(Integer app, ArrayList<Integer> ids) throws KintoneAPIException {
        // execute DELETE RECORDS API
        DeleteRecordsRequest deleteRecordsRequest = new DeleteRecordsRequest(app, ids, null);
        String requestBody = parser.parseObject(deleteRecordsRequest);
        this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS, requestBody);
    }

    /**
     * Delete records on kintone APP with revision
     *
     * @param app             app of the deleteRecordsWithRevision
     * @param idsWithRevision idsWithRevision of the deleteRecordsWithRevision
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app       app of the updateRecordAssignees
     * @param id        id of the updateRecordAssignees
     * @param assignees assignees of the updateRecordAssignees
     * @param revision  revision of the updateRecordAssignees
     * @return UpdateRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app      app of the updateRecordStatus
     * @param id       id of the updateRecordStatus
     * @param action   action of the updateRecordStatus
     * @param assignee assignee of the updateRecordStatus
     * @param revision revision of the updateRecordStatus
     * @return UpdateRecordResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app     app of the updateRecordsStatus
     * @param records records of the updateRecordsStatus
     * @return UpdateRecordsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app    app of the getComments
     * @param record record of the getComments
     * @param order  order of the getComments
     * @param offset offset of the getComments
     * @param limit  limit of the getComments
     * @return GetCommentsResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app     app of the addComment
     * @param record  record of the addComment
     * @param comment comment of the addComment
     * @return AddCommentResponse
     * @throws KintoneAPIException the KintoneAPIException to throw
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
     *
     * @param app     app of the deleteComment
     * @param record  record of the deleteComment
     * @param comment comment of the deleteComment
     * @throws KintoneAPIException the KintoneAPIException to throw
     */
    public void deleteComment(Integer app, Integer record, Integer comment) throws KintoneAPIException {
        // execute DELETE RECORD_COMMENT API
        DeleteCommentRecordRequest deleteCommentRequest = new DeleteCommentRecordRequest(app, record, comment);
        String requestBody = parser.parseObject(deleteCommentRequest);
        this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORD_COMMENT, requestBody);
    }

    private BulkRequestResponse updateBulkRecord(int app, ArrayList<RecordUpdateItem> records) throws KintoneAPIException {
        BulkRequest bulkRequest = new BulkRequest(this.connection);
        int length = records.size();
        int loopTimes = (int) length / LIMIT_UPDATE_RECORD;
        if ((length % LIMIT_UPDATE_RECORD) > 0) {
            loopTimes++;
        }
        if (records.size() > 0 && records.size() < LIMIT_UPDATE_RECORD) {
            loopTimes = 1;
        }
        for (int index = 0; index < loopTimes; index++) {
            int begin = index * LIMIT_UPDATE_RECORD;
            int end = (length - begin) < LIMIT_UPDATE_RECORD ? length : begin + LIMIT_UPDATE_RECORD;
            ArrayList<RecordUpdateItem> recordsPerRequest = new ArrayList<RecordUpdateItem>(records.subList(begin, end));
            bulkRequest.updateRecords(app, recordsPerRequest);
        }
        return bulkRequest.execute();
    }

    private BulkRequestResponse addBulkRecord(int app, ArrayList<HashMap<String, FieldValue>> records) throws KintoneAPIException {
        BulkRequest bulkRequest = new BulkRequest(this.connection);
        int length = records.size();
        int loopTimes = (int) length / LIMIT_POST_RECORD;
        if ((length % LIMIT_POST_RECORD) > 0) {
            loopTimes++;
        }
        if (length > 0 && length < LIMIT_POST_RECORD) {
            loopTimes = 1;
        }
        for (int index = 0; index < loopTimes; index++) {
            int begin = index * LIMIT_POST_RECORD;
            int end = (length - begin) < LIMIT_POST_RECORD ? length : begin + LIMIT_POST_RECORD;
            ArrayList<HashMap<String, FieldValue>> recordsPerRequest = new ArrayList<HashMap<String, FieldValue>>(records.subList(begin, end));
            bulkRequest.addRecords(app, recordsPerRequest);
        }
        return bulkRequest.execute();
    }

    private BulkRequestResponse deleteBulkRecord(Integer app, ArrayList<Integer> ids) throws KintoneAPIException {
        BulkRequest bulkRequest = new BulkRequest(this.connection);
        int length = ids.size();
        int loopTimes = length / LIMIT_DELETE_RECORD;

        if ((length % LIMIT_DELETE_RECORD) > 0) {
            loopTimes++;
        }
        if (length > 0 && length < LIMIT_DELETE_RECORD) {
            loopTimes = 1;
        }
        for (int i = 0; i < loopTimes; i++) {
            int begin = i * LIMIT_DELETE_RECORD;
            int end = (length - begin) < LIMIT_DELETE_RECORD ? length : (begin + LIMIT_DELETE_RECORD);
            List<Integer> idsPerRequest = ids.subList(begin, end);
            ArrayList<Integer> idsPerRequestArray = new ArrayList<>(idsPerRequest);
            bulkRequest.deleteRecords(app, idsPerRequestArray);
        }
        return bulkRequest.execute();
    }

    public BulkRequestResponse deleteAllRecordsByQuery(Integer app, String query) throws BulksException, KintoneAPIException {
        BulkRequestResponse requestResponse = new BulkRequestResponse();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("$id");
        GetRecordsResponse getRecordsRequest = getAllRecordsByQuery(app, query, fields, true);
        ArrayList<HashMap<String, FieldValue>> recordsArray = getRecordsRequest.getRecords();
        int totalRecords = getRecordsRequest.getTotalCount();

        int numRecordsPerBulk = NUM_BULK_REQUEST * LIMIT_DELETE_RECORD;
        int numBulkRequest = totalRecords / numRecordsPerBulk;

        if ((totalRecords % numRecordsPerBulk) > 0) {
            numBulkRequest++;
        }
        if (totalRecords > 0 && totalRecords < numRecordsPerBulk) {
            numBulkRequest = 1;
        }
        ArrayList<Integer> ids = new ArrayList<>();
        recordsArray.forEach(item -> {
            Integer id = Integer.parseInt(item.get("$id").getValue().toString());
            ids.add(id);
        });

        int offset = 0;
        for (int i = 0; i < numBulkRequest; i++) {
            int end = (totalRecords - offset) < numRecordsPerBulk ? totalRecords : (offset + numRecordsPerBulk);
            List<Integer> idPerBulk = ids.subList(offset, end);
            ArrayList<Integer> idPerBulkArray = new ArrayList<>(idPerBulk);

            try {
                BulkRequestResponse requestResponsePerBulk = this.deleteBulkRecord(app, idPerBulkArray);
                requestResponse.addResponses(requestResponsePerBulk.getResults());
            } catch (KintoneAPIException e) {
                requestResponse.addResponse(e);
                throw new BulksException(requestResponse.getResults());
            }
            offset += numRecordsPerBulk;
        }
        return requestResponse;
    }

    public BulkRequestResponse deleteAllRecordsByQuery(Integer app) throws BulksException, KintoneAPIException {
        return deleteAllRecordsByQuery(app, "");
    }

    public BulkRequestResponse addAllRecords(Integer app, ArrayList<HashMap<String, FieldValue>> records) throws BulksException {
        int numRecordsPerBulk = NUM_BULK_REQUEST * LIMIT_POST_RECORD;
        int numBulkRequest = (int) (records.size() / numRecordsPerBulk);
        if ((records.size() % numRecordsPerBulk) > 0) {
            numBulkRequest++;
        }
        if (records.size() > 0 && records.size() < numRecordsPerBulk) {
            numBulkRequest = 1;
        }
        int offset = 0;
        BulkRequestResponse requestResponse = new BulkRequestResponse();
        for (int i = 0; i < numBulkRequest; i++) {
            int length = records.size();
            int end = (length - offset) < numRecordsPerBulk ? length : offset + numRecordsPerBulk;
            ArrayList<HashMap<String, FieldValue>> recordsPerBulk = new ArrayList<HashMap<String, FieldValue>>(records.subList(offset, end));
            try {
                BulkRequestResponse requestResponsePerBulk = this.addBulkRecord(app, recordsPerBulk);
                requestResponse.addResponses(requestResponsePerBulk.getResults());
            } catch (KintoneAPIException e) {
                requestResponse.addResponse(e);
                throw new BulksException(requestResponse.getResults());
            }

            offset += numRecordsPerBulk;
        }
        return requestResponse;
    }

    public BulkRequestResponse updateAllRecords(Integer app, ArrayList<RecordUpdateItem> records) throws BulksException {
        int numRecordsPerBulk = NUM_BULK_REQUEST * LIMIT_UPDATE_RECORD;
        int numBulkRequest = (int) records.size() / numRecordsPerBulk;
        if ((records.size() % numRecordsPerBulk) > 0) {
            numBulkRequest++;
        }
        if (records.size() > 0 && records.size() < numRecordsPerBulk) {
            numBulkRequest = 1;
        }
        int offset = 0;

        BulkRequestResponse requestResponse = new BulkRequestResponse();
        for (int i = 0; i < numBulkRequest; i++) {
            int length = records.size();
            int end = (length - offset) < numRecordsPerBulk ? length : offset + numRecordsPerBulk;

            ArrayList<RecordUpdateItem> recordsPerBulk = new ArrayList<RecordUpdateItem>(records.subList(offset, end));
            try {
                BulkRequestResponse requestResponsePerBulk = this.updateBulkRecord(app, recordsPerBulk);
                requestResponse.addResponses(requestResponsePerBulk.getResults());
            } catch (KintoneAPIException e) {
                requestResponse.addResponse(e);
                throw new BulksException(requestResponse.getResults());
            }

            offset += numRecordsPerBulk;
        }
        return requestResponse;
    }
}
