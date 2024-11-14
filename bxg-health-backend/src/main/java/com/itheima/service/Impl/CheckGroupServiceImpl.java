package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupMapper.pageQuery(queryPageBean);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询所有检查组
     *
     * @param
     * @returnList<CheckGroup>
     */
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroups = checkGroupMapper.findAll();
        return checkGroups;
    }

    /**
     * 查询检查组
     *
     * @param id
     * @return CheckGroup
     */
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupMapper.findById(id);
    }

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Integer id) {
        checkGroupMapper.deleteById(id);
    }

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */

    @Transactional
    public void add(CheckGroup checkGroup, String checkItemIds) {
        // Insert CheckGroup and get generated ID
        checkGroupMapper.add(checkGroup);

        // Parse checkItemIds and perform batch insertion for associations
        List<Integer> checkitemIdList = parseCheckItemIds(checkItemIds);
        if (checkitemIdList != null && checkitemIdList.size() > 0) {
            checkGroupMapper.addCheckItemsBatch(checkGroup.getId(), checkitemIdList);
        }
    }


    // Helper method to parse checkItemIds from comma-separated string to List<Integer>
    private List<Integer> parseCheckItemIds(String checkItemIds) {
        return Arrays.stream(checkItemIds.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * 编辑检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @Transactional
    public void edit(CheckGroup checkGroup, String checkItemIds) {
        checkGroupMapper.edit(checkGroup);  // Update CheckGroup details
        Integer checkGroupId = checkGroup.getId();

        // Delete existing associations
        checkGroupMapper.deleteCheckItemByCheckGroupId(checkGroupId);

        // Parse checkItemIds and perform batch insertion
        List<Integer> checkitemIdList = parseCheckItemIds(checkItemIds);
        checkGroupMapper.addCheckItemsBatch(checkGroupId, checkitemIdList);
    }


    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }


}
