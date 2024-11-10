package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;


import java.util.List;

public interface CheckGroupService {
    /**
     * 分页查询检查项目
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 查询所有检查组
     *
     * @param
     * @returnList<CheckGroup>
     */
    List<CheckGroup> findAll();

    /**
     * 查询检查组
     *
     * @param id
     * @return CheckGroup
     */
    CheckGroup findById(Integer id);

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    void add(CheckGroup checkGroup,  String checkItemIds);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    void edit(CheckGroup checkGroup, String checkItemIds);
}
