package com.ucmed.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ValueUtil {
    public ValueUtil() {
    }

    public static Integer getInteger(String v, Integer d) {
        if(v == null) {
            return d;
        } else {
            try {
                return Integer.valueOf(Integer.parseInt(v));
            } catch (Exception var3) {
                return d;
            }
        }
    }

    public static boolean hasValue(Object v) {
        return v != null && StringUtils.isNotBlank(v.toString());
    }
}
