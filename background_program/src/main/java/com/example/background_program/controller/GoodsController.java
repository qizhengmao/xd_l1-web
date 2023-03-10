package com.example.background_program.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.common.QueryPageParam;
import com.example.background_program.common.Result;
import com.example.background_program.entity.Goods;
import com.example.background_program.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-02-24
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService iGoodsService;
    //查找
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return iGoodsService.save(goods)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return iGoodsService.updateById(goods)?Result.suc():Result.fail();
    }
    //删除
    @PostMapping("/del")
    public Result del(@RequestParam String id){
        return iGoodsService.removeById(id)?Result.suc():Result.fail();
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam pageParam) {
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName, name);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype, goodstype);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage, storage);
        }

        //IPage result= iUserService.pageC(page);
        IPage result = iGoodsService.pageCC(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

}
