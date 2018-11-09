package com.weichi.erp.component.init;

import com.weichi.erp.component.myType.Cache;
import com.weichi.erp.domain.SysConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目启动后加载自定义系统参数
 * Created by Wewon on 2018/11/9.
 */
@Component
//这里通过设定value的值来指定执行顺序
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SysConfig sysConfig = new SysConfig();
        List<SysConfig> sysConfigList = sysConfig.selectAll();
        Map<String, Object> sysConfigMap = new HashMap<>();
        for (SysConfig s : sysConfigList) {
            sysConfigMap.put(s.getParmName(), s.getParmValue());
        }
        Cache.setSysConfigMap(sysConfigMap);
    }
}
