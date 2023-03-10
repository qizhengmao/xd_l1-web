package com.example.background_program.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goods;
import com.example.background_program.entity.Goodstype;
import com.example.background_program.mapper.GoodsMapper;
import com.example.background_program.mapper.GoodstypeMapper;
import com.example.background_program.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-02-24
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public IPage pageCC(Page<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page,wrapper);
    }
}
