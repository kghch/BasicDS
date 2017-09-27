public class hello extends Thread{
    public static void main(String[] args) {
        IOQueue queue = new IOQueue();
        Thread producer = new Thread() {
            public void run() {
                for (int i=0; i<26; i++) {
                    queue.put(i);
                }
            }
        };
        Thread consumer = new Thread() {
            public void run() {
                while (true) {
                    queue.get();
                }
            }
        };
        Thread consumer2 = new Thread() {
            public void run() {
                while (true) {
                    queue.get();
                }
            }
        };
        consumer.start();
        consumer2.start();
        producer.start();

    }
}

class IOQueue {
    // 头进尾出
    ReentrantLock lock;
    private int head;
    private int tail;
    private int[] buffer;
    private final int size = 8;
    private Condition notFull;
    private Condition notEmpty;

    IOQueue() {
        lock = new ReentrantLock();
        head = 0;
        tail = 0;
        buffer = new int[size];
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    private int nextPos(int x) {
        return (x+1) % size;
    }

    private boolean isFull(){
        return nextPos(head) == tail;
    }

    private boolean isEmpty() {
        return head == tail;
    }

    public void put(int x) {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }
            buffer[head] = x;
            head = nextPos(head);
            notEmpty.signalAll();
            //System.out.println("Produce: " + x);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public int get() {
        lock.lock();
        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            int x = buffer[tail];
            tail = nextPos(tail);
            notFull.signalAll();
            System.out.println("Consume: " + x);
            return x;
        } catch (Exception e) {
            return 0;
        } finally {
            lock.unlock();
        }
    }

}
