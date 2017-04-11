package com.ucmed.common.service.invoker;

import com.ucmed.common.invoker.*;
import com.ucmed.common.util.ApplicationContextUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ucmed on 2017/3/16.
 */
public class LocalServiceInvoker implements ServiceInvoker {
    private static final Logger LOG = Logger.getLogger("localServiceInvoker");

    public LocalServiceInvoker() {
    }

    public ServiceResult doService(Service service) throws NoSuchServiceException, NoSuchServiceMethodException {
        String name = service.getService();
        if(StringUtils.isBlank(name)) {
            throw new NoSuchServiceException("service name can\'t be blank");
        } else {
            String method = service.getMethod();
            if(StringUtils.isBlank(method)) {
                throw new NoSuchServiceMethodException("service\'s method name can\'t be blank");
            } else {
                Object obj = ApplicationContextUtil.getBean(name);
                if(obj == null) {
                    throw new NoSuchServiceException("No such service configured named: " + name + ", check your configure file");
                } else {
                    Method m = null;
                    ServiceResult sr = new ServiceResult();

                    try {
                        if(service.getParams() == null) {
                            m = obj.getClass().getMethod(method, new Class[0]);
                            sr.setParams((JSONObject)m.invoke(obj, new Object[0]));
                            sr.setCode(Integer.valueOf(1));
                        } else {
                            m = obj.getClass().getMethod(method, new Class[]{JSONObject.class});
                            sr.setParams((JSONObject)m.invoke(obj, new Object[]{service.getParams()}));
                            sr.setCode(Integer.valueOf(1));
                        }

                        return sr;
                    } catch (SecurityException var8) {
                        throw new NoSuchServiceMethodException("service(" + method + ") method named " + name + " no permission with this params");
                    } catch (NoSuchMethodException var9) {
                        throw new NoSuchServiceMethodException("service(" + method + ") no such method named " + name + " with this params");
                    } catch (IllegalArgumentException var10) {
                        sr.setCode(Integer.valueOf(2));
                        sr.setErrInfo("the service method has illegal arguments");
                        LOG.error("", var10);
                        return sr;
                    } catch (IllegalAccessException var11) {
                        sr.setCode(Integer.valueOf(3));
                        sr.setErrInfo("the service method has no permission to call");
                        LOG.error("", var11);
                        return sr;
                    } catch (InvocationTargetException var12) {
                        sr.setCode(Integer.valueOf(4));
                        sr.setErrInfo("the service method cause an exception, " + var12.getLocalizedMessage());
                        LOG.error("", var12);
                        return sr;
                    }
                }
            }
        }
    }
}

