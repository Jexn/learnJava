## Spring概念

Spring：Spring是一个容器，用于降低代码间的耦合度，根据不同的代码它采用了IoC和AOP两种技术的解耦合框架。不同代码是指主业务逻辑与系统级业务逻辑，比如一个取款机，里面资金的流转是主业务逻辑，而记录这些业务的日志、事务则是系统级业务逻辑。IoC控制反转，它主要用于解决主业务逻辑的耦合度问题。AOP面向切面编程，主要用于解决系统级业务逻辑。

## Spring特点

### 非侵入式
所谓非侵入是指，Spring框架的API不会在业务逻辑上出现，即业务逻辑是POJO。由于业务逻辑中没有Spring的API，所以业务逻辑可以从Spring框架快速移植到其他框架，即与环境无关。

### 容器
Spring作为一个容器，可以管理对象的生命周期、对象与对象之间的依赖关系。可以通过配置文件，来定义对象，以及设置与其他对象的依赖关系。

### IoC

IoC是一种概念思想，指将传统上由程序代码直接控制的对象调用权转交给容器，通过容器来实现对象的装配和管理。控制反转就是对对象控制权的转移，从程序代码本身反转到了外部容器。

其实现方式方法多种多样。当前比较流行的实现方法有两种：依赖注入（Dependency Lookup）和依赖查找（Dependency Injection）。

**依赖查找**：Dependency Lookup，DL，容器提供回调接口和上下文环境给组件，程序代码则需要提供具体的查找方式。比较典型的是依赖于JNDI系统的查找。

**依赖注入**：Dependency Injection，DI，程序代码不做定位查询，这些工作由容器自行完成。

依赖注入DI是指程序运行过程中，若需要调用另一个对象协助时，无须在代码中创建被调用者，而是依赖于外部容器，由外部容器创建后传递给程序。

Spring的依赖注入对调用者与被调用者几乎没有任何要求，完全支持POJO之间依赖关系的管理。

依赖注入是目前最优秀的解耦方式。依赖注入让Spring的Bean之间以配置文件的方式组织在一起，而不是以硬编码的方式耦合在一起的。

## ApplicationContext容器和BeanFactory区别

```Java
    @Test
    public void Test02(){
        // 创建容器对象,加载Spring配置文件
        // ClassPathXmlApplicationContext从类路径也就是src文件夹下查找
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ISomeService service = (ISomeService) applicationContext.getBean("myService");
        service.doSome();
    }

    @Test
    public void Test04(){
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        ISomeService service = (ISomeService) beanFactory.getBean("myService");
        service.doSome();
    }
```

ApplicationContext和BeanFactory区别在于对象的创建时间。

1. ApplicationContext容器会在进行时初始化，会将其中的所有Bean（对象）进行初始化。其优点是：响应速度快；缺点是：占用系统资源。
2. BeanFactory容器中的对象，在容器初始化的时候不会被创建，而是在真正获取这个对象的时候进行初始化。其优缺点和ApplicationContext相反

## Bean的装配

Bean的装配，即Bean对象的创建。容器根据代码要求创建Bean对象后再传递给代码的过程，称为Bean的装配。

代码通过getBean()方式从容器获取指定的Bean实例，容器首先会调用Bean类的无参构造器，创建空值的实例对象。