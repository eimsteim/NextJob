package com.onefanr.nextjob;

import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Job即为一个任务，任务既可以观察其所依赖的任务，也可以被其他任务观察
 * 因此既是观察者，也是被观察者
 * Created by Leo on 2015/12/26.
 */
public abstract class Job extends Observable implements Observer {

    /**
     * Job名称，必填
     */
    private String id;
    /**
     * Job状态，1：完成，0：未完成
     */
    private int status = 0;

    /**
     * 通知者
     */
    private Vector<Job> notifiers;

    public Job(String id) {
        this.id = id;
        if(notifiers == null) {
            notifiers = new Vector<Job>();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //获取所有的通知者
        Enumeration<Job> en = notifiers.elements();
        Boolean completeAll = Boolean.TRUE;
        //查看通知者状态是否完成
        while(en.hasMoreElements()) {
            Job el = en.nextElement();
            if(el.getStatus() != 1) {
                completeAll = Boolean.FALSE;
                System.out.println(el.getId() + "未完成，暂不执行" + this.getId());
                break;
            }
        }
        //只有当所有通知者状态都完成了，才能执行execute()
        if(completeAll) {
            start();
        }
    }

    public int getStatus() {
        return status;
    }

    protected void setStatus(int status) {

        this.status = status;
        if(status == 1) { //只有当标记为完成的时候才通知下一个Job
            setChanged();
            notifyObservers(status);
        }
    }

    public abstract void start();

    @Override
    public synchronized void addObserver(Observer observer) {
        super.addObserver(observer);

        //反向绑定通知者
        ((Job)observer).addNotifier(this);
    }

    private void addNotifier(Job notifier){
        notifiers.addElement(notifier);
    }

    public String getId() {
        return id;
    }
}
