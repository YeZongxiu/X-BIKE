package com.ucmed.common.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.ucmed.common.invoker.Service;
import com.ucmed.common.invoker.ServiceInvoker;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ServiceUtil {

    private static final Logger LOG = Logger.getLogger(ServiceUtil.class);

    private static ServiceInvoker serviceInvoker;

    @SuppressWarnings("static-access")
    public void setServiceInvoker(ServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }

    public static JSONObject doService(String className, String methodName, JSONObject params) {
        serviceInvoker = ApplicationContextUtil.getBean("serviceInvoker", ServiceInvoker.class);
        Service s = new Service();
        s.setMethod(methodName);
        s.setService(className);
        if(params != null) {
            s.setParams(params);
        }
        try {
            JSONObject res = serviceInvoker.doService(s).getParams();
            if(res != null) {
                return res;
            }
        } catch(Exception e) {
            LOG.error("未知错误", e);
        }
        return null;
    }
}
