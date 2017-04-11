package com.ucmed.common.api.logger;

import net.sf.json.JSONObject;

/**
 * Created by ucmed on 2017/3/15.
 */
public abstract class AbsApiLogger implements ApiLogger {
    protected Object service;

    public AbsApiLogger() {
    }

    public abstract void log(String var1, JSONObject var2);

    public void setService(Object var1) {
        this.service = var1;
    }
}