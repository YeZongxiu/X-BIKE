package com.ucmed.common.api.web;

import com.ucmed.common.api.ApiFactory;
import com.ucmed.common.api.ClientSession;
import com.ucmed.common.api.exce.ApiExecFilterManager;
import com.ucmed.common.api.logger.ApiLoggerFactory;
import com.ucmed.common.exception.AccessException;
import com.ucmed.common.exception.ValidateException;
import com.ucmed.common.filter.SessionExecFilterException;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.SysUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * abstract controller, format out and in.
 * @author shin
 */
public abstract class AbsApiController {

    @Autowired
    private ApiLoggerFactory apiLoggerFactory;

    @Autowired(required = true)
    private ApiExecFilterManager apiExecFilterManager;

    @Autowired
    private MemcachedClient memcachedClient;

    /**
     * can't be null.
     * @return
     */
    protected abstract Logger getLogger();

    protected abstract ApiFactory getApiFactory();

    /**
     * access statistic.
     */
    protected void accessStatistics(HttpServletRequest request,
                                    JSONObject jsonReq) throws Exception {
        String apiName = jsonReq.optString(Constants.API_NAME);
        Integer userId = null;
        String sessionId = jsonReq.optString("session_id");
        if(!SysUtil.isBlank(sessionId)) {
            ClientSession session = (ClientSession) memcachedClient
                    .get(sessionId);
            if(session != null) {
                userId = session.getUserId();
            }
        }
        apiLoggerFactory.getLogger(apiName).log(
                userId == null ? null : userId.toString(), jsonReq);
    }

    protected String absApi(HttpServletRequest request,
                            HttpServletResponse response, ModelMap map) {
        long begtime = System.currentTimeMillis();
        response.setContentType("application/json; charset=utf-8");
        String reqStr = request.getParameter("requestData");
        getLogger().info(
                "session is " + (request.getSession()).getId()
                        + " &&&&& REQUEST:\n\n" + reqStr + "\n\n");

        JSONObject jsonReq = null;
        JSONObject jsonRes = null;
        try {
            jsonReq = JSONObject.fromObject(reqStr);
            JSONObject params = jsonReq.optJSONObject(Constants.PARAMS);
            params.put("session_id", jsonReq.optString("session_id"));
            jsonRes = apiExecFilterManager.filter(jsonReq, request,
                    response);
        } catch(AccessException ae) { // access error
            writeError(ae);
            jsonRes = getApiResult(4, ae.getMessage(), null);
        } catch(ValidateException ve) {
            writeError(ve);
            jsonRes = getApiResult(ve.getCode(), ve.getMessage(), null);
        } catch (SessionExecFilterException se){
            writeError(se);
            jsonRes = getApiResult(se.getCode(), se.getMessage(), null);
        } catch(Exception e) {
            writeError(e);
            jsonRes = getApiResult(3, "server cause an error", null);
        } finally {
            // access statistics
            try {
                accessStatistics(request,
                        jsonReq == null ? JSONObject.fromObject(reqStr)
                                : jsonReq);
            } catch(Exception e) {
                getLogger().error(e);
            }
        }

        map.put("jsonStr", jsonRes.toString());
        long costtime = System.currentTimeMillis() - begtime;
        getLogger().info(
                "session is " + (request.getSession()).getId()
                        + "&&&&& RESPONSE is success! this cost time is:"
                        + costtime + " s !\n\nresponse data is: "
                        + jsonRes.toString() + "\n");
        return "api/x";
    }

    private void writeError(Exception e) {
        getLogger().error("", e);
    }

    protected JSONObject getApiResult(int code, String msg,
                                      Map<String, Object> params) {
        JSONObject res = new JSONObject();
        res.put("return_code", code);
        res.put("return_msg", msg);
        if(params != null) {
            res.put("return_params", params);
        }
        return res;
    }
}

