package com.specflow.common;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    private final int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
