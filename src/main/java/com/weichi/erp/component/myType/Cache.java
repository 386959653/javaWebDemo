package com.weichi.erp.component.myType;

import java.util.Map;

/**
 * Created by Wewon on 2018/11/9.
 */
public class Cache {


    private static Map<String, Object> sysConfigMap;

    public static Map<String, Object> getSysConfigMap() {
        return sysConfigMap;
    }

    public static void setSysConfigMap(Map<String, Object> sysConfigMap) {
        Cache.sysConfigMap = sysConfigMap;
    }

}
