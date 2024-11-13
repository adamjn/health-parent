package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    /**
     * 查询套餐数据
     * @return
     */
    @PostMapping("/getSetmeal")
    public Result getSetmeal(){
        log.info("查询套餐数据");
        try {
            List<com.itheima.pojo.Setmeal> lists = setmealService.getSetmeal();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, lists);
        }catch (Exception e)   {
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }

    }

    @PostMapping("/findById")
    public Result findById(@RequestParam("id") Integer id) {
        log.info("根据id查询套餐数据: {}", id);
        try {
            setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.findById(id));

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
