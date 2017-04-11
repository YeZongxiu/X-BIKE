package com.ucmed.common.invoker;

import net.sf.json.JSONObject;

/**
 * Created by ucmed on 2017/3/15.
 */
public class Service {
    private String service;
    private String method;
    private JSONObject params;

    public Service() {
    }

    public Service(String service, String method) {
        this.service = service;
        this.method = method;
    }

    public Service(String service, String method, JSONObject params) {
        this.service = service;
        this.method = method;
        this.params = params;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return this.method;
    }

    public String getService() {
        return this.service;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public JSONObject getParams() {
        return this.params;
    }
}
