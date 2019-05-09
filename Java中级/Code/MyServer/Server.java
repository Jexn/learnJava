package MyServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        // 读取客户端数据
        DataInputStream readFromClient;
        // 向客户端发送数据
        DataOutputStream writeToClient;
        ServerSocket serverSocket;
        Socket socket;

        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            boolean flag = true;
            while (flag){
                readFromClient = new DataInputStream(socket.getInputStream());
                writeToClient = new DataOutputStream(socket.getOutputStream());
                String str = readFromClient.readUTF();
                if (str.equals("exit")){
                    flag = false;
                    writeToClient.writeUTF("exit");
                    System.out.println("服务器已经停止！");
                }else {
                    System.out.println("已经接收的数据："+str);
                    writeToClient.writeUTF("你好，"+str);
                }
            }
            System.out.println("服务器关闭！");
            System.exit(0);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
