1、创建maven的web项目
2、引入web和web-mvc包
3、web.xml设置
```xml
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```
4、配置spring-mvc.xml配置
```xml
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/hello/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```


https://search.maven.org
https://repository.apache.org
https://mvnrepository.com
