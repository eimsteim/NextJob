# NextJob
A dependency schedule framework.

依赖式定时任务框架 NextJob 是为了解决在多个定时任务执行时，存在依赖关系的问题。
> 适用场景：

1、任务B的执行需要依赖任务A的数据，因此任务B必须在任务A执行完毕后才能触发。

2、任务B的执行需要依赖任务A和任务C的数据，因此任务B必须在A和C都执行完毕后才能触发。

> 其用法大体上分为两种：

1、以SimpleJob为基类的单线程处理

1）创建业务逻辑Job，继承自 SimpleJob，实现其execute()，业务逻辑写在execute()中

2）初始化各Job及其依赖关系（如果使用Spring，可以在配置中实现）

```java
//初始化Job
Job job_1 = new MySimpleJob("Job_1");
Job job_2 = new MySimpleJob("Job_2");
Job job_3 = new MySimpleJob("Job_3");

//job_1改变时通知job_2和job_3
job_1.addObserver(job_2);
job_2.addObserver(job_3);
```
3）启动入口Job（如果使用Spring，可以用cronExpression的方式定时启动）
```java 
//改变job_1的状态
job_1.start();
```
2、以RunnableJob为基类的多线程处理

RunnableJob和SimpleJob最大的区别就在于前者不需要实现自己的业务逻辑Job，也不需要实现execute()，而是直接使用RunnableJob本身即可，业务逻辑可以使用自带的Task类，在初始化Task时重写execute()即可。使用方法：

1）初始化线程池
```java 
ExecutorService executorService = Executors.newFixedThreadPool(3);
```
2）初始化状态池（StatusHolder）
```java 
StatusHolder holder = new StatusHolder();
```
3）初始化任务List
```java 
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
```
4）初始化Job
```java 
RunnableJob job_1 = new RunnableJob("runjob_1", list, holder);
job_1.setExecutorService(executorService);
```
5）启动Job
```java 
job_1.start();
```
6）初始化依赖关系
```java 
MySimpleJob job_2 = new MySimpleJob("simplejob_2");
job_1.addObserver(job_2);
```
7）启动状态池监控
```java 
holder.startCheck();
```

> 对StatusHolder的说明：

StatusHolder是一个独立的概念，专用于多线程处理时保存线程执行状态，当执行holder.startCheck()时，即会开启一个专用线程去检查上述初始化的RunnableJob中所有线程的处理状态，只有当所有线程都执行完毕，才会通知其观察者可以启动。因此如果需要使用RunnableJob，请务必按照Sample中的用法，在配置完成后执行holder.startCheck()。
