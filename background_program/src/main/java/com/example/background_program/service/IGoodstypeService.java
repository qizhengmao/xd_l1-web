package com.example.background_program.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.background_program.entity.Stoage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-02-23
 */
public interface IGoodstypeService extends IService<Goodstype> {
    IPage pageCC(Page<Goodstype> page, Wrapper wrapper);
}
