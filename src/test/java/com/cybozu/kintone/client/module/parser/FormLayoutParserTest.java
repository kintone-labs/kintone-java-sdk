package com.cybozu.kintone.client.module.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.form.LayoutType;
import com.cybozu.kintone.client.model.app.form.layout.FieldLayout;
import com.cybozu.kintone.client.model.app.form.layout.FieldSize;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.GroupLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.form.layout.RowLayout;
import com.cybozu.kintone.client.model.app.form.layout.SubTableLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FormLayoutParserTest {
	private static final JsonParser jsonParser = new JsonParser();
	private static FormLayoutParser parser = new FormLayoutParser();;
	private static JsonElement validLayoutDataInput;
	
	@BeforeClass
	public static void setup() {
		validLayoutDataInput = jsonParser.parse(readInput("/form/layout/ValidLayoutValue.txt"));
	}
	
	private static String readInput(String file) {
		URL url = AppParserTest.class.getResource(file);
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
	public void testFormLayoutParserShouldSuccess() {
		assertNotNull(validLayoutDataInput);
		try {
			FormLayout parseFormLayout = parser.parseFormLayout(validLayoutDataInput);
			assertNotNull(parseFormLayout);
			assertEquals("2", parseFormLayout.getRevision());
			List<ItemLayout> layout = parseFormLayout.getLayout();
			for (ItemLayout itemLayout : layout) {
				if (itemLayout instanceof RowLayout) {
					List<FieldLayout> fields = ((RowLayout) itemLayout).getFields();
					assertEquals("文字列__1行_", fields.get(0).getCode());
					assertEquals("200", fields.get(0).getSize().getWidth());
					assertEquals("SINGLE_LINE_TEXT", fields.get(0).getType());
				} else if (itemLayout instanceof GroupLayout) {
					List<RowLayout> layoutOfGroup = ((GroupLayout) itemLayout).getLayout();
					List<FieldLayout> fields = layoutOfGroup.get(0).getFields();
					assertEquals("数値2", fields.get(0).getCode());
					assertEquals("200", fields.get(0).getSize().getWidth());
					assertEquals("NUMBER", fields.get(0).getType());
				} else if (itemLayout instanceof SubTableLayout) {
					List<FieldLayout> fields = ((SubTableLayout) itemLayout).getFields();
					assertEquals("数値", fields.get(0).getCode());
					assertEquals("200", fields.get(0).getSize().getWidth());
					assertEquals("NUMBER", fields.get(0).getType());
				}
			}
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testItemLayoutParserShouldSuccess() {
		assertNotNull(validLayoutDataInput);
		try {
			JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
			JsonElement rowElement = asJsonArray.get(0);		
			ItemLayout rowLayout = parser.parseItemLayout(rowElement);
			assertEquals(LayoutType.ROW, rowLayout.getType());
			
			JsonElement subtableElement = asJsonArray.get(1);		
			ItemLayout subtableLayout = parser.parseItemLayout(subtableElement);
			assertEquals(LayoutType.SUBTABLE, subtableLayout.getType());
			
			JsonElement groupElement = asJsonArray.get(2);		
			ItemLayout groupLayout = parser.parseItemLayout(groupElement);
			assertEquals(LayoutType.GROUP, groupLayout.getType());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void testRowLayoutParserShouldSuccess() throws KintoneAPIException {
		assertNotNull(validLayoutDataInput);
		JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
		JsonElement rowElement = asJsonArray.get(0);
		RowLayout parseRowLayout = parser.parseRowLayout(rowElement.getAsJsonObject());
		assertEquals(LayoutType.ROW,parseRowLayout.getType());
		List<FieldLayout> fields = parseRowLayout.getFields();
		assertEquals("文字列__1行_", fields.get(0).getCode());
		assertEquals("200", fields.get(0).getSize().getWidth());
		assertEquals("SINGLE_LINE_TEXT", fields.get(0).getType());
	}
	
	@Test
	public void testSubtableLayoutParserShouldSuccess() throws KintoneAPIException {
		assertNotNull(validLayoutDataInput);
		JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
		JsonElement subtableElement = asJsonArray.get(1);
		SubTableLayout parseSubtableLayout = parser.parseSubTableLayout(subtableElement.getAsJsonObject());
		assertEquals(LayoutType.SUBTABLE,parseSubtableLayout.getType());
		List<FieldLayout> fields = ((SubTableLayout) parseSubtableLayout).getFields();
		assertEquals("数値", fields.get(0).getCode());
		assertEquals("200", fields.get(0).getSize().getWidth());
		assertEquals("NUMBER", fields.get(0).getType());
	}
	
	@Test
	public void testGuoupLayoutParserShouldSuccess() throws KintoneAPIException {
		assertNotNull(validLayoutDataInput);
		JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
		JsonElement groupElement = asJsonArray.get(2);
		GroupLayout parseGroupLayout = parser.parseGroupLayout(groupElement.getAsJsonObject());
		assertEquals(LayoutType.GROUP,parseGroupLayout.getType());
		List<RowLayout> layoutOfGroup = ((GroupLayout) parseGroupLayout).getLayout();
		List<FieldLayout> fields = layoutOfGroup.get(0).getFields();
		assertEquals("数値2", fields.get(0).getCode());
		assertEquals("200", fields.get(0).getSize().getWidth());
	}

	
	@Test
	public void testFieldLayoutParserShouldSuccess() throws KintoneAPIException {
		assertNotNull(validLayoutDataInput);
		JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
		JsonElement rowElement = asJsonArray.get(0);	
		JsonArray rowJsonArray = rowElement.getAsJsonObject().get("fields").getAsJsonArray();
		FieldLayout parseFieldLayout = parser.parseFieldLayout(rowJsonArray.get(0));
		assertEquals("文字列__1行_", parseFieldLayout.getCode());
		assertEquals("200", parseFieldLayout.getSize().getWidth());
		assertEquals("SINGLE_LINE_TEXT", parseFieldLayout.getType());
			
	}
	
	@Test
	public void testValidLayoutSizeParserShouldSuccess() throws KintoneAPIException {
		assertNotNull(validLayoutDataInput);
		assertNotNull(validLayoutDataInput);
		JsonArray asJsonArray = validLayoutDataInput.getAsJsonObject().get("layout").getAsJsonArray();
		JsonElement rowElement = asJsonArray.get(0);	
		JsonArray rowJsonArray = rowElement.getAsJsonObject().get("fields").getAsJsonArray();
		FieldSize parseFieldSize = parser.parseFieldSize(rowJsonArray.get(0).getAsJsonObject().get("size").getAsJsonObject());
		assertEquals("200", parseFieldSize.getWidth());
		assertEquals("100", parseFieldSize.getHeight());
		assertEquals("100", parseFieldSize.getInnerHeight());

	}
}
