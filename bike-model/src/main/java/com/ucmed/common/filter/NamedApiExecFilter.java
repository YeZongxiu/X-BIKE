package com.ucmed.common.filter;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface NamedApiExecFilter extends ApiExecExtraFilter {
    String[] getApiNames();
}
