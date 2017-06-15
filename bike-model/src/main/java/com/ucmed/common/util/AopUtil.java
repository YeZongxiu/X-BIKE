package com.ucmed.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Aop工具类
 * @ClassName AopUtil
 * @Description
 * @author HJH
 * @date 2017年2月9日 下午3:07:22
 */
public class AopUtil {
    /**
     * 过滤url中包含的id
     * @Description
     * @param url
     * @return
     */
    public static String filterId(String url) {
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()) {
            int length = matcher.group().length() + 1;
            url = url.substring(0, url.length() - length);
        }
        return url;
    }

    /**
     * 根据key获取参数
     * @Description
     * @param key
     * @param names
     * @param values
     * @return
     */
    public static Object getParameter(String key, String[] names,
                                      Object[] values) throws Exception {
        if(values != null && names != null) {
            int j = 0;
            j = Math.min(names.length, values.length);
            for(int i = 0; i < j; i++) {
                String name = names[i];
                if(name.equals(key)) {
                    return values[i];
                }
            }
        }
        return null;
    }
}
