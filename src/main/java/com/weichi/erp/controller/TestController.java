package com.weichi.erp.controller;

import com.weichi.erp.component.myType.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wewon on 2018/5/15 10:21
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/helloTest")
    public String index() {
        logger.info("hello world");
//        UserGroupEnums.GroupRole.吃货.name();
        return "Greetings from Spring Boot!";
//        return service.selectById(1).toString();
    }

    //    需要普通用户权限
    @RequestMapping("jsonResultTest")
    public JsonResult<?> jsonResultTest() {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }

}
