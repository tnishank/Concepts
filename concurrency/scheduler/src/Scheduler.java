import java.util.concurrent.*;

public class Scheduler {
    private final ScheduledExecutorService scheduler;
    Scheduler(int poolSize) {
        this.scheduler = Executors.newScheduledThreadPool(poolSize);
    }

    ScheduledFuture<?> scheduleTask(ScheduledTask task, long delay, TimeUnit unit) {
        return scheduler.schedule(task, delay, unit);
    }

    public void shutDown(){
        //Try to shut down gracefully
        scheduler.shutdown();

        try{
            //It verifies if scheduler shut down
            if(!this.scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                //Try to shut down forcefully
                scheduler.shutdownNow();
                if(!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("Scheduler failed to shutdown");
                }
            }

        }catch(InterruptedException ie) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
