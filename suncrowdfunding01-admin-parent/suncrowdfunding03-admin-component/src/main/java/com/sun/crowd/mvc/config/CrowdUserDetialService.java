package com.sun.crowd.mvc.config;

import com.sun.crowd.entity.Admin;
import com.sun.crowd.entity.Auth;
import com.sun.crowd.entity.Role;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.service.api.AuthService;
import com.sun.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetialService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 1.  根据账号名称查询admin对象
        Admin admin = adminService.getAdminByLoginAcct(s);

        // 2. 获取adminId
        Integer adminId = admin.getId();

        // 3. 根据adminId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 4. 根据adminId查询权限信息
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(adminId);

        // 5. 创建集合对象来存储GrantAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 6. 遍历assRoleList 传入角色信息
        for (Role role:assignedRoleList){
            // 注意不要忘了加上Role的前缀
            String roleName="ROLE_"+role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 7. 遍历authnames存入权限信息
        for (String authName :authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 8. 封装入SeurityAdmin 对象
        return new SecurityAdmin(admin, authorities);

    }
}
