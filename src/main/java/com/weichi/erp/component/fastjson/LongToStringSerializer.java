package com.weichi.erp.component.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JS在处理返回数据类型是Long的时候，精度会丢失一部分，String类型就不会。
 * Created by Wewon on 2018/8/13.
 */
public class LongToStringSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        Long value = (Long) object;
        String text = value.toString();
        serializer.write(text);
    }
}
