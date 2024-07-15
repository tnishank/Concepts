import java.util.concurrent.Semaphore;
class BoundedBufferSemaphore<T>{
    T[] buffer;
    private final int capacity;
    private int size;
    private int head;
    private int tail;
    Semaphore binarySemaphore;
    Semaphore producerSemaphore;
    Semaphore consumerSemaphore;
    @SuppressWarnings("unchecked")
    BoundedBufferSemaphore(int capacity) {
        this.capacity = capacity;
        buffer = (T[]) new Object[capacity];
        size = 0;
        head = 0;
        tail = 0;
        binarySemaphore = new Semaphore(1);
        producerSemaphore = new Semaphore(capacity);
        consumerSemaphore = new Semaphore(0);
    }

    void enqueue(T data) throws InterruptedException{
        producerSemaphore.acquire();
        binarySemaphore.acquire();//3

        if(tail == capacity) {
            tail = 0;
        }

        buffer[tail] = data;
        tail++;
        size++;

        consumerSemaphore.release();
        binarySemaphore.release();

    }

    T dequeue() throws InterruptedException{
        T data = null;
        consumerSemaphore.acquire();
        binarySemaphore.acquire();

        if(head == capacity) {
            head = 0;
        }

        data = buffer[head];
        buffer[head] = null;

        head++;
        size--;

        producerSemaphore.release();
        binarySemaphore.release();

        return data;
    }
}
public class Main {
    public static void main(String[] args) {
        BoundedBufferSemaphore<Integer> buffer = new BoundedBufferSemaphore<>(2);
        Thread producer1 = new Thread(() -> {
            try {
                int count = 1;
                for(int i = 0; i < 4; i++) {
                    buffer.enqueue(count++);
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

        });

        Thread producer2 = new Thread(() -> {
            try {
                int count = 10;
                for(int i = 0; i < 4; i++) {
                    buffer.enqueue(count++);
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

        });

        Thread consumer1 = new Thread(() ->{
            try {
                for (int i = 0; i < 4; i++) {
                    System.out.println("consumer1: " + buffer.dequeue());
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        });

        Thread consumer2 = new Thread(() ->{
            try {
                for (int i = 0; i < 4; i++) {
                    System.out.println("consumer2: " + buffer.dequeue());
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        });

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        try {
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }
}