package com.ucmed.common.filter;


import com.ucmed.common.exception.ExecFilterException;

public class SessionExecFilterException extends ExecFilterException {

	private static final long serialVersionUID = -5534715653716257570L;
	
	private Integer userId;

	public SessionExecFilterException(int code, String msg) {
		super(code, msg);
	}
	
	public SessionExecFilterException(int code, String msg, Integer userId) {
		super(code, msg);
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}

}
