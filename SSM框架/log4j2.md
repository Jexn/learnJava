## 下载

下载链接：https://logging.apache.org/

## 使用

参考：https://blog.csdn.net/FANGAOHUA200/article/details/53561718

常见异常一：https://stackoverflow.com/questions/25891737/getting-exception-org-apache-logging-slf4j-slf4jloggercontext-cannot-be-cast-to

常见异常二：https://stackoverflow.com/questions/20537190/how-to-rectify-java-lang-noclassdeffounderror-while-using-log4j-in-our-class

记住在使用时必须要有其`log4j2.xml`配置文件，其配置内容为

```XML
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <!--终端显示格式-->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%c] [%-5level] %logger{36} - %msg%n"/>
        </Console>

        <!--写入文件的信息-->
        <RollingFile name="RollingFile" fileName="logs/app.log" filePattern="logs/app-%d{MM-dd-yyyy}.log.gz">
            <BurstFilter level="INFO" rate="16" maxBurst="100"/>
            <PatternLayout>
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%c] [%-5level] %logger{36} - %msg%n</pattern>
            </PatternLayout>
            <TimeBasedTriggeringPolicy />
        </RollingFile>

    </Appenders>
    <Loggers>
        <!--显示文件的等级-->
        <Root level="info">
            <!--需要信息的位置-->
            <AppenderRef ref="Console"/>
            <!--写入到文件-->
            <AppenderRef ref="RollingFile"/>
        </Root>
    </Loggers>

</Configuration>
```


