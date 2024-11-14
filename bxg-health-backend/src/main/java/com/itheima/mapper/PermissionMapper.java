package com.itheima.mapper;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface PermissionMapper {
    @Select("SELECT p.* FROM t_permission p, t_role_permission rp WHERE p.id = rp.permission_id and rp.role_id=#{roleId}")
    Set<Permission> findByRoleId(Integer roleId);
}
