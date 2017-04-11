package com.ucmed.common.util;

import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ApplicationContextUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextUtil.class);
    private static ApplicationContext appContext = null;

    public ApplicationContextUtil() {
    }

    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        appContext = appContext;
        LOG.debug("AppContext set application context for spring");
    }

    public static <T> T getBean(String beanId, Class<T> type) {
        if(appContext != null) {
            return appContext.getBean(beanId, type);
        } else {
            LOG.warn("appContext is null");
            return null;
        }
    }

    public static Object getBean(String beanId) {
        if(appContext != null) {
            return appContext.getBean(beanId);
        } else {
            LOG.warn("appContext is null");
            return null;
        }
    }

    public static void setAppContextBySpringUtil(ServletContext context) {
        appContext = WebApplicationContextUtils.getWebApplicationContext(context);
    }
}
