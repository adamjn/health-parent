package com.itheima.service.Impl;

import com.itheima.mapper.PermissionMapper;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public User findByUsername(String username) {
        //查询用户基本信息，不包含用户的角色
        User user = userMapper.findByUsername( username );
        if (user == null) {
            return null;
        }
        //根据用户ID查询对应的角色
        Set<Role> roles = roleMapper.findByUserId( user.getId() );
        for(Role role : roles){
            Integer roleId = role.getId();
            //根据角色ID查询对应的权限
            Set<Permission> permissions = permissionMapper.findByRoleId( roleId );
            role.setPermissions( permissions );
        }
        //让用户关联角色
        user.setRoles( roles );
        return user;
    }
}