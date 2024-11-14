package com.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckItemMapper {

    void findCheckItemById(Integer id);
}
