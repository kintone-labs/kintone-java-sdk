package com.cybozu.kintone.client.model.app.basic.response;

import com.cybozu.kintone.client.model.app.app.AppDeployStatus;

import java.util.ArrayList;


public class GetAppDeployStatusResponse {
    private ArrayList<AppDeployStatus> apps;

    public ArrayList<AppDeployStatus> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<AppDeployStatus> apps) {
        this.apps = apps;
    }
}
