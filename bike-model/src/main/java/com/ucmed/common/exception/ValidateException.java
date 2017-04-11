package com.ucmed.common.exception;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ValidateException extends Exception {
    private static final long serialVersionUID = 4501339472200957400L;
    private Integer code;
    private String message;

    public ValidateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
