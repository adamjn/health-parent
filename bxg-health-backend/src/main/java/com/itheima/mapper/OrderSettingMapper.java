package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderSettingMapper {


    @Select("SELECT * FROM t_ordersetting WHERE orderDate BETWEEN #{start} AND #{end}")
    List<OrderSetting> findOrderSettingByDateRange(String start, String end);

    // 根据日期查询预约设置
    @Select("SELECT * FROM t_ordersetting WHERE orderDate = #{orderDate}")
    OrderSetting findOrderSettingByDate(String orderDate);

    // 新增预约设置
    @Insert("INSERT INTO t_ordersetting(orderDate, number, reservations) VALUES(#{orderDate}, #{number}, #{reservations})")
    void addOrderSetting(OrderSetting orderSetting);

    // 更新预约设置
    @Update("UPDATE t_ordersetting SET number = #{number}, reservations = #{reservations} WHERE orderDate = #{orderDate}")
    void updateOrderSetting(OrderSetting orderSetting);

    @Insert(" insert into t_ordersetting (orderDate,number,reservations) values (#{orderDate},#{number},#{reservations})")
    public void add(OrderSetting orderSetting);

    @Update("update t_ordersetting set number = #{number} where orderDate = #{orderDate}")
    public void editNumberByOrderDate(OrderSetting orderSetting);

    @Select(" select count(*) from t_ordersetting where orderDate = #{orderDate}")
    public long findCountByOrderDate(Date orderDate);

    @Select("  select * from t_ordersetting where orderDate between #{dateBegin} and #{dateEnd}")
    List<OrderSetting> getOrderSettingByMonth(Map map);


}
