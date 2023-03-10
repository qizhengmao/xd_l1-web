package com.example.background_program.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Goodstype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.background_program.entity.Stoage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2023-02-23
 */
@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {
    IPage pageCC(Page<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
