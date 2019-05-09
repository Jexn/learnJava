package MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 8080;
        DataInputStream readFromServer;
        DataOutputStream writeToServer;
        Socket socket;

        try {
            socket = new Socket("localhost",port);

            // 向服务器发送数据
            writeToServer = new DataOutputStream(socket.getOutputStream());
            // 读取服务器数据
            readFromServer = new DataInputStream(socket.getInputStream());

            boolean flag = true;
            while (flag){
                Scanner in = new Scanner(System.in);
                System.out.print("请输入你的名字（exit退出）：");
                writeToServer.writeUTF(in.nextLine());
                String str = readFromServer.readUTF();
                if (str.equals("exit")){
                    flag = false;
                    in.close();
                    System.exit(0);
                }else {
                    System.out.println(str);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
