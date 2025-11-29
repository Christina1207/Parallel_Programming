import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Runnable> taskQueue = new LinkedList<>();
    private final List<PoolThreadRunnable> runnables = new ArrayList<>();

    private boolean isStopped = false;
    private final int maxQueueSize;
    private int activeTasks = 0;


    public ThreadPool (int numOfThreads, int maxQueueSize){
        this.maxQueueSize=maxQueueSize;

        //creating threads for the pool
        for(int i=0;i<numOfThreads; i++)
        {
            PoolThreadRunnable runnable = new PoolThreadRunnable(this);
            runnables.add(runnable);
        }

        //starting all the threads
        for (PoolThreadRunnable runnable:runnables)
        {
            new Thread(runnable).start();
        }
    }


}
