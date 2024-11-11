package com.itheima.controller.admin;

import com.itheima.common.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ordersetting")
@Slf4j
public class OrderSettingController {

    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 下载文件接口
     *
     * @param filename 文件名
     * @return 文件内容
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) {
        try {
            // 加载 resource 目录下的文件
            Resource resource = new ClassPathResource("templates/" + filename);

            // 如果文件不存在，返回 404 错误
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 将文件内容读取到字节数组中
            byte[] fileBytes = toByteArray(resource.getInputStream());

            // 设置响应头，指定文件名称和下载类型
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);

            // 返回文件内容
            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常返回 500 错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Java 8 方法，用于将 InputStream 转换为字节数组
    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    // 上传文件接口

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    /**
     * 上传文件接口
     *
     * @param file 文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new Result(false, "文件缺失");
        }

        try {
            // 确保目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 获取文件扩展名并生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;

            // 构建文件的目标路径
            File destinationFile = new File(UPLOAD_DIR + objectName);

            // 将文件写入到目标路径
            file.transferTo(destinationFile);

            // 返回成功信息，包含文件相对路径
            return new Result(true, "预约设置成功", "/uploads/" + objectName);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "预约设置成功失败");
        }
    }

    /**
     * 查询月份范围内的预约设置接口
     *
     * @param month 月份（格式 yyyy-MM）
     * @return 预约设置列表
     */
    @GetMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("month") String month) {
        log.info("查询月份：" + month);
        try {
            // 解析传入的月份（格式 yyyy-MM）
            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
            Date monthDate = monthFormat.parse(month);

            // 获取该月的第一天
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(monthDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date monthStart = calendar.getTime();

            // 获取该月的最后一天
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthEnd = calendar.getTime();

            // 查询该月范围内的记录
            List<OrderSetting> orderSettings = orderSettingService.findOrderSettingByDateRange(monthStart, monthEnd);
            return new Result(true, "查询成功", orderSettings);

        } catch (ParseException e) {
            e.printStackTrace();
            return new Result(false, "日期格式错误");
        }
    }

    /**
     * 编辑预约数量接口
     *
     * @param orderDate 预约日期（格式 yyyy-MM-dd）
     * @param number    预约数量
     * @return 编辑结果
     */
    @PostMapping("editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting) {
        log.info("编辑预约数量：{}，数量：{}", orderSetting.getOrderDate(), orderSetting.getNumber());

        // 获取请求体中的日期
        Date orderDate = orderSetting.getOrderDate();
        Integer number = orderSetting.getNumber();

        // 查询该日期的记录
        OrderSetting existingOrderSetting = orderSettingService.findOrderSettingByDate(orderDate);

        if (existingOrderSetting == null) {
            // 如果记录不存在，则新增记录
            OrderSetting orderSettingToAdd = new OrderSetting();
            orderSettingToAdd.setOrderDate(orderDate);
            orderSettingToAdd.setNumber(number);
            orderSettingService.addOrderSetting(orderSettingToAdd);
            log.info("没有找到该日期的记录，新增记录：{}", orderSettingToAdd);
        } else {
            // 如果记录存在，则更新记录
            existingOrderSetting.setNumber(number);
            orderSettingService.updateOrderSetting(existingOrderSetting);
            log.info("找到该日期的记录，更新记录：{}", existingOrderSetting);
        }

        return new Result(true, "编辑成功");
    }


}
