package com.bxoon.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 定时任务调度日志表
    */
@Data
@TableName(value = "sys_job_log")
public class SysJobLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务日志ID
     */
    @TableId(value = "job_log_id", type = IdType.INPUT)
    private Integer jobLogId;

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
     * 日志信息
     */
    @TableField(value = "job_message")
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 异常信息
     */
    @TableField(value = "exception_info")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}