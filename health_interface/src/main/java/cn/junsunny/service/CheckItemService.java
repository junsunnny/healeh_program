package cn.junsunny.service;

import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckItem;

import java.util.List;

/**
 * 检查项管理
 */
public interface CheckItemService {
    // 检查项添加方法
    public void add(CheckItem checkItem);

    // 检查项分页查询
    public PageResult findPage(QueryPageBean queryPageBean);

    // 删除检查项
    public void delCheckItem(Integer id);

    // 根据id查询检查项
    public CheckItem findCheckItemById (Integer id);

    // 修改检查项信息
    public void updateCheckItem (CheckItem checkItem);

    // 查询所有检查项信息
    public List<CheckItem> findAll();
}
