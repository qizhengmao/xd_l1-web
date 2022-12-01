package com.example.background_program.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.common.QueryPageParam;
import com.example.background_program.common.Result;
import com.example.background_program.entity.User;
import com.example.background_program.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping("/list")
    public List<User> list(){
        return iUserService.list();
    }
    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return iUserService.save(user);
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return iUserService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveorMod")
    public boolean saveorMod(@RequestBody User user){
        return iUserService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iUserService.removeById(id);
    }
    //查询（模糊，匹配）
    @PostMapping("/listp")
    public List<User> listp(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,user.getName());
        return iUserService.list(lambdaQueryWrapper);
    }
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam pageParam){
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");
        System.out.println("param="+param.get("name"));

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        IPage result= iUserService.page(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());
        return result.getRecords();
    }
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam pageParam){
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);
        //IPage result= iUserService.pageC(page);
        IPage result= iUserService.pageCC(page,lambdaQueryWrapper);



        System.out.println(result.getTotal());
        return result.getRecords();
    }
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam pageParam) {
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);
        //IPage result= iUserService.pageC(page);
        IPage result = iUserService.pageCC(page, lambdaQueryWrapper);


        System.out.println(result.getTotal());
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
