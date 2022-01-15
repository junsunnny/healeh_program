package cn.junsunny.controller;

import cn.junsunny.constant.MessageConstant;
import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.entity.Result;
import cn.junsunny.pojo.CheckItem;
import cn.junsunny.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference // 查找服务
    private CheckItemService checkItemService;

    // 新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            // 服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    // 检查项分页查新
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        return pageResult;
    }
}
