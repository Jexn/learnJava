import java.util.Scanner;

public class finalTest{
    public static long factorial(int num){
        long sum = 1;
        do{
            sum = sum * num;
            num--;
        }while( num > 1);
        return sum;
    } 

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("输入两个数字：");
        try{
            double a = in.nextDouble();
            double b = in.nextDouble();
            System.out.println("两个数之和是："+(a+b));
        }catch(NumberFormatException e){
            System.err.println(e.getMessage());
        }

        System.out.println("请输入一个数，计算其阶乘：");
        int num = in.nextInt();
        System.out.println("阶乘是："+factorial(num));

    }
}
