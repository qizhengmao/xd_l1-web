package com.example.background_program.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.common.QueryPageParam;
import com.example.background_program.common.Result;
import com.example.background_program.entity.Goods;
import com.example.background_program.entity.Record;
import com.example.background_program.service.IGoodsService;
import com.example.background_program.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private IRecordService iRecordService;
    @Autowired
    private IGoodsService iGoodsService;
    //查找
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam pageParam) {
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");
        String roleId = (String) param.get("roleId");
        String userId = (String) param.get("userId");

        Page<Record> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply("a.goods=b.id AND b.storage=c.id AND b.goodsType=d.id");
        if ("2".equals(roleId)){
            queryWrapper.apply("a.userId="+userId);
        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.like("d.id",goodstype);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.like("c.id",storage);
        }

        IPage result = iRecordService.pageCC(page, queryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
    //查找
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods = iGoodsService.getById(record.getGoods());
        int n = record.getCount();
        if ("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }
        int num = goods.getCount()+n;
        goods.setCount(num);
        iGoodsService.updateById(goods);
        return iRecordService.save(record)?Result.suc():Result.fail();
    }
}
