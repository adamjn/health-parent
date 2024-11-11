package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.mapper.OrderMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 分页查询预约信息
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Order> page =orderMapper.pageQuery(queryPageBean);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 修改预约状态
     *
     * @param order
     * @return
     */
    @Override
    public void update(Order order) {
         orderMapper.update(order);
    }
}
