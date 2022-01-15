package cn.junsunny.service;

import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckItem;

/**
 * 检查项管理
 */
public interface CheckItemService {
    // 检查项添加方法
    public void add(CheckItem checkItem);

    // 检查项分页查询
    public PageResult findPage(QueryPageBean queryPageBean);
}
