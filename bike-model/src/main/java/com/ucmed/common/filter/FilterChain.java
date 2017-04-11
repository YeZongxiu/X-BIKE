package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface FilterChain {
    void doFilter(JSONObject var1, JSONObject var2, HttpServletRequest var3, HttpServletResponse var4) throws ExecFilterException, Exception;

    JSONObject doApi(JSONObject var1, HttpServletRequest var2, HttpServletResponse var3) throws Exception;
}
