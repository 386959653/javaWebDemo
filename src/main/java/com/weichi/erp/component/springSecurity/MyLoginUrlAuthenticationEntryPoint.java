package com.weichi.erp.component.springSecurity;

import com.weichi.erp.component.myType.JsonResult;
import com.weichi.erp.component.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Wewon on 2018/7/10.
 */
public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (WebUtils.isAjaxRequest(request)) {
            JsonResult<?> jsonResult = JsonResult.needLoginJsonResult();
            WebUtils.renderJsonResult(response, jsonResult);
        } else {
            request.getRequestDispatcher(getLoginFormUrl()).forward(request, response);
        }
    }

}
