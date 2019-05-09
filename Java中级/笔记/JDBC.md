## JDBC

*JDBC是访问关系型数据库的Java API。*

开发数据库应用程序的Java API称为JDBC。JDBC是一种Java API的商标名称，它支持访问关系数据库的Java程序。

JDBC给Java程序员提供访问和操纵众多关系数据库的一个统一的接口。使用JDBC API，用Java程序设计语言编写的应用程序能够以一种用户友好的接口执行SQL语句、获取结果以及显示数据，并且可以将所做的改动传回数据库。

### 使用JDBC开发数据库应用程序
JDBC接口和类是开发Java数据库程序的构建模块。访问数据库的定性Java程序主要采用下列步骤：

1) 加载驱动程序
    在连接到数据库之前，必须使用下面的语句，加载一个合适的驱动程序。
    Class.forName("JDBCDriverClass");
    驱动程序是一个实现接口java.sql.Driver的具体类。
    
    |数据库|驱动程序类|来源|
    |:---:|---|:---:|
    |Access|sun.jdbc.odbc.JdbcOdbcDriver|已经在JDK中|
    |MySQL|com.mysql.jdbc.Driver|mysql-connerctor-java-5.1.26.jar|
    |Oracle|oracle.jdbc.driver.OracleDriver|ojdbc6.jar|

    *注意：MySQL8已经改为com.mysql.cj.jdbc.Driver*
2) 建立连接为了连接到一个数据库，需要使用DriverManager类中的静态方法getConnection(databaseURL)，如下所示：
    Connection connection = DriverManager.getConnection(databaseURL);
    其中databaseURL是数据库在Internet上的位移标识符。
    |数据库|URL模式|
    |:---:|:---:|
    |Access|jdbc:odbc:dataSource|
    |MySQL|jdbc:mysql://hostname/dbname|
    |Oracle|jdbc:oracle:thin:@hostname:port#:oracleDBSID|

    注意：新版本的MySQL8连接需要添加时区信息，所以URL变成jdbc:mysql://localhost/school?useTimezone=true&serverTimezone=GMT%2B8（东八区时间）

3) 创建语句
    如果把Connection对象想象成一条连接程序和数据库的缆道，那么Statement的对象则是一辆缆车，它为数据库传输SQL语句用于执行，并把运行结果返回程序。
    Statement statement = connection.createStatement();

4) 执行语句。
    可以使用executeUpdate(String sql)来执行SQL DDL（数据定义语言）或更新语句。可以使用executeQuery(String sql)来执行SQL查询语句。查询结果在ResultSet中返回。例如下面分别使用DDL和SQL查询语句。
    ```Java
    <!-- 查询语句 -->
    statement.executeUpdate("create table Temp(col1 char(5),col2 char(5))");
    <!-- 数据查询语句 -->
    ResultSet resultSet = statement.executeQuery("select firstName,mi,lastName from Student where lastName = 'Smith'");
    ```
5) 处理ResultSet
    结果集ResultSet维护一个表，该表的当前行可以获得。当前行的初始位置是null。可以使用next方法移动到下一行，可以使用各种getter方法从当前行获取值。
    ```Java
        // 获取列数
        //ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        //int numberOfColumns = resultSetMetaData.getColumnCount();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1)+"-"+resultSet.getString(2)+"-"+resultSet.getString(3)+"-"+resultSet.getString(4));
        }
    ```

[利用网络和数据的成绩查询系统](..\Code\ScoreSearch\scoreServer.java)

*需要注意的是在网路传输中，如果需要传输对象，那么这个对象必须是已经序列化的，例如ResultSet不是序列化对象，需要我们使用CachedRowSet cachedRS = new CachedRowSetImpl();cachedRS.populate(resultSet);序列化对象之后再进行传输*

## PreparedStatement

PreparedStatement可以创建参数化的SQL语句

一旦建立了一个到特定数据库的连接，就可以用这个连接吧程序的SQL语句发送到数据库。Statement接口用于执行不含参数的静态SQL语句。PreparedStaement接口继承自Statement接口，用于执行含有或不含参数的预编译的SQL语句。

```Java
package Chapter32;

import java.sql.*;

public class PreparedStatementTest {
    public static void main(String[] args) {
        String databaseURL = "jdbc:mysql://localhost/school?useTimezone=true&serverTimezone=GMT%2B8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(databaseURL,"scott","tiger");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT studentName,courseName,score FROM student " +
                    "WHERE student.studentName = ? AND student.studentID = ?");
            preparedStatement.setString(1,"许达");
            preparedStatement.setString(2,"184132002");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String course = resultSet.getString(2);
                String score = resultSet.getString(3);
                System.out.println(name+"\t\t"+course+"\t\t"+score);
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
}
```

**注意：preparedStatement中的值不能是列名，只能是值，否则获取的不是数值，而是列名，比如`PreparedStatement preparedStatement =connection.prepareStatement(SELECT ?,?,? FROM student)`**

## CallableStatement

CallableStatement可以自行SQL存储过程

CallableStatement接口是为执行SQL存储过程而设计的。这个进程可能会有IN、OUT或IN OUT参数。当调用过程时，参数IN接收床底给过程的值。在进程结束后，参数OUT返回一个值，但是当调用过程时，它不包含任何值。当过程被调用时，IN OUT参数包含传递给过程的值，在它完成之后返回一个值。

```Java
package Chapter32;

import java.sql.*;
import java.util.Scanner;

public class CallableStatementTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/school?useTimezone=true&serverTimezone=GMT%2B8";
        Scanner in = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","passwd");
            CallableStatement callableStatement = connection.prepareCall("{ ? = CALL studentFound(?)}");
            System.out.println("输入需要查询的姓名：");
            String name = in.nextLine();
            callableStatement.setString(2,name);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            if (callableStatement.getInt(1) >= 1){
                System.out.println("有"+callableStatement.getInt(1)+"条记录");
            }else {
                System.out.println(name+"不存在");
            }

        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
}

```

## statement、PreparedStatement和CallableStatement区别

Statement用于执行不带参数的简单SQL语句，并返回它所生成的结果，每次执行SQL豫剧时，数据库都要编译该SQL语句。
```Java
Satatement stmt = conn.getStatement();
stmt.executeUpdate("insert into client values("aa","aaa")");
```
PreparedStatement表示预编译的SQL语句的对象，用于执行带参数的预编译的SQL语句。

CallableStatement则提供了用来调用数据库中存储过程的接口，如果有输出参数要注册，说明是输出参数。

虽然Statement对象与PreparedStatement对象能够完成相同的功能，但是相比之下，PreparedStatement具有以下优点：

1. 效率更高。

在使用PreparedStatement对象执行SQL命令时，命令会被数据库进行编译和解析，并放到命令缓冲区，然后，每当执行同一个PreparedStatement对象时，由于在缓存区中可以发现预编译的命令，虽然它会被再解析一次，但是不会被再一次编译，是可以重复使用的，能够有效提高系统性能，因此，如果要执行插入，更新，删除等操作，最好使用PreparedSatement。鉴于此，PreparedStatement适用于存在大量用户的企业级应用软件中。

2. 代码可读性和可维护性更好。

下两种方法分别使用Statement和PreparedStatement来执行SQL语句，显然方法二具有更好的可读性。

方法1：`stmt.executeUpdate("insert into t(col1,xol2) values('"+var2+"','"+var2+"')");`
方法2：
```Java
perstmt = con.prepareStatement("insert into tb_name(col1,col2) values(?,?)");
perstmt.setString(1,var1);
perstmt.setString(2,var2);
```
3. 安全性更好。

使用PreparedStatement能够预防SQL注入攻击，所谓SQL注入，指的是通过把SQL命令插入到Web表单提交或者输入域名或者页面请求的查询字符串，最终达到欺骗服务器，达到执行恶意SQL命令的目的。注入只对SQL语句的编译过程有破坏作用，而执行阶段只是把输入串作为数据处理，不再需要对SQL语句进行解析，因此也就避免了类似select * from user where name='aa' and password='bb' or 1=1的sql注入问题的发生。

CallableStatement由prepareCall()方法所创建，它为所有的DBMS（Database Management System）提供了一种以标准形式调用已存储过程的方法。它从PreparedStatement中继承了用于处理输入参数的方法，而且还增加了调用数据库中的存储过程和函数以及设置输出类型参数的功能。

这部分来[博客园alittlecomputer](https://www.cnblogs.com/LoganChen/p/6816713.html)

## 获取元数据

**元数据**（英语：metadata），又称诠释数据、中介数据、中继数据、后设数据等，为描述其他数据信息的数据。有三种不同类型的元数据，分别是记叙性元数据、结构性元数据和管理性元数据。

- 记叙性元数据描述了用于发现与辨别意义的资源。它可以包括如标题、摘要、作者和关键字等元素。
- 结构性元数据是有关于数据容器的元数据，指示如何整理其中复合的对象。例如页面依什么排序方式组成章节。
- 管理性元数据是用于管理资源的信息，例如数据产生的时间和方式、文件种类和其它技术信息，以及谁有权限访问它。在某些国家，有关于邮箱、电话、网页、IP链接与手机位置的元数据是固定由国家来存储的。

可以使用DatabaseMetaData接口来获取数据库的元数据，例如数据库URL、用户名、JDBC驱动程序名称等。ResultSetMetaData接口可以用于获取到结果的集合的元数据，例如表的列数和列名等。

JDBC提供DatabaseMetdData接口，可用来获取数据库范围的信息，还提供ResultSetMetaData接口，用于获取特定的ResultSet信息。

### 数据库元数据

Connection接口用于建立与数据库的连接。SQL语句的执行和结果的返回是在一个连接上下文中进行的。连接还提供对数据库元数据信息的访问，该信息描述了数据库的能力、支持的SQL语法、存储过程等等。要得到数据库的一个DatabaseMetaData实例，可以使用Connection对象的getMetaData方法，如下所示：
```Java
DatabaseMetaData dmMetaData = connection.getMetaData();
```

### 结果元数据

ResultSetMetaData接口描述属于结果集的信息。ResultSetMetaData对象能够用于在结果集ResultSet中找出关于列的类型和属性的信息。要得到ResultSetMetadata的一个实例，可以再结果集上使用getMetaData方法。
```Java
ResultSetMetaData reMetaData = resultSet.getMetaData();
```

## 事务提交

为了保持数据的一致性，需要使用一致性操作，要么都成功，要么都失败。比如在执行某个数据加减各一次操作，在执行加命令后执行减命令，但是减命令出错，结果就是加了但没有减，我们需要让这两个操作保持一致，要么都成功，要么都失败。

在事务中的多个操作，要么都成功，要么都失败
- 通过 c.setAutoCommit(false);关闭自动提交
- 使用 c.commit();进行手动提交

```Java
package JavaDatabase;

import java.sql.*;

public class workCommit {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/warehouse?useTime=true&serverTimezone=GMT";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","passwd");
            Statement preparedStatement = connection.createStatement();
            // 禁止自动提交
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate("UPDATE product set prod_price = prod_price + 20 WHERE prod_company = 'Apple Inc'");
            // 此条SQL命令出错
            preparedStatement.executeUpdate("UPDATA product set prod_price = prod_price - 20 WHERE prod_company = 'Apple Inc'");
            // 手动提交
            connection.commit();
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
}
```