package com.itheima.service.Impl;

import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Map<String, Object> getBusinessReportData() {

        // 获取当前时间
        LocalDate today = LocalDate.now();

// 获取本周和本月的起始时间
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());

// 格式化日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayStr = today.format(formatter);
        String startOfWeekStr = startOfWeek.format(formatter);
        String startOfMonthStr = startOfMonth.format(formatter);

// 查询会员数据
        Integer todayNewMember = memberMapper.findMemberCountByDate(todayStr);
        Integer totalMember = memberMapper.findMemberTotalCount();
        Integer thisWeekNewMember = memberMapper.findMemberCountAfterDate(startOfWeekStr);
        Integer thisMonthNewMember = memberMapper.findMemberCountAfterDate(startOfMonthStr);

// 查询预约数据
        Integer todayOrderNumber = orderMapper.findOrderCountByDate(todayStr);
        Integer thisWeekOrderNumber = orderMapper.findOrderCountAfterDate(startOfWeekStr);
        Integer thisMonthOrderNumber = orderMapper.findOrderCountAfterDate(startOfMonthStr);

// 查询到诊数据
        Integer todayVisitsNumber = orderMapper.findVisitsCountByDate(todayStr);
        Integer thisWeekVisitsNumber = orderMapper.findVisitsCountAfterDate(startOfWeekStr);
        Integer thisMonthVisitsNumber = orderMapper.findVisitsCountAfterDate(startOfMonthStr);

// 热门套餐查询（取前4个）
        List<Map<String, Object>> hotSetmeal = orderMapper.findHotSetmeal();

// 返回查询结果
        Map<String, Object> result = new HashMap<>();
        result.put("todayNewMember", todayNewMember);
        result.put("totalMember", totalMember);
        result.put("thisWeekNewMember", thisWeekNewMember);
        result.put("thisMonthNewMember", thisMonthNewMember);
        result.put("todayOrderNumber", todayOrderNumber);
        result.put("thisWeekOrderNumber", thisWeekOrderNumber);
        result.put("thisMonthOrderNumber", thisMonthOrderNumber);
        result.put("todayVisitsNumber", todayVisitsNumber);
        result.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        result.put("hotSetmeal", hotSetmeal);

        return result;


    }
}
