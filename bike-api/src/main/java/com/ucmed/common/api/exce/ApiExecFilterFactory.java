package com.ucmed.common.api.exce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ucmed.common.exception.ValidateException;
import net.sf.json.JSONObject;

public interface ApiExecFilterFactory {
	public void before(JSONObject jsonReq, HttpServletRequest request,
					   HttpServletResponse response) throws ValidateException;
	
	public void after(JSONObject jsonReq, JSONObject jsonRes,
					  HttpServletRequest request, HttpServletResponse response);
}
