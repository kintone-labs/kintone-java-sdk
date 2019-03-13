package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.basic.request.PreviewAppRequest;

public class DeployAppSettingsRequest {
    private ArrayList<PreviewAppRequest> apps;
    private Boolean revert;

    public ArrayList<PreviewAppRequest> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<PreviewAppRequest> apps) {
        this.apps = apps;
    }

    public Boolean getRevert() {
        return this.revert;
    }

    public void setRevert(Boolean revert) {
        this.revert = revert;
    }

    public DeployAppSettingsRequest(ArrayList<PreviewAppRequest> apps, Boolean revert) {
        this.apps = apps;
        this.revert = revert;
    }
}
