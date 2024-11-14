package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.SMSUtils;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map<String, Object> map) {
        String telephone = map.get("telephone") != null ? map.get("telephone").toString() : null;
        String validateCode = map.get("validateCode") != null ? map.get("validateCode").toString() : null;

        // Retrieve the verification code from Redis
        String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
        String validateCodeInRedis = redisTemplate.opsForValue().get(key);

        // Verify the validation code
        if (validateCodeInRedis == null || !validateCodeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        // Proceed with the appointment process since the code is valid
        map.put("orderType", Order.ORDERTYPE_WEIXIN); // Set appointment type, e.g., WeChat

        Result result = new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);

        try {
            // Call orderService to handle the business logic
            result = orderService.submitOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result; // Return failure result on exception
        }

        if (result.isFlag()) {
            // On successful booking, send an SMS notification
            try {
                SMSUtils.sendMessage(SMSUtils.ORDER_NOTICE, telephone, "666666");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 根据预约订单id查询预约信息
     *
     * @param id 预约订单id
     * @return 套餐信息和会员信息
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Map<String, Object> map = orderService.findById( id );
            //查询成功
            return new Result( true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result( false,  MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
