package com.junsunny.jobs;

import cn.junsunny.constant.RedisConstant;
import cn.junsunny.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        // 根据redis中保存的两个set集合进行差值运算 获取垃圾图片名称集合
        Set<String> differentImgName = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String claerImgName : differentImgName) {
            // 1.删除七牛云数据
            QiniuUtils.deleteFileFromQiniu(claerImgName);
            // 2.删除redis缓存数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,claerImgName);
            System.out.println("自定义任务执行 清理垃圾图片" + claerImgName);
        }
    }
}
