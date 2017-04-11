package com.ucmed.common.invoker;

/**
 * Created by ucmed on 2017/3/15.
 */
public class NoSuchServiceException extends Exception {
    private static final long serialVersionUID = 3561538709642300427L;

    public NoSuchServiceException(String msg) {
        super(msg);
    }
}
