package com.example.background_program.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goods;
import com.example.background_program.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-03-07
 */
public interface IRecordService extends IService<Record> {
    IPage pageCC(Page<Record> page, Wrapper wrapper);
}
