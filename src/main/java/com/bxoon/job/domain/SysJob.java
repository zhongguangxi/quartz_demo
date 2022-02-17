package com.bxoon.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 定时任务调度表
 */
@Data
@TableName(value = "sys_job")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 任务名称
     */
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @TableField(value = "job_group")
    private String jobGroup;

    /**
     * 任务方法
     */
    @TableField(value = "method_name")
    private String methodName;

    /**
     * 方法参数
     */
    @TableField(value = "method_params")
    private String methodParams;

    /**
     * cron执行表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @TableField(value = "misfire_policy")
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    @TableField(value = "concurrent")
    private String concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    private String remark;
}