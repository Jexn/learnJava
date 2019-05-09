import java.util.Scanner;

public class TestThrows{
    static double test(int num) throws Exception{
        int i = 10;
        i = i / num;
        return i;
    }

    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int num = in.nextInt();
        try{
            double a = test(num);
            System.out.println("值:"+a);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            in.close();
        }
    }
}