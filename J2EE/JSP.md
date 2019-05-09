## JSP执行过程
```JSP
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>使用JSP写网页</title>
  </head>
  <body>
    <h1>使用JSP写网页</h1>
    <p>你好JSP</p>
    <%=new Date().toString()%>
  </body>
</html>
```

1. 把 index.jsp转译为index_jsp.java

2. index_jsp.java 位于d:\tomcat\work\Catalina\localhost\_\org\apache\jsp

3. index_jsp.java是一个servlet

4. 把index_jsp.java 编译为index_jsp.class

5. 执行index_jsp，生成html

6. 通过http协议把html 响应返回给浏览器

## JSP页面组成

```JSP
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>价格表</title>
    <style>
        body{
            width: 80%;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<%
    HashMap<String, Double> hashMap = new HashMap<>();
    hashMap.put("青菜", 2.5);
    hashMap.put("茄子", 2.5);
    hashMap.put("南瓜", 3.5);
    hashMap.put("西红柿", 2.0);
%>

<table width="200px" border="1" cellspacing="0">
    <%
        Set<Map.Entry<String,Double>> entrySet = hashMap.entrySet();
        for (Map.Entry<String,Double> entry: entrySet){
            {%>
                <tr>
                    <td><%=entry.getKey()%></td>
                    <td><%=entry.getValue()%></td>
                </tr>
            <%}
        }
    %>
</table>

<%@include file="footer.jsp"%>

</body>
</html>
```

**jsp由这些页面元素组成：**

1. 静态内容：html,css,javascript等内容

2. 指令：以<%@开始 %> 结尾，比如<%@page import="java.util.*"%>

3. 表达式 <%=%>：用于输出一段html

4. Scriptlet：在<%%> 之间，可以写任何java 代码

5. 声明：在<%!%> 之间可以声明字段或者方法。但是不建议这么做。

6. 动作：<jsp:include page="Filename" > 在jsp页面中包含另一个页面。在包含的章节有详细的讲解

7. 注释 <%-- -- %>：不同于 html的注释 <!-- --> 通过jsp的注释，浏览器也看不到相应的代码，相当于在servlet中注释掉了

## Cookie

**Cookie:**Cookie是一种浏览器和服务器交互数据的方式。Cookie是由服务器端创建，但是不会保存在服务器。创建好之后，发送给浏览器。浏览器保存在用户本地。下一次访问网站的时候，就会把该Cookie发送给服务器。

```JSP
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取Cookie</title>
</head>
<body>
    <h1>使用Cookie</h1>
    <%
        Cookie c = new Cookie("name","Green");
        c.setMaxAge(24*60*60);
        c.setPath("/");
        response.addCookie(c);
    %>

    <a href="getCookie.jsp">跳转到获取Cookie的页面</a>
</body>
</html>
```

在web目录下创建一个文件 setCookie.jsp
 
`Cookie c = new Cookie("name", "Gareen");` 创建了一个cookie,名字是"name" 值是"Gareen"

`c.setMaxAge(24 * 60 * 60);`表示这个cookie可以保留一天，如果是0，表示浏览器一关闭就销毁

`c.setPath("/");`Path表示访问服务器的所有应用都会提交这个cookie到服务端，如果其值是 /a, 那么就表示仅仅访问 /a 路径的时候才会提交 cookie

`response.addCookie(c);`通过response把这个cookie保存在浏览器端

访问地址：
 
http://127.0.0.1/setCookie.jsp

```JSP
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取Cookie</title>
</head>
<body>
    <%
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (int i = 0; i < cookies.length;i++){
                out.print(cookies[i].getName()+":"+cookies[i].getValue()+"<br>");
            }
        }
    %>
</body>
</html>
```

在web目录下创建文件getCookie.jsp
然后访问网页:http://127.0.0.1/getCookie.jsp
 
`Cookie[] cookies  = request.getCookies();`表示获取所有浏览器传递过来的cookie

`if (null != cookies )`如果浏览器端没有任何cookie，得到的Cookie数组是null

```Java
for (int d = 0; d <= cookies.length - 1; d++) {
   out.print(cookies[d].getName() + ":" + cookies[d].getValue() + "<br>");
}
```
遍历所有的cookie,可以看到name:Gareen，这个在setCookie.jsp中设置的cookie

## session

```JSP
<!-- setSession -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>设置Session</title>
</head>
<body>
    <%
        session.setAttribute("id","001");
    %>
    <a href="getSession.jsp">跳转到获取Sesion页面</a>
</body>
</html>
```

```JSP
<!-- getSession -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取Session</title>
</head>
<body>
    <%
        String id = (String) session.getAttribute("id");
    %>

    <p>session中的id:<%=id%></p>
</body>
</html>
```

Session对应的中文翻译是会话。会话指的是从用户打开浏览器访问一个网站开始，无论在这个网站中访问了多少页面，点击了多少链接，都属于同一个会话。 直到该用户关闭浏览器为止，都属于同一个会话。

Session相当于每个用户访问网站留下的记录。比如上淘宝，你把一些商品加入到购物车，这个购物车里只有你自己的加入的商品，而没有别人的物品，只有你自己才能访问。而Cookie则相当于访问这个购物车的钥匙，只有相应的Cookie才能解开相对应的Session。

## JSP作用域

JSP有4个作用域，分别是：pageContext 当前页面、requestContext 一次请求、sessionContext 当前会话、applicationContext 全局，所有用户共享

### JSP内置对象

1. request  ， HttpServletRequest，它包含了有关浏览器请求的信息，并且提供了几个用于获取cookie, header, 和session数据的有用的方法。 

2. response, HttpServletResponse对象，并提供了几个用于设置送回 浏览器的响应的方法（如cookies,头信息等） 

3. out对象是javax.jsp.JspWriter的一个实例，并提供了几个方法使你能用于向浏览器回送输出结果。 

4. pageContext表示一个javax.servlet.jsp.PageContext对象。它是用于方便存取各种范围的名字空间、servlet相关的对象的API，并且包装了通用的servlet相关功能的方法。 

5. session表示一个请求的javax.servlet.http.HttpSession对象。Session可以存贮用户的状态信息

6. applicaton 表示一个javax.servle.ServletContext对象。这有助于查找有关servlet引擎和servlet环境的信息

7. config表示一个javax.servlet.ServletConfig对象。该对象用于存取servlet实例的初始化参数。 

8. page表示javax.servlet.jsp.HttpJspPage，从该页面产生的一个servlet实例。

### pageContext当前页面
```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    pageContext.setAttribute("name","gareen");
%>
<%=pageContext.getAttribute("name")%>


<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=pageContext.getAttribute("name")%>
```

在上述两个页面中，getContext.jsp是无法获取到name的值的。

### requestContext 一次请求
```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setAttribute("name","gareen");
%>
<%=request.getAttribute("name")%>

<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=request.getAttribute("name")%>
```

requestContext会随着本次请求结束而失效。

#### 服务端跳转
requestContext指的是一次请求。如果发生了服务端跳转，从setContext.jsp跳转到getContext.jsp，这其实，还是一次请求。 所以在getContext.jsp中，可以取到在requestContext中设置的值。这也是一种页面间传递数据的方式

```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setAttribute("name","gareen");
%>
<jsp:forward page="getContext.jsp"/>

<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%=request.getAttribute("name")%>
```

#### 客户端跳转

客户端跳转，浏览器会发生一次新的访问，新的访问会产生一个新的request对象。所以页面间客户端跳转的情况下，是无法通过request传递数据的。

```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setAttribute("name","gareen");
    response.sendRedirect("getContext.jsp");
%>

<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%=request.getAttribute("name")%>
```

### sessionContext 当前会话

sessionContext 指的是会话，从一个用户打开网站的那一刻起，无论访问了多少网页，链接都属于同一个会话，直到浏览器关闭。 所以页面间传递数据，也是可以通过session传递的。但是，不同用户对应的session是不一样的，所以session无法在不同的用户之间共享数据。

与requestContext类似的，也可以用如下方式来做

```JAVA
pageContext.setAttribute("name","gareen",pageContext.SESSION_SCOPE);
pageContext.getAttribute("name",pageContext.SESSION_SCOPE)
```

```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    session.setAttribute("name","gareen");
    response.sendRedirect("getContext.jsp");
%>

<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%=session.getAttribute("name")%>
```

### applicationContext 全局，所有用户共享

applicationContext 指的是全局，所有用户共享同一个数据

在JSP中使用application对象， application对象是ServletContext接口的实例

也可以通过 request.getServletContext()来获取。所以 application == request.getServletContext() 会返回true。application映射的就是web应用本身。

与requestContext类似的，也可以用如下方式来做

```java
pageContext.setAttribute("name","gareen",pageContext.APPLICATION_SCOPE);
pageContext.getAttribute("name",pageContext.APPLICATION_SCOPE);
```

```JSP
<!-- setContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    application.setAttribute("name","gareen");
    System.out.println(application == request.getServletContext());
    response.sendRedirect("getContext.jsp");
%>

<!-- getContext.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%=application.getAttribute("name")%>
```