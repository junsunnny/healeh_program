package cn.junsunny.Dao;

import cn.junsunny.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemDao {
    // 检查项新增方法
    public void add(CheckItem checkItem);

    // 检查项分页查询
    public Page<CheckItem> findByCondition(String queueString);

    // 删除检查项
    public void delCheckItem(Integer id);

    // 根据id查询检查项
    public CheckItem findCheckItemById (Integer id);

    // 修改检查项信息
    public void updateCheckItem (CheckItem checkItem);

    // 查询所有检查项
    public List<CheckItem> findAll();

}
