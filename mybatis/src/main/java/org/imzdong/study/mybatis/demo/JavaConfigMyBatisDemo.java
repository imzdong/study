package org.imzdong.study.mybatis.demo;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.imzdong.study.mybatis.domain.AnnotationUserMapper;
import org.imzdong.study.mybatis.domain.User;


/**
 * @description: 非xml配置mybatis
 * @author: Winter
 * @time: 2020/4/6
 */
public class JavaConfigMyBatisDemo {

    public static void main(String[] args) {
        //1、获取mysql数据库连接
        String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        //2、构建事务管理工厂
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        //3、构造环境
        Environment environment = new Environment("development", transactionFactory, dataSource);
        //4、初始化配置
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(AnnotationUserMapper.class);
        //configuration.addMapper(XmlUserMapper.class);
        //5、获取SqlSessionFactory
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession session = build.openSession()) {
            AnnotationUserMapper annotationMapper = session.getMapper(AnnotationUserMapper.class);
            User userAnnotation = annotationMapper.selectUserById(1);
            System.out.println("userAnnotation: "+userAnnotation);
            /*XmlUserMapper mapper = session.getMapper(XmlUserMapper.class);
            User user = mapper.selectUserById(1);
            System.out.println("user: "+user);*/
        }
    }
}
