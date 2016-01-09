package com.onefanr.nextjob.sample;

import com.onefanr.nextjob.RunnableJob;
import com.onefanr.nextjob.StatusHolder;
import com.onefanr.nextjob.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leo on 2016/1/9.
 */
public class SampleRunnableJobTrigger {

    public static void main(String[] args) {
        //初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        StatusHolder holder = new StatusHolder();

        List<Task> list = new ArrayList<Task>();

        //定义每个Task应该实现的逻辑
        Task t_1 = new Task("task_1001"){

            @Override
            public void execute() {
                System.out.println("执行t_1定义的方法逻辑...");
            }
        };

        Task t_2 = new Task("task_1002"){

            @Override
            public void execute() {
                System.out.println("执行t_2定义的方法逻辑...");
            }
        };

        Task t_3 = new Task("task_1003"){

            @Override
            public void execute() {
                System.out.println("执行t_3定义的方法逻辑...");
            }
        };

        list.add(t_1); list.add(t_2); list.add(t_3);

        RunnableJob job_1 = new RunnableJob("runjob_1", list, holder);
        job_1.setExecutorService(executorService);

        job_1.start();

        MySimpleJob job_2 = new MySimpleJob("simplejob_2");
        job_1.addObserver(job_2);


        holder.startCheck();
    }

}
