package com.weichi.erp.component.springSecurity;

import com.google.code.kaptcha.Constants;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Wewon on 2018/10/19.
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private String imageCode;

    private String session_imageCode;

    private long session_imageTime;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = request.getParameter("verifyCode");
        this.session_imageCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        long session_verifyTime = (long) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_DATE);
        this.session_imageTime = session_verifyTime;

    }

    public String getImageCode() {
        return imageCode;
    }

    public String getSession_imageCode() {
        return session_imageCode;
    }

    public long getSession_imageTime() {
        return session_imageTime;
    }
}
