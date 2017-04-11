package com.ucmed.common.api.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.ucmed.common.util.Constants;

public class DefaultApiLogger extends AbsLocalApiLogger {
    private static final Logger LOG = Logger.getLogger(DefaultApiLogger.class);

    @Override
    protected void addDateToMap(Map<String, Object> map, String userId,
                                JSONObject req) {
        JSONObject params = req.optJSONObject(Constants.PARAMS);
        if(params != null)
            try {
                map.put("id", params.getInt("id"));
            } catch (Exception e) {
            }
    }
}
