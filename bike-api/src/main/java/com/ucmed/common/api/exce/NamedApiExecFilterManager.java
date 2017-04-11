package com.ucmed.common.api.exce;

import com.ucmed.common.exception.ExecFilterException;
import com.ucmed.common.filter.ApiExecFilter;
import com.ucmed.common.filter.FilterChain;
import com.ucmed.common.filter.NamedApiExecFilter;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by ucmed on 2017/3/15.
 */
public class NamedApiExecFilterManager extends DefaultApiExecFilterManager {
    private static final Logger LOG = Logger.getLogger("api.filter");
    private Map<String, List<ApiExecFilter>> map;
    private String apiName = "api_name";
    private List<ApiExecFilter> tempList = new ArrayList();

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void init() {
        this.map = new HashMap();
        if(CollectionUtils.isNotEmpty(getFilters())) {
            Iterator it = getFilters().iterator();
            while(it.hasNext()) {
                ApiExecFilter filter = (ApiExecFilter) it.next();
                if(filter instanceof NamedApiExecFilter) {
                    it.remove();
                    String[] apis = ((NamedApiExecFilter) filter)
                            .getApiNames();
                    if((apis != null) && (apis.length > 0))
                        for(String api : apis)
                            addFilter(api, filter);
                } else {
                    this.tempList.add(filter);
                    addFilterAll(filter);
                }
            }
        }
        this.tempList.clear();
    }

    private void addFilterAll(ApiExecFilter filter) {
        for(String key : this.map.keySet())
            ((List) this.map.get(key)).add(filter);
    }

    private void addFilter(String api, ApiExecFilter filter) {
        if(!(this.map.containsKey(api))) {
            List list = new ArrayList();
            if(CollectionUtils.isNotEmpty(this.tempList))
                list.addAll(this.tempList);
            this.map.put(api, list);
        }
        ((List) this.map.get(api)).add(filter);
    }

    public JSONObject filter(JSONObject jsonReq,
                             HttpServletRequest request, HttpServletResponse response)
            throws ExecFilterException, Exception {
        String an = jsonReq.optString(this.apiName);
        LOG.debug("api named exec filter factory do filter start");
        List l = (List) this.map.get(an);
        List temp = new ArrayList();
        if(CollectionUtils.isNotEmpty(l))
            temp.addAll(l);
        else if(CollectionUtils.isNotEmpty(getFilters()))
            temp.addAll(getFilters());
        FilterChain filterChain = getFilterChainFactory().getFilterChain(
                temp);
        JSONObject jsonRes = new JSONObject();
        filterChain.doFilter(jsonReq, jsonRes, request, response);
        LOG.debug("api named exec filter factory do filter end");
        return jsonRes;
    }
}
