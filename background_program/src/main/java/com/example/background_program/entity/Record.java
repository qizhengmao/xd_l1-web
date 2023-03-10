package com.example.background_program.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 货品id
     */
    private Integer goods;

    /**
     * 取货人/补货人
     */
    @TableField("userId")
    private Integer userid;

    /**
     * 操作人id
     */
    private Integer adminId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 操作时间
     */
    private LocalDateTime createtime;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private String action;


}
