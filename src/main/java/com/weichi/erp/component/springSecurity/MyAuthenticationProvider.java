package com.weichi.erp.component.springSecurity;

import com.weichi.erp.domain.MyApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Wewon on 2018/5/28 10:36
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userService;
    @Autowired
    private MyApplicationProperties myApplicationProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 验证码校验
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String imageCode = details.getImageCode();
        String session_imageCode = details.getSession_imageCode();
        long session_imageTime = details.getSession_imageTime();

        if (imageCode == null || session_imageCode == null) {
            throw new BadCredentialsException("验证码错误");
        }

        if (!imageCode.equals(session_imageCode)) {
            throw new BadCredentialsException("验证码错误");
        } else {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - session_imageTime) / 1000 > myApplicationProperties.getCaptchaOverTime()) { //超时
                throw new BadCredentialsException("验证码已超时");
            }
        }

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
