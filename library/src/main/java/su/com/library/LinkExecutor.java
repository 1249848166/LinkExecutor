package su.com.library;

import android.support.annotation.Nullable;

public class LinkExecutor implements Notifier {

    private AfterExecutor afterExecutor;
    private Executor[] executors;
    private int len;
    private int index;

    public interface BeforeExecutor{
        void preHandle();
    }

    public interface AfterExecutor{
        void onFinishAll();
    }

    public interface Executor{
        void execute(Notifier notifier);
    }

    public void execute(@Nullable BeforeExecutor beforeExecutor,@Nullable AfterExecutor afterExecutor, Executor... executors){
        if(beforeExecutor!=null){
            beforeExecutor.preHandle();
        }
        this.len=executors.length;
        this.index=0;
        this.afterExecutor=afterExecutor;
        this.executors=executors;
        executeNextTask(len,index,executors);
    }

    private void executeNextTask(final int len, final int index, final Executor... executors){
        Executor executor=null;
        if(index<len)
            executor=executors[index];
        if (executor != null) {
            executor.execute(this);
        }
    }

    @Override
    public void notifyTaskFinish() {
        index+=1;
        if(index==len){
            if(afterExecutor!=null)
                afterExecutor.onFinishAll();
            afterExecutor=null;
            executors=null;
        }else{
            executeNextTask(len,index,executors);
        }
    }
}
