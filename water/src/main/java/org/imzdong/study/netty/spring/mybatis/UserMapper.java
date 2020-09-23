package org.imzdong.study.netty.spring.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.imzdong.study.netty.spring.mybatis.model.User;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月31日, 0031 21:33
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUser(@Param("userId") String userId);
}
