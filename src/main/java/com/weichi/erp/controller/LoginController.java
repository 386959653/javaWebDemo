package com.weichi.erp.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.weichi.erp.component.myType.JsonResult;
import com.weichi.erp.component.springSecurity.CustomWebAuthenticationDetails;
import com.weichi.erp.component.utils.StringUtils;
import com.weichi.erp.domain.MyApplicationProperties;
import com.weichi.erp.domain.SysUser;
import com.weichi.erp.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by Wewon on 2018/5/29 11:06
 */
@Controller
public class LoginController {
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    private MyApplicationProperties myApplicationProperties;
    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String freemarker(@RequestParam(value = "error", required = false) String error, Map<String, Object> map) {
        if (error != null) {
            map.put("error", error);
        }
        return "login";
    }

    @RequestMapping("/showRegisterPage")
    public String showRegisterPage(Map<String, Object> map) {
        map.put("activeFlag", "showRegisterPage");
        return "register";
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, HttpSession httpSession, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                           @RequestParam(value = "verifyCode") String verifyCode, Map<String, Object> map) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        map.put("activeFlag", "showRegisterPage");
        // 校验用户名是否为email格式
        if (!StringUtils.isEmail(username)) {
            map.put("error", "请填写正确的邮箱格式！");
            return "register";
        }
        //校验用户名是否已存在
        int usernameCount = sysUser.selectCount(Condition.create().eq("username", username));
        if (usernameCount > 0) {
            map.put("error", "用户名已存在！");
            return "register";
        }
// 校验验证码
        String captchaId = (String) httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!verifyCode.equalsIgnoreCase(captchaId)) {
            httpSession.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            map.put("captchaError", "captchaError");
            return "register";
        }

        // 校验都通过，写入数据库
        sysUser.setPassword(password);
        sysUser.setInsertUsername("registerUser");
        sysUser.setUpdateUsername("registerUser");
        sysUser.insert();
        UserRole userRole = new UserRole();
        // 2是普通用户角色
        userRole.setRoleId(2L);
        userRole.setUserId(sysUser.getId());
        userRole.setInsertUsername("registerUser");
        userRole.setUpdateUsername("registerUser");
        userRole.insert();

        // 使注册用户处于登录状态
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new CustomWebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return "forward:/index";
    }

    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
            httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date().getTime());
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequestMapping("/verifyCodeCheck")
    @ResponseBody
    public JsonResult<?> checkVerifyCode(HttpSession httpSession, @RequestBody String verifyCode) {
        JsonResult jsonResult = new JsonResult();
        try {
            String captchaId = (String) httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            long session_imageTime = (long) httpSession.getAttribute(Constants.KAPTCHA_SESSION_DATE);
            long nowTime = System.currentTimeMillis();
            if (!verifyCode.equalsIgnoreCase(captchaId)) {
                jsonResult.setMessage("验证码错误");
                httpSession.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                httpSession.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
            } else if ((nowTime - session_imageTime) / 1000 > myApplicationProperties.getCaptchaOverTime()) {//超时
                jsonResult.setMessage("验证码已超时");
                httpSession.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                httpSession.removeAttribute(Constants.KAPTCHA_SESSION_DATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setStatus(JsonResult.ERROR);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }


}
