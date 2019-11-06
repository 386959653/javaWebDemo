package com.weichi.erp.component.init;

import com.weichi.erp.service.SysConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Bean被实例化以后立马被调用，在项目启动前加载自定义系统参数
 * Created by Wewon on 2018/11/9.
 */
@Component
public class MyApplicationRunner implements InitializingBean {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void afterPropertiesSet() throws Exception {
        sysConfigService.refreshSysConfig();
    }
}
