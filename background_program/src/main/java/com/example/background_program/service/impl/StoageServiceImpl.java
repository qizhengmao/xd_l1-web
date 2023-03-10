package com.example.background_program.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.background_program.entity.Stoage;
import com.example.background_program.mapper.StoageMapper;
import com.example.background_program.service.IStoageService;
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
public class StoageServiceImpl extends ServiceImpl<StoageMapper, Stoage> implements IStoageService {

    @Autowired
    private StoageMapper stoageMapper;
    @Override
    public IPage pageCC(Page<Stoage> page, Wrapper wrapper) {
        return stoageMapper.pageCC(page,wrapper);
    }
}
