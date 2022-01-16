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

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference // 查找服务
    private CheckItemService checkItemService;

    // 新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        Integer id = checkItem.getId();
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

    // 删除检查项
    @RequestMapping("/delCheckItem")
    public Result delCheckItem(Integer id){
        try {
            checkItemService.delCheckItem(id);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);

    }

    // 根据id查询检查项
    @RequestMapping("/findCheckItem")
    public Result findCheckItem(Integer id){
        CheckItem checkItemRes = null;
        try {
            checkItemRes = checkItemService.findCheckItemById(id);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemRes);
    }

    // 修改检查项数据
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.updateCheckItem(checkItem);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    // 查询检查项所有功能
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> checkItemsAll = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemsAll);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

}
