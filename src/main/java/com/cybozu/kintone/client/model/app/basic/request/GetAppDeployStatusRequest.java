package com.cybozu.kintone.client.model.app.basic.request;

import java.util.ArrayList;

public class GetAppDeployStatusRequest {
    private ArrayList<Integer> apps;

    public ArrayList<Integer> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<Integer> apps) {
        this.apps = apps;
    }

    public GetAppDeployStatusRequest(ArrayList<Integer> apps) {
        this.apps = apps;
    }
}
