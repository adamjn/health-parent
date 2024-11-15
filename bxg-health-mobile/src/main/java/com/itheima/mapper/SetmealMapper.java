package com.itheima.mapper;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select * from t_setmeal")
    List<Setmeal> getSetmeal();
    void add(Setmeal setmeal);

    Setmeal findById(Integer id);
}
