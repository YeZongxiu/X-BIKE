package com.ucmed.common.api;

import java.io.Serializable;
import java.util.Map;

/**
 * 标准化api结果信息
 * 
 * @author shin
 *
 */
public class ApiResult implements Serializable {
	private static final long serialVersionUID = 917664758890128561L;
	
	private int return_code;
	private String return_msg;
	private Map<String, Object> return_params;

	public ApiResult(int return_code, String return_msg) {
		this.return_code = return_code;
		this.return_msg = return_msg;
	}

	public ApiResult(int return_code, String return_msg,
			Map<String, Object> return_params) {
		this.return_code = return_code;
		this.return_msg = return_msg;
		this.return_params = return_params;
	}

	public int getReturn_code() {
		return return_code;
	}

	public void setReturn_code(int return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public Map<String, Object> getReturn_params() {
		return return_params;
	}

	public void setReturn_params(Map<String, Object> return_params) {
		this.return_params = return_params;
	}

}
