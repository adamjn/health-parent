package com.itheima.service.Impl;

import com.itheima.common.entity.Result;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //已经存在，执行更新操作
                    orderSettingMapper.editNumberByOrderDate(orderSetting);
                }else{
                    //不存在，执行添加操作
                    orderSettingMapper.add(orderSetting);
                }
            }
        }
    }

    public List<Map> getOrderSettingByMonth(String date) {//2019-3
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd = date + "-31";//2019-3-31
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingMapper.add(orderSetting);
        }
    }
}
