package multiThreadServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        
        try (
            Scanner in = new Scanner(System.in);
            Socket socket = new Socket("localHost", 8080);
        ){
            DataInputStream readToServer = new DataInputStream(socket.getInputStream());
            DataOutputStream writeToServer = new DataOutputStream(socket.getOutputStream());

            System.out.print("请输入圆的半径（输入-1退出）：");
            double radius = in.nextDouble();
            writeToServer.writeDouble(radius);
            double area = readToServer.readDouble();
            if (area == 0) {
                System.exit(0);
            }
            System.out.println("\n圆的面积是：" + area);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}