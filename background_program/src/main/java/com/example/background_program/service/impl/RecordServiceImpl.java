package com.example.background_program.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goodstype;
import com.example.background_program.entity.Record;
import com.example.background_program.mapper.GoodstypeMapper;
import com.example.background_program.mapper.RecordMapper;
import com.example.background_program.service.IRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-03-07
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public IPage pageCC(Page<Record> page, Wrapper wrapper) {
        return recordMapper.pageCC(page,wrapper);
    }
}
