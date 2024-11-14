package com.itheima.service;

import com.itheima.common.entity.Result;
import com.itheima.pojo.Member;

import java.util.Map;

public interface OrderService {

    Result submitOrder(Map<String, Object> member);

    Map<String, Object> findById(Integer id);

}
