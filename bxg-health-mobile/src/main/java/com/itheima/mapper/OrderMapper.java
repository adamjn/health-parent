package com.itheima.mapper;

import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("insert into t_order ()")
    Integer submitOrder(Member member);
}
