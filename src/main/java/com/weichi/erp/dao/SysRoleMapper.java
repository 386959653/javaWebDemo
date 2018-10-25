package com.weichi.erp.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.weichi.erp.domain.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<Long> listRoleIdAndParentRoleId(Long roleId);
}