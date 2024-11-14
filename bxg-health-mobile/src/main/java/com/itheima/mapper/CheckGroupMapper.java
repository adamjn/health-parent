package com.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckGroupMapper {
    void findCheckGroupById(Integer id);

}
