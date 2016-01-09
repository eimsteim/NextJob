package com.onefanr.nextjob.sample;

import com.onefanr.nextjob.SimpleJob;

/**
 * Created by Leo on 2015/12/26.
 */
public class MySimpleJob extends SimpleJob {


    public MySimpleJob(String name) {
        super(name);
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(3000);
            System.out.println("执行" + super.getId() + "实现的逻辑代码");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
