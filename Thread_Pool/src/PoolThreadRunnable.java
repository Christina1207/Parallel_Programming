public class PoolThreadRunnable implements Runnable {
    private Thread thread=null;
    private final ThreadPool threadPool;
    private boolean isStopped=false;

    public PoolThreadRunnable (ThreadPool pool){
        this.threadPool=pool;
    }

    //Task1
    public void run(){
        this.thread = Thread.currentThread();
        while(!isStopped){
            try{
                Runnable task = threadPool.takeTask();
                if (task==null)break;
                task.run();
            }
            catch(InterruptedException e){
                break;
            }
            catch (Exception e) {
                System.err.println("Error in task execution: " + e.getMessage());
            }
        }
    }

    //Task3
    public synchronized void stop() {
        isStopped = true;
        // Interrupt the thread in case it is sleeping or waiting
        if (this.thread != null) {
            this.thread.interrupt();
        }
    }
}
