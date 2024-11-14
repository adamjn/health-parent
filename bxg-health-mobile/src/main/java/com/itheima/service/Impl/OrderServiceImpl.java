package com.itheima.service.Impl;

import com.itheima.common.entity.Result;
import com.itheima.common.utils.MD5Utils;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.pojo.Member;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Result submitOrder(Map<String, Object> member) {

        return null;
    }

    //根据手机号查询会员
    @Override
    public Map<String, Object> findById(Integer id) {
        return null;
    }
}
