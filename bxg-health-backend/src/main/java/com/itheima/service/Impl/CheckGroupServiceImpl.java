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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param  id
     * @return CheckGroup
     */
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupMapper.findById(id);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @Override
    public void deleteById(Integer id) {
        checkGroupMapper.deleteById(id);
    }

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */

    @Transactional
    public void add(CheckGroup checkGroup,  String checkItemIds) {
        checkGroupMapper.add(checkGroup);
        List<Integer> checkitemIdList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkItemIds.split(",")) {
            checkitemIdList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkItemId : checkitemIdList) {

            checkGroupMapper.addCheckItem(checkGroup.getId(), checkItemId);
        }
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @Transactional
    public void edit(CheckGroup checkGroup, String checkItemIds) {
        checkGroupMapper.edit(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        // 删除原有关联关系
        checkGroupMapper.deleteCheckItemByCheckGroupId(checkGroupId);
        List<Integer> checkitemIdList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkItemIds.split(",")) {
            checkitemIdList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkItemId : checkitemIdList) {

            checkGroupMapper.addCheckItem(checkGroupId, checkItemId);
        }
    }

    //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupMapper.setCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }
}
