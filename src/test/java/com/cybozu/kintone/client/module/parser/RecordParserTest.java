package com.cybozu.kintone.client.module.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.record.SubTableValueItem;
import com.cybozu.kintone.client.model.record.field.FieldValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RecordParserTest {
	private static final JsonParser jsonParser = new JsonParser();
	private static JsonElement validRecordDataInput;
	
	@BeforeClass
	public static void setup() {
		validRecordDataInput = jsonParser.parse(readInput("/record/ValidRecordValue.txt"));
	}
	
	private static String readInput(String file) {
		URL url = RecordParserTest.class.getResource(file);
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
	public void testParseRecordSingleLineTextShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("文字列__1行_");
			FieldValue singleLineText = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.SINGLE_LINE_TEXT,singleLineText.getType());
            assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), singleLineText.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordFileShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("添付ファイル_0");
			FieldValue file = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.FILE,file.getType() );
			ArrayList<FileModel> fileList = (ArrayList<FileModel>) file.getValue();
			JsonArray asJsonArray = jsonElement.getAsJsonObject().get("value").getAsJsonArray();
			assertEquals(asJsonArray.get(0).getAsJsonObject().get("fileKey").getAsString(), fileList.get(0).getFileKey());
		    assertEquals(asJsonArray.get(0).getAsJsonObject().get("name").getAsString(), fileList.get(0).getName());
	     	assertEquals(asJsonArray.get(0).getAsJsonObject().get("contentType").getAsString(), fileList.get(0).getContentType());
		    assertEquals(asJsonArray.get(0).getAsJsonObject().get("size").getAsString(), fileList.get(0).getSize());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordRecordNumberShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("レコード番号");
			FieldValue recordnumber = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.RECORD_NUMBER,recordnumber.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), recordnumber.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordRichTextShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("リッチエディター");
			FieldValue richetext = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.RICH_TEXT,richetext.getType() );
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), richetext.getValue());		
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordModifierShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("更新者");
			FieldValue modifier = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.MODIFIER,modifier.getType());
			Member m = (Member) modifier.getValue();
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsJsonObject().get("code").getAsString(),m.getCode());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsJsonObject().get("name").getAsString(),m.getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordMultiLineTextShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("文字列__複数行_");
			FieldValue multiLineText = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.MULTI_LINE_TEXT,multiLineText.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), multiLineText.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordLookUpShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("ルックアップ");
			FieldValue lookup = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.SINGLE_LINE_TEXT,lookup.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), lookup.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordCalcShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("計算");
			FieldValue calculate = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.CALC,calculate.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), calculate.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordDateShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("日付");
			FieldValue date = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.DATE,date.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), date.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordSubTableShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("Table");
			FieldValue subtable = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.SUBTABLE,subtable.getType());
			ArrayList<SubTableValueItem> value = (ArrayList<SubTableValueItem>) subtable.getValue();
			JsonArray asJsonArray = jsonElement.getAsJsonObject().get("value").getAsJsonArray();
			assertEquals(Integer.valueOf(asJsonArray.get(0).getAsJsonObject().get("id").getAsInt()), value.get(0).getID());
			HashMap<String, FieldValue> tableValue = value.get(0).getValue();
			for(Entry<String, FieldValue> entry:tableValue.entrySet()) {
				if(entry.getKey().equals("文字列__1行__0")) {
					assertEquals(FieldType.SINGLE_LINE_TEXT, entry.getValue().getType());
					assertEquals(asJsonArray.get(0).getAsJsonObject().get("value").getAsJsonObject().get("文字列__1行__0").getAsJsonObject().get("value").getAsString(),entry.getValue().getValue());
				}
				if(entry.getKey().equals("数値_0")) {
					assertEquals(FieldType.NUMBER, entry.getValue().getType());
					assertEquals(asJsonArray.get(0).getAsJsonObject().get("value").getAsJsonObject().get("数値_0").getAsJsonObject().get("value").getAsString(),entry.getValue().getValue());					
				}
			}
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordUserSelectShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("ユーザー選択");
			FieldValue userSelect = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(), jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.USER_SELECT,userSelect.getType());
			ArrayList<Member> memberList = (ArrayList<Member>) userSelect.getValue();
			JsonArray asJsonarray = jsonElement.getAsJsonObject().get("value").getAsJsonArray();
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("code").getAsString(),memberList.get(0).getCode());
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("name").getAsString(),memberList.get(0).getName());
			assertEquals(asJsonarray.get(1).getAsJsonObject().get("code").getAsString(),memberList.get(1).getCode());
			assertEquals(asJsonarray.get(1).getAsJsonObject().get("name").getAsString(),memberList.get(1).getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordGroupSelectShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("グループ選択");
			FieldValue groupSelect = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(), jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.GROUP_SELECT,groupSelect.getType());
			ArrayList<Member> memberList = (ArrayList<Member>) groupSelect.getValue();
			JsonArray asJsonarray = jsonElement.getAsJsonObject().get("value").getAsJsonArray();
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("code").getAsString(),memberList.get(0).getCode());
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("name").getAsString(),memberList.get(0).getName());
			assertEquals(asJsonarray.get(1).getAsJsonObject().get("code").getAsString(),memberList.get(1).getCode());
			assertEquals(asJsonarray.get(1).getAsJsonObject().get("name").getAsString(),memberList.get(1).getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordCreatorShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("作成者");
			FieldValue creator = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.CREATOR,creator.getType());
			Member m = (Member) creator.getValue();
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsJsonObject().get("code").getAsString(),m.getCode());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsJsonObject().get("name").getAsString(),m.getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordRadioButtonShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("ラジオボタン");
			FieldValue radiobutton = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.RADIO_BUTTON,radiobutton.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), radiobutton.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordDropDownShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("ドロップダウン");
			FieldValue dropdown = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.DROP_DOWN,dropdown.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), dropdown.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordOrganizationSelectShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("組織選択");
			FieldValue orgSelect = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(), jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.ORGANIZATION_SELECT,orgSelect.getType());
			ArrayList<Member> orgList = (ArrayList<Member>) orgSelect.getValue();
            JsonArray asJsonarray = jsonElement.getAsJsonObject().get("value").getAsJsonArray();
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("code").getAsString(),orgList.get(0).getCode());
			assertEquals(asJsonarray.get(0).getAsJsonObject().get("name").getAsString(),orgList.get(0).getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordRevisionShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("$revision");
			FieldValue revision = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.__REVISION__,revision.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), revision.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordUpdateTimeShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("更新日時");
			FieldValue updatetime = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.UPDATED_TIME,updatetime.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), updatetime.getValue());		
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordDateTimeShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("日時");
			FieldValue datetime = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.DATETIME,datetime.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), datetime.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordTimeShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("時刻");
			FieldValue time = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.TIME,time.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), time.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordNumberShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("数値");
			FieldValue number = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.NUMBER,number.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), number.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordMailShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("メール");
			FieldValue mail = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.LINK,mail.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), mail.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordAddressShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("アドレス帳");
			FieldValue address = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.LINK,address.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), address.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordLinkShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("リンク");
			FieldValue link = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.LINK,link.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), link.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordCreatedTimeShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("作成日時");
			FieldValue createdtime = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.CREATED_TIME,createdtime.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), createdtime.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordIDShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("$id");
			FieldValue id = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.__ID__,id.getType());
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), id.getValue());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordCheckBoxShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("チェックボックス");
			FieldValue checkbox = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.CHECK_BOX,checkbox.getType());
			ArrayList<String> checkboxList = (ArrayList<String>) checkbox.getValue();
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), checkboxList.get(0));
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseRecordMultiSelectShouldSuccess() {
		assertNotNull(validRecordDataInput);
		try {
			RecordParser parser = new RecordParser();
			JsonElement jsonElement = validRecordDataInput.getAsJsonObject().get("record").getAsJsonObject().get("複数選択");
			FieldValue multiselect = parser.parseField(jsonElement.getAsJsonObject().get("type").getAsString(),jsonElement.getAsJsonObject().get("value"));
			assertEquals(FieldType.MULTI_SELECT,multiselect.getType());
			ArrayList<String> multiselectList = (ArrayList<String>) multiselect.getValue();
			assertEquals(jsonElement.getAsJsonObject().get("value").getAsString(), multiselectList.get(0));
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

}
