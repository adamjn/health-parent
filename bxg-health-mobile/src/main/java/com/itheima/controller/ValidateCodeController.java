package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.SMSUtils;
import com.itheima.common.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Generate a 6-digit random authentication code
     */
    Integer authCode = ValidateCodeUtils.generateValidateCode(6);

    /**
     * Sends a verification code for appointment booking.
     *
     * @param telephone User's phone number
     * @return Result indicating success or failure of sending the code
     */
    @PostMapping("/sendAppointmentOrder/{telephone}")
    public Result sendAppointmentOrder(@PathVariable("telephone") String telephone) {
        try {
            // Send the verification code to the user
            SMSUtils.sendMessage(SMSUtils.VALIDATE_CODE, telephone, String.valueOf(authCode));
        } catch (Exception e) {
            e.printStackTrace();
            // Failed to send the code
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // Successfully sent the code, cache it in Redis with a 5-minute expiration
        String redisKey = telephone + RedisMessageConstant.SENDTYPE_ORDER;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(authCode), 5, TimeUnit.MINUTES);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * Sends a verification code for quick login.
     *
     * @param telephone User's phone number
     * @return Result indicating success or failure of sending the code
     */
    @GetMapping("/send4Login/{telephone}")
    public Result send4Login(@PathVariable("telephone") String telephone) {
        try {
            // Send the verification code to the user
            SMSUtils.sendMessage(SMSUtils.VALIDATE_CODE, telephone, String.valueOf(authCode));
        } catch (Exception e) {
            e.printStackTrace();
            // Failed to send the code
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // Successfully sent the code, cache it in Redis with a 5-minute expiration
        String redisKey = telephone + RedisMessageConstant.SENDTYPE_LOGIN;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(authCode), 5, TimeUnit.MINUTES);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
