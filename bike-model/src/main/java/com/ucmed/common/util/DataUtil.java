package com.ucmed.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 参数获取工具类.
 */
public class DataUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DataUtil.class);

    public static Integer getIntegerValue(Map<String, Object> params,
            String key, Integer defaultValue) {
        try {
            return ((Number) params.get(key)).intValue();
        } catch(Exception e) {
            LOG.warn("Parse Integer value failed.");
        }
        return defaultValue;
    }

    public static Long getLongValue(Map<String, Object> params, String key,
            Long defaultValue) {
        try {
            if(params.get(key) == null) {
                return defaultValue;
            } else {
                String valueStr = params.get(key).toString().trim();
                return Long.parseLong(valueStr);
            }
        } catch(Exception e) {
            LOG.warn("Parse Long value failed.");
        }
        return defaultValue;
    }

    public static Long getLongValue(Map<String, Object> params, String key) {
        return getLongValue(params, key, null);
    }

    public static Long getLongValue(HttpServletRequest request, String key,
            Long defaultValue) {
        try {
            String p = request.getParameter(key);
            return Long.parseLong(p);
        } catch(Exception e) {
            LOG.warn("Parse Long value failed.");
        }
        return defaultValue;
    }

    public static Integer getIntegerValue(HttpServletRequest request,
            String key, Integer defaultValue) {
        try {
            String p = request.getParameter(key);
            return Integer.parseInt(p);
        } catch(Exception e) {
            LOG.warn("Parse Integer value failed.");
        }
        return defaultValue;
    }

    public static Long getLongValue(HttpServletRequest request, String key) {
        return getLongValue(request, key, null);
    }

    public static Integer getIntegerValue(HttpServletRequest request, String key) {
        return getIntegerValue(request, key, null);
    }

    /**
     * 从参数表中取出字符串类型的参数值，最后的值为空时，返回指定的默认值.
     * 
     * @param params
     *            参数表
     * @param key
     *            键值
     * @param defaultValue
     *            默认值
     * @return 键对应的值；如果为空，则返回默认值。
     */
    public static String getStringValue(Map<String, Object> params, String key,
            String defaultValue) {
        if(params == null || key == null) {
            return defaultValue;
        }
        String result = params.get(key) == null ? null : params.get(key)
                .toString().trim();
        return result == null ? defaultValue : result;
    }

    /**
     * 从参数表中取出字符串类型的参数值，如果参数表中不存在对应的键，则返回空.
     * 
     * @param params
     *            参数表
     * @param key
     *            键
     * @return 键对应的值或者空。
     */
    public static String getStringValue(Map<String, Object> params, String key) {
        return getStringValue(params, key, null);
    }

    /**
     * 取得整型参数值，如不存在则返回 null.
     * 
     * @param params
     *            参数表.
     * @param key
     *            键值.
     * @return 对应的值，或者 null.
     */
    public static Integer getIntegerValue(Map<String, Object> params, String key) {
        return getIntegerValue(params, key, null);
    }

    public static Long getLongValue2(Map<String, String> params, String key) {
        return getLongValue2(params, key, null);
    }
    
    public static Long getLongValue2(Map<String, String> params, String key,
            Long defaultValue) {
        try {
            if(params.get(key) == null) {
                return defaultValue;
            } else {
                String valueStr = params.get(key).trim();
                return Long.parseLong(valueStr);
            }
        } catch(Exception e) {
            LOG.warn("Parse Long value failed.");
        }
        return defaultValue;
    }
}
