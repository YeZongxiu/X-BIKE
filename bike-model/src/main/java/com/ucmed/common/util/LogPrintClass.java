package com.ucmed.common.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 日志记录类-用于记录每次请求的参数以及响应数据
 * @author Billy
 * @date 2016/05/09
 */

public class LogPrintClass {

    /** 日志打印对象 */
    Logger API_LOG = Logger.getLogger(LogPrintClass.class);

//    @Autowired
//    private LogSysUtil logSysUtil;

    public Object logApi(ProceedingJoinPoint joinPoint) {
        Object res = null;
        JSONObject pms = new JSONObject();
        try {
            API_LOG.info("====================================================================================");
            MethodSignature signature = (MethodSignature) joinPoint
                    .getSignature();
            Method method = signature.getMethod();
            String[] names = signature.getParameterNames();
            Object[] values = joinPoint.getArgs();
            String apiName = method.getName();
            String url = "";

            
            API_LOG.info("接口方法名称: " + apiName);
            API_LOG.info("接口请求入参: ");
            if(values != null && names != null) {
                Class<?>[] types = method.getParameterTypes();
                int j = 0;
                j = Math.min(names.length, types.length);
                j = Math.min(j, values.length);
                for(int i = 0; i < j; i++) {
                    String key = types[i].getSimpleName() + " " + names[i];
                    if(!(values[i] instanceof HttpServletRequest)
                            && !(values[i] instanceof javax.servlet.http.HttpServletResponse)
                            && !(values[i] instanceof org.springframework.web.multipart.commons.CommonsMultipartFile)) {
                        pms.put(key, values[i]);
                    }
                    if(values[i] instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) values[i];
                        url = AopUtil.filterId(request
                                .getRequestURI());
                    }
                }
                API_LOG.info(pms.toString());
            }
//            Long startTime = System.currentTimeMillis();
            res = joinPoint.proceed();
//            Long endTime = System.currentTimeMillis();
//            Long costTime = endTime - startTime;
            if(res instanceof ResponseEntity<?>) {
                ResponseEntity<?> temp = (ResponseEntity<?>) res;
//                String resStr;
                if(null == temp.getBody()) {
                    API_LOG.info("接口返回状态码:" + temp.getStatusCode());
                    API_LOG.info("接口返回出参 :为null");
                } else {
                    String ret = JSON.toJSONString(temp.getBody());
                    API_LOG.info("接口返回状态码:" + temp.getStatusCode());
                    API_LOG.info("接口返回出参 :" + ret);
//                    resStr = ret;
                }
//                logSysUtil.addLogSys(url, apiName, costTime,
//                        pms.toString(), resStr, temp.getStatusCode());
            }
            API_LOG.info("====================================================================================");
        } catch(Throwable e) {
            API_LOG.error("LogPrintClass 出错,信息如下:" + e.getMessage(), e);
        }
        return res;
    }
}
