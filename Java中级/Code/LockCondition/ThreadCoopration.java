package LockCondition;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadCoopration {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new DepositTask());
        executorService.execute(new withdrawalTask());
        executorService.shutdown();

        while (!executorService.isTerminated()){
        }
        System.out.println("当前余额："+account.getBalance());
    }

    // 随机增加存款任务
    public static class DepositTask implements Runnable{
        @Override
        public void run() {
            try {
                int i = 0;
                while (i < 10){
                    Random random = new Random();
                    account.deposit(random.nextInt(100));
                    i++;
                    Thread.sleep(1000);
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    // 随机取款任务
    public static class withdrawalTask implements Runnable{
        @Override
        public void run() {
            int i = 0;
            while (i < 10){
                Random random = new Random();
                account.withdrawal(random.nextInt(200));
                i++;
            }
        }
    }
}
