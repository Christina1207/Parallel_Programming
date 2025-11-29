public class PoolThreadRunnable implements Runnable {
    private Thread thread=null;
    private final ThreadPool threadPool;
    private boolean isStopped=false;

    public PoolThreadRunnable (ThreadPool pool){
        this.threadPool=pool;
    }
    public void run(){

    }

}
