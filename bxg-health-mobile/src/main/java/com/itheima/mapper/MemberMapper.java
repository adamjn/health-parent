package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@SuppressWarnings("MybatisXMapperMethodInspection")
@Mapper
public interface MemberMapper {
    @Select("select * from t_member")
     List<Member> findAll();

    Page<Member> selectByCondition(String queryString);

    void add(Member member);

    void deleteById(Integer id);

    Member findById(Integer id);

    Member findByTelephone(String telephone);

    void edit(Member member);
    @Select("select count(id) from t_member where regTime >= #{value}")
    Integer findMemberCountBeforeDate(String date);
    @Select("select count(id) from t_member where regTime = #{value}")
    Integer findMemberCountByDate(String date);
    @Select("select count(id) from t_member where regTime >= #{value}")
    Integer findMemberCountAfterDate(String date);
    @Select("select count(id) from t_member")
    Integer findMemberTotalCount();
}
