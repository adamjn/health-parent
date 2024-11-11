package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderSettingMapper {





    @Select("SELECT * FROM t_ordersetting WHERE orderDate BETWEEN #{start} AND #{end}")
    List<OrderSetting> findOrderSettingByDateRange(Date start, Date end);
    // 根据日期查询预约设置
    @Select("SELECT * FROM t_ordersetting WHERE DATE(orderDate) = DATE(#{orderDate})")
    OrderSetting findOrderSettingByDate(Date orderDate);

    // 新增预约设置
    @Insert("INSERT INTO t_ordersetting(orderDate, number, reservations) VALUES(#{orderDate}, #{number}, #{reservations})")
    void addOrderSetting(OrderSetting orderSetting);

    // 更新预约设置
    @Update("UPDATE t_ordersetting SET number = #{number}, reservations = #{reservations} WHERE orderDate = #{orderDate}")
    void updateOrderSetting(OrderSetting orderSetting);
}
