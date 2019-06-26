package com.cybozu.kintone.client.module.recordCursor;

import java.util.ArrayList;
import java.util.HashMap;

import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.connection.ConnectionConstants;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.cursor.*;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.parser.CursorParser;
import com.google.gson.JsonElement;

public class RecordCursor {
	private static final CursorParser parser = new CursorParser();
    private Connection connection;
    
	public RecordCursor(Connection connection) {
        this.connection = connection;
    }
	
	/**
	 * Create cursor for record module
	 * @param app
	 * @param fields
	 * @param query
	 * @param size
	 * @return
	 * @throws KintoneAPIException
	 */
	public CreateRecordCursorResponse createCursor(Integer app, ArrayList<String> fields, String query, Integer size) throws KintoneAPIException {
		CreateRecordCursorRequest createRecordCursorRequest = new CreateRecordCursorRequest(app, fields, query, size);
		String requestBody = (String) parser.parseObject(createRecordCursorRequest);
		
		JsonElement response = this.connection.request(ConnectionConstants.POST_REQUEST, ConnectionConstants.RECORDS_CURSOR,
                requestBody);
		return (CreateRecordCursorResponse) parser.parseJson(response, CreateRecordCursorResponse.class);
	}
	
	/**
	 * Get the records by cursor
	 * @param cursorID
	 * @return
	 * @throws KintoneAPIException
	 */
	public GetRecordCursorResponse getRecords(String cursorID) throws KintoneAPIException {
		GetRecordCursorRequest recordCursorRequest = new GetRecordCursorRequest(cursorID);
		String requestBody = (String) parser.parseObject(recordCursorRequest);
		JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORDS_CURSOR,
                requestBody);
		return parser.parseForGetRecordCursorResponse(response);
	}
	
	/**
	 * Get all records by cursor
	 * @param cursorID
	 * @return
	 * @throws KintoneAPIException
	 */
	public GetRecordsResponse getAllRecords(String cursorID) throws KintoneAPIException {
		GetRecordCursorRequest recordCursorRequest = new GetRecordCursorRequest(cursorID);
		String requestBody = (String) parser.parseObject(recordCursorRequest);
		GetRecordCursorResponse getRecordCursorResponse = new GetRecordCursorResponse();
		getRecordCursorResponse.setNext(true);
		
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		while (getRecordCursorResponse.getNext() == true) {
			JsonElement response = this.connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.RECORDS_CURSOR,
	                requestBody);
			getRecordCursorResponse = parser.parseForGetRecordCursorResponse(response);
			records.addAll(getRecordCursorResponse.getRecords());
		}
		GetRecordsResponse getRecordsResponse = new GetRecordsResponse();
		getRecordsResponse.setRecords(records);
		getRecordsResponse.setTotalCount(records.size());
		
		return getRecordsResponse;
	}
	
	/**
	 * Delete the cursor
	 * @param cursorID
	 * @throws KintoneAPIException
	 */
	public void deleteCursor(String cursorID) throws KintoneAPIException {
		GetRecordCursorRequest recordCursorRequest = new GetRecordCursorRequest(cursorID);
		String requestBody = (String) parser.parseObject(recordCursorRequest);
		
		this.connection.request(ConnectionConstants.DELETE_REQUEST, ConnectionConstants.RECORDS_CURSOR,
                requestBody);
	}
}
