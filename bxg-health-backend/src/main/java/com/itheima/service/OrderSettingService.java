package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    /**
     * 查询月份范围内的预约设置接口
     *
     * @param  start当月的起始日期，end当月的最后一天
     * @return 预约设置列表
     */
    List<OrderSetting> findOrderSettingByDateRange(String start, String end);

    /**
     * 根据日期查询预约设置
     *
     * @param orderDateDate
     * @return
     */
    OrderSetting findOrderSettingByDate(String orderDate);

    /**
     * 添加预约设置
     *
     * @param orderSetting
     * @return
     */
    void addOrderSetting(OrderSetting orderSetting);
    List<Map> getOrderSettingByMonth(String date);
    /**
     * 编辑预约设置
     * @param orderSetting
     */
    void updateOrderSetting(OrderSetting orderSetting);
    public void add(List<OrderSetting> list);

    void editNumberByOrderDate(OrderSetting orderSetting);
}





