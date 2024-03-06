import java.util.concurrent.*;

class TaskPool implements Runnable {
    public void run() {
        System.out.println("I am a thread.");
    }
}

public class ThreadPoolExample {
    public static void main(String[] args) {
        int numTasks = 5;

        // Docs https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html

        /* Create the thread pool */
        ExecutorService pool = Executors.newCachedThreadPool();

        /* Run each task using a thread in the pool */
        for (int i = 0; i < numTasks; i++)
            pool.execute(new TaskPool());
        
        /* Shut down the pool once all threads have completed */
        pool.shutdown();
    }
}
