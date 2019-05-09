## Web容器中的两个Map表

*Web容器中有两个Map表：已实例化的Map表和未实例化的Map表*

**已实例化Map表：** 当Servlet实例创建好了之后，会将该Servlet实例存放到一个Map集合中，这个Map集合中的Key为URL，而value则是这个Servlet的实例，即Map<String,Servlet>。当Web服务器从用户请求中解析分离出URL，会先从这个Map中查找对应的Servlet实例。如果找到对应的Servlet实例，则直接运行其实例的service服务。

**未实例化Map表：** 如果在第一个Map中没有找到这个实例，则会去第二Map表中查找。第二个Map表相当于Web.xml中的的配置合集，Map<<url-pattern>,<servlet-class>>这样，如果找到了对应的servlet-class，则实例化这个servlet，然后进行service服务。

造成这两个表存在的原因servlet实例化顺序和性能的原因，因为不是每个servlet都需要马上运行，有些servlet服务使用频率比较低，不需要服务器一运行就实例化，有些则需要马上运行。

## `<url-pattern>`的匹配模式

`<url-pattern>`有四种匹配模式，分别为：精确路径模式、通配符路径模式、全路径模式、后缀名模式。

### 精确路径模式

```XML
    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>/servlet2</url-pattern>
    </servlet-mapping>
```

这个模式之下，需要输入准确的路径才能访问相应的服务，注意：一个servlet服务可以有多个url-pattern路径，多个url-pattern可以访问同一个服务。

### 通配符路径模式

```XML
    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>/servlet2</url-pattern>
        <url-pattern>/servlet2/*</url-pattern>
    </servlet-mapping>
```

上述例子中，通配符路径只要前缀是localhost/servlet2/，后面不管加什么字符，都会同一定位到servlet_2这个服务

### 全路径模式

全路径模式有两种，一种是`<url-pattern>/<url-pattern>`，另一种是`<url-pattern>/*</url-pattern>`。`/`模式只会拦截静态资源访问（html、jpg之类），不拦截动态资源请求（jsp网页）。而`/*`则是真正的全路径模式，它拦截所有资源请求，不管是否是动态资源。

### 后缀名模式

```XML
    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
```

后缀名模式顾名思义，拦截所有以本字符结尾的服务，比如上例，譬如`localhost/dksjhds.do`、`localhost/asdf/asdasd/asd/asd.do`之类的地址都会被拦截定位到servlet_2的服务上。而非.do结尾的访问请求则不被拦截。

**注意：带斜杠的通配符路径模式不能与后缀名模式同时使用，譬如`/servlet/*.do`、`/*.do`之类的。**


## url-parttern匹配原则

### 路径优先后缀原则

```XML
    <servlet>
        <servlet-name>servlet1</servlet-name>
        <servlet-class>Servlet_1</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet1</servlet-name>
        <url-pattern>/servlet1/*</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
```

在上例中，如果输入`localhost/servlet/some.do`，不管是通配符模式还是后缀名模式都能够正确匹配，但根据路径优先原则，服务器会选择调用servlet1，而不是servlet2

### 精确路径优先原则

```XML
    <servlet>
        <servlet-name>servlet1</servlet-name>
        <servlet-class>Servlet_1</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet1</servlet-name>
        <url-pattern>/servlet1/*</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>/servlet1/servlet2</url-pattern>
    </servlet-mapping>
```

在上例中，如果输入`/servlet1/other`，则访问servlet1；而如果`/servlet1/servlet2`，两个url-pattern都能够正确匹配，但根据精确路径优先原则，服务会访问的servlet2服务。

### 最长路径优先原则

```XML
    <servlet>
        <servlet-name>servlet1</servlet-name>
        <servlet-class>Servlet_1</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet1</servlet-name>
        <url-pattern>/servlet/*</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>servlet2</servlet-name>
        <servlet-class>Servlet_2</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>servlet2</servlet-name>
        <url-pattern>/servlet/servlet2/*</url-pattern>
    </servlet-mapping>
```

在上例中，如果访问`localhost/servlet/servlet2/some`,两个url-pattern都能够匹配，但是servlet2的匹配更加的准确的，则匹配servlet2。