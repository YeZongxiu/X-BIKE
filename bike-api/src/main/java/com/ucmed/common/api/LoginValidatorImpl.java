package com.ucmed.common.api;

import com.ucmed.common.exception.NoLoginApiException;
import com.ucmed.common.exception.ValidateException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ucmed.common.api.valid.LoginValidator;
import com.ucmed.common.util.ServiceUtil;
import com.ucmed.common.util.ReturnMsg;

public class LoginValidatorImpl implements LoginValidator {

	public static final Logger LOG = Logger.getLogger(LoginValidatorImpl.class);

	private MemcachedClient memcachedClient;

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public void login(JSONObject res, JSONObject obj) throws ValidateException {
		ClientSession session = null;
		String sessionId = obj.optString("session_id");
		String userType = obj.optString("user_type");
		try {
			session = getClientSession(sessionId,userType);
		} catch (Exception e) {
			throw new ValidateException(ReturnMsg.LOGIN_AFTER_ACCESS, "登录超时，请重新登录");
		}

		if (session == null) {
			throw new NoLoginApiException(ReturnMsg.LOGIN_AFTER_ACCESS, "登录超时，请重新登录");
		}
		setClientSession(sessionId, session);
		if(res!=null){
			res.put("client_session", session);
			res.optJSONObject("client_session").put("U", session.getUserId());
			res.put("user_id", session.getUserId());
		}
		JSONObject o = res.optJSONObject("params");
		if (o != null) {
			o.put("client_session", session);
			o.put("user_id", session.getUserId());
		}
	}

	public ClientSession getClientSession(String sessionId,String userType) {
		ClientSession cs;
		JSONObject d;
		try {
			cs = (ClientSession) memcachedClient.get(sessionId);
			if (cs == null) {
				JSONObject obj = new JSONObject();
				obj.put("sessionId", sessionId);
				obj.put("userType", userType);
				obj.put("U", userType);
				JSONObject o = ServiceUtil.doService("userSessionService",
						"getBySessionId", obj);
				if (o != null) {
					cs = new ClientSession();
					cs.setSessionId(o.optString("htSessionId"));
					cs.setUserId(o.optInt("userId"));
					//setClientSession(o.optString("sessionId"),cs);
				}
			}
			return cs;
		} catch (Exception e) {
			LOG.info("LoginValidatorImpl get memcachedClient error", e);
		}
		return null;
	}

	public void setClientSession(String sessionId, ClientSession clientSession) {
		try {
			memcachedClient.set(sessionId, 30 * 60, clientSession);
		} catch (Exception e) {
			LOG.info("LoginValidatorImpl set memcachedClient error", e);
		}
	}
}
