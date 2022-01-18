package cn.junsunny.service;

import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.Setmeal;

import java.util.List;

/**
 * 套餐管理服务层接口
 */
public interface SetmealService {
    // 新建套餐项
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds);

    PageResult findPageSetmeal(Integer currentPage, Integer pageSize, String queryString);
    // 分页查询套餐管理

}
