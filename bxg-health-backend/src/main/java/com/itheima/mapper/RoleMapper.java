package com.itheima.mapper;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface RoleMapper {
    @Select("SELECT r.* FROM t_role r, t_user_role ur WHERE r.id = ur.role_id and ur.user_id=#{id}")
    Set<Role> findByUserId(Integer id);
}
