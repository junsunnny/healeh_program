package cn.junsunny.Dao;

import cn.junsunny.pojo.CheckItem;
import com.github.pagehelper.Page;

public interface CheckItemDao {
    // 检查项新增方法
    public void add(CheckItem checkItem);

    // 检查项分页查询
    public Page<CheckItem> findByCondition(String queueString);
}
