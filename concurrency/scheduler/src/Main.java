import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(2);
        ScheduledTask task1 = new ScheduledTask("task1");
        ScheduledTask task2 = new ScheduledTask("task2");
        ScheduledTask task3 = new ScheduledTask("task3");

        scheduler.scheduleTask(task1, 5, TimeUnit.SECONDS);
        scheduler.scheduleTask(task2, 10, TimeUnit.SECONDS);
        scheduler.scheduleTask(task3, 20, TimeUnit.SECONDS);

        new Thread(() -> {
            try{
                TimeUnit.SECONDS.sleep(25);
            }catch(InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            scheduler.shutDown();
        }).start();
    }
}