package com.bxoon.config;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bxoon.constant.Constants;
import com.bxoon.constant.ScheduleConstants;
import com.bxoon.job.domain.SysJob;
import com.bxoon.job.domain.SysJobLog;
import com.bxoon.service.ISysJobLogService;
import com.bxoon.uitl.BeanUtils;
import com.bxoon.uitl.ScheduleRunnable;
import com.bxoon.uitl.SpringContextUtil;
import com.bxoon.uitl.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务处理
 * 
 * @author Runner
 *
 */
@Slf4j
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean
{
    private ExecutorService service = Executors.newSingleThreadExecutor();

    private final static ISysJobLogService jobLogService = (ISysJobLogService) SpringContextUtil.getBean("sysJobLogServiceImpl");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        SysJob job = new SysJob();
        BeanUtils.copyBeanProp(job, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));

        SysJobLog jobLog = new SysJobLog();
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setMethodName(job.getMethodName());
        jobLog.setMethodParams(job.getMethodParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try
        {
            // 执行任务
            log.info("任务开始执行 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            ScheduleRunnable task = new ScheduleRunnable(job.getJobName(), job.getMethodName(), job.getMethodParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.SUCCESS);
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");

            log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", job.getJobName(), times);
        }
        catch (Exception e)
        {
            log.info("任务执行失败 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            log.error("任务执行异常  - ：", e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.FAIL);
            jobLog.setExceptionInfo(StringUtils.substring(e.getMessage(), 0, 2000));
        }
        finally
        {
            jobLogService.addJobLog(jobLog);
        }
    }
}
