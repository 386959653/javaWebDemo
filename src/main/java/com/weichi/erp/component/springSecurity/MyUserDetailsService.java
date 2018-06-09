package com.weichi.erp.component.springSecurity;

import com.weichi.erp.dao.SysRoleMapper;
import com.weichi.erp.dao.SysUserMapper;
import com.weichi.erp.dao.UserRoleMapper;
import com.weichi.erp.domain.SysRole;
import com.weichi.erp.domain.SysUser;
import com.weichi.erp.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // TODO 根据用户名，查找到对应的密码，与角色
        SysUser sysUserCondition = new SysUser();
        sysUserCondition.setUsername(s);
        SysUser sysUser = sysUserMapper.selectOne(sysUserCondition);

        List<Object> roleNameList = new SysRole().sql().selectObjs("SELECT t.`role_name` FROM sys_role t WHERE t.`id` IN (SELECT t2.`role_id`  FROM user_role t2 WHERE t2.`user_id`={0})"
                , sysUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Object roleName : roleNameList) {
            authorities.add(new SimpleGrantedAuthority(roleName.toString()));
        }

        // 封装用户信息，并返回。参数分别是：用户名，密码，用户角色
        User user = new User(s, sysUser.getPassword(), authorities);
        return user;
    }
}
