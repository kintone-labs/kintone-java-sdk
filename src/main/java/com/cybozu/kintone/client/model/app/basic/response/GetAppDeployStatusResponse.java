package com.cybozu.kintone.client.model.app.basic.response;

import java.util.ArrayList;

import com.cybozu.kintone.client.model.app.AppDeployStatus;

public class GetAppDeployStatusResponse {
    private ArrayList<AppDeployStatus> apps;

    public ArrayList<AppDeployStatus> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<AppDeployStatus> apps) {
        this.apps = apps;
    }
}
