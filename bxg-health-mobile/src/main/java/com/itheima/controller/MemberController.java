package com.itheima.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.common.constant.MessageConstant;
import com.itheima.common.constant.RedisMessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate  redisTemplate;
    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        // Retrieve and validate input parameters
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        // Fetch stored validation code from Redis
        String redisKey = telephone + RedisMessageConstant.SENDTYPE_LOGIN;
        String validateCodeInRedis = (String) redisTemplate.opsForValue().get(redisKey);

        // Verify the code
        if (validateCodeInRedis == null || !validateCodeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        // Check membership status
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            // Auto-register as a new member
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            memberService.add(member);
        }

        // Set a cookie for the login session
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(cookie);

        // Save member info to Redis with a 30-minute expiration
        String memberJson = JSON.toJSONString(member);
        redisTemplate.opsForValue().set(telephone, memberJson, 30, TimeUnit.MINUTES);

        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

}
