import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountWithoutSync {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一百个线程并发读写数据
        for (int i = 0; i < 100;i++){
            executorService.execute(new AddApennyTask());
        }

        executorService.shutdown();

        while (!executorService.isTerminated()){
        }

        System.out.println("What is balance?"+account.getBalance());
    }

    // 这个任务往balance加1
    private static class AddApennyTask implements Runnable{
        @Override
        public void run() {
            account.deposit(1);
        }
    }

    private static class Account{
        private int balance = 0;

        public int getBalance(){
            return balance;
        }

        public void deposit(int amount){
            int newBalance = balance + amount;
            try {
                Thread.sleep(5);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }

            balance = newBalance;
        }
    }
}