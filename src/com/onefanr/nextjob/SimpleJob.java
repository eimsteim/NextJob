package com.onefanr.nextjob;

/**
 * 最简单的只支持单线程的任务处理器
 * Created by Leo on 2015/12/26.
 */
public abstract class SimpleJob extends Job {
    public SimpleJob(String id) {
        super(id);
    }

    /**
     * 开发者自己定义的逻辑
     */
    public abstract void execute();
    /**
     * 任务逻辑入口
     */
    public void start(){
        System.out.println("Job " + super.getId() + " start...");

        execute();

        System.out.println("Job " + super.getId() + " finish...");
        //标记为任务完成
        super.setStatus(1);
    }
}
