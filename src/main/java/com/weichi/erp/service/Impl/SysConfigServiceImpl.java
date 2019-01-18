package com.weichi.erp.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.weichi.erp.component.myType.Cache;
import com.weichi.erp.dao.SysConfigMapper;
import com.weichi.erp.domain.SysConfig;
import com.weichi.erp.service.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wewon on 2019-01-18.
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Override
    public void refreshSysConfig() {
        SysConfig sysConfig = new SysConfig();
        List<SysConfig> sysConfigList = sysConfig.selectAll();
        Map<String, Object> sysConfigMap = new HashMap<>();
        for (SysConfig s : sysConfigList) {
            sysConfigMap.put(s.getParmName(), s.getParmValue());
        }
        Cache.setSysConfigMap(sysConfigMap);
    }
}
