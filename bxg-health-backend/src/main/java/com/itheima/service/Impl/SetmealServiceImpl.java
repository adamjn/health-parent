package com.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.common.constant.RedisConstant;
import com.itheima.common.entity.PageResult;
import com.itheima.common.entity.QueryPageBean;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}") // 从属性文件读取输出目录的路径
    private String outputPath;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = setmealMapper.pageQuery(queryPageBean);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Transactional
    public void add(Setmeal setmeal, String checkgroupIds) {
        setmealMapper.add(setmeal);


        List<Integer> checkgroupIdsList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkgroupIds.split(",")) {
            checkgroupIdsList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkgroupId : checkgroupIdsList) {

            setmealMapper.addCheckGroup(setmeal.getId(), checkgroupId);
        }
        Integer setmealId = setmeal.getId(); // 获取套餐id


        // 完成数据库操作后需要将图片名称保存到 Redis
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

        // 新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();
    }


    /**
     * 编辑套餐
     *  1.先删除原有的检查项
     *  2.再新增新的检查项
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    public void edit(Setmeal setmeal, String checkgroupIds) {


        setmealMapper.edit(setmeal);
        Integer setmealId = setmeal.getId();
        // 删除原有关联关系
        setmealMapper.deleteCheckItemByCheckGroupId(setmealId);
        List<Integer> checkgroupIdList = new ArrayList<>();

        // 将checkitemIds从字符串分割为数组并转换为整数后添加到List中
        for (String id : checkgroupIds.split(",")) {
            checkgroupIdList.add(Integer.parseInt(id.trim()));
        }

        for (Integer checkItemId : checkgroupIdList) {

            setmealMapper.addSetmealCheckGroup(setmealId, checkItemId);
        }
    }


    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealMapper.findSetmealCount();
    }

    // 生成静态页面
    public void generateMobileStaticHtml() {
        // 准备模板文件中所需的数据
        List<Setmeal> setmealList = setmealMapper.getSetmeal();
        // 生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        // 生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

   //生成套餐列表静态页面
  public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    dataMap.put("setmealList", setmealList);
    this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
  }

  //生成套餐详情静态页面（多个）
  public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
    for (Setmeal setmeal : setmealList) {
      Map<String, Object> dataMap = new HashMap<String, Object>();
      dataMap.put("setmeal", this.findById(setmeal.getId()));
      this.generateHtml("mobile_setmeal_detail.ftl",
                        "setmeal_detail_"+setmeal.getId()+".html",
                        dataMap);
    }
  }

  public void generateHtml(String templateName,String htmlPageName,Map<String, Object> dataMap){
    Configuration configuration = freeMarkerConfigurer.getConfiguration();
    Writer out = null;
    try {
      // 加载模版文件
      Template template = configuration.getTemplate(templateName);
      // 生成数据
      File docFile = new File(outputPath + "\\" + htmlPageName);
      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
      // 输出文件
      template.process(dataMap, out);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != out) {
          out.flush();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }



}

