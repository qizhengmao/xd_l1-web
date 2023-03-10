package com.example.background_program.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.entity.Stoage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.background_program.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2023-02-23
 */
public interface IStoageService extends IService<Stoage> {

    IPage pageCC(Page<Stoage> page, Wrapper wrapper);
}
