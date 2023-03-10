package com.example.background_program.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 货名
     */
    private String name;

    /**
     * 仓库
     */
    private Integer storage;

    /**
     * 分类
     */
    @TableField("goodsType")
    private Integer goodstype;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 备注
     */
    private String remark;


}
