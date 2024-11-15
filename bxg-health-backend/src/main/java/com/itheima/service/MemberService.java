package com.itheima.service;

import java.util.List;

public interface MemberService {
    List<Integer> findMemberCountByMonth(List<String> month);
}
