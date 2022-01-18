package cn.junsunny.service.impl;

import cn.junsunny.Dao.CheckGroupDao;
import cn.junsunny.entity.PageResult;
import cn.junsunny.pojo.CheckGroup;
import cn.junsunny.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务实现类
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    // 添加的检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        // System.out.println(checkGroup);
        checkGroupDao.add(checkGroup);
        // System.out.println(checkGroup.getId());
        setCheckGroupAndCheckItem(checkGroup.getId(), checkItemIds);
    }

    // 设置检查组和检查项之间的关联关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("checkGroupId",checkGroupId);
            for (Integer checkItemId : checkItemIds) {
                paramMap.put("checkItemId",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(paramMap);
            }
        }
    }

    // 分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 使用pageHelper分页助手进行查询
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> pageRes = checkGroupDao.findAll(queryString);
        return new PageResult(pageRes.getTotal(),pageRes.getResult());
    }

    // 删除检查组表数据
    @Override
    public void del(Integer id) {
        // 删除检查组表中的数据
        checkGroupDao.delete(id);
        // 清空检查组与检查数据之间的关联关系
        checkGroupDao.deleteAssociation(id);
    }

    // 查询检查项根据id
    @Override
    public CheckGroup finCheckGroup(Integer id) {
        return checkGroupDao.finCheckGroup(id);
    }

    // 查询检查项和检查组关联的所有检查项id根据检查组id
    @Override
    public List<Integer> findCheckGroupAndCheckItem(Integer id) {
        return checkGroupDao.findCheckGroupAndCheckItem(id);
    }

    // 编辑检查组
    @Override
    public void editCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds) {
        // 1.向检查组中更新检查组对应的id值
        checkGroupDao.editCheckGroup(checkGroup);
        // 2.清空检查组和检查性对应的关联关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        // 3.建立检查组和检查项对应的关联关系
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
    }

    // 查询检查组内所有的数据信息
    @Override
    public List<CheckGroup> findAllCheckGroup() {
        return checkGroupDao.findAllCheckGroup();
    }


}
