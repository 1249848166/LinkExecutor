package su.com.linkexecutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import su.com.library.LinkExecutor;
import su.com.library.Notifier;

public class MainActivity extends AppCompatActivity {

    LinkExecutor linkExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
