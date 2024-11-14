package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @Select("select * from t_order")
    Page<Order> pageQuery(QueryPageBean queryPageBean);

    /**
     * 修改预约状态
     *
     * @param order
     * @return
     */
    @Update("update t_order set orderStatus = #{orderStatus} where id = #{id}")
    void update(Order order);
}
