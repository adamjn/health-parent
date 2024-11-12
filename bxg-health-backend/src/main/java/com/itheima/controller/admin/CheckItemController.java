package com.itheima.controller.admin;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/checkitem")
@Slf4j
public class CheckItemController {
    @Autowired
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        log.info("新增检查项目: {}", checkItem);
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 按id删除检查项
     *
     * @param id
     * @return
     */
    //为什么pathvariable不能用delete方法？
    @GetMapping("/delete")
    public Result deleteById(@RequestParam Integer id) {
        log.info("删除检查项目: {}", id);
        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS, id);
    }

    /**
     * 编辑检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {   //编辑检查项成功
        log.info("编辑检查项成功: {}", checkItem);
        checkItemService.edit(checkItem);
        return new Result(true, "编辑检查项目成功", checkItem);
    }


    /**
     * 查询所有检查项目
     *
     * @param
     * @return List<CheckItem>
     */
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("查询所有检查项目");
        List<CheckItem> checkItems = checkItemService.findAll();
        return new Result(true, "查询所有检查项目成功", checkItems);
    }

    /**
     * 分页查询检查项目
     *
     * @param
     * @return PageResult
     */
    @PostMapping("/findPage")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {
        log.info("分页查询检查项目: {}", queryPageBean);
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 根据id查询检查项目
     *
     * @param id
     * @return CheckItem
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id) {
        log.info("查询检查项目: {}", id);
        return new Result(true, "查询检查项目成功", checkItemService.findById(id));
    }

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("checkgroupId") Integer checkGroupId) {
        log.info("查询检查项id: {}", checkGroupId);

        return new Result(true, "查询检查项id成功", checkItemService.findCheckItemIdsByCheckGroupId(checkGroupId));
    }

}
