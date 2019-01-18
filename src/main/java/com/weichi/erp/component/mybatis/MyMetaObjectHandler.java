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
        Object insertUsername = getFieldValByName("insertUsername", metaObject);
        Object updateUsername = getFieldValByName("updateUsername", metaObject);
        // 当insertUsername或者updateUsername为空时候才需要自动填充这两个字段，如果这两个字段都有值，就不需要从SecurityContextHolder取用户名（用户没登录，SecurityContextHolder为空）
        if (insertUsername == null || updateUsername == null) {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            if (insertUsername == null) {
                setFieldValByName("insertUsername", userDetails.getUsername(), metaObject);
            }

            if (updateUsername == null) {
                setFieldValByName("updateUsername", userDetails.getUsername(), metaObject);
            }
        }
        Object insertTime = getFieldValByName("insertTime", metaObject);
        if (insertTime == null) {
            setFieldValByName("insertTime", new Date(), metaObject);
        }

        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }

    }

    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateUsername = getFieldValByName("updateUsername", metaObject);
//        当updateUsername为空时候才需要自动填充这个字段
        if (updateUsername == null) {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            setFieldValByName("updateUsername", userDetails.getUsername(), metaObject);
        }

        Object updateTime = getFieldValByName("updateTime", metaObject);
//        if (updateTime == null) {
        setFieldValByName("updateTime", new Date(), metaObject);
//        }
    }
}
