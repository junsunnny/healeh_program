package cn.junsunny.Dao;

import cn.junsunny.pojo.Setmeal;
import com.github.pagehelper.Page;

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

}
