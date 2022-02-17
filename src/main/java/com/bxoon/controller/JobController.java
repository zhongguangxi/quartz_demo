package com.bxoon.controller;

import com.bxoon.dto.AjaxResult;
import com.bxoon.dto.JobSearchForm;
import com.bxoon.dto.ResultInfo;
import com.bxoon.exception.TaskException;
import com.bxoon.job.domain.SysJob;
import com.bxoon.service.ISysJobService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/monitor/job")
public class JobController {

	@Autowired
    private ISysJobService jobService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public AjaxResult getJobList(@Validated JobSearchForm form){
		SysJob job = new SysJob();
		job.setStatus(form.getStatus());
		job.setJobName(form.getJobName());
		job.setMethodName(form.getMethodName());
		return AjaxResult.success().put("list", jobService.selectJobList(job));
	}

	@RequestMapping(value = "/info/{jobId}", method = RequestMethod.GET)
	@ApiOperation(value = "查看定时任务信息", notes = "查看定时任务信息")
	public ResultInfo<SysJob> getJobInfo(
			@ApiParam(name = "jobId", value = "定时任务ID", required = true) @PathVariable Long jobId){
		return ResultInfo.success(jobService.selectJobById(jobId));
	}

	@RequestMapping(value = "/changeStatus/{jobId}/{status}", method = RequestMethod.PUT)
	@ApiOperation(value = "修改定时任务状态", notes = "修改定时任务状态")
	public AjaxResult changeStatus(
			@ApiParam(name = "jobId", value = "定时任务ID", required = true) @PathVariable Long jobId,
			@ApiParam(name = "status", value = "状态0-正常 1-暂停", required = true) @PathVariable String status)  throws SchedulerException {
		SysJob job = new SysJob();
		job.setStatus(status);
		job.setId(jobId);
		int rows = jobService.changeStatus(job);
		return rows > 0 ? AjaxResult.success() : AjaxResult.error();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "新增定时任务", notes = "新增定时任务")
	public AjaxResult addJob(SysJob job)  throws SchedulerException, TaskException {
		int rows = jobService.insertJobCron(job);
		return rows > 0 ? AjaxResult.success() : AjaxResult.error();
	}

	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	@ApiOperation(value = "批量删除定时任务", notes = "批量删除定时任务")
	public AjaxResult deleteJob(String ids)  throws SchedulerException {
		jobService.deleteJobByIds(ids);
		return AjaxResult.success();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	@ApiOperation(value = "修改定时任务", notes = "修改定时任务")
	public AjaxResult editJob(SysJob job) throws SchedulerException, TaskException {
		int rows = jobService.updateJobCron(job);
		return rows > 0 ? AjaxResult.success() : AjaxResult.error();
	}

    @RequestMapping(value = "/checkCronExpressionIsValid", method = RequestMethod.GET)
	@ApiOperation(value = "校验cron表达式是否有效", notes = "校验cron表达式是否有效")
    public boolean checkCronExpressionIsValid(SysJob job)
    {
        return jobService.checkCronExpressionIsValid(job.getCronExpression());
    }

    @RequestMapping(value = "/run", method = RequestMethod.PUT)
	@ApiOperation(value = "定时任务立即执行一次", notes = "定时任务立即执行一次")
    public AjaxResult run(SysJob job) throws SchedulerException {
    	jobService.run(job);
    	return AjaxResult.success();
    }
}
