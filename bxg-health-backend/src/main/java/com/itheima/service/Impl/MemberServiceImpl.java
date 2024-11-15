package com.itheima.service.Impl;

import com.itheima.mapper.MemberMapper;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {

        List<Integer> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String m : month) {
            // 确保 month 的格式是 "yyyy-MM"，然后加上 "-01" 得到该月的第一天
            LocalDate date = LocalDate.parse(m + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 获取该月的最后一天
            LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());

            // 格式化为 "yyyy-MM-dd" 格式
            String lastDayStr = lastDayOfMonth.format(formatter);

            // 查询该月的会员数量
            Integer count = memberMapper.findMemberCountBeforeDate(lastDayStr);
            list.add(count);
        }

        return list;
    }
}
