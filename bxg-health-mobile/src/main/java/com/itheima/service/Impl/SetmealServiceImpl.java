package com.itheima.service.Impl;

import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public List<Setmeal> getSetmeal() {
        return setmealMapper.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }
}
