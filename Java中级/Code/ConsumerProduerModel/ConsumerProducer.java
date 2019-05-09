package ConsumerProduerModel;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerProducer {
    private static Buffer buffer = new Buffer();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ProducerTask());
        executorService.execute(new ConsumerTask());
        executorService.shutdown();
    }

    private static class ProducerTask implements Runnable{
        @Override
        public void run() {
            try {
                int i = 1;
                Random random = new Random();
                while (true){
                    System.out.println("生产者写入："+i);
                    buffer.write(i++);
                    Thread.sleep(random.nextInt(10000));
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    private static class ConsumerTask implements Runnable{
        @Override
        public void run() {
            Random random = new Random();
            try {
                while (true){
                    System.out.println("消费者删除："+buffer.read());
                    Thread.sleep(random.nextInt(10000));
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
