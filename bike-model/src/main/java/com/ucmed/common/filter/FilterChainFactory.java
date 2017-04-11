package com.ucmed.common.filter;

import java.util.List;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface FilterChainFactory {
    FilterChain getFilterChain(List<ApiExecFilter> var1);
}
