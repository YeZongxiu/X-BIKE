package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface ApiExecExtraFilter extends ApiExecFilter {
    void before(JSONObject var1, HttpServletRequest var2, HttpServletResponse var3) throws ExecFilterException;

    void after(JSONObject var1, JSONObject var2, HttpServletRequest var3, HttpServletResponse var4) throws ExecFilterException;
}
