package com.ucmed.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import com.ucmed.common.exception.ValidateException;

/**
 * Created by ucmed on 2017/3/15.
 */
public class TypeUtil {
    public TypeUtil() {
    }

    public static boolean isValidBaseType(Object value, String type) {
        try {
            Class c = Class.forName(type);
            if(c.isInstance(value)) {
                return true;
            }

            Constructor con = c.getConstructor(new Class[]{String.class});
            con.newInstance(new Object[]{value.toString()});
            return true;
        } catch (ClassNotFoundException var4) {
            ;
        } catch (SecurityException var5) {
            ;
        } catch (NoSuchMethodException var6) {
            ;
        } catch (IllegalArgumentException var7) {
            ;
        } catch (InstantiationException var8) {
            ;
        } catch (IllegalAccessException var9) {
            ;
        } catch (InvocationTargetException var10) {
            ;
        }

        return false;
    }

    public static Object getValue(String type, String value) throws ValidateException {
        try {
            Class c = Class.forName(type);
            Constructor con = c.getConstructor(new Class[]{String.class});
            return con.newInstance(new Object[]{value});
        } catch (ClassNotFoundException var4) {
            ;
        } catch (SecurityException var5) {
            ;
        } catch (NoSuchMethodException var6) {
            ;
        } catch (IllegalArgumentException var7) {
            ;
        } catch (InstantiationException var8) {
            ;
        } catch (IllegalAccessException var9) {
            ;
        } catch (InvocationTargetException var10) {
            ;
        }

        throw new ValidateException(Integer.valueOf(4), "api definition is not valid, can not get value for type(" + type + ") from " + value);
    }

    public static Object getValidValue(Object value, String type) throws ValidateException {
        try {
            Class c = Class.forName(type);
            if(c.isInstance(value)) {
                return value;
            }

            Constructor con = c.getConstructor(new Class[]{String.class});
            return con.newInstance(new Object[]{value.toString()});
        } catch (ClassNotFoundException var4) {
            ;
        } catch (SecurityException var5) {
            ;
        } catch (NoSuchMethodException var6) {
            ;
        } catch (IllegalArgumentException var7) {
            ;
        } catch (InstantiationException var8) {
            ;
        } catch (IllegalAccessException var9) {
            ;
        } catch (InvocationTargetException var10) {
            ;
        }

        throw new ValidateException(Integer.valueOf(4), "api param type error or api definition is not valid, type: " + type + "  value: " + value);
    }
}
