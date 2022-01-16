package cn.junsunny.service.impl;

import cn.junsunny.Dao.CheckItemDao;
import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckItem;
import cn.junsunny.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    // 注入Dao对象
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 使用Mybatis完成分页助手的插件
        PageHelper.startPage(currentPage, pageSize);

        // 调用service进行查询操作
        Page<CheckItem> resPage = checkItemDao.findByCondition(queryString);

        // 设置查询结果的返回值
        PageResult pageResult = new PageResult(resPage.getTotal(), resPage.getResult());
        return pageResult;
    }

    @Override
    public void delCheckItem(Integer id) {
        checkItemDao.delCheckItem(id);
    }

    @Override
    public CheckItem findCheckItemById(Integer id) {
        return checkItemDao.findCheckItemById(id);
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemDao.updateCheckItem(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

}
