package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据体检预约信息查询订单
     *
     * @param order 体检预约信息
     * @return 预约信息
     */
    List<Order> findByCondition(Order order);

    /**
     * 根据预约id查询预约信息，
     *
     * @param id 预约订单id
     * @return 包括体检人信息、套餐信息
     */
    Map<String, Object> findById4Detail(Integer id);

    /**
     * 根据指定时间统计预约数
     *
     * @param date 指定时间
     * @return 当前预约总数
     */
    @Select("select count(id)\n" +
            "        from t_order\n" +
            "        where orderDate = #{date}")
    Integer findOrderCountByDate(String date);

    /**
     * 统计指定时间之后的预约数
     *
     * @param date 指定时间
     * @return 预约总数
     */
    @Select(" select count(id)\n" +
            "        from t_order\n" +
            "        where orderDate >= #{date}")
    Integer findOrderCountAfterDate(String date);

    /**
     * 统计指定时间到诊数
     *
     * @param date 指定时间
     * @return 到诊总数
     */
    @Select("select count(id)\n" +
            "        from t_order\n" +
            "        where orderDate = #{date}\n" +
            "          and orderStatus = '已到诊'")
    Integer findVisitsCountByDate(String date);

    /**
     * 统计指定时间之后的到诊数
     *
     * @param date 指定时间
     * @return 到诊总数
     */
    @Select("select count(id)\n" +
            "        from t_order\n" +
            "        where orderDate >= #{date}\n" +
            "          and orderStatus = '已到诊'")
    Integer findVisitsCountAfterDate(String date);

    /**
     * 查询热门套餐
     *
     * @return 套餐预约数量前四名
     */
    @Select("select s.name name, count(o.id) setmeal_count, count(o.id) / (select count(id) from t_order) proportion\n" +
            "        from t_order o\n" +
            "                 inner join t_setmeal s on s.id = o.setmeal_id\n" +
            "        group by o.setmeal_id\n" +
            "        order by setmeal_count desc\n" +
            "        limit 0\n" +
            "            ,4")
    List<Map<String, Object>> findHotSetmeal();
}

