package com.onefanr.nextjob.sample;

import com.onefanr.nextjob.Job;

/**
 * 模拟链式Job调用
 * Created by Leo on 2015/12/26.
 */
public class SampleLinkedJobTrigger {
    public static void main(String[] args) {
        //初始化Job
        Job job_1 = new MySimpleJob("Job_1");
        Job job_2 = new MySimpleJob("Job_2");
        Job job_3 = new MySimpleJob("Job_3");


        //job_1改变时通知job_2和job_3
        job_1.addObserver(job_2);
        job_2.addObserver(job_3);

        //改变job_1的状态
        job_1.start();
    }
}
