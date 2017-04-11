package com.ucmed.common.exception;

/**
 * 未登录异常.
 */
public class NoLoginApiException extends ValidateException {

    private static final long serialVersionUID = 6323473112111171900L;

    public NoLoginApiException(Integer code, String message) {
        super(code, message);
    }
}
