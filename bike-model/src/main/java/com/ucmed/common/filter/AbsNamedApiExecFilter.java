package com.ucmed.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ucmed.common.exception.ExecFilterException;

public abstract class AbsNamedApiExecFilter implements NamedApiExecFilter {
	public void before(JSONObject jsonReq, HttpServletRequest request,
			HttpServletResponse response) throws ExecFilterException {
	}

	public void after(JSONObject jsonReq, JSONObject jsonRes,
			HttpServletRequest request, HttpServletResponse response)
			throws ExecFilterException {
	}

	public void filter(FilterChain filterChain, JSONObject jsonReq,
			JSONObject jsonRes, HttpServletRequest request,
			HttpServletResponse response) throws ExecFilterException, Exception {
		this.before(jsonReq, request, response);
		filterChain.doFilter(jsonReq, jsonRes, request, response);
		this.after(jsonReq, jsonRes, request, response);
	}
}
