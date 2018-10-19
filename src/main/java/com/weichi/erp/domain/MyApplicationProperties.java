package com.weichi.erp.domain;

/**
 * 注入application.properties中自定义的配置
 * Created by Wewon on 2018/10/19.
 */
public class MyApplicationProperties {
    //    默认验证码3分钟超时
    Long captchaOverTime = 3 * 60L;

    public Long getCaptchaOverTime() {
        return captchaOverTime;
    }

    public void setCaptchaOverTime(Long captchaOverTime) {
        this.captchaOverTime = captchaOverTime;
    }

}
