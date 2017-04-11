package com.ucmed.common.api.exce;

import com.ucmed.common.exception.ExecFilterException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface ApiExecFilterManager {
    JSONObject filter(JSONObject var1, HttpServletRequest var2, HttpServletResponse var3) throws ExecFilterException, Exception;
}