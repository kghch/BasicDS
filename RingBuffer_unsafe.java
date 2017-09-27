class IOQueue {
    // 头进尾出
    //ReentrantLock lock;
    private int head;
    private int tail;
    private char[] buffer;
    private final int size = 8;

    IOQueue() {
        //lock = new ReentrantLock();
        head = 0;
        tail = 0;
        buffer = new char[size];
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

    public void put(char x) {
        if (!isFull()) {
            buffer[head] = x;
            head ++;
        } else {
            System.out.println("full queue");
        }
    }

    public char get() throws Exception {
        if (!isEmpty()) {
            char x = buffer[tail++];
            return x;
        }
        throw new Exception("empty queue");
    }

}