package cn.junsunny.Dao;

import cn.junsunny.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 检查组数据处理
 */
public interface CheckGroupDao {
    // 添加检查组数据
    public void add(CheckGroup checkGroup);

    // 设置检查组和检查项之间的关联关系
    void setCheckGroupAndCheckItem(Map<String, Integer> paramMap);

    // 分页查询
    public Page<CheckGroup> findAll(String queryString);

    // 删除检查组表中数据
    public void delete(Integer id);

    // 删除检查项和检查组的关联关系
    public void deleteAssociation(Integer id);

    // 查询检查项根据id
    public CheckGroup finCheckGroup(Integer id);

    // 查询检查项和检查组关联的所有检查项id根据检查组id
    public List<Integer> findCheckGroupAndCheckItem(Integer id);

    // 更新检查组内数据
    public void editCheckGroup(CheckGroup checkGroup);
}
