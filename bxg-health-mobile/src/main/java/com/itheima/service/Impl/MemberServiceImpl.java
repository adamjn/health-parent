package com.itheima.service.Impl;

import com.itheima.common.utils.MD5Utils;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员服务
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Override
    public void add(Member member) {

        String password = member.getPassword();
        if (password == null || password.trim().length() == 0) {
            password = MD5Utils.md5(password);
        }
        memberMapper.add(member);
    }

    @Override
    public Member findByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }
}
