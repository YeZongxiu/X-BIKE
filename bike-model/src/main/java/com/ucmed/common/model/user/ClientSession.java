package com.ucmed.common.model.user;

import java.io.Serializable;
import java.util.HashMap;

public class ClientSession extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;

	private Long userId;

	public void putCache(String key, Serializable value) {
		this.put(key, value);
	}

	public Serializable getCache(String key) {
		return (Serializable) this.get(key);
	}

	public final String getSessionId() {
		return sessionId;
	}

	public final void setSessionId(String sessionId) {
		this.put("session_id", sessionId);
		this.sessionId = sessionId;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.put("user_id", userId);
		this.userId = userId;
	}

}
