package com.cybozu.kintone.client.model.app.basic.request;

import com.cybozu.kintone.client.model.app.general.GeneralSettings;

public class UpdateGeneralSettingsRequest extends GeneralSettings {
    private Integer app;

    public UpdateGeneralSettingsRequest(Integer app, GeneralSettings generalSettings) {
        this.app = app;
        if (generalSettings != null) {
            super.setName(generalSettings.getName());
            super.setDescription(generalSettings.getDescription());
            super.setIcon(generalSettings.getIcon());
            super.setTheme(generalSettings.getTheme());
            super.setRevision(generalSettings.getRevision());
        }
    }

    public Integer getApp() {
        return app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public GeneralSettings getGeneralSettings() {
        GeneralSettings generalSetting = new GeneralSettings();
        generalSetting.setName(super.getName());
        generalSetting.setDescription(super.getDescription());
        generalSetting.setIcon(super.getIcon());
        generalSetting.setTheme(super.getTheme());
        generalSetting.setRevision(super.getRevision());
        return generalSetting;
    }

    public void setGeneralSettings(GeneralSettings generalSettings) {
        super.setName(generalSettings.getName());
        super.setDescription(generalSettings.getDescription());
        super.setIcon(generalSettings.getIcon());
        super.setTheme(generalSettings.getTheme());
        super.setRevision(generalSettings.getRevision());
    }

}
