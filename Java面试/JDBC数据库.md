## PreparedStatement对比Statement的优势
1. 可以设置参数，而不是像Statement那样字符串拼接

```Java
package JavaDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class preparedStatement {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String url = "jdbc:mysql://localhost/warehouse??serverTimezone=GMT%2B8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        try (
                Connection connection = DriverManager.getConnection(url,"root","passwd");
                PreparedStatement prepared = connection.prepareStatement("INSERT INTO product VALUES (null,?,?,?,?)");
        ){
            System.out.println("输入产品名：");
            String name = in.nextLine();
            System.out.println("请输入数量：");
            int num = in.nextInt();
            System.out.println("请输入价格：");
            int price = in.nextInt();
            System.out.println("请输入产品生产公司：");
            String company = in.next();

            prepared.setString(1,name);
            prepared.setInt(2,num);
            prepared.setInt(3,price);
            prepared.setString(4,company);
            prepared.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
     }
}
```

2. PreparedStatement有预编译机制，性能比Statement快

```Java
package jdbc;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
  
public class TestJDBC {
    public static void main(String[] args) {
  
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
  
        String sql = "insert into hero values(null,?,?,?)";
        try (Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8","root", "admin");
                Statement s = c.createStatement();
                PreparedStatement ps = c.prepareStatement(sql);
            ) {
            // Statement执行10次，需要10次把SQL语句传输到数据库端
            // 数据库要对每一次来的SQL语句进行编译处理
            for (int i = 0; i < 10; i++) {
                String sql0 = "insert into hero values(null," + "'提莫'" + ","
                        + 313.0f + "," + 50 + ")";
                s.execute(sql0);
            }
            s.close();
  
            // PreparedStatement 执行10次，只需要1次把SQL语句传输到数据库端
            // 数据库对带?的SQL进行预编译
  
            // 每次执行，只需要传输参数到数据库端
            // 1. 网络传输量比Statement更小
            // 2. 数据库不需要再进行编译，响应更快
            for (int i = 0; i < 10; i++) {
                ps.setString(1, "提莫");
                ps.setFloat(2, 313.0f);
                ps.setInt(3, 50);
                ps.execute();
            }
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
}
```

3. PreparedStatement可以防止SQL注入式攻击

假设name是用户提交来的数据`String name = "'盖伦' OR 1=1";`

使用Statement就需要进行字符串拼接出来的语句是：`select * from hero where name = '盖伦' OR 1=1`

因为有OR 1=1，这是恒成立的,那么就会把所有的英雄都查出来，而不只是盖伦。

如果Hero表里的数据时海量的，比如几百万条，把这个表里的数据全部查出来，会让数据库负载变高，CPU100%，内存消耗光，响应变得极其缓慢

而PreparedStatement使用的是参数设置，就不会有这个问题

```Java
package jdbc;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
  
public class TestJDBC {
    public static void main(String[] args) {
  
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
  
        String sql = "select * from hero where name = ?";
        try (Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8","root", "admin");
                Statement s = c.createStatement();
            PreparedStatement ps = c.prepareStatement(sql);
        ) {
            // 假设name是用户提交来的数据
            String name = "'盖伦' OR 1=1";
            String sql0 = "select * from hero where name = " + name;
            // 拼接出来的SQL语句就是
            // select * from hero where name = '盖伦' OR 1=1
            // 因为有OR 1=1，所以恒成立
            // 那么就会把所有的英雄都查出来，而不只是盖伦
            // 如果Hero表里的数据时海量的，比如几百万条，把这个表里的数据全部查出来
            // 会让数据库负载变高，CPU100%，内存消耗光，响应变得极其缓慢
            System.out.println(sql0);
  
            ResultSet rs0 = s.executeQuery(sql0);
            while (rs0.next()) {
                String heroName = rs0.getString("name");
                System.out.println(heroName);
            }
  
            s.execute(sql0);
  
            // 使用预编译Statement就可以杜绝SQL注入
  
            ps.setString(1, name);
  
            ResultSet rs = ps.executeQuery();
            // 查不出数据出来
            while (rs.next()) {
                String heroName = rs.getString("name");
                System.out.println(heroName);
            }
 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
}
```

## execute与executeUpdate的区别

**相同点：** execute与executeUpdate的相同点：都可以执行增加，删除，修改。

**不同点：** 

不同1：

- execute可以执行查询语句,然后通过getResultSet，把结果集取出来;
- executeUpdate不能执行查询语句

不同2:

- execute返回boolean类型，true表示执行的是查询语句，false表示执行的是insert,delete,update等等
- executeUpdate返回的是int，表示有多少条数据受到了影响
