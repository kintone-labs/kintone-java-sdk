/**
 * Copyright 2017 Cybozu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cybozu.kintone.client.module.app;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstants;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.form.LayoutType;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.field.system.RecordNumberField;
import com.cybozu.kintone.client.model.app.form.layout.FieldLayout;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.GroupLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.form.layout.RowLayout;
import com.cybozu.kintone.client.model.app.form.layout.SubTableLayout;
import com.cybozu.kintone.client.model.member.Member;

public class AppTest {
    private static String USERNAME = "xxxxx";
    private static String PASSWORD = "xxxxx";

    private static int SPACE_ID = 127;
    private static int THREAD_ID = 148;

    private static int APP_ID = 1639;
    private static int APP_CONTAINS_NORMAL_LAYOUT_ID = 1642;
    private static int APP_CONTAINS_TABLE_LAYOUT_ID = 1643;
    private static int APP_CONTAINS_FIELD_GROUP_ID = 1644;
    private static int APP_DOES_NOT_BELONG_TO_THREAD_ID = 1645;
    private static int MULTI_LANG_APP_ID = 1646;
    private static int RESTRICT_APP_ID = 1647;
    private static int GUEST_SPACE_APP_ID = 1631;
    private static int PREVIEW_APP_ID = 1649;
    private static int PRE_LIVE_APP_ID = 1650;

    private static String VIEW_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static String POST_API_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    private App appManagerment;

    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
    }

    @Test
    public void testGetAppShouldSuccess() throws KintoneAPIException {
        AppModel app = this.appManagerment.getApp(APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(THREAD_ID), app.getThreadId());
        assertNotNull(app.getCode());
        assertNotNull(app.getName());
        assertNotNull(app.getDescription());
        assertNotNull(app.getCreatedAt());
        assertNotNull(app.getCreator());
        assertNotNull(app.getModifiedAt());
        assertNotNull(app.getModifier());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenDoesNotHavePermissionViewApp() throws KintoneAPIException {
        this.appManagerment.getApp(RESTRICT_APP_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenInputAppIdNull() throws KintoneAPIException {
        this.appManagerment.getApp(null);
    }

    @Test
    public void testGetAppShouldSucessWhenUsingAPITokenHasViewRecordsPermission() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);

        AppModel app = appManagerment.getApp(APP_ID);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenUsingAPITokenDoesNotHasViewRecordsPermission() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(POST_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getApp(146);
    }

    @Test
    public void testGetAppShouldSuccessWhenAppIsNotInSpaceOrThead() throws KintoneAPIException {
        AppModel app = this.appManagerment.getApp(APP_DOES_NOT_BELONG_TO_THREAD_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_DOES_NOT_BELONG_TO_THREAD_ID), app.getAppId());
        assertEquals(null, app.getSpaceId());
        assertEquals(null, app.getThreadId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenRetrieveAppInGuestSpace() throws KintoneAPIException {
        this.appManagerment.getApp(1631);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsNotExist() throws KintoneAPIException {
        this.appManagerment.getApp(100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsMinus() throws KintoneAPIException {
        this.appManagerment.getApp(-1);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsZero() throws KintoneAPIException {
        this.appManagerment.getApp(0);
    }

    @Test
    public void testGetAppInGuestSpaceShouldSuccess() throws KintoneAPIException, ParseException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        AppModel app = this.appManagerment.getApp(GUEST_SPACE_APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(GUEST_SPACE_APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstants.GUEST_SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(150), app.getThreadId());
        assertEquals("guestApp", app.getCode());
        assertEquals("Guest Space Test", app.getName());
        assertEquals("<div>Guest Space Test</div>", app.getDescription());

        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(isoDateFormat.parse("2018-08-22T02:10:08.000Z"), app.getCreatedAt());
        assertEquals(isoDateFormat.parse("2018-09-07T09:25:40.000Z"), app.getModifiedAt());

        Member creator = app.getCreator();
        assertNotNull(creator);
        assertEquals("xxxxx", creator.getCode());
        assertEquals("xxxxx", creator.getName());

        Member modifier = app.getModifier();
        assertNotNull(modifier);
        assertEquals("xxxxx", modifier.getCode());
        assertEquals("xxxxx", modifier.getName());
    }

    @Test
    public void testGetAppsShouldSuccess() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getApps(600, null);
        assertEquals(52, setOffset.size());
    }

    @Test
    public void testDoNotSetOffsetIsZero() throws KintoneAPIException {
        List<AppModel> noneOffset = this.appManagerment.getApps(null, null);
        List<AppModel> setOffset = this.appManagerment.getApps(0, null);
        assertEquals(noneOffset.size(), setOffset.size());
    }

    @Test
    public void testGetAppsShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testDoNotSetlimitIsHundred() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getApps(null, null);
        List<AppModel> limitHundred = this.appManagerment.getApps(null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsInGuestSpaceShouldSuccess() throws KintoneAPIException, ParseException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        List<AppModel> apps = this.appManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getApps(null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getApps(null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongOffsetValue() throws KintoneAPIException {
        this.appManagerment.getApps(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getApps(null, null);
    }

    @Test
    public void testGetAppsShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(0, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsEmpty() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByIDs(null, 600, null);
        assertEquals(52, setOffset.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null,null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithIdsInGuestSpaceShouldSuccess() throws KintoneAPIException, ParseException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(GUEST_SPACE_APP_ID);
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsNull() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByIDs(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByIDs(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccess() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_CONTAINS_NORMAL_LAYOUT_ID);
        appIds.add(APP_CONTAINS_TABLE_LAYOUT_ID);
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertEquals(2, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByIDs(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByIDs(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getAppsByIDs(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenInvalidIds() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(-1);
        appIds.add(-2);
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenOverHundred() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        List<Integer> ids = Arrays.asList(arr);
        this.appManagerment.getAppsByIDs(ids, null, null);
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenListEmpty() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccess() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        codes.add("tablelayout");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessBigBody() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        for (int i = 0; i < 99; i++) {
        	codes.add("normallayout"+i);
		}
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeInGuestSpaceShouldSuccess() throws KintoneAPIException, ParseException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        List<String> codes = new ArrayList<String>();
        codes.add("guestApp");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenListEmpty() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenCodesIsNull() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailForEmptyArray() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodesShouldReturnEmptyArrayWhenGivenNonExistCodes() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("wrongCodeOne");
        codes.add("wrongCodeTwo");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByCodes(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByCodes(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByCodes(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByCodes(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenInvalidFormatCodes() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("Invalid Code");
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test
    public void testGetAppsWithCodesShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByCodes(null, 600, null);
        assertEquals(52, setOffset.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getAppsByCodes(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverThanSixtyFour() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverHundred() throws KintoneAPIException {
        String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
                "g1", "h1", "i1", "j1", "k1", "l1", "m1", "n1", "o1", "p1", "q1", "r1", "s1", "t1", "u1", "v1", "w1",
                "x1", "y1", "z1", "a5", "a6", "a7", "a8", "a9", "a0", "a1", "a2", "a3", "a4", "b5", "b6", "b7", "b8",
                "d9", "j0", "j1", "j2", "k3", "k4", "d5", "d6", "d7", "d8", "d9", "c0", "c1", "c2", "c3", "c4", "c5",
                "x6", "x7", "x8", "x9", "x0", "z1", "z2", "z3", "z4", "z5", "z6", "z7", "z8", "z9", "a00", "a01" };
        List<String> codes = Arrays.asList(arr);
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test
    public void testGetAppsWithNameShouldSuccess() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("Test table layout", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppSWithNameInGuestSpaceShouldSuccess() throws KintoneAPIException, ParseException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        List<AppModel> apps = this.appManagerment.getAppsByName("Guest Space Test", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenNameIsEmpty() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("", null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenGivenOtherLanguage() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("SHANGHAI", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnEmptyArrayWhenGivenNonExistName() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("Non exits name", null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldNotReturnEmptyArrayWhenGivenNoName() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByName(null, 600, null);
        assertEquals(52, setOffset.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByName(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByName(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByName(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByName(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, -1, null);
    }


    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getAppsByName(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenOverThanSixtyFour() throws KintoneAPIException {
        String name = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.appManagerment.getAppsByName(name, null, null);
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccess() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(25, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, 1, null);
        assertEquals(24, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessGuestSpace() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstants.GUEST_SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(2, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getAppsBySpaceIDs(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstants.GUEST_SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstants.GUEST_SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstants.GUEST_SPACE_ID);
    	this.appManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsBySpaceIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsBySpaceIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldReturnEmptyArrayWhenGivenNonExistSpaceId() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(10);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenInvalidSpaceId() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(-1);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenOverThanHundred() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        List<Integer> spaceIds = Arrays.asList(arr);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);
        appManagerment.getFormFields(null, null, null);
    }


    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNoAppId() throws KintoneAPIException {
        this.appManagerment.getFormFields(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNonExistAppId() throws KintoneAPIException {
        this.appManagerment.getFormFields(1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenMinus() throws KintoneAPIException {
        this.appManagerment.getFormFields(-1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenZero() throws KintoneAPIException {
        this.appManagerment.getFormFields(0, null, null);
    }


    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenRetrievePreviewApp() throws KintoneAPIException {
        this.appManagerment.getFormFields(1649, null, null);
    }

    @Test
    public void testGetFormFieldsShouldSuccess() throws KintoneAPIException {
        FormFields formfields = this.appManagerment.getFormFields(APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(APP_ID), formfields.getApp());
        assertEquals(Integer.valueOf(10), formfields.getRevision());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(9, properties.size());
    }

    @Test
    public void testGetFormFieldsWithPreLiveShouldSuccess() throws KintoneAPIException {
        FormFields formfields = this.appManagerment.getFormFields(PRE_LIVE_APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(PRE_LIVE_APP_ID), formfields.getApp());
        assertEquals(Integer.valueOf(3), formfields.getRevision());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(9, properties.size());
    }


    @Test
    public void testGetFormFieldsShouldSuccessGuestSpace() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        FormFields formfields = this.appManagerment.getFormFields(GUEST_SPACE_APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(GUEST_SPACE_APP_ID), formfields.getApp());
        assertEquals(Integer.valueOf(12), formfields.getRevision());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(10, properties.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenUseToken() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        this.appManagerment = new App(connection);
        this.appManagerment.getFormFields(APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenDoesNotHavePermissionViewRecords() throws KintoneAPIException {
        this.appManagerment.getFormFields(192, null, false);
    }

    @Test
    public void testGetFormFieldsShouldSuccessWhenRetrivePreviewApp() throws KintoneAPIException {
        FormFields formfields = this.appManagerment.getFormFields(APP_ID, null, true);
        assertNotNull(formfields);
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(9, properties.size());
    }

    @Test
    public void testGetFormFieldsShouldReturnCorrectLanguageSetting() throws KintoneAPIException {
        // test with English language setting
        FormFields formfields = this.appManagerment.getFormFields(MULTI_LANG_APP_ID, LanguageSetting.EN, null);
        assertNotNull(formfields);
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(8, properties.size());

        Field recordNumber = properties.get("Record_number");
        assertTrue(recordNumber instanceof RecordNumberField);
        assertEquals("Record No.", ((RecordNumberField) recordNumber).getLabel());

        // Test with Japanese language setting
        formfields = this.appManagerment.getFormFields(MULTI_LANG_APP_ID, LanguageSetting.JA, null);
        assertNotNull(formfields);
        properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(8, properties.size());

        recordNumber = properties.get("Record_number");
        assertTrue(recordNumber instanceof RecordNumberField);
        assertEquals("レコード番号", ((RecordNumberField) recordNumber).getLabel());
    }

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenRetrievePreviewAppButAccountDoesNotHaveAppManagePermission()
            throws KintoneAPIException {
        this.appManagerment.getFormFields(1649, null, true);
    }

    @Test
    public void testGetFormLayoutWithNormalLayoutShouldSuccess() throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(APP_CONTAINS_NORMAL_LAYOUT_ID, null);

        assertNotNull(formLayout);

        List<ItemLayout> layout = formLayout.getLayout();
        assertNotNull(layout);
        assertEquals(3, layout.size());

        assertTrue(layout.get(0) instanceof RowLayout);
        RowLayout firstRow = (RowLayout) layout.get(0);
        assertEquals(LayoutType.ROW, firstRow.getType());

        List<FieldLayout> fields = firstRow.getFields();
        assertEquals(3, fields.size());

        FieldLayout firstItem = fields.get(0);
        assertNotNull(firstItem);
        assertEquals("LABEL", firstItem.getType());
        assertEquals("Label", firstItem.getLabel());
        assertNull(firstItem.getCode());
        assertNotNull(firstItem.getSize());
        assertEquals("65", firstItem.getSize().getWidth());
        assertNull(firstItem.getSize().getHeight());

        FieldLayout secondItem = fields.get(1);
        assertNotNull(secondItem);
        assertEquals("NUMBER", secondItem.getType());
        assertNull(secondItem.getLabel());
        assertEquals("Number", secondItem.getCode());
        assertNotNull(secondItem.getSize());
        assertEquals("193", secondItem.getSize().getWidth());
        assertNull(secondItem.getSize().getHeight());

        FieldLayout thirdItem = fields.get(2);
        assertNotNull(thirdItem);
        assertEquals("SINGLE_LINE_TEXT", thirdItem.getType());
        assertNull(thirdItem.getLabel());
        assertEquals("Text", thirdItem.getCode());
        assertNotNull(thirdItem.getSize());
        assertEquals("193", thirdItem.getSize().getWidth());
        assertNull(thirdItem.getSize().getHeight());
    }

    @Test
    public void testGetFormLayoutWithFieldGroupShouldSuccess() throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(APP_CONTAINS_FIELD_GROUP_ID, null);

        assertNotNull(formLayout);

        List<ItemLayout> layout = formLayout.getLayout();
        assertNotNull(layout);
        assertEquals(1, layout.size());

        assertTrue(layout.get(0) instanceof GroupLayout);
        GroupLayout fieldGroup = (GroupLayout) layout.get(0);
        assertEquals(LayoutType.GROUP, fieldGroup.getType());
        List<RowLayout> fieldGroupLayout = fieldGroup.getLayout();
        assertNotNull(fieldGroupLayout);
        assertEquals(1, fieldGroupLayout.size());

        RowLayout firstRow = fieldGroupLayout.get(0);
        assertNotNull(firstRow);
        assertEquals(LayoutType.ROW, firstRow.getType());

        List<FieldLayout> fields = firstRow.getFields();
        assertEquals(3, fields.size());

        FieldLayout firstItem = fields.get(0);
        assertNotNull(firstItem);
        assertEquals("TIME", firstItem.getType());
        assertEquals("Time", firstItem.getCode());
        assertNull(firstItem.getLabel());
        assertNotNull(firstItem.getSize());
        assertEquals("101", firstItem.getSize().getWidth());
        assertNull(firstItem.getSize().getHeight());

        FieldLayout secondItem = fields.get(1);
        assertNotNull(secondItem);
        assertEquals("NUMBER", secondItem.getType());
        assertNull(secondItem.getLabel());
        assertEquals("Number", secondItem.getCode());
        assertNotNull(secondItem.getSize());
        assertEquals("193", secondItem.getSize().getWidth());
        assertNull(secondItem.getSize().getHeight());

        FieldLayout thirdItem = fields.get(2);
        assertNotNull(thirdItem);
        assertEquals("SINGLE_LINE_TEXT", thirdItem.getType());
        assertNull(thirdItem.getLabel());
        assertEquals("Text", thirdItem.getCode());
        assertNotNull(thirdItem.getSize());
        assertEquals("193", thirdItem.getSize().getWidth());
        assertNull(thirdItem.getSize().getHeight());
    }

    @Test
    public void testGetFormLayoutWithTableShouldSuccess() throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(APP_CONTAINS_TABLE_LAYOUT_ID, null);

        assertNotNull(formLayout);

        List<ItemLayout> layout = formLayout.getLayout();
        assertNotNull(layout);
        assertEquals(1, layout.size());

        assertTrue(layout.get(0) instanceof SubTableLayout);
        SubTableLayout tableLayout = (SubTableLayout) layout.get(0);
        assertEquals(LayoutType.SUBTABLE, tableLayout.getType());
        assertEquals("Table", tableLayout.getCode());

        List<FieldLayout> fields = tableLayout.getFields();
        assertEquals(3, fields.size());

        FieldLayout firstItem = fields.get(0);
        assertNotNull(firstItem);
        assertEquals("SINGLE_LINE_TEXT", firstItem.getType());
        assertEquals("Text", firstItem.getCode());
        assertNull(firstItem.getLabel());
        assertNotNull(firstItem.getSize());
        assertEquals("193", firstItem.getSize().getWidth());
        assertNull(firstItem.getSize().getHeight());

        FieldLayout secondItem = fields.get(1);
        assertNotNull(secondItem);
        assertEquals("RICH_TEXT", secondItem.getType());
        assertNull(secondItem.getLabel());
        assertEquals("Rich_text", secondItem.getCode());
        assertNotNull(secondItem.getSize());
        assertEquals("315", secondItem.getSize().getWidth());
        assertNull(secondItem.getSize().getHeight());
        assertEquals("125", secondItem.getSize().getInnerHeight());

        FieldLayout thirdItem = fields.get(2);
        assertNotNull(thirdItem);
        assertEquals("TIME", thirdItem.getType());
        assertNull(thirdItem.getLabel());
        assertEquals("Time", thirdItem.getCode());
        assertNotNull(thirdItem.getSize());
        assertEquals("101", thirdItem.getSize().getWidth());
        assertNull(thirdItem.getSize().getHeight());
    }

    @Test
    public void testGetFormLayoutShouldSuccessGuestSpace() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth, TestConstants.GUEST_SPACE_ID);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        this.appManagerment = new App(connection);
        FormLayout formLayout = this.appManagerment.getFormLayout(GUEST_SPACE_APP_ID, null);
        assertNotNull(formLayout);
        assertEquals(2, formLayout.getLayout().size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenUseToken() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken(VIEW_API_TOKEN);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        this.appManagerment = new App(connection);
        this.appManagerment.getFormLayout(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNoAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveZero() throws KintoneAPIException {
        this.appManagerment.getFormLayout(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNonExistAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenDoesNotHavePermissionViewRecords() throws KintoneAPIException {
        this.appManagerment.getFormLayout(192, false);
    }

    @Test
    public void testGetFormLayoutShouldSuccessWhenRetrievePreviewApp() throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(PREVIEW_APP_ID, true);

        assertNotNull(formLayout);

        List<ItemLayout> layout = formLayout.getLayout();
        assertNotNull(layout);
        assertEquals(3, layout.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrievePreviewApp() throws KintoneAPIException {
        this.appManagerment.getFormLayout(1649, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrieveInvalidPreviewApp() throws KintoneAPIException {
        this.appManagerment.getFormLayout(16501, null);
    }

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrievePreviewAppButAccountDoesNotHaveAppManagePermission()
            throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(USERNAME, PASSWORD);
        Connection connection = new Connection(TestConstants.DOMAIN, auth);
        connection.setProxy(TestConstants.PROXY_HOST, TestConstants.PROXY_PORT);

        App appManagerment = new App(connection);

        FormLayout formLayout = appManagerment.getFormLayout(145, true);
        assertNotNull(formLayout);
    }
}
