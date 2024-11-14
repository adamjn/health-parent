package com.itheima.service.Impl;

import com.itheima.common.constant.MessageConstant;
import com.itheima.common.entity.Result;
import com.itheima.common.utils.DateUtils;
import com.itheima.common.utils.MD5Utils;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;



    @Override
    public Result submitOrder(Map<String, Object> map) throws Exception {
        String orderDate = (String) map.get("orderDate"); //预约日期
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if(orderSetting == null){

            //预约日期未配置
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //检查用户所选择的日期是否已经预约满，如果已经满了则不能再预约
        Integer orderNum = orderSetting.getNumber();
        Integer reservations = orderSetting.getReservations();
        if(reservations >= orderNum){
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //检查用户是否已经预约过，如果已经预约过则不能再预约
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByTelephone(telephone);

        if(member == null){
            Integer memberId = member.getId();//会员的id

            Date order_date = DateUtils.parseString2Date(orderDate);//预约日期

            String setmealId = (String) map.get("setmealId");//套餐id
            Order order = new Order(memberId, order_date, Integer.parseInt(setmealId));
            //根据条件查询
            List<Order> list = orderMapper.findByCondition(order);

            if(list!= null && list.size() > 0){
                //说明用户在重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else{
            //检查用户是否是会员，如果是会员直接完成预约，如果不是会员则自动完成注册并完成预约
            member = new Member();
            member.setName((String)map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberMapper.add(member);
        }
        //预约成功，更新当日预约人数
        Order order = new Order();
        order.setMemberId(member.getId());//会员id
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//预约日期
        order.setOrderType((String) map.get("orderType")); //预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐id

        orderMapper.add(order);

        orderSetting.setReservations(orderSetting.getReservations() + 1);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }


    @Override
    public Map<String, Object> findById(Integer id) throws Exception {

        Map<String, Object> map = orderMapper.findById4Detail(id);
        if (map == null) {
            Date orderDate = (Date) map.get("orderDate");
            String orderDateStr = DateUtils.parseDate2String( orderDate);
            map.put("orderDate", orderDateStr);

        }
        return map;
    }
}
