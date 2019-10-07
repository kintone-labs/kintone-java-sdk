package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.basic.request.PreviewApp;

public class DeployAppSettingsRequest {
    private ArrayList<PreviewApp> apps;
    private Boolean revert;

    public ArrayList<PreviewApp> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<PreviewApp> apps) {
        this.apps = apps;
    }

    public Boolean getRevert() {
        return this.revert;
    }

    public void setRevert(Boolean revert) {
        this.revert = revert;
    }

    public DeployAppSettingsRequest(ArrayList<PreviewApp> apps, Boolean revert) {
        this.apps = apps;
        this.revert = revert;
    }
}
