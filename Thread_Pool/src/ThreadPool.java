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

    //Task1 : execute and give the task to a thread from the pool
    public synchronized void execute (Runnable task) throws Exception{
        if(this.isStopped) throw new IllegalStateException("Thread Pool is stopped");

        // if the queue is full , no more tasks can be taken now so we wait
        while(this.taskQueue.size()>=maxQueueSize){
            this.wait();
        }

        // add the task to the queue and notify all the threads of the change of state
        this.taskQueue.add(task);
        this.notifyAll();
    }

    protected synchronized Runnable takeTask() throws InterruptedException{

        while (this.taskQueue.isEmpty()) {
            if (this.isStopped) return null; //exit if stopped and empty
            this.wait(); //wait for a task to be added
        }

        Runnable task = this.taskQueue.removeFirst();
        this.activeTasks++;

        // Notify execute() that there is space in the queue
        this.notifyAll();
        return task;
    }

    //Task2 : wait until all tasks are finished
    public synchronized void waitUntilAllTaskFinished(long timeout) {
        long endTime = System.currentTimeMillis() + timeout;

        while (!this.taskQueue.isEmpty() || this.activeTasks > 0) {
            long timeLeft = endTime - System.currentTimeMillis();

            if (timeLeft <= 0) {
                //timeout exceeded, Force stop.
                System.out.println("Timeout reached! Stopping pool...");
                stop();
                return;
            }

            try {
                this.wait(timeLeft);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
    protected synchronized void taskFinished() {
        this.activeTasks--;
        //notify waitUntilAllTaskFinished() to check if it's done
        this.notifyAll();
    }

    //Task3 : Stop the thread pool
    public synchronized void stop(){
        this.isStopped=true;
        this.notifyAll();

        for(PoolThreadRunnable runnable:runnables){
            runnable.stop();//interrupts the threads
        }
    }
}
