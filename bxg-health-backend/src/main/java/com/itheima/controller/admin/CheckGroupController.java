package com.itheima.controller.admin;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.pojo.CheckGroup;

import com.itheima.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
@Slf4j
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {
        log.info("分页查询检查组: {}", queryPageBean);
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 查询所有检查组
     * @param
     * @returnList<CheckGroup>
     */
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("查询所有检查组");
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        return new Result(true, "查询所有检查组成功", checkGroups);
    }

    /**
     * 查询检查组
     * @param  id
     * @return CheckGroup
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id) {
        log.info("查询检查组: {}", id);
        return new Result(true, "查询检查组成功", checkGroupService.findById(id));
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam Integer id) {
        log.info("删除检查组: {}", id);
        checkGroupService.deleteById(id);
        return new Result(true, "删除检查组成功", id);
    }

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") String checkItemIds) {
        log.info("新增检查组: {}", checkGroup, "检查项: {}", checkItemIds);
        checkGroupService.add(checkGroup, checkItemIds);
        return new Result(true, "新增检查组成功");
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") String checkItemIds) {   //编辑检查项成功
        log.info("编辑检查组成功: {}", checkGroup);
        checkGroupService.edit(checkGroup, checkItemIds);
        return new Result(true, "编辑检查项目成功");
    }

}
