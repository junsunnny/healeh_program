package cn.junsunny.service;

import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckGroup;
import cn.junsunny.pojo.Setmeal;

import java.util.List;

/**
 * 套餐管理服务层接口
 */
public interface SetmealService {
    // 新建套餐项
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds);

    // 分页查询套餐管理
    public PageResult findPageSetmeal(Integer currentPage, Integer pageSize, String queryString);

    // 删除数据并清空redis里面的数据
    public void deleteSetmeal(Integer id, String img);

    // 查询检查套餐信息根据id
    public Setmeal findSetMealById(Integer id);

    // 根据检查套餐id 获取检查组信息
    public List<Integer> findSetMealAndCheckGroupBySetMealId(Integer id);

    // 编辑检查套餐选项
    public void editSetMeal(Setmeal setmeal, Integer[] checkGroupIds);
}
