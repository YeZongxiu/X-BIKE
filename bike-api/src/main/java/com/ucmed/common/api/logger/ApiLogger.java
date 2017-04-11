package com.ucmed.common.api.logger;

import net.sf.json.JSONObject;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface ApiLogger {
    void log(String var1, JSONObject var2);

    void setService(Object var1);
}
