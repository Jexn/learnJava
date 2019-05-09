JDK安装直接去oracle下载。

## 配置以安装C:\Program Files\Java\jdk1.8和C:\Program Files\Java\jdk11为例

1. 配置路径：计算机-属性-高级系统设置-环境变量-系统变量
2. 新建JAVA_HOME8,值：C:\Program Files\Java\jdk1.8；新建JAVA_HOME11，值：C:\Program Files\Java\jdk11(同时安装多个jdk版本)
3. 新建CLASSPATH，值为：..;%JAVA_HOME8%\lib;%JAVA_HOME8%\lib\tools.jar;%JAVA_HOME11%\lib;%JAVA_HOME11%\lib\tools.jar
4. 编辑PATH，添加值：C:\Program Files\Java\jdk1.8\bin;C:\Program Files\Java\jdk11;C:\Program Files\Java\jre1.8\bin;(三条分开添加)。