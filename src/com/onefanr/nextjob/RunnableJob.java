package com.onefanr.nextjob;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 应该在同一个Job中开启多个线程，而不是使用多个Job来模拟线程
 * Created by Leo on 2016/1/9.
 */
public class RunnableJob extends Job {

    /**
     * 线程池Executor
     */
    private ExecutorService executorService;
    /**
     * 需要执行的任务
     */
    private List<Task> tasks;

    /**
     * 执行状态holder
     */
    private StatusHolder holder;

    public RunnableJob(String id, List<Task> lt, StatusHolder sh) {
        super(id);
        this.tasks = lt;

        sh.setTarget(this);
        this.holder = sh;
    }

    @Override
    public void start() {
        System.out.println("Job " + super.getId() + " start...");

        for(Task t : tasks) {
            //执行外部定义的任务
            Future ft = executorService.submit(t);
            holder.register(ft);
        }

        System.out.println("Job " + super.getId() + " registering complete ...");
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

}
