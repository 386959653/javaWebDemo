package com.weichi.erp.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.weichi.erp.component.myType.JsonResult;
import com.weichi.erp.domain.MyApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/login")
    public String freemarker(@RequestParam(value = "error", required = false) String error, Map<String, Object> map) {
        if (error != null) {
            map.put("error", error);
        }
        return "login";
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
