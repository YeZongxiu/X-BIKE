package com.ucmed.common.invoker;

/**
 * Created by ucmed on 2017/3/15.
 */
public class NoSuchServiceMethodException extends Exception {
    private static final long serialVersionUID = 1308109356667776153L;

    public NoSuchServiceMethodException(String msg) {
        super(msg);
    }
}