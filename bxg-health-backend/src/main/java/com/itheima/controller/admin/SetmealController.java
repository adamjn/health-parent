package com.itheima.controller.admin;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.AliOssUtil;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AliOssUtil aliOssUtil;

    private static final String REDIS_UPLOADED_IMAGES_SET = "uploaded_images";
    private static final String REDIS_USED_IMAGES_SET = "used_images";

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
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.findById(id));
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
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            log.error("新增套餐失败：{}", e);
            //新增套餐失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
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
        try {
            setmealService.edit(setmeal, checkgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            log.error("编辑套餐失败：{}", e);
            //编辑套餐失败
        }
        return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);

    }


    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        log.info("文件上传：{}", file);
        if (file == null || file.isEmpty()) {
            return new Result(false, "文件缺失");
        }
        try {
            // 计算文件的哈希值
            String hash = calculateHash(file);
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            // 将文件名加入 Redis 中 uploaded_images 集合中
            redisTemplate.opsForSet().add(REDIS_UPLOADED_IMAGES_SET, objectName);

            return new Result(true, "图片上传成功", filePath);
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("文件上传失败：{}", e);
            return new Result(false, "文件上传失败");
        }
    }

    // 当套餐数据插入数据库后，将图片名称加入到 used_images 集合
    public void addUsedImage(String imageName) {
        redisTemplate.opsForSet().add(REDIS_USED_IMAGES_SET, imageName);
    }

    // 计算两个集合的差集，返回垃圾图片名称集合
    public Set<String> getGarbageImages() {
        return redisTemplate.opsForSet().difference(REDIS_UPLOADED_IMAGES_SET, REDIS_USED_IMAGES_SET);
    }

    // 计算文件哈希值
    private String calculateHash(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(file.getBytes());
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }


}


