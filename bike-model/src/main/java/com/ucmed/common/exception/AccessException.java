package com.ucmed.common.exception;
/**
 * 权限检查异常
 *
 */
public class AccessException extends Exception {
	
	private static final long serialVersionUID = 6323473112111171900L;

	public AccessException(String msg) {
		super(msg);
	}
}
