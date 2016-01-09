package com.onefanr.nextjob.sample;

import com.onefanr.nextjob.Job;

/**
 * 模拟多个Job完成后才能通知后续Job
 * Created by Leo on 2015/12/26.
 */
public class SampleMultiDependJobTrigger {
    public static void main(String[] args) {
        //初始化Job
        Job job_1 = new MySimpleJob("Job_1");
        Job job_2 = new MySimpleJob("Job_2");
        Job job_3 = new MySimpleJob("Job_3");

        //job_3同时观察job_1和job_2
        job_1.addObserver(job_3);
        job_2.addObserver(job_3);

        //job_1和job_2同时完成时，才能启动job_3
        job_1.start();
        job_2.start();
    }
}
