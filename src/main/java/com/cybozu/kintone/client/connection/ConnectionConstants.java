/**
 * MIT License
 *
 * Copyright (c) 2018 Cybozu
 * https://github.com/kintone/kintone-java-sdk/blob/master/LICENSE
 */

package com.cybozu.kintone.client.connection;

public class ConnectionConstants {
    public static final String BASE_URL = "/k/v1/{API_NAME}.json";
    public static final String BASE_GUEST_URL = "/k/guest/{GUEST_SPACE_ID}/v1/{API_NAME}.json";
    public static final String GET_REQUEST = "GET";
    public static final String POST_REQUEST = "POST";
    public static final String PUT_REQUEST = "PUT";
    public static final String DELETE_REQUEST = "DELETE";
    public static final String HTTPS_PREFIX = "https://";
    public static final String SECURE_ACCESS_SYMBOL = ".s.";
    public static final String USER_AGENT_KEY = "User-Agent";
    public static final String USER_AGENT_VALUE = "kintone-java-sdk";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    public static final String BOUNDARY = "boundary_aj8gksdnsdfakj342fs3dt3stk8g6j32";

    public static final String APP = "app";
    public static final String APP_CUSTOMIZE = "app/customize";
    public static final String APP_CUSTOMIZE_PREVIEW = "preview/app/customize";
    public static final String APP_DEPLOY = "review/app/deploy";
    public static final String APP_DEPLOY_PREVIEW = "preview/app/deploy";
    public static final String APP_FIELDS = "app/form/fields";
    public static final String APP_FIELDS_PREVIEW = "preview/app/form/fields";
    public static final String APP_LAYOUT = "app/form/layout";
    public static final String APP_LAYOUT_PREVIEW = "preview/app/form/layout";
    public static final String APP_PERMISSION = "app/acl";
    public static final String APP_PERMISSION_PREVIEW = "preview/app/acl";
    public static final String APP_PREVIEW = "preview/app";
    public static final String APP_SETTINGS = "app/settings";
    public static final String APP_SETTINGS_PREVIEW = "preview/app/settings";
    public static final String APP_STATUS = "app/status";
    public static final String APP_STATUS_PREVIEW = "preview/app/status";
    public static final String APP_VIEWS = "app/views";
    public static final String APP_VIEWS_PREVIEW = "preview/app/views";
    public static final String APPS = "apps";
    public static final String BULK_REQUEST = "bulkRequest";
    public static final String FIELD_PERMISSION = "field/acl";
    public static final String FILE = "file";
    public static final String GUESTS = "guests";
    public static final String RECORD = "record";
    public static final String RECORD_ASSIGNEES = "record/assignees";
    public static final String RECORD_COMMENT = "record/comment";
    public static final String RECORD_COMMENTS = "record/comments";
    public static final String RECORD_PERMISSION = "record/acl";
    public static final String RECORD_STATUS = "record/status";
    public static final String RECORDS = "records";
    public static final String RECORDS_CURSOR = "records/cursor";
    public static final String RECORDS_STATUS = "records/status";
    public static final String SPACE = "space";
    public static final String SPACE_BODY = "space/body";
    public static final String SPACE_GUEST = "space/guests";
    public static final String SPACE_MEMBER = "space/members";
    public static final String SPACE_TEMPLATE = "template/space";
    public static final String SPACE_THREAD = "space/thread";
    public static final String SPACE_THREAD_COMMENT = "space/thread/comment";
}
