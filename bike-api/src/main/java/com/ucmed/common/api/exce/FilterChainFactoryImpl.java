package com.ucmed.common.api.exce;

import java.util.List;


import com.ucmed.common.api.ApiFactory;
import com.ucmed.common.filter.ApiExecFilter;
import com.ucmed.common.filter.FilterChain;
import com.ucmed.common.filter.FilterChainFactory;

/**
 * @author sbwkl
 */
public class FilterChainFactoryImpl implements FilterChainFactory {

    private ApiFactory apiFactory;

    public void setApiFactory(ApiFactory apiFactory) {
        this.apiFactory = apiFactory;
    }

    @Override
    public FilterChain getFilterChain(List<ApiExecFilter> filters) {
        FilterChainImpl filterChain = new FilterChainImpl(filters);
        filterChain.setApiFactory(apiFactory);
        return filterChain;
    }

}
