package com.onefanr.nextjob;

import java.util.*;
import java.util.concurrent.Future;

/**
 * 用来保存同一个事务的多线程任务执行状态存储器
 * Created by Leo on 2016/1/3.
 */
public class StatusHolder {

    private List<Future> status;

    private Job target;

    public StatusHolder() {
        if(status == null)
            status = new ArrayList<Future>();

    }

    /**
     * 注册
     * 初始化一个线程的状态，刚初始化的状态都是0
     */
    public void register(Future future) {
        status.add(future);
    }

    /**
     * 开始检查所有异步Job是否完成
     */
    public boolean startCheck() {


        boolean allDone = false;

        int allSize = status.size();

        int count = 0;

        while(count < allSize) {
            for(Future ft : status) {
                if(ft.isDone()) {
                    count++;
                } else {
                    System.out.println("Future not done...");

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //通知目标job
        target.setStatus(1);

        return allDone;
    }

    public void setTarget(Job target) {
        this.target = target;
    }
}
