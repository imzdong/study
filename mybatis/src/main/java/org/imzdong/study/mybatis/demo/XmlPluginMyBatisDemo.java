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
public class XmlPluginMyBatisDemo {

    public static void main(String[] args) {
        String resource = "mybatis-config-plugin.xml";
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            try (SqlSession session = build.openSession()) {
                User user = session.selectOne("org.imzdong.study.mybatis.domain.XmlUserMapper.selectUserById", 1);
                System.out.println("userAnnotation: "+user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
