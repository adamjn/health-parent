package com.itheima.service;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserServiceImp implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库获取用户信息
        com.itheima.pojo.User user = userService.findByUsername(username);
        if (user == null) {
            //用户名不存在
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            //遍历角色集合，为用户授予角色
            list.add( new SimpleGrantedAuthority( role.getKeyword() ) );
            Set<Permission> permissions = role.getPermissions();

            for (Permission permission : permissions) {
                //遍历权限集合，为用户授予权限
                list.add( new SimpleGrantedAuthority( permission.getKeyword() ) );
            }
        }
        return new org.springframework.security.core.userdetails.User( username, user.getPassword(), list );
    }
}