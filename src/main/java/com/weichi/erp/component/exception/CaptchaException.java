package com.weichi.erp.component.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Wewon on 2018/9/5.
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
