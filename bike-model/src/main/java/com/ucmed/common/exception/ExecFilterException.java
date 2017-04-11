package com.ucmed.common.exception;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ExecFilterException extends Exception {
    private static final long serialVersionUID = 5535407244683737631L;
    private int code;

    public ExecFilterException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}