package ScoreSearch;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class scoreServer {
    public static void main(String[] args) {
        int port = 9000;
        DataInputStream readFormClient;
        ObjectOutputStream seedToClient;
        ServerSocket serverSocket;
        Socket socket;
        String dbURL = "jdbc:mysql://localhost/school?useTimezone&serverTimezone=GMT%2B8";

        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            // 获取客户端数据
            readFormClient = new DataInputStream(socket.getInputStream());
            // 发送数据到客户端
            seedToClient = new ObjectOutputStream(socket.getOutputStream());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbURL, "scott", "tiger");
            System.out.println("数据库已经连接");
            do {
                String name = readFormClient.readUTF();
                String ID = readFormClient.readUTF();

                // 查询数据库
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Student WHERE studentID='" + ID + "' AND StudentName = '" + name + "'");
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("没有查询到记录！");
                    seedToClient.writeObject(null);
                } else {
                    // 序列化resultSet对象
                    // ResultSet并不是序列化对象，所以不可以使用网络直接传输对象
                    CachedRowSet cachedRowSet = new CachedRowSetImpl();
                    cachedRowSet.populate(resultSet);

                    seedToClient.writeObject(cachedRowSet);
                }
            } while (readFormClient.available() != 0);
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}