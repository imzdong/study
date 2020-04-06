package org.imzdong.study.mybatis.domain;


/**
 * @description:
 * @author: Winter
 * @time: 2020/4/6
 */
public interface XmlUserMapper {
    /**
     * 两种配置方法：
     * 1、通过注解sql语句。
     * 2、通过mapper.xml文件配置
     * @param id
     * @return
     */
    //@Select("SELECT * FROM user WHERE id = #{id}")
    User selectUserById(int id);
}
