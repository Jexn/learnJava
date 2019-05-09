## HttpSession

Session，即会话，是Web开发中的一种会话状态跟踪技术。Cookie也是一种会话跟踪技术。不同的是Cookie是将会话状态保存在了客户端，而Session则是将会话状态保存在服务器端。

所谓会话就是指用户打开浏览器，从发出第一次请求开始，一直到最终关闭浏览器，就表示一次会话的完成。

Session并不是JavaWeb开发所特有的，而是整个Web开发中所使用的技术。在JavaWeb开发中，Session是以javax.servlet.http.HttpSession的接口对象的形式出现的。

## Session

### Session访问

[session的获取和设置](Code/Session/sessionA.java)

[session获取其值和属性](Code/Session/sessionB.java)

对于request的getSession()的用法：

- 一般情况下，若要向Session中写入数据，则需要使用getSession(true)，即getSession()方法。它的意思是如果request中已经有Session了，则用已有的Session，如果没有Session则新建一个Session。

- getSession(false)一般用于从session中读取数据，它的意思是如果request中已经有session值了，则从session中读取数据。如果request中没有session数据，则返回null。

### Session的工作原理

在服务器中，服务器会每个会话维护一个Session会话，不同的会话，对应不同的Session。系统是如何识别不同会话的Session对象的呢？如何做到在同一个会话中，一直使用的是同一个Session对象呢？

1. 写入Session列表

服务器对当前应用中的Session是以Map形式进行管理的，这个Map称为Session列表。该Map的Key为一个32位长度的随机串，这个随机串称为JSessionID，Value则为Session对象的引用。

当用户第一次提交请求时，服务端Servlet中执行到request.getSession()方法后，会自动生成一个Map.Entry对象，key为一个根据某种算法新生成的JSessionID，Value则为新创建的HttpSession对象。

key|value
|:---:|:---:|
|DFK...9AF|HttpSession的引用1|
|KH8...KK1|HttpSession的引用2|
|...|...|
|DL3...23D|HttpSession的引用n|

2. 服务器生成并发送Cookie

在Session信息写入Session列表后，系统还会自动将“JSessionID”作为name，这个32位长度的随机串作为Value，以Cookie的形式存放到响应报头中，并随着响应，将该Cookie发送到客户端中。

也就是说如果遇到request.getSession()，服务器会生成Session的映射表，服务器会自动创建一个Cookie，用JSESSIONID作为Cookie的name，并将Session映射表的中32位key作为Cookie的Value，并将这个Cookie写入到response中发送到客户端中，Cookie默认绑定到当前项目资源路径下。

3. 客户端接收并发送Cookie

客户端接收到这个Cookie后将其存放到浏览器中的缓存中。即，只要客户端不关闭，浏览器缓存中的Cookie就不会失效。

当用户提交第二次请求时，会将缓存中的这个Cookie，伴随着请求的头部信息，一块发送到服务器。

4. 服务器从Session列表中查找

服务端从请求中读取到客户端发送来的Cookie，并根据Cookie的JSESSIONID的值，从Map中查找相应Key所对应的value，即Session对象，然后，对该Session对象的域属性进行读写操作。

### Session的失效

Web开发中引入的Session超时的概念，Session的失效就是指Session的超时。若某个Session在指定的时间范围内一直未被访问，那么Session将超时，即失效。

在web.xml中可以通过`<session-config>`标签中设置Session的超时时间，单位为分钟。默认的Session的超时时间为30分钟。

值得注意的是，这个超时时间的计时开始时间并不是从Session创建的开始计时，而是从你最后一次访问开始计时。

```XML
<!-- 设置Session的失效时间为120分钟 -->
<session-config>
    <session-timeout>120</session-timeout>
</session-config>
```

若未超时时限，也可以通过代码提前使session失效。HttpSession中的invalidate()方法可以让session解绑任何绑定它的对象。

注意：session.invalidate()只是让session失效，并不是让session为null，session在执行invalidate()后session不为空。

### Cookie禁用后使用Session进行跟踪

如果客户端禁用了Cookie，可以使用`resp.encodeRedirectURL(uri);`重定向，进行传递数据，这相当于在地址末尾上加了`;jsession=KLDSJ099SADF90FASDF8V`，但这并安全。

#### 重定向实现

[禁用Cookie实现session会话](Code/Session/unCookie.java)

#### 超链接实现

如果是超链接则需要使用`response.encodeURL(uri)`实现跳转

[禁用Cookie，解决非重定向Session跟踪问题](Code/Session/hyperlinkUnCookie.java)

注意：这种方式只解决超链接Session跟踪问题，不解决重定向方式

### 域属性空间

在JavaWeb编程的API中，存在三个可以存放域属性的空间范围对象，这个三个对象中所存储的域属性作用范围，由大到小分别为：ServletContext => HttpSession => HttpServletRequest

- ServletContext，即application，置入其中域属性是整个应用范围的，可以完成跨会话共享数据。比如可以现在Chrome的会话中可以获取其数据，在Firefox新建一个会话，同样也可以访问其数据。

- HttpSession，植入其中的域属性是会话范围的，可以完成跨请求共享数据。在同一个会话中共享，在同一浏览器中，不关闭浏览器之前新建的请求，访问同一项目，所有的请求都能访问到其数据。

- HttpServletRequest，置入其中的域属性是请求范围的，可以完成跨Servlet共享数据。但这些Servlet必须在同一请求中。比如进行请求转发。

这三个域属性空间对象的使用原则是，在可以保证功能需求的前提下，有线使用小范围的。这样不仅可以节省服务器内存，还可以保证数据的安全性。