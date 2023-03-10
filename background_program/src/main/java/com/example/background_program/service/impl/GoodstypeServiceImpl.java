package com.example.background_program.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goodstype;
import com.example.background_program.mapper.GoodstypeMapper;
import com.example.background_program.mapper.StoageMapper;
import com.example.background_program.service.IGoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-02-23
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements IGoodstypeService {
    @Autowired
    private GoodstypeMapper goodstypeMapper;
    @Override
    public IPage pageCC(Page<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.pageCC(page,wrapper);
    }
}
