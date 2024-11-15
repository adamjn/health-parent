package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckGroupMapper {

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    Page<CheckGroup> pageQuery(QueryPageBean queryPageBean);

    /**
     * 查询所有检查组
     *
     * @param
     * @returnList<CheckGroup>
     */
    @Select("SELECT * FROM t_checkgroup")
    List<CheckGroup> findAll();

    /**
     * 查询检查组
     *
     * @param id
     * @return CheckGroup
     */
    @Select("select * from t_checkgroup where id = #{id}")
    CheckGroup findById(Integer id);

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    @Delete("delete from t_checkgroup where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @return
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组和检查项的关联关系
     */
    @Insert("Insert into t_checkgroup_checkitem (checkgroup_id, checkitem_id) values (#{checkGroupId}, #{checkItemId})")
    void addCheckItem(Integer checkGroupId, Integer checkItemId);

    void edit(CheckGroup checkGroup);

    @Delete("delete from t_checkgroup_checkitem where checkgroup_id = #{checkGroupId}")
    void deleteCheckItemByCheckGroupId(Integer checkGroupId);

    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id = #{id}")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);








    void addCheckItemsBatch(@Param("checkgroupId") Integer checkgroupId,
                            @Param("checkitemIds") List<Integer> checkitemIds);
}

