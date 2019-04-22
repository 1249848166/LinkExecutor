![img](gif5新文件.gif)
## 使用
```java

LinkExecutor linkExecutor;
linkExecutor=new LinkExecutor();
linkExecutor.execute(new LinkExecutor.BeforeExecutor() {
    @Override
    public void preHandle() {//所有任务前执行
        Log.i("LinkExecutor","执行任务之前，可能需要做一些预处理");
    }
}, new LinkExecutor.AfterExecutor() {
    @Override
    public void onFinishAll() {//所有任务执行结束后执行
        Log.i("LinkExecutor","所有任务执行结束后，可能需要做些处理");
    }
}, new LinkExecutor.Executor() {
    @Override
    public void execute(Notifier notifier) {//执行任务1
        Log.i("LinkExecutor","执行任务1");
        notifier.notifyTaskFinish();
    }
}, new LinkExecutor.Executor() {
    @Override
    public void execute(final Notifier notifier) {//执行任务2
        Log.i("LinkExecutor","开始执行任务2");
        //开启线程执行异步耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime=System.currentTimeMillis();
                while (System.currentTimeMillis()-startTime<5000){//5秒内重复执行
                    Log.i("LinkExecutor","正在执行任务2当中。。。");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                notifier.notifyTaskFinish();
            }
        }).start();
    }
}, new LinkExecutor.Executor() {
    @Override
    public void execute(Notifier notifier) {//执行任务3
        Log.i("LinkExecutor","执行任务3");
        notifier.notifyTaskFinish();
    }
});

```
将线程放入任务顺序链中，线程会执行结束后执行下一个任务，任务执行完后需要调用notifier通知下一个任务执行，否则将无法执行后续任务。