package com.ucmed.common.invoker;

import net.sf.json.JSONObject;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ServiceResult {
    private Integer code;
    private JSONObject params;
    private String errInfo;

    public ServiceResult() {
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public JSONObject getParams() {
        return this.params;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getErrInfo() {
        return this.errInfo;
    }
}
