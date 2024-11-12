package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}
