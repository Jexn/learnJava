import java.io.File;
import java.util.Date;

public class TestFile{
    public static void main(String[] args) {
        File file = new File("C:/User/Cube/Downloads");

        // 常见File类操作
        System.out.println("文件是否存在："+file.exists());
        System.out.println("文件能否读写："+file.canRead()+file.canWrite());
        System.out.println("目标是目录："+file.isDirectory());
        System.out.println("目标是文件："+file.isFile());
        System.out.println("获取文件名："+file.getName());
        System.out.println("获取完整地址："+file.getPath());
        System.out.println("获取父目录："+file.getParent());
        System.out.println("获取文件最后修改时间"+new Date(file.lastModified()));
        System.out.println("获取文件大小："+file.length());
        System.out.println("删除文件用file.delete(),重命名用file.reNameTo(dest:File)");
        System.out.println("创建文件目录：file.mkdir(),如果父目录不存在用file.mkdirs()");
    }
}