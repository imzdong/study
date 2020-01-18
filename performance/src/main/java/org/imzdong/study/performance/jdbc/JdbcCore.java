package org.imzdong.study.performance.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月23日, 0023 17:14
 */
public class JdbcCore {

    private final static Logger logger = LoggerFactory.getLogger(JdbcCore.class);

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/world?serverTimezone=UTC&characterEncoding=UTF-8",
                "root", "root")){
            String sql = "select * from user;";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                logger.info(resultSet.getString("id")+"-"+resultSet.getString("name")+"-"+resultSet.getString("item"));
            }
        } catch (SQLException e) {
            logger.error("Jdbc异常：",e);
        }

    }
}
