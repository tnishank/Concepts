
public class Main {
    public static void main(String[] args) {
        BoundedBufferSynchronized<Integer> buffer = new BoundedBufferSynchronized<>(3);

        Thread producer1 = new Thread(() -> {
            int start = 100;
            try{
                for(int i = 0; i < 10; i++) {
                    System.out.println("producer1: " + ++start);
                    buffer.enqueue(start);
                }
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        });

        Thread producer2 = new Thread(() -> {
            int start = 200;
            try{
                for(int i = 0; i < 10; i++) {
                    System.out.println("producer2: " + ++start);
                    buffer.enqueue(start);
                }
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        });

        Thread consumer1 = new Thread(() -> {
            try{
                for(int i = 0; i < 10; i++){
                    System.out.println("consumer 1 " + buffer.dequeue());
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        });

        Thread consumer2 = new Thread(() -> {
            try{
                for(int i = 0; i < 10; i++){
                    System.out.println("consumer2 " + buffer.dequeue());
                }
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        });

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try{
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }
}

class BoundedBufferSynchronized <T>{
    T[] buffer;
    private final int capacity;
    int size;
    int head;
    int tail;

    @SuppressWarnings("unchecked")
    BoundedBufferSynchronized(int capacity) {
        this.capacity = capacity;
        buffer = (T[]) new Object[capacity];
        size = 0;
        head = 0;
        tail = 0;
    }
    synchronized void enqueue(T data) throws InterruptedException{
        while(size == capacity) {
            wait();
        }

        if(tail == capacity) {
            tail = 0;
        }

        buffer[tail] = data;
        tail++;
        size++;

        notifyAll();
    }

    synchronized T dequeue() throws InterruptedException{
        while(size == 0) {
            wait();
        }

        if(head == capacity) {
            head = 0;
        }

        T data = buffer[head];
        head++;
        size--;

        notifyAll();

        return data;
    }
}