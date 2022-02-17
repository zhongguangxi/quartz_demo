package com.bxoon.job.task;

import org.springframework.stereotype.Component;

@Component
public class TestJob2 {

    protected void exec(){
        // 任务的具体逻辑
        System.out.println(222);
    }
}