package com.ucmed.common.api.exce;

import com.ucmed.common.exception.ExecFilterException;
import com.ucmed.common.filter.ApiExecFilter;
import com.ucmed.common.filter.FilterChain;
import com.ucmed.common.filter.FilterChainFactory;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ucmed on 2017/3/15.
 */
public class DefaultApiExecFilterManager implements ApiExecFilterManager {
    private static final Logger LOG = Logger.getLogger("api.filter");
    private List<ApiExecFilter> filters;
    private FilterChainFactory filterChainFactory;

    public DefaultApiExecFilterManager() {
    }

    public void setFilterChainFactory(FilterChainFactory filterChainFactory) {
        this.filterChainFactory = filterChainFactory;
    }

    public void setFilters(List<ApiExecFilter> filters) {
        this.filters = filters;
    }

    protected FilterChainFactory getFilterChainFactory() {
        return this.filterChainFactory;
    }

    protected List<ApiExecFilter> getFilters() {
        return this.filters;
    }

    public JSONObject filter(JSONObject jsonReq, HttpServletRequest request, HttpServletResponse response) throws ExecFilterException, Exception {
        JSONObject jsonRes = new JSONObject();
        FilterChain filterChain = this.getFilterChainFactory().getFilterChain(this.getFilters());
        LOG.debug("api exec filter factory do filter start");
        filterChain.doFilter(jsonReq, jsonRes, request, response);
        LOG.debug("api exec filter factory do filter end");
        return jsonRes;
    }
}

