package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    Page<CheckGroup> pageQuery(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM t_setmeal WHERE id = #{id}")
    Setmeal findById(Integer id);

    /**
     * 新增套餐
     *
     * @param setmeal
     * @return
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐和检查组关联关系
     *
     * @param id
     * @param checkgroupId
     */
    @Insert("INSERT INTO t_setmeal_checkgroup (setmeal_id, checkgroup_id) VALUES (#{id}, #{checkgroupId})")
    void addCheckGroup(Integer id, Integer checkgroupId);


    /**
     * 编辑套餐
     *
     * @param setmeal
     * @return
     */
    void edit(Setmeal setmeal);

    @Delete("delete from t_setmeal_checkgroup where setmeal_id =#{setmealId}")
    void deleteCheckItemByCheckGroupId(Integer setmealId);


    @Insert("Insert into t_setmeal_checkgroup (setmeal_id, checkgroup_id) values (#{setmealId}, #{checkItemId})")
    void addSetmealCheckGroup(Integer setmealId, Integer checkItemId);
}
