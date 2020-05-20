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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cybozu.kintone.client.TestConstantsSample;
import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.app.AppModel;
import com.cybozu.kintone.client.model.app.LanguageSetting;
import com.cybozu.kintone.client.model.app.basic.request.PreviewAppRequest;
import com.cybozu.kintone.client.model.app.basic.response.AddPreviewAppResponse;
import com.cybozu.kintone.client.model.app.basic.response.BasicResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetAppDeployStatusResponse;
import com.cybozu.kintone.client.model.app.basic.response.GetViewsResponse;
import com.cybozu.kintone.client.model.app.basic.response.UpdateViewsResponse;
import com.cybozu.kintone.client.model.app.form.AlignLayout;
import com.cybozu.kintone.client.model.app.form.FieldType;
import com.cybozu.kintone.client.model.app.form.LayoutType;
import com.cybozu.kintone.client.model.app.form.LinkProtocol;
import com.cybozu.kintone.client.model.app.form.NumberFormat;
import com.cybozu.kintone.client.model.app.form.UnitPosition;
import com.cybozu.kintone.client.model.app.form.field.Field;
import com.cybozu.kintone.client.model.app.form.field.FieldGroup;
import com.cybozu.kintone.client.model.app.form.field.FieldMapping;
import com.cybozu.kintone.client.model.app.form.field.FormFields;
import com.cybozu.kintone.client.model.app.form.field.SubTableField;
import com.cybozu.kintone.client.model.app.form.field.input.AttachmentField;
import com.cybozu.kintone.client.model.app.form.field.input.CalculatedField;
import com.cybozu.kintone.client.model.app.form.field.input.LinkField;
import com.cybozu.kintone.client.model.app.form.field.input.MultiLineTextField;
import com.cybozu.kintone.client.model.app.form.field.input.NumberField;
import com.cybozu.kintone.client.model.app.form.field.input.RichTextField;
import com.cybozu.kintone.client.model.app.form.field.input.SingleLineTextField;
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupField;
import com.cybozu.kintone.client.model.app.form.field.input.lookup.LookupItem;
import com.cybozu.kintone.client.model.app.form.field.input.member.DepartmentSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.GroupSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.member.MemberSelectEntity;
import com.cybozu.kintone.client.model.app.form.field.input.member.UserSelectionField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.CheckboxField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.DropDownField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.MultipleSelectField;
import com.cybozu.kintone.client.model.app.form.field.input.selection.OptionData;
import com.cybozu.kintone.client.model.app.form.field.input.selection.RadioButtonField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateField;
import com.cybozu.kintone.client.model.app.form.field.input.time.DateTimeField;
import com.cybozu.kintone.client.model.app.form.field.input.time.TimeField;
import com.cybozu.kintone.client.model.app.form.field.related_record.ReferenceTable;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedApp;
import com.cybozu.kintone.client.model.app.form.field.related_record.RelatedRecordsField;
import com.cybozu.kintone.client.model.app.form.field.system.AssigneeField;
import com.cybozu.kintone.client.model.app.form.field.system.CategoryField;
import com.cybozu.kintone.client.model.app.form.field.system.CreatedTimeField;
import com.cybozu.kintone.client.model.app.form.field.system.CreatorField;
import com.cybozu.kintone.client.model.app.form.field.system.ModifierField;
import com.cybozu.kintone.client.model.app.form.field.system.RecordNumberField;
import com.cybozu.kintone.client.model.app.form.field.system.StatusField;
import com.cybozu.kintone.client.model.app.form.field.system.UpdatedTimeField;
import com.cybozu.kintone.client.model.app.form.layout.FieldLayout;
import com.cybozu.kintone.client.model.app.form.layout.FieldSize;
import com.cybozu.kintone.client.model.app.form.layout.FormLayout;
import com.cybozu.kintone.client.model.app.form.layout.GroupLayout;
import com.cybozu.kintone.client.model.app.form.layout.ItemLayout;
import com.cybozu.kintone.client.model.app.form.layout.RowLayout;
import com.cybozu.kintone.client.model.app.form.layout.SubTableLayout;
import com.cybozu.kintone.client.model.app.general.GeneralSettings;
import com.cybozu.kintone.client.model.app.general.GeneralSettings.IconTheme;
import com.cybozu.kintone.client.model.app.general.Icon;
import com.cybozu.kintone.client.model.app.general.Icon.IconType;
import com.cybozu.kintone.client.model.app.view.ViewModel;
import com.cybozu.kintone.client.model.app.view.ViewModel.BuiltinType;
import com.cybozu.kintone.client.model.app.view.ViewModel.ViewType;
import com.cybozu.kintone.client.model.file.FileModel;
import com.cybozu.kintone.client.model.member.Member;
import com.cybozu.kintone.client.model.member.MemberSelectEntityType;
import com.cybozu.kintone.client.module.file.File;

public class AppTest {
    private static int APP_ID;
    private static int UPADATE_APP_ID;
    private static int FORMLAYOUT_APP_ID;
    private static int DEPLOY_APP_ID;
    private static int VIEW_SETTING_APP_ID;
    private static int NO_PERMISSION_APP_ID;
    private static int APP_CONTAINS_NORMAL_LAYOUT_ID;
    private static int APP_CONTAINS_TABLE_LAYOUT_ID;
    private static int APP_CONTAINS_FIELD_GROUP_ID;
    private static int APP_DOES_NOT_BELONG_TO_THREAD_ID;
    private static int MULTI_LANG_APP_ID;
    private static int RESTRICT_APP_ID;
    private static int FORM_LAYOUT_GUEST_SPACE_APP_ID;
    private static int PREVIEW_APP_ID;
    private static int PRE_LIVE_APP_ID;
    private static String ADD_FORMFIELD_API_TOKEN = "xxx";
    private static String VIEW_API_TOKEN = "xxx";
    private static String NO_VIEW_API_TOKEN = "xxx";
    private static String NOT_IN_SPACE_OR_THREAD_API_TOKEN = "xxx";
    private static String GUEST_SPACE_API_TOKEN = "xxx";
    private static String UPDATE_FORMLAYOUT_API_TOKEN = "xxx";

    private App appManagerment;
    private App guestSpaceAppManagerment;
    private App guestSpaceTokenAppManagerment;
    private App addFormFieldTokenAppManagerment;
    private App viewTokenAppManagerment;
    private App noViewTokenAppManagerment;
    private App updateformlayoutTokenAppManagerment;
    private App notInSpaceOrThreadTokenAppManagerment;
    private App addOnlyAppManagerment;
    private App readOnlyAppManagerment;
    private App noAdminAppManagerment;
    private App noAddNoReadAppManagerment;
    private App certAppManagerment;
    private App certGuestAppManagerment;
    private Connection fileConnection;
    private File file;

    @Before
    public void setup() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection passwordAuthConnection = new Connection(TestConstantsSample.DOMAIN, auth);
        passwordAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.appManagerment = new App(passwordAuthConnection);

        Auth fileAuth = new Auth();
        fileAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        this.fileConnection = new Connection(TestConstantsSample.DOMAIN, fileAuth);
        this.fileConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.file = new File(this.fileConnection);

        Auth noAddNoReadAuth = new Auth();
        noAddNoReadAuth.setPasswordAuth("xxx", "xxx");
        Connection noAddNoReadAuthConnection = new Connection(TestConstantsSample.DOMAIN, noAddNoReadAuth);
        noAddNoReadAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.noAddNoReadAppManagerment = new App(noAddNoReadAuthConnection);

        Auth addOnlyAuth = new Auth();
        addOnlyAuth.setPasswordAuth("xxx", "xxx");
        Connection addOnlyAuthConnection = new Connection(TestConstantsSample.DOMAIN, addOnlyAuth);
        addOnlyAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.addOnlyAppManagerment = new App(addOnlyAuthConnection);

        Auth readOnlyAuth = new Auth();
        readOnlyAuth.setPasswordAuth("xxx", "xxx");
        Connection readOnlyAuthConnection = new Connection(TestConstantsSample.DOMAIN, readOnlyAuth);
        readOnlyAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.readOnlyAppManagerment = new App(readOnlyAuthConnection);

        Auth noAdminauth = new Auth();
        noAdminauth.setPasswordAuth("xxx", "xxx");
        Connection noAdminAuthConnection = new Connection(TestConstantsSample.DOMAIN, noAdminauth);
        noAdminAuthConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.noAdminAppManagerment = new App(noAdminAuthConnection);

        Auth guestSpaceAuth = new Auth();
        guestSpaceAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        Connection guestSpacePasswordAuthconnection = new Connection(TestConstantsSample.DOMAIN, guestSpaceAuth,
                TestConstantsSample.GUEST_SPACE_ID);
        guestSpacePasswordAuthconnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.guestSpaceAppManagerment = new App(guestSpacePasswordAuthconnection);

        Auth guestSpaceTokenAuth = new Auth();
        guestSpaceTokenAuth.setApiToken(GUEST_SPACE_API_TOKEN);
        Connection guestSpaceTokenconnection = new Connection(TestConstantsSample.DOMAIN, guestSpaceTokenAuth,
                TestConstantsSample.GUEST_SPACE_ID);
        guestSpaceTokenconnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.guestSpaceTokenAppManagerment = new App(guestSpaceTokenconnection);

        Auth addFormFieldTokenAuth = new Auth();
        addFormFieldTokenAuth.setApiToken(ADD_FORMFIELD_API_TOKEN);
        Connection addFormFieldTokenConnection = new Connection(TestConstantsSample.DOMAIN, addFormFieldTokenAuth);
        addFormFieldTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.addFormFieldTokenAppManagerment = new App(addFormFieldTokenConnection);

        Auth viewTokenAuth = new Auth();
        viewTokenAuth.setApiToken(VIEW_API_TOKEN);
        Connection ViewTokenConnection = new Connection(TestConstantsSample.DOMAIN, viewTokenAuth);
        ViewTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.viewTokenAppManagerment = new App(ViewTokenConnection);

        Auth noViewTokenAuth = new Auth();
        noViewTokenAuth.setApiToken(NO_VIEW_API_TOKEN);
        Connection noViewTokenConnection = new Connection(TestConstantsSample.DOMAIN, noViewTokenAuth);
        noViewTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.noViewTokenAppManagerment = new App(noViewTokenConnection);

        Auth updateformlayoutTokenAuth = new Auth();
        updateformlayoutTokenAuth.setApiToken(UPDATE_FORMLAYOUT_API_TOKEN);
        Connection updateformlayoutTokenConnection = new Connection(TestConstantsSample.DOMAIN, updateformlayoutTokenAuth);
        updateformlayoutTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.updateformlayoutTokenAppManagerment = new App(updateformlayoutTokenConnection);

        Auth notInSpaceOrThreadTokenAuth = new Auth();
        notInSpaceOrThreadTokenAuth.setApiToken(NOT_IN_SPACE_OR_THREAD_API_TOKEN);
        Connection notInSpaceOrThreadTokenConnection = new Connection(TestConstantsSample.DOMAIN,
                notInSpaceOrThreadTokenAuth);
        notInSpaceOrThreadTokenConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.notInSpaceOrThreadTokenAppManagerment = new App(notInSpaceOrThreadTokenConnection);

        Auth certAuth = new Auth();
        certAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection CertConnection = new Connection(TestConstantsSample.SECURE_DOMAIN, certAuth);
        CertConnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.certAppManagerment = new App(CertConnection);

        Auth certGuestAuth = new Auth();
        certGuestAuth.setPasswordAuth(TestConstantsSample.USERNAME, TestConstantsSample.PASSWORD);
        certGuestAuth.setClientCertByPath(TestConstantsSample.CLIENT_CERT_PATH, TestConstantsSample.CLIENT_CERT_PASSWORD);
        Connection certGuestonnection = new Connection(TestConstantsSample.DOMAIN, auth, TestConstantsSample.GUEST_SPACE_ID);
        certGuestonnection.setProxy(TestConstantsSample.PROXY_HOST, TestConstantsSample.PROXY_PORT);
        this.certGuestAppManagerment = new App(certGuestonnection);
    }

    public SingleLineTextField createSingleLineTextField(String fieldCode) {
        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setDefaultValue("single line text");
        sltf.setMaxLength("64");
        sltf.setMinLength("1");
        sltf.setLabel("SingleLineText");
        sltf.setNoLabel(true);
        sltf.setRequired(true);
        sltf.setUnique(false);
        return sltf;
    }

    public RichTextField createRichTextField(String fieldCode) {
        RichTextField richTextField = new RichTextField(fieldCode);
        richTextField.setCode(fieldCode);
        richTextField.setDefaultValue("rich editor default");
        richTextField.setLabel("rich editor");
        richTextField.setNoLabel(false);
        richTextField.setRequired(false);
        return richTextField;
    }

    public MultiLineTextField createMultiLineTextField(String fieldCode) {
        MultiLineTextField multiLineTextField = new MultiLineTextField(fieldCode);
        multiLineTextField.setDefaultValue("multi line default");
        multiLineTextField.setLabel("multi line");
        multiLineTextField.setRequired(false);
        multiLineTextField.setNoLabel(false);
        return multiLineTextField;
    }

    public NumberField createNumberField(String fieldCode) {
        NumberField numberField = new NumberField(fieldCode);
        numberField.setDefaultValue("123");
        numberField.setDigit(true);
        numberField.setDisplayScale("3");
        numberField.setMaxValue("10000");
        numberField.setMinValue("10");
        numberField.setUnique(true);
        numberField.setUnit("$");
        numberField.setUnitPosition(UnitPosition.AFTER);
        numberField.setLabel("number");
        numberField.setRequired(true);
        numberField.setNoLabel(false);
        return numberField;
    }

    public CalculatedField createCalculatedField(String fieldCode) {
        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setDisplayScale("3");
        calculatedField.setExpression("数値");
        calculatedField.setFormat(NumberFormat.NUMBER);
        calculatedField.setHideExpression(false);
        calculatedField.setUnit("$");
        calculatedField.setUnitPosition(UnitPosition.AFTER);
        calculatedField.setLabel("calculated");
        calculatedField.setRequired(false);
        calculatedField.setNoLabel(false);
        return calculatedField;
    }

    public RadioButtonField createRadioButtonField(String fieldCode) {
        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(2, "sample2"));
        radioOption.put("sample3", new OptionData(3, "sample3"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setNoLabel(false);
        radioButtonField.setRequired(true);
        radioButtonField.setLabel("Label Radio");
        radioButtonField.setAlign(AlignLayout.VERTICAL);
        radioButtonField.setRequired(false);
        return radioButtonField;
    }

    public CheckboxField createCheckboxField(String fieldCode) {
        CheckboxField checkboxField = new CheckboxField(fieldCode);

        HashMap<String, OptionData> checkBoxOption = new HashMap<>();
        checkBoxOption.put("sample1", new OptionData(1, "sample1"));
        checkBoxOption.put("sample2", new OptionData(2, "sample2"));
        checkBoxOption.put("sample3", new OptionData(3, "sample3"));
        checkboxField.setOptions(checkBoxOption);

        checkboxField.setAlign(AlignLayout.HORIZONTAL);

        ArrayList<String> defaultValue = new ArrayList<>();
        defaultValue.add("sample1");
        checkboxField.setDefaultValue(defaultValue);

        checkboxField.setLabel("checkbox");
        checkboxField.setNoLabel(false);
        checkboxField.setRequired(false);
        return checkboxField;
    }

    public MultipleSelectField createMultipleSelectField(String fieldCode) {
        MultipleSelectField multipleSelectField = new MultipleSelectField(fieldCode);

        HashMap<String, OptionData> multipleSelectOption = new HashMap<>();
        multipleSelectOption.put("sample1", new OptionData(1, "sample1"));
        multipleSelectOption.put("sample2", new OptionData(2, "sample2"));
        multipleSelectOption.put("sample3", new OptionData(3, "sample3"));
        multipleSelectField.setOptions(multipleSelectOption);

        ArrayList<String> multipleSelectDefaultValue = new ArrayList<>();
        multipleSelectDefaultValue.add("sample1");
        multipleSelectField.setDefaultValue(multipleSelectDefaultValue);

        multipleSelectField.setLabel("multipleSelect");
        multipleSelectField.setNoLabel(false);
        multipleSelectField.setRequired(false);
        return multipleSelectField;
    }

    public DropDownField createDropDownField(String fieldCode) {
        DropDownField dropDownField = new DropDownField(fieldCode);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();
        dropDownOption.put("sample1", new OptionData(1, "sample1"));
        dropDownOption.put("sample2", new OptionData(2, "sample2"));
        dropDownOption.put("sample3", new OptionData(3, "sample3"));
        dropDownField.setOptions(dropDownOption);

        dropDownField.setDefaultValue("sample1");
        dropDownField.setLabel("dropDown");
        dropDownField.setNoLabel(false);
        dropDownField.setRequired(false);
        return dropDownField;
    }

    public DateField createDateField(String fieldCode) {
        DateField dateField = new DateField(fieldCode);
        dateField.setDefaultNowValue(true);
        dateField.setDefaultValue("");
        dateField.setUnique(false);
        dateField.setLabel("date");
        dateField.setRequired(false);
        return dateField;
    }

    public DateTimeField createDateTimeField(String fieldCode) {
        DateTimeField dateTimeField = new DateTimeField(fieldCode);
        dateTimeField.setDefaultNowValue(true);
        dateTimeField.setDefaultValue("");
        dateTimeField.setUnique(false);
        dateTimeField.setLabel("datetime");
        dateTimeField.setRequired(false);
        return dateTimeField;
    }

    public TimeField createTimeField(String fieldCode) {
        TimeField timeField = new TimeField(fieldCode);
        timeField.setDefaultNowValue(true);
        timeField.setDefaultValue("");
        timeField.setLabel("time");
        timeField.setNoLabel(false);
        timeField.setRequired(false);
        return timeField;
    }

    public AttachmentField createAttachmentField(String fieldCode) {
        AttachmentField attachmentField = new AttachmentField(fieldCode);
        attachmentField.setThumbnailSize("150");
        attachmentField.setLabel("file");
        attachmentField.setNoLabel(false);
        attachmentField.setRequired(false);
        return attachmentField;
    }

    public LinkField createLinkField(String fieldCode) {
        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("https://xxx");
        linkField.setMaxLength("64");
        linkField.setMinLength("1");
        linkField.setProtocol(LinkProtocol.WEB);
        linkField.setUnique(false);
        linkField.setLabel("link");
        linkField.setNoLabel(false);
        linkField.setRequired(false);
        return linkField;
    }

    public UserSelectionField createUserSelectionField(String fieldCode) {
        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("xxx");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("xxx");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("xxx");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(memberSelectEntity);
        userList.add(groupSelectEntity);
        userList.add(orgSelectEntity);

        userSelectionField.setEntities(userList);

        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");
        userSelectionField.setNoLabel(false);
        userSelectionField.setRequired(false);

        return userSelectionField;
    }

    public GroupSelectionField createGroupSelectionField(String fieldCode) {
        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("xxx");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);
        groupList.add(memberSelectEntity);

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setLabel("xxx");
        groupSelectionField.setEntities(null);
        groupSelectionField.setNoLabel(false);
        groupSelectionField.setRequired(false);
        return groupSelectionField;
    }

    public DepartmentSelectionField createDepartmentSelectionField(String fieldCode) {
        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("xxx");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("xxx");
        departmentSelectionField.setNoLabel(false);
        departmentSelectionField.setRequired(false);
        return departmentSelectionField;
    }

    public FieldGroup createFieldGroup(String fieldCode) {
        FieldGroup fieldGroup = new FieldGroup(fieldCode);
        fieldGroup.setLabel("group");
        fieldGroup.setNoLabel(false);
        fieldGroup.setOpenGroup(true);
        return fieldGroup;
    }

    public LookupField createLookupField(String fieldCode) {
        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        // 関連付けるアプリ
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        // コピー元のフィールド
        lookupItem.setRelatedKeyField("文字列__1行_");

        // ほかのフィールドのコピー
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        // コピー元のレコードの選択時に表示するフィールド
        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        // 絞り込みの初期設定
        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        // ソートの初期設定
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");
        lookupField.setNoLabel(false);
        lookupField.setRequired(false);
        return lookupField;
    }

    public LookupField createLookupField(String fieldCode, LookupItem lookupItem) {
        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);

        // 絞り込みの初期設定
        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        // ソートの初期設定
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");
        lookupField.setNoLabel(false);
        lookupField.setRequired(false);
        return lookupField;
    }

    public LookupField createLookupField(String fieldCode, RelatedApp relatedApp) {
        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);

        // コピー元のフィールド
        lookupItem.setRelatedKeyField("文字列__1行_");

        // ほかのフィールドのコピー
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        // コピー元のレコードの選択時に表示するフィールド
        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        // 絞り込みの初期設定
        lookupItem.setFilterCond("数値 >= 10");

        // ソートの初期設定
        lookupItem.setSort("数値 desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");
        lookupField.setNoLabel(false);
        lookupField.setRequired(false);
        return lookupField;
    }

    public RelatedRecordsField createRelatedRecordsField(String fieldCode) {
        // 参照するアプリ
        RelatedApp relatedApp = new RelatedApp("1757", "");

        // 表示するレコードの条件
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        // 表示するフィールド
        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        // レコードのソート
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");
        relatedRecordsField.setNoLabel(false);

        return relatedRecordsField;
    }

    public SubTableField createSubTableField(String fieldCode) {
        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext = createSingleLineTextField("tabletext");
        subTableField.addField(tabletext);

        return subTableField;
    }

    public AssigneeField createAssigneeField(String fieldCode) {
        AssigneeField assigneeField = new AssigneeField(fieldCode);
        assigneeField.setLabel("assigneeField");
        assigneeField.setCode("assigneeField");
        assigneeField.setEnabled(true);
        return assigneeField;
    }

    public CategoryField createCategoryField(String fieldCode) {
        CategoryField categoryField = new CategoryField(fieldCode);
        categoryField.setLabel("category");
        categoryField.setEnabled(true);
        return categoryField;
    }

    public StatusField createStatusField(String fieldCode) {
        StatusField statusField = new StatusField(fieldCode);
        statusField.setLabel("status");
        statusField.setEnabled(true);
        return statusField;
    }

    public CreatedTimeField createCreatedTimeField(String fieldCode) {
        CreatedTimeField createdTimeFieldstatusField = new CreatedTimeField(fieldCode);
        createdTimeFieldstatusField.setLabel("createdTime");
        return createdTimeFieldstatusField;
    }

    public CreatorField createCreatorField(String fieldCode) {
        CreatorField creatorField = new CreatorField(fieldCode);
        creatorField.setLabel("creator");
        creatorField.setCode("creator");
        return creatorField;
    }

    public ModifierField createModifierField(String fieldCode) {
        ModifierField modifierField = new ModifierField(fieldCode);
        modifierField.setLabel("modify");
        return modifierField;
    }

    public UpdatedTimeField createUpdatedTimeField(String fieldCode) {
        UpdatedTimeField updatedTimeField = new UpdatedTimeField(fieldCode);
        updatedTimeField.setLabel("updatetime");
        return updatedTimeField;
    }

    public RecordNumberField createRecordNumberField(String fieldCode) {
        RecordNumberField recordNumberField = new RecordNumberField(fieldCode);
        recordNumberField.setLabel("recordNumberField");
        return recordNumberField;
    }

    @Test
    public void testGetAppShouldSuccess() throws KintoneAPIException {
        AppModel app = this.appManagerment.getApp(APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_THREAD_ID), app.getThreadId());
        assertNotNull(app.getCode());
        assertNotNull(app.getName());
        assertNotNull(app.getDescription());
        assertNotNull(app.getCreatedAt());
        assertNotNull(app.getCreator());
        assertNotNull(app.getModifiedAt());
        assertNotNull(app.getModifier());
    }

    @Test
    public void testGetAppShouldSuccessToken() throws KintoneAPIException {
        AppModel app = this.viewTokenAppManagerment.getApp(APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_THREAD_ID), app.getThreadId());
        assertNotNull(app.getCode());
        assertNotNull(app.getName());
        assertNotNull(app.getDescription());
        assertNotNull(app.getCreatedAt());
        assertNotNull(app.getCreator());
        assertNotNull(app.getModifiedAt());
        assertNotNull(app.getModifier());
    }

    @Test
    public void testGetAppShouldSuccessCert() throws KintoneAPIException {
        AppModel app = this.certAppManagerment.getApp(APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(TestConstantsSample.SPACE_THREAD_ID), app.getThreadId());
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
    public void testGetAppShouldFailWhenDoesNotHavePermissionViewAppToken() throws KintoneAPIException {
        this.noViewTokenAppManagerment.getApp(RESTRICT_APP_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenDoesNotHavePermissionViewAppCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(RESTRICT_APP_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenInputAppIdNull() throws KintoneAPIException {
        this.appManagerment.getApp(null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenInputAppIdNullCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(null);
    }

    @Test
    public void testGetAppShouldSucessWhenUsingAPITokenHasViewRecordsPermission() throws KintoneAPIException {
        AppModel app = appManagerment.getApp(APP_ID);
        assertEquals(Integer.valueOf(APP_ID), app.getAppId());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppShouldFailWhenUsingAPITokenDoesNotHasViewRecordsPermission() throws KintoneAPIException {
        this.noViewTokenAppManagerment.getApp(APP_ID);
    }

    @Test
    public void testGetAppShouldSuccessWhenAppIsNotInSpaceOrThead() throws KintoneAPIException {
        AppModel app = this.appManagerment.getApp(APP_DOES_NOT_BELONG_TO_THREAD_ID);
        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_DOES_NOT_BELONG_TO_THREAD_ID), app.getAppId());
        assertEquals(null, app.getSpaceId());
        assertEquals(null, app.getThreadId());
    }

    @Test
    public void testGetAppShouldSuccessWhenAppIsNotInSpaceOrTheadToken() throws KintoneAPIException {
        AppModel app = this.notInSpaceOrThreadTokenAppManagerment.getApp(APP_DOES_NOT_BELONG_TO_THREAD_ID);
        assertNotNull(app);
        assertEquals(Integer.valueOf(APP_DOES_NOT_BELONG_TO_THREAD_ID), app.getAppId());
        assertEquals(null, app.getSpaceId());
        assertEquals(null, app.getThreadId());
    }

    @Test
    public void testGetAppShouldSuccessWhenAppIsNotInSpaceOrTheadCert() throws KintoneAPIException {
        AppModel app = this.certAppManagerment.getApp(APP_DOES_NOT_BELONG_TO_THREAD_ID);
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
    public void testGetAppShouldFailWhenRetrieveAppInGuestSpaceCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(1631);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsNotExist() throws KintoneAPIException {
        this.appManagerment.getApp(100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsNotExistCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(100000);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsMinus() throws KintoneAPIException {
        this.appManagerment.getApp(-1);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsMinusCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(-1);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsZero() throws KintoneAPIException {
        this.appManagerment.getApp(0);
    }

    @Test(expected = KintoneAPIException.class)
    public void GetAppShouldFailWhenIdIsZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getApp(0);
    }

    @Test
    public void testGetAppInGuestSpaceShouldSuccess()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        AppModel app = this.guestSpaceAppManagerment.getApp(TestConstantsSample.GUEST_SPACE_APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(150), app.getThreadId());
        assertEquals("guestApp", app.getCode());
        assertEquals("Guest Space java sdk Test", app.getName());
        assertEquals("<div>Guest Space Test</div>", app.getDescription());

        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(isoDateFormat.parse("2018-08-22T02:10:08.000Z"), app.getCreatedAt());

        Member creator = app.getCreator();
        assertNotNull(creator);
        assertEquals("yfang", creator.getCode());
        assertEquals("yfang", creator.getName());

        Member modifier = app.getModifier();
        assertNotNull(modifier);
        assertEquals("yfang", modifier.getCode());
        assertEquals("yfang", modifier.getName());
    }

    @Test
    public void testGetAppInGuestSpaceShouldSuccessToken()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        AppModel app = this.guestSpaceTokenAppManagerment.getApp(TestConstantsSample.GUEST_SPACE_APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(150), app.getThreadId());
        assertEquals("guestApp", app.getCode());
        assertEquals("Guest Space java sdk Test", app.getName());
        assertEquals("<div>Guest Space Test</div>", app.getDescription());

        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(isoDateFormat.parse("2018-08-22T02:10:08.000Z"), app.getCreatedAt());

        Member creator = app.getCreator();
        assertNotNull(creator);
        assertEquals("yfang", creator.getCode());
        assertEquals("yfang", creator.getName());

        Member modifier = app.getModifier();
        assertNotNull(modifier);
        assertEquals("yfang", modifier.getCode());
        assertEquals("yfang", modifier.getName());
    }

    @Test
    public void testGetAppInGuestSpaceShouldSuccessCert()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        AppModel app = this.certGuestAppManagerment.getApp(TestConstantsSample.GUEST_SPACE_APP_ID);

        assertNotNull(app);
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_APP_ID), app.getAppId());
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_ID), app.getSpaceId());
        assertEquals(Integer.valueOf(150), app.getThreadId());
        assertEquals("guestApp", app.getCode());
        assertEquals("Guest Space java sdk Test", app.getName());
        assertEquals("<div>Guest Space Test</div>", app.getDescription());

        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(isoDateFormat.parse("2018-08-22T02:10:08.000Z"), app.getCreatedAt());

        Member creator = app.getCreator();
        assertNotNull(creator);
        assertEquals("yfang", creator.getCode());
        assertEquals("yfang", creator.getName());

        Member modifier = app.getModifier();
        assertNotNull(modifier);
        assertEquals("yfang", modifier.getCode());
        assertEquals("yfang", modifier.getName());
    }

    @Test
    public void testGetAppsShouldSuccess() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsShouldSuccessCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getApps(600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsShouldSuccessThenSkippedBasedOnTheOffsetCert() throws KintoneAPIException {
        List<AppModel> setOffset = this.certAppManagerment.getApps(600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testDoNotSetOffsetIsZero() throws KintoneAPIException {
        List<AppModel> noneOffset = this.appManagerment.getApps(null, null);
        List<AppModel> setOffset = this.appManagerment.getApps(0, null);
        assertEquals(noneOffset.size(), setOffset.size());
    }

    @Test
    public void testDoNotSetOffsetIsZeroCert() throws KintoneAPIException {
        List<AppModel> noneOffset = this.certAppManagerment.getApps(null, null);
        List<AppModel> setOffset = this.certAppManagerment.getApps(0, null);
        assertEquals(noneOffset.size(), setOffset.size());
    }

    @Test
    public void testGetAppsShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsShouldSuccessMaximumOfThelimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getApps(null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testDoNotSetlimitIsHundred() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getApps(null, null);
        List<AppModel> limitHundred = this.appManagerment.getApps(null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testDoNotSetlimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> limitNull = this.certAppManagerment.getApps(null, null);
        List<AppModel> limitHundred = this.certAppManagerment.getApps(null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsInGuestSpaceShouldSuccess()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        List<AppModel> apps = this.guestSpaceAppManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsInGuestSpaceShouldSuccessCert()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        List<AppModel> apps = this.certGuestAppManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getApps(null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueOverHundredCert() throws KintoneAPIException {
        this.certAppManagerment.getApps(null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getApps(null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        this.certAppManagerment.getApps(null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongOffsetValue() throws KintoneAPIException {
        this.appManagerment.getApps(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongOffsetValueCert() throws KintoneAPIException {
        this.certAppManagerment.getApps(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getApps(null, null);
    }

    @Test
    public void testGetAppsShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getApps(0, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsShouldReturnListHasOneElementWhenGivenLimitIsOneCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getApps(0, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsEmpty() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsEmptyCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldReturnListHasOneElementWhenGivenLimitIsOneCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByIDs(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessThenSkippedBasedOnTheOffsetCert() throws KintoneAPIException {
        List<AppModel> setOffset = this.certAppManagerment.getAppsByIDs(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessMaximumOfThelimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithIdsInGuestSpaceShouldSuccess()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);
        List<AppModel> apps = this.guestSpaceAppManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsInGuestSpaceShouldSuccessCert()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);
        List<AppModel> apps = this.certGuestAppManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsNull() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByIDs(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenIdsNullCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByIDs(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetLimitCert() throws KintoneAPIException {
        List<AppModel> limitNull = this.certAppManagerment.getAppsByIDs(null, null, null);
        List<AppModel> limitHundred = this.certAppManagerment.getAppsByIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByIDs(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessDoNotSetOffsetCert() throws KintoneAPIException {
        List<AppModel> offsetNull = this.certAppManagerment.getAppsByIDs(null, null, null);
        List<AppModel> offsetZero = this.certAppManagerment.getAppsByIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccess() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_CONTAINS_NORMAL_LAYOUT_ID);
        appIds.add(APP_CONTAINS_TABLE_LAYOUT_ID);
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_CONTAINS_NORMAL_LAYOUT_ID);
        appIds.add(APP_CONTAINS_TABLE_LAYOUT_ID);
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(appIds, null, null);
        assertEquals(2, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByIDs(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByIDs(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByIDs(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenWrongOffsetValueLessThanZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByIDs(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getAppsByIDs(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenInvalidIds() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(-1);
        appIds.add(-2);
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenInvalidIdsCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(-1);
        appIds.add(-2);
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenOverHundred() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        ArrayList<Integer> asList = new ArrayList<Integer>(Arrays.asList(arr));
        this.appManagerment.getAppsByIDs(asList, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenOverHundredCert() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        ArrayList<Integer> ids = new ArrayList<Integer>(Arrays.asList(arr));
        this.certAppManagerment.getAppsByIDs(ids, null, null);
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenListEmpty() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccessWhenListEmptyCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        List<AppModel> apps = this.certAppManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccess() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        codes.add("tablelayout");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        codes.add("tablelayout");
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(codes, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessBigBody() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        for (int i = 0; i < 99; i++) {
            codes.add("normallayout" + i);
        }
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessBigBodyCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("normallayout");
        for (int i = 0; i < 99; i++) {
            codes.add("normallayout" + i);
        }
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeInGuestSpaceShouldSuccess()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("guestApp");
        List<AppModel> apps = this.guestSpaceAppManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeInGuestSpaceShouldSuccessCert()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("guestApp");
        List<AppModel> apps = this.certGuestAppManagerment.getAppsByCodes(codes, null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenListEmpty() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenListEmptyCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenCodesIsNull() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccessWhenCodesIsNullCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailForEmptyArray() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailForEmptyArrayCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("");
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(codes, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodesShouldReturnEmptyArrayWhenGivenNonExistCodes() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("wrongCodeOne");
        codes.add("wrongCodeTwo");
        List<AppModel> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodesShouldReturnEmptyArrayWhenGivenNonExistCodesCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("wrongCodeOne");
        codes.add("wrongCodeTwo");
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(codes, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByCodes(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByCodes(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetLimitCert() throws KintoneAPIException {
        List<AppModel> limitNull = this.certAppManagerment.getAppsByCodes(null, null, null);
        List<AppModel> limitHundred = this.certAppManagerment.getAppsByCodes(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByCodes(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByCodes(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessDoNotSetOffsetCert() throws KintoneAPIException {
        List<AppModel> offsetNull = this.certAppManagerment.getAppsByCodes(null, null, null);
        List<AppModel> offsetZero = this.certAppManagerment.getAppsByCodes(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenInvalidFormatCodes() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("Invalid Code");
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenInvalidFormatCodesCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("Invalid Code");
        this.certAppManagerment.getAppsByCodes(codes, null, null);
    }

    @Test
    public void testGetAppsWithCodesShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodesShouldReturnListHasOneElementWhenGivenLimitIsOneCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByCodes(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessThenSkippedBasedOnTheOffsetCert() throws KintoneAPIException {
        List<AppModel> setOffset = this.certAppManagerment.getAppsByCodes(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByCodes(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithCodesShouldSuccessMaximumOfThelimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByCodes(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getAppsByCodes(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverThanSixtyFour() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverThanSixtyFourCert() throws KintoneAPIException {
        ArrayList<String> codes = new ArrayList<String>();
        codes.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.certAppManagerment.getAppsByCodes(codes, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByCodes(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongLimitValueOverHundredCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByCodes(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByCodes(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenWrongOffsetValueLessThanZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByCodes(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverHundred() throws KintoneAPIException {
        String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "g1", "h1", "i1",
                "j1", "k1", "l1", "m1", "n1", "o1", "p1", "q1", "r1", "s1", "t1", "u1", "v1", "w1", "x1", "y1", "z1",
                "a5", "a6", "a7", "a8", "a9", "a0", "a1", "a2", "a3", "a4", "b5", "b6", "b7", "b8", "d9", "j0", "j1",
                "j2", "k3", "k4", "d5", "d6", "d7", "d8", "d9", "c0", "c1", "c2", "c3", "c4", "c5", "x6", "x7", "x8",
                "x9", "x0", "z1", "z2", "z3", "z4", "z5", "z6", "z7", "z8", "z9", "a00", "a01" };
        ArrayList<String> codes = new ArrayList<String>(Arrays.asList(arr));
        this.appManagerment.getAppsByCodes(codes, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenOverHundredCert() throws KintoneAPIException {
        String[] arr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "g1", "h1", "i1",
                "j1", "k1", "l1", "m1", "n1", "o1", "p1", "q1", "r1", "s1", "t1", "u1", "v1", "w1", "x1", "y1", "z1",
                "a5", "a6", "a7", "a8", "a9", "a0", "a1", "a2", "a3", "a4", "b5", "b6", "b7", "b8", "d9", "j0", "j1",
                "j2", "k3", "k4", "d5", "d6", "d7", "d8", "d9", "c0", "c1", "c2", "c3", "c4", "c5", "x6", "x7", "x8",
                "x9", "x0", "z1", "z2", "z3", "z4", "z5", "z6", "z7", "z8", "z9", "a00", "a01" };
        ArrayList<String> codes = new ArrayList<String>(Arrays.asList(arr));
        this.certAppManagerment.getAppsByCodes(codes, null, null);
    }

    @Test
    public void testGetAppsWithNameShouldSuccess() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("Test table layout", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName("Test table layout", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppSWithNameInGuestSpaceShouldSuccess()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        List<AppModel> apps = this.guestSpaceAppManagerment.getAppsByName("Guest Space Test", null, null);
        assertEquals(0, apps.size());
    }

    @Test
    public void testGetAppSWithNameInGuestSpaceShouldSuccessCert()
            throws KintoneAPIException, ParseException, InterruptedException, ExecutionException {
        List<AppModel> apps = this.certGuestAppManagerment.getAppsByName("Guest Space Test", null, null);
        assertEquals(0, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenNameIsEmpty() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("", null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenNameIsEmptyCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName("", null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenGivenOtherLanguage() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("SHANGHAI", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessWhenGivenOtherLanguageCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName("SHANGHAI", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnEmptyArrayWhenGivenNonExistName() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName("Non exits name", null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldReturnEmptyArrayWhenGivenNonExistNameCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName("Non exits name", null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldNotReturnEmptyArrayWhenGivenNoName() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldNotReturnEmptyArrayWhenGivenNoNameCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessMaximumOfThelimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsByName(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnListHasOneElementWhenGivenLimitIsOneCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsByName(null, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        List<AppModel> setOffset = this.appManagerment.getAppsByName(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessThenSkippedBasedOnTheOffsetCert() throws KintoneAPIException {
        List<AppModel> setOffset = this.certAppManagerment.getAppsByName(null, 600, null);
        assertEquals(98, setOffset.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsByName(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsByName(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetLimitCert() throws KintoneAPIException {
        List<AppModel> limitNull = this.certAppManagerment.getAppsByName(null, null, null);
        List<AppModel> limitHundred = this.certAppManagerment.getAppsByName(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsByName(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsByName(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithNameShouldSuccessDoNotSetOffsetCert() throws KintoneAPIException {
        List<AppModel> offsetNull = this.certAppManagerment.getAppsByName(null, null, null);
        List<AppModel> offsetZero = this.certAppManagerment.getAppsByName(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByName(null, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongLimitValueOverHundredCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByName(null, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        this.appManagerment.getAppsByName(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWrongOffsetValueLessThanZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getAppsByName(null, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getAppsByName(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenOverThanSixtyFour() throws KintoneAPIException {
        String name = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.appManagerment.getAppsByName(name, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithNameShouldFailWhenOverThanSixtyFourCert() throws KintoneAPIException {
        String name = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        this.certAppManagerment.getAppsByName(name, null, null);
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccess() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(39, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(39, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessReturnListHasOneElementWhenGivenLimitIsOne()
            throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessReturnListHasOneElementWhenGivenLimitIsOneCert()
            throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, 1);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessThenSkippedBasedOnTheOffset() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, 1, null);
        assertEquals(38, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessThenSkippedBasedOnTheOffsetCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(spaceIds, 1, null);
        assertEquals(38, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessMaximumOfThelimitIsHundred() throws KintoneAPIException {
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessMaximumOfThelimitIsHundredCert() throws KintoneAPIException {
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(null, null, null);
        assertEquals(100, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessGuestSpace() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(4, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessGuestSpaceCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(4, apps.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getAppsBySpaceIDs(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueLessThanOne() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueLessThanOneCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongLimitValueOverHundredCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueOverHundred() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongLimitValueOverHundredCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, 101);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenWrongOffsetValueLessThanZeroCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongOffsetValueLessThanZero() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithGuestSpaceIdShouldFailWhenWrongOffsetValueLessThanZeroCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(TestConstantsSample.GUEST_SPACE_ID);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, -1, null);
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetLimit() throws KintoneAPIException {
        List<AppModel> limitNull = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> limitHundred = this.appManagerment.getAppsBySpaceIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetLimitCert() throws KintoneAPIException {
        List<AppModel> limitNull = this.certAppManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> limitHundred = this.certAppManagerment.getAppsBySpaceIDs(null, null, 100);
        assertEquals(limitNull.size(), limitHundred.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetOffset() throws KintoneAPIException {
        List<AppModel> offsetNull = this.appManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> offsetZero = this.appManagerment.getAppsBySpaceIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccessDoNotSetOffsetCert() throws KintoneAPIException {
        List<AppModel> offsetNull = this.certAppManagerment.getAppsBySpaceIDs(null, null, null);
        List<AppModel> offsetZero = this.certAppManagerment.getAppsBySpaceIDs(null, 0, null);
        assertEquals(offsetNull.size(), offsetZero.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldReturnEmptyArrayWhenGivenNonExistSpaceId() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(10);
        List<AppModel> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldReturnEmptyArrayWhenGivenNonExistSpaceIdCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(10);
        List<AppModel> apps = this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenInvalidSpaceId() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(-1);
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenInvalidSpaceIdCert() throws KintoneAPIException {
        ArrayList<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(-1);
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenOverThanHundred() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        ArrayList<Integer> spaceIds = new ArrayList<Integer>(Arrays.asList(arr));
        this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenOverThanHundredCert() throws KintoneAPIException {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
                79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
        ArrayList<Integer> spaceIds = new ArrayList<Integer>(Arrays.asList(arr));
        this.certAppManagerment.getAppsBySpaceIDs(spaceIds, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenWhenUsingTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getFormFields(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNoAppId() throws KintoneAPIException {
        this.appManagerment.getFormFields(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNoAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(null, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNonExistAppId() throws KintoneAPIException {
        this.appManagerment.getFormFields(1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenNonExistAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenMinus() throws KintoneAPIException {
        this.appManagerment.getFormFields(-1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenMinusCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(-1, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenZero() throws KintoneAPIException {
        this.appManagerment.getFormFields(0, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenGivenZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(0, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenRetrievePreviewApp() throws KintoneAPIException {
        this.appManagerment.getFormFields(1649, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenRetrievePreviewAppCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(1649, null, null);
    }

    // KCB-656
    @Test
    public void testGetFormFieldsShouldSuccess() throws KintoneAPIException {
        FormFields formfields = this.appManagerment.getFormFields(APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(APP_ID), formfields.getApp());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(44, properties.size());
    }

    // KCB-656
    @Test
    public void testGetFormFieldsShouldSuccessCert() throws KintoneAPIException {
        FormFields formfields = this.certAppManagerment.getFormFields(APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(APP_ID), formfields.getApp());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(44, properties.size());
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
    public void testGetFormFieldsWithPreLiveShouldSuccessCert() throws KintoneAPIException {
        FormFields formfields = this.certAppManagerment.getFormFields(PRE_LIVE_APP_ID, null, null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(PRE_LIVE_APP_ID), formfields.getApp());
        assertEquals(Integer.valueOf(3), formfields.getRevision());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(9, properties.size());
    }

    @Test
    public void testGetFormFieldsShouldSuccessGuestSpace() throws KintoneAPIException {
        FormFields formfields = this.guestSpaceAppManagerment.getFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, null,
                null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_APP_ID), formfields.getApp());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(13, properties.size());
    }

    @Test
    public void testGetFormFieldsShouldSuccessGuestSpaceCert() throws KintoneAPIException {
        FormFields formfields = this.certGuestAppManagerment.getFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, null,
                null);
        assertNotNull(formfields);
        assertEquals(Integer.valueOf(TestConstantsSample.GUEST_SPACE_APP_ID), formfields.getApp());
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(13, properties.size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenDoesNotHavePermissionViewRecords() throws KintoneAPIException {
        this.appManagerment.getFormFields(192, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenDoesNotHavePermissionViewRecordsCert() throws KintoneAPIException {
        this.certAppManagerment.getFormFields(192, null, false);
    }

    @Test
    public void testGetFormFieldsShouldSuccessWhenRetrivePreviewApp() throws KintoneAPIException {
        FormFields formfields = this.appManagerment.getFormFields(APP_ID, null, true);
        assertNotNull(formfields);
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(44, properties.size());
    }

    @Test
    public void testGetFormFieldsShouldSuccessWhenRetrivePreviewAppCert() throws KintoneAPIException {
        FormFields formfields = this.certAppManagerment.getFormFields(APP_ID, null, true);
        assertNotNull(formfields);
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(44, properties.size());
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

    @Test
    public void testGetFormFieldsShouldReturnCorrectLanguageSettingCert() throws KintoneAPIException {
        // test with English language setting
        FormFields formfields = this.certAppManagerment.getFormFields(MULTI_LANG_APP_ID, LanguageSetting.EN, null);
        assertNotNull(formfields);
        Map<String, Field> properties = formfields.getProperties();
        assertNotNull(properties);
        assertEquals(8, properties.size());

        Field recordNumber = properties.get("Record_number");
        assertTrue(recordNumber instanceof RecordNumberField);
        assertEquals("Record No.", ((RecordNumberField) recordNumber).getLabel());
        // Test with Japanese language setting
        formfields = this.certAppManagerment.getFormFields(MULTI_LANG_APP_ID, LanguageSetting.JA, null);
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

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetFormFieldsShouldFailWhenRetrievePreviewAppButAccountDoesNotHaveAppManagePermissionCert()
            throws KintoneAPIException {
        this.certAppManagerment.getFormFields(1649, null, true);
    }

    // KCB-661
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

    // KCB-661
    @Test
    public void testGetFormLayoutWithNormalLayoutShouldSuccessCert() throws KintoneAPIException {
        FormLayout formLayout = this.certAppManagerment.getFormLayout(APP_CONTAINS_NORMAL_LAYOUT_ID, null);
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
    public void testGetFormLayoutWithFieldGroupShouldSuccessCert() throws KintoneAPIException {
        FormLayout formLayout = this.certAppManagerment.getFormLayout(APP_CONTAINS_FIELD_GROUP_ID, null);
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
    public void testGetFormLayoutWithTableShouldSuccessCert() throws KintoneAPIException {
        FormLayout formLayout = this.certAppManagerment.getFormLayout(APP_CONTAINS_TABLE_LAYOUT_ID, null);
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
        FormLayout formLayout = this.guestSpaceAppManagerment.getFormLayout(TestConstantsSample.GUEST_SPACE_APP_ID, null);
        assertNotNull(formLayout);
        assertEquals(4, formLayout.getLayout().size());
    }

    @Test
    public void testGetFormLayoutShouldSuccessGuestSpaceCert() throws KintoneAPIException {
        FormLayout formLayout = this.certGuestAppManagerment.getFormLayout(TestConstantsSample.GUEST_SPACE_APP_ID, null);
        assertNotNull(formLayout);
        assertEquals(4, formLayout.getLayout().size());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenUseToken() throws KintoneAPIException {
        this.viewTokenAppManagerment.getFormLayout(APP_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNoAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNoAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveInvalidAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(-1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveZero() throws KintoneAPIException {
        this.appManagerment.getFormLayout(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNonExistAppId() throws KintoneAPIException {
        this.appManagerment.getFormLayout(1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenGiveNonExistAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(1, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenDoesNotHavePermissionViewRecords() throws KintoneAPIException {
        this.appManagerment.getFormLayout(192, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenDoesNotHavePermissionViewRecordsCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(192, false);
    }

    @Test
    public void testGetFormLayoutShouldSuccessWhenRetrievePreviewApp() throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(PREVIEW_APP_ID, true);
        assertNotNull(formLayout);
        List<ItemLayout> layout = formLayout.getLayout();
        assertNotNull(layout);
        assertEquals(3, layout.size());
    }

    @Test
    public void testGetFormLayoutShouldSuccessWhenRetrievePreviewAppCert() throws KintoneAPIException {
        FormLayout formLayout = this.certAppManagerment.getFormLayout(PREVIEW_APP_ID, true);
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
    public void testGetFormLayoutShouldFailWhenRetrievePreviewAppCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(1649, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrieveInvalidPreviewApp() throws KintoneAPIException {
        this.appManagerment.getFormLayout(16501, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrieveInvalidPreviewAppCert() throws KintoneAPIException {
        this.certAppManagerment.getFormLayout(16501, null);
    }

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrievePreviewAppButAccountDoesNotHaveAppManagePermission()
            throws KintoneAPIException {
        FormLayout formLayout = this.appManagerment.getFormLayout(145, true);
        assertNotNull(formLayout);
    }

    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetFormLayoutShouldFailWhenRetrievePreviewAppButAccountDoesNotHaveAppManagePermissionCert()
            throws KintoneAPIException {
        FormLayout formLayout = certAppManagerment.getFormLayout(145, true);
        assertNotNull(formLayout);
    }

    @Test
    public void testAddFormFieldsShouldSuccess() throws KintoneAPIException {
        String fieldCode = "radioFieldCode";
        String singeLineFieldCode = "Singline_Text";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        fields.add(singeLineFieldCode);

        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField addNewRadioField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> optionArray = new HashMap<String, OptionData>();
        optionArray.put("1", new OptionData(1, "1"));
        optionArray.put("2", new OptionData(2, "2"));
        optionArray.put("3", new OptionData(3, "3"));

        addNewRadioField.setOptions(optionArray);
        addNewRadioField.setNoLabel(false);
        addNewRadioField.setRequired(true);
        addNewRadioField.setLabel("Label Radio");
        addNewRadioField.setAlign(AlignLayout.VERTICAL);

        HashMap<String, Field> properties = new HashMap<String, Field>();
        properties.put(fieldCode, addNewRadioField);

        SingleLineTextField singleLineTxt = new SingleLineTextField(singeLineFieldCode);
        singleLineTxt.setLabel("Single Line Text");
        singleLineTxt.setRequired(true);

        properties.put(singeLineFieldCode, singleLineTxt);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessCert() throws KintoneAPIException {
        String fieldCode = "radioFieldCode";
        String singeLineFieldCode = "Singline_Text";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        fields.add(singeLineFieldCode);

        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField addNewRadioField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> optionArray = new HashMap<String, OptionData>();
        optionArray.put("1", new OptionData(1, "1"));
        optionArray.put("2", new OptionData(2, "2"));
        optionArray.put("3", new OptionData(3, "3"));

        addNewRadioField.setOptions(optionArray);
        addNewRadioField.setNoLabel(false);
        addNewRadioField.setRequired(true);
        addNewRadioField.setLabel("Label Radio");
        addNewRadioField.setAlign(AlignLayout.VERTICAL);

        HashMap<String, Field> properties = new HashMap<String, Field>();
        properties.put(fieldCode, addNewRadioField);

        SingleLineTextField singleLineTxt = new SingleLineTextField(singeLineFieldCode);
        singleLineTxt.setLabel("Single Line Text");
        singleLineTxt.setRequired(true);

        properties.put(singeLineFieldCode, singleLineTxt);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenCustomizeFieldCode() throws KintoneAPIException {
        String fieldCode = "__a__";
        ArrayList<String> fields = new ArrayList<>();

        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField addNewRadioField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> optionArray = new HashMap<String, OptionData>();
        optionArray.put("1", new OptionData(1, "1"));

        addNewRadioField.setOptions(optionArray);
        addNewRadioField.setNoLabel(false);
        addNewRadioField.setRequired(true);
        addNewRadioField.setLabel("Label Radio");
        addNewRadioField.setAlign(AlignLayout.VERTICAL);

        HashMap<String, Field> properties = new HashMap<String, Field>();
        properties.put(fieldCode, addNewRadioField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenCustomizeFieldCodeCert() throws KintoneAPIException {
        String fieldCode = "__a__";
        ArrayList<String> fields = new ArrayList<>();

        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField addNewRadioField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> optionArray = new HashMap<String, OptionData>();
        optionArray.put("1", new OptionData(1, "1"));

        addNewRadioField.setOptions(optionArray);
        addNewRadioField.setNoLabel(false);
        addNewRadioField.setRequired(true);
        addNewRadioField.setLabel("Label Radio");
        addNewRadioField.setAlign(AlignLayout.VERTICAL);

        HashMap<String, Field> properties = new HashMap<String, Field>();
        properties.put(fieldCode, addNewRadioField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenLookupPickerFieldsIsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenLookupPickerFieldsIsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenLookupFieldMappingsIsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__1行_");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenLookupFieldMappingsIsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__1行_");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenEntitesIsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(memberSelectEntity);
        userList.add(groupSelectEntity);
        userList.add(orgSelectEntity);

        userSelectionField.setEntities(null);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenEntitesIsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(memberSelectEntity);
        userList.add(groupSelectEntity);
        userList.add(orgSelectEntity);

        userSelectionField.setEntities(null);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWithGroupAndOrganization() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String gsfieldCode = "a";
        String dsfieldCode = "b";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(gsfieldCode);
        fields.add(dsfieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        GroupSelectionField gsField = createGroupSelectionField(gsfieldCode);
        DepartmentSelectionField dsField = createDepartmentSelectionField(dsfieldCode);

        properties.put(gsfieldCode, gsField);
        properties.put(dsfieldCode, dsField);
        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWithGroupAndOrganizationCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String gsfieldCode = "a";
        String dsfieldCode = "b";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(gsfieldCode);
        fields.add(dsfieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        GroupSelectionField gsField = createGroupSelectionField(gsfieldCode);
        DepartmentSelectionField dsField = createDepartmentSelectionField(dsfieldCode);

        properties.put(gsfieldCode, gsField);
        properties.put(dsfieldCode, dsField);
        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenSetFilterCondOnRelatedRecords() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        RelatedRecordsField rrField = createRelatedRecordsField(fieldCode);
        properties.put(fieldCode, rrField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenSetFilterCondOnRelatedRecordsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        RelatedRecordsField rrField = createRelatedRecordsField(fieldCode);
        properties.put(fieldCode, rrField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenAddAllFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        ArrayList<String> fields = new ArrayList<>();
        for (int m = 0; m < 21; m++) {
            fields.add(fieldCode + m);
        }
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        int i = 0;
        SingleLineTextField sglineField = createSingleLineTextField(fieldCode + i++);
        RichTextField rtField = createRichTextField(fieldCode + i++);
        MultiLineTextField mtlineField = createMultiLineTextField(fieldCode + i++);
        NumberField numberField = createNumberField(fieldCode + i++);
        CalculatedField calField = createCalculatedField(fieldCode + i++);
        RadioButtonField radioField = createRadioButtonField(fieldCode + i++);
        CheckboxField checkBoxField = createCheckboxField(fieldCode + i++);
        MultipleSelectField mtSelectField = createMultipleSelectField(fieldCode + i++);
        DropDownField dropDownField = createDropDownField(fieldCode + i++);
        DateField dateField = createDateField(fieldCode + i++);
        DateTimeField dateTimeField = createDateTimeField(fieldCode + i++);
        TimeField timeField = createTimeField(fieldCode + i++);
        AttachmentField attmentField = createAttachmentField(fieldCode + i++);
        LinkField linkField = createLinkField(fieldCode + i++);
        UserSelectionField userSelectField = createUserSelectionField(fieldCode + i++);
        GroupSelectionField groupSelectField = createGroupSelectionField(fieldCode + i++);
        DepartmentSelectionField dpSelectField = createDepartmentSelectionField(fieldCode + i++);
        FieldGroup fieldGroup = createFieldGroup(fieldCode + i++);
        LookupField lkupField = createLookupField(fieldCode + i++);
        RelatedRecordsField rrField = createRelatedRecordsField(fieldCode + i++);
        SubTableField stField = createSubTableField(fieldCode + i++);

        int j = 0;
        properties.put(fieldCode + j++, sglineField);
        properties.put(fieldCode + j++, rtField);
        properties.put(fieldCode + j++, mtlineField);
        properties.put(fieldCode + j++, numberField);
        properties.put(fieldCode + j++, calField);
        properties.put(fieldCode + j++, radioField);
        properties.put(fieldCode + j++, checkBoxField);
        properties.put(fieldCode + j++, mtSelectField);
        properties.put(fieldCode + j++, dropDownField);
        properties.put(fieldCode + j++, dateField);
        properties.put(fieldCode + j++, dateTimeField);
        properties.put(fieldCode + j++, timeField);
        properties.put(fieldCode + j++, attmentField);
        properties.put(fieldCode + j++, linkField);
        properties.put(fieldCode + j++, userSelectField);
        properties.put(fieldCode + j++, groupSelectField);
        properties.put(fieldCode + j++, dpSelectField);
        properties.put(fieldCode + j++, fieldGroup);
        properties.put(fieldCode + j++, lkupField);
        properties.put(fieldCode + j++, rrField);
        properties.put(fieldCode + j++, stField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenAddAllFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        ArrayList<String> fields = new ArrayList<>();
        for (int m = 0; m < 21; m++) {
            fields.add(fieldCode + m);
        }
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        int i = 0;
        SingleLineTextField sglineField = createSingleLineTextField(fieldCode + i++);
        RichTextField rtField = createRichTextField(fieldCode + i++);
        MultiLineTextField mtlineField = createMultiLineTextField(fieldCode + i++);
        NumberField numberField = createNumberField(fieldCode + i++);
        CalculatedField calField = createCalculatedField(fieldCode + i++);
        RadioButtonField radioField = createRadioButtonField(fieldCode + i++);
        CheckboxField checkBoxField = createCheckboxField(fieldCode + i++);
        MultipleSelectField mtSelectField = createMultipleSelectField(fieldCode + i++);
        DropDownField dropDownField = createDropDownField(fieldCode + i++);
        DateField dateField = createDateField(fieldCode + i++);
        DateTimeField dateTimeField = createDateTimeField(fieldCode + i++);
        TimeField timeField = createTimeField(fieldCode + i++);
        AttachmentField attmentField = createAttachmentField(fieldCode + i++);
        LinkField linkField = createLinkField(fieldCode + i++);
        UserSelectionField userSelectField = createUserSelectionField(fieldCode + i++);
        GroupSelectionField groupSelectField = createGroupSelectionField(fieldCode + i++);
        DepartmentSelectionField dpSelectField = createDepartmentSelectionField(fieldCode + i++);
        FieldGroup fieldGroup = createFieldGroup(fieldCode + i++);
        LookupField lkupField = createLookupField(fieldCode + i++);
        RelatedRecordsField rrField = createRelatedRecordsField(fieldCode + i++);
        SubTableField stField = createSubTableField(fieldCode + i++);

        int j = 0;
        properties.put(fieldCode + j++, sglineField);
        properties.put(fieldCode + j++, rtField);
        properties.put(fieldCode + j++, mtlineField);
        properties.put(fieldCode + j++, numberField);
        properties.put(fieldCode + j++, calField);
        properties.put(fieldCode + j++, radioField);
        properties.put(fieldCode + j++, checkBoxField);
        properties.put(fieldCode + j++, mtSelectField);
        properties.put(fieldCode + j++, dropDownField);
        properties.put(fieldCode + j++, dateField);
        properties.put(fieldCode + j++, dateTimeField);
        properties.put(fieldCode + j++, timeField);
        properties.put(fieldCode + j++, attmentField);
        properties.put(fieldCode + j++, linkField);
        properties.put(fieldCode + j++, userSelectField);
        properties.put(fieldCode + j++, groupSelectField);
        properties.put(fieldCode + j++, dpSelectField);
        properties.put(fieldCode + j++, fieldGroup);
        properties.put(fieldCode + j++, lkupField);
        properties.put(fieldCode + j++, rrField);
        properties.put(fieldCode + j++, stField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField stField = createSubTableField(fieldCode);
        stField.setFields(null);

        properties.put(fieldCode, stField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField stField = createSubTableField(fieldCode);
        stField.setFields(null);

        properties.put(fieldCode, stField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedAppNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp(null, "");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedAppNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp(null, "");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayFieldsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5, null);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayFieldsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5, null);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenConditionNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        ArrayList<String> displayFields = new ArrayList<>();

        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(null, "文字列__1行_ = \"a\"", relatedApp, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenConditionNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        ArrayList<String> displayFields = new ArrayList<>();

        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(null, "文字列__1行_ = \"a\"", relatedApp, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedAppOfRelatedRecordsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");
        ArrayList<String> displayFields = new ArrayList<>();

        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", null, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedAppOfRelatedRecordsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");
        ArrayList<String> displayFields = new ArrayList<>();

        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", null, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenReferenceTableNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(null);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenReferenceTableNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(null);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenOptionsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        radioButtonField.setOptions(null);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenOptionsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        radioButtonField.setOptions(null);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenPropertiesIsNull() throws KintoneAPIException {
        this.appManagerment.addFormFields(APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenPropertiesIsNullCert() throws KintoneAPIException {
        this.certAppManagerment.addFormFields(APP_ID, null, null);
    }

    // KCB-643
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInEntitiesType() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        orgList.add(userSelectEntity);
        orgList.add(groupSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    // KCB-643
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInEntitiesTypeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        orgList.add(userSelectEntity);
        orgList.add(groupSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenEntitiesTypeNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(null);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenEntitiesTypeNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(null);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    // KCB-645
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueOfEntitiesCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("LOGINUSER()");
        userSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    // KCB-645
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueOfEntitiesCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("LOGINUSER()");
        userSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfEntitiesCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfEntitiesCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueOfEntitiesCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setType(MemberSelectEntityType.USER);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueOfEntitiesCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setType(MemberSelectEntityType.USER);

        orgList.add(userSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    // KCB-643
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInEntitiesTypeInGroupSelection() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        groupList.add(userSelectEntity);
        groupList.add(orgSelectEntity);

        groupSelectionField.setDefaultValue(null);
        groupSelectionField.setLabel("TeamA");
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    // KCB-643
    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInEntitiesTypeInGroupSelectionCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        groupList.add(userSelectEntity);
        groupList.add(orgSelectEntity);

        groupSelectionField.setDefaultValue(null);
        groupSelectionField.setLabel("TeamA");
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        orgList.add(userSelectEntity);
        orgList.add(groupSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(null);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity groupSelectEntity = new MemberSelectEntity();
        groupSelectEntity.setCode("TeamA");
        groupSelectEntity.setType(MemberSelectEntityType.GROUP);

        orgList.add(userSelectEntity);
        orgList.add(groupSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(null);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInDefaultValueInGroupSelection() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        groupList.add(userSelectEntity);
        groupList.add(orgSelectEntity);

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setLabel("TeamA");
        groupSelectionField.setEntities(null);

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenInvalidValueInDefaultValueInGroupSelectionCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity userSelectEntity = new MemberSelectEntity();
        userSelectEntity.setCode("yfang");
        userSelectEntity.setType(MemberSelectEntityType.USER);

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        groupList.add(userSelectEntity);
        groupList.add(orgSelectEntity);

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setLabel("TeamA");
        groupSelectionField.setEntities(null);

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenOrganizationSelectInGuestSpace() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField dsField = createDepartmentSelectionField(fieldCode);

        properties.put(fieldCode, dsField);
        this.guestSpaceAppManagerment.addFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenOrganizationSelectInGuestSpaceCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField dsField = createDepartmentSelectionField(fieldCode);

        properties.put(fieldCode, dsField);
        this.certGuestAppManagerment.addFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldDiffWithRelatedField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcde";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("リンク_0", "リンク");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldDiffWithRelatedFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcde";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("リンク_0", "リンク");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidCondition() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcde";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidConditionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcde";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedKeyFieldNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedKeyFieldNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAddLookupToTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        SubTableField subTableField = new SubTableField(fieldCode);
        LookupField lkupField = createLookupField(fieldCode + 1, lookupItem);
        subTableField.addField(lkupField);

        properties.put(fieldCode, subTableField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAddLookupToTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        SubTableField subTableField = new SubTableField(fieldCode);
        LookupField lkupField = createLookupField(fieldCode + 1, lookupItem);
        subTableField.addField(lkupField);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsOfSubTableNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField subTableField = new SubTableField(fieldCode);
        subTableField.addField(null);

        properties.put(fieldCode, subTableField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsOfSubTableNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField subTableField = new SubTableField(fieldCode);
        subTableField.addField(null);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenNoFieldsOfSubTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField subTableField = new SubTableField(fieldCode);

        properties.put(fieldCode, subTableField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenNoFieldsOfSubTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SubTableField subTableField = new SubTableField(fieldCode);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldInPickerFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("関連レコード一覧");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldInPickerFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("関連レコード一覧");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedFieldIsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRelatedFieldIsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("Text", "");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldMappingValueDuplicate() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        LookupField lkupField2 = createLookupField(fieldCode + 1, lookupItem);
        properties.put(fieldCode, lkupField);
        properties.put(fieldCode + 1, lkupField2);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldMappingValueDuplicateCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fdmappings.setField("文字列__1行_");
        fdmappings.setRelatedFields("文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        LookupField lkupField2 = createLookupField(fieldCode + 1, lookupItem);
        properties.put(fieldCode, lkupField);
        properties.put(fieldCode + 1, lkupField2);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldMappingValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("作成者", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldMappingValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abcd";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("作成者", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankFieldMappingFieldValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankFieldMappingFieldValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithNoFieldMappingFieldValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithNoFieldMappingFieldValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedKeyFieldValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("作成者");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedKeyFieldValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("作成者");

        // ほかのフィールドのコピー
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankRelatedKeyFieldValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankRelatedKeyFieldValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithNoRelatedKeyFieldValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithNoRelatedKeyFieldValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankAppAndCodeValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankAppAndCodeValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidReferenceTableSize() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 35,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidReferenceTableSizeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 35,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankDisplayFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankDisplayFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDisplayFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("Table");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDisplayFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("Table");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenBlankValueOptions() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenBlankValueOptionsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutReferenceTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutReferenceTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfRelatedAppAppAndCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfRelatedAppAppAndCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedAppAndNoCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("99999", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedAppAndNoCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("99999", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutFieldOfCondition() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutFieldOfConditionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueOfConditionField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("作成者", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueOfConditionFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("作成者", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutRelatedFieldOfCondition() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("数値", "");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutRelatedFieldOfConditionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("数値", "");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueRelatedFieldOfCondition() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "作成者");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidValueRelatedFieldOfConditionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "作成者");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutDisplayFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5, null);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutDisplayFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5, null);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedKeyField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行__0");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedKeyFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行__0");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFilterCond() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("abcde");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");

        properties.put(fieldCode, lookupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFilterCondCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("abcde");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetLookupFieldToConditionField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("ルックアップ", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetLookupFieldToConditionFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("ルックアップ", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedFieldMappingValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行__0");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);
        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedFieldMappingValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行_", "文字列__1行__0");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);
        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableInLookupPickerFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__1行__0");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableInLookupPickerFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__1行__0");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToConditionField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行__0", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToConditionFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行__0", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToRelatedFieldOfCondition() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行__0");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToRelatedFieldOfConditionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行__0");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableFieldToSort() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("文字列__1行__0 desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");

        properties.put(fieldCode, lookupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableFieldToSortCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("文字列__1行__0 desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToFieldMappingValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行__0", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableToFieldMappingValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdmappings = new FieldMapping("文字列__1行__0", "文字列__1行_");
        fieldMappings.add(fdmappings);

        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableFieldToRelatedKeyField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行__0");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);
        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetSubtableFieldToRelatedKeyFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行__0");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);
        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetLookupValueToField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("ルックアップ", "数値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSetLookupValueToFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("ルックアップ", "数値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFilterCondInRelatedRecordsList() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "abcde", relatedApp, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFilterCondInRelatedRecordsListCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "abcde", relatedApp, 5, displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfProtocol() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("https://cybozu.co.jp");
        linkField.setProtocol(null);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfProtocolCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("https://cybozu.co.jp");
        linkField.setProtocol(null);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutProtocol() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("https://cybozu.co.jp");
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutProtocolCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("https://cybozu.co.jp");
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUnitOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setUnit(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUnitOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setUnit(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithDuplicateValueIndexOfOptions() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(1, "sample2"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithDuplicateValueIndexOfOptionsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(1, "sample2"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueIndexOfOptions() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(null, "sample1"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueIndexOfOptionsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(null, "sample1"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithValueLabelOfOptionsOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithValueLabelOfOptionsOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithValueLabelOfOptionsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithValueLabelOfOptionsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueLabelOfOptions() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueLabelOfOptionsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithOptionsNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);

        radioButtonField.setOptions(null);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithOptionsNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);

        radioButtonField.setOptions(null);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutOptions() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutOptionsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayScaleOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("11");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayScaleOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("11");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayScaleMinusOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("-1");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenDisplayScaleMinusOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("-1");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfExpression() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setExpression("");
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfExpressionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setExpression("");
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueOfExpression() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutValueOfExpressionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutTypeOfDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(null);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutTypeOfDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(null);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankCodeOfDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankCodeOfDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutCodeOfDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutCodeOfDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);

        userSelectionField.setEntities(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfMail() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        linkField.setProtocol(LinkProtocol.MAIL);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfMailCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        linkField.setProtocol(LinkProtocol.MAIL);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfWeb() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        linkField.setProtocol(LinkProtocol.WEB);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfWebCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        linkField.setProtocol(LinkProtocol.WEB);
        linkField.setLabel("link");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfDateTimeEtc() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DateField dateField = new DateField(fieldCode);
        dateField.setDefaultValue("hoge");
        dateField.setLabel("date");

        DateTimeField dateTimeField = new DateTimeField(fieldCode);
        dateTimeField.setDefaultValue("hoge");
        dateTimeField.setLabel("datetime");

        TimeField timeField = new TimeField(fieldCode);
        timeField.setDefaultValue("hoge");
        timeField.setLabel("time");

        properties.put(fieldCode, dateField);
        properties.put(fieldCode, dateTimeField);
        properties.put(fieldCode, timeField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfDateTimeEtcCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DateField dateField = new DateField(fieldCode);
        dateField.setDefaultValue("hoge");
        dateField.setLabel("date");

        DateTimeField dateTimeField = new DateTimeField(fieldCode);
        dateTimeField.setDefaultValue("hoge");
        dateTimeField.setLabel("datetime");

        TimeField timeField = new TimeField(fieldCode);
        timeField.setDefaultValue("hoge");
        timeField.setLabel("time");

        properties.put(fieldCode, dateField);
        properties.put(fieldCode, dateTimeField);
        properties.put(fieldCode, timeField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfRadioButtonDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("");
        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankValueOfRadioButtonDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("");
        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedValueOfRadioButtonDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("abcde");
        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedValueOfRadioButtonDefaultValueCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("abcde");
        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfNumber() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDefaultValue("hoge");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidDefaultValueOfNumberCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDefaultValue("hoge");
        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinLengthBiggerThanMaxLengthInSingleLineText()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("64");
        sltf.setMinLength("100");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinLengthBiggerThanMaxLengthInSingleLineTextCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("64");
        sltf.setMinLength("100");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinLengthNegativeNumberInSingleLineText() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMinLength("-1");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinLengthNegativeNumberInSingleLineTextCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMinLength("-1");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMaxLengthNegativeNumberInSingleLineText() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("-1");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMaxLengthNegativeNumberInSingleLineTextCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("-1");
        sltf.setLabel("SingleLineText");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinValueBiggerThanMaxValueInNumber() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setMaxValue("1");
        numberField.setMinValue("10");

        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenMinValueBiggerThanMaxValueInNumberCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        NumberField numberField = new NumberField(fieldCode);
        numberField.setMaxValue("1");
        numberField.setMinValue("10");

        numberField.setLabel("number");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenLengthOfLabelInSingleLineTextOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenLengthOfLabelInSingleLineTextOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenLabelInSingleLineTextNull() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenLabelInSingleLineTextNullCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutLabelInSingleLineText() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutLabelInSingleLineTextCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldCodeDiffWithCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode + 1);
        sltf.setCode(fieldCode + 1);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldCodeDiffWithCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode + 1);
        sltf.setCode(fieldCode + 1);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenCodeOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenCodeOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankFieldCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithBlankFieldCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "@";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidFieldCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "@";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("single line text");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUseInsideField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        int i = 0;
        CategoryField catField = createCategoryField(fieldCode + i++);
        StatusField stsField = createStatusField(fieldCode + i++);
        CreatedTimeField ctField = createCreatedTimeField(fieldCode + i++);
        CreatorField ctrField = createCreatorField(fieldCode + i++);
        AssigneeField assField = createAssigneeField(fieldCode + i++);
        UpdatedTimeField uptField = createUpdatedTimeField(fieldCode + i++);
        ModifierField mfrField = createModifierField(fieldCode + i++);
        RecordNumberField rnmbField = createRecordNumberField(fieldCode + i++);

        int j = 0;
        properties.put(fieldCode + j++, catField);
        properties.put(fieldCode + j++, stsField);
        properties.put(fieldCode + j++, ctField);
        properties.put(fieldCode + j++, ctrField);
        properties.put(fieldCode + j++, assField);
        properties.put(fieldCode + j++, uptField);
        properties.put(fieldCode + j++, mfrField);
        properties.put(fieldCode + j++, rnmbField);

        this.appManagerment.addFormFields(FORMLAYOUT_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUseInsideFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        int i = 0;
        CategoryField catField = createCategoryField(fieldCode + i++);
        StatusField stsField = createStatusField(fieldCode + i++);
        CreatedTimeField ctField = createCreatedTimeField(fieldCode + i++);
        CreatorField ctrField = createCreatorField(fieldCode + i++);
        AssigneeField assField = createAssigneeField(fieldCode + i++);
        UpdatedTimeField uptField = createUpdatedTimeField(fieldCode + i++);
        ModifierField mfrField = createModifierField(fieldCode + i++);
        RecordNumberField rnmbField = createRecordNumberField(fieldCode + i++);

        int j = 0;
        properties.put(fieldCode + j++, catField);
        properties.put(fieldCode + j++, stsField);
        properties.put(fieldCode + j++, ctField);
        properties.put(fieldCode + j++, ctrField);
        properties.put(fieldCode + j++, assField);
        properties.put(fieldCode + j++, uptField);
        properties.put(fieldCode + j++, mfrField);
        properties.put(fieldCode + j++, rnmbField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutApp() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        this.appManagerment.addFormFields(null, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutAppCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        this.certAppManagerment.addFormFields(null, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithDuplicateFieldCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SingleLineTextField sglineField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, sglineField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithDuplicateFieldCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SingleLineTextField sglineField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, sglineField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutPermissionForReferenceTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedRecordsField rrField = createRelatedRecordsField(fieldCode);
        properties.put(fieldCode, rrField);

        this.noAddNoReadAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutPermissionForLookup() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupField lkupField = createLookupField(fieldCode);
        properties.put(fieldCode, lkupField);

        this.readOnlyAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithoutAdminPermission() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        SingleLineTextField sglineField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, sglineField);
        this.noAdminAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidCodeOfLookupRelatedApp() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "hoge");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidCodeOfLookupRelatedAppCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "hoge");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidIdOfLookupRelatedApp() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("99999", "");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidIdOfLookupRelatedAppCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("99999", "");
        LookupField lkupField = createLookupField(fieldCode, relatedApp);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        for (int i = 0, j = 0; i < 501 & j < 501; i++, j++) {
            SingleLineTextField sglineField = createSingleLineTextField(fieldCode + i);
            properties.put(("abc" + j), sglineField);
        }
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldsOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        for (int i = 0, j = 0; i < 501 & j < 501; i++, j++) {
            SingleLineTextField sglineField = createSingleLineTextField(fieldCode + i);
            properties.put(("abc" + j), sglineField);
        }
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedAppSort() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("abc.文字列__1行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel(fieldCode);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidRelatedAppSortCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行_", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("abc.文字列__1行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel(fieldCode);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSubtableOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        for (int i = 0, j = 0; i < 51 & j < 51; i++, j++) {
            SubTableField stField = createSubTableField(fieldCode + i);
            properties.put(fieldCode + j, stField);
        }
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenSubtableOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        for (int i = 0, j = 0; i < 51 & j < 51; i++, j++) {
            SubTableField stField = createSubTableField(fieldCode + i);
            properties.put(fieldCode + j, stField);
        }
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedOrgInDefaultCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("abcde");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedOrgInDefaultCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("abcde");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);
        departmentSelectionField.setLabel("検証組織");

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedGroupInDefaultCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("abcde");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);
        orgList.add(memberSelectEntity);

        groupSelectionField.setDefaultValue(orgList);
        groupSelectionField.setEntities(orgList);
        groupSelectionField.setLabel("検証組織");

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedGroupInDefaultCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("abcde");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);
        orgList.add(memberSelectEntity);

        groupSelectionField.setDefaultValue(orgList);
        groupSelectionField.setEntities(orgList);
        groupSelectionField.setLabel("検証組織");

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidCalcInSubtable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SubTableField subTableField = new SubTableField(fieldCode);
        CalculatedField calculatedField = new CalculatedField(fieldCode + 1);

        calculatedField.setExpression("数値A+数値B");
        calculatedField.setLabel("calculated");

        subTableField.addField(calculatedField);

        properties.put(fieldCode, subTableField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidCalcInSubtableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        SubTableField subTableField = new SubTableField(fieldCode);
        CalculatedField calculatedField = new CalculatedField(fieldCode + 1);

        calculatedField.setExpression("数値A+数値B");
        calculatedField.setLabel("calculated");

        subTableField.addField(calculatedField);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupPickerFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("添付ファイル1");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupPickerFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("添付ファイル1");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupFieldMappingsRelatedField()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行__123");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupFieldMappingsRelatedFieldCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行__123");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupFieldMappingsField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_123", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupFieldMappingsFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_123", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupRelatedKeyField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行__123");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInLookupRelatedKeyFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行__123");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);

        properties.put(fieldCode, lkupField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableDisplayFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行__123");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableDisplayFieldsCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行__123");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableRelatedFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "文字列__1行__123");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableRelatedFieldsCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "文字列__1行__123");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行__123", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailUnexistedFieldInReferenceTableFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("文字列__1行__123", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);
        relatedRecordsField.setLabel("relatedrecords");

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidSumInCalField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);

        calculatedField.setExpression("数値A+数値B");
        calculatedField.setFormat(NumberFormat.NUMBER);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithInvalidSumInCalFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        CalculatedField calculatedField = new CalculatedField(fieldCode);

        calculatedField.setExpression("数値A+数値B");
        calculatedField.setFormat(NumberFormat.NUMBER);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedSumInCalField() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        CalculatedField calculatedField = new CalculatedField(fieldCode);

        calculatedField.setExpression("数値1");
        calculatedField.setFormat(NumberFormat.NUMBER);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedSumInCalFieldCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        CalculatedField calculatedField = new CalculatedField(fieldCode);

        calculatedField.setExpression("数値1");
        calculatedField.setFormat(NumberFormat.NUMBER);
        calculatedField.setLabel("calculated");

        properties.put(fieldCode, calculatedField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedOrgInDefaultValueCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedOrgInDefaultValueCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedGroupInDefaultValueCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.GROUP);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedGroupInDefaultValueCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.GROUP);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedUserInDefaultValueCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWithUnexistedUserInDefaultValueCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abc";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織abc");
        orgSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(orgSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setLabel("userlist");

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUseOwnAppFilterCondInReferenceTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "abc.文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setLabel(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUseOwnAppFilterCondInReferenceTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "abce";

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "abc.文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setLabel(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.addFormFieldTokenAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdNegativeNumber() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);
        this.appManagerment.addFormFields(-1, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdNegativeNumberCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);
        this.certAppManagerment.addFormFields(-1, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdIsZero() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);
        this.appManagerment.addFormFields(0, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdIsZeroCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);
        this.certAppManagerment.addFormFields(0, properties, null);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenRevisionIsMinusOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        BasicResponse response = this.appManagerment.addFormFields(APP_ID, properties, -1);
        assertNotNull(response);
    }

    @Test
    public void testAddFormFieldsShouldSuccessWhenRevisionIsMinusOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(APP_ID, fields, null);

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        BasicResponse response = this.certAppManagerment.addFormFields(APP_ID, properties, -1);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.appManagerment.addFormFields(APP_ID, properties, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.certAppManagerment.addFormFields(APP_ID, properties, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsZero() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.appManagerment.addFormFields(APP_ID, properties, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsZeroCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.certAppManagerment.addFormFields(APP_ID, properties, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsLessThanMinusOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.appManagerment.addFormFields(APP_ID, properties, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenRevisionIsLessThanMinusOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.certAppManagerment.addFormFields(APP_ID, properties, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdNotExists() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.appManagerment.addFormFields(99999, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenAppIdNotExistsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "radioFieldCode";

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.certAppManagerment.addFormFields(99999, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldCodeIsRoot() throws KintoneAPIException {
        String fieldCode = "__ROOT__";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.appManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddFormFieldsShouldFailWhenFieldCodeIsRootCert() throws KintoneAPIException {
        String fieldCode = "__ROOT__";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField rbField = createRadioButtonField(fieldCode);
        properties.put(fieldCode, rbField);

        this.certAppManagerment.addFormFields(APP_ID, properties, null);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccess() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField singleLineTxt = new SingleLineTextField(fieldCode);
        singleLineTxt.setLabel("Single Line Text");
        singleLineTxt.setDefaultValue("Hello Kintone");
        singleLineTxt.setRequired(true);

        properties.put(fieldCode, singleLineTxt);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessCert() throws KintoneAPIException {
        String fieldCode = "Singline_Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();
        SingleLineTextField singleLineTxt = new SingleLineTextField(fieldCode);

        singleLineTxt.setLabel("Single Line Text");
        singleLineTxt.setDefaultValue("Hello Kintone");
        singleLineTxt.setRequired(true);

        properties.put(fieldCode, singleLineTxt);

        BasicResponse response = this.certAppManagerment.updateFormFields(APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAsWeek() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "更新日時 <= LAST_WEEK(SUNDAY)", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);
        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("日付 = LAST_WEEK()");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAsWeekCert() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "更新日時 <= LAST_WEEK(SUNDAY)", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);
        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("日付 = LAST_WEEK()");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAsFromToday() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "更新日時 <= FROM_TODAY(-1, YEARS)", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);
        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("日付 = FROM_TODAY(2,WEEKS)");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAsFromTodayCert() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "更新日時 <= FROM_TODAY(-1, YEARS)", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);
        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("日付 = FROM_TODAY(2,WEEKS)");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenUpdateOrgAndGroup() throws KintoneAPIException {
        String fieldCode1 = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField gsField = createGroupSelectionField(fieldCode1);
        properties.put(fieldCode1, gsField);

        String fieldCode2 = "組織選択";
        DepartmentSelectionField dsField = createDepartmentSelectionField(fieldCode2);
        properties.put(fieldCode2, dsField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenUpdateOrgAndGroupCert() throws KintoneAPIException {
        String fieldCode1 = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField gsField = createGroupSelectionField(fieldCode1);
        properties.put(fieldCode1, gsField);

        String fieldCode2 = "組織選択";
        DepartmentSelectionField dsField = createDepartmentSelectionField(fieldCode2);
        properties.put(fieldCode2, dsField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAndSortAs$id() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "$id >= 10", relatedApp, 5, displayFields);
        referenceTable.setSort("$id desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("$id >= 10");
        lookupItem.setSort("$id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenSetFilterCondAndSortAs$idCert() throws KintoneAPIException {
        String fieldCode1 = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "$id >= 10", relatedApp, 5, displayFields);
        referenceTable.setSort("$id desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode1);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode1, relatedRecordsField);

        String fieldCode2 = "ルックアップ";
        LookupField lookupField = new LookupField(fieldCode2, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        lookupItem.setRelatedApp(relatedApp);
        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("$id >= 10");
        lookupItem.setSort("$id desc");

        lookupField.setLookup(lookupItem);
        properties.put(fieldCode2, lookupField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeRelatedAppId() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "ルックアップ");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeRelatedAppIdCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1639", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "ルックアップ");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeLookupFieldCode() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeLookupFieldCodeCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupItem lookupItem = new LookupItem();
        lookupItem.setRelatedKeyField("文字列__1行_");

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeRelatedRecordFieldCode() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("数値", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeRelatedRecordFieldCodeCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("数値", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWithoutOptionsLabel() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));
        radioOption.put("sample2", new OptionData(2, "sample2"));

        radioButtonField.setOptions(radioOption);
        properties.put(fieldCode, radioButtonField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWithoutOptionsLabelCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));
        radioOption.put("sample2", new OptionData(2, "sample2"));

        radioButtonField.setOptions(radioOption);
        properties.put(fieldCode, radioButtonField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenBlankValueOfDefaultValueAndEntities() throws KintoneAPIException {
        String fieldCode1 = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode1);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode1, userSelectionField);

        String fieldCode2 = "グループ選択";
        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode2);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode2, groupSelectionField);

        String fieldCode3 = "組織選択";
        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode3);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode3, departmentSelectionField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenBlankValueOfDefaultValueAndEntitiesCert()
            throws KintoneAPIException {
        String fieldCode1 = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode1);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode1, userSelectionField);

        String fieldCode2 = "グループ選択";
        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode2);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode2, groupSelectionField);

        String fieldCode3 = "組織選択";
        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode3);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode3, departmentSelectionField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    // KCB-650
    @Test
    public void testUpdateFormFieldsShouldSuccessWhenBlankValueInDateCalTimeNumSglineDropEtc()
            throws KintoneAPIException {
        String fieldCode1 = "計算";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        CalculatedField calculatedField = new CalculatedField(fieldCode1);
        calculatedField.setDisplayScale("");
        calculatedField.setUnit("");

        properties.put(fieldCode1, calculatedField);

        String fieldCode2 = "日付";

        DateField dateField = new DateField(fieldCode2);
        dateField.setDefaultValue("");

        properties.put(fieldCode2, dateField);

        String fieldCode3 = "日時";

        DateTimeField dateTimeField = new DateTimeField(fieldCode3);
        dateTimeField.setDefaultValue("");

        properties.put(fieldCode3, dateTimeField);

        String fieldCode4 = "数値";

        NumberField numberField = new NumberField(fieldCode4);
        numberField.setDisplayScale("");
        numberField.setMaxValue("");
        numberField.setMinValue("");
        numberField.setDefaultValue("");
        numberField.setUnit("");

        properties.put(fieldCode4, numberField);

        String fieldCode5 = "リッチエディター";

        RichTextField richTextField = new RichTextField(fieldCode5);
        richTextField.setDefaultValue("rich editor default");

        properties.put(fieldCode5, richTextField);

        String fieldCode6 = "リンク_web";

        LinkField linkField = new LinkField(fieldCode6);
        linkField.setMaxLength("");
        linkField.setMinLength("");
        linkField.setDefaultValue("");
        linkField.setProtocol(LinkProtocol.WEB);

        properties.put(fieldCode6, linkField);

        String fieldCode7 = "文字列__複数行_";

        MultiLineTextField multiLineTextField = new MultiLineTextField(fieldCode7);
        multiLineTextField.setDefaultValue("");

        properties.put(fieldCode7, multiLineTextField);

        String fieldCode8 = "文字列__1行_";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode8);
        sltf.setMinLength("");
        sltf.setMaxLength("");
        sltf.setDefaultValue("");
        sltf.setExpression("");

        properties.put(fieldCode8, sltf);

        String fieldCode9 = "ドロップダウン";

        DropDownField dropDownField = new DropDownField(fieldCode9);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();

        dropDownOption.put("sample1", new OptionData(1, "sample1"));
        dropDownOption.put("sample2", new OptionData(2, "sample2"));
        dropDownField.setOptions(dropDownOption);

        dropDownField.setDefaultValue("");
        properties.put(fieldCode9, dropDownField);

        String fieldCode10 = "時刻";

        TimeField timeField = new TimeField(fieldCode10);
        timeField.setDefaultValue("");
        properties.put(fieldCode10, timeField);

        String fieldCode11 = "添付ファイル";

        AttachmentField attachmentField = createAttachmentField(fieldCode11);
        attachmentField.setThumbnailSize("");

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    // KCB-650
    @Test
    public void testUpdateFormFieldsShouldSuccessWhenBlankValueInDateCalTimeNumSglineDropEtcCert()
            throws KintoneAPIException {
        String fieldCode1 = "計算";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        CalculatedField calculatedField = new CalculatedField(fieldCode1);
        calculatedField.setDisplayScale("");
        calculatedField.setUnit("");

        properties.put(fieldCode1, calculatedField);

        String fieldCode2 = "日付";

        DateField dateField = new DateField(fieldCode2);
        dateField.setDefaultValue("");

        properties.put(fieldCode2, dateField);

        String fieldCode3 = "日時";

        DateTimeField dateTimeField = new DateTimeField(fieldCode3);
        dateTimeField.setDefaultValue("");

        properties.put(fieldCode3, dateTimeField);

        String fieldCode4 = "数値";

        NumberField numberField = new NumberField(fieldCode4);
        numberField.setDisplayScale("");
        numberField.setMaxValue("");
        numberField.setMinValue("");
        numberField.setDefaultValue("");
        numberField.setUnit("");

        properties.put(fieldCode4, numberField);

        String fieldCode5 = "リッチエディター";

        RichTextField richTextField = new RichTextField(fieldCode5);
        richTextField.setDefaultValue("rich editor default");

        properties.put(fieldCode5, richTextField);

        String fieldCode6 = "リンク_web";

        LinkField linkField = new LinkField(fieldCode6);
        linkField.setMaxLength("");
        linkField.setMinLength("");
        linkField.setDefaultValue("");
        linkField.setProtocol(LinkProtocol.WEB);

        properties.put(fieldCode6, linkField);

        String fieldCode7 = "文字列__複数行_";

        MultiLineTextField multiLineTextField = new MultiLineTextField(fieldCode7);
        multiLineTextField.setDefaultValue("");

        properties.put(fieldCode7, multiLineTextField);

        String fieldCode8 = "文字列__1行_";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode8);
        sltf.setMinLength("");
        sltf.setMaxLength("");
        sltf.setDefaultValue("");
        sltf.setExpression("");

        properties.put(fieldCode8, sltf);

        String fieldCode9 = "ドロップダウン";

        DropDownField dropDownField = new DropDownField(fieldCode9);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();

        dropDownOption.put("sample1", new OptionData(1, "sample1"));
        dropDownOption.put("sample2", new OptionData(2, "sample2"));
        dropDownField.setOptions(dropDownOption);

        dropDownField.setDefaultValue("");
        properties.put(fieldCode9, dropDownField);

        String fieldCode10 = "時刻";

        TimeField timeField = new TimeField(fieldCode10);
        timeField.setDefaultValue("");
        properties.put(fieldCode10, timeField);

        String fieldCode11 = "添付ファイル";

        AttachmentField attachmentField = createAttachmentField(fieldCode11);
        attachmentField.setThumbnailSize("");

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeInsideFunction() throws KintoneAPIException {
        String fieldCode1 = "ステータス";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        StatusField stsField = createStatusField(fieldCode1);
        properties.put(fieldCode1, stsField);

        String fieldCode2 = "カテゴリー";
        CategoryField catField = createCategoryField(fieldCode2);
        properties.put(fieldCode2, catField);

        String fieldCode3 = "作業者";
        AssigneeField assField = createAssigneeField(fieldCode3);
        properties.put(fieldCode3, assField);

        BasicResponse response = this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenChangeInsideFunctionCert() throws KintoneAPIException {
        String fieldCode1 = "ステータス";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        StatusField stsField = createStatusField(fieldCode1);
        properties.put(fieldCode1, stsField);

        String fieldCode2 = "カテゴリー";
        CategoryField catField = createCategoryField(fieldCode2);
        properties.put(fieldCode2, catField);

        String fieldCode3 = "作業者";
        AssigneeField assField = createAssigneeField(fieldCode3);
        properties.put(fieldCode3, assField);

        BasicResponse response = this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenPropertiesNull() throws KintoneAPIException {
        this.appManagerment.updateFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenPropertiesNullCert() throws KintoneAPIException {
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSortOverflow() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("数値 >= 10");
        lookupItem.setSort("数値 desc, $id desc, 数値 desc, $id desc, 数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSortOverflowCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("数値 >= 10");
        lookupItem.setSort("数値 desc, $id desc, 数値 desc, $id desc, 数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidSort() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("数値 >= 10");
        lookupItem.setSort("文字列__複数行_ desc");

        lookupField.setLookup(lookupItem);
        lookupField.setLabel("lookup");

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidSortCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("数値 >= 10");
        lookupItem.setSort("文字列__複数行_ desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFilterCond() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("abcde");
        lookupItem.setSort("数値 desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFilterCondCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond("abcde");
        lookupItem.setSort("数値 desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSortOverflowInReferenceTable() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc, 数値 desc, 数値 desc, 数値 desc, 数値 desc, 数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSortOverflowInReferenceTableCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc, 数値 desc, 数値 desc, 数値 desc, 数値 desc, 数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidSortInReferecnceTable() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("文字列__複数行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidSortInReferecnceTableCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("文字列__複数行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFilterCondInReferecnceTable() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"test\" offset 1", relatedApp, 5,
                displayFields);
        referenceTable.setSort("文字列__1行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFilterCondInReferecnceTableCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "abcde", relatedApp, 5, displayFields);
        referenceTable.setSort("文字列__1行_ desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueInGroup() throws KintoneAPIException {
        String fieldCode = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        groupList.add(memberSelectEntity);

        groupSelectionField.setEntities(groupList);
        groupSelectionField.setDefaultValue(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueInGroupCert() throws KintoneAPIException {
        String fieldCode = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        groupList.add(memberSelectEntity);

        groupSelectionField.setEntities(groupList);
        groupSelectionField.setDefaultValue(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInGroup() throws KintoneAPIException {
        String fieldCode = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("TeamA");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        groupList.add(memberSelectEntity);

        groupSelectionField.setEntities(groupList);
        groupSelectionField.setDefaultValue(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInGroupCert() throws KintoneAPIException {
        String fieldCode = "グループ選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("TeamA");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        groupList.add(memberSelectEntity);

        groupSelectionField.setEntities(groupList);
        groupSelectionField.setDefaultValue(null);

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueInOrg() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("user1");
        memberSelectEntity.setType(MemberSelectEntityType.USER);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidEntitiesTypeInOrgCert() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("user1");
        memberSelectEntity.setType(MemberSelectEntityType.USER);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(null);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInOrg() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInOrgCert() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();

        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInUser() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        userList.add(memberSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInUserCert() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        userList.add(memberSelectEntity);
        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInDefaultValueTypeInOrg() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("LOGINUSER()");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetFunctionInDefaultValueTypeInOrgCert() throws KintoneAPIException {
        String fieldCode = "組織選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("LOGINUSER()");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenReferenceTableConditionFieldDiffWithRelatedField()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("リンク_mail", "リンク_web");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenReferenceTableConditionFieldDiffWithRelatedFieldCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("リンク_mail", "リンク_web");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidReferenceTableConditionValue() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidReferenceTableConditionValueCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "レコード番号");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLookupFieldMappingsFieldDiffWithRelatedField()
            throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("リンク_mail", "リンク_web");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLookupFieldMappingsFieldDiffWithRelatedFieldCert()
            throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("リンク_mail", "リンク_web");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        LookupField lkupField = createLookupField(fieldCode, lookupItem);
        properties.put(fieldCode, lkupField);

        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetInvalidValueInUser() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("PRIMARY_ORGANIZATION()");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setEntities(userList);

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetInvalidValueInUserCert() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("PRIMARY_ORGANIZATION()");
        memberSelectEntity.setType(MemberSelectEntityType.FUNCTION);

        userList.add(memberSelectEntity);
        userSelectionField.setDefaultValue(userList);
        userSelectionField.setEntities(userList);

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenBlankFieldsOfSubtable() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        properties.put(fieldCode, subTableField);

        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenBlankFieldsOfSubtableCert() throws KintoneAPIException {
        String fieldCode = "ユーザー選択";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        properties.put(fieldCode, subTableField);

        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfLookupPickerFields() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("関連レコード一覧");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfLookupPickerFieldsCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("関連レコード一覧");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfFieldMappingsRelatedField() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数値", "添付ファイル");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfFieldMappingsRelatedFieldCert()
            throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数値", "添付ファイル");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateValueOfFieldMappingsField() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("日時", "日時");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateValueOfFieldMappingsFieldCert() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("日時", "日時");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLookupInTable() throws KintoneAPIException {
        String fieldCode1 = "ルックアップ_1";
        String fieldCode2 = "Table";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode1, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        SubTableField subTableField = new SubTableField(fieldCode2);
        subTableField.addField(lookupField);

        properties.put(fieldCode2, subTableField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLookupInTableCert() throws KintoneAPIException {
        String fieldCode1 = "ルックアップ_1";
        String fieldCode2 = "Table";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode1, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("文字列__1行_", "文字列__1行_");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        SubTableField subTableField = new SubTableField(fieldCode2);
        subTableField.addField(lookupField);

        properties.put(fieldCode2, subTableField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfLookupFieldMappingsField() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("作成者", "作成者");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfLookupFieldMappingsFieldCert()
            throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");
        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("作成者", "作成者");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");
        lookupItem.setSort("数値 desc, $id desc");

        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValueOfTimeDate() throws KintoneAPIException {
        String fieldCode1 = "日付";
        String fieldCode2 = "時刻";
        String fieldCode3 = "日時";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DateField dateField = new DateField(fieldCode1);
        dateField.setDefaultValue("hoge");

        TimeField timeField = new TimeField(fieldCode2);
        timeField.setDefaultValue("hoge");

        DateTimeField dateTimeField = new DateTimeField(fieldCode3);
        dateTimeField.setDefaultValue("hoge");

        properties.put(fieldCode1, dateField);
        properties.put(fieldCode2, timeField);
        properties.put(fieldCode3, dateTimeField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValueOfTimeDateCert() throws KintoneAPIException {
        String fieldCode1 = "日付";
        String fieldCode2 = "時刻";
        String fieldCode3 = "日時";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        DateField dateField = new DateField(fieldCode1);
        dateField.setDefaultValue("hoge");

        TimeField timeField = new TimeField(fieldCode2);
        timeField.setDefaultValue("hoge");

        DateTimeField dateTimeField = new DateTimeField(fieldCode3);
        dateTimeField.setDefaultValue("hoge");

        properties.put(fieldCode1, dateField);
        properties.put(fieldCode2, timeField);
        properties.put(fieldCode3, dateTimeField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferenceTableDisplayFields()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("関連レコード一覧");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferenceTableDisplayFieldsCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("関連レコード一覧");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankValueOfReferencecTableDisplayFields()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankValueOfReferencecTableDisplayFieldsCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferencecTableConditionRelatedField()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "作成者");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferencecTableConditionRelatedFieldCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "作成者");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferencecTableConditionField()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("作成者", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidValueOfReferencecTableConditionFieldCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("作成者", "文字列__1行_");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutRelatedApp() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutRelatedAppCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenUnitOverflow() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setUnit(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, numberField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenUnitOverflowCert() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setUnit(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidThumbnailSize() throws KintoneAPIException {
        String fieldCode = "添付ファイル";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        AttachmentField attachmentField = new AttachmentField(fieldCode);
        attachmentField.setThumbnailSize("1000");

        properties.put(fieldCode, attachmentField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidThumbnailSizeCert() throws KintoneAPIException {
        String fieldCode = "添付ファイル";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        AttachmentField attachmentField = new AttachmentField(fieldCode);
        attachmentField.setThumbnailSize("1000");

        properties.put(fieldCode, attachmentField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDisplayScaleBiggerThanTen() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("11");

        properties.put(fieldCode, numberField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDisplayScaleBiggerThanTenCert() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("11");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDisplayScaleSmallerThanZero() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("-1");

        properties.put(fieldCode, numberField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDisplayScaleSmallerThanZeroCert() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDisplayScale("-1");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenChangeProtocol() throws KintoneAPIException {
        String fieldCode = "リンク_web";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setProtocol(LinkProtocol.CALL);

        properties.put(fieldCode, linkField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenChangeProtocolCert() throws KintoneAPIException {
        String fieldCode = "リンク_web";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setProtocol(LinkProtocol.CALL);

        properties.put(fieldCode, linkField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidMailLinkDefaultValue() throws KintoneAPIException {
        String fieldCode = "リンク_mail";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        properties.put(fieldCode, linkField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidMailLinkDefaultValueCert() throws KintoneAPIException {
        String fieldCode = "リンク_mail";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidWebLinkDefaultValue() throws KintoneAPIException {
        String fieldCode = "リンク_web";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        properties.put(fieldCode, linkField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidWebLinkDefaultValueCert() throws KintoneAPIException {
        String fieldCode = "リンク_web";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LinkField linkField = new LinkField(fieldCode);
        linkField.setDefaultValue("hoge");

        properties.put(fieldCode, linkField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidNumberDefaultValue() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDefaultValue("hoge");

        properties.put(fieldCode, numberField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidNumberDefaultValueCert() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setDefaultValue("hoge");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinLengthBiggerThanMaxLength() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("6");
        sltf.setMinLength("10");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinLengthBiggerThanMaxLengthCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("6");
        sltf.setMinLength("10");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinLengthNegativeNumber() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMinLength("-1");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinLengthNegativeNumberCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMinLength("-1");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMaxLengthNegativeNumber() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("-1");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMaxLengthNegativeNumberCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setMaxLength("-1");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinValueBiggerThanMaxValue() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setMaxValue("100");
        numberField.setMinValue("1000");

        properties.put(fieldCode, numberField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenMinValueBiggerThanMaxValueCert() throws KintoneAPIException {
        String fieldCode = "数値";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        NumberField numberField = new NumberField(fieldCode);
        numberField.setMaxValue("100");
        numberField.setMinValue("1000");

        properties.put(fieldCode, numberField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetTrueInUnique() throws KintoneAPIException {
        String fieldCode = "Table_0";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField sglineField = createSingleLineTextField("文字列__1行__0");
        sglineField.setUnique(true);
        subTableField.addField(sglineField);

        properties.put(fieldCode, subTableField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenSetTrueInUniqueCert() throws KintoneAPIException {
        String fieldCode = "Table_0";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField sglineField = createSingleLineTextField("文字列__1行__0");
        sglineField.setUnique(true);
        subTableField.addField(sglineField);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDuplicateFieldCode() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDuplicateFieldCodeCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLabelOverflow() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLabelOverflowCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLabelBlank() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenLabelBlankCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setLabel("");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenCodeOverflow() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenCodeOverflowCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenCodeBlank() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenCodeBlankCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidCode() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_®");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidCodeCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_®");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutProperties() throws KintoneAPIException {
        this.appManagerment.updateFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutPropertiesCert() throws KintoneAPIException {
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutAppId() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(null, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutAppIdCert() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setCode("文字列__1行_");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(null, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankOptions() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankOptionsCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenOptionsDiffOptionsLabel() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample12", new OptionData(1, "sample1"));
        radioOption.put("", new OptionData(2, "sample2"));
        radioOption.put(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                new OptionData(3, "sample3"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenOptionsDiffOptionsLabelCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample12", new OptionData(1, "sample1"));
        radioOption.put("", new OptionData(2, "sample2"));
        radioOption.put(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                new OptionData(3, "sample3"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setLabel("Label Radio");

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutOptionsIndex() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(null, "sample1"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutOptionsIndexCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(null, "sample1"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateIndex() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(1, "sample2"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateIndexCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(1, "sample2"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutLabel() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));
        radioOption.put("sample4", new OptionData(4, null));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithoutLabelCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, null));
        radioOption.put("sample4", new OptionData(4, null));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateLabel() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(2, "sample1"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithDuplicateLabelCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));
        radioOption.put("sample2", new OptionData(2, "sample1"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRadioLabelOverflow() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRadioLabelOverflowCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1,
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankRadioLabel() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankRadioLabelCert() throws KintoneAPIException {
        String fieldCode = "ラジオボタン";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, ""));

        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenBlankValueOfReferenceTableRelatedApp() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp(null, null);
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenBlankValueOfReferenceTableRelatedAppCert()
            throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp(null, null);
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenAddInvalidFieldToSubtable() throws KintoneAPIException {
        String fieldCode = "Table_0";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext1 = createSingleLineTextField("文字列__1行__0");
        SingleLineTextField tabletext2 = createSingleLineTextField("Text");

        subTableField.addField(tabletext1);
        subTableField.addField(tabletext2);

        properties.put(fieldCode, subTableField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenAddInvalidFieldToSubtableCert() throws KintoneAPIException {
        String fieldCode = "Table_0";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext1 = createSingleLineTextField("文字列__1行__0");
        SingleLineTextField tabletext2 = createSingleLineTextField("Text");

        subTableField.addField(tabletext1);
        subTableField.addField(tabletext2);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenNoPermissionForRelatedApp() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.noAddNoReadAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenNoAdminPermissionForApp() throws KintoneAPIException {
        String fieldCode = "Text";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.noAdminAppManagerment.updateFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenNoAppPermission() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedRecordsField createRelatedRecordsField = createRelatedRecordsField(fieldCode);

        properties.put(fieldCode, createRelatedRecordsField);
        this.noAdminAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenNoAppPermissionLookup() throws KintoneAPIException {
        String fieldCode = "ルックアップ";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        LookupField createLookupField = createLookupField(fieldCode);

        properties.put(fieldCode, createLookupField);
        this.noAdminAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedRelatedApp() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("99999", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedRelatedAppCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("99999", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedRelatedAppCode() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "aaaa");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedRelatedAppCodeCert() throws KintoneAPIException {
        String fieldCode = "関連レコード一覧";
        HashMap<String, Field> properties = new HashMap<String, Field>();

        RelatedApp relatedApp = new RelatedApp("1757", "aaaa");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");
        displayFields.add("数値");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setCode(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenOrganizationSelectInGuestSpace() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(orgSelectEntity);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.guestSpaceAppManagerment.updateFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenOrganizationSelectInGuestSpaceCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();

        MemberSelectEntity orgSelectEntity = new MemberSelectEntity();
        orgSelectEntity.setCode("検証組織");
        orgSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(orgSelectEntity);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.certGuestAppManagerment.updateFormFields(TestConstantsSample.GUEST_SPACE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenFieldCodeOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenFieldCodeOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankFieldCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithBlankFieldCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFieldCode() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "文字列__1行_®";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidFieldCodeCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "文字列__1行_®";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);

        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInSubTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Table";

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext = createSingleLineTextField("tabletext");
        subTableField.addField(tabletext);

        properties.put(fieldCode, subTableField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInSubTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Table";

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext = createSingleLineTextField("tabletext");
        subTableField.addField(tabletext);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupPickerFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__複数行_1");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupPickerFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupPickerFields.add("文字列__複数行_1");
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupFieldMappingsRelatedField()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数値", "数1値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupFieldMappingsRelatedFieldCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数値", "数1値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupFieldMappingsField()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数1値", "数値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInLookupFieldMappingsFieldCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ルックアップ";

        LookupField lookupField = new LookupField(fieldCode, FieldType.SINGLE_LINE_TEXT);
        LookupItem lookupItem = new LookupItem();

        RelatedApp relatedApp = new RelatedApp("1757", "");
        lookupItem.setRelatedApp(relatedApp);

        lookupItem.setRelatedKeyField("文字列__1行_");

        ArrayList<FieldMapping> fieldMappings = new ArrayList<>();
        FieldMapping fdMapping = new FieldMapping("数1値", "数値");
        fieldMappings.add(fdMapping);
        lookupItem.setFieldMapping(fieldMappings);

        ArrayList<String> lookupPickerFields = new ArrayList<>();
        lookupItem.setLookupPickerFields(lookupPickerFields);

        lookupItem.setFilterCond(
                "数値 >= 10 and 更新日時 = THIS_WEEK(WEDNESDAY) and 作成日時 < FROM_TODAY(-1, WEEKS) and $id >= 10 and 文字列__1行_ = \"a\"");

        lookupItem.setSort("数値 desc, $id desc");
        lookupField.setLookup(lookupItem);

        properties.put(fieldCode, lookupField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableDisplayFields()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_1");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableDisplayFieldsCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_1");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableConditionRelatedField()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値1");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableConditionRelatedFieldCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号", "数値1");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableConditionField()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号1", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedFieldInReferenceTableConditionFieldCert()
            throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "関連レコード一覧";

        RelatedApp relatedApp = new RelatedApp("1757", "");
        FieldMapping fieldMapping = new FieldMapping("レコード番号1", "数値");

        ArrayList<String> displayFields = new ArrayList<>();
        displayFields.add("文字列__1行_");

        ReferenceTable referenceTable = new ReferenceTable(fieldMapping, "文字列__1行_ = \"a\"", relatedApp, 5,
                displayFields);
        referenceTable.setSort("数値 desc");

        RelatedRecordsField relatedRecordsField = new RelatedRecordsField(fieldCode);
        relatedRecordsField.setReferenceTable(referenceTable);

        properties.put(fieldCode, relatedRecordsField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenExpressionUseOtherTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Table_2";

        SubTableField subTableField = new SubTableField(fieldCode);

        CalculatedField calculatedField = new CalculatedField("計算_0");
        calculatedField.setExpression("数値_0 +  数値_1");

        subTableField.addField(calculatedField);

        properties.put(fieldCode, subTableField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenExpressionUseOtherTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Table_2";

        SubTableField subTableField = new SubTableField(fieldCode);

        CalculatedField calculatedField = new CalculatedField("計算_0");
        calculatedField.setExpression("数値_0 +  数値_1");

        subTableField.addField(calculatedField);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenExpressionUseTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "計算";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setExpression("数値_0 +  数値_1");

        properties.put(fieldCode, calculatedField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenExpressionUseTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "計算";

        CalculatedField calculatedField = new CalculatedField(fieldCode);
        calculatedField.setExpression("数値_0 +  数値_1");

        properties.put(fieldCode, calculatedField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidExpression() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setExpression("hoge");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidExpressionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setExpression("hoge");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ドロップダウン";

        DropDownField dropDownField = new DropDownField(fieldCode);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();
        dropDownOption.put("sample1", new OptionData(1, "sample1"));

        dropDownField.setOptions(dropDownOption);
        dropDownField.setDefaultValue("jpge");

        properties.put(fieldCode, dropDownField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ドロップダウン";

        DropDownField dropDownField = new DropDownField(fieldCode);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();
        dropDownOption.put("sample1", new OptionData(1, "sample1"));

        dropDownField.setOptions(dropDownOption);
        dropDownField.setDefaultValue("jpge");

        properties.put(fieldCode, dropDownField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValueInRadioButton() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ラジオボタン";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("sample2");
        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithInvalidDefaultValueInRadioButtonCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ラジオボタン";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample1", new OptionData(1, "sample1"));

        radioButtonField.setDefaultValue("sample2");
        radioButtonField.setOptions(radioOption);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    // KCB-651
    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValue() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "複数選択";

        MultipleSelectField multipleSelectField = new MultipleSelectField(fieldCode);

        HashMap<String, OptionData> multipleSelectOption = new HashMap<>();
        multipleSelectOption.put("sample1", new OptionData(0, "sample1"));
        multipleSelectField.setOptions(multipleSelectOption);
        multipleSelectField.setDefaultValue(null);

        properties.put(fieldCode, multipleSelectField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    // KCB-651
    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "複数選択";

        MultipleSelectField multipleSelectField = new MultipleSelectField(fieldCode);

        HashMap<String, OptionData> multipleSelectOption = new HashMap<>();
        multipleSelectOption.put("sample1", new OptionData(0, "sample1"));
        multipleSelectField.setOptions(multipleSelectOption);
        multipleSelectField.setDefaultValue(null);

        properties.put(fieldCode, multipleSelectField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    // KCB-651
    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInCheckBox() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "チェックボックス";

        CheckboxField checkboxField = new CheckboxField(fieldCode);

        HashMap<String, OptionData> checkBoxOption = new HashMap<>();
        checkBoxOption.put("sample1", new OptionData(1, "sample1"));

        checkboxField.setOptions(checkBoxOption);
        checkboxField.setDefaultValue(null);

        properties.put(fieldCode, checkboxField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    // KCB-651
    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInCheckBoxCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "チェックボックス";

        CheckboxField checkboxField = new CheckboxField(fieldCode);

        HashMap<String, OptionData> checkBoxOption = new HashMap<>();
        checkBoxOption.put("sample1", new OptionData(1, "sample1"));

        checkboxField.setOptions(checkBoxOption);
        checkboxField.setDefaultValue(null);

        properties.put(fieldCode, checkboxField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInDropdown() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ドロップダウン2";

        DropDownField dropDownField = new DropDownField(fieldCode);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();
        dropDownOption.put("sample2", new OptionData(2, "sample2"));

        dropDownField.setOptions(dropDownOption);
        dropDownField.setDefaultValue(null);

        properties.put(fieldCode, dropDownField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInDropdownCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ドロップダウン2";

        DropDownField dropDownField = new DropDownField(fieldCode);
        HashMap<String, OptionData> dropDownOption = new HashMap<>();
        dropDownOption.put("sample2", new OptionData(2, "sample2"));

        dropDownField.setOptions(dropDownOption);
        dropDownField.setDefaultValue(null);

        properties.put(fieldCode, dropDownField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInRadioButton() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ラジオボタン";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample3", new OptionData(3, "sample3"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setDefaultValue(null);

        properties.put(fieldCode, radioButtonField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenDeleteDefaultValueInRadioButtonCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ラジオボタン";

        RadioButtonField radioButtonField = new RadioButtonField(fieldCode);
        HashMap<String, OptionData> radioOption = new HashMap<>();
        radioOption.put("sample3", new OptionData(3, "sample3"));

        radioButtonField.setOptions(radioOption);
        radioButtonField.setDefaultValue(null);

        properties.put(fieldCode, radioButtonField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedUser() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang1");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedUserCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("yfang1");
        memberSelectEntity.setType(MemberSelectEntityType.USER);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedGroup() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("team");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedGroupCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("team");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedOrg() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("org");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedOrgCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "ユーザー選択";

        UserSelectionField userSelectionField = new UserSelectionField(fieldCode);
        ArrayList<MemberSelectEntity> userList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();

        memberSelectEntity.setCode("org");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);

        userList.add(memberSelectEntity);

        userSelectionField.setEntities(userList);
        userSelectionField.setDefaultValue(userList);

        properties.put(fieldCode, userSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedGroupInGroupSelection() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "グループ選択";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("Team");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);
        groupList.add(memberSelectEntity);

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedGroupInGroupSelectionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "グループ選択";

        GroupSelectionField groupSelectionField = new GroupSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> groupList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("Team");
        memberSelectEntity.setType(MemberSelectEntityType.GROUP);
        groupList.add(memberSelectEntity);

        groupSelectionField.setDefaultValue(groupList);
        groupSelectionField.setEntities(groupList);

        properties.put(fieldCode, groupSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedOrgInOrgSelection() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "組織選択";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織1");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.appManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWithUnexistedOrgInOrgSelectionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "組織選択";

        DepartmentSelectionField departmentSelectionField = new DepartmentSelectionField(fieldCode);

        ArrayList<MemberSelectEntity> orgList = new ArrayList<>();
        MemberSelectEntity memberSelectEntity = new MemberSelectEntity();
        memberSelectEntity.setCode("検証組織1");
        memberSelectEntity.setType(MemberSelectEntityType.ORGANIZATION);
        orgList.add(memberSelectEntity);

        departmentSelectionField.setDefaultValue(orgList);
        departmentSelectionField.setEntities(orgList);

        properties.put(fieldCode, departmentSelectionField);
        this.certAppManagerment.updateFormFields(UPADATE_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.addFormFieldTokenAppManagerment.updateFormFields(APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdNotExists() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(99999, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdNotExistsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(99999, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdNegativeNumber() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(-1, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdNegativeNumberCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(-1, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdZero() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(0, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsFailWhenAppIdZeroCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(0, properties, null);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenRevisionIsMinusOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        BasicResponse response = this.appManagerment.updateFormFields(APP_ID, properties, -1);
        assertNotNull(response);
    }

    @Test
    public void testUpdateFormFieldsShouldSuccessWhenRevisionIsMinusOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        BasicResponse response = this.certAppManagerment.updateFormFields(APP_ID, properties, -1);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionIsOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(APP_ID, properties, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionIsOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(APP_ID, properties, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionIsZero() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(APP_ID, properties, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionIsZeroCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(APP_ID, properties, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(APP_ID, properties, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(APP_ID, properties, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenHasNoPermission() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.updateFormFields(NO_PERMISSION_APP_ID, properties, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateFormFieldsShouldFailWhenHasNoPermissionCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Text";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.updateFormFields(NO_PERMISSION_APP_ID, properties, null);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccess() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Singlin_Txt";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.addFormFields(APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("Singlin_Txt");

        BasicResponse response = this.appManagerment.deleteFormFields(APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Singlin_Txt";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.addFormFields(APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("Singlin_Txt");

        BasicResponse response = this.appManagerment.deleteFormFields(APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenRevisionIsNegativeOne() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Sinlne_Txt";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Sinlne_Txt");
        BasicResponse response = this.appManagerment.deleteFormFields(APP_ID, fields, -1);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenRevisionIsNegativeOneCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "Sinlne_Txt";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.addFormFields(APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Sinlne_Txt");
        BasicResponse response = this.certAppManagerment.deleteFormFields(APP_ID, fields, -1);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionIsOne() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(APP_ID, fields, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionIsOneCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(APP_ID, fields, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionIsZero() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(APP_ID, fields, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionIsZeroCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(APP_ID, fields, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(APP_ID, fields, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(APP_ID, fields, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenUseTokenAPI() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.addFormFieldTokenAppManagerment.deleteFormFields(APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdNotExists() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(99999, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdNotExistsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(99999, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdNegativeNumber() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(-1, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdNegativeNumberCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(-1, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdZero() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(0, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsFailWhenAppIdZeroCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(0, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenHasNoPermission() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.appManagerment.deleteFormFields(NO_PERMISSION_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenHasNoPermissionCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("Singline_Text");
        this.certAppManagerment.deleteFormFields(NO_PERMISSION_APP_ID, fields, null);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteSpecialFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("ステータス");
        fields.add("作業者");
        fields.add("カテゴリー");

        BasicResponse response = this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteSpecialFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("ステータス");
        fields.add("作業者");
        fields.add("カテゴリー");

        BasicResponse response = this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    // Execute after add below fields
    @Ignore
    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteInsideFields() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("作成者");
        fields.add("作成日時");
        fields.add("更新者");
        fields.add("更新日時");

        BasicResponse response = this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    // Execute after add below fields
    @Ignore
    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteInsideFieldsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("作成者");
        fields.add("作成日時");
        fields.add("更新者");
        fields.add("更新日時");

        BasicResponse response = this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    // Execute after add records
    @Ignore
    @Test
    public void testDeleteFormFieldsShouldSuccessWhenHasRecords() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行__2");
        BasicResponse response = this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    // Execute after add records
    @Ignore
    @Test
    public void testDeleteFormFieldsShouldSuccessWhenHasRecordsCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("文字列__1行__2");
        BasicResponse response = this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteGroupAndSubTable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode1 = "group";

        FieldGroup createFieldGroup = createFieldGroup(fieldCode1);
        properties.put(fieldCode1, createFieldGroup);

        String fieldCode2 = "SubTable";
        SubTableField createSubTableField = createSubTableField(fieldCode2);

        properties.put(fieldCode2, createSubTableField);
        this.appManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add(fieldCode1);
        fields.add(fieldCode2);

        BasicResponse response = this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteGroupAndSubTableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode1 = "group";

        FieldGroup createFieldGroup = createFieldGroup(fieldCode1);
        properties.put(fieldCode1, createFieldGroup);

        String fieldCode2 = "SubTable";
        SubTableField createSubTableField = createSubTableField(fieldCode2);

        properties.put(fieldCode2, createSubTableField);
        this.certAppManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add(fieldCode1);
        fields.add(fieldCode2);

        BasicResponse response = this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteAllFields() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode1 = "文字列1行1";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode1);
        properties.put(fieldCode1, createSingleLineTextField);

        String fieldCode2 = "リッチエディタ1";
        RichTextField createRichTextField = createRichTextField(fieldCode2);
        properties.put(fieldCode2, createRichTextField);

        String fieldCode3 = "文字列複数行1";
        MultiLineTextField createMultiLineTextField = createMultiLineTextField(fieldCode3);
        properties.put(fieldCode3, createMultiLineTextField);

        String fieldCode4 = "数値1";
        NumberField createNumberField = createNumberField(fieldCode4);
        properties.put(fieldCode4, createNumberField);

        String fieldCode5 = "計算1";
        CalculatedField createCalculatedField = createCalculatedField(fieldCode5);
        properties.put(fieldCode5, createCalculatedField);

        String fieldCode6 = "ラジオボタン1";
        RadioButtonField createRadioButtonField = createRadioButtonField(fieldCode6);
        properties.put(fieldCode6, createRadioButtonField);

        String fieldCode7 = "チェックボックス1";
        CheckboxField createCheckboxField = createCheckboxField(fieldCode7);
        properties.put(fieldCode7, createCheckboxField);

        String fieldCode8 = "複数選択1";
        MultipleSelectField createMultipleSelectField = createMultipleSelectField(fieldCode8);
        properties.put(fieldCode8, createMultipleSelectField);

        String fieldCode9 = "ドロップダウン1";
        DropDownField createDropDownField = createDropDownField(fieldCode9);
        properties.put(fieldCode9, createDropDownField);

        String fieldCode10 = "日付1";
        DateField createDateField = createDateField(fieldCode10);
        properties.put(fieldCode10, createDateField);

        String fieldCode11 = "時刻1";
        TimeField createTimeField = createTimeField(fieldCode11);
        properties.put(fieldCode11, createTimeField);

        String fieldCode12 = "日時1";
        DateTimeField createDateTimeField = createDateTimeField(fieldCode12);
        properties.put(fieldCode12, createDateTimeField);

        String fieldCode13 = "添付ファイル1";
        AttachmentField createAttachmentField = createAttachmentField(fieldCode13);
        properties.put(fieldCode13, createAttachmentField);

        String fieldCode14 = "リンクWEB1";
        LinkField createLinkField = createLinkField(fieldCode14);
        properties.put(fieldCode14, createLinkField);

        String fieldCode15 = "ルックアップ1";
        LookupField createLookupField = createLookupField(fieldCode15);
        properties.put(fieldCode15, createLookupField);

        String fieldCode16 = "関連レコード1";
        RelatedRecordsField createRelatedRecordsField = createRelatedRecordsField(fieldCode16);
        properties.put(fieldCode16, createRelatedRecordsField);

        String fieldCode17 = "ユーザー選択1";
        UserSelectionField createUserSelectionField = createUserSelectionField(fieldCode17);
        properties.put(fieldCode17, createUserSelectionField);

        String fieldCode18 = "グループ選択選択1";
        GroupSelectionField createGroupSelectionField = createGroupSelectionField(fieldCode18);
        properties.put(fieldCode18, createGroupSelectionField);

        String fieldCode19 = "組織選択1";
        DepartmentSelectionField createDepartmentSelectionField = createDepartmentSelectionField(fieldCode19);
        properties.put(fieldCode19, createDepartmentSelectionField);

        this.appManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add(fieldCode1);
        fields.add(fieldCode2);
        fields.add(fieldCode3);
        fields.add(fieldCode4);
        fields.add(fieldCode5);
        fields.add(fieldCode6);
        fields.add(fieldCode7);
        fields.add(fieldCode8);
        fields.add(fieldCode9);
        fields.add(fieldCode10);
        fields.add(fieldCode11);
        fields.add(fieldCode12);
        fields.add(fieldCode13);
        fields.add(fieldCode14);
        fields.add(fieldCode15);
        fields.add(fieldCode16);
        fields.add(fieldCode17);
        fields.add(fieldCode18);
        fields.add(fieldCode19);
        BasicResponse response = this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    @Test
    public void testDeleteFormFieldsShouldSuccessWhenDeleteAllFieldsCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode1 = "文字列1行1";

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode1);
        properties.put(fieldCode1, createSingleLineTextField);

        String fieldCode2 = "リッチエディタ1";
        RichTextField createRichTextField = createRichTextField(fieldCode2);
        properties.put(fieldCode2, createRichTextField);

        String fieldCode3 = "文字列複数行1";
        MultiLineTextField createMultiLineTextField = createMultiLineTextField(fieldCode3);
        properties.put(fieldCode3, createMultiLineTextField);

        String fieldCode4 = "数値1";
        NumberField createNumberField = createNumberField(fieldCode4);
        properties.put(fieldCode4, createNumberField);

        String fieldCode5 = "計算1";
        CalculatedField createCalculatedField = createCalculatedField(fieldCode5);
        properties.put(fieldCode5, createCalculatedField);

        String fieldCode6 = "ラジオボタン1";
        RadioButtonField createRadioButtonField = createRadioButtonField(fieldCode6);
        properties.put(fieldCode6, createRadioButtonField);

        String fieldCode7 = "チェックボックス1";
        CheckboxField createCheckboxField = createCheckboxField(fieldCode7);
        properties.put(fieldCode7, createCheckboxField);

        String fieldCode8 = "複数選択1";
        MultipleSelectField createMultipleSelectField = createMultipleSelectField(fieldCode8);
        properties.put(fieldCode8, createMultipleSelectField);

        String fieldCode9 = "ドロップダウン1";
        DropDownField createDropDownField = createDropDownField(fieldCode9);
        properties.put(fieldCode9, createDropDownField);

        String fieldCode10 = "日付1";
        DateField createDateField = createDateField(fieldCode10);
        properties.put(fieldCode10, createDateField);

        String fieldCode11 = "時刻1";
        TimeField createTimeField = createTimeField(fieldCode11);
        properties.put(fieldCode11, createTimeField);

        String fieldCode12 = "日時1";
        DateTimeField createDateTimeField = createDateTimeField(fieldCode12);
        properties.put(fieldCode12, createDateTimeField);

        String fieldCode13 = "添付ファイル1";
        AttachmentField createAttachmentField = createAttachmentField(fieldCode13);
        properties.put(fieldCode13, createAttachmentField);

        String fieldCode14 = "リンクWEB1";
        LinkField createLinkField = createLinkField(fieldCode14);
        properties.put(fieldCode14, createLinkField);

        String fieldCode15 = "ルックアップ1";
        LookupField createLookupField = createLookupField(fieldCode15);
        properties.put(fieldCode15, createLookupField);

        String fieldCode16 = "関連レコード1";
        RelatedRecordsField createRelatedRecordsField = createRelatedRecordsField(fieldCode16);
        properties.put(fieldCode16, createRelatedRecordsField);

        String fieldCode17 = "ユーザー選択1";
        UserSelectionField createUserSelectionField = createUserSelectionField(fieldCode17);
        properties.put(fieldCode17, createUserSelectionField);

        String fieldCode18 = "グループ選択選択1";
        GroupSelectionField createGroupSelectionField = createGroupSelectionField(fieldCode18);
        properties.put(fieldCode18, createGroupSelectionField);

        String fieldCode19 = "組織選択1";
        DepartmentSelectionField createDepartmentSelectionField = createDepartmentSelectionField(fieldCode19);
        properties.put(fieldCode19, createDepartmentSelectionField);

        this.certAppManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        fields.add(fieldCode1);
        fields.add(fieldCode2);
        fields.add(fieldCode3);
        fields.add(fieldCode4);
        fields.add(fieldCode5);
        fields.add(fieldCode6);
        fields.add(fieldCode7);
        fields.add(fieldCode8);
        fields.add(fieldCode9);
        fields.add(fieldCode10);
        fields.add(fieldCode11);
        fields.add(fieldCode12);
        fields.add(fieldCode13);
        fields.add(fieldCode14);
        fields.add(fieldCode15);
        fields.add(fieldCode16);
        fields.add(fieldCode17);
        fields.add(fieldCode18);
        fields.add(fieldCode19);
        BasicResponse response = this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
        assertNotNull(response);
    }

    // Execute will add 100 fields
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenFieldsOverflow() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "文字列1行";

        for (int i = 0, j = 0; i < 101 & j < 101; i++, j++) {
            SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode + i);
            properties.put(fieldCode + j, createSingleLineTextField);
        }

        this.appManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        for (int k = 0; k < 101; k++) {
            fields.add(fieldCode + k);
        }

        this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    // Execute will add 100 fields
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenFieldsOverflowCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<String, Field>();
        String fieldCode = "文字列1行1";

        for (int i = 0, j = 0; i < 101 & j < 101; i++, j++) {
            SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode + i);
            properties.put(fieldCode + j, createSingleLineTextField);
        }

        this.certAppManagerment.addFormFields(UPADATE_APP_ID, properties, null);

        ArrayList<String> fields = new ArrayList<String>();
        for (int k = 0; k < 101; k++) {
            fields.add(fieldCode + k);
        }

        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithoutApp() throws KintoneAPIException {
        String fieldCode = "文字列1行";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(null, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithoutAppCert() throws KintoneAPIException {
        String fieldCode = "文字列1行";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(null, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithoutFields() throws KintoneAPIException {
        this.appManagerment.deleteFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithoutFieldsCert() throws KintoneAPIException {
        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteUserSelectWithProcessManOn() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();

        fields.add("ユーザー選択_0");
        fields.add("グループ選択_0");
        fields.add("組織選択_0");

        this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteUserSelectWithProcessManOnCert() throws KintoneAPIException {
        ArrayList<String> fields = new ArrayList<String>();

        fields.add("ユーザー選択_0");
        fields.add("グループ選択_0");
        fields.add("組織選択_0");

        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithUnexistedField() throws KintoneAPIException {
        String fieldCode = "Text1";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWithUnexistedFieldCert() throws KintoneAPIException {
        String fieldCode = "Text1";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteFieldWhichHasExpression() throws KintoneAPIException {
        String fieldCode = "Text";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteFieldWhichHasExpressionCert() throws KintoneAPIException {
        String fieldCode = "Text";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteFieldWhichHasCal() throws KintoneAPIException {
        String fieldCode = "数値";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.appManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteFormFieldsShouldFailWhenDeleteFieldWhichHasCalCert() throws KintoneAPIException {
        String fieldCode = "数値";
        ArrayList<String> fields = new ArrayList<String>();

        fields.add(fieldCode);
        this.certAppManagerment.deleteFormFields(UPADATE_APP_ID, fields, null);
    }

    @Test
    public void testUpdateRowLayoutShouldSuccess() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        BasicResponse response = this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRowLayoutShouldSuccessCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        BasicResponse response = this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRowLayoutGuestSpaceShouldSuccess() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        FieldSize singleFieldSizeRowLayout = new FieldSize();
        singleFieldSizeRowLayout.setWidth("193");
        singleFieldRowLayout.setSize(singleFieldSizeRowLayout);
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        FieldSize radioButtonFieldSizeRowLayout = new FieldSize();
        radioButtonFieldSizeRowLayout.setWidth("168");
        radioButtonFieldRowLayout.setSize(radioButtonFieldSizeRowLayout);
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.guestSpaceAppManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID,
                itemLayoutRequest, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRowLayoutGuestSpaceShouldSuccessCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        FieldSize singleFieldSizeRowLayout = new FieldSize();
        singleFieldSizeRowLayout.setWidth("193");
        singleFieldRowLayout.setSize(singleFieldSizeRowLayout);
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        FieldSize radioButtonFieldSizeRowLayout = new FieldSize();
        radioButtonFieldSizeRowLayout.setWidth("168");
        radioButtonFieldRowLayout.setSize(radioButtonFieldSizeRowLayout);
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.certGuestAppManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID,
                itemLayoutRequest, null);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRowLayoutShouldSuccessWhenRevisionIsMinusOne() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout3);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.appManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    @Test
    public void testUpdateRowLayoutShouldSuccessWhenRevisionIsMinusOneCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout3);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.certAppManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KINTONE-14104
    @Ignore
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenGroupLayoutNull() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        ArrayList<RowLayout> subLayout = new ArrayList<>();
        subLayout.add(null);
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(subLayout);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        BasicResponse response = this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KINTONE-14104
    @Ignore
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenGroupLayoutNullCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        ArrayList<RowLayout> subLayout = new ArrayList<>();
        subLayout.add(null);
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(subLayout);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        BasicResponse response = this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KCB-652
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeSpaceElementId() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout labelFieldRowLayout = new FieldLayout();
        labelFieldRowLayout.setType(FieldType.LABEL.toString());
        labelFieldRowLayout.setLabel("ラベル");
        fieldsRowLayout.add(labelFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("update");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.appManagerment.updateFormLayout(1800, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KCB-652
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeSpaceElementIdCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout labelFieldRowLayout = new FieldLayout();
        labelFieldRowLayout.setType(FieldType.LABEL.toString());
        labelFieldRowLayout.setLabel("ラベルlabe");
        fieldsRowLayout.add(labelFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("update");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);

        BasicResponse response = this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // Execute after delete Text2
    @Ignore
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeOrderOfSubtable() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Table";

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext2 = createSingleLineTextField("Text2");
        subTableField.addField(tabletext2);

        properties.put(fieldCode, subTableField);
        this.appManagerment.addFormFields(1797, properties, null);

        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableRowLayout = new SubTableLayout();
        subTableRowLayout.setCode(fieldCode);
        subTableRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableRowLayout);
        BasicResponse response = this.appManagerment.updateFormLayout(1797, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // Execute after delete Text2
    @Ignore
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeOrderOfSubtableCert() throws KintoneAPIException {
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Table";

        SubTableField subTableField = new SubTableField(fieldCode);
        SingleLineTextField tabletext2 = createSingleLineTextField("Text2");
        subTableField.addField(tabletext2);

        properties.put(fieldCode, subTableField);
        this.certAppManagerment.addFormFields(1797, properties, null);

        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableRowLayout = new SubTableLayout();
        subTableRowLayout.setCode(fieldCode);
        subTableRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableRowLayout);
        BasicResponse response = this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // Execute after change position
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangePositionOfFields() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout2);

        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout3);

        rowLayout2.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(rowLayout2);
        BasicResponse response = this.appManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // Execute after change position
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangePositionOfFieldsCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout2);

        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout3);

        rowLayout2.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(rowLayout2);
        BasicResponse response = this.certAppManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KCB-652
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeWidthAndHeightAndInnerHeight() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<ArrayList<FieldLayout>> fieldsRowLayouts = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
            fieldsRowLayouts.add(fieldsRowLayout);
        }

        ArrayList<RowLayout> rowLayouts = new ArrayList<>();
        for (int j = 0; j < 29; j++) {
            RowLayout rowLayout = new RowLayout();
            rowLayouts.add(rowLayout);
        }

        FieldSize size = new FieldSize();
        size.setWidth("500");
        size.setInnerHeight("600");
        size.setHeight("700");

        FieldLayout recordNumberFieldRowLayout = new FieldLayout();
        recordNumberFieldRowLayout.setCode("レコード番号");
        recordNumberFieldRowLayout.setType(FieldType.RECORD_NUMBER.toString());
        recordNumberFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(0).add(recordNumberFieldRowLayout);
        rowLayouts.get(0).setFields(fieldsRowLayouts.get(0));

        FieldLayout modifierFieldRowLayout = new FieldLayout();
        modifierFieldRowLayout.setCode("更新者");
        modifierFieldRowLayout.setType(FieldType.MODIFIER.toString());
        modifierFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(1).add(modifierFieldRowLayout);
        rowLayouts.get(1).setFields(fieldsRowLayouts.get(1));

        FieldLayout creatorFieldRowLayout = new FieldLayout();
        creatorFieldRowLayout.setCode("作成者");
        creatorFieldRowLayout.setType(FieldType.CREATOR.toString());
        creatorFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(2).add(creatorFieldRowLayout);
        rowLayouts.get(2).setFields(fieldsRowLayouts.get(2));

        FieldLayout updateTimeFieldRowLayout = new FieldLayout();
        updateTimeFieldRowLayout.setCode("更新日時");
        updateTimeFieldRowLayout.setType(FieldType.UPDATED_TIME.toString());
        updateTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(3).add(updateTimeFieldRowLayout);
        rowLayouts.get(3).setFields(fieldsRowLayouts.get(3));

        FieldLayout createTimeFieldRowLayout = new FieldLayout();
        createTimeFieldRowLayout.setCode("作成日時");
        createTimeFieldRowLayout.setType(FieldType.CREATED_TIME.toString());
        createTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(4).add(createTimeFieldRowLayout);
        rowLayouts.get(4).setFields(fieldsRowLayouts.get(4));

        FieldLayout singleLineTextFieldRowLayout = new FieldLayout();
        singleLineTextFieldRowLayout.setCode("文字列__1行_");
        singleLineTextFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleLineTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(5).add(singleLineTextFieldRowLayout);
        rowLayouts.get(5).setFields(fieldsRowLayouts.get(5));

        FieldLayout richTextFieldRowLayout = new FieldLayout();
        richTextFieldRowLayout.setCode("リッチエディター");
        richTextFieldRowLayout.setType(FieldType.RICH_TEXT.toString());
        richTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(6).add(richTextFieldRowLayout);
        rowLayouts.get(6).setFields(fieldsRowLayouts.get(6));

        FieldLayout multiLineTextFieldRowLayout = new FieldLayout();
        multiLineTextFieldRowLayout.setCode("文字列__複数行_");
        multiLineTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        multiLineTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(7).add(multiLineTextFieldRowLayout);
        rowLayouts.get(7).setFields(fieldsRowLayouts.get(7));

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        numberFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(8).add(numberFieldRowLayout);
        rowLayouts.get(8).setFields(fieldsRowLayouts.get(8));

        FieldLayout calculateFieldRowLayout = new FieldLayout();
        calculateFieldRowLayout.setCode("計算");
        calculateFieldRowLayout.setType(FieldType.CALC.toString());
        calculateFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(9).add(calculateFieldRowLayout);
        rowLayouts.get(9).setFields(fieldsRowLayouts.get(9));

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("ラジオボタン");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        radioButtonFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(10).add(radioButtonFieldRowLayout);
        rowLayouts.get(10).setFields(fieldsRowLayouts.get(10));

        FieldLayout checkBoxFieldRowLayout = new FieldLayout();
        checkBoxFieldRowLayout.setCode("チェックボックス");
        checkBoxFieldRowLayout.setType(FieldType.CHECK_BOX.toString());
        checkBoxFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(11).add(checkBoxFieldRowLayout);
        rowLayouts.get(11).setFields(fieldsRowLayouts.get(11));

        FieldLayout multiSelectFieldRowLayout = new FieldLayout();
        multiSelectFieldRowLayout.setCode("複数選択");
        multiSelectFieldRowLayout.setType(FieldType.MULTI_SELECT.toString());
        multiSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(12).add(multiSelectFieldRowLayout);
        rowLayouts.get(12).setFields(fieldsRowLayouts.get(12));

        FieldLayout dropDownFieldRowLayout = new FieldLayout();
        dropDownFieldRowLayout.setCode("ドロップダウン");
        dropDownFieldRowLayout.setType(FieldType.DROP_DOWN.toString());
        dropDownFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(13).add(dropDownFieldRowLayout);
        rowLayouts.get(13).setFields(fieldsRowLayouts.get(13));

        FieldLayout dateFieldRowLayout = new FieldLayout();
        dateFieldRowLayout.setCode("日付");
        dateFieldRowLayout.setType(FieldType.DATE.toString());
        dateFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(14).add(dateFieldRowLayout);
        rowLayouts.get(14).setFields(fieldsRowLayouts.get(14));

        FieldLayout timeFieldRowLayout = new FieldLayout();
        timeFieldRowLayout.setCode("時刻");
        timeFieldRowLayout.setType(FieldType.TIME.toString());
        timeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(15).add(timeFieldRowLayout);
        rowLayouts.get(15).setFields(fieldsRowLayouts.get(15));

        FieldLayout dateTimeFieldRowLayout = new FieldLayout();
        dateTimeFieldRowLayout.setCode("日時");
        dateTimeFieldRowLayout.setType(FieldType.DATETIME.toString());
        dateTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(16).add(dateTimeFieldRowLayout);
        rowLayouts.get(16).setFields(fieldsRowLayouts.get(16));

        FieldLayout attachmentFieldRowLayout = new FieldLayout();
        attachmentFieldRowLayout.setCode("添付ファイル");
        attachmentFieldRowLayout.setType(FieldType.FILE.toString());
        attachmentFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(17).add(attachmentFieldRowLayout);
        rowLayouts.get(17).setFields(fieldsRowLayouts.get(17));

        FieldLayout linkWebFieldRowLayout = new FieldLayout();
        linkWebFieldRowLayout.setCode("リンク_web");
        linkWebFieldRowLayout.setType(FieldType.LINK.toString());
        linkWebFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(18).add(linkWebFieldRowLayout);
        rowLayouts.get(18).setFields(fieldsRowLayouts.get(18));

        FieldLayout linkTelFieldRowLayout = new FieldLayout();
        linkTelFieldRowLayout.setCode("リンク_tel");
        linkTelFieldRowLayout.setType(FieldType.LINK.toString());
        linkTelFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(19).add(linkTelFieldRowLayout);
        rowLayouts.get(19).setFields(fieldsRowLayouts.get(19));

        FieldLayout linkMailFieldRowLayout = new FieldLayout();
        linkMailFieldRowLayout.setCode("リンク_mail");
        linkMailFieldRowLayout.setType(FieldType.LINK.toString());
        linkMailFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(20).add(linkMailFieldRowLayout);
        rowLayouts.get(20).setFields(fieldsRowLayouts.get(20));

        FieldLayout userSelectFieldRowLayout = new FieldLayout();
        userSelectFieldRowLayout.setCode("ユーザー選択");
        userSelectFieldRowLayout.setType(FieldType.USER_SELECT.toString());
        userSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(21).add(userSelectFieldRowLayout);
        rowLayouts.get(21).setFields(fieldsRowLayouts.get(21));

        FieldLayout orgSelectFieldRowLayout = new FieldLayout();
        orgSelectFieldRowLayout.setCode("組織選択");
        orgSelectFieldRowLayout.setType(FieldType.ORGANIZATION_SELECT.toString());
        orgSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(22).add(orgSelectFieldRowLayout);
        rowLayouts.get(22).setFields(fieldsRowLayouts.get(22));

        FieldLayout groupSelectFieldRowLayout = new FieldLayout();
        groupSelectFieldRowLayout.setCode("グループ選択");
        groupSelectFieldRowLayout.setType(FieldType.GROUP_SELECT.toString());
        groupSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(23).add(groupSelectFieldRowLayout);
        rowLayouts.get(23).setFields(fieldsRowLayouts.get(23));

        FieldLayout lookupFieldRowLayout = new FieldLayout();
        lookupFieldRowLayout.setCode("ルックアップ");
        lookupFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        lookupFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(24).add(lookupFieldRowLayout);
        rowLayouts.get(24).setFields(fieldsRowLayouts.get(24));

        FieldLayout relatedRecordsFieldRowLayout = new FieldLayout();
        relatedRecordsFieldRowLayout.setCode("関連レコード一覧");
        relatedRecordsFieldRowLayout.setType(FieldType.REFERENCE_TABLE.toString());
        relatedRecordsFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(25).add(relatedRecordsFieldRowLayout);
        rowLayouts.get(25).setFields(fieldsRowLayouts.get(25));

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("space");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        spaceFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(26).add(spaceFieldRowLayout);
        rowLayouts.get(26).setFields(fieldsRowLayouts.get(26));

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        stringFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(27).add(stringFieldRowLayout);
        rowLayouts.get(27).setFields(fieldsRowLayouts.get(27));

        FieldLayout labelFieldRowLayout = new FieldLayout();
        labelFieldRowLayout.setType(FieldType.LABEL.toString());
        labelFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(28).add(labelFieldRowLayout);
        rowLayouts.get(28).setFields(fieldsRowLayouts.get(28));

        itemLayoutRequest.addAll(rowLayouts);

        BasicResponse response = this.appManagerment.updateFormLayout(1799, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    // KCB-652
    @Test
    public void testUpdateRowLayoutShouldSuccessWhenChangeWidthAndHeightAndInnerHeightCert()
            throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<ArrayList<FieldLayout>> fieldsRowLayouts = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
            fieldsRowLayouts.add(fieldsRowLayout);
        }

        ArrayList<RowLayout> rowLayouts = new ArrayList<>();
        for (int j = 0; j < 29; j++) {
            RowLayout rowLayout = new RowLayout();
            rowLayouts.add(rowLayout);
        }

        FieldSize size = new FieldSize();
        size.setWidth("500");
        size.setInnerHeight("600");
        size.setHeight("700");

        FieldLayout recordNumberFieldRowLayout = new FieldLayout();
        recordNumberFieldRowLayout.setCode("レコード番号");
        recordNumberFieldRowLayout.setType(FieldType.RECORD_NUMBER.toString());
        recordNumberFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(0).add(recordNumberFieldRowLayout);
        rowLayouts.get(0).setFields(fieldsRowLayouts.get(0));

        FieldLayout modifierFieldRowLayout = new FieldLayout();
        modifierFieldRowLayout.setCode("更新者");
        modifierFieldRowLayout.setType(FieldType.MODIFIER.toString());
        modifierFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(1).add(modifierFieldRowLayout);
        rowLayouts.get(1).setFields(fieldsRowLayouts.get(1));

        FieldLayout creatorFieldRowLayout = new FieldLayout();
        creatorFieldRowLayout.setCode("作成者");
        creatorFieldRowLayout.setType(FieldType.CREATOR.toString());
        creatorFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(2).add(creatorFieldRowLayout);
        rowLayouts.get(2).setFields(fieldsRowLayouts.get(2));

        FieldLayout updateTimeFieldRowLayout = new FieldLayout();
        updateTimeFieldRowLayout.setCode("更新日時");
        updateTimeFieldRowLayout.setType(FieldType.UPDATED_TIME.toString());
        updateTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(3).add(updateTimeFieldRowLayout);
        rowLayouts.get(3).setFields(fieldsRowLayouts.get(3));

        FieldLayout createTimeFieldRowLayout = new FieldLayout();
        createTimeFieldRowLayout.setCode("作成日時");
        createTimeFieldRowLayout.setType(FieldType.CREATED_TIME.toString());
        createTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(4).add(createTimeFieldRowLayout);
        rowLayouts.get(4).setFields(fieldsRowLayouts.get(4));

        FieldLayout singleLineTextFieldRowLayout = new FieldLayout();
        singleLineTextFieldRowLayout.setCode("文字列__1行_");
        singleLineTextFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleLineTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(5).add(singleLineTextFieldRowLayout);
        rowLayouts.get(5).setFields(fieldsRowLayouts.get(5));

        FieldLayout richTextFieldRowLayout = new FieldLayout();
        richTextFieldRowLayout.setCode("リッチエディター");
        richTextFieldRowLayout.setType(FieldType.RICH_TEXT.toString());
        richTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(6).add(richTextFieldRowLayout);
        rowLayouts.get(6).setFields(fieldsRowLayouts.get(6));

        FieldLayout multiLineTextFieldRowLayout = new FieldLayout();
        multiLineTextFieldRowLayout.setCode("文字列__複数行_");
        multiLineTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        multiLineTextFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(7).add(multiLineTextFieldRowLayout);
        rowLayouts.get(7).setFields(fieldsRowLayouts.get(7));

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        numberFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(8).add(numberFieldRowLayout);
        rowLayouts.get(8).setFields(fieldsRowLayouts.get(8));

        FieldLayout calculateFieldRowLayout = new FieldLayout();
        calculateFieldRowLayout.setCode("計算");
        calculateFieldRowLayout.setType(FieldType.CALC.toString());
        calculateFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(9).add(calculateFieldRowLayout);
        rowLayouts.get(9).setFields(fieldsRowLayouts.get(9));

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("ラジオボタン");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        radioButtonFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(10).add(radioButtonFieldRowLayout);
        rowLayouts.get(10).setFields(fieldsRowLayouts.get(10));

        FieldLayout checkBoxFieldRowLayout = new FieldLayout();
        checkBoxFieldRowLayout.setCode("チェックボックス");
        checkBoxFieldRowLayout.setType(FieldType.CHECK_BOX.toString());
        checkBoxFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(11).add(checkBoxFieldRowLayout);
        rowLayouts.get(11).setFields(fieldsRowLayouts.get(11));

        FieldLayout multiSelectFieldRowLayout = new FieldLayout();
        multiSelectFieldRowLayout.setCode("複数選択");
        multiSelectFieldRowLayout.setType(FieldType.MULTI_SELECT.toString());
        multiSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(12).add(multiSelectFieldRowLayout);
        rowLayouts.get(12).setFields(fieldsRowLayouts.get(12));

        FieldLayout dropDownFieldRowLayout = new FieldLayout();
        dropDownFieldRowLayout.setCode("ドロップダウン");
        dropDownFieldRowLayout.setType(FieldType.DROP_DOWN.toString());
        dropDownFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(13).add(dropDownFieldRowLayout);
        rowLayouts.get(13).setFields(fieldsRowLayouts.get(13));

        FieldLayout dateFieldRowLayout = new FieldLayout();
        dateFieldRowLayout.setCode("日付");
        dateFieldRowLayout.setType(FieldType.DATE.toString());
        dateFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(14).add(dateFieldRowLayout);
        rowLayouts.get(14).setFields(fieldsRowLayouts.get(14));

        FieldLayout timeFieldRowLayout = new FieldLayout();
        timeFieldRowLayout.setCode("時刻");
        timeFieldRowLayout.setType(FieldType.TIME.toString());
        timeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(15).add(timeFieldRowLayout);
        rowLayouts.get(15).setFields(fieldsRowLayouts.get(15));

        FieldLayout dateTimeFieldRowLayout = new FieldLayout();
        dateTimeFieldRowLayout.setCode("日時");
        dateTimeFieldRowLayout.setType(FieldType.DATETIME.toString());
        dateTimeFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(16).add(dateTimeFieldRowLayout);
        rowLayouts.get(16).setFields(fieldsRowLayouts.get(16));

        FieldLayout attachmentFieldRowLayout = new FieldLayout();
        attachmentFieldRowLayout.setCode("添付ファイル");
        attachmentFieldRowLayout.setType(FieldType.FILE.toString());
        attachmentFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(17).add(attachmentFieldRowLayout);
        rowLayouts.get(17).setFields(fieldsRowLayouts.get(17));

        FieldLayout linkWebFieldRowLayout = new FieldLayout();
        linkWebFieldRowLayout.setCode("リンク_web");
        linkWebFieldRowLayout.setType(FieldType.LINK.toString());
        linkWebFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(18).add(linkWebFieldRowLayout);
        rowLayouts.get(18).setFields(fieldsRowLayouts.get(18));

        FieldLayout linkTelFieldRowLayout = new FieldLayout();
        linkTelFieldRowLayout.setCode("リンク_tel");
        linkTelFieldRowLayout.setType(FieldType.LINK.toString());
        linkTelFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(19).add(linkTelFieldRowLayout);
        rowLayouts.get(19).setFields(fieldsRowLayouts.get(19));

        FieldLayout linkMailFieldRowLayout = new FieldLayout();
        linkMailFieldRowLayout.setCode("リンク_mail");
        linkMailFieldRowLayout.setType(FieldType.LINK.toString());
        linkMailFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(20).add(linkMailFieldRowLayout);
        rowLayouts.get(20).setFields(fieldsRowLayouts.get(20));

        FieldLayout userSelectFieldRowLayout = new FieldLayout();
        userSelectFieldRowLayout.setCode("ユーザー選択");
        userSelectFieldRowLayout.setType(FieldType.USER_SELECT.toString());
        userSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(21).add(userSelectFieldRowLayout);
        rowLayouts.get(21).setFields(fieldsRowLayouts.get(21));

        FieldLayout orgSelectFieldRowLayout = new FieldLayout();
        orgSelectFieldRowLayout.setCode("組織選択");
        orgSelectFieldRowLayout.setType(FieldType.ORGANIZATION_SELECT.toString());
        orgSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(22).add(orgSelectFieldRowLayout);
        rowLayouts.get(22).setFields(fieldsRowLayouts.get(22));

        FieldLayout groupSelectFieldRowLayout = new FieldLayout();
        groupSelectFieldRowLayout.setCode("グループ選択");
        groupSelectFieldRowLayout.setType(FieldType.GROUP_SELECT.toString());
        groupSelectFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(23).add(groupSelectFieldRowLayout);
        rowLayouts.get(23).setFields(fieldsRowLayouts.get(23));

        FieldLayout lookupFieldRowLayout = new FieldLayout();
        lookupFieldRowLayout.setCode("ルックアップ");
        lookupFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        lookupFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(24).add(lookupFieldRowLayout);
        rowLayouts.get(24).setFields(fieldsRowLayouts.get(24));

        FieldLayout relatedRecordsFieldRowLayout = new FieldLayout();
        relatedRecordsFieldRowLayout.setCode("関連レコード一覧");
        relatedRecordsFieldRowLayout.setType(FieldType.REFERENCE_TABLE.toString());
        relatedRecordsFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(25).add(relatedRecordsFieldRowLayout);
        rowLayouts.get(25).setFields(fieldsRowLayouts.get(25));

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("space");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        spaceFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(26).add(spaceFieldRowLayout);
        rowLayouts.get(26).setFields(fieldsRowLayouts.get(26));

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        stringFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(27).add(stringFieldRowLayout);
        rowLayouts.get(27).setFields(fieldsRowLayouts.get(27));

        FieldLayout labelFieldRowLayout = new FieldLayout();
        labelFieldRowLayout.setType(FieldType.LABEL.toString());
        labelFieldRowLayout.setSize(size);
        fieldsRowLayouts.get(28).add(labelFieldRowLayout);
        rowLayouts.get(28).setFields(fieldsRowLayouts.get(28));

        itemLayoutRequest.addAll(rowLayouts);

        BasicResponse response = this.certAppManagerment.updateFormLayout(1799, itemLayoutRequest, -1);
        assertNotNull(response);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithBlankListFields() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithBlankListFieldsCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSubtableDuplicate() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout1 = new SubTableLayout();
        subTableFieldRowLayout1.setCode("Table");
        subTableFieldRowLayout1.setFields(fieldsRowLayout2);

        SubTableLayout subTableFieldRowLayout2 = new SubTableLayout();
        subTableFieldRowLayout2.setCode("Table");
        subTableFieldRowLayout2.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout2);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSubtableDuplicateCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout1 = new SubTableLayout();
        subTableFieldRowLayout1.setCode("Table");
        subTableFieldRowLayout1.setFields(fieldsRowLayout2);

        SubTableLayout subTableFieldRowLayout2 = new SubTableLayout();
        subTableFieldRowLayout2.setCode("Table");
        subTableFieldRowLayout2.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout2);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenGroupDuplicate() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout1 = new SubTableLayout();
        subTableFieldRowLayout1.setCode("Table");
        subTableFieldRowLayout1.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout1 = new GroupLayout();
        groupFieldRowLayout1.setCode("グループ");
        groupFieldRowLayout1.setLayout(null);

        GroupLayout groupFieldRowLayout2 = new GroupLayout();
        groupFieldRowLayout2.setCode("グループ");
        groupFieldRowLayout2.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout1);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout1);
        itemLayoutRequest.add(groupFieldRowLayout2);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenGroupDuplicateCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout1 = new SubTableLayout();
        subTableFieldRowLayout1.setCode("Table");
        subTableFieldRowLayout1.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout1 = new GroupLayout();
        groupFieldRowLayout1.setCode("グループ");
        groupFieldRowLayout1.setLayout(null);

        GroupLayout groupFieldRowLayout2 = new GroupLayout();
        groupFieldRowLayout2.setCode("グループ");
        groupFieldRowLayout2.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout1);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout1);
        itemLayoutRequest.add(groupFieldRowLayout2);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutCode() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout2);

        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout3);

        rowLayout2.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(rowLayout2);
        this.appManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutCodeCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout2);

        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout3);

        rowLayout2.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(rowLayout2);
        this.certAppManagerment.updateFormLayout(1798, itemLayoutRequest, -1);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddStringToSubtable() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddStringToSubtableCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddSpaceToSubtable() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddSpaceToSubtableCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddLabelToSubtable() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.LABEL.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddLabelToSubtableCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.LABEL.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutSubTableAndGroupCode() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutSubTableAndGroupCodeCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceElementIdOverflow() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceElementIdOverflowCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidSpaceElementId() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("123");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidSpaceElementIdCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setElementId("123");
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceElementIdDuplicate() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout1 = new FieldLayout();
        spaceFieldRowLayout1.setElementId("abc");
        spaceFieldRowLayout1.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout1);

        FieldLayout spaceFieldRowLayout2 = new FieldLayout();
        spaceFieldRowLayout2.setElementId("abc");
        spaceFieldRowLayout2.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout2);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    // KCB-652
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceElementIdDuplicateCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout spaceFieldRowLayout1 = new FieldLayout();
        spaceFieldRowLayout1.setElementId("abc");
        spaceFieldRowLayout1.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout1);

        FieldLayout spaceFieldRowLayout2 = new FieldLayout();
        spaceFieldRowLayout2.setElementId("abc");
        spaceFieldRowLayout2.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout2);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenInnerHeightAndWidthOverflow() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("500");
        size.setInnerHeight("10001");
        size.setWidth("10001");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenInnerHeightAndWidthOverflowCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("500");
        size.setInnerHeight("10001");
        size.setWidth("10001");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenInnerHeightAndWidthZero() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("500");
        size.setInnerHeight("0");
        size.setWidth("0");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenInnerHeightAndWidthZeroCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("500");
        size.setInnerHeight("0");
        size.setWidth("0");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidInnerHeightAndWidth() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("900");
        size.setInnerHeight("abc");
        size.setWidth("abc");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidInnerHeightAndWidthCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout3 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();
        RowLayout rowLayout2 = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("500");
        size.setInnerHeight("abc");
        size.setWidth("abc");

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        singleFieldRowLayout.setSize(null);
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setSize(size);
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout3.add(multiTextFieldRowLayout);
        rowLayout2.setFields(fieldsRowLayout3);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout2);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHeightOverflow() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("10001");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHeightOverflowCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("10001");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHeightZero() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("0");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHeightZeroCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("0");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidHeight() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("abc");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithInvalidHeightCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldSize size = new FieldSize();
        size.setHeight("abc");
        size.setInnerHeight("500");
        size.setWidth("500");

        FieldLayout stringFieldRowLayout = new FieldLayout();
        stringFieldRowLayout.setType(FieldType.HR.toString());
        fieldsRowLayout.add(stringFieldRowLayout);

        FieldLayout spaceFieldRowLayout = new FieldLayout();
        spaceFieldRowLayout.setSize(size);
        spaceFieldRowLayout.setType(FieldType.SPACER.toString());
        fieldsRowLayout.add(spaceFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1800, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithWrongFieldType() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithWrongFieldTypeCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutFieldType() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutFieldTypeCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutFields() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        fieldsRowLayout.add(singleFieldRowLayout1);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutFieldsCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        fieldsRowLayout.add(singleFieldRowLayout1);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout);

        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutLayout() throws KintoneAPIException {
        this.appManagerment.updateFormLayout(1797, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutLayoutCert() throws KintoneAPIException {
        this.certAppManagerment.updateFormLayout(1797, null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenFieldCodeDuplicate() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text1");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout3);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(1798, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenFieldCodeDuplicateCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text1");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout2);

        FieldLayout singleFieldRowLayout3 = new FieldLayout();
        singleFieldRowLayout3.setCode("Text3");
        singleFieldRowLayout3.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout3);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(1798, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddFieldToSubTable() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout1.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout1);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout2.add(multiTextFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout2);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAddFieldToSubTableCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout = new RowLayout();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout1.add(radioButtonFieldRowLayout);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout1.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout1);

        FieldLayout multiTextFieldRowLayout = new FieldLayout();
        multiTextFieldRowLayout.setCode("文字列__複数行_");
        multiTextFieldRowLayout.setType(FieldType.MULTI_LINE_TEXT.toString());
        fieldsRowLayout2.add(multiTextFieldRowLayout);
        rowLayout.setFields(fieldsRowLayout2);

        GroupLayout groupFieldRowLayout = new GroupLayout();
        groupFieldRowLayout.setCode("グループ");
        groupFieldRowLayout.setLayout(null);

        itemLayoutRequest.add(subTableFieldRowLayout);
        itemLayoutRequest.add(rowLayout);
        itemLayoutRequest.add(groupFieldRowLayout);

        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenMoveSubTableFieldOut() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenMoveSubTableFieldOutCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        ArrayList<FieldLayout> fieldsRowLayout1 = new ArrayList<FieldLayout>();
        ArrayList<FieldLayout> fieldsRowLayout2 = new ArrayList<FieldLayout>();
        RowLayout rowLayout1 = new RowLayout();

        FieldLayout singleFieldRowLayout1 = new FieldLayout();
        singleFieldRowLayout1.setCode("Text1");
        singleFieldRowLayout1.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout1.add(singleFieldRowLayout1);
        rowLayout1.setFields(fieldsRowLayout1);

        FieldLayout singleFieldRowLayout2 = new FieldLayout();
        singleFieldRowLayout2.setCode("Text2");
        singleFieldRowLayout2.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout2.add(singleFieldRowLayout2);

        FieldLayout numberFieldRowLayout = new FieldLayout();
        numberFieldRowLayout.setCode("数値");
        numberFieldRowLayout.setType(FieldType.NUMBER.toString());
        fieldsRowLayout2.add(numberFieldRowLayout);

        SubTableLayout subTableFieldRowLayout = new SubTableLayout();
        subTableFieldRowLayout.setCode("Table");
        subTableFieldRowLayout.setFields(fieldsRowLayout2);

        itemLayoutRequest.add(rowLayout1);
        itemLayoutRequest.add(subTableFieldRowLayout);
        this.certAppManagerment.updateFormLayout(1797, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdUnexisted() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(99999, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdUnexistedCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(99999, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdNegative() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(-1, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdNegativeCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(-1, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdZero() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(0, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenAppIdZeroCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(0, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutAppId() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(null, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWithoutAppIdCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(null, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionUnexisted() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionUnexistedCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionZero() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionZeroCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(1797, itemLayoutRequest, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHasNoPermission() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(1759, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenHasNoPermissionCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(1759, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenUseTokenAPI() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.updateformlayoutTokenAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    // Execute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutGuestSpaceShouldFailWhenGuestSpaceFunctionOff() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.guestSpaceAppManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID, itemLayoutRequest, null);
    }

    // Execute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutGuestSpaceShouldFailWhenGuestSpaceFunctionOffCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.certGuestAppManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID, itemLayoutRequest, null);
    }

    // Execute When Space Function Off
    // @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceFuntionOff() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.appManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    // Execute When Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutShouldFailWhenSpaceFuntionOffCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        RowLayout rowLayout = new RowLayout();
        ArrayList<FieldLayout> fieldsRowLayout = new ArrayList<FieldLayout>();

        FieldLayout singleFieldRowLayout = new FieldLayout();
        singleFieldRowLayout.setCode("Singline_Text");
        singleFieldRowLayout.setType(FieldType.SINGLE_LINE_TEXT.toString());
        fieldsRowLayout.add(singleFieldRowLayout);

        FieldLayout radioButtonFieldRowLayout = new FieldLayout();
        radioButtonFieldRowLayout.setCode("radioFieldCode");
        radioButtonFieldRowLayout.setType(FieldType.RADIO_BUTTON.toString());
        fieldsRowLayout.add(radioButtonFieldRowLayout);

        rowLayout.setFields(fieldsRowLayout);
        itemLayoutRequest.add(rowLayout);
        this.certAppManagerment.updateFormLayout(FORMLAYOUT_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutGuestSpaceShouldFailWhenLinkIsNotGuestSpace() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.appManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID, itemLayoutRequest, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateRowLayoutGuestSpaceShouldFailWhenLinkIsNotGuestSpaceCert() throws KintoneAPIException {
        ArrayList<ItemLayout> itemLayoutRequest = new ArrayList<ItemLayout>();
        this.certAppManagerment.updateFormLayout(FORM_LAYOUT_GUEST_SPACE_APP_ID, itemLayoutRequest, null);
    }

    @Test
    public void testAddPreviewAppShouldSuccess() throws KintoneAPIException {
        AddPreviewAppResponse appPreviewResponse = this.appManagerment.addPreviewApp("AddPreviewApp_Test", null, null);
        assertNotNull(appPreviewResponse.getApp());
        assertNotNull(appPreviewResponse.getRevision());
    }

    @Test
    public void testAddPreviewAppInSpaceShouldSuccess() throws KintoneAPIException {
        AddPreviewAppResponse appPreviewResponse = this.appManagerment.addPreviewApp("AddPreviewApp_Test",
                TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
        assertNotNull(appPreviewResponse.getApp());
        assertNotNull(appPreviewResponse.getRevision());
    }

    @Test
    public void testAddPreviewAppInSpaceShouldSuccessCert() throws KintoneAPIException {
        AddPreviewAppResponse appPreviewResponse = this.certAppManagerment.addPreviewApp("AddPreviewApp_Test",
                TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
        assertNotNull(appPreviewResponse.getApp());
        assertNotNull(appPreviewResponse.getRevision());
    }

    @Test
    public void testAddPreviewAppInGuestSpaceShouldSuccess() throws KintoneAPIException {
        AddPreviewAppResponse appPreviewResponse = this.guestSpaceAppManagerment.addPreviewApp("AddPreviewApp_Test",
                TestConstantsSample.GUEST_SPACE_ID, TestConstantsSample.GUEST_SPACE_THREAD_ID);
        assertNotNull(appPreviewResponse.getApp());
        assertNotNull(appPreviewResponse.getRevision());
    }

    @Test
    public void testAddPreviewAppInGuestSpaceShouldSuccessCert() throws KintoneAPIException {
        AddPreviewAppResponse appPreviewResponse = this.certGuestAppManagerment.addPreviewApp("AddPreviewApp_Test",
                TestConstantsSample.GUEST_SPACE_ID, TestConstantsSample.GUEST_SPACE_THREAD_ID);
        assertNotNull(appPreviewResponse.getApp());
        assertNotNull(appPreviewResponse.getRevision());
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNoName() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("", TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNoNameCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("", TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNameNull() throws KintoneAPIException {
        this.appManagerment.addPreviewApp(null, TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNameNullCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp(null, TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNameOverflow() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklm",
                TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenNameOverflowCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklm",
                TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenSpaceNull() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", null, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenSpaceNullCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", null, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenSpaceZero() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", 0, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenSpaceZeroCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", 0, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenUnexistedSpace() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", 99999, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenUnexistedSpaceCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", 99999, TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadNull() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadNullCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadZero() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadZeroCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 0);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadUnexisted() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 9999);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadUnexistedCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 9999);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadNotBelongToSpace() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 130);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenThreadNotBelongToSpaceCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, 130);
    }

    // Excute When Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppInSpaceShouldFailWhenSpaceFunctionOff() throws KintoneAPIException {
        this.appManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID, TestConstantsSample.SPACE_THREAD_ID);
    }

    // Excute When Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppInSpaceShouldFailWhenSpaceFunctionOffCert() throws KintoneAPIException {
        this.certAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID,
                TestConstantsSample.SPACE_THREAD_ID);
    }

    // Excute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppInGuestSpaceShouldFailWhenGuestSpaceFunctionOff() throws KintoneAPIException {
        this.guestSpaceAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.GUEST_SPACE_ID,
                TestConstantsSample.GUEST_SPACE_THREAD_ID);
    }

    // Excute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppInGuestSpaceShouldFailWhenGuestSpaceFunctionOffCert() throws KintoneAPIException {
        this.certGuestAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.GUEST_SPACE_ID,
                TestConstantsSample.GUEST_SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenHasNoPermisssionForCreateApp() throws KintoneAPIException {
        this.noAdminAppManagerment.addPreviewApp("AddPreviewApp_Test", null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenHasNoPermisssionForCreateAppInSpace() throws KintoneAPIException {
        this.noAdminAppManagerment.addPreviewApp("AddPreviewApp_Test", TestConstantsSample.SPACE_ID,
                TestConstantsSample.SPACE_THREAD_ID);
    }

    @Test(expected = KintoneAPIException.class)
    public void testAddPreviewAppShouldFailWhenHasNoPermisssionOfSpace() throws KintoneAPIException {
        this.noAddNoReadAppManagerment.addPreviewApp("AddPreviewApp_Test", 135, 156);
    }

    // KCB-656
    @Test
    public void testDeployAppSettingsShouldSuccess() throws KintoneAPIException, InterruptedException {
        FormFields responseBefore = this.appManagerment.getFormFields(DEPLOY_APP_ID, null, false);
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Text";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setDefaultValue("123");

        properties.put(fieldCode, sltf);
        this.appManagerment.updateFormFields(DEPLOY_APP_ID, properties, null);

        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(DEPLOY_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);

        Thread.sleep(3000);

        FormFields responseAfter = this.appManagerment.getFormFields(DEPLOY_APP_ID, null, false);
        assertEquals(Integer.valueOf(responseBefore.getRevision() + 1), responseAfter.getRevision());
    }

    // KCB-656
    @Test
    public void testDeployAppSettingsShouldSuccessCert() throws KintoneAPIException, InterruptedException {
        FormFields responseBefore = this.appManagerment.getFormFields(DEPLOY_APP_ID, null, false);
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Text";

        SingleLineTextField sltf = new SingleLineTextField(fieldCode);
        sltf.setDefaultValue("123");

        properties.put(fieldCode, sltf);
        this.certAppManagerment.updateFormFields(DEPLOY_APP_ID, properties, null);

        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(DEPLOY_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);

        Thread.sleep(3000);

        FormFields responseAfter = this.certAppManagerment.getFormFields(DEPLOY_APP_ID, null, false);
        assertEquals(Integer.valueOf(responseBefore.getRevision() + 1), responseAfter.getRevision());
    }

    @Test
    public void testDeployAppSettingsShouldSuccessWhenRevertIsTrue() throws KintoneAPIException, InterruptedException {
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Text101";

        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(DEPLOY_APP_ID);
        previewAppRequest.setRevision(-1);

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.appManagerment.addFormFields(DEPLOY_APP_ID, properties, null);

        ArrayList<PreviewAppRequest> deployApps2 = new ArrayList<PreviewAppRequest>();
        deployApps2.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps2, true);

        Thread.sleep(3000);

        FormFields getResponse = this.appManagerment.getFormFields(DEPLOY_APP_ID, null, true);
        assertFalse(getResponse.getProperties().containsKey("Text101"));
    }

    @Test
    public void testDeployAppSettingsShouldSuccessWhenRevertIsTrueCert()
            throws KintoneAPIException, InterruptedException {
        HashMap<String, Field> properties = new HashMap<>();
        String fieldCode = "Text101";

        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(DEPLOY_APP_ID);
        previewAppRequest.setRevision(-1);

        SingleLineTextField createSingleLineTextField = createSingleLineTextField(fieldCode);
        properties.put(fieldCode, createSingleLineTextField);
        this.certAppManagerment.addFormFields(DEPLOY_APP_ID, properties, null);

        ArrayList<PreviewAppRequest> deployApps2 = new ArrayList<PreviewAppRequest>();
        deployApps2.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps2, true);

        Thread.sleep(3000);

        FormFields getResponse = this.certAppManagerment.getFormFields(DEPLOY_APP_ID, null, true);
        assertFalse(getResponse.getProperties().containsKey("Text101"));
    }

    // KINTONE-14003
    @Test
    public void testDeployAppSettingsShouldSuccessWhenAppListBlank() throws KintoneAPIException {
        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    // KINTONE-14003
    @Test
    public void testDeployAppSettingsShouldSuccessWhenAppListBlankCert() throws KintoneAPIException {
        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppsNull() throws KintoneAPIException {
        this.appManagerment.deployAppSettings(null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppsNullCert() throws KintoneAPIException {
        this.certAppManagerment.deployAppSettings(null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldSuccessWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-2);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldSuccessWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-2);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppNull() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(null);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppNullCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(null);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenUseTokenAPI() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.addFormFieldTokenAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenHasNopermission() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(NO_PERMISSION_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenHasNopermissionCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(NO_PERMISSION_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppDuplicate() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        PreviewAppRequest previewAppRequest1 = new PreviewAppRequest();
        previewAppRequest1.setApp(APP_ID);
        previewAppRequest1.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        deployApps.add(previewAppRequest1);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppDuplicateCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        PreviewAppRequest previewAppRequest1 = new PreviewAppRequest();
        previewAppRequest1.setApp(APP_ID);
        previewAppRequest1.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        deployApps.add(previewAppRequest1);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppZero() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(0);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppZeroCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(0);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppUnexisted() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(99999);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppUnexistedCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(99999);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppOverflow() throws KintoneAPIException {
        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        for (int i = 0; i <= 300; i++) {
            PreviewAppRequest previewAppRequest = new PreviewAppRequest();
            previewAppRequest.setApp(i + 1);
            previewAppRequest.setRevision(-1);

            deployApps.add(previewAppRequest);
        }
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppOverflowCert() throws KintoneAPIException {
        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        for (int i = 0; i <= 300; i++) {
            PreviewAppRequest previewAppRequest = new PreviewAppRequest();
            previewAppRequest.setApp(i + 1);
            previewAppRequest.setRevision(-1);

            deployApps.add(previewAppRequest);
        }
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenLinkIsGuestSpace() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.guestSpaceAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenLinkIsGuestSpaceCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certGuestAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAppIsGuestSpace() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(TestConstantsSample.GUEST_SPACE_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test
    public void testDeployAppSettingsShouldFailWhenAppIsGuestSpaceCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(TestConstantsSample.GUEST_SPACE_APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certGuestAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldSuccessWhenRevisionUnexisted() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(99999);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldSuccessWhenRevisionUnexistedCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(99999);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAlreadyDeployed() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.appManagerment.deployAppSettings(deployApps, false);
        this.appManagerment.deployAppSettings(deployApps, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeployAppSettingsShouldFailWhenAlreadyDeployedCert() throws KintoneAPIException {
        PreviewAppRequest previewAppRequest = new PreviewAppRequest();
        previewAppRequest.setApp(APP_ID);
        previewAppRequest.setRevision(-1);

        ArrayList<PreviewAppRequest> deployApps = new ArrayList<PreviewAppRequest>();
        deployApps.add(previewAppRequest);
        this.certAppManagerment.deployAppSettings(deployApps, false);
        this.certAppManagerment.deployAppSettings(deployApps, false);
    }

    @Test
    public void testGetAppDeployStatusShouldSuccess() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);
        appIds.add(APP_CONTAINS_NORMAL_LAYOUT_ID);

        GetAppDeployStatusResponse appDeployStatusResponse = new GetAppDeployStatusResponse();
        appDeployStatusResponse = this.appManagerment.getAppDeployStatus(appIds);
        assertEquals(2, appDeployStatusResponse.getApps().size());
        assertEquals(APP_ID, appDeployStatusResponse.getApps().get(0).getApp().intValue());
        assertEquals("SUCCESS", appDeployStatusResponse.getApps().get(1).getStatus().toString());
    }

    @Test
    public void testGetAppDeployStatusShouldSuccessCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);
        appIds.add(APP_CONTAINS_NORMAL_LAYOUT_ID);

        GetAppDeployStatusResponse appDeployStatusResponse = new GetAppDeployStatusResponse();
        appDeployStatusResponse = this.certAppManagerment.getAppDeployStatus(appIds);
        assertEquals(2, appDeployStatusResponse.getApps().size());
        assertEquals(APP_ID, appDeployStatusResponse.getApps().get(0).getApp().intValue());
        assertEquals("SUCCESS", appDeployStatusResponse.getApps().get(1).getStatus().toString());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenUseTokenAPI() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);
        this.viewTokenAppManagerment.getAppDeployStatus(appIds);

    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppDuplicate() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);
        appIds.add(APP_ID);
        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppDuplicateCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);
        appIds.add(APP_ID);
        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppOverFlow() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        for (int i = 0; i < 301; i++) {
            appIds.add(i + 1);
        }
        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppOverFlowCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        for (int i = 0; i < 301; i++) {
            appIds.add(i + 1);
        }
        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppZero() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(0);
        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppZeroCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(0);
        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppUnexisted() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(99999);
        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppUnexistedCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(99999);
        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppNull() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(null);
        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppNullCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(null);
        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWithoutApps() throws KintoneAPIException {
        this.appManagerment.getAppDeployStatus(null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWithoutAppsCert() throws KintoneAPIException {
        this.certAppManagerment.getAppDeployStatus(null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenHasNoPermission() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(NO_PERMISSION_APP_ID);

        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenHasNoPermissionCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(NO_PERMISSION_APP_ID);

        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenLinkIsGuestSpace() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);

        this.guestSpaceAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenLinkIsGuestSpaceCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(APP_ID);

        this.certGuestAppManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppIsGuestSpace() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);

        this.appManagerment.getAppDeployStatus(appIds);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetAppDeployStatusShouldFailWhenAppIsGuestSpaceCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);

        this.certAppManagerment.getAppDeployStatus(appIds);
    }

    @Test
    public void testGetAppDeployStatusShouldSuccessWhenGuestSpace() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);

        GetAppDeployStatusResponse appDeployStatus = this.guestSpaceAppManagerment.getAppDeployStatus(appIds);
        assertEquals(TestConstantsSample.GUEST_SPACE_APP_ID, appDeployStatus.getApps().get(0).getApp().intValue());
        assertEquals("SUCCESS", appDeployStatus.getApps().get(0).getStatus().toString());
    }

    @Test
    public void testGetAppDeployStatusShouldSuccessWhenGuestSpaceCert() throws KintoneAPIException {
        ArrayList<Integer> appIds = new ArrayList<Integer>();
        appIds.add(TestConstantsSample.GUEST_SPACE_APP_ID);

        GetAppDeployStatusResponse appDeployStatus = this.certGuestAppManagerment.getAppDeployStatus(appIds);
        assertEquals(TestConstantsSample.GUEST_SPACE_APP_ID, appDeployStatus.getApps().get(0).getApp().intValue());
        assertEquals("SUCCESS", appDeployStatus.getApps().get(0).getStatus().toString());
    }

    @Test
    public void testGetViewShouldSuccess() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "AList View";
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.appManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);

        assertEquals(name, response.getViews().get(name).getName().toString());
        assertEquals("[レコード番号]", response.getViews().get(name).getFields().toString());
    }

    @Test
    public void testGetViewShouldSuccessCert() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "AList View";
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.certAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);

        assertEquals(name, response.getViews().get(name).getName().toString());
        assertEquals("[レコード番号]", response.getViews().get(name).getFields().toString());
    }

    @Test
    public void testGetViewShouldSuccessWhenAppNeverChangeSettingsInPreview() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "AList View";
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(1805, views, null);

        GetViewsResponse response = this.appManagerment.getViews(1805, null, true);
        assertEquals(name, response.getViews().get(name).getName().toString());
    }

    @Test
    public void testGetViewShouldSuccessWhenAppNeverChangeSettingsInPreviewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "AList View";
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(1805, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(1805, null, true);
        assertEquals(name, response.getViews().get(name).getName().toString());
    }

    @Test
    public void testGetViewShouldSuccessWhenNotSaveChanges() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "Be View";
        ViewModel properties = new ViewModel();

        properties.setType(ViewType.CUSTOM);
        properties.setIndex(1);
        properties.setName(name);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertNull(response.getViews().get(name));
    }

    @Test
    public void testGetViewShouldSuccessWhenNotSaveChangesCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "Be View";
        ViewModel properties = new ViewModel();

        properties.setType(ViewType.CUSTOM);
        properties.setIndex(1);
        properties.setName(name);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertNull(response.getViews().get(name));
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewInPreview() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewInPreviewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewInPreviewPagerFalse() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(false);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(false, response.getViews().get(name).getPager());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewInPreviewPagerFalseCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(false);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(false, response.getViews().get(name).getPager());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeView() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.appManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewCert() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.certAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewPagerFalse() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(false);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.appManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(false, response.getViews().get(name).getPager());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithCustomizeViewPagerFalseCert()
            throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(false);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.certAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(false, response.getViews().get(name).getPager());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithClandarViewInPreview() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACalendar View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithClandarViewInPreviewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACalendar View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithClandarView() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCalendar View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.appManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(ViewType.CALENDAR, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithClandarViewCert() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCalendar View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(VIEW_SETTING_APP_ID);
        apps.add(app);
        this.certAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        assertEquals(ViewType.CALENDAR, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWithListViewNoAllViewInPreview() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "AList View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
        assertFalse(response.getViews().containsKey("（すべて）"));
    }

    @Test
    public void testGetViewShouldSuccessWithListViewNoAllViewInPreviewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "AList View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse response = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
        assertFalse(response.getViews().containsKey("（すべて）"));
    }

    @Test
    public void testGetViewShouldSuccessWhenGuestSpaceInPreview() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.guestSpaceAppManagerment.updateViews(1804, views, null);

        GetViewsResponse response = this.guestSpaceAppManagerment.getViews(1804, null, true);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWhenGuestSpaceInPreviewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "BCustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.certGuestAppManagerment.updateViews(1804, views, null);

        GetViewsResponse response = this.certGuestAppManagerment.getViews(1804, null, true);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWhenGuestSpace() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.guestSpaceAppManagerment.updateViews(1804, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(1804);
        apps.add(app);
        this.guestSpaceAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.guestSpaceAppManagerment.getViews(1804, null, false);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWhenGuestSpaceCert() throws KintoneAPIException, InterruptedException {
        HashMap<String, ViewModel> views = new HashMap<>();
        String name = "ACustomize View";
        ViewModel properties = new ViewModel();

        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.certGuestAppManagerment.updateViews(1804, views, null);

        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();

        app.setApp(1804);
        apps.add(app);
        this.certGuestAppManagerment.deployAppSettings(apps, false);

        Thread.sleep(3000);

        GetViewsResponse response = this.certGuestAppManagerment.getViews(1804, null, false);
        assertEquals(ViewType.CUSTOM, response.getViews().get(name).getType());
        assertEquals(name, response.getViews().get(name).getName());
    }

    @Test
    public void testGetViewShouldSuccessWhenHasOnlyAddPermission() throws KintoneAPIException {
        this.addOnlyAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
    }

    @Test
    public void testGetViewShouldSuccessWhenHasOnlyReadPermission() throws KintoneAPIException {
        this.readOnlyAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenHasOnlyAddPermissionInPreview() throws KintoneAPIException {
        this.addOnlyAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenHasOnlyReadPermissionInPreview() throws KintoneAPIException {
        this.readOnlyAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenHasNoAdminPermissionInPreview() throws KintoneAPIException {
        this.noAdminAppManagerment.getViews(APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenHasNoAddNoReadPermission() throws KintoneAPIException {
        this.noAddNoReadAppManagerment.getViews(APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppPrivate() throws KintoneAPIException {
        this.appManagerment.getViews(1777, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppPrivateCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(1777, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppNull() throws KintoneAPIException {
        this.appManagerment.getViews(null, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppNullCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(null, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppNullInPreview() throws KintoneAPIException {
        this.appManagerment.getViews(null, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppNullInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(null, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppZeroInPreview() throws KintoneAPIException {
        this.appManagerment.getViews(0, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppZeroInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(0, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppZero() throws KintoneAPIException {
        this.appManagerment.getViews(0, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(0, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        this.appManagerment.getViews(-1, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenGiveInvalidAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(-1, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppUnexistedInPreview() throws KintoneAPIException {
        this.appManagerment.getViews(99999, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppUnexistedInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(99999, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppUnexisted() throws KintoneAPIException {
        this.appManagerment.getViews(99999, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewhouldFailWhenAppUnexistedCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(99999, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppInGuestSpaceInPreview() throws KintoneAPIException {
        this.appManagerment.getViews(TestConstantsSample.GUEST_SPACE_APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppInGuestSpaceInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(TestConstantsSample.GUEST_SPACE_APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppInGuestSpace() throws KintoneAPIException {
        this.appManagerment.getViews(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWhenAppInGuestSpaceCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWithDuplicateViewName() throws KintoneAPIException {
        this.appManagerment.getViews(1778, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWithDuplicateViewNameCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(1778, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWithDuplicateViewNameInPreview() throws KintoneAPIException {
        this.appManagerment.getViews(1778, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWithDuplicateViewNameInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getViews(1778, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetViewShouldFailWithTokenAPI() throws KintoneAPIException {
        this.viewTokenAppManagerment.getViews(APP_ID, null, false);
    }

    @Test
    public void testUpdateViewShouldSuccessWhenAddWithoutDevice() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setIndex(1);
        properties.setName(name);
        properties.setType(ViewType.CUSTOM);
        properties.setHtml("");
        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, -1);
        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(name, viewResponse.getViews().get(name).getName());
        assertEquals(ViewType.CUSTOM, viewResponse.getViews().get(name).getType());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenAddWithoutDeviceCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setIndex(1);
        properties.setName(name);
        properties.setType(ViewType.CUSTOM);
        properties.setHtml("");
        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(name, viewResponse.getViews().get(name).getName());
        assertEquals(ViewType.CUSTOM, viewResponse.getViews().get(name).getType());
    }

    // KCB-640
    @Test
    public void testUpdateViewShouldSuccessWhenRevisionNull() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(APP_ID, null, false);
        UpdateViewsResponse updateView = this.appManagerment.updateViews(APP_ID, views.getViews(), null);
        assertNotNull(updateView);
    }

    // KCB-640
    @Test
    public void testUpdateViewShouldSuccessWhenRevisionNullCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(APP_ID, null, false);
        System.out.println(views.getViews());
        UpdateViewsResponse updateView = this.certAppManagerment.updateViews(APP_ID, views.getViews(), null);
        assertNotNull(updateView);
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetStatusAsSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setIndex(1);
        properties.setName(name);
        properties.setType(ViewType.LIST);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        properties.setFields(fields);

        String filterCond = "関連レコード一覧.ステータス in (\"未処理\", \"処理中\")";
        properties.setFilterCond(filterCond);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(name, viewResponse.getViews().get(name).getName());
        assertEquals(filterCond, viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetStatusAsSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setIndex(1);
        properties.setName(name);
        properties.setType(ViewType.LIST);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        properties.setFields(fields);

        String filterCond = "関連レコード一覧.ステータス in (\"未処理\", \"処理中\")";
        properties.setFilterCond(filterCond);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(name, viewResponse.getViews().get(name).getName());
        assertEquals(filterCond, viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetEWeekFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties1 = new ViewModel();
        ViewModel properties2 = new ViewModel();
        ViewModel properties3 = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name1 = "List View";
        properties1.setIndex(1);
        properties1.setName(name1);
        properties1.setType(ViewType.LIST);
        properties1.setFields(fields);
        properties1.setFilterCond("日付 > THIS_WEEK()");
        properties1.setSort("レコード番号 asc");

        properties1.setFields(fields);

        views.put(name1, properties1);

        String name2 = "Calendar View";
        properties2.setName(name2);
        properties2.setType(ViewType.CALENDAR);
        properties2.setName(name2);
        properties2.setDate("作成日時");
        properties2.setTitle("aaa");
        properties2.setFilterCond("更新日時 = THIS_WEEK(SATURDAY)");
        properties2.setSort("レコード番号 asc");
        properties2.setIndex(2);

        views.put(name2, properties2);

        String name3 = "Customize View";
        properties3.setName(name3);
        properties3.setType(ViewType.CUSTOM);
        properties3.setHtml("カスタマイズされた一覧のHTML更新");
        properties3.setFilterCond("日時 <= LAST_WEEK()");
        properties3.setSort("レコード番号 asc");
        properties3.setIndex(4);

        views.put(name3, properties3);

        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals("日付 > THIS_WEEK()", viewResponse.getViews().get(name1).getFilterCond());
        assertEquals("更新日時 = THIS_WEEK(SATURDAY)", viewResponse.getViews().get(name2).getFilterCond());
        assertEquals("日時 <= LAST_WEEK()", viewResponse.getViews().get(name3).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetEWeekFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties1 = new ViewModel();
        ViewModel properties2 = new ViewModel();
        ViewModel properties3 = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name1 = "List View";
        properties1.setIndex(1);
        properties1.setName(name1);
        properties1.setType(ViewType.LIST);
        properties1.setFields(fields);
        properties1.setFilterCond("日付 > THIS_WEEK()");
        properties1.setSort("レコード番号 asc");

        properties1.setFields(fields);

        views.put(name1, properties1);

        String name2 = "Calendar View";
        properties2.setName(name2);
        properties2.setType(ViewType.CALENDAR);
        properties2.setName(name2);
        properties2.setDate("作成日時");
        properties2.setTitle("aaa");
        properties2.setFilterCond("更新日時 = THIS_WEEK(SATURDAY)");
        properties2.setSort("レコード番号 asc");
        properties2.setIndex(-1);

        views.put(name2, properties2);

        String name3 = "Customize View";
        properties3.setName(name3);
        properties3.setType(ViewType.CUSTOM);
        properties3.setHtml("カスタマイズされた一覧のHTML更新");
        properties3.setFilterCond("日時 <= LAST_WEEK()");
        properties3.setSort("レコード番号 asc");
        properties3.setIndex(4);

        views.put(name3, properties3);

        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals("日付 > THIS_WEEK()", viewResponse.getViews().get(name1).getFilterCond());
        assertEquals("更新日時 = THIS_WEEK(SATURDAY)", viewResponse.getViews().get(name2).getFilterCond());
        assertEquals("日時 <= LAST_WEEK()", viewResponse.getViews().get(name3).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetEFormTodayFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties1 = new ViewModel();
        ViewModel properties2 = new ViewModel();
        ViewModel properties3 = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name1 = "List View";
        properties1.setIndex(1);
        properties1.setName(name1);
        properties1.setType(ViewType.LIST);
        properties1.setFields(fields);
        properties1.setFilterCond("日付 > FROM_TODAY(-3, DAYS)");
        properties1.setSort("レコード番号 asc");

        properties1.setFields(fields);

        views.put(name1, properties1);

        String name2 = "Calendar View";
        properties2.setName(name2);
        properties2.setType(ViewType.CALENDAR);
        properties2.setName(name2);
        properties2.setDate("作成日時");
        properties2.setTitle("aaa");
        properties2.setFilterCond("日時 = FROM_TODAY(2, WEEKS)");
        properties2.setSort("レコード番号 asc");
        properties2.setIndex(2);

        views.put(name2, properties2);

        String name3 = "Customize View";
        properties3.setName(name3);
        properties3.setType(ViewType.CUSTOM);
        properties3.setHtml("カスタマイズされた一覧のHTML更新");
        properties3.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties3.setSort("レコード番号 asc");
        properties3.setIndex(3);

        views.put(name3, properties3);

        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals("日付 > FROM_TODAY(-3, DAYS)", viewResponse.getViews().get(name1).getFilterCond());
        assertEquals("日時 = FROM_TODAY(2, WEEKS)", viewResponse.getViews().get(name2).getFilterCond());
        assertEquals("日時 <= FROM_TODAY(1, YEARS)", viewResponse.getViews().get(name3).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetEFormTodayFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties1 = new ViewModel();
        ViewModel properties2 = new ViewModel();
        ViewModel properties3 = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name1 = "List View";
        properties1.setIndex(1);
        properties1.setName(name1);
        properties1.setType(ViewType.LIST);
        properties1.setFields(fields);
        properties1.setFilterCond("日付 > FROM_TODAY(-3, DAYS)");
        properties1.setSort("レコード番号 asc");

        properties1.setFields(fields);

        views.put(name1, properties1);

        String name2 = "Calendar View";
        properties2.setName(name2);
        properties2.setType(ViewType.CALENDAR);
        properties2.setName(name2);
        properties2.setDate("作成日時");
        properties2.setTitle("aaa");
        properties2.setFilterCond("日時 = FROM_TODAY(2, WEEKS)");
        properties2.setSort("レコード番号 asc");
        properties2.setIndex(2);

        views.put(name2, properties2);

        String name3 = "Customize View";
        properties3.setName(name3);
        properties3.setType(ViewType.CUSTOM);
        properties3.setHtml("カスタマイズされた一覧のHTML更新");
        properties3.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties3.setSort("レコード番号 asc");
        properties3.setIndex(3);

        views.put(name3, properties3);

        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals("日付 > FROM_TODAY(-3, DAYS)", viewResponse.getViews().get(name1).getFilterCond());
        assertEquals("日時 = FROM_TODAY(2, WEEKS)", viewResponse.getViews().get(name2).getFilterCond());
        assertEquals("日時 <= FROM_TODAY(1, YEARS)", viewResponse.getViews().get(name3).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessCalendarViewWhenTitleIsGroupSelect() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "New List View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("グループ選択");
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
        assertEquals("グループ選択", viewResponse.getViews().get(newName).getTitle());
    }

    @Test
    public void testUpdateViewShouldSuccessCalendarViewWhenTitleIsGroupSelectCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "New List View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("グループ選択");
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
        assertEquals("グループ選択", viewResponse.getViews().get(newName).getTitle());
    }

    @Test
    public void testUpdateViewShouldSuccessCalendarViewWhenTitleIsOrgSelect() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "New List View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("組織選択");
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
        assertEquals("組織選択", viewResponse.getViews().get(newName).getTitle());
    }

    @Test
    public void testUpdateViewShouldSuccessCalendarViewWhenTitleIsOrgSelectCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日時 <= FROM_TODAY(1, YEARS)");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "New List View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("組織選択");
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
        assertEquals("組織選択", viewResponse.getViews().get(newName).getTitle());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSet$IdAsFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("$id >= 10");
        properties.setSort("$id asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("レコード番号 >= 10", viewResponse.getViews().get(name).getFilterCond());
        assertEquals("レコード番号 asc", viewResponse.getViews().get(name).getSort());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSet$IdAsFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("$id >= 10");
        properties.setSort("$id asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("レコード番号 >= 10", viewResponse.getViews().get(name).getFilterCond());
        assertEquals("レコード番号 asc", viewResponse.getViews().get(name).getSort());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetConditionNotNowFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日付 = TODAY() and 日付 = LAST_MONTH() and 日付 = THIS_MONTH() and 日付 = THIS_YEAR()");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("日付 = TODAY() and 日付 = LAST_MONTH() and 日付 = THIS_MONTH() and 日付 = THIS_YEAR()",
                viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetConditionNotNowFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("日付 = TODAY() and 日付 = LAST_MONTH() and 日付 = THIS_MONTH() and 日付 = THIS_YEAR()");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("日付 = TODAY() and 日付 = LAST_MONTH() and 日付 = THIS_MONTH() and 日付 = THIS_YEAR()",
                viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetRelatedRecordsInFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧1.文字列__1行_ in (\"aaa\")");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("関連レコード一覧1.文字列__1行_ in (\"aaa\")", viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenSetRelatedRecordsInFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧1.文字列__1行_ in (\"aaa\")");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(name).getType());
        assertEquals("関連レコード一覧1.文字列__1行_ in (\"aaa\")", viewResponse.getViews().get(name).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenChangeToListWithoutFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("日付");
        properties.setTitle("aaa");
        properties.setFilterCond("");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "List View";
        properties.setName(newName);
        properties.setType(ViewType.LIST);
        properties.setFilterCond("aaa = \"aaa\"");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(newName).getType());
        assertEquals("aaa = \"aaa\"", viewResponse.getViews().get(newName).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenChangeToListWithoutFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("日付");
        properties.setTitle("aaa");
        properties.setFilterCond("");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "List View";
        properties.setName(newName);
        properties.setType(ViewType.LIST);
        properties.setFilterCond("aaa = \"aaa\"");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.LIST, viewResponse.getViews().get(newName).getType());
        assertEquals("aaa = \"aaa\"", viewResponse.getViews().get(newName).getFilterCond());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenChangeToCalendarWithoutDateTitle() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Calendar View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
    }

    @Test
    public void testUpdateViewShouldSuccessWhenChangeToCalendarWithoutDateTitleCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");
        fields.add("aaa");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 desc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Calendar View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);
        properties.setFields(fields);
        properties.setFilterCond("");
        properties.setSort("レコード番号 asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        GetViewsResponse viewResponse = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, true);
        assertEquals(ViewType.CALENDAR, viewResponse.getViews().get(newName).getType());
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetSubTableAsSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("数値_0");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetSubTableAsSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("数値_0");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordAsSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("関連レコード一覧1.文字列__1行_");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordAsSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("関連レコード一覧1.文字列__1行_");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupAsSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("グループ");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupAsSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setSort("グループ");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetWrongFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧1.レコード番号 in (1780)");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetWrongFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧1.レコード番号 in (1780)");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("aaa");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);
        properties.setFilterCond("aaa");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetSubTableToFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("数値_0");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetSubTableToFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("数値_0");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAddListViewWithoutFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View1";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAddListViewWithoutFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View1";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenKeyNameDiffWithName() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "Diff List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name + 1, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenKeyNameDiffWithNameCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "Diff List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name + 1, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAddViewWithoutName() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAddViewWithoutNameCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordToFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("関連レコード一覧");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordToFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("関連レコード一覧");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetCategoryToFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("カテゴリー");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetCategoryToFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("カテゴリー");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("ステータス");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("ステータス");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeCustomizeViewToCalendar() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);
        properties.setHtml("<b>test</b>");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Calendar View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeCustomizeViewToCalendarCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CUSTOM);
        properties.setPager(true);
        properties.setHtml("<b>test</b>");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Calendar View";
        properties.setName(newName);
        properties.setType(ViewType.CALENDAR);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeListViewToCustomize() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Customize View";
        properties.setName(newName);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeListViewToCustomizeCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);

        String newName = "Customize View";
        properties.setName(newName);
        properties.setType(ViewType.CUSTOM);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeFilterCondWithAssignee() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "（作業者が自分）";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setBuiltinType(BuiltinType.ASSIGNEE);
        properties.setFilterCond("更新日時 > \"2012-02-03T09:00:00Z\" and 更新日時 < \"2012-02-03T10:00:00Z\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(1779, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenChangeFilterCondWithAssigneeCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "（作業者が自分）";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setBuiltinType(BuiltinType.ASSIGNEE);
        properties.setFilterCond("更新日時 > \"2012-02-03T09:00:00Z\" and 更新日時 < \"2012-02-03T10:00:00Z\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(1779, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDeleteInsideView() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "作業者が自分";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setBuiltinType(BuiltinType.ASSIGNEE);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(1779, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDeleteInsideViewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "作業者が自分";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setBuiltinType(BuiltinType.ASSIGNEE);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(1779, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenIndexDuplicate() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);

        String newName = "Customize View";
        properties.setName(newName);
        properties.setType(ViewType.CUSTOM);
        properties.setIndex(1);

        views.put(newName, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenIndexDuplicateCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);

        String newName = "Customize View";
        properties.setName(newName);
        properties.setType(ViewType.CUSTOM);
        properties.setIndex(1);

        views.put(newName, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutIndex() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutIndexCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenHtmlOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setName(name);
        properties.setType(ViewType.CUSTOM);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10001; i++) {
            sb.append("a");
        }
        properties.setHtml(sb.toString());
        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenHtmlOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setName(name);
        properties.setType(ViewType.CUSTOM);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10001; i++) {
            sb.append("a");
        }
        properties.setHtml(sb.toString());
        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidTitle() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("ドロップダウン");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidTitleCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("ドロップダウン");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenTitleOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }

        properties.setTitle(sb.toString());
        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenTitleOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }

        properties.setTitle(sb.toString());
        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenTitleZero() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenTitleZeroCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setTitle("");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidDate() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("aaa");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidDateCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("aaa");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDateOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }

        properties.setDate(sb.toString());
        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDateOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }

        properties.setDate(sb.toString());
        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDateZero() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDateZeroCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Calendar View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.CALENDAR);
        properties.setDate("");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSortOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setSort("$id asc,$id asc,$id asc,$id asc,$id asc,$id asc");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSortOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setSort("$id asc,$id asc,$id asc,$id asc,$id asc,$id asc");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupToFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("グループ");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupToFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("グループ");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsZero() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsZeroCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }
        fields.add(sb.toString());

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 129; i++) {
            sb.append("a");
        }
        fields.add(sb.toString());

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsDuplicate() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        fields.add("レコード番号");
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsDuplicateCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        fields.add("レコード番号");
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsBlank() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenFieldsBlankCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNameOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 65; i++) {
            sb.append("a");
        }

        String name = sb.toString();
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNameOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 65; i++) {
            sb.append("a");
        }

        String name = sb.toString();
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNameZero() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNameZeroCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "";
        properties.setName(name);
        properties.setIndex(1);
        properties.setType(ViewType.LIST);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutViewType() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutViewTypeCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "List View";
        properties.setName(name);
        properties.setIndex(1);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithNoAdminPermission() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();

        String name = "Customize View";
        properties.setName(name);
        properties.setType(ViewType.CUSTOM);
        properties.setIndex(1);

        views.put(name, properties);
        this.noAdminAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithNoReadPermission() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧2.abc in (1780)");

        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithNoReadPermissionCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧2.abc in (1780)");

        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("ステータス");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("ステータス");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("aaaa");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("aaaa");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidSort() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("asc");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidSortCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setSort("asc");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号.AAA = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetRelatedRecordToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号.AAA = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOffsetToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 offset 10");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOffsetToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 offset 10");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetLimitToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 limit 10");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetLimitToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 limit 10");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOrderByToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 >= 1 order by レコード番号");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOrderByToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 >= 1 order by レコード番号");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetNotToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("not レコード番号 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetNotToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("not レコード番号 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetBothAndORToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 and レコード番号 = 2 or レコード番号 = 3");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetBothAndORToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = 1 and レコード番号 = 2 or レコード番号 = 3");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetNumberInSubTableToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("数値_0 in (1, 2)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetNumberInSubTableToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("数値_0 in (1, 2)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetLikeToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("aaa like \"\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetLikeToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("aaa like \"\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUserInGroupToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("グループ選択 in (\" USER\", \"yfang\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUserInGroupToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("グループ選択 in (\" USER\", \"yfang\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUserInOrgToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("組織選択 in (\" USER\", \"yfang\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUserInOrgToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("組織選択 in (\" USER\", \"yfang\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedOrgToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\" ORGANIZATION\", \"存在しない組織\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedOrgToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\" ORGANIZATION\", \"存在しない組織\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedGroupToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\" GROUP\", \"存在しないグループ\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedGroupToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\" GROUP\", \"存在しないグループ\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedUserToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\"存在しないユーザー\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedUserToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成者 in (\"存在しないユーザー\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    // KINTONE-11160
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDecimalToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_MONTH(0.1)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    // KINTONE-11160
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDecimalToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_MONTH(0.1)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetMonthStringToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_MONTH(AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetMonthStringToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_MONTH(AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetWeekStringToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_WEEK(AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetWeekStringToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < LAST_WEEK(AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDayOverflowToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(9999, YEARS)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDayOverflowToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(9999, YEARS)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalid2ndFromTodayToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(1, AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalid2ndFromTodayToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(1, AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalid1stFromTodayToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(1, AAA)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalid1stFromTodayToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(0.1, DAYS)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStringFromTodayToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(AAA, DAYS)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStringFromTodayToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("作成日時 < FROM_TODAY(AAA, DAYS)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidDateTimeToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日時 = \"2013/01/01T19:55Z\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidDateTimeToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日時 = \"2013/01/01T19:55Z\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidTimeToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("時刻 = \"1:1\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidTimeToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("時刻 = \"1:1\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidDateToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日付 = \"2013/01/01\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetInvalidDateToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日付 = \"2013/01/01\"");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("グループ in (\"AAA\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetGroupToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("グループ in (\"AAA\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("ステータス in (\"未処理\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetStatusToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("ステータス in (\"未処理\")");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOnlyRelatedRecordsToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧 in (1)");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetOnlyRelatedRecordsToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧 in (1)");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetFieldInRelatedRecordsToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧4.数値 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetFieldInRelatedRecordsToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("関連レコード一覧4.数値 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetFieldInSubTableToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("数値_0 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetFieldInSubTableToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("数値_0 = 1");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetTodayToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = TODAY()");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetTodayToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("レコード番号 = TODAY()");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedFieldToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("フィールドA = 100");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetUnexistedFieldToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("フィールドA = 100");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDuplicateAllView() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "（すべて）";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenDuplicateAllViewCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "（すべて）";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDateNowToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日付 = NOW()");
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetDateNowToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        String name = "List View";
        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFilterCond("日付 = NOW()");
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenRequestOverflow() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        for (int i = 0; i < 1001; i++) {
            String name = "a" + i;
            properties.setName(name);
            properties.setType(ViewType.LIST);
            properties.setIndex(i);
            properties.setFields(fields);

            views.put(name, properties);
        }
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenRequestOverflowCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        for (int i = 0; i < 1001; i++) {
            String name = "a" + i;
            properties.setName(name);
            properties.setType(ViewType.LIST);
            properties.setIndex(i);
            properties.setFields(fields);

            views.put(name, properties);
        }
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedTitle() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        properties.setName(name);
        properties.setType(ViewType.CALENDAR);
        properties.setIndex(1);
        properties.setTitle("文字列1行2");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedTitleCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        properties.setName(name);
        properties.setType(ViewType.CALENDAR);
        properties.setIndex(1);
        properties.setTitle("文字列1行2");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedDate() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        properties.setName(name);
        properties.setType(ViewType.CALENDAR);
        properties.setIndex(1);
        properties.setDate("日付2");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedDateCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        properties.setName(name);
        properties.setType(ViewType.CALENDAR);
        properties.setIndex(1);
        properties.setDate("日付2");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedFields() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("文字列1行2");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithUnexistedFieldsCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "Calendar View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("文字列1行2");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidFiltedCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "List View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);
        properties.setFilterCond("aaa < \"aaa\"");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithInvalidFiltedCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "List View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);
        properties.setFilterCond("aaa < \"aaa\"");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetCategoryToFilterCond() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "List View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧3.カテゴリー in (\"カテゴリーA\")");

        views.put(name, properties);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenSetCategoryToFilterCondCert() throws KintoneAPIException {
        HashMap<String, ViewModel> views = new HashMap<>();
        ViewModel properties = new ViewModel();
        String name = "List View";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("レコード番号");

        properties.setName(name);
        properties.setType(ViewType.LIST);
        properties.setIndex(1);
        properties.setFields(fields);
        properties.setFilterCond("関連レコード一覧3.カテゴリー in (\"カテゴリーA\")");

        views.put(name, properties);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNoViewValue() throws KintoneAPIException {
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, null, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenNoViewValueCert() throws KintoneAPIException {
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, null, -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views.getViews(), -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views.getViews(), -2);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAppZero() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        this.appManagerment.updateViews(0, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWhenAppZeroCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(VIEW_SETTING_APP_ID, null, false);
        this.certAppManagerment.updateViews(0, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutApp() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(APP_ID, null, false);
        this.appManagerment.updateViews(null, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewShouldFailWithoutAppCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(APP_ID, null, false);
        this.certAppManagerment.updateViews(null, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(APP_ID, null, false);
        this.appManagerment.updateViews(-1, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWhenGiveInvalidAppIdCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(APP_ID, null, false);
        this.certAppManagerment.updateViews(-1, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWhenAppUnexisted() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(APP_ID, null, false);
        this.appManagerment.updateViews(99999, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWhenAppUnexistedCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(APP_ID, null, false);
        this.certAppManagerment.updateViews(99999, views.getViews(), -1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWithInvalidRevision() throws KintoneAPIException {
        GetViewsResponse views = this.appManagerment.getViews(APP_ID, null, false);
        this.appManagerment.updateViews(VIEW_SETTING_APP_ID, views.getViews(), 1);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateViewhouldFailWithInvalidRevisionCert() throws KintoneAPIException {
        GetViewsResponse views = this.certAppManagerment.getViews(APP_ID, null, false);
        this.certAppManagerment.updateViews(VIEW_SETTING_APP_ID, views.getViews(), 1);
    }

    // KCB-641
    @Test
    public void testUpdateGeneralSettingsShouldSuccess() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP80", IconType.PRESET);

        generalSettings.setName("kintone sdk test");
        generalSettings.setRevision(null);
        generalSettings.setIcon(icon);
        generalSettings.setDescription("<b>kintone sdk testです。</b>");
        generalSettings.setTheme(IconTheme.BLUE);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    // KCB-641
    @Test
    public void testUpdateGeneralSettingsShouldSuccessCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP80", IconType.PRESET);

        generalSettings.setName("kintone sdk test");
        generalSettings.setRevision(null);
        generalSettings.setIcon(icon);
        generalSettings.setDescription("<b>kintone sdk testです。</b>");
        generalSettings.setTheme(IconTheme.BLUE);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test
    public void testUpdateGeneralSettingsShouldSuccessWithFileUpload() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.jpg");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test
    public void testUpdateGeneralSettingsShouldSuccessWithFileUploadCert() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.jpg");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithInvalidFileUpload() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithInvalidFileUploadCert() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test.txt");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenFileOverflow() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test1.jpg");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenFileOverflowCert() throws KintoneAPIException {
        FileModel fileModel = this.file.upload(TestConstantsSample.UPLOAD_PATH + "test1.jpg");

        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(fileModel, null, IconType.FILE);

        generalSettings.setIcon(icon);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutFile() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, IconType.FILE);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutFileCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, IconType.FILE);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutFileKey() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        FileModel file = new FileModel();
        file.setFileKey(null);
        Icon icon = new Icon(file, null, IconType.FILE);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutFileKeyCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        FileModel file = new FileModel();
        file.setFileKey(null);
        Icon icon = new Icon(file, null, IconType.FILE);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithInvalidIconKey() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP", IconType.PRESET);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithInvalidIconKeyCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP", IconType.PRESET);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIconKey() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, IconType.PRESET);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIconKeyCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, IconType.PRESET);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIconType() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP80", null);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIconTypeCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, "APP80", null);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIcon() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, null);
        generalSettings.setIcon(icon);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutIconCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        Icon icon = new Icon(null, null, null);
        generalSettings.setIcon(icon);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenDescriptionOverflow() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10001; i++) {
            sb.append("a");
        }

        String description = sb.toString();

        generalSettings.setDescription(description);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenDescriptionOverflowCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10001; i++) {
            sb.append("a");
        }

        String description = sb.toString();

        generalSettings.setDescription(description);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenNameOverflow() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 65; i++) {
            sb.append("a");
        }

        String name = sb.toString();

        generalSettings.setName(name);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenNameOverflowCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 65; i++) {
            sb.append("a");
        }

        String name = sb.toString();

        generalSettings.setName(name);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenNameBlank() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setName("");
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWhenNameBlankCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setName("");
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingsShouldFailWithoutAdminPermission() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        this.noAdminAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenAppIdZero() throws KintoneAPIException {
        this.appManagerment.updateGeneralSettings(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenAppIdZeroCert() throws KintoneAPIException {
        this.certAppManagerment.updateGeneralSettings(0, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWithUnexistedAppId() throws KintoneAPIException {
        this.appManagerment.updateGeneralSettings(99999, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWithUnexistedAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.updateGeneralSettings(99999, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWithoutApp() throws KintoneAPIException {
        this.appManagerment.updateGeneralSettings(null, null);
    }

    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWithoutAppCert() throws KintoneAPIException {
        this.certAppManagerment.updateGeneralSettings(null, null);
    }

    // KCB-641
    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        this.appManagerment.updateGeneralSettings(-1, null);
    }

    // KCB-641
    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenGiveInvalidAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.updateGeneralSettings(-1, null);
    }

    // KCB-659
    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenRevisionLessThanMinusOne() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setRevision(-2);
        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    // KCB-659
    @Test(expected = KintoneAPIException.class)
    public void testUpdateGeneralSettingshouldFailWhenRevisionLessThanMinusOneCert() throws KintoneAPIException {
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setRevision(-2);
        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);
    }

    @Test
    public void testGetGeneralSettingsShouldSuccess() throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();
        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();
        app.setApp(APP_ID);
        apps.add(app);

        generalSettings.setName("kintone sdk test");
        generalSettings.setDescription("cba");
        generalSettings.setTheme(IconTheme.GREEN);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);

        this.appManagerment.deployAppSettings(apps, false);
        Thread.sleep(3000);

        GeneralSettings response = this.appManagerment.getGeneralSettings(APP_ID, null, false);

        assertEquals("cba", response.getDescription());
        assertEquals(IconTheme.GREEN, response.getTheme());
        assertEquals("kintone sdk test", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessCert() throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();
        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();
        app.setApp(APP_ID);
        apps.add(app);

        generalSettings.setName("kintone sdk test");
        generalSettings.setDescription("cba");
        generalSettings.setTheme(IconTheme.GREEN);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);

        this.certAppManagerment.deployAppSettings(apps, false);
        Thread.sleep(3000);

        GeneralSettings response = this.certAppManagerment.getGeneralSettings(APP_ID, null, false);

        assertEquals("cba", response.getDescription());
        assertEquals(IconTheme.GREEN, response.getTheme());
        assertEquals("kintone sdk test", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWithPreview() throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();

        generalSettings.setName("test kintone sdk");
        generalSettings.setDescription("cba");
        generalSettings.setTheme(IconTheme.BLACK);

        this.appManagerment.updateGeneralSettings(APP_ID, generalSettings);

        GeneralSettings response = this.appManagerment.getGeneralSettings(APP_ID, null, true);

        assertEquals("cba", response.getDescription());
        assertEquals(IconTheme.BLACK, response.getTheme());
        assertEquals("test kintone sdk", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWithPreviewCert() throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();

        generalSettings.setName("test kintone sdk");
        generalSettings.setDescription("cba");
        generalSettings.setTheme(IconTheme.BLACK);

        this.certAppManagerment.updateGeneralSettings(APP_ID, generalSettings);

        GeneralSettings response = this.certAppManagerment.getGeneralSettings(APP_ID, null, true);

        assertEquals("cba", response.getDescription());
        assertEquals(IconTheme.BLACK, response.getTheme());
        assertEquals("test kintone sdk", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWhenInGuestSpace() throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();
        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();
        app.setApp(TestConstantsSample.GUEST_SPACE_APP_ID);
        apps.add(app);

        generalSettings.setName("Guest Space java sdk Test");
        generalSettings.setDescription("abcd");
        generalSettings.setTheme(IconTheme.GREEN);

        this.guestSpaceAppManagerment.updateGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, generalSettings);

        this.guestSpaceAppManagerment.deployAppSettings(apps, false);
        Thread.sleep(5000);

        GeneralSettings response = this.guestSpaceAppManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID,
                LanguageSetting.DEFAULT, false);

        assertEquals("abcd", response.getDescription());
        assertEquals(IconTheme.GREEN, response.getTheme());
        assertEquals("Guest Space java sdk Test", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWhenInGuestSpaceCert()
            throws KintoneAPIException, InterruptedException {
        GeneralSettings generalSettings = new GeneralSettings();
        ArrayList<PreviewAppRequest> apps = new ArrayList<>();
        PreviewAppRequest app = new PreviewAppRequest();
        app.setApp(TestConstantsSample.GUEST_SPACE_APP_ID);
        apps.add(app);

        generalSettings.setName("Guest Space java sdk Test");
        generalSettings.setDescription("abcd");
        generalSettings.setTheme(IconTheme.GREEN);
        generalSettings.setRevision(-1);

        this.certGuestAppManagerment.updateGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, generalSettings);

        this.certGuestAppManagerment.deployAppSettings(apps, false);
        Thread.sleep(5000);

        GeneralSettings response = this.certGuestAppManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID,
                LanguageSetting.DEFAULT, false);

        assertEquals("abcd", response.getDescription());
        assertEquals(IconTheme.GREEN, response.getTheme());
        assertEquals("Guest Space java sdk Test", response.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWithAllLang() throws KintoneAPIException {
        GeneralSettings engeneralSettings = this.appManagerment.getGeneralSettings(1806, LanguageSetting.EN, false);
        assertEquals("kintone-java-sdk-file-test1", engeneralSettings.getName());
        GeneralSettings jageneralSettings = this.appManagerment.getGeneralSettings(1806, LanguageSetting.JA, false);
        assertEquals("kintone-java-sdk-file-test", jageneralSettings.getName());
        GeneralSettings zhgeneralSettings = this.appManagerment.getGeneralSettings(1806, LanguageSetting.ZH, false);
        assertEquals("中国語", zhgeneralSettings.getName());
        GeneralSettings usergeneralSettings = this.appManagerment.getGeneralSettings(1806, LanguageSetting.USER, false);
        assertEquals("kintone-java-sdk-file-test", usergeneralSettings.getName());
    }

    @Test
    public void testGetGeneralSettingsShouldSuccessWithAllLangCert() throws KintoneAPIException {
        GeneralSettings engeneralSettings = this.certAppManagerment.getGeneralSettings(1806, LanguageSetting.EN, false);
        assertEquals("kintone-java-sdk-file-test1", engeneralSettings.getName());
        GeneralSettings jageneralSettings = this.certAppManagerment.getGeneralSettings(1806, LanguageSetting.JA, false);
        assertEquals("kintone-java-sdk-file-test", jageneralSettings.getName());
        GeneralSettings zhgeneralSettings = this.certAppManagerment.getGeneralSettings(1806, LanguageSetting.ZH, false);
        assertEquals("中国語", zhgeneralSettings.getName());
        GeneralSettings usergeneralSettings = this.certAppManagerment.getGeneralSettings(1806, LanguageSetting.USER,
                false);
        assertEquals("kintone-java-sdk-file-test", usergeneralSettings.getName());
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenHasNoAdminPermission() throws KintoneAPIException {
        this.noAdminAppManagerment.getGeneralSettings(APP_ID, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenHasNoReadPermission() throws KintoneAPIException {
        this.addOnlyAppManagerment.getGeneralSettings(APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppZero() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(0, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppZeroCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(0, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppZeroInPreview() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(0, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppZeroInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(0, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppNull() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(null, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppNullCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(null, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppNullInPreview() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(null, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppNullInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(null, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGiveInvalidAppId() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(-1, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGiveInvalidAppIdCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(-1, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGiveInvalidAppIdInPreview() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(-1, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGiveInvalidAppIdInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(-1, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppUnexisted() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(99999, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppUnexistedCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(99999, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppUnexistedInPreview() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(99999, null, true);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppUnexistedInPreviewCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(99999, null, true);
    }

    // Excute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGuestSpaceFunctionOff() throws KintoneAPIException {
        this.guestSpaceAppManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }

    // Excute When Guest Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenGuestSpaceFunctionOffCert() throws KintoneAPIException {
        this.certGuestAppManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }

    // Excute When Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenSpaceFunctionOff() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(TestConstantsSample.SPACE_APP_ID, null, false);
    }

    // Excute When Space Function Off
    @Ignore
    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenSpaceFunctionOffCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(TestConstantsSample.SPACE_APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppInGuestSpace() throws KintoneAPIException {
        this.appManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetGeneralSettingsShouldFailWhenAppInGuestSpaceCert() throws KintoneAPIException {
        this.certAppManagerment.getGeneralSettings(TestConstantsSample.GUEST_SPACE_APP_ID, null, false);
    }
}
