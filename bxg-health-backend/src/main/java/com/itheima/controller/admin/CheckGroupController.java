package com.itheima.controller.admin;

import com.itheima.common.constant.MessageConstant;
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
     *
     * @param
     * @returnList<CheckGroup>
     */
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("查询所有检查组");
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        if (checkGroups != null && checkGroups.size() > 0) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkGroups);
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);

    }

    /**
     * 查询检查组
     *
     * @param id
     * @return CheckGroup
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id) {
        log.info("查询检查组: {}", id);
        CheckGroup checkGroup = checkGroupService.findById(id);
        if (checkGroup != null) {
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }


    /**
     * 删除检查组
     *
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
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") String checkItemIds) {

        log.info("新增检查组: {}", checkGroup, "检查项: {}", checkItemIds);

        try {
            checkGroupService.add(checkGroup, checkItemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    //根据检查组合id查询对应的所有检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkitemIds =
                    checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 编辑检查组
     *
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
