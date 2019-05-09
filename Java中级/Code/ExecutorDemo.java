import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo{
    public static void main(String[] args) {
        // 定义一个3个数目的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 添加线程
        executorService.execute(new CountFib(20));
        executorService.execute(new CountFib(15));
        executorService.execute(new CountFib(10));

        // 关闭线程池
        executorService.shutdown();
    }
}