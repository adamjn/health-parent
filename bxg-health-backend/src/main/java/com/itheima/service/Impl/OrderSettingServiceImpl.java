package com.itheima.service.Impl;

import com.itheima.common.entity.Result;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public List<OrderSetting> findOrderSettingByDateRange(Date start, Date end) {
        return orderSettingMapper.findOrderSettingByDateRange(start, end);
    }

    @Override
    public OrderSetting findOrderSettingByDate(Date orderDate) {
        return orderSettingMapper.findOrderSettingByDate(orderDate);
    }

    @Override
    public void addOrderSetting(OrderSetting orderSetting) {
        orderSettingMapper.addOrderSetting(orderSetting);
    }

    @Override
    public void updateOrderSetting(OrderSetting orderSetting) {
        orderSettingMapper.updateOrderSetting(orderSetting);
    }
}
