public class ScheduledTask implements Runnable{
    private final String taskName;
    ScheduledTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("Running task " + this.taskName);
    }
}
