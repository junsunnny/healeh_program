package cn.junsunny.controller;

import cn.junsunny.constant.MessageConstant;
import cn.junsunny.entity.PageResult;
import cn.junsunny.entity.QueryPageBean;
import cn.junsunny.entity.Result;
import cn.junsunny.pojo.CheckGroup;
import cn.junsunny.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 检查组管理
 */
@RequestMapping("/checkgroup")
@RestController
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    // 添加
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        try{
            checkGroupService.add(checkGroup,checkItemIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    // 分页查询检查组
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;

    }

    // 添加
    @RequestMapping("/del")
    public Result del(Integer id){
        try{
            checkGroupService.del(id);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    // 查询检查组信息
    @RequestMapping("/findCheckGroup")
    public Result finCheckGroup(Integer id){
        try{
            CheckGroup checkGroup = checkGroupService.finCheckGroup(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    @RequestMapping("/findCheckGroupAndCheckItem")
    public Result findCheckGroupAndCheckItem(Integer id){
        try{
            // System.out.println(id);
            List<Integer> checkItemIds = checkGroupService.findCheckGroupAndCheckItem(id);
            // System.out.println(checkItemIds);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItemIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    // 编辑表单项
    @RequestMapping("/editCheckGroup")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        try{
            checkGroupService.editCheckGroup(checkGroup,checkItemIds);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    // 查询检查组所有的信息
    @RequestMapping("/findAllCheckGroup")
    public Result findAllCheckGroup() {
        try{
            List<CheckGroup> checkGroups = checkGroupService.findAllCheckGroup();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}
