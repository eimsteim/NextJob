package com.onefanr.nextjob;

//import com.onefanr.nextjob.sample.MyConcurrentJob;

import com.sun.deploy.util.StringUtils;

/**
 * Created by Leo on 2016/1/3.
 */
public abstract class Task implements Runnable {

    private String id;

    public Task(String id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(id + " start...");
        try {
            System.out.println(id + " running...");

            this.execute();

            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(id + " finish...");
    }

    public abstract void execute();

}
