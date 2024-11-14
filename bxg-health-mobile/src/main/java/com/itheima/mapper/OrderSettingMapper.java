package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface OrderSettingMapper {
    @Select("SELECT * FROM t_ordersetting WHERE orderDate = #{orderDate}")
    OrderSetting findByOrderDate(Date orderDate);
}
