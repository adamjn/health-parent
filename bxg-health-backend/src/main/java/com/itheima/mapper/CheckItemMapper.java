package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CheckItemMapper {

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @Insert("insert into t_checkitem( name, code, sex, age, price, type, remark, attention) values" +
            "(#{name}, #{code}, #{sex}, #{age}, #{price}, #{type}, #{remark}, #{attention})")
    void insert(CheckItem checkItem);

    /**
     * 按id删除检查项
     *
     * @param id
     * @return
     */
    @Delete("delete from t_checkitem where id = #{id}")
    void deleteById(Integer id);


    /**
     * 查询所有检查项目
     *
     * @param
     * @return List<CheckItem>
     */
    @Select("select * from t_checkitem")
    List<CheckItem> findAll();


    /**
     * 编辑检查项
     *
     * @param checkItem
     * @return
     */
    void update(CheckItem checkItem);

    /**
     * 分页查询检查项目
     *
     * @param
     * @return PageResult
     */
    Page<CheckItem> pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id查询检查项目
     *
     * @param id
     * @return CheckItem
     */

    @Select("select * from t_checkitem where id = #{id}")
    CheckItem findById(Integer id);

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId
     * @return
     */
    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id =#{ checkGroupId}")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
}
