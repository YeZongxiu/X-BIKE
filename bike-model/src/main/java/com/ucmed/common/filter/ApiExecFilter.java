package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface ApiExecFilter {
    void filter(FilterChain var1, JSONObject var2, JSONObject var3, HttpServletRequest var4, HttpServletResponse var5) throws ExecFilterException, Exception;
}
