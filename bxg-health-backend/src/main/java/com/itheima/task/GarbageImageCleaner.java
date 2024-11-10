package com.itheima.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class GarbageImageCleaner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String REDIS_UPLOADED_IMAGES_SET = "uploaded_images";
    private static final String REDIS_USED_IMAGES_SET = "used_images";

    /**
     * 定时任务，每天凌晨2点执行垃圾图片清理
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
    public void cleanGarbageImages() {
        // 获取垃圾图片名称集合
        Set<String> garbageImages = redisTemplate.opsForSet().difference(REDIS_UPLOADED_IMAGES_SET, REDIS_USED_IMAGES_SET);

        if (garbageImages != null && !garbageImages.isEmpty()) {
            // 打印垃圾图片的名称（可以根据业务需求执行其他操作，比如删除垃圾图片）
            System.out.println("找到垃圾图片: " + garbageImages);

            // 假设要删除 Redis 中的垃圾图片记录，可以在此处调用删除逻辑
            // 例如：清理 Redis 中 uploaded_images 集合的垃圾图片
            redisTemplate.opsForSet().remove(REDIS_UPLOADED_IMAGES_SET, garbageImages.toArray());
        } else {
            System.out.println("没有找到垃圾图片");
        }
    }
}
