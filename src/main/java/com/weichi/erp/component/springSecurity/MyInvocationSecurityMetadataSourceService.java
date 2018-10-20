package com.weichi.erp.component.springSecurity;

import com.weichi.erp.dao.SysPermissionMapper;
import com.weichi.erp.domain.SysPermission;
import com.weichi.erp.domain.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Wewon on 2018/6/9.
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限对应的角色
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<SysPermission> permissions = new SysPermission().selectAll();
        for (SysPermission permission : permissions) {
            array = new ArrayList<>();
            // 根据权限从数据库里获取对应的角色
            List<Object> sysRoles = new SysRole().sql().selectObjs("SELECT t.`role_name` FROM sys_role t\n" +
                    "WHERE t.`id` in (SELECT t2.`role_id`  FROM role_permission t2 WHERE t2.`permission_id`={0})", permission.getId());
            for (Object sysRole : sysRoles) {
                cfg = new SecurityConfig(sysRole.toString());
                //此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
                array.add(cfg);
            }
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null)
            loadResourceDefine();
//        loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        Collection<ConfigAttribute> matchedRoleArray = new ArrayList<>();
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                ((ArrayList<ConfigAttribute>) matchedRoleArray).addAll(map.get(resUrl));
            }
        }
        if (CollectionUtils.isEmpty(matchedRoleArray)) {
            return null;
        } else {
            return matchedRoleArray;
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
