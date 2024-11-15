package com.itheima.service.Impl;


import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import java.util.List;


@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}") // 从属性文件读取输出目录的路径
    private String outputPath;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealMapper.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }





}

