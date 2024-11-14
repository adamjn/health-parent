package com.itheima.service;

import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.Order;

public interface OrderService {
    /**
     * 分页查询预约信息
     *
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 修改预约状态
     *
     * @param order
     * @return
     */
    void update(Order order);
}
