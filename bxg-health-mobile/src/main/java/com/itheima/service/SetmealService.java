package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
