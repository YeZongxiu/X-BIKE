package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.service.user.UserSessionService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ucmed on 2017/3/18.
 */
public class UserLoginOutApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(UserLoginOutApi.class);
    private MemcachedClient memcachedClient;
    private UserSessionService userSessionService;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        String sessionId = params.optString(Constants.API_PARAMS_SESSION_ID);
        if (!StringUtil.isNotBlank(sessionId)) {
            return errorResult(result, "退出登录失败，请稍后再试", "缺少参数");
        }
        try {
            memcachedClient.delete(sessionId);
            userSessionService.loginOut(sessionId);
            result.put("ret_code", 0);
            result.put("ret_info", "退出登录成功");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", Constants.API_EXCEPTION_MSG);
        }
        return result;
    }

    private JSONObject errorResult(JSONObject res, String retInfo, String logString) {
        JSONObject result = res;
        if (result == null) {
            result = new JSONObject();
        }
        result.put("ret_code", 1);
        result.put("ret_info", retInfo);
        LOG.warn(logString);
        return result;
    }
}
