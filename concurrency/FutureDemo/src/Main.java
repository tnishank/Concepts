import java.util.Random;
import java.util.concurrent.*;

class Task<T> implements Callable<T> {
    @Override
    public T call() throws Exception {
        Random random = new Random();
        Integer sum = random.nextInt(1000);

        try{
            for(int i = 0; i < 10; i++) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("currentThread interrupted");
                    return null;
                }
                Thread.sleep(100);
                sum += i;
            }
        }catch (InterruptedException ie){
            Thread.currentThread().interrupt();
            System.out.println("Task interrupted during sleep");
            return null;
        }

        return (T) sum;
    }
}

class FutureDemo{
    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try{
            Task<Integer> t1 = new Task<>();
            Task<Integer> t2 = new Task<>();

            Future<Integer> f1 = executor.submit(t1);
            Future<Integer> f2 = executor.submit(t2);

            while (!f1.isDone()) {
                System.out.println("waiting for first task to complete");
            }

            System.out.println("value of f1: " + f1.get());

            f2.cancel(true);

            if (f2.isCancelled()){
                System.out.println("f2 is cancelled");
            }else{
                System.out.println("f2 is not cancelled");
            }
            System.out.println("is cancelled f2: " + f2.isCancelled());
        }finally {
            executor.shutdown();
            if(!executor.awaitTermination(1, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }
        }

    }
}