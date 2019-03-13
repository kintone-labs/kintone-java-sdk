package com.cybozu.kintone.client.module.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppDeployStatus;
import com.cybozu.kintone.client.model.app.AppDeployStatus.Status;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.basic.response.AddPreviewAppResponse;
import com.cybozu.kintone.client.model.app.basic.response.BasicResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetAppDeployStatusResponse;
import com.cybozu.kintone.client.model.member.Member;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class AppParserTest {
	private static final JsonParser jsonParser = new JsonParser();
	private static JsonElement validAppDataInput;
	private static JsonElement validAppsDataInput;
	
	@BeforeClass
	public static void setup() {
		validAppDataInput = jsonParser.parse(readInput("/app/ValidAppValue.txt"));
		validAppsDataInput = jsonParser.parse(readInput("/apps/ValidAppsValue.txt"));
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
	public void testParseAppIdShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getAppId());
			if (appModel.getAppId() <= 0) {
				fail("Invalid appId value");
			}
			assertEquals(Integer.valueOf(1), appModel.getAppId());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppCodeShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getCode());
			assertEquals("test", appModel.getCode());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppNameShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getName());
			assertEquals("app_name", appModel.getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppDescriptionShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getName());
			assertEquals("jdk test app", appModel.getDescription());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppSpaceIDShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getSpaceId());
			if (appModel.getSpaceId() <= 0) {
				fail("Invalid spaceId value");
			}
			assertEquals(Integer.valueOf(3), appModel.getSpaceId());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseAppSpaceIDIsNullShouldSuccess() {
		String alidAppValueWithOutSpace = readInput("/app/ValidAppValueWithOutSpace.txt");
		assertNotNull(alidAppValueWithOutSpace);

		try {
			AppParser parser = new AppParser();
			AppModel appModel = parser.parseApp(jsonParser.parse(alidAppValueWithOutSpace));
			assertNotNull(appModel);
			assertNull(appModel.getSpaceId());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppThreadIDShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getThreadId());
			if (appModel.getThreadId() <= 0) {
				fail("Invalid spaceId value");
			}
			assertEquals(Integer.valueOf(3), appModel.getThreadId());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseAppThreadIDIsNullShouldSuccess() {
		String alidAppValueWithOutSpace = readInput("/app/ValidAppValueWithOutSpace.txt");
		assertNotNull(alidAppValueWithOutSpace);

		try {
			AppParser parser = new AppParser();
			AppModel appModel = parser.parseApp(jsonParser.parse(alidAppValueWithOutSpace));
			assertNotNull(appModel);
			assertNull(appModel.getThreadId());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testParseAppCreatedAtShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getCreatedAt());
			Date createdAt = appModel.getCreatedAt();
			SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2018-08-17T05:14:05.000Z", isoDateFormat.format(createdAt));
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppCreatorShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getCreator());
			assertEquals(new Member("test", "test"), appModel.getCreator());
			assertEquals("test", appModel.getCreator().getCode());
			assertEquals("test", appModel.getCreator().getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppModifiedAtShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getModifiedAt());
			Date modifiedAt = appModel.getModifiedAt();
			SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2018-08-17T05:14:05.000Z", isoDateFormat.format(modifiedAt));
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppModifierShouldSuccess() {
		assertNotNull(validAppDataInput);
		AppParser parser = new AppParser();

		try {
			AppModel appModel = parser.parseApp(validAppDataInput);
			assertNotNull(appModel);
			assertNotNull(appModel.getModifier());
			assertEquals(new Member("test", "test"), appModel.getModifier());
			assertEquals("test", appModel.getModifier().getCode());
			assertEquals("test", appModel.getModifier().getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseAppShouldFailWhenGivenInvalidAppIdIsInt() throws KintoneAPIException {
		String invalidAppIdIsInt = readInput("/app/InvalidAppID.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseApp(jsonParser.parse(invalidAppIdIsInt));
		} catch (KintoneAPIException e) {
			assertEquals("Invalid data type", e.getMessage());
		}
	}
	
	@Test(expected = KintoneAPIException.class)
	public void testParseAppShouldFailWhenGivenInvalidCreatedTime() throws KintoneAPIException {
		String invalidCreatedTime = readInput("/app/InvalidCreatedTime.txt");
		AppParser parser = new AppParser();
		parser.parseApp(jsonParser.parse(invalidCreatedTime));
	}
	
	@Test
	public void testParseAppShouldFailWhenGivenInvalidAppSpaceId() throws KintoneAPIException {
		String invalidAppSpaceId = readInput("/app/InvalidAppSpaceId.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseApp(jsonParser.parse(invalidAppSpaceId));
		} catch (KintoneAPIException e) {
			assertEquals("Invalid data type", e.getMessage());
		}
	}
	
	@Test
	public void testParseAppShouldFailWhenGivenInvalidAppThreadId() throws KintoneAPIException {
		String invalidAppSpaceId = readInput("/app/InvalidAppThreadId.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseApp(jsonParser.parse(invalidAppSpaceId));
		} catch (KintoneAPIException e) {
			assertEquals("Invalid data type", e.getMessage());
		}
	}
	
	@Test
	public void testParseAppsShouldSuccess() {
		assertNotNull(validAppsDataInput);
		AppParser parser = new AppParser();
		try {
			assertTrue(validAppsDataInput.getAsJsonObject().has("apps"));
			List<AppModel> apps = parser.parseApps(validAppsDataInput);
			assertNotNull(apps);
			assertEquals(2, apps.size());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testParseAppsOfPropertiesShouldSuccess() {
		assertNotNull(validAppsDataInput);
		AppParser parser = new AppParser();
		try {
			assertTrue(validAppsDataInput.getAsJsonObject().has("apps"));;
			List<AppModel> apps = parser.parseApps(validAppsDataInput);
			assertNotNull(apps);
			//apps[0]
			assertEquals(Integer.valueOf(1), apps.get(0).getAppId());
			assertEquals("BAR", apps.get(0).getCode());
			assertEquals("MyTestApp", apps.get(0).getName());
			assertEquals("", apps.get(0).getDescription());
			assertNull(apps.get(0).getSpaceId());
			assertNull(apps.get(0).getThreadId());
			
			Date createdAt = apps.get(0).getCreatedAt();
			SimpleDateFormat cDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			cDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2018-08-17T05:14:05.000Z", cDateFormat.format(createdAt));
			
			assertNotNull(apps.get(0).getCreator());
			assertEquals(new Member("user1", "user1"), apps.get(0).getCreator());
			assertEquals("user1", apps.get(0).getCreator().getCode());
			assertEquals("user1", apps.get(0).getCreator().getName());
			
			Date modifiedAt = apps.get(0).getModifiedAt();
			SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			mDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2018-08-17T05:14:05.000Z", mDateFormat.format(modifiedAt));
			
			assertEquals(new Member("user1", "user1"), apps.get(0).getModifier());
			assertEquals("user1", apps.get(0).getModifier().getCode());
			assertEquals("user1", apps.get(0).getModifier().getName());
			
			//apps[1]
			assertEquals(Integer.valueOf(2), apps.get(1).getAppId());
			assertEquals("FOO", apps.get(1).getCode());
			assertEquals("TEST", apps.get(1).getName());
			assertEquals("", apps.get(1).getDescription());
			assertEquals(Integer.valueOf(123), apps.get(1).getSpaceId());
			assertEquals(Integer.valueOf(456), apps.get(1).getThreadId());
			
			Date createdAtTwo = apps.get(1).getCreatedAt();
			SimpleDateFormat cDateFormatTwo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			cDateFormatTwo.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2014-06-03T05:14:05.000Z", cDateFormatTwo.format(createdAtTwo));
			
			assertNotNull(apps.get(1).getCreator());
			assertEquals(new Member("user2", "user2"), apps.get(1).getCreator());
			assertEquals("user2", apps.get(1).getCreator().getCode());
			assertEquals("user2", apps.get(1).getCreator().getName());
			
			Date modifiedAtTwo = apps.get(1).getModifiedAt();
			SimpleDateFormat mDateFormatTwo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			mDateFormatTwo.setTimeZone(TimeZone.getTimeZone("UTC"));
			assertEquals("2014-06-03T05:14:05.000Z", mDateFormatTwo.format(modifiedAtTwo));
			
			assertEquals(new Member("user2", "user2"), apps.get(1).getModifier());
			assertEquals("user2", apps.get(1).getModifier().getCode());
			assertEquals("user2", apps.get(1).getModifier().getName());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseAddPreviewAppResponseShouldSuccess() {
		String validPreviewApp = readInput("/app/ValidPreviewAppResponse.txt");
		assertNotNull(jsonParser.parse(validPreviewApp));
		AppParser parser = new AppParser();
		try {
			AddPreviewAppResponse parseAddPreviewAppResponse = parser.parseAddPreviewAppResponse(jsonParser.parse(validPreviewApp));
			assertNotNull(parseAddPreviewAppResponse.getApp());
			assertNotNull(parseAddPreviewAppResponse.getRevision());
			assertEquals(Integer.valueOf(123), parseAddPreviewAppResponse.getApp());
			assertEquals(Integer.valueOf(12), parseAddPreviewAppResponse.getRevision());			
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	//KCB-660
	@Test
	public void testParseAddPreviewAppResponseShouldFailWithInvalidData() throws KintoneAPIException {
		String invalidPreviewApp = readInput("/app/InvalidPreviewAppResponse.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseAddPreviewAppResponse(jsonParser.parse(invalidPreviewApp));
		} catch (KintoneAPIException e) {
			assertEquals(e.getMessage(), "Invalid data type");
		}
	}
	
	@Test
	public void testParseAddPreviewAppResponseShouldFailWithParseError() throws KintoneAPIException {
		String invalidParse = readInput("/app/InvalidPreviewAppResponse.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseAddPreviewAppResponse(jsonParser.parse(invalidParse).getAsJsonObject().get("app"));
		} catch (KintoneAPIException e) {
			assertEquals(e.getMessage(), "Parse error");
		}
	}
	
	@Test
	public void testParseBasicResponseShouldSuccess() {
		String validBasic = readInput("/app/ValidBasicResponse.txt");
		assertNotNull(jsonParser.parse(validBasic));
		AppParser parser = new AppParser();
		try {
			BasicResponse parseBasicResponse = parser.parseBasicResponse(jsonParser.parse(validBasic));
			assertEquals(Integer.valueOf(123), parseBasicResponse.getRevision());
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	//KCB-660
	@Test
	public void testParseBasicResponseShouldFailWithInvalidData() throws KintoneAPIException{
		String invalidBasic = readInput("/app/InvalidBasicResponse.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseBasicResponse(jsonParser.parse(invalidBasic));
		} catch (KintoneAPIException e) {
			assertEquals("Invalid data type", e.getMessage());
		}
	}
	
	@Test
	public void testParseBasicResponseShouldFailWithParseError() throws KintoneAPIException{
		String invalidParse = readInput("/app/InvalidBasicResponse.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseBasicResponse(jsonParser.parse(invalidParse).getAsJsonObject().get("revision"));
		} catch (KintoneAPIException e) {
			assertEquals("Parse error", e.getMessage());
		}
	}
	
	@Test 
	public void testParseAppDeployStatusResponseShouldSuccess() {
		String validParse = readInput("/app/ValidAppDeployStatus.txt");
		assertNotNull(jsonParser.parse(validParse));
		AppParser parser = new AppParser();
		try {
			GetAppDeployStatusResponse parseAppDeployStatusResponse = parser.parseAppDeployStatusResponse(jsonParser.parse(validParse));
			ArrayList<AppDeployStatus> apps = parseAppDeployStatusResponse.getApps();
			assertEquals(Integer.valueOf(20), apps.get(0).getApp());
			assertEquals(Status.valueOf("PROCESSING"), apps.get(0).getStatus());
			assertEquals(Integer.valueOf(21), apps.get(1).getApp());
			assertEquals(Status.valueOf("SUCCESS"), apps.get(1).getStatus());
			assertEquals(Integer.valueOf(22), apps.get(2).getApp());
			assertEquals(Status.valueOf("FAIL"), apps.get(2).getStatus());						
		} catch (KintoneAPIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParseAppDeployStatusResponseShouldFailWithInvalidData() throws KintoneAPIException {
		String invalidDeployStatus = readInput("/app/InvalidDeployStatus.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseAppDeployStatusResponse(jsonParser.parse(invalidDeployStatus));
		} catch (KintoneAPIException e) {
			assertEquals("Invalid data type", e.getMessage());
		}
	}

	@Test
	public void testParseAppDeployStatusResponseShouldFailWithParseError() throws KintoneAPIException {
		String invalidDeployStatus = readInput("/app/InvalidDeployStatus.txt");
		try {
			AppParser parser = new AppParser();
			parser.parseAppDeployStatusResponse(jsonParser.parse(invalidDeployStatus).getAsJsonObject().get("apps"));
		} catch (KintoneAPIException e) {
			assertEquals("Parse error", e.getMessage());
		}
	}
}
	
