public class CountFib implements Runnable{
    private long index;

    public CountFib(long index){
        this.index = index;
    }

    public long fib(long index){
        long f0 = 0;
        long f1 = 1;
        long f2 = 1;
        if (index == 0)
            return f0;
        else if (index == 1)
            return f1;
        else if (index == 2)
            return f2;

        for (int i = 3; i <= index;i++){
            f0 = f1;
            f1 = f2;
            f2 = f0 + f1;
        }

        return f2;
    }

    @Override
    public void run() {
        long sum = 0;
        long current = 0;
        for (int i = 0; i < index ; i++){
            current = fib(i);
            sum += current;
            System.out.print(" "+current);
        }
        System.out.println("\n"+index+" Sum:"+sum);
    }
}