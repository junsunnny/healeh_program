package cn.junsunny.controller;

import cn.junsunny.constant.MessageConstant;
import cn.junsunny.constant.RedisConstant;
import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.entity.Result;
import cn.junsunny.pojo.Setmeal;
import cn.junsunny.service.SetmealService;
import cn.junsunny.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 套餐管理控制层
 */

@RequestMapping("/setmeal")
@RestController
public class SetmealController {

    //使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    // 文件上传
    @RequestMapping("/uploadImage")
    public Result uploadImage(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        // 1.获取原先上传的文件名称及后缀
        String oleFileName = imgFile.getOriginalFilename();
        // 2.获取文件后缀
        // 2.1获取最后以 . 结尾的位置信息
        int lastPiontIndex = oleFileName.lastIndexOf(".");
        // 2.2使用字符串截取的方法获取文件后缀名
        String postfix = oleFileName.substring(lastPiontIndex - 1);
        // 3.图片上传的新的文件名 使用UUID随机生成防止文件名重复 需要加上传入文件名的后缀
        String newFileName = UUID.randomUUID().toString() + postfix;
        // 4.使用七牛的工具类进行图片的上传并返回数据
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),newFileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,newFileName);
    }

    @Reference
    private SetmealService setmealService;

    // 添加套餐项
    @RequestMapping("/addSetmeal")
    public Result addSetMeal(@RequestBody Setmeal setmeal, Integer[] checkGroupIds){
        try{
            setmealService.addSetMeal(setmeal,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    // 分页查询套餐项 findPageSetmeal
    @RequestMapping("/findPageSetmeal")
    public PageResult findPageSetmeal(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPageSetmeal(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }
}
