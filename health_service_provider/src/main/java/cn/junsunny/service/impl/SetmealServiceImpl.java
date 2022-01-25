package cn.junsunny.service.impl;

import cn.junsunny.Dao.SetmealDao;
import cn.junsunny.constant.RedisConstant;
import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.pojo.CheckGroup;
import cn.junsunny.pojo.Setmeal;
import cn.junsunny.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐管理接口的实现类
 */
@Service(interfaceClass = SetmealService.class) // 使用Dubbo进行关管理
@Transactional // 添加事务
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    // 添加套餐
    @Override
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds) {
        // 1.进行套餐管理的添加
        setmealDao.addSetMeal(setmeal);
        // 2.设置套餐管理和检查组之间的关联关系
        if (checkGroupIds != null && checkGroupIds.length > 0){
            setSetmealAndCheckGroup(setmeal.getId(),checkGroupIds);
        }
        // 3.将图片名称保存到Redis中
        savePicRedis(setmeal.getImg());
    }

    // 设置套餐管理和检查组之间的关联关系
    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkGroupIds) {
        // 1.定义一个Map集合用来传递参数数据因为Mybatis使用多参数传递时
        // 需要注解或参数名不固定 String 表示参数名 Integer表示传递的ID值
        Map<String,Integer> paramMap = new HashMap<>();
        // 2.设置套餐管理的Id值 因为只有一次 所以赋值一次就行
        paramMap.put("setMealId", setmealId);
        // 3.使用for循环遍历检查组Id的值
        for (Integer checkGroupId : checkGroupIds) {
            paramMap.put("checkGroupId", checkGroupId);
            // 4.向数据库方式请求
            setmealDao.setSetmealAndCheckGroup(paramMap);
        }

    }

    // 将图片名称保存到Redis中
    private void savePicRedis(String picName) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,picName);
    }

    // 分页查询套餐项
    @Override
    public PageResult findPageSetmeal(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> pageMessage = setmealDao.selectByCondition(queryString);
        return new PageResult(pageMessage.getTotal(),pageMessage.getResult());
    }

    // 删除检查套餐对应的数据
    @Override
    public void deleteSetmeal(Integer id, String img) {
        // 删除数据库信息
        setmealDao.deleteSetmeal(id);
        // 删除缓存中的数据信息
        deleteSetMealRidis(img);
        // 删除检查组和检查套餐之间的关系
        deleteSetMealAndCheckGroup(id);
    }

    // 删除redis缓存
    private void deleteSetMealRidis(String img) {
        // 使用redis连接池技术进行数据的删除
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }

    // 删除检查组和套餐之间的关系
    private void deleteSetMealAndCheckGroup(Integer id) {
        setmealDao.deleteSetMealAndCheckGroup(id);
    }

    // 查询检查套餐信息根据id
    @Override
    public Setmeal findSetMealById(Integer id) {
        return setmealDao.findSetMealById(id);
    }

    // 根据检查套餐id 获取检查组信息
    @Override
    public List<Integer> findSetMealAndCheckGroupBySetMealId(Integer id) {
        return setmealDao.findSetMealAndCheckGroupBySetMealId(id);
    }

    // 编辑检查组信息
    @Override
    public void editSetMeal(Setmeal setmeal, Integer[] checkGroupIds) {
        // 1.请求Dao层数据
        setmealDao.editSetMeal(setmeal);
        String newImgName = setmeal.getImg();
        // 2.清除Redis缓存数据
        deleteSetMealRidis(newImgName);
        // 3.将编辑的数据放到缓存中
        savePicRedis(newImgName);
        // 4.清除检查组和检查套餐之间的关系
        deleteSetMealAndCheckGroup(setmeal.getId());
        // 5.设置检查组和检查项之间的关系
        setSetmealAndCheckGroup(setmeal.getId(),checkGroupIds);
    }


}
