package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select * from t_setmeal")
    List<Setmeal> getSetmeal();

    @Select("select * from t_setmeal where id = #{id}")
    Setmeal findById(Integer id);
}