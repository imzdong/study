# 1、IOC发展
## 1.1、IOC定义
IOC反转控制，
## 1.2、IOC简史
好莱坞原则，导演联系演员。
# 2、IOC主要实现策略
2.1、service location服务定位（java EE）
2.2、依赖注入
2.3、依赖查找
2.4、应用上下文（JavaBeans）
# 3、IOC容器职责
3.1、通用职责
3.2、依赖处理
依赖查找
依赖注入
3.3、生命周期管理
容器：启动、停止
托管的资源（Java Beans或其他资源）：pojo，事件监听
3.4、配置
容器：控制容器行为
外部化配置：属性配置、XML配置
托管的资源（Java Beans或其他资源）：线程池配置
# 4、IOC的实现
Java SE实现
     Java Beans Api
     SPI
     JNI
Java EE实现
     EJB
     Servlet  
开源项目
• Apache Avalon（http://avalon.apache.org/closed.html） 
• PicoContainer（http://picocontainer.com/） 
• Google Guice（https://github.com/google/guice） 
• Spring Framework（https://spring.io/projects/spring-framework）
# 5、传统IOC容器实现
• Java Beans 作为IoC 容器 
• 特性 
    • 依赖查找 
    • 生命周期管理 
    • 配置元信息 
    • 事件 
    • 自定义 
    • 资源管理 
    • 持久化 
    • 规范 
• JavaBeans：https://www.oracle.com/technetwork/java/javase/tech/index-jsp-138795.html 
• BeanContext：https://docs.oracle.com/javase/8/docs/technotes/guides/beans/spec/beancontex
# 6、轻量级IOC容器
• Expert One-on-One™ J2EE™ Development without EJB™》认为轻量级容器的好处：
• Escaping the monolithic container
• Maximizing code reusability
• Greater object orientation
• Greater productivity
• Better testability
# 7、依赖查找 VS 依赖注入
依赖查找  主动获取 相对繁琐 侵入业务逻辑 依赖容器API 良好
依赖注入  被动提供 相对遍历 低侵入性    不依赖      一般
# 8、构造器注入 VS Setter注入


Spring IOC的优势
答： 典型的IoC 管理，
依赖查找和依赖注入 
AOP抽象 事务抽象 
事件机制 SPI 扩展 
强大的第三方整合 易测试性 更好的面向对象

# IOC配置元信息

# IOC的真正容器：
BeanFactory和Application谁才是真正的容器？
Application就是BeanFactory，简化了AOP,应用级别上下文。
配置元信息，资源管理，事件，国际化，注解，Environment抽象
ConfigurableApplication Application BenFactory

ConfigurableApplication组合BeanFactory
BenFactory是一个底层的IOC，Application扩展了BeanFactory

啥时候用BeanFactory和ApplicationContext？

启动
1、准备prepareRefresh
2、获取obtainFreshBeanFactory
3、prepareBeanFactory
4、postProcessBeanFactory自定义扩展
5、this.invokeBeanFactoryPostProcessors(beanFactory);
6、this.registerBeanPostProcessors(beanFactory);
7、this.initMessageSource();
8、this.initApplicationEventMulticaster();
9、this.onRefresh();
10、this.registerListeners();
11、this.finishBeanFactoryInitialization(beanFactory);
12、this.finishRefresh();

关闭
close

BeanFactory和FactoryBean
BeanFactory是IOC的底层容器
FactoryBean创建Bean的方式，实现工厂Bean的方法就是继承FactoryBean

Spring IOC启动准备那些东西？
IOC配置元数据读取和解析，IOC容器生命周期，Spring事件发布，国际化等。

# Spring Bean基础

 什么是BeanDefinition？ 
• BeanDefinition 是Spring Framework 中定义Bean 的配置元信息接口，包含： 
• Bean 的类名
• Bean 行为配置元素，如作用域、自动绑定的模式，生命周期回调等
• 其他Bean 引用，又可称作合作者（collaborators）或者依赖（dependencies）
• 配置设置，比如Bean 属性（Properties）

 BeanDefinition 元信息 
 属性（Property） 说明 
 Class Bean 全类名，必须是具体类，不能用抽象类或接口 
 Name Bean 的名称或者ID 
 Scope Bean 的作用域（如：singleton、prototype 等） 
 Constructor arguments Bean 构造器参数（用于依赖注入） 
 Properties Bean 属性设置（用于依赖注入） 
 Autowiring mode Bean 自动绑定模式（如：通过名称byName） 
 Lazy initialization mode Bean 延迟初始化模式（延迟和非延迟） 
 Initialization method Bean 初始化回调方法名称 
 Destruction method Bean 销毁回调方法名称
 
 BeanNameGenerator(Bean名称生成器)
 
 别名
 Bean 别名（Alias）的价值 • 复用现有的BeanDefinition • 更具有场景化的命名方法，比如： <alias name="myApp-dataSource" alias="subsystemA-dataSource"/> <alias name="myApp-dataSource" alias="subsystemB-dataSource"/>
 
 注册Spring Bean
 
  BeanDefinition 注册 
  • XML 配置元信息 • <bean name=”...” ... /> 
  • Java 注解配置元信息 • @Bean • @Component • @Import 
  • Java API 配置元信息 • 命名方式：BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition) • 非命名方式： BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition,Be anDefinitionRegistry) • 配置类方式：AnnotatedBeanDefinitionReader#register(Class...)
 








