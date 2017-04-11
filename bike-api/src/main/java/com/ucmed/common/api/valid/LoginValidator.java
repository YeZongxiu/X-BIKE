package com.ucmed.common.api.valid;

import net.sf.json.JSONObject;
import com.ucmed.common.exception.ValidateException;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface LoginValidator {
    void login(JSONObject var1, JSONObject var2) throws ValidateException;
}
