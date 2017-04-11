package com.ucmed.common.invoker;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface ServiceInvoker {
    ServiceResult doService(Service var1) throws NoSuchServiceException, NoSuchServiceMethodException;
}