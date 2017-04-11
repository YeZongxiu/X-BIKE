package com.ucmed.common.api.exce;

import com.ucmed.common.api.ApiFactory;
import com.ucmed.common.api.ApiResult;
import com.ucmed.common.api.web.Api;
import com.ucmed.common.filter.AbsFilterChain;
import com.ucmed.common.filter.ApiExecFilter;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author sbwkl
 */
public class FilterChainImpl extends AbsFilterChain {
    private ApiFactory apiFactory;


    public FilterChainImpl(List<ApiExecFilter> f) {
        super(f);
    }

    public void setApiFactory(ApiFactory apiFactory) {
        this.apiFactory = apiFactory;
    }

    @Override
    public JSONObject doApi(JSONObject jsonReq, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JSONObject jsonRes;
        String iendtity = jsonReq.optString(Constants.API_NAME);
        Api api = apiFactory.getApi(iendtity);
        ApiResult a = execute(api, jsonReq, request);
        return JSONObject.fromObject(a);
    }

    /**
     * 可以通过复写此方法实现其他的例如验证登陆等行为.
     * @param jsonReq
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    protected ApiResult execute(ApiFactory apiFactory, JSONObject jsonReq,
                                HttpServletRequest request) throws Exception {
        String apiName = jsonReq.optString(Constants.API_NAME);
        JSONObject params = jsonReq.optJSONObject(Constants.PARAMS);
        params.put("api_name", apiName);
        String iendtity = jsonReq.optString(Constants.API_NAME);
        Api api = apiFactory.getApi(iendtity);
        ApiResult res = null;
        if(api == null) {
            // not find api
            res = new ApiResult(1, "no such api", null);
        }
        if(api instanceof Api) {
            res = new ApiResult(0, "", ((Api) api).execute(params));
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private ApiResult execute(Api api, JSONObject req, HttpServletRequest request) {
        JSONObject params = req.optJSONObject(Constants.PARAMS);
        ApiResult res = null;
        if(api == null) {
            // not find api
            res = new ApiResult(1, "no such api", null);
        }
        if(api instanceof Api) {
            res = new ApiResult(0, "", ((Api) api).execute(params));
        }
        return res;
    }

}
