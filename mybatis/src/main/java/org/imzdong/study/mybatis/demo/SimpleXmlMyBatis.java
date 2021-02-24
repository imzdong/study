package org.imzdong.study.mybatis.demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.imzdong.study.mybatis.domain.User;
import org.imzdong.study.mybatis.domain.XmlUserMapper;

import java.io.InputStream;

/**
 * @author zhoudong6
 * @since 2021年2月24日, 0024 14:56
 */
public class SimpleXmlMyBatis {

    public static void main(String[] args) throws Exception{
        String resource = "mybatis-simple-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            XmlUserMapper mapper = session.getMapper(XmlUserMapper.class);
            User user = mapper.selectUserById(1);
            System.out.println(user);
        }
    }
}
