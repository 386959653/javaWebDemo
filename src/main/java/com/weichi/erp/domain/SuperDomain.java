package com.weichi.erp.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.weichi.erp.component.fastjson.LongToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 * Created by Wewon on 2018/6/7.
 */
public class SuperDomain<T extends Model> extends Model<T> {
    @JSONField(serializeUsing = LongToStringSerializer.class)
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private String insertUsername;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUsername;

    public String getInsertUsername() {
        return insertUsername;
    }

    public void setInsertUsername(String insertUsername) {
        this.insertUsername = insertUsername;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insetTime) {
        this.insertTime = insetTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
