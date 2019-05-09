package ScoreSearch;

import javax.sql.rowset.CachedRowSet;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class scoreSearch {
    public static void main(String[] args) {
        DataOutputStream seedToServer;
        ObjectInputStream readFromServer;
        int port = 9000;
        Socket socket;

        try (
            Scanner in = new Scanner(System.in);
        ){
            socket = new Socket("localhost",port);
            seedToServer = new DataOutputStream(socket.getOutputStream());
            readFromServer = new ObjectInputStream(socket.getInputStream());
            System.out.print("请输入需要查询的姓名：");
            String name = in.nextLine();
            System.out.print("请输入需要查询人的学号：");
            String ID = in.nextLine();
            seedToServer.writeUTF(name);
            seedToServer.writeUTF(ID);
            CachedRowSet cachedRowSet = (CachedRowSet)readFromServer.readObject();
            if (cachedRowSet == null){
                System.out.println("没有此人信息");
            }else {
                while (cachedRowSet.next()){
                    System.out.println(cachedRowSet.getString("studentID")+"\t"+cachedRowSet.getString("studentName")+"\t"
                            +cachedRowSet.getString("courseID")+"\t"+cachedRowSet.getString("courseName")+"\t"+cachedRowSet.getString("score"));
                }
            }
        }catch (IOException | ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
}