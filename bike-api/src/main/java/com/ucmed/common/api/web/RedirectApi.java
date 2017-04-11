package com.ucmed.common.api.web;


import com.ucmed.common.api.net.HttpClient;
import net.sf.json.JSONObject;

/**
 * use for redirect to other platform
 * 
 * @author shin
 * 
 */
public class RedirectApi implements Api {

	private HttpClient client;

	private String appId;

	private String appKey;

	private String redirectUrl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		client = new HttpClient(this.redirectUrl);
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	@Override
	public JSONObject execute(JSONObject params) {
		return null;
	}

	/**
	 * 
	 * get access result
	 * 
	 * @param reqStr
	 * @return
	 */
	public JSONObject exec(String reqStr) {
		JSONObject temp = JSONObject.fromObject(reqStr);
		temp.put("app_id", appId);
		temp.put("app_key", appKey);
		return JSONObject.fromObject(client.sendSynchronousRequest(temp
				.toString()));
	}

}
