package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = setmealMapper.pageQuery(queryPageBean);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Transactional
    public void add(Setmeal setmeal, String checkgroupIds) {
        setmealMapper.add(setmeal);


        List<Integer> checkgroupIdsList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkgroupIds.split(",")) {
            checkgroupIdsList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkgroupId : checkgroupIdsList) {

            setmealMapper.addCheckGroup(setmeal.getId(), checkgroupId);
        }
    }


    /**
     * 编辑套餐
     *  1.先删除原有的检查项
     *  2.再新增新的检查项
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    public void edit(Setmeal setmeal, String checkgroupIds) {


        setmealMapper.edit(setmeal);
        Integer setmealId = setmeal.getId();
        // 删除原有关联关系
        setmealMapper.deleteCheckItemByCheckGroupId(setmealId);
        List<Integer> checkgroupIdList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkgroupIds.split(",")) {
            checkgroupIdList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkItemId : checkgroupIdList) {

            setmealMapper.addSetmealCheckGroup(setmealId, checkItemId);
        }
    }


    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealMapper.findSetmealCount();
    }
}

