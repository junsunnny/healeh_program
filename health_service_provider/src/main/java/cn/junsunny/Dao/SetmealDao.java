package cn.junsunny.Dao;

import cn.junsunny.pojo.CheckGroup;
import cn.junsunny.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 套餐管理的数据访问层
 */
public interface SetmealDao {

    // 添加套餐
    public void addSetMeal(Setmeal setmeal);

    // 设置套餐管理和检查组之间的关联关系
    public void setSetmealAndCheckGroup(Map<String, Integer> paramMap);

    // 分页查询套餐管理
    public Page<Setmeal> selectByCondition(String queryString);

    // 删除检查项信息
    public void deleteSetmeal(Integer id);

    // 删除检查组和套餐之间的关系
    public void deleteSetMealAndCheckGroup(Integer id);

    // 查询检查套餐信息根据id
    public Setmeal findSetMealById(Integer id);

    // 根据检查套餐id 获取检查组信息
    public List<Integer> findSetMealAndCheckGroupBySetMealId(Integer id);

    // 编辑检查组和检查套餐之间的数据
    public void editSetMeal(Setmeal setmeal);
}
