package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息, 用户对应角色与权限
     */
    User findByUsername(String username);
}