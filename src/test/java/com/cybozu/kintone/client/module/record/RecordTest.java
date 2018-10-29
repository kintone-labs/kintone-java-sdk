package com.cybozu.kintone.client.module.record;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.comment.AddCommentResponse;
import com.cybozu.kintone.client.model.comment.CommentContent;
import com.cybozu.kintone.client.model.comment.CommentMention;
import com.cybozu.kintone.client.model.comment.GetCommentsResponse;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.AddRecordResponse;
import com.cybozu.kintone.client.model.record.AddRecordsResponse;
import com.cybozu.kintone.client.model.record.GetRecordResponse;
import com.cybozu.kintone.client.model.record.GetRecordsResponse;
import com.cybozu.kintone.client.model.record.RecordUpdateItem;
import com.cybozu.kintone.client.model.record.RecordUpdateKey;
import com.cybozu.kintone.client.model.record.RecordUpdateResponseItem;
import com.cybozu.kintone.client.model.record.RecordUpdateStatusItem;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.UpdateRecordResponse;
import com.cybozu.kintone.client.model.record.UpdateRecordsResponse;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.cybozu.kintone.client.module.file.File;

public class RecordTest {

	private static String USERNAME = "xxxxx";
	private static String PASSWORD = "xxxxx";
	private static String API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_VIEW_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_ADD_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String ADD_NO_VIEW_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String BLANK_APP_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String GUEST_SPACE_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String ANOTHER_GUEST_SPACE_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String PROHIBIT_DUPLICATE_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String REQUIRED_FIELD_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_ADMIN_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_DELETE_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_EDIT_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String LOCAL_LANGUAGE_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_SET_ASSIGNEE_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static String NO_MANAGE_PERMISSION_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static Integer APP_ID = 1622;
	private static Integer GUEST_SPACE_ID = 129;

	private static Member testman1 = new Member("user1", "user1");
	private static Member testman2 = new Member("user2", "user2");
	private static Member testgroup1 = new Member("TeamA", "チームA");
    private static Member testgroup2 = new Member("TeamB", "チームB");
	private static Member testorg1 = new Member("検証組織", "検証組織");
	private static Member testorg2 = new Member("test", "テスト組織");
	private static Member testAdimin = new Member("xxxxx", "xxxxx");
	private static Member testTokenAdimin = new Member("xxxxx", "xxxxx");

	private Record passwordAuthRecordManagerment;
	private Record tokenRecordManagerment;
	private Record noviewpermissiontokenRecordManagerment;
	private Record noaddpermissiontokenReocrdManagerment;
	private Record addnoviewtokenRecordManagerment;
	private Record blankappapitokenRecordManagerment;
    private Record prohibitduplicatetokenRecordManagerment;
    private Record requiredfieldtokenRecordManagerment;
    private Record noadminpermissionRecordManagerment;
    private Record nodeletepermissionRecordManagerment;
    private Record noeditpermissionRecordManagerment;
    private Record locallanguageRecordManagerment;
    private Record nosetassigneeRcordManagerment;
    private Record nomanagepermissionRecordManagerment;
	private Integer uniqueKey = 1;

	@Before
	public void setup() throws KintoneAPIException {
		Auth passwordAuth = new Auth();
		passwordAuth.setPasswordAuth(USERNAME, PASSWORD);
		Connection passwordAuthConnection = new Connection(TestConstants.DOMAIN, passwordAuth);
		passwordAuthConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.passwordAuthRecordManagerment = new Record(passwordAuthConnection);

		Auth tokenAuth = new Auth();
		tokenAuth.setApiToken(API_TOKEN);
		Connection tokenConnection = new Connection(TestConstants.DOMAIN, tokenAuth);
		tokenConnection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.tokenRecordManagerment = new Record(tokenConnection);

		Auth tokenAuth1 = new Auth();
		tokenAuth1.setApiToken(NO_VIEW_PERMISSION_API_TOKEN);
		Connection tokenConnection1 = new Connection(TestConstants.DOMAIN, tokenAuth1);
		tokenConnection1.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.noviewpermissiontokenRecordManagerment = new Record(tokenConnection1);

		Auth tokenAuth2 = new Auth();
		tokenAuth2.setApiToken(BLANK_APP_API_TOKEN);
		Connection tokenConnection2 = new Connection(TestConstants.DOMAIN, tokenAuth2);
		tokenConnection2.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.blankappapitokenRecordManagerment = new Record(tokenConnection2);

		Auth tokenAuth3 = new Auth();
		tokenAuth3.setApiToken(PROHIBIT_DUPLICATE_API_TOKEN);
		Connection tokenConnection3 = new Connection(TestConstants.DOMAIN, tokenAuth3);
		tokenConnection3.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.prohibitduplicatetokenRecordManagerment = new Record(tokenConnection3);

		Auth tokenAuth4 = new Auth();
		tokenAuth4.setApiToken(REQUIRED_FIELD_API_TOKEN);
		Connection tokenConnection4 = new Connection(TestConstants.DOMAIN, tokenAuth4);
		tokenConnection4.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.requiredfieldtokenRecordManagerment = new Record(tokenConnection4);

		Auth tokenAuth5 = new Auth();
		tokenAuth5.setApiToken(NO_ADD_PERMISSION_API_TOKEN);
		Connection tokenConnection5 = new Connection(TestConstants.DOMAIN, tokenAuth5);
		tokenConnection5.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.noaddpermissiontokenReocrdManagerment = new Record(tokenConnection5);

		Auth tokenAuth6 = new Auth();
		tokenAuth6.setApiToken(ADD_NO_VIEW_API_TOKEN);
		Connection tokenConnection6 = new Connection(TestConstants.DOMAIN, tokenAuth6);
		tokenConnection6.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.addnoviewtokenRecordManagerment = new Record(tokenConnection6);

		Auth tokenAuth7 = new Auth();
		tokenAuth7.setApiToken(NO_ADMIN_PERMISSION_API_TOKEN);
		Connection tokenConnection7 = new Connection(TestConstants.DOMAIN, tokenAuth7);
		tokenConnection7.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.noadminpermissionRecordManagerment = new Record(tokenConnection7);

		Auth tokenAuth8 = new Auth();
		tokenAuth8.setApiToken(NO_DELETE_PERMISSION_API_TOKEN);
		Connection tokenConnection8 = new Connection(TestConstants.DOMAIN, tokenAuth8);
		tokenConnection8.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.nodeletepermissionRecordManagerment = new Record(tokenConnection8);

		Auth tokenAuth9 = new Auth();
		tokenAuth9.setApiToken(NO_EDIT_PERMISSION_API_TOKEN);
		Connection tokenConnection9 = new Connection(TestConstants.DOMAIN, tokenAuth9);
		tokenConnection9.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.noeditpermissionRecordManagerment = new Record(tokenConnection9);

		Auth tokenAuth10 = new Auth();
		tokenAuth10.setApiToken(LOCAL_LANGUAGE_API_TOKEN);
		Connection tokenConnection10 = new Connection(TestConstants.DOMAIN, tokenAuth10);
		tokenConnection10.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.locallanguageRecordManagerment = new Record(tokenConnection10);

		Auth tokenAuth11 = new Auth();
		tokenAuth11.setApiToken(NO_SET_ASSIGNEE_API_TOKEN);
		Connection tokenConnection11 = new Connection(TestConstants.DOMAIN, tokenAuth11);
		tokenConnection11.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.nosetassigneeRcordManagerment = new Record(tokenConnection11);

		Auth tokenAuth12 = new Auth();
		tokenAuth12.setApiToken(NO_MANAGE_PERMISSION_API_TOKEN);
		Connection tokenConnection12 = new Connection(TestConstants.DOMAIN, tokenAuth12);
		tokenConnection12.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.nomanagepermissionRecordManagerment = new Record(tokenConnection12);

		// get maximum "数値"field value in all records and set it uniqueKey.
		String query = "order by 数値 desc";
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("数値");
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, fields, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		this.uniqueKey += Integer.parseInt((String) resultRecords.get(0).get("数値").getValue());
	}

	public HashMap<String, FieldValue> createTestRecord() {
		HashMap<String, FieldValue> testRecord = new HashMap<String, FieldValue>();

		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text");
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, this.uniqueKey);
		this.uniqueKey += 1;
		testRecord = addField(testRecord, "文字列__複数行", FieldType.MULTI_LINE_TEXT, "test multi text");
		testRecord = addField(testRecord, "リッチエディター", FieldType.RICH_TEXT, "<div>test rich text<br /></div>");

		ArrayList<String> selectedItemList = new ArrayList<String>();
		selectedItemList.add("sample1");
		selectedItemList.add("sample2");
		testRecord = addField(testRecord, "チェックボックス", FieldType.CHECK_BOX, selectedItemList);
		testRecord = addField(testRecord, "ラジオボタン", FieldType.RADIO_BUTTON, "sample2");
		testRecord = addField(testRecord, "ドロップダウン", FieldType.DROP_DOWN, "sample3");
		testRecord = addField(testRecord, "複数選択", FieldType.MULTI_SELECT, selectedItemList);
		testRecord = addField(testRecord, "リンク", FieldType.LINK, "http://cybozu.co.jp/");
		testRecord = addField(testRecord, "日付", FieldType.DATE, "2018-01-01");
		testRecord = addField(testRecord, "時刻", FieldType.TIME, "12:34");
		testRecord = addField(testRecord, "日時", FieldType.DATETIME, "2018-01-02T02:30:00Z");

		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(testman1);
		userList.add(testman2);
		addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
		ArrayList<Member> groupList = new ArrayList<Member>();
		groupList.add(testgroup1);
		groupList.add(testgroup2);
		addField(testRecord, "グループ選択", FieldType.GROUP_SELECT, groupList);
		ArrayList<Member> orgList = new ArrayList<Member>();
		orgList.add(testorg1);
		orgList.add(testorg2);
		addField(testRecord, "組織選択", FieldType.ORGANIZATION_SELECT, orgList);
		return testRecord;
	}

	public HashMap<String, FieldValue> addField(HashMap<String, FieldValue> record, String code, FieldType type,
			Object value) {
		FieldValue newField = new FieldValue();
		newField.setType(type);
		newField.setValue(value);
		record.put(code, newField);
		return record;
	}

	@Test
	public void testGetRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();

		ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
		FileModel file1 = new FileModel();
		file1.setContentType("text/plain");
		file1.setFileKey("20180806013856AF9D0EFCA84B40CEAAB5D9882D9E4700212");
		file1.setName("RecordModuleTest.txt");
		file1.setSize("0");
		cbFileList.add(file1);
		FileModel file2 = new FileModel();
		file2.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		file2.setFileKey("20180806013856B42A4F91FEB045159B065A7BA9B01497121");
		file2.setName("RecordModuleTest.xlsx");
		file2.setSize("6577");
		cbFileList.add(file2);
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, cbFileList);

		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tableItem1 = new SubTableValueItem();
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(testman1);
		userList.add(testman2);
		addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
		tableItem1.setID(3016494);
		HashMap<String, FieldValue> tableItemValue1 = new HashMap<String, FieldValue>();
		tableItemValue1 = addField(tableItemValue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tableItemValue1 = addField(tableItemValue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableItemValue1 = addField(tableItemValue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text1");
		tableItem1.setValue(tableItemValue1);
		subTable.add(tableItem1);
		SubTableValueItem tableItem2 = new SubTableValueItem();
		tableItem2.setID(3016497);
		HashMap<String, FieldValue> tableItemValue2 = new HashMap<String, FieldValue>();
		tableItemValue2 = addField(tableItemValue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");
		tableItemValue2 = addField(tableItemValue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableItemValue2 = addField(tableItemValue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text2");
		tableItem2.setValue(tableItemValue2);
		subTable.add(tableItem2);
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1234);
		testRecord = addField(testRecord, "計算_数値", FieldType.CALC, 1234);

		ArrayList<String> categoryList = new ArrayList<String>();
		categoryList.add("テスト１－１");
		categoryList.add("テスト１");
		categoryList.add("テスト２");
		testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, categoryList);
		testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "未処理");

		ArrayList<Member> assigneeList = new ArrayList<Member>();
		assigneeList.add(testman1);
		testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, assigneeList);
		testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "lookup1");
		// Main Test processing
		Integer id = 1;
		GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> resultRecord = response.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();

		ArrayList<FileModel> cbFileList = new ArrayList<FileModel>();
		FileModel file1 = new FileModel();
		file1.setContentType("text/plain");
		file1.setFileKey("20180806013856AF9D0EFCA84B40CEAAB5D9882D9E4700212");
		file1.setName("RecordModuleTest.txt");
		file1.setSize("0");
		cbFileList.add(file1);
		FileModel file2 = new FileModel();
		file2.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		file2.setFileKey("20180806013856B42A4F91FEB045159B065A7BA9B01497121");
		file2.setName("RecordModuleTest.xlsx");
		file2.setSize("6577");
		cbFileList.add(file2);
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, cbFileList);

		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tableItem1 = new SubTableValueItem();
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(testman1);
		userList.add(testman2);
		addField(testRecord, "ユーザー選択", FieldType.USER_SELECT, userList);
		tableItem1.setID(3016494);
		HashMap<String, FieldValue> tableItemValue1 = new HashMap<String, FieldValue>();
		tableItemValue1 = addField(tableItemValue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tableItemValue1 = addField(tableItemValue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableItemValue1 = addField(tableItemValue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text1");
		tableItem1.setValue(tableItemValue1);
		subTable.add(tableItem1);
		SubTableValueItem tableItem2 = new SubTableValueItem();
		tableItem2.setID(3016497);
		HashMap<String, FieldValue> tableItemValue2 = new HashMap<String, FieldValue>();
		tableItemValue2 = addField(tableItemValue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample2");
		tableItemValue2 = addField(tableItemValue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableItemValue2 = addField(tableItemValue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "test table_text2");
		tableItem2.setValue(tableItemValue2);
		subTable.add(tableItem2);
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, 1234);
		testRecord = addField(testRecord, "計算_数値", FieldType.CALC, 1234);

		ArrayList<String> categoryList = new ArrayList<String>();
		categoryList.add("テスト１－１");
		categoryList.add("テスト１");
		categoryList.add("テスト２");
		testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, categoryList);
		testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "未処理");

		ArrayList<Member> assigneeList = new ArrayList<Member>();
		assigneeList.add(testman1);
		testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, assigneeList);
		testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "lookup1");
		// Main Test processing
		Integer id = 1;
		GetRecordResponse response = this.tokenRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> resultRecord = response.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordDefaultBlankApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "$id", FieldType.__ID__, 1);
		testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
		testRecord = addField(testRecord, "创建人", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "创建时间", FieldType.CREATED_TIME, "2018-08-22T06:30:00Z");
		testRecord = addField(testRecord, "更新人", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "更新时间", FieldType.UPDATED_TIME, "2018-08-22T06:30:00Z");
		testRecord = addField(testRecord, "记录编号", FieldType.RECORD_NUMBER, 1);

		// Main Test processing
		Integer appid = 1633;
		Integer recordId = 1;
		GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(appid, recordId);
		HashMap<String, FieldValue> resultRecord = response.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordDefaultBlankAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "$id", FieldType.__ID__, 1);
		testRecord = addField(testRecord, "$revision", FieldType.__REVISION__, 1);
		testRecord = addField(testRecord, "创建人", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "创建时间", FieldType.CREATED_TIME, "2018-08-22T06:30:00Z");
		testRecord = addField(testRecord, "更新人", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "更新时间", FieldType.UPDATED_TIME, "2018-08-22T06:30:00Z");
		testRecord = addField(testRecord, "记录编号", FieldType.RECORD_NUMBER, 1);

		// Main Test processing
		Integer appid = 1633;
		Integer recordId = 1;
		GetRecordResponse response = this.blankappapitokenRecordManagerment.getRecord(appid, recordId);
		HashMap<String, FieldValue> resultRecord = response.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordNoPermissionFieldDoNotDisplay() throws KintoneAPIException {
		Integer appid = 1635;
		Integer recordId = 1;
		GetRecordResponse response = this.passwordAuthRecordManagerment.getRecord(appid, recordId);
		HashMap<String, FieldValue> resultRecord = response.getRecord();
		assertNull(resultRecord.get("数值"));
		assertEquals(9, resultRecord.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWithoutApp() throws KintoneAPIException {
		Integer id = 1;
		this.passwordAuthRecordManagerment.getRecord(null, id);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWithoutAppToken() throws KintoneAPIException {
		Integer id = 1;
		this.tokenRecordManagerment.getRecord(null, id);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(100000, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(100000, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdNegative() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(-1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdNegativeToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(-1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(0, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordAppIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(0, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWithoutRecord() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWithoutRecordToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(APP_ID, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(APP_ID, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdNegative() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(APP_ID, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdNegativeToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(APP_ID, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(APP_ID, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordRecordIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecord(APP_ID, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWhenDoNotHavePermissionOfApp() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecord(1632, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWhenDoNotHavePermissionOfAppToken() throws KintoneAPIException {
		this.noviewpermissiontokenRecordManagerment.getRecord(1632, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordWhenDoNotHavePermissionOfRecord() throws KintoneAPIException {
		Integer appId = 1634;
		Integer recordId = 1;
		this.passwordAuthRecordManagerment.getRecord(appId, recordId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailInGuestSpace() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.passwordAuthRecordManagerment = new Record(connection);
		this.passwordAuthRecordManagerment.getRecord(APP_ID, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailInGuestSpaceToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.tokenRecordManagerment = new Record(connection);
		this.tokenRecordManagerment.getRecord(APP_ID, 1);
	}

	@Test
	public void testGetRecords() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals((Integer) 3, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals((Integer) 3, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
		}
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		this.passwordAuthRecordManagerment.getRecords(null, query, null, true);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		this.tokenRecordManagerment.getRecords(null, query, null, true);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecords(100000, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecords(100000, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdNegative() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecords(-1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdNegativeToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecords(-1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecords(0, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsAppIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecords(0, null, null, null);
	}

	@Test
	public void testGetRecordsWithoutQuery() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertTrue((Integer) 3 <= response.getTotalCount());
		assertTrue(3 <= resultRecords.size());
	}

	@Test
	public void testGetRecordsWithoutQueryToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, null, null, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertTrue((Integer) 3 <= response.getTotalCount());
		assertTrue(3 <= resultRecords.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailWhenGivenInvalidQuery() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getRecords(APP_ID, "aaa", null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailWhenGivenInvalidQueryToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getRecords(APP_ID, "aaa", null, null);
	}

	@Test
	public void testGetRecordsWithFields() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("数値");
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, fields, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals((Integer) 3, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		assertEquals(1, resultRecords.get(0).size());
		assertEquals(String.valueOf(testRecord1.get("数値").getValue()), resultRecords.get(0).get("数値").getValue());
		assertEquals(1, resultRecords.get(1).size());
		assertEquals(String.valueOf(testRecord2.get("数値").getValue()), resultRecords.get(1).get("数値").getValue());
		assertEquals(1, resultRecords.get(2).size());
		assertEquals(String.valueOf(testRecord3.get("数値").getValue()), resultRecords.get(2).get("数値").getValue());
	}

	@Test
	public void testGetRecordsWithFieldsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("数値");
		GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, fields, true);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals((Integer) 3, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		assertEquals(1, resultRecords.get(0).size());
		assertEquals(String.valueOf(testRecord1.get("数値").getValue()), resultRecords.get(0).get("数値").getValue());
		assertEquals(1, resultRecords.get(1).size());
		assertEquals(String.valueOf(testRecord2.get("数値").getValue()), resultRecords.get(1).get("数値").getValue());
		assertEquals(1, resultRecords.get(2).size());
		assertEquals(String.valueOf(testRecord3.get("数値").getValue()), resultRecords.get(2).get("数値").getValue());
	}

	@Test
	public void testGetRecordByBigBody() throws KintoneAPIException {
		ArrayList<String> fields = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			fields.add("test");
		}
		this.passwordAuthRecordManagerment.getRecords(APP_ID, null, fields, false);
	}

	@Test
	public void testGetRecordByBigBodyToken() throws KintoneAPIException {
		ArrayList<String> fields = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			fields.add("test");
		}
		this.tokenRecordManagerment.getRecords(APP_ID, null, fields, false);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailWhenGivenInvalidFields() throws KintoneAPIException {
		ArrayList<String> fields = new ArrayList<>();
		for (int i = 0; i <= 1000; i++) {
			fields.add("test");
		}
		this.passwordAuthRecordManagerment.getRecords(APP_ID, null, fields, false);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordShouldFailWhenGivenInvalidFieldsToken() throws KintoneAPIException {
		ArrayList<String> fields = new ArrayList<>();
		for (int i = 0; i <= 1000; i++) {
			fields.add("test");
		}
		this.tokenRecordManagerment.getRecords(APP_ID, null, fields, false);
	}

	@Test
	public void testGetRecordsWithoutTotal() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals(null, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordsWithoutTotalToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer lowerLimit = (Integer) testRecord1.get("数値").getValue();
		Integer upperLimit = (Integer) testRecord3.get("数値").getValue();
		String query = "数値 >=" + lowerLimit + "and 数値 <=" + upperLimit + "order by 数値 asc";
		GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals(null, response.getTotalCount());
		assertEquals(3, resultRecords.size());
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(0).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(0).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(1).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(1).get(entry.getKey()).getValue());
		}
		for (Entry<String, FieldValue> entry : testRecord3.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecords.get(2).get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecords.get(2).get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testGetRecordsWhenCountFalse() throws KintoneAPIException {
		GetRecordsResponse recordRep = this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, false);
		assertNotNull(recordRep);
		ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
		assertNotNull(records);
		assertNull(recordRep.getTotalCount());
	}

	@Test
	public void testGetRecordsWhenCountFalseToken() throws KintoneAPIException {
		GetRecordsResponse recordRep = this.tokenRecordManagerment.getRecords(APP_ID, null, null, false);
		assertNotNull(recordRep);
		ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
		assertNotNull(records);
		assertNull(recordRep.getTotalCount());
	}

	@Test
	public void testGetRecordsLimitZeroAndCountTrue() throws KintoneAPIException {
		GetRecordsResponse recordRep = this.passwordAuthRecordManagerment.getRecords(APP_ID, "limit 0", null, true);
		ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
		assertEquals(0, records.size());
		assertNotNull(recordRep.getTotalCount());
	}

	@Test
	public void testGetRecordsLimitZeroAndCountTrueToken() throws KintoneAPIException {
		GetRecordsResponse recordRep = this.tokenRecordManagerment.getRecords(APP_ID, "limit 0", null, true);
		ArrayList<HashMap<String, FieldValue>> records = recordRep.getRecords();
		assertEquals(0, records.size());
		assertNotNull(recordRep.getTotalCount());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsWhenDoNotHavePermissionOfApp() throws KintoneAPIException {
		Integer appId = 1632;
		this.passwordAuthRecordManagerment.getRecords(appId, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsWhenDoNotHavePermissionOfAppToken() throws KintoneAPIException {
		Integer appId = 1632;
		this.noviewpermissiontokenRecordManagerment.getRecords(appId, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShouldFailInGuestSpace() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.passwordAuthRecordManagerment = new Record(connection);
		this.passwordAuthRecordManagerment.getRecords(APP_ID, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShouldFailInGuestSpaceToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.tokenRecordManagerment = new Record(connection);
		this.tokenRecordManagerment.getRecords(APP_ID, null, null, null);
	}

	@Test
	public void testGetRecordsNoPermissionFieldDoNotDisplay() throws KintoneAPIException {
		Integer appid = 1635;
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(appid, null, null, null);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		for (HashMap<String, FieldValue> hashMap : resultRecords) {
			assertEquals(9, hashMap.size());
		}
	}

	@Test
	public void testGetRecordsTheTotalCountShould500() throws KintoneAPIException {
		String query = "limit 500";
		GetRecordsResponse response = this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals(500, resultRecords.size());
	}

	@Test
	public void testGetRecordsTheTotalCountShould500Token() throws KintoneAPIException {
		String query = "limit 500";
		GetRecordsResponse response = this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
		ArrayList<HashMap<String, FieldValue>> resultRecords = response.getRecords();
		assertEquals(500, resultRecords.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShouldFailThenLimitOver500() throws KintoneAPIException {
		String query = "limit 501";
		this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShouldFailThenLimitOver500Token() throws KintoneAPIException {
		String query = "limit 501";
		this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInvalidLimit() throws KintoneAPIException {
		String query = "limit -1";
		this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInvalidLimitToken() throws KintoneAPIException {
		String query = "limit -1";
		this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInvalidOffset() throws KintoneAPIException {
		String query = "offset -1";
		this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInvalidOffsetToken() throws KintoneAPIException {
		String query = "offset -1";
		this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInactiveUser() throws KintoneAPIException {
		String query = "ユーザー選択 in (\" USER\", \"xxx xxx\")";
		this.passwordAuthRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetRecordsShowFailGivenInactiveUserToken() throws KintoneAPIException {
		String query = "ユーザー選択 in (\" USER\", \"xxx xxx\")";
		this.tokenRecordManagerment.getRecords(APP_ID, query, null, null);
	}

	@Test
	public void testAddRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, testman1);
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-01T09:00:00Z");
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, testman2);
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T18:00:00Z");
		// Main Test processing
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
		GetRecordResponse getResponse = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> resultRecord = getResponse.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testAddRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, testman1);
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-01-01T09:00:00Z");
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, testman2);
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-01-02T18:00:00Z");
		// Main Test processing
		AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
		GetRecordResponse getResponse = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> resultRecord = getResponse.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), resultRecord.get(entry.getKey()).getType());
			Object expectedValue;
			if (entry.getValue().getValue() instanceof ArrayList || entry.getValue().getValue() instanceof Member) {
				expectedValue = entry.getValue().getValue();
			} else {
				expectedValue = String.valueOf(entry.getValue().getValue());
			}
			assertEquals(expectedValue, resultRecord.get(entry.getKey()).getValue());
		}
	}

	@Test
	public void testAddRecordWithoutRecord() throws KintoneAPIException {
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, null);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecordWithoutRecordToken() throws KintoneAPIException {
		AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, null);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecordWithAttachment() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		// Main Test processing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());

		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordWithAttachmentToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		// Main Test processing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);
		AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());

		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordDataWithTable() throws KintoneAPIException {
		// Preprocessing
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testAddRecordDataWithTableToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		AddRecordResponse response = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testAddRecordWithAttachmentInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "附件", FieldType.FILE, al);
		// Main Test processing
		AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());

		GetRecordResponse rp = guestRecord.getRecord(1631, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordWithAttachmentInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "附件", FieldType.FILE, al);
		// Main Test processing
		AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());

		GetRecordResponse rp = guestRecord.getRecord(1631, response.getID());
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordInGuest() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecordInGuestToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse response = guestRecord.addRecord(1631, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordWithoutApp() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(null, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordWithoutAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(null, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecord(100000, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecord(100000, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdNegative() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecord(-1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdNegativeToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecord(-1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecord(0, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordAppIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecord(0, null);
	}

	@Test
	public void testAddRecordInvalidFieldShouldSkipped() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "不存在的", FieldType.SINGLE_LINE_TEXT, 123);
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test
	public void testAddRecordInvalidFieldShouldSkippedToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "不存在的", FieldType.SINGLE_LINE_TEXT, 123);
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInputStringToNumberFieldInvalidField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInputStringToNumberFieldInvalidFieldToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.addRecord(1636, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.prohibitduplicatetokenRecordManagerment.addRecord(1636, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
		this.passwordAuthRecordManagerment.addRecord(1636, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
		this.prohibitduplicatetokenRecordManagerment.addRecord(1636, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInvalidLookupValue() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "agdagsgasdg");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailInvalidLookupValueToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "ルックアップ", FieldType.SINGLE_LINE_TEXT, "agdagsgasdg");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetCategories() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, "テスト１");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetCategoriesToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "カテゴリー", FieldType.CATEGORY, "テスト１");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetStatus() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "処理中");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetStatusToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "ステータス", FieldType.STATUS, "処理中");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetAssignee() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, new Member("user1", "user1"));
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetAssigneeToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作業者", FieldType.STATUS_ASSIGNEE, new Member("user1", "user1"));
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedCreator() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedCreatorToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedModifier() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedModifierToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedCeratedTime() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedCeratedTimeToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedUpdatedTime() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetNonexistedUpdatedTimeToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.requiredfieldtokenRecordManagerment.addRecord(1640, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetUnexistedFileKey() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWhenSetUnexistedFileKeyToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.addRecord(1632, testRecord);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.noaddpermissiontokenReocrdManagerment.addRecord(1632, testRecord);
	}

	@Test
	public void testAddRecordShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(1634, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecordShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		AddRecordResponse response = this.addnoviewtokenRecordManagerment.addRecord(1634, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordShouldFailWheDoNotHavepermissionOfField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
		this.passwordAuthRecordManagerment.addRecord(1635, testRecord);
	}

	@Test
	public void testAddRecordShouldSuccessUseBlankApp() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
		AddRecordResponse response = this.passwordAuthRecordManagerment.addRecord(1633, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecordShouldSuccessUseBlankAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
		AddRecordResponse response = this.blankappapitokenRecordManagerment.addRecord(1633, testRecord);
		assertTrue(response.getID() instanceof Integer);
		assertEquals((Integer) 1, response.getRevision());
	}

	@Test
	public void testAddRecords() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test
	public void testAddRecordsToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test
	public void testAddRecordsWithAttachment() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);

		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);

		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordsWithAttachmentToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);

		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);

		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordsDataWithTable() throws KintoneAPIException {
		// Preprocessing
		ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
		tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue1);
		subTable1.add(tablelist1);
		ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist2 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
		tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList2 = new ArrayList<Member>();
		userList2.add(new Member("cyuan", "cyuan"));
		tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
		tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist2.setID(1);
		tablelist2.setValue(tableitemvalue2);
		subTable2.add(tablelist2);
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
		GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testAddRecordsDataWithTableToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
		tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue1);
		subTable1.add(tablelist1);
		ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist2 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
		tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList2 = new ArrayList<Member>();
		userList2.add(new Member("cyuan", "cyuan"));
		tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
		tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist2.setID(1);
		tablelist2.setValue(tableitemvalue2);
		subTable2.add(tablelist2);
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
		GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testAddRecordsWithAttachmentInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		File attachmet = new File(connection);
		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "附件", FieldType.FILE, al1);
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "附件", FieldType.FILE, al2);

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = guestRecord.addRecords(1631, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = guestRecord.getRecord(1631, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = guestRecord.getRecord(1631, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordsWithAttachmentInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		File attachmet = new File(connection);
		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		FileModel file2 = attachmet.upload("src/test/resources/app/InvalidAppID.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "附件", FieldType.FILE, al1);
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "附件", FieldType.FILE, al2);

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = guestRecord.addRecords(1631, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));

		GetRecordResponse rp1 = guestRecord.getRecord(1631, response.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = guestRecord.getRecord(1631, response.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testAddRecordsInGuest() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = guestRecord.addRecords(1631, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test
	public void testAddRecordsInGuestToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		// Main Test processing
		AddRecordsResponse response = guestRecord.addRecords(1631, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecords(100000, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecords(100000, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdNegative() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecords(-1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdNegativeToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecords(-1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecords(0, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsAppIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecords(0, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsWithoutApp() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecords(null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsWithoutAppToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecords(null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsWithoutRecords() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.addRecords(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsWithoutRecordsToken() throws KintoneAPIException {
		this.tokenRecordManagerment.addRecords(APP_ID, null);
	}

	@Test
	public void testAddRecordsInvalidFieldShouldSkipped() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不存在的字符串", FieldType.SINGLE_LINE_TEXT, "123");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不存在的数值", FieldType.NUMBER, 123);
		records.add(testRecord2);

		AddRecordsResponse response = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test
	public void testAddRecordsInvalidFieldShouldSkippedToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不存在的字符串", FieldType.SINGLE_LINE_TEXT, "123");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不存在的数值", FieldType.NUMBER, 123);
		records.add(testRecord2);

		AddRecordsResponse response = this.tokenRecordManagerment.addRecords(APP_ID, records);
		assertEquals(2, response.getIDs().size());
		assertTrue(response.getIDs().get(0) instanceof Integer);
		assertTrue(response.getIDs().get(1) instanceof Integer);
		assertEquals(2, response.getRevisions().size());
		assertEquals((Integer) 1, response.getRevisions().get(0));
		assertEquals((Integer) 1, response.getRevisions().get(1));
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailInputStringToNumberField() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 123);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 123);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "aaa");
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(1636, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "aaa");
		records.add(testRecord2);
		this.prohibitduplicatetokenRecordManagerment.addRecords(1636, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(1636, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.prohibitduplicatetokenRecordManagerment.addRecords(1636, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedCreator() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedCreatorToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成者", FieldType.CREATOR, new Member("xxx", "xxx xxx"));
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedModifier() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedModifierToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "更新者", FieldType.MODIFIER, new Member("xxx", "xxx xxx"));
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedCeretedTime() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedCeretedTimeToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2019-11-11T01:46:00Z");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedUpdatedTime() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetNonexistedUpdatedTimeToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "更新日時", FieldType.UPDATED_TIME, "2019-11-11T01:46:00Z");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 8);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(1640, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetUnexistedFileKey() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWhenSetUnexistedFileKeyToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, "xxxxxxxxxxxxxxxxxxx");
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(1632, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord1);

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		records.add(testRecord2);
		this.noaddpermissiontokenReocrdManagerment.addRecords(1632, records);
	}

	@Test
	public void testAddRecordsShouldSuccessOfHundred() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(100, iDs.size());
	}

	@Test
	public void testAddRecordsShouldSuccessOfHundredToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addRecords = this.tokenRecordManagerment.addRecords(APP_ID, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(100, iDs.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailThenOverHundred() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		for (int i = 0; i <= 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailThenOverHundredToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		for (int i = 0; i <= 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		this.tokenRecordManagerment.addRecords(APP_ID, records);
	}

	@Test
	public void testAddRecordsShouldSuccessUesAdminToSetFields() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		records.add(testRecord2);
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord3);
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		records.add(testRecord4);

		AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(4, iDs.size());
	}

	@Test
	public void testAddRecordsShouldSuccessUesAdminToSetFieldsToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		records.add(testRecord2);
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord3);
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		records.add(testRecord4);

		AddRecordsResponse addRecords = this.tokenRecordManagerment.addRecords(APP_ID, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(4, iDs.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailDoNotAdminToSetFields() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		records.add(testRecord2);
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord3);
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		records.add(testRecord4);
		AddRecordsResponse addRecords = this.passwordAuthRecordManagerment.addRecords(1637, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(4, iDs.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddRecordsShouldFailDoNotAdminToSetFieldsToken() throws KintoneAPIException {
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<>();
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord1);
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		records.add(testRecord2);
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		records.add(testRecord3);
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		records.add(testRecord4);
		AddRecordsResponse addRecords = this.noadminpermissionRecordManagerment.addRecords(1637, records);
		ArrayList<Integer> iDs = addRecords.getIDs();
		assertEquals(4, iDs.size());
	}

	@Test
	public void testUpdateRecordById() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIDWithAttachment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);

		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByIDWithAttachmentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);
		testRecord = addField(testRecord, "添付ファイル", FieldType.FILE, al);

		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByIDDataWithTable() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByIDDataWithTableToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("cyuan", "cyuan"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		testRecord = addField(testRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void tesUpdateRecordByIDInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		// Main Test processing
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		UpdateRecordResponse response = guestRecord.updateRecordByID(1631, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void tesUpdateRecordByIDInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		// Main Test processing
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		UpdateRecordResponse response = guestRecord.updateRecordByID(1631, id, testRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdWithoutRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdWithoutRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdRevisionShouldFailLessThanNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdRevisionShouldFailLessThanNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailRevisionUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailRevisionUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailRevisionZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailRevisionZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIDUnexisted() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(10000, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIDUnexistedToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(10000, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIDNegativeNumber() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(-1, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(-1, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIdZero() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(0, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailAppIdZeroToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(0, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdUnexisted() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, 100000, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdUnexistedToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, 100000, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdNegativeNumber() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, -1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdNegativeNumberToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, -1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdZero() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, 0, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailIdZeroToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, 0, testRecord, null);
	}

	@Test
	public void testUpdateRecordByIdInvalidFieldWillSkip() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdInvalidFieldWillSkipToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = createTestRecord();
		testRecord = addField(testRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdWithoutRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, null, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByIdWithoutRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByID(APP_ID, id, null, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailInputStringToNumberField() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.prohibitduplicatetokenRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
		this.passwordAuthRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 11);
		this.prohibitduplicatetokenRecordManagerment.updateRecordByID(1636, 2, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, 111);
		this.passwordAuthRecordManagerment.updateRecordByID(1640, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, 111);
		this.requiredfieldtokenRecordManagerment.updateRecordByID(1640, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdChangeCreatorEtc() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdChangeCreatorEtcToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		testRecord = addField(testRecord, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord = addField(testRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		testRecord = addField(testRecord, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		this.tokenRecordManagerment.updateRecordByID(APP_ID, id, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.updateRecordByID(1632, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.noaddpermissiontokenReocrdManagerment.updateRecordByID(1632, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.addnoviewtokenRecordManagerment.updateRecordByID(1634, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "数值", FieldType.NUMBER, 123);
		this.passwordAuthRecordManagerment.updateRecordByID(1635, 1, testRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(APP_ID, null, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(APP_ID, null, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByID(null, id, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByIdWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByID(null, id, testRecord, revision);
	}

	@Test
	public void testUpdateRecordByUpdateKey() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyMultiUpdateKeys() throws KintoneAPIException {
		// 1622のアプリの二つの重複禁止のフィールドを設定している
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyMultiUpdateKeysToken() throws KintoneAPIException {
		// 1622のアプリの二つの重複禁止のフィールドを設定している
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		Integer revision = addResponse.getRevision();
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyWithAttachment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);

		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "添付ファイル", FieldType.FILE, al);
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByUpdateKeyWithAttachmentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);
		FileModel file = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al = new ArrayList<>();
		al.add(file);

		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "添付ファイル", FieldType.FILE, al);
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByUpdateKeyDataWithTable() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("xxxxx", "xxxxx"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.passwordAuthRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByUpdateKeyDataWithTableToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<SubTableValueItem> subTable = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue = new HashMap<>();
		tableitemvalue = addField(tableitemvalue, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList = new ArrayList<Member>();
		userList.add(new Member("xxxxx", "xxxxx"));
		tableitemvalue = addField(tableitemvalue, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList);
		tableitemvalue = addField(tableitemvalue, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");

		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue);
		subTable.add(tablelist1);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "サブテーブル", FieldType.SUBTABLE, subTable);
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());

		GetRecordResponse rp = this.tokenRecordManagerment.getRecord(APP_ID, id);
		HashMap<String, FieldValue> record = rp.getRecord();
		for (Entry<String, FieldValue> entry : testRecord.entrySet()) {
			assertEquals(entry.getValue().getType(), record.get(entry.getKey()).getType());
			if (FieldType.FILE == record.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordByUpdateKeyInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
		AddRecordResponse addResponse = guestRecord.addRecord(1656, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		UpdateRecordResponse response = guestRecord.updateRecordByUpdateKey(1656, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(ANOTHER_GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		testRecord = addField(testRecord, "数値", FieldType.NUMBER, new Random().nextInt());
		AddRecordResponse addResponse = guestRecord.addRecord(1656, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		UpdateRecordResponse response = guestRecord.updateRecordByUpdateKey(1656, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyInvalidFildWillSkip() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyInvalidFildWillSkipToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailLessThanNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailLessThanNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyRevisionShouldFailRevisionZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailWrongUpdatekey() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("リンク", String.valueOf(testRecord.get("リンク").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailWrongUpdatekeyToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("リンク", String.valueOf(testRecord.get("リンク").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(10000, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(10000, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDNegativeNumber() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(-1, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(-1, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(0, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailAppIDZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(0, updateKey, updateRecord, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyWithoutKey() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, null, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyWithoutKeyToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, null, testRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailInputStringToNumberField() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数値", FieldType.NUMBER, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer revision = addResponse.getRevision();
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数値", FieldType.NUMBER, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		this.prohibitduplicatetokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数值", FieldType.NUMBER, 11);
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值_0", "11");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数值", FieldType.NUMBER, 11);
		this.prohibitduplicatetokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailUpdatekeyNotUnique() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "9");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数值_0", FieldType.NUMBER, 12);
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailUpdatekeyNotUniqueToken() throws KintoneAPIException {
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "9");
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "数值_0", FieldType.NUMBER, 12);
		this.prohibitduplicatetokenRecordManagerment.updateRecordByUpdateKey(1636, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyChangeCreatorEtc() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		updateRecord = addField(updateRecord, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		updateRecord = addField(updateRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		updateRecord = addField(updateRecord, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyChangeCreatorEtcToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		updateRecord = addField(updateRecord, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		updateRecord = addField(updateRecord, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		updateRecord = addField(updateRecord, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord = new HashMap<>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord = new HashMap<>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
		this.noaddpermissiontokenReocrdManagerment.updateRecordByUpdateKey(1632, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord = new HashMap<>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord = new HashMap<>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
		this.addnoviewtokenRecordManagerment.updateRecordByUpdateKey(1634, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord = new HashMap<>();
		updateRecord = addField(updateRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		RecordUpdateKey updateKey = new RecordUpdateKey("数值", "1");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(1635, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.passwordAuthRecordManagerment.updateRecordByUpdateKey(null, updateKey, updateRecord, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordByUpdateKeyWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		HashMap<String, FieldValue> updateRecord = new HashMap<String, FieldValue>();
		updateRecord = addField(updateRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text after");
		this.tokenRecordManagerment.updateRecordByUpdateKey(null, updateKey, updateRecord, null);
	}

	@Test
	public void testUpdateRecordByUpdateKeyWithoutRecordData() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, null, null);
		assertEquals((Integer) (addResponse.getRevision() + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordByUpdateKeyWithoutRecordDataToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		RecordUpdateKey updateKey = new RecordUpdateKey("数値", String.valueOf(testRecord.get("数値").getValue()));
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordByUpdateKey(APP_ID, updateKey, null, null);
		assertEquals((Integer) (addResponse.getRevision() + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecords() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsByKey() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
		RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

		HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
		updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
		updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsByKeyToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		RecordUpdateKey updateKey1 = new RecordUpdateKey("数値", String.valueOf(testRecord1.get("数値").getValue()));
		RecordUpdateKey updateKey2 = new RecordUpdateKey("数値", String.valueOf(testRecord2.get("数値").getValue()));

		HashMap<String, FieldValue> updateRecord1 = new HashMap<String, FieldValue>();
		updateRecord1 = addField(updateRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after1");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<String, FieldValue>();
		updateRecord2 = addField(updateRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after2");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(null, null, updateKey1, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(null, null, updateKey2, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWrongRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWrongRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -2, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -2, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsChangeCreatorEtc() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		records.add(testRecord4);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
		RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
		updateItems.add(item1);
		updateItems.add(item2);
		updateItems.add(item3);
		updateItems.add(item4);
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsChangeCreatorEtcToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		HashMap<String, FieldValue> testRecord3 = createTestRecord();
		HashMap<String, FieldValue> testRecord4 = createTestRecord();
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		records.add(testRecord3);
		records.add(testRecord4);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "作成日時", FieldType.CREATED_TIME, "2018-08-28T08:07:00Z");
		testRecord2 = addField(testRecord2, "作成者", FieldType.CREATOR, new Member("xxxxx", "xxxxx"));
		testRecord3 = addField(testRecord3, "更新日時", FieldType.UPDATED_TIME, "2018-08-28T08:07:00Z");
		testRecord4 = addField(testRecord4, "更新者", FieldType.MODIFIER, new Member("xxxxx", "xxxxx"));
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		RecordUpdateItem item3 = new RecordUpdateItem(addResponse.getIDs().get(2), null, null, testRecord3);
		RecordUpdateItem item4 = new RecordUpdateItem(addResponse.getIDs().get(3), null, null, testRecord4);
		updateItems.add(item1);
		updateItems.add(item2);
		updateItems.add(item3);
		updateItems.add(item4);
		this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfApp() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
		updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
		updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1632, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWheDoNotHavepermissionOfAppToken() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
		updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
		updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.noaddpermissiontokenReocrdManagerment.updateRecords(1632, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecord() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
		updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
		updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1634, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfRecordToken() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
		updateRecord1 = addField(updateRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");
		HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
		updateRecord2 = addField(updateRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test1");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.addnoviewtokenRecordManagerment.updateRecords(1634, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldSuccessWheDoNotHavepermissionOfField() throws KintoneAPIException {
		HashMap<String, FieldValue> updateRecord1 = new HashMap<>();
		updateRecord1 = addField(updateRecord1, "数值", FieldType.NUMBER, 123);
		HashMap<String, FieldValue> updateRecord2 = new HashMap<>();
		updateRecord2 = addField(updateRecord2, "数值", FieldType.NUMBER, 123);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, updateRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, updateRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1635, updateItems);
	}

	@Test
	public void testUpdateRecordsInvalidFieldWillSkip() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsInvalidFieldWillSkipToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsWithAttachment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);

		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
		FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

		GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordsWithAttachmentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);

		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		File attachmet = new File(connection);

		FileModel file1 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al1 = new ArrayList<>();
		al1.add(file1);
		testRecord1 = addField(testRecord1, "添付ファイル", FieldType.FILE, al1);
		FileModel file2 = attachmet.upload("src/test/resources/record/ValidRecordValue.txt");
		ArrayList<FileModel> al2 = new ArrayList<>();
		al2.add(file2);
		testRecord2 = addField(testRecord2, "添付ファイル", FieldType.FILE, al2);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

		GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.FILE == record1.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record1.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
		GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(1));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.FILE == record2.get(entry.getKey()).getType()) {
				ArrayList<FileModel> alf = (ArrayList<FileModel>) record2.get(entry.getKey()).getValue();
				assertEquals(1, alf.size());
			}
		}
	}

	@Test
	public void testUpdateRecordsDataWithTable() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
		tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList1 = new ArrayList<Member>();
		userList1.add(new Member("cyuan", "cyuan"));
		tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
		tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue1);
		subTable1.add(tablelist1);

		ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist2 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
		tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList2 = new ArrayList<Member>();
		userList2.add(new Member("cyuan", "cyuan"));
		tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
		tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tablelist2.setID(1);
		tablelist2.setValue(tableitemvalue2);
		subTable2.add(tablelist2);
		// Main Test processing
		testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
		testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);

		GetRecordResponse rp1 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
		GetRecordResponse rp2 = this.passwordAuthRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void testUpdateRecordsDataWithTableToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		HashMap<String, FieldValue> testRecord2 = createTestRecord();

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		ArrayList<SubTableValueItem> subTable1 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist1 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue1 = new HashMap<>();
		tableitemvalue1 = addField(tableitemvalue1, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList1 = new ArrayList<Member>();
		userList1.add(new Member("cyuan", "cyuan"));
		tableitemvalue1 = addField(tableitemvalue1, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList1);
		tableitemvalue1 = addField(tableitemvalue1, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tablelist1.setID(1);
		tablelist1.setValue(tableitemvalue1);
		subTable1.add(tablelist1);

		ArrayList<SubTableValueItem> subTable2 = new ArrayList<SubTableValueItem>();
		SubTableValueItem tablelist2 = new SubTableValueItem();

		HashMap<String, FieldValue> tableitemvalue2 = new HashMap<>();
		tableitemvalue2 = addField(tableitemvalue2, "文字列__1行_テーブル", FieldType.SINGLE_LINE_TEXT, "文字列__1行inテーブル");
		ArrayList<Member> userList2 = new ArrayList<Member>();
		userList2.add(new Member("cyuan", "cyuan"));
		tableitemvalue2 = addField(tableitemvalue2, "ユーザー選択_テーブル", FieldType.USER_SELECT, userList2);
		tableitemvalue2 = addField(tableitemvalue2, "ドロップダウン_テーブル", FieldType.DROP_DOWN, "sample1");
		tablelist2.setID(1);
		tablelist2.setValue(tableitemvalue2);
		subTable2.add(tablelist2);
		// Main Test processing
		testRecord1 = addField(testRecord1, "サブテーブル", FieldType.SUBTABLE, subTable1);
		testRecord2 = addField(testRecord2, "サブテーブル", FieldType.SUBTABLE, subTable2);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);

		GetRecordResponse rp1 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record1 = rp1.getRecord();
		for (Entry<String, FieldValue> entry : testRecord1.entrySet()) {
			assertEquals(entry.getValue().getType(), record1.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record1.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record1.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
		GetRecordResponse rp2 = this.tokenRecordManagerment.getRecord(APP_ID, addResponse.getIDs().get(0));
		HashMap<String, FieldValue> record2 = rp2.getRecord();
		for (Entry<String, FieldValue> entry : testRecord2.entrySet()) {
			assertEquals(entry.getValue().getType(), record2.get(entry.getKey()).getType());
			if (FieldType.SUBTABLE == record2.get(entry.getKey()).getType()) {
				ArrayList<SubTableValueItem> al = (ArrayList<SubTableValueItem>) record2.get(entry.getKey()).getValue();
				assertEquals(1, al.size());
			}
		}
	}

	@Test
	public void tesUpdateRecordsInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = guestRecord.updateRecords(1631, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test
	public void tesUpdateRecordsInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest_文字列__1行__更新");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		UpdateRecordsResponse response = guestRecord.updateRecords(1631, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 1), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 1), results.get(1).getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailInputStringToNumberField() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailInputStringToNumberFieldToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "不在在的字段", FieldType.SINGLE_LINE_TEXT, "test single text after");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, "test single text after");
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, "test single text after");

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailFieldProhibitDuplicate() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailFieldProhibitDuplicateToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");

		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.prohibitduplicatetokenRecordManagerment.updateRecords(1636, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsdShouldFailInvalidValueOverMaximum() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1636, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsdShouldFailInvalidValueOverMaximumToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "数值", FieldType.NUMBER, 11);
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "数值", FieldType.NUMBER, 11);

		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.prohibitduplicatetokenRecordManagerment.updateRecords(1636, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWhenDoNotSetRequiredField() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		// Main Test processing
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(1640, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailWhenDoNotSetRequiredFieldToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		// Main Test processing
		testRecord1 = addField(testRecord1, "数値", FieldType.NUMBER, 111);
		testRecord2 = addField(testRecord2, "数値", FieldType.NUMBER, 111);
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(1, null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(2, null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.requiredfieldtokenRecordManagerment.updateRecords(1640, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(100000, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(100000, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDNegativeNumber() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(-1, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDNegativeNumberToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(-1, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(0, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsShouldFailAppIDZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), -1, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), -1, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(0, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsWithoutItems() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.passwordAuthRecordManagerment.updateRecords(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsWithoutItemsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.tokenRecordManagerment.updateRecords(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecords(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1 after");
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2 after");
		ArrayList<RecordUpdateItem> updateItems = new ArrayList<RecordUpdateItem>();
		RecordUpdateItem item1 = new RecordUpdateItem(addResponse.getIDs().get(0), null, null, testRecord1);
		RecordUpdateItem item2 = new RecordUpdateItem(addResponse.getIDs().get(1), null, null, testRecord2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecords(null, updateItems);
	}

	@Test
	public void testDeleteRecords() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test
	public void testDeleteRecordsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test
	public void testDeleteRecordsOnlyOneId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test
	public void testDeleteRecordsOnlyOneIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutIds() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutIdsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.tokenRecordManagerment.deleteRecords(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsShouldFailNotHaveDeletePermission() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1658, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.passwordAuthRecordManagerment.deleteRecords(1658, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsShouldFailNotHaveDeletePermissionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.nodeletepermissionRecordManagerment.addRecords(1658, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.nodeletepermissionRecordManagerment.deleteRecords(1658, ids);
	}

	@Test
	public void testDeleteRecordsSuccessNotHaveEditPermission() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1659, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.passwordAuthRecordManagerment.deleteRecords(1659, ids);
	}

	@Test
	public void testDeleteRecordsSuccessNotHaveEditPermissionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.noeditpermissionRecordManagerment.addRecords(1659, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.noeditpermissionRecordManagerment.deleteRecords(1659, ids);
	}

	@Test
	public void testDeleteRecordsSuccessNotHaveViewEditPermissionOfField() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);

		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1635, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getIDs().get(0));
		ids.add(addResponse.getIDs().get(1));
		this.passwordAuthRecordManagerment.deleteRecords(1635, ids);
	}

	@Test
	public void tesDeleteRecordByIDInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);

		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getID());
		guestRecord.deleteRecords(1631, ids);
	}

	@Test
	public void tesDeleteRecordByIDInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(addResponse.getID());
		guestRecord.deleteRecords(1631, ids);
	}

	@Test
	public void testDeleteRecordsSuccessIDsIsHundred() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < addResponse.getIDs().size(); i++) {
			ids.add(addResponse.getIDs().get(i));
		}
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test
	public void testDeleteRecordsSuccessIDsIsHundredToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < addResponse.getIDs().size(); i++) {
			ids.add(addResponse.getIDs().get(i));
		}
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsSuccessIDsOverHundred() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i <= 100; i++) {
			ids.add(i);
		}
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsSuccessIDsOverHundredToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i <= 100; i++) {
			ids.add(i);
		}
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdUnexisted() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.passwordAuthRecordManagerment.deleteRecords(10000, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdUnexistedToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.tokenRecordManagerment.deleteRecords(10000, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdNegativeNumber() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.passwordAuthRecordManagerment.deleteRecords(-1, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdNegativeNumberToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.tokenRecordManagerment.deleteRecords(-1, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdZero() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.passwordAuthRecordManagerment.deleteRecords(0, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsAppIdZeroToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		this.tokenRecordManagerment.deleteRecords(0, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutApp() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		this.passwordAuthRecordManagerment.deleteRecords(null, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutAppToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		this.tokenRecordManagerment.deleteRecords(null, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsUnexistedIds() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(100000);
		ids.add(200000);
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsUnexistedIdsToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(100000);
		ids.add(200000);
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsNegativeNumbertIds() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(-1);
		ids.add(-2);
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsNegativeNumbertIdsToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(-1);
		ids.add(-2);
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsZeroIds() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(0);
		this.passwordAuthRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsZeroIdsToken() throws KintoneAPIException {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(0);
		this.tokenRecordManagerment.deleteRecords(APP_ID, ids);
	}

	@Test
	public void testDeleteRecordsWithRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test
	public void testDeleteRecordsWithRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionRecordIdNotExisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(111111111, addResponse.getRevisions().get(1));
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionRecordIdNotExistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(111111111, addResponse.getRevisions().get(1));
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test
	public void testDeleteRecordsWithRevisionWhenRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), -1);
		idsWithRevision.put(addResponse.getIDs().get(1), -1);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test
	public void testDeleteRecordsWithRevisionWhenRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), -1);
		idsWithRevision.put(addResponse.getIDs().get(1), -1);
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test
	public void tesDeleteRecordWithRevisionInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), -1);
		idsWithRevision.put(addResponse.getIDs().get(1), -1);
		guestRecord.deleteRecordsWithRevision(1631, idsWithRevision);
	}

	@Test
	public void tesDeleteRecordWithRevisionInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), -1);
		idsWithRevision.put(addResponse.getIDs().get(1), -1);
		guestRecord.deleteRecordsWithRevision(1631, idsWithRevision);
	}

	@Test
	public void tesDeleteRecordWithRevisionSuccessIDsIsHundred() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		for (int i = 0; i < addResponse.getIDs().size(); i++) {
			idsWithRevision.put(addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
		}
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test
	public void tesDeleteRecordWithRevisionSuccessIDsIsHundredToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		for (int i = 0; i < addResponse.getIDs().size(); i++) {
			idsWithRevision.put(addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
		}
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordWithRevisionSuccessIDsOverHundred() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		for (int i = 0; i <= 100; i++) {
			idsWithRevision.put(i, i);
		}
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordWithRevisionSuccessIDsOverHundredToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		for (int i = 0; i <= 100; i++) {
			idsWithRevision.put(i, i);
		}
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionShouldFailNotHaveDeletePermission() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1658, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(1658, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionShouldFailNotHaveDeletePermissionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.nodeletepermissionRecordManagerment.addRecords(1658, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
		this.nodeletepermissionRecordManagerment.deleteRecordsWithRevision(1658, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsSuccessNotHaveDeletePermissionOfRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1634, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(1634, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsSuccessNotHaveDeletePermissionOfRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.addnoviewtokenRecordManagerment.addRecords(1634, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(0));
		this.addnoviewtokenRecordManagerment.deleteRecordsWithRevision(1634, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdUnexisted() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(10000, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdUnexistedToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.tokenRecordManagerment.deleteRecordsWithRevision(10000, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdNegativeNumber() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(-1, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdNegativeNumberToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.tokenRecordManagerment.deleteRecordsWithRevision(-1, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdZero() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(0, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionAppIdZeroToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1);
		idsWithRevision.put(2, 1);
		this.tokenRecordManagerment.deleteRecordsWithRevision(0, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionUnexistedRevision() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1000);
		idsWithRevision.put(2, 1000);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionUnexistedRevisionToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 1000);
		idsWithRevision.put(2, 1000);
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionNegativeNumberRevision() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, -2);
		idsWithRevision.put(2, -3);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionNegativeNumberRevisionToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, -2);
		idsWithRevision.put(2, -3);
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionZeroRevision() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 0);
		idsWithRevision.put(2, 0);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionZeroRevisionToken() throws KintoneAPIException {
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(1, 0);
		idsWithRevision.put(2, 0);
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionRecordIdDuplicate() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addresponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord1);
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addresponse.getID(), addresponse.getRevision());
		idsWithRevision.put(addresponse.getID(), addresponse.getRevision() + 1);
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionRecordIdDuplicateToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addresponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord1);
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addresponse.getID(), addresponse.getRevision());
		idsWithRevision.put(addresponse.getID(), addresponse.getRevision() + 1);
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> hm = new HashMap<>();
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, hm);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithoutRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> hm = new HashMap<>();
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, hm);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithNullRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithNullRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.tokenRecordManagerment.deleteRecordsWithRevision(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(1));
		this.passwordAuthRecordManagerment.deleteRecordsWithRevision(null, idsWithRevision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteRecordsWithRevisionWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		HashMap<Integer, Integer> idsWithRevision = new HashMap<Integer, Integer>();
		idsWithRevision.put(addResponse.getIDs().get(0), addResponse.getRevisions().get(0));
		idsWithRevision.put(addResponse.getIDs().get(1), addResponse.getRevisions().get(1));
		this.tokenRecordManagerment.deleteRecordsWithRevision(null, idsWithRevision);
	}

	@Test
	public void testUpdateRecordAssignees() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	//users "user1 - user100"need to be added to domain
	@Test
	public void testUpdateRecordAssigneesShouldSuccessAddHundredAssignees() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
		ArrayList<String> assignees = new ArrayList<String>();
		for (int i = 1; i < 101; i++) {
			assignees.add("user" + i);
		}
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 3), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesShouldSuccessAddHundredAssigneesToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
		ArrayList<String> assignees = new ArrayList<String>();
		for (int i = 1; i < 101; i++) {
			assignees.add("user" + i);
		}
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 3), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesShouldFailAddOverHundredAssignees() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);

		ArrayList<String> assignees = new ArrayList<String>();
		for (int i = 1; i <= 101; i++) {
			assignees.add("user" + i);
		}
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 3), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesShouldFailAddOverHundredAssigneesToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);

		ArrayList<String> assignees = new ArrayList<String>();
		for (int i = 1; i <= 101; i++) {
			assignees.add("user" + i);
		}
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 3), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldFailThanMultiAssignees() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		assignees.add(testman2.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldFailThanMultiAssigneesToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		assignees.add(testman2.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, revision);
	}

	@Test
	public void testUpdateRecordAssigneesSuccessRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesSuccessRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -1);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesWithoutRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesWithoutRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldFailWhenNotHasManageAppPermissionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.nomanagepermissionRecordManagerment.addRecord(1667, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.nomanagepermissionRecordManagerment.updateRecordAssignees(1667, id, assignees, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 111111);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 111111);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionNegativeTwo() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionNegativeTwoToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, -2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesRevisionZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWrongUserCode() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("aaaaaaaaaaaaaaaaaaa");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWrongUserCodeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("aaaaaaaaaaaaaaaaaaa");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesUserInactive() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("Brian");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesUserInactiveToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("Brian");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesUserIsDeleted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("Duc");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesUserIsDeletedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("Duc");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
	}

	@Test
	public void testUpdateRecordAssigneesUserDuplicate() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesUserDuplicateToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		assignees.add(testman1.getCode());
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		UpdateRecordResponse response = guestRecord.updateRecordAssignees(1631, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test
	public void testUpdateRecordAssigneesInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		UpdateRecordResponse response = guestRecord.updateRecordAssignees(1631, id, assignees, null);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesProcessOFF() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(1640, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesProcessOFFToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.requiredfieldtokenRecordManagerment.updateRecordAssignees(1640, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldFailNotHavePermissionApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1658, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(1658, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldFailNotHavePermissionAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.nodeletepermissionRecordManagerment.addRecord(1658, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.nodeletepermissionRecordManagerment.updateRecordAssignees(1658, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldSuccessWhenNotHavePermissionId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1659, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(1659, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesShouldSuccessWhenNotHavePermissionIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.noeditpermissionRecordManagerment.addRecord(1659, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.noeditpermissionRecordManagerment.updateRecordAssignees(1659, id, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdUnexisted() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(100000, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdUnexistedToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(100000, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdNegativeNumber() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(-1, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdNegativeNumberToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(-1, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdZero() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(0, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesAppIdZeroToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(0, 1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdUnexisted() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, 100000, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdUnexistedToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, 100000, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdNegativeNumber() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, -1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdNegativeNumberToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, -1, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdZero() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, 0, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIdZeroToken() throws KintoneAPIException {
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add("xxxxx");
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, 0, assignees, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIWithoutAssignees() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, id, null, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesIWithoutAssigneesToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordAssignees(APP_ID, id, null, revision);
		assertEquals((Integer) (revision + 1), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(APP_ID, null, assignees, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(APP_ID, null, assignees, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.passwordAuthRecordManagerment.updateRecordAssignees(null, id, assignees, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordAssigneesWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		ArrayList<String> assignees = new ArrayList<String>();
		assignees.add(testman1.getCode());
		this.tokenRecordManagerment.updateRecordAssignees(null, id, assignees, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusOnlyAction() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusOnlyActionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusActionPlusAssignee() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusActionPlusAssigneeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusLocalLanguage() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1661, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = "xxxxx";
		String action = "しょりかいし";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(1661, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusLocalLanguageToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.locallanguageRecordManagerment.addRecord(1661, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = "xxxxx";
		String action = "しょりかいし";
		UpdateRecordResponse response = this.locallanguageRecordManagerment.updateRecordStatus(1661, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusDoNotSetAssignee() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1662, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(1662, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusDoNotSetAssigneeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.nosetassigneeRcordManagerment.addRecord(1662, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		UpdateRecordResponse response = this.nosetassigneeRcordManagerment.updateRecordStatus(1662, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusCurrentUserToChangeStatus() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());

		Auth passwordAuth = new Auth();
		passwordAuth.setPasswordAuth("user1", "user1");
		Connection connection = new Connection(TestConstants.DOMAIN, passwordAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record record = new Record(connection);
		String action2 = "完了する";
		UpdateRecordResponse response1 = record.updateRecordStatus(APP_ID, id, action2, null, revision+2);
		assertEquals((Integer) (revision + 4), response1.getRevision());
	}

	@Test
	public void testUpdateRecordStatusCurrentUserToChangeStatusToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());

		Auth passwordAuth = new Auth();
		passwordAuth.setPasswordAuth("user1", "user1");
		Connection connection = new Connection(TestConstants.DOMAIN, passwordAuth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record record = new Record(connection);
		String action2 = "完了する";
		UpdateRecordResponse response1 = record.updateRecordStatus(APP_ID, id, action2, null, revision+2);
		assertEquals((Integer) (revision + 4), response1.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedAssignee() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = "aaaaaaaaaaa";
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedAssigneeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = "aaaaaaaaaaa";
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, revision);
	}

	@Test
	public void testUpdateRecordStatusRevisionNegativeOne() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -1);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusRevisionNegativeOneToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		UpdateRecordResponse response = this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, -1);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWhenComplete() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5665, action, assignee, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWhenCompleteToken() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5665, action, assignee, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusOnlyAssigneeCanChage() throws KintoneAPIException {
		String action = "完了する";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5662, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusOnlyAssigneeCanChageToken() throws KintoneAPIException {
		String action = "完了する";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5662, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutAssignee() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutAssigneeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, null, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutAction() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, null, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutActionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, null, assignee, revision);
	}

	@Test
	public void testUpdateRecordStatusWithoutRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		String action = "処理開始";
		String assignee = testman1.getCode();
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, null);
	}

	@Test
	public void testUpdateRecordStatusWithoutRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		String action = "処理開始";
		String assignee = testman1.getCode();
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, null, action, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, null, action, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(null, id, action, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(null, id, action, assignee, revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAlreadyHasAssignee() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, 5664, action, assignee, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAlreadyHasAssigneeToken() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, 5664, action, assignee, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusNotHavePermissionApp() throws KintoneAPIException {
		String action = "开始处理";
		this.passwordAuthRecordManagerment.updateRecordStatus(1632, 1, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusNotHavePermissionAppToken() throws KintoneAPIException {
		String action = "开始处理";
		this.noaddpermissiontokenReocrdManagerment.updateRecordStatus(1632, 1, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusNotHavePermissionRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1634, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		String action = "开始处理";
		this.passwordAuthRecordManagerment.updateRecordStatus(1634, id, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusNotHavePermissionRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.addnoviewtokenRecordManagerment.addRecord(1634, testRecord1);
		// Main Test processing
		Integer id = addResponse.getID();
		String action = "开始处理";
		this.addnoviewtokenRecordManagerment.updateRecordStatus(1634, id, action, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedAppId() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(100000, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedAppIdToken() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(100000, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAppIdNegativeOne() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(-1, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAppIdNegativeOneToken() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(-1, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAppIdZreo() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(0, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusAppIdZreoToken() throws KintoneAPIException {
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(0, 1, action, assignee,null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedRevision() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,-2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedRevisionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		String assignee = testman1.getCode();
		String action = "処理開始";
		this.tokenRecordManagerment.updateRecordStatus(APP_ID, id, action, assignee,-2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedAction() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1662, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理1開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(1662, id, action, assignee,revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusUnexistedActionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.nosetassigneeRcordManagerment.addRecord(1662, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String assignee = testman1.getCode();
		String action = "処理1開始";
		this.nosetassigneeRcordManagerment.updateRecordStatus(1662, id, action, assignee,revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusProcessOff() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "文字列1行");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1658, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.passwordAuthRecordManagerment.updateRecordStatus(1658, id, action, null,revision);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordStatusProcessOffToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "文字列1行");
		AddRecordResponse addResponse = this.nodeletepermissionRecordManagerment.addRecord(1658, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "処理開始";
		this.nodeletepermissionRecordManagerment.updateRecordStatus(1658, id, action, null,revision);
	}

	@Test
	public void testUpdateRecordStatusInGuest() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "开始处理";
		UpdateRecordResponse response = guestRecord.updateRecordStatus(1631, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordStatusInGuestToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		Integer revision = addResponse.getRevision();
		String action = "开始处理";
		UpdateRecordResponse response = guestRecord.updateRecordStatus(1631, id, action, null, revision);
		assertEquals((Integer) (revision + 2), response.getRevision());
	}

	@Test
	public void testUpdateRecordsStatus() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);

		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsStatusToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);

		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsStatusInGuest() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);

		UpdateRecordsResponse response = guestRecord.updateRecordsStatus(1631, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsStatusInGuestToken() throws KintoneAPIException {
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = guestRecord.addRecords(1631, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);

		UpdateRecordsResponse response = guestRecord.updateRecordsStatus(1631, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(2, results.size());
		assertEquals(addResponse.getIDs().get(0), results.get(0).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(0) + 2), results.get(0).getRevision());
		assertEquals(addResponse.getIDs().get(1), results.get(1).getID());
		assertEquals((Integer) (addResponse.getRevisions().get(1) + 2), results.get(1).getRevision());
	}

	@Test
	public void testUpdateRecordsStatusHundred() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		for (int i = 0; i < 100; i++) {
			RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(), addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
			updateItems.add(item);
		}
		UpdateRecordsResponse response = this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(100, results.size());
	}

	@Test
	public void testUpdateRecordsStatusHundredToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i < 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		for (int i = 0; i < 100; i++) {
			RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(), addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
			updateItems.add(item);
		}
		UpdateRecordsResponse response = this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
		ArrayList<RecordUpdateResponseItem> results = response.getRecords();
		assertEquals(100, results.size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusOverHundred() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i <= 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		for (int i = 0; i <= 100; i++) {
			RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(), addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
			updateItems.add(item);
		}
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusOverHundredToken() throws KintoneAPIException {
		// Preprocessing
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		for (int i = 0; i <= 100; i++) {
			HashMap<String, FieldValue> testRecord = createTestRecord();
			testRecord = addField(testRecord, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
			records.add(testRecord);
		}
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		for (int i = 0; i <= 100; i++) {
			RecordUpdateStatusItem item = new RecordUpdateStatusItem("処理開始", testman1.getCode(), addResponse.getIDs().get(i), addResponse.getRevisions().get(i));
			updateItems.add(item);
		}
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusProcessOff() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1658, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(1658, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusProcessOffToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.nodeletepermissionRecordManagerment.addRecords(1658, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.nodeletepermissionRecordManagerment.updateRecordsStatus(1658, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusNotHavePermissionApp() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, 1, -1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, 2, -1);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(1658, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusNotHavePermissionAppToken() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, 1, -1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, 2, -1);
		updateItems.add(item1);
		updateItems.add(item2);
		this.nodeletepermissionRecordManagerment.updateRecordsStatus(1658, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusNotHavePermissionRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(1634, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(1634, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusNotHavePermissionRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = new HashMap<>();
		testRecord1 = addField(testRecord1, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		HashMap<String, FieldValue> testRecord2 = new HashMap<>();
		testRecord2 = addField(testRecord2, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.addnoviewtokenRecordManagerment.addRecords(1634, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("开始处理", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("开始处理", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.addnoviewtokenRecordManagerment.updateRecordsStatus(1634, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongActionNameBlank() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongActionNameBlankToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongActionNameInvalid() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("aaa", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongActionNameInvalidToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("aaa", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAssigneeInvalid() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "ssssssssssssss", id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAssigneeInvalidToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "ssssssssssssss", id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongIDInvalid() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), -1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongIDInvalidToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), -1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongIDUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 100000, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongIDUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 100000, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongRevisionInvalid() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, -2);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongRevisionInvalidToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, -2);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongRevisionUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, 100000);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongRevisionUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, 100000);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithInactiveUser() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "xxxxx", id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithInactiveUserToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", "xxxxx", id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdUnexisted() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(10000, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdUnexistedToken() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(10000, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdNegativeNumber() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(-1, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdNegativeNumberToken() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(-1, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdZero() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(0, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithWrongAppIdZeroToken() throws KintoneAPIException {
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 1, null);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), 2, null);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(0, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutItems() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.passwordAuthRecordManagerment.updateRecordsStatus(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutItemsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		this.tokenRecordManagerment.updateRecordsStatus(APP_ID, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutRecord() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutRecordToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", testman1.getCode(), null, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutAssignee() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.passwordAuthRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.passwordAuthRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test(expected = KintoneAPIException.class)
	public void testUpdateRecordsStatusWithoutAssigneeToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord1 = createTestRecord();
		testRecord1 = addField(testRecord1, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		HashMap<String, FieldValue> testRecord2 = createTestRecord();
		testRecord2 = addField(testRecord2, "文字列__1行", FieldType.SINGLE_LINE_TEXT, "test single text 2");
		ArrayList<HashMap<String, FieldValue>> records = new ArrayList<HashMap<String, FieldValue>>();
		records.add(testRecord1);
		records.add(testRecord2);
		AddRecordsResponse addResponse = this.tokenRecordManagerment.addRecords(APP_ID, records);
		// Main Test processing
		Integer id1 = addResponse.getIDs().get(0);
		Integer revision1 = addResponse.getRevisions().get(0);
		Integer id2 = addResponse.getIDs().get(1);
		Integer revision2 = addResponse.getRevisions().get(1);
		ArrayList<RecordUpdateStatusItem> updateItems = new ArrayList<RecordUpdateStatusItem>();
		RecordUpdateStatusItem item1 = new RecordUpdateStatusItem("処理開始", null, id1, revision1);
		RecordUpdateStatusItem item2 = new RecordUpdateStatusItem("処理開始", null, id2, revision2);
		updateItems.add(item1);
		updateItems.add(item2);
		this.tokenRecordManagerment.updateRecordsStatus(null, updateItems);
	}

	@Test
	public void testAddComment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse response = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(response.getId());
	}

	@Test
	public void testAddCommentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse response = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(response.getId());
	}

	@Test
	public void testAddCommentsInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse response = guestRecord.addComment(1631, id, comment);
		assertNotNull(response.getId());
	}

	@Test
	public void testAddCommentsInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse response = guestRecord.addComment(1631, id, comment);
		assertNotNull(response.getId());
	}

	@Test
	public void testAddCommentAndCheckComment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());

		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testman2.getName() + " \ntest comment ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentAndCheckCommentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());

		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
		assertEquals(testman2.getName() + " \ntest comment ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithGroup() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();

		CommentMention groupMention = new CommentMention();
		groupMention.setCode(testgroup1.getCode());
		groupMention.setType("GROUP");
		mentionList.add(groupMention);
		comment.setText("test comment group");
		comment.setMentions(mentionList);

		AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testgroup1.getName() + " \ntest comment group ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithGroupToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();

		CommentMention groupMention = new CommentMention();
		groupMention.setCode(testgroup1.getCode());
		groupMention.setType("GROUP");
		mentionList.add(groupMention);
		comment.setText("test comment group");
		comment.setMentions(mentionList);

		AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testgroup1.getName() + " \ntest comment group ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithOrganization() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention orgMention = new CommentMention();
		orgMention.setCode(testorg1.getCode());
		orgMention.setType("ORGANIZATION");
		mentionList.add(orgMention);
		comment.setText("test comment organization");
		comment.setMentions(mentionList);

		AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testorg1.getName() + " \ntest comment organization ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithOrganizationToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention orgMention = new CommentMention();
		orgMention.setCode(testorg1.getCode());
		orgMention.setType("ORGANIZATION");
		mentionList.add(orgMention);
		comment.setText("test comment organization");
		comment.setMentions(mentionList);

		AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testorg1.getName() + " \ntest comment organization ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithoutMetion() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("test comment no metion");

		AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("test comment no metion ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentWithoutMetionToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("test comment no metion");

		AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("test comment no metion ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentSpecialCharacter() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("テスト");
		AddCommentResponse addCommentResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());

		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("テスト ", response.getComments().get(0).getText());
	}

	@Test
	public void testAddCommentSpecialCharacterToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("テスト");
		AddCommentResponse addCommentResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		assertNotNull(addCommentResponse.getId());

		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(1, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("テスト ", response.getComments().get(0).getText());
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentBlank() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentBlankToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentNull() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentNullToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentMaxCharacter() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= 65535; i++) {
			sb.append("a");
		}
		comment.setText(sb.toString());
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentMaxCharacterToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= 65535; i++) {
			sb.append("a");
		}
		comment.setText(sb.toString());
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedUser() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedUserToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentMetionOverTen() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		for (int i = 0; i <= 10; i++) {
			CommentMention mention = new CommentMention();
			mention.setCode(testman1.getCode());
			mention.setType("USER");
			mentionList.add(mention);
		}
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentMetionOverTenToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		for (int i = 0; i <= 10; i++) {
			CommentMention mention = new CommentMention();
			mention.setCode(testman1.getCode());
			mention.setType("USER");
			mentionList.add(mention);
		}
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedGroup() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("GROUP");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedGroupToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("GROUP");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedOrganization() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("ORGANIZATION");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentUnexistedOrganizationToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("aaaaaaaaaaaa");
		mention.setType("ORGANIZATION");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(null, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(null, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppUnexisted() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(100000, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppUnexistedToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(100000, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppNegativeNumber() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(-1, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppNegativeNumberToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(-1, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppZero() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(0, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentAppZeroToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(0, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, null, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, null, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordUnexisted() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(APP_ID, 100000, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordUnexistedToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(APP_ID, 100000, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordNegativeNumber() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(APP_ID, -1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordNegativeNumberToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(APP_ID, -1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordZero() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.passwordAuthRecordManagerment.addComment(APP_ID, 0, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentRecordZeroToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("test comment");
		this.tokenRecordManagerment.addComment(APP_ID, 0, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutComment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentWithoutCommentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		this.tokenRecordManagerment.addComment(APP_ID, id, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentDoNotHavePermissionApp() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("xxxxx");
		this.passwordAuthRecordManagerment.addComment(1632, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentDoNotHavePermissionAppToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("xxxxx");
		this.noviewpermissiontokenRecordManagerment.addComment(1632, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentDoNotHavePermissionRecord() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("xxxxx");
		this.passwordAuthRecordManagerment.addComment(1634, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentDoNotHavePermissionRecordToken() throws KintoneAPIException {
		CommentContent comment = new CommentContent();
		comment.setText("xxxxx");
		this.addnoviewtokenRecordManagerment.addComment(1634, 1, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentCommentOff() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("yrdy");
		this.passwordAuthRecordManagerment.addComment(1640, id, comment);
	}

	@Test(expected = KintoneAPIException.class)
	public void testAddCommentCommentOffToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.requiredfieldtokenRecordManagerment.addRecord(1640, testRecord);
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		comment.setText("xxxxx");
		this.requiredfieldtokenRecordManagerment.addComment(1640, id, comment);
	}

	@Test
	public void testGetComments() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", 1, 2);
		assertEquals(2, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testman2.getName() + " \ntest comment2 ", response.getComments().get(0).getText());
		assertEquals(testAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertTrue(response.getOlder());
		assertTrue(response.getNewer());
	}

	@Test
	public void testGetCommentsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", 1, 2);
		assertEquals(2, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testman2.getName() + " \ntest comment2 ", response.getComments().get(0).getText());
		assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertTrue(response.getOlder());
		assertTrue(response.getNewer());
	}

	@Test
	public void testGetCommentsInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment2");
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment3");
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment4");
		guestRecord.addComment(1631, id, comment);
		// Main Test processing
		GetCommentsResponse response = guestRecord.getComments(1631, id, "asc", 1, 2);
		assertEquals(2, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("xxxxx" + " \ntest comment2 ", response.getComments().get(0).getText());
		assertEquals(testAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertTrue(response.getOlder());
		assertTrue(response.getNewer());
	}

	@Test
	public void testGetCommentsInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");
		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment2");
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment3");
		guestRecord.addComment(1631, id, comment);
		comment.setText("test comment4");
		guestRecord.addComment(1631, id, comment);
		// Main Test processing
		GetCommentsResponse response = guestRecord.getComments(1631, id, "asc", 1, 2);
		assertEquals(2, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals("xxxxx" + " \ntest comment2 ", response.getComments().get(0).getText());
		assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertTrue(response.getOlder());
		assertTrue(response.getNewer());
	}

	@Test
	public void testGetCommentsAsc() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", null, null);
		assertEquals(Integer.valueOf(1),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsAscToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", null, null);
		assertEquals(Integer.valueOf(1),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsDesc() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "desc", null, null);
		assertEquals(Integer.valueOf(2),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsDescToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "desc", null, null);
		assertEquals(Integer.valueOf(2),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsSetOffset() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 6; i++) {
			this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, 5, null);
		assertEquals(1, response.getComments().size());
		assertEquals(Integer.valueOf(1),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsSetOffsetToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 6; i++) {
			this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, 5, null);
		assertEquals(1, response.getComments().size());
		assertEquals(Integer.valueOf(1),response.getComments().get(0).getId());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsOffsetLessThanZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, -1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsOffsetLessThanZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.tokenRecordManagerment.getComments(APP_ID, id, null, -1, null);
	}

	@Test
	public void testGetCommentsSetLimit() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 6; i++) {
			this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(6),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsSetLimitToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 6; i++) {
			this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(6),response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsSetLimitNoComment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 5);
		assertEquals(1, response.getComments().size());
	}

	@Test
	public void testGetCommentsSetLimitNoCommentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 5);
		assertEquals(1, response.getComments().size());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsSetLimitOverTen() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, 11);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsSetLimitOverTenToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.tokenRecordManagerment.getComments(APP_ID, id, null, null, 11);
	}

	@Test
	public void testGetCommentsAscOfflitLimit() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 10; i++) {
			this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "asc", 5, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsAscOfflitLimitToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 10; i++) {
			this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "asc", 5, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(6), response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsDescOffsetLimit() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 10; i++) {
			this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, "desc", 5, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(5), response.getComments().get(0).getId());
	}

	@Test
	public void testGetCommentsDescOffsetLimitToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		for (int i = 0; i < 10; i++) {
			this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		}
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, "desc", 5, 5);
		assertEquals(5, response.getComments().size());
		assertEquals(Integer.valueOf(5), response.getComments().get(0).getId());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsInvalidOrder() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.passwordAuthRecordManagerment.getComments(APP_ID, id, "test", null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsInvalidOrderToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.tokenRecordManagerment.getComments(APP_ID, id, "test", null, null);
	}

	@Test
	public void testGetCommentsWithoutOptions() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.passwordAuthRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(4, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testman2.getName() + " \ntest comment4 ", response.getComments().get(0).getText());
		assertEquals(testAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertFalse(response.getOlder());
		assertFalse(response.getNewer());
	}

	@Test
	public void testGetCommentsWithoutOptionsToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		GetCommentsResponse response = this.tokenRecordManagerment.getComments(APP_ID, id, null, null, null);
		assertEquals(4, response.getComments().size());
		assertNotNull(response.getComments().get(0).getId());
		assertNotNull(response.getComments().get(0).getCreatedAt());
		assertEquals(testman2.getName() + " \ntest comment4 ", response.getComments().get(0).getText());
		assertEquals(testTokenAdimin, response.getComments().get(0).getCreator());
		assertEquals(mentionList, response.getComments().get(0).getMentions());
		assertFalse(response.getOlder());
		assertFalse(response.getNewer());
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsCommentOff() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(1658, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsCommentOffToken() throws KintoneAPIException {
		this.nodeletepermissionRecordManagerment.getComments(1658, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsDoNotHavePermissionApp() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(1632, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsDoNotHavePermissionAppToken() throws KintoneAPIException {
		this.noviewpermissiontokenRecordManagerment.getComments(1632, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsDoNotHavePermissionRecord() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(1634, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsDoNotHavePermissionRecordToken() throws KintoneAPIException {
		this.addnoviewtokenRecordManagerment.getComments(1634, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWithoutAppId() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(null, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWithoutAppIdToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(null, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(100000, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(100000, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdNegativeNumber() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(-1, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdNegativeNumberToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(-1, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(0, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsAppIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(0, 1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, 10000, null, null, null);
	}

	@Ignore
	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, 1000000, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdNegativeNumber() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, -1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdNegativeNumberToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, -1, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, 0, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsRecordIdZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, 0, null, null, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWrongOffset() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, -1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWrongOffsetToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, 1, null, -1, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsLimitNegativeNumber() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, null, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsLimitNegativeNumberToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, 1, null, null, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsLimitOverTen() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.getComments(APP_ID, 1, null, null, 11);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsLimitOverTenToken() throws KintoneAPIException {
		this.tokenRecordManagerment.getComments(APP_ID, 1, null, null, 11);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.passwordAuthRecordManagerment.getComments(APP_ID, null, "asc", 1, 2);
	}

	@Test(expected = KintoneAPIException.class)
	public void testGetCommentsWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment1");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment2");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment3");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		comment.setText("test comment4");
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.tokenRecordManagerment.getComments(APP_ID, null, "asc", 1, 2);
	}

	@Test
	public void testDeleteComment() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, commentId);
	}

	@Test
	public void testDeleteCommentToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.tokenRecordManagerment.deleteComment(APP_ID, id, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentDoNotHavePermissionApp() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(1632, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentDoNotHavePermissionAppToken() throws KintoneAPIException {
		this.noviewpermissiontokenRecordManagerment.deleteComment(1632, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentDoNotHavePermissionRecord() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(1634, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentDoNotHavePermissionRecordToken() throws KintoneAPIException {
		this.addnoviewtokenRecordManagerment.deleteComment(1634, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutCommentId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutCommentIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		this.tokenRecordManagerment.deleteComment(APP_ID, id, null);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDUnexisted() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDUnexistedToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.tokenRecordManagerment.deleteComment(APP_ID, id, 100000);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDNegativeNumber() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDNegativeNumberToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.tokenRecordManagerment.deleteComment(APP_ID, id, -1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDZero() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentIDZeroToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);
		Integer id = addResponse.getID();
		this.tokenRecordManagerment.deleteComment(APP_ID, id, 0);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutRecordId() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, null, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutRecordIdToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.tokenRecordManagerment.deleteComment(APP_ID, null, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, 1000000, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(APP_ID, 1000000, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordNegativeNumber() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, -1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordNegativeNumberToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(APP_ID, -1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, 0, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentRecordZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(APP_ID, 0, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutApp() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.passwordAuthRecordManagerment.deleteComment(null, id, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentWithoutAppToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		this.tokenRecordManagerment.deleteComment(null, id, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppUnexisted() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(10000, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppUnexistedToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(10000, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppNegativeNumber() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(-1, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppNegativeNumberToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(-1, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppZero() throws KintoneAPIException {
		this.passwordAuthRecordManagerment.deleteComment(0, 1, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentAppZeroToken() throws KintoneAPIException {
		this.tokenRecordManagerment.deleteComment(0, 1, 1);
	}

	@Test
	public void testDeleteCommentsInGuest() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setPasswordAuth(USERNAME, PASSWORD);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse addComment = guestRecord.addComment(1631, id, comment);
		guestRecord.deleteComment(1631, id, addComment.getId());
	}

	@Test
	public void testDeleteCommentsInGuestToken() throws KintoneAPIException {
		// Preprocessing
		Auth auth = new Auth();
		auth.setApiToken(GUEST_SPACE_API_TOKEN);
		Connection connection = new Connection(TestConstants.DOMAIN, auth, GUEST_SPACE_ID);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		Record guestRecord = new Record(connection);

		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "text", FieldType.SINGLE_LINE_TEXT, "guest 文字列__1行");

		AddRecordResponse addResponse = guestRecord.addRecord(1631, testRecord);
		// Main Test processing
		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode("xxxxx");
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse addComment = guestRecord.addComment(1631, id, comment);
		guestRecord.deleteComment(1631, id, addComment.getId());
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentCommentOff() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(1640, testRecord);
		Integer id = addResponse.getID();
		this.passwordAuthRecordManagerment.deleteComment(1640, id, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentCommentOffToken() throws KintoneAPIException {
		HashMap<String, FieldValue> testRecord = new HashMap<>();
		testRecord = addField(testRecord, "单行文本框", FieldType.SINGLE_LINE_TEXT, "test single text 1");
		AddRecordResponse addResponse = this.requiredfieldtokenRecordManagerment.addRecord(1640, testRecord);
		Integer id = addResponse.getID();
		this.requiredfieldtokenRecordManagerment.deleteComment(1640, id, 1);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentOtherUser() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.passwordAuthRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.passwordAuthRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		Auth auth = new Auth();
		auth.setPasswordAuth("xxxxx", "xxxxx");
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.passwordAuthRecordManagerment = new Record(connection);
		this.passwordAuthRecordManagerment.deleteComment(APP_ID, id, commentId);
	}

	@Test(expected = KintoneAPIException.class)
	public void testDeleteCommentOtherUserToken() throws KintoneAPIException {
		// Preprocessing
		HashMap<String, FieldValue> testRecord = createTestRecord();
		AddRecordResponse addResponse = this.tokenRecordManagerment.addRecord(APP_ID, testRecord);

		Integer id = addResponse.getID();
		CommentContent comment = new CommentContent();
		ArrayList<CommentMention> mentionList = new ArrayList<CommentMention>();
		CommentMention mention = new CommentMention();
		mention.setCode(testman2.getCode());
		mention.setType("USER");
		mentionList.add(mention);
		comment.setText("test comment");
		comment.setMentions(mentionList);
		AddCommentResponse commentAddResponse = this.tokenRecordManagerment.addComment(APP_ID, id, comment);
		// Main Test processing
		Integer commentId = commentAddResponse.getId();
		Auth auth = new Auth();
		auth.setPasswordAuth("xxxxx", "xxxxx");
		Connection connection = new Connection(TestConstants.DOMAIN, auth);
		connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);
		this.tokenRecordManagerment = new Record(connection);
		this.tokenRecordManagerment.deleteComment(APP_ID, id, commentId);
    }
}
