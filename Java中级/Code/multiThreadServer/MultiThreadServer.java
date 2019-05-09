package multiThreadServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(8080);
        ){
            while (true) {
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("当前连接客户：" + inetAddress.getHostName() + " - " + inetAddress.getHostAddress());
                new Thread(new HandleAClient(socket)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    static class HandleAClient implements Runnable {
        private Socket socket;

        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // 读取
                DataInputStream readToClient = new DataInputStream(socket.getInputStream());
                // 发送到客户端
                DataOutputStream writeToClient = new DataOutputStream(socket.getOutputStream());
                // 注意：如果连接过程中，有一端连接中断了会出现java.net.SocketException: Connection reset异常
                // 需要用readToClient.available()检测是否还有数据
                do {
                    double radius = readToClient.readDouble();
                    double area = radius * radius * Math.PI;
                    writeToClient.writeDouble(area);
                }while (readToClient.available() != 0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}