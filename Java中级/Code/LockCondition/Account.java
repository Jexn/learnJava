package LockCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private static Lock lock = new ReentrantLock();
    private static Condition newDeposit = lock.newCondition();
    private int balance = 0;

    public int getBalance(){
        return balance;
    }

    // 取款
    public void withdrawal(int amount){
        lock.lock();
        System.out.println("取款金额："+amount);
        try {
            // 取款大于存款
            while (balance < amount){
                System.out.println("余额不足，当前存款："+balance);
                // 停止当前取款进程
                newDeposit.await();
            }

            // 存款大于取款
            balance -= amount;
            System.out.println("取款成功！取款："+amount+",当前余额："+getBalance());
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    // 存款
    public void deposit(int amount){
        lock.lock();
        try {
            balance += amount;
            System.out.println("存款："+amount+"，当前余额："+getBalance());
            // 通知唤醒取款进程
            newDeposit.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
