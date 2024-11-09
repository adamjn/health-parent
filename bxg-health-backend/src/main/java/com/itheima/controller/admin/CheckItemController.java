package com.itheima.controller.admin;

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
        checkItemService.add(checkItem);
        return new Result(true, "新增检查项目成功", checkItem);
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
        checkItemService.deleteById(id);
        return new Result(true, "删除检查项目成功", id);
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
        log.info("编辑时的 CheckItem ID: {}", checkItem.getId());
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

}
