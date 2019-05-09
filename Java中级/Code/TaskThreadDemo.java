public class TaskThreadDemo {
    public static void main(String[] args) {
        // 创建任务但不执行
        Runnable printA = new PrintChar('a',100);
        Runnable printB = new PrintChar('b',100);
        Runnable printNum = new PrintNum(100);

        // 创建进程，但还没执行
        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(printNum);

        //开始运行
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class PrintChar implements Runnable{
    private char charToPrint;
    private int times;

    public PrintChar(char c,int t){
        charToPrint = c;
        times = t;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++){
            System.out.print(" "+charToPrint);
        }
    }
}

class PrintNum implements Runnable{
    private int lastNum;

    public PrintNum(int n){
        lastNum = n;
    }

    @Override
    public void run() {
        for (int i = 1; i <= lastNum;i++){
            System.out.print(" "+i);
        }
    }
}