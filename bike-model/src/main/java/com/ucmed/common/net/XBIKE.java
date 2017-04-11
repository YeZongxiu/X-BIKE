/**
 * 
 */
package com.ucmed.common.net;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.ucmed.common.util.Constants;
import net.sf.json.JSONObject;

import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

/**
 * @author Gerrard
 * @date 2015年5月12日
 */
public class XBIKE {
	private static final Logger log = Logger.getLogger(XBIKE.class);

	public static final String TARGET_URL = "http://127.0.0.1:8080/api/exec.htm";
	private HttpClient mClient;
	private static XBIKE mInstance;

	/**
	 * 渠道标志 空 android 1 android 2 android pad 3 apple 4 apple ipad 5 wap 6 web
	 */

	public static XBIKE getInstance() {
		if (mInstance == null)
			mInstance = new XBIKE();
		return mInstance;
	}

	private XBIKE() {
		mClient = new HttpClient(TARGET_URL);
	}

	/**
	 * for new uri's request
	 * 
	 * @param url
	 */
	public XBIKE(String url) {
		mClient = new HttpClient(url);
	}

	public JSONObject requestActionParams(String apiName, JSONObject params, String sessionId) {
		JSONObject res = null;

		try {
			JSONObject request = new JSONObject();
			request.put(Constants.PARAMS, params);
			request.put(Constants.API_NAME, apiName);
			request.put(Constants.API_PARAMS_SESSION_ID, sessionId);
			res = request(request);
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}
		return res;
	}

	public JSONObject request(JSONObject request) {
		JSONObject res = null;
		String response = null;
		try {
			synchronized (mClient) {
				log.info("&&&&&&&&&&&&&&&&&&&-->" + request.toString());
				response = mClient.sendSynchronousRequest(request.toString());
			}

			res = JSONObject.fromObject(response);
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}
		return res;
	}
}