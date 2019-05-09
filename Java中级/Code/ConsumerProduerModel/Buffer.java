package ConsumerProduerModel;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private static final int CAPACITY = 1;
    private LinkedList<Integer> queue = new LinkedList<>();
    private static Lock lock = new ReentrantLock();

    // 缓冲区非空
    private static Condition notEmpty = lock.newCondition();
    // 缓冲区非满
    private static Condition notFull = lock.newCondition();

    // 写入数据
    public void write(int value){
        lock.lock();
        try {
            while (queue.size() == CAPACITY){
                System.out.println("等待非满锁条件");
                notFull.await();
            }
            queue.offer(value);
            notEmpty.signal();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 读取数据
    public int read(){
        int value = 0;
        lock.lock();
        try {
            while (queue.isEmpty()){
                System.out.println("等待非空锁条件");
                notEmpty.await();
            }
            value = queue.remove();
            notFull.signal();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
        return value;
    }
}
