package org.imzdong.study.mybatis.demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.imzdong.study.mybatis.domain.AnnotationUserMapper;
import org.imzdong.study.mybatis.domain.User;
import org.imzdong.study.mybatis.domain.XmlUserMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: xml配置mybatis
 * @author: Winter
 * @time: 2020/4/6
 */
public class XmlMyBatisDemo {

    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            try (SqlSession session = build.openSession()) {
                //User user = session.selectOne("org.imzdong.study.mybatis.domain.UserMapper.selectUserById", 1);
                XmlUserMapper mapper = session.getMapper(XmlUserMapper.class);
                User user = mapper.selectUserById(1);
                System.out.println("user: "+user);
                AnnotationUserMapper annotationMapper = session.getMapper(AnnotationUserMapper.class);
                User userAnnotation = annotationMapper.selectUserById(1);
                System.out.println("userAnnotation: "+userAnnotation);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
