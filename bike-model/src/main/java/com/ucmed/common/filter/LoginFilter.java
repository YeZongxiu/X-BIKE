package com.ucmed.common.filter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ucmed.common.dao.user.UserSessionMapper;
import com.ucmed.common.dataobj.user.UserSession;
import com.ucmed.common.exception.ExecFilterException;
import com.ucmed.common.model.user.ClientSession;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.SHA256Util;
import com.ucmed.common.util.SysUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class LoginFilter extends AbsNamedApiExecFilter {

    private List<String> apiNameList = null;

    public void setApiNameList(List<String> apiNameList) {
        this.apiNameList = apiNameList;
    }

    public static final Logger LOG = Logger.getLogger(LoginFilter.class);

    private MemcachedClient memcachedClient;
    private UserSessionMapper userSessionMapper;


    public void setUserSessionMapper(UserSessionMapper userSessionMapper) {
        this.userSessionMapper = userSessionMapper;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public void after(JSONObject jsonReq, JSONObject jsonRes,
                      HttpServletRequest request, HttpServletResponse response)
            throws ExecFilterException {
        JSONObject result = jsonRes.optJSONObject("return_params");
        if (result != null && result.optInt("ret_code") == Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS) {
        	String sessionId = SysUtil.getClientSessionId();
            Long userId = result.optLong("user_id");
            String deviceType = jsonReq.optString("device_type");
            String device = jsonReq.optString("device");
            UserSession userSession= userSessionMapper.getByUserId(userId);
            if(null != userSession){
            	userSession.setSessionId(sessionId);
            	userSession.setUpdateTime(new Date());
            	userSession.setIsDelete("0");
                userSession.setDevice(device);
                userSession.setDeviceType(deviceType);
                userSessionMapper.updateByPrimaryKeySelective(userSession);
            }else {
            	userSession = new UserSession();
            	userSession.setUserId(userId);
            	userSession.setSessionId(sessionId);
                userSession.setDevice(device);
                userSession.setDeviceType(deviceType);
            	userSession.setCreateTime(new Date());
            	userSession.setUpdateTime(new Date());
            	userSessionMapper.insertSelective(userSession);
			}
            result.put("session_id", sessionId);
            result.remove("user_id");
            jsonRes.put("return_params", result);
            ClientSession clientSession = new ClientSession();
            clientSession.setUserId(userId);
            clientSession.setSessionId(sessionId);
            setClientSession(sessionId, clientSession);
        }
    }

    protected String getClientSessionId() {
        String randKey = UUID.randomUUID().toString() + "_"
                + System.currentTimeMillis();
        String s = SHA256Util.hash(randKey);
        return s;
    }

    public void setClientSession(String sessionId, ClientSession clientSession) {
        try {
            memcachedClient.set(sessionId, 30 * 24 * 60 * 60, clientSession);
        } catch (Exception e) {
            LOG.info("LoginFilter set memcachedClient error", e);
        }
    }

    @Override
    public String[] getApiNames() {
        if (apiNameList == null || apiNameList.size() < 1)
            throw new RuntimeException(
                    "api names of LoginRequiredFilter must not be empty.");
        return apiNameList.toArray(new String[apiNameList.size()]);
    }
}
