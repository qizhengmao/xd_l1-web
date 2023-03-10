package com.example.background_program.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.common.QueryPageParam;
import com.example.background_program.common.Result;
import com.example.background_program.entity.Menu;
import com.example.background_program.entity.Stoage;
import com.example.background_program.entity.User;
import com.example.background_program.service.IStoageService;
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
 * @since 2023-02-23
 */
@RestController
@RequestMapping("/stoage")
public class StoageController {
    @Autowired
    private IStoageService iStoageService;
    //查找
    @PostMapping("/save")
    public Result save(@RequestBody Stoage stoage){
        return iStoageService.save(stoage)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Stoage stoage){
        return iStoageService.updateById(stoage)?Result.suc():Result.fail();
    }
    //删除
    @PostMapping("/del")
    public Result del(@RequestParam String id){
        return iStoageService.removeById(id)?Result.suc():Result.fail();
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam pageParam) {
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");

        Page<Stoage> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<Stoage> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Stoage::getName, name);
        }
        //IPage result= iUserService.pageC(page);
        IPage result = iStoageService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
    @GetMapping("/list")
    public Result list(){
        List list = iStoageService.lambdaQuery().list();
        return Result.suc(list);
    }
}
