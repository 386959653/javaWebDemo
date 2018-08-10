package com.weichi.erp.component.mybatis;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.weichi.erp.component.springSecurity.MyUserDetails;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * 注入公共字段自动填充
 * Created by Wewon on 2018/6/14.
 */
public class MyMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Object insertUsername = getFieldValByName("insertUsername", metaObject);
        if (insertUsername == null) {
            setFieldValByName("insertUsername", userDetails.getUsername(), metaObject);
        }

        Object insertTime = getFieldValByName("insertTime", metaObject);
        if (insertTime == null) {
            setFieldValByName("insertTime", new Date(), metaObject);
        }

        Object updateUsername = getFieldValByName("updateUsername", metaObject);
        if (updateUsername == null) {
            setFieldValByName("updateUsername", userDetails.getUsername(), metaObject);
        }

        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }

    }

    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Object updateUsername = getFieldValByName("updateUsername", metaObject);
//        if (updateUsername == null) {
            setFieldValByName("updateUsername", userDetails.getUsername(), metaObject);
//        }

        Object updateTime = getFieldValByName("updateTime", metaObject);
//        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
//        }
    }
}
