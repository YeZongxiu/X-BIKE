package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import com.ucmed.common.model.user.ClientSession;
import com.ucmed.common.model.user.UserSessionModel;
import com.ucmed.common.service.user.UserSessionService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ucmed on 2017/3/17.
 */
public class LoginRequiredFilter extends AbsNamedApiExecFilter {

    /*
     * private static final Logger LOG = Logger
     * .getLogger(LoginRequiredFilter.class);
     */

    private MemcachedClient memcachedClient;


    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    private UserSessionService userSessionService;

    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    private List<String> apiNameList = null;

    public void setApiNameList(List<String> apiNameList) {
        this.apiNameList = apiNameList;
    }

    public List<String> getApiNameList() {
        return apiNameList;
    }

    public void before(JSONObject jsonReq, HttpServletRequest request,
                       HttpServletResponse response) throws ExecFilterException {
        try {
            assertLogin(jsonReq, request, response);
        } catch(SessionExecFilterException e) {
            throw e;
        }
    }
    private void assertLogin(JSONObject jsonReq, HttpServletRequest request,
                             HttpServletResponse response) throws ExecFilterException {
        String sessionId = jsonReq.optString(Constants.API_PARAMS_SESSION_ID);
        if(StringUtils.isBlank(sessionId))
            throw new SessionExecFilterException(401, "未取到用户登录状态");
        ClientSession clientSession = new ClientSession();
        try {
            clientSession = (ClientSession) memcachedClient
                    .get(sessionId);
        }catch (Exception e){
            throw new SessionExecFilterException(401, "登陆超时，请重新登陆");
        }
        if (null == clientSession)
            throw new SessionExecFilterException(401, "登陆超时，请重新登陆");
        jsonReq.optJSONObject(Constants.PARAMS).put(
                Constants.API_PARAMS_SESSION_ID, sessionId);

        try {
            if(null == clientSession) {
                throw new SessionExecFilterException(401, "登陆超时，请重新登陆");
            }
            UserSessionModel model = userSessionService.getBySession( sessionId);
            if(null == model || null == model.getUserId()
                    || null == model.getSessionId()) {
                throw new SessionExecFilterException(401, "登陆超时，请重新登陆");
            }
            if(sessionId.equals(model.getSessionId()) && clientSession.getUserId().equals(model.getUserId())) {
                addCacheSession(sessionId, model.getUserId());
                jsonReq.optJSONObject(Constants.PARAMS).put(
                        Constants.API_PARAMS_USER_ID, model.getUserId());
                return;
            } else {
                throw new SessionExecFilterException(401, "用户登录信息缓存异常");
            }
        }catch (Exception e) {
            throw new SessionExecFilterException(402, "用户的登录状态异常");
        }
    }

    protected void addCacheSession(String sessionId, Long userId) throws Exception{
        ClientSession clientSession = new ClientSession();
        clientSession.setSessionId(sessionId);
        clientSession.setUserId(userId);
        memcachedClient.set(sessionId, 30 * 60 * 60, clientSession);
    }

    @Override
    public String[] getApiNames() {
        if(null == apiNameList || 1 > apiNameList.size()) {
            throw new RuntimeException(
                    "Api names of LoginRequiredFilter must not be empty.");
        }
        return apiNameList.toArray(new String[apiNameList.size()]);
    }

}
