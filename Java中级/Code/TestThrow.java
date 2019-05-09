public class TestThrow{
    public static void throwChecked(int a) throws Exception{
        if( a > 0){
            throw new Exception("值大于0，不符合要求！");
        }
    }

    public static void throwRunTime(int a){
        if( a > 0){
            throw new RuntimeException("a的值大于0，不符合要求");
        }
    }
    public static void main(String[] args) {
        // 调用throws声明的方法，必须显式的捕获该异常。
        // 否则必须在该方法再次抛出，让其他调用该方法的方法处理异常。
        try {
            throwChecked(-3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 调用RunTime异常的方法既可以显式捕获该异常，也可以不理会该异常。
        throwRunTime(3);
        
        // 显式的处理该异常
        try {
            throwRunTime(4);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}