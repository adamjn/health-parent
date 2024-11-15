package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    void add(Setmeal setmeal, String checkgroupIds);


    /**
     * 编辑套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    void edit(Setmeal setmeal, String checkgroupIds);


    List<Map<String, Object>> findSetmealCount();


}
