package com.weichi.erp.component.springSecurity;

import com.weichi.erp.Constant.BaseEnums;
import com.weichi.erp.dao.SysRoleMapper;
import com.weichi.erp.dao.SysUserMapper;
import com.weichi.erp.dao.UserRoleMapper;
import com.weichi.erp.domain.SysRole;
import com.weichi.erp.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wewon on 2018/5/28 9:38
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysUserMapper sysUserMapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据用户名，查找到对应的密码，与角色
        SysUser sysUserCondition = new SysUser();
        //        只查找有效用户
        sysUserCondition.setEnableFlag(BaseEnums.enableFlag.Y.name());
        sysUserCondition.setUsername(s);
        SysUser sysUser = sysUserMapper.selectOne(sysUserCondition);
        if (sysUser == null) {
            throw new UsernameNotFoundException("找不到用户名");
        }
        Long roleId = (Long) sysUser.sql().selectObj("SELECT t2.`role_id`  FROM user_role t2 WHERE t2.`user_id`={0}", sysUser.getId());
        if (roleId == null) {
            throw new UsernameNotFoundException("找不到用户名对应的角色");
        }
        SysRole sysRole = new SysRole();
        sysRole = sysRole.selectById(roleId);

        List<Long> roleIdList = sysRoleMapper.listRoleIdAndParentRoleId(roleId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Long roleIdTemp : roleIdList) {
            authorities.add(new SimpleGrantedAuthority(roleIdTemp.toString()));
        }

        // 封装用户信息，并返回。参数分别是：用户id，用户名，密码，用户拥有的角色（用户本身所属角色，及子角色）
        MyUserDetails user = new MyUserDetails(sysUser.getId(), s, sysUser.getPassword(), authorities);
        user.setSysRole(sysRole);
        return user;
    }
}
