package com.weichi.erp.service;

import com.baomidou.mybatisplus.service.IService;
import com.weichi.erp.domain.SysConfig;

/**
 * Created by Wewon on 2019-01-18.
 */
public interface SysConfigService extends IService<SysConfig> {
    void refreshSysConfig();
}
