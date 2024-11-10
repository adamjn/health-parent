package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;


import java.util.List;

public interface CheckItemService {

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    void add(CheckItem checkItem);

    /**
     * 按id删除检查项
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /**
     * 查询所有检查项目
     * @param
     * @return List<CheckItem>
     */
    List<CheckItem> findAll();

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    void edit(CheckItem checkItem);

    /**
     * 分页查询检查项目
     * @param
     * @return PageResult
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id查询检查项目
     *
     * @param id
     * @return CheckItem
     */
    CheckItem findById(Integer id);

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
}
