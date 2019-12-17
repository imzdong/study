### @SpringBootApplication
@SpringBootApplication标记spring-boot的启动类。配置扫描包，过滤bean等。
等价于@Configuration+@EnableAutoConfiguration（自动配置）+@ComponentScan（组件扫描）（spring-boot1.1及以前的版本使用3个注解），1.2.0及以后的版本整合为一个
#### @Configuration
标注配置类，相当于xml配置.示例：ConfigureMain
#### @ComponentScan
指定包扫描路径，可配置显示过滤条件
TypeExcludeFilter
AutoConfigurationExcludeFilter
#### @SpringBootConfiguration
相当于@Configuration
#### @EnableAutoConfiguration
装配组件有三种方式
1、使用@Component(spring2.5+)
2、使用@Configuration+@Bean(3.0+)
3、使用模块@EnableXXX+@Import(3.1+)
Import:引入类
AutoConfigurationPackage：初始化自动扫描包（主类当前包及子包）
Registrar实现 registerBeanDefinitions
AutoConfigurationImportSelector：
selectImports



