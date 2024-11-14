package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */

    public void add(CheckItem checkItem) {

        checkItemMapper.insert(checkItem);
    }

    /**
     * 按id删除检查项
     *
     * @param id
     * @return
     */
    @Override
    public void deleteById(Integer id) {
        //查询当前检查项是否和检查组关联
        long count = checkItemMapper.findCountByCheckItemId(id);
        if (count > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }

        checkItemMapper.deleteById(id);
    }

    /**
     * 查询所有检查项目
     *
     * @param
     * @return List<CheckItem>
     */
    @Override
    public List<CheckItem> findAll() {

        List<CheckItem> checkItems = checkItemMapper.findAll();

        return checkItems;
    }

    /**
     * 编辑检查项
     *
     * @param checkItem
     * @return
     */
    @Override
    public void edit(CheckItem checkItem) {

        checkItemMapper.update(checkItem);
    }


    /**
     * 分页查询检查项目
     *
     * @param
     * @return PageResult
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemMapper.pageQuery(queryPageBean);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询检查项目
     *
     * @param id
     * @return CheckItem
     */
    @Override
    public CheckItem findById(Integer id) {

        return checkItemMapper.findById(id);
    }

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {

        return checkItemMapper.findCheckItemIdsByCheckGroupId(checkGroupId);
    }
}
