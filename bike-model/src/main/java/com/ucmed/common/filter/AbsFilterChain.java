package com.ucmed.common.filter;

import com.ucmed.common.exception.ExecFilterException;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/15.
 */
public abstract class AbsFilterChain implements FilterChain {
    private List<ApiExecFilter> filters = new ArrayList();
    private int index = 0;
    private static final Logger LOG = Logger.getLogger("api.filter");

    public AbsFilterChain(List<ApiExecFilter> f) {
        if(CollectionUtils.isNotEmpty(f)) {
            this.filters = f;
        }

    }

    public void doFilter(JSONObject jsonReq, JSONObject jsonRes, HttpServletRequest request, HttpServletResponse response) throws ExecFilterException, Exception {
        if(this.filters != null && this.filters.size() > this.index) {
            LOG.debug("filter chain do filter start, index of:" + this.index);
            ((ApiExecFilter)this.filters.get(this.index++)).filter(this, jsonReq, jsonRes, request, response);
            LOG.debug("filter chain do filter end, index of:" + (this.index - 1));
        } else if(this.filters == null || this.filters.size() == this.index) {
            LOG.debug("filter chain do api start: " + jsonReq);
            jsonRes.putAll(this.doApi(jsonReq, request, response));
            LOG.debug("filter chain do api end: " + jsonRes);
        }

    }
}
