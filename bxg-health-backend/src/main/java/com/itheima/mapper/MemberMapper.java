package com.itheima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select(" select count(id) from t_member where regTime <= #{date}")
    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String date);

    /**
     * 统计指定时间之后的会员数
     *
     * @param date 指定时间
     * @return 会员总数
     */
    @Select("select count(id)\n" +
            "        from t_member\n" +
            "        where regTime >= #{value}")
    Integer findMemberCountAfterDate(String date);

    /**
     * 统计总会员数
     *
     * @return 总会员数
     */
    Integer findMemberTotalCount();
}
