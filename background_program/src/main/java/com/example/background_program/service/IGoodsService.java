package com.example.background_program.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.background_program.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-02-24
 */
public interface IGoodsService extends IService<Goods> {
    IPage pageCC(Page<Goods> page, Wrapper wrapper);
}
