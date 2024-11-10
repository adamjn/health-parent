package com.itheima.controller.admin;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.AliOssUtil;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {
        log.info("分页查询套餐: {}", queryPageBean);
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id) {
        log.info("查询套餐: {}", id);
        return new Result(true, "查询套餐成功", setmealService.findById(id));
    }

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") String checkgroupIds) {
        log.info("新增套餐: {}", setmeal, "检查组: {}", checkgroupIds);
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, "新增套餐成功");
    }

    /**
     * 编辑套餐
     * 1.先删除原有的检查项
     * 2.再新增新的检查项
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal, @RequestParam("checkGroupIds") String checkgroupIds) {   //编辑检查项成功
        log.info("编辑套餐: {}", setmeal);
        setmealService.edit(setmeal, checkgroupIds);
        return new Result(true, "编辑套餐成功");
    }


    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return new Result(true, "图片上传成功", filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }

        return null;
    }

}
