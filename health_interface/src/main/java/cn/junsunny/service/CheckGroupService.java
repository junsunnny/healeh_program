package cn.junsunny.service;

import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组管理
 */
public interface CheckGroupService {
    // 添加检查组
    public void add(CheckGroup checkGroup, Integer[] checkItemIds);

    // 分页查询
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    // 删除检查项
    public void del(Integer id);

    // 查询检查项根据id
    public CheckGroup finCheckGroup(Integer id);

    // 查询检查项和检查组关联的所有检查项id根据检查组id
    public List<Integer> findCheckGroupAndCheckItem(Integer id);

    // 修改检查组数据
    public void editCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds);

    // 查询检查组的所有信息
    public List<CheckGroup> findAllCheckGroup();
}
