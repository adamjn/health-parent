package com.itheima.controller;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    public Result submitOrder(@RequestBody Member member){
        log.info("订单提交成功，订单号：{}", member);
        try{
            orderService.submitOrder(member);
            return new Result(true, MessageConstant.ORDER_SUCCESS, member.getId());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FULL, null);
        }
    }

}
