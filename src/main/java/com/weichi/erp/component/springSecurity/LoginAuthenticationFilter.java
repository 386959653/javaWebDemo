package com.weichi.erp.component.springSecurity;

import com.google.code.kaptcha.Constants;
import com.weichi.erp.component.exception.CaptchaException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wewon on 2018/9/5.
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public LoginAuthenticationFilter() {
        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String verification = request.getParameter("verifyCode");
        String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (verification == null || captcha == null || !captcha.equalsIgnoreCase(verification)) {
            request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            throw new CaptchaException("验证码错误");
        }
        return super.attemptAuthentication(request, response);
    }
}
