package com.weichi.erp.component.init;

import com.weichi.erp.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目启动后加载自定义系统参数
 * Created by Wewon on 2018/11/9.
 */
@Component
//这里通过设定value的值来指定执行顺序
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        sysConfigService.refreshSysConfig();
    }
}
